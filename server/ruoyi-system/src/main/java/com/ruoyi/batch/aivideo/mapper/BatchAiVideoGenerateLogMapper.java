package com.ruoyi.batch.aivideo.mapper;

import com.ruoyi.batch.aivideo.domain.BatchAiVideoGenerateLog;

/**
 * AI 视频生成记录Mapper接口
 */
public interface BatchAiVideoGenerateLogMapper
{
    /**
     * 新增生成记录
     */
    public int insertBatchAiVideoGenerateLog(BatchAiVideoGenerateLog log);
}
