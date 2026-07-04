package com.ruoyi.batch.computing.service;

import java.math.BigDecimal;
import java.util.List;
import com.ruoyi.batch.computing.domain.BatchComputingPowerLog;

/**
 * computing业务模块Service接口占位
 *
 * @author ruoyi
 */
public interface IBatchComputingPowerLogService
{
    /**
     * 查询列表
     *
     * @param batchComputingPowerLog 查询条件
     * @return 结果列表
     */
    public List<BatchComputingPowerLog> selectList(BatchComputingPowerLog batchComputingPowerLog);

    /**
     * 检查并消耗算力
     *
     * @param phone 账号手机号
     * @param operationType 操作类型：1 生成 / 2 下载
     * @param consumeValue 消耗算力值
     * @param bizNo 业务单号/说明
     * @return 消耗后剩余算力
     */
    public BigDecimal consumeComputingPower(String phone, Integer operationType, BigDecimal consumeValue, String bizNo);
}
