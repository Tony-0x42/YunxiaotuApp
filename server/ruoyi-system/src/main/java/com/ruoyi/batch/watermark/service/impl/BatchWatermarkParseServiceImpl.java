package com.ruoyi.batch.watermark.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.batch.watermark.domain.BatchWatermarkParse;
import com.ruoyi.batch.watermark.mapper.BatchWatermarkParseMapper;
import com.ruoyi.batch.watermark.service.IBatchWatermarkParseService;

/**
 * AI 去水印解析记录 Service 业务层处理
 *
 * @author ruoyi
 */
@Service
public class BatchWatermarkParseServiceImpl implements IBatchWatermarkParseService
{
    /** 解析状态：已完成 */
    private static final int STATUS_FINISHED = 2;

    /** 解析状态：失败 */
    private static final int STATUS_FAILED = 9;

    /** 删除标志：存在 */
    private static final int DEL_FLAG_EXIST = 0;

    private static final Logger log = LoggerFactory.getLogger(BatchWatermarkParseServiceImpl.class);

    @Autowired
    private BatchWatermarkParseMapper batchWatermarkParseMapper;

    @Override
    public List<BatchWatermarkParse> selectBatchWatermarkParseList(BatchWatermarkParse batchWatermarkParse)
    {
        return batchWatermarkParseMapper.selectBatchWatermarkParseList(batchWatermarkParse);
    }

    @Override
    public BatchWatermarkParse selectBatchWatermarkParseById(Long parseId)
    {
        return batchWatermarkParseMapper.selectBatchWatermarkParseById(parseId);
    }

    @Override
    public BatchWatermarkParse parseLink(String phone, String sourceLink)
    {
        BatchWatermarkParse parse = new BatchWatermarkParse();
        parse.setPhone(phone);
        parse.setSourceLink(sourceLink);
        parse.setDelFlag(DEL_FLAG_EXIST);
        parse.setCreateBy(phone);
        parse.setCreateTime(DateUtils.getNowDate());

        if (StringUtils.isEmpty(sourceLink) || !sourceLink.startsWith("http"))
        {
            return saveFailed(parse, "链接格式不正确，请以 http(s):// 开头");
        }

        String platform = detectPlatform(sourceLink);
        if (StringUtils.isEmpty(platform))
        {
            return saveFailed(parse, "暂不支持该平台链接解析，目前仅支持抖音、小红书");
        }
        parse.setPlatform(platform);

        try
        {
            JSONObject result = callPythonParser(sourceLink);
            if (result == null)
            {
                return saveFailed(parse, "解析服务未返回数据，请稍后重试");
            }
            if (result.containsKey("error"))
            {
                return saveFailed(parse, result.getString("error"));
            }

            parse.setParseStatus(STATUS_FINISHED);
            parse.setVideoUrl(result.getString("videoUrl"));
            parse.setVideoText(result.getString("text"));

            List<String> images = new ArrayList<>();
            if (result.get("images") instanceof List)
            {
                for (Object item : result.getJSONArray("images"))
                {
                    if (item != null)
                    {
                        images.add(item.toString());
                    }
                }
            }
            if (!images.isEmpty())
            {
                parse.setImageUrls(StringUtils.join(images, ","));
            }

            batchWatermarkParseMapper.insertBatchWatermarkParse(parse);
            return parse;
        }
        catch (Exception e)
        {
            log.error("去水印解析异常，phone={}, link={}", phone, sourceLink, e);
            String reason = StringUtils.isNotEmpty(e.getMessage()) ? e.getMessage() : "解析失败，请检查链接是否有效或稍后重试";
            return saveFailed(parse, reason);
        }
    }

    /**
     * 保存失败记录并返回
     */
    private BatchWatermarkParse saveFailed(BatchWatermarkParse parse, String failReason)
    {
        parse.setParseStatus(STATUS_FAILED);
        parse.setFailReason(failReason);
        batchWatermarkParseMapper.insertBatchWatermarkParse(parse);
        return parse;
    }

    /**
     * 调用 Python 解析脚本
     */
    private JSONObject callPythonParser(String sourceLink) throws Exception
    {
        String pythonCmd = System.getProperty("batch.python.cmd", System.getenv().getOrDefault("PYTHON_CMD", "python"));
        File scriptFile = resolveScriptFile();
        if (scriptFile == null || !scriptFile.exists())
        {
            throw new RuntimeException("未找到视频解析脚本");
        }

        ProcessBuilder pb = new ProcessBuilder(pythonCmd, scriptFile.getAbsolutePath(), sourceLink);
        pb.redirectErrorStream(true);
        pb.environment().put("PYTHONIOENCODING", "utf-8");
        Process process = pb.start();

        StringBuilder output = new StringBuilder();
        try (InputStream is = process.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                output.append(line).append("\n");
            }
        }

        boolean finished = process.waitFor(120, java.util.concurrent.TimeUnit.SECONDS);
        if (!finished)
        {
            process.destroyForcibly();
            throw new RuntimeException("解析服务响应超时");
        }

        String text = output.toString().trim();
        if (process.exitValue() != 0)
        {
            String err = extractErrorFromOutput(text);
            throw new RuntimeException(StringUtils.isNotEmpty(err) ? err : "解析服务执行失败");
        }
        if (StringUtils.isEmpty(text))
        {
            throw new RuntimeException("解析结果为空");
        }
        return JSON.parseObject(text);
    }

    /**
     * 从脚本输出中提取 error 字段内容
     */
    private String extractErrorFromOutput(String output)
    {
        if (StringUtils.isEmpty(output))
        {
            return null;
        }
        try
        {
            JSONObject obj = JSON.parseObject(output);
            if (obj != null && obj.containsKey("error"))
            {
                return obj.getString("error");
            }
        }
        catch (Exception ignored)
        {
        }
        return output.length() > 200 ? output.substring(0, 200) + "..." : output;
    }

    /**
     * 定位解析脚本路径
     */
    private File resolveScriptFile()
    {
        String userDir = System.getProperty("user.dir");
        List<String> candidates = Arrays.asList(
                userDir + File.separator + "scripts" + File.separator + "video_parse.py",
                userDir + File.separator + ".." + File.separator + "scripts" + File.separator + "video_parse.py");
        String envPath = System.getenv("VIDEO_PARSE_SCRIPT");
        if (StringUtils.isNotEmpty(envPath))
        {
            candidates = new ArrayList<>(candidates);
            candidates.add(0, envPath);
        }
        for (String path : candidates)
        {
            File file = new File(path);
            if (file.exists())
            {
                return file;
            }
        }
        return null;
    }

    @Override
    public int insertBatchWatermarkParse(BatchWatermarkParse batchWatermarkParse)
    {
        if (batchWatermarkParse.getCreateTime() == null)
        {
            batchWatermarkParse.setCreateTime(DateUtils.getNowDate());
        }
        if (batchWatermarkParse.getDelFlag() == null)
        {
            batchWatermarkParse.setDelFlag(DEL_FLAG_EXIST);
        }
        return batchWatermarkParseMapper.insertBatchWatermarkParse(batchWatermarkParse);
    }

    @Override
    public int updateBatchWatermarkParse(BatchWatermarkParse batchWatermarkParse)
    {
        batchWatermarkParse.setUpdateTime(DateUtils.getNowDate());
        return batchWatermarkParseMapper.updateBatchWatermarkParse(batchWatermarkParse);
    }

    @Override
    public int deleteBatchWatermarkParseByIds(Long[] parseIds)
    {
        return batchWatermarkParseMapper.deleteBatchWatermarkParseByIds(parseIds);
    }

    /**
     * 根据链接识别平台，目前仅支持抖音、小红书
     */
    private String detectPlatform(String sourceLink)
    {
        String lower = sourceLink.toLowerCase();
        if (lower.contains("douyin") || lower.contains("iesdouyin"))
        {
            return "douyin";
        }
        if (lower.contains("xiaohongshu") || lower.contains("xhslink"))
        {
            return "xiaohongshu";
        }
        return null;
    }
}
