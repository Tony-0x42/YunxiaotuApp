package com.ruoyi.batch.aivideo.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.batch.aivideo.domain.BatchAiVideoGenerateBody;
import com.ruoyi.batch.aivideo.domain.BatchAiVideoGroup;
import com.ruoyi.batch.aivideo.service.IBatchAiVideoService;

/**
 * APP 端 AI 云创视频接口
 */
@Validated
@RestController
@RequestMapping("/batch/ai/video")
public class BatchAiVideoController extends BaseController
{
    @Autowired
    private IBatchAiVideoService aiVideoService;

    /**
     * 查询当前账号视频组列表
     */
    @PreAuthorize("@ss.hasPermi('app:user')")
    @GetMapping("/group/list")
    public TableDataInfo list()
    {
        String phone = getLoginUser().getUsername();
        startPage();
        List<BatchAiVideoGroup> list = aiVideoService.selectBatchAiVideoGroupList(phone);
        return getDataTable(list);
    }

    /**
     * 获取视频组详情（含分镜头）
     */
    @PreAuthorize("@ss.hasPermi('app:user')")
    @GetMapping("/group/{groupId}")
    public AjaxResult getInfo(@PathVariable("groupId") Long groupId)
    {
        return success(aiVideoService.selectBatchAiVideoGroupById(groupId));
    }

    /**
     * 新增视频组
     */
    @PreAuthorize("@ss.hasPermi('app:user')")
    @PostMapping("/group")
    public AjaxResult add(@Valid @RequestBody BatchAiVideoGroup group)
    {
        String phone = getLoginUser().getUsername();
        group.setPhone(phone);
        int rows = aiVideoService.insertBatchAiVideoGroup(group);
        AjaxResult ajax = toAjax(rows);
        ajax.put("groupId", group.getGroupId());
        return ajax;
    }

    /**
     * 修改视频组（含分镜头覆盖保存）
     */
    @PreAuthorize("@ss.hasPermi('app:user')")
    @PutMapping("/group")
    public AjaxResult edit(@RequestBody BatchAiVideoGroup group)
    {
        return toAjax(aiVideoService.updateBatchAiVideoGroup(group));
    }

    /**
     * 删除视频组
     */
    @PreAuthorize("@ss.hasPermi('app:user')")
    @DeleteMapping("/group/{groupId}")
    public AjaxResult remove(@PathVariable("groupId") Long groupId)
    {
        return toAjax(aiVideoService.deleteBatchAiVideoGroupById(groupId));
    }

    /**
     * 提交 AI 视频生成
     */
    @PreAuthorize("@ss.hasPermi('app:user')")
    @PostMapping("/generate")
    public AjaxResult generate(@Valid @RequestBody BatchAiVideoGenerateBody body)
    {
        String phone = getLoginUser().getUsername();
        Long logId = aiVideoService.submitGenerate(phone, body);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("logId", logId);
        return ajax;
    }
}
