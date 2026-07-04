package com.ruoyi.batch.watermark.service;

import java.util.List;
import com.ruoyi.batch.watermark.domain.BatchWatermarkParse;

/**
 * AI 去水印解析记录 Service 接口
 *
 * @author ruoyi
 */
public interface IBatchWatermarkParseService
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
     * 解析视频链接并保存记录
     *
     * @param phone 当前登录手机号
     * @param sourceLink 原始分享链接
     * @return 解析记录
     */
    public BatchWatermarkParse parseLink(String phone, String sourceLink);

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
