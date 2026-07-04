package com.ruoyi.batch.aivideo.service;

import java.util.List;
import com.ruoyi.batch.aivideo.domain.BatchAiVideoGenerateBody;
import com.ruoyi.batch.aivideo.domain.BatchAiVideoGroup;

/**
 * AI 云创视频服务接口
 */
public interface IBatchAiVideoService
{
    /**
     * 查询当前账号视频组列表
     */
    public List<BatchAiVideoGroup> selectBatchAiVideoGroupList(String phone);

    /**
     * 根据ID查询视频组（含分镜头）
     */
    public BatchAiVideoGroup selectBatchAiVideoGroupById(Long groupId);

    /**
     * 新增视频组
     */
    public int insertBatchAiVideoGroup(BatchAiVideoGroup group);

    /**
     * 修改视频组（含分镜头覆盖保存）
     */
    public int updateBatchAiVideoGroup(BatchAiVideoGroup group);

    /**
     * 删除视频组（含分镜头）
     */
    public int deleteBatchAiVideoGroupById(Long groupId);

    /**
     * 提交 AI 视频生成任务
     *
     * @param phone 当前账号手机号
     * @param body  生成请求
     * @return 生成记录 logId
     */
    public Long submitGenerate(String phone, BatchAiVideoGenerateBody body);
}
