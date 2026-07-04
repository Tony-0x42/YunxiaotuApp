package com.ruoyi.batch.computing.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.batch.computing.domain.BatchComputingPowerLog;
import com.ruoyi.batch.computing.mapper.BatchComputingPowerLogMapper;
import com.ruoyi.batch.computing.service.IBatchComputingPowerLogService;
import com.ruoyi.batch.customer.domain.BatchCustomer;
import com.ruoyi.batch.customer.mapper.BatchCustomerMapper;

/**
 * computing业务模块Service业务层处理占位
 *
 * @author ruoyi
 */
@Service
public class BatchComputingPowerLogServiceImpl implements IBatchComputingPowerLogService
{
    @Autowired
    private BatchComputingPowerLogMapper batchComputingPowerLogMapper;

    @Autowired
    private BatchCustomerMapper batchCustomerMapper;

    @Override
    public List<BatchComputingPowerLog> selectList(BatchComputingPowerLog batchComputingPowerLog)
    {
        return batchComputingPowerLogMapper.selectList(batchComputingPowerLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BigDecimal consumeComputingPower(String phone, Integer operationType, BigDecimal consumeValue, String bizNo)
    {
        if (consumeValue == null || consumeValue.compareTo(BigDecimal.ZERO) <= 0)
        {
            throw new ServiceException("消耗算力值必须大于 0");
        }

        BatchCustomer customer = batchCustomerMapper.selectBatchCustomerByPhone(phone);
        if (customer == null)
        {
            throw new ServiceException("账号不存在");
        }

        BigDecimal total = customer.getComputingPowerTotal() != null ? customer.getComputingPowerTotal() : BigDecimal.ZERO;
        BigDecimal used = customer.getComputingPowerUsed() != null ? customer.getComputingPowerUsed() : BigDecimal.ZERO;
        BigDecimal remain = total.subtract(used);

        if (remain.compareTo(consumeValue) < 0)
        {
            throw new ServiceException("当前算力已耗尽，请联系管理员增加算力额度");
        }

        BigDecimal newUsed = used.add(consumeValue);
        BigDecimal newRemain = total.subtract(newUsed);

        BatchCustomer update = new BatchCustomer();
        update.setCustomerId(customer.getCustomerId());
        update.setComputingPowerUsed(newUsed);
        update.setComputingPowerRemain(newRemain);
        update.setUpdateTime(DateUtils.getNowDate());
        batchCustomerMapper.updateBatchCustomer(update);

        BatchComputingPowerLog log = new BatchComputingPowerLog();
        log.setPhone(phone);
        log.setOperationType(operationType);
        log.setConsumeValue(consumeValue);
        log.setRemainValue(newRemain);
        log.setVideoGroupName(bizNo);
        log.setCreateTime(DateUtils.getNowDate());
        batchComputingPowerLogMapper.insert(log);

        return newRemain;
    }
}
