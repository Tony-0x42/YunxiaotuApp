package com.ruoyi.batch.watermark.mapper;

import java.util.List;
import com.ruoyi.batch.watermark.domain.BatchWatermarkParse;

/**
 * AI 去水印解析记录 Mapper 接口
 *
 * @author ruoyi
 */
public interface BatchWatermarkParseMapper
{
    /**
     * 查询解析记录列表
     *
     * @param batchWatermarkParse 查询条件
     * @return 解析记录列表
     */
    public List<BatchWatermarkParse> selectBatchWatermarkParseList(BatchWatermarkParse batchWatermarkParse);

    /**
     * 根据 ID 查询解析记录
     *
     * @param parseId 解析记录 ID
     * @return 解析记录
     */
    public BatchWatermarkParse selectBatchWatermarkParseById(Long parseId);

    /**
     * 新增解析记录
     *
     * @param batchWatermarkParse 解析记录
     * @return 影响行数
     */
    public int insertBatchWatermarkParse(BatchWatermarkParse batchWatermarkParse);

    /**
     * 修改解析记录
     *
     * @param batchWatermarkParse 解析记录
     * @return 影响行数
     */
    public int updateBatchWatermarkParse(BatchWatermarkParse batchWatermarkParse);

    /**
     * 批量删除解析记录
     *
     * @param parseIds 解析记录 ID 数组
     * @return 影响行数
     */
    public int deleteBatchWatermarkParseByIds(Long[] parseIds);
}
