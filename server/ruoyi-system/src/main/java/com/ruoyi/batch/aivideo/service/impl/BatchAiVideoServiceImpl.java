package com.ruoyi.batch.aivideo.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.batch.aivideo.domain.BatchAiVideoClip;
import com.ruoyi.batch.aivideo.domain.BatchAiVideoGenerateBody;
import com.ruoyi.batch.aivideo.domain.BatchAiVideoGenerateLog;
import com.ruoyi.batch.aivideo.domain.BatchAiVideoGroup;
import com.ruoyi.batch.aivideo.mapper.BatchAiVideoClipMapper;
import com.ruoyi.batch.aivideo.mapper.BatchAiVideoGenerateLogMapper;
import com.ruoyi.batch.aivideo.mapper.BatchAiVideoGroupMapper;
import com.ruoyi.batch.aivideo.service.IBatchAiVideoService;
import com.ruoyi.batch.customer.domain.BatchCustomer;
import com.ruoyi.batch.customer.mapper.BatchCustomerMapper;

/**
 * AI 云创视频服务实现
 */
@Service
public class BatchAiVideoServiceImpl implements IBatchAiVideoService
{
    @Autowired
    private BatchAiVideoGroupMapper groupMapper;

    @Autowired
    private BatchAiVideoClipMapper clipMapper;

    @Autowired
    private BatchCustomerMapper customerMapper;

    @Autowired
    private BatchAiVideoGenerateLogMapper generateLogMapper;

    @Override
    public List<BatchAiVideoGroup> selectBatchAiVideoGroupList(String phone)
    {
        BatchAiVideoGroup query = new BatchAiVideoGroup();
        query.setPhone(phone);
        return groupMapper.selectBatchAiVideoGroupList(query);
    }

    @Override
    public BatchAiVideoGroup selectBatchAiVideoGroupById(Long groupId)
    {
        BatchAiVideoGroup group = groupMapper.selectBatchAiVideoGroupById(groupId);
        if (group != null)
        {
            group.setClips(clipMapper.selectBatchAiVideoClipByGroupId(groupId));
        }
        return group;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBatchAiVideoGroup(BatchAiVideoGroup group)
    {
        if (group.getGeneratedCount() == null)
        {
            group.setGeneratedCount(0);
        }
        if (group.getMaxLimit() == null)
        {
            group.setMaxLimit(10);
        }
        if (group.getStatus() == null)
        {
            group.setStatus(0);
        }
        if (group.getDelFlag() == null)
        {
            group.setDelFlag(0);
        }
        group.setCreateTime(DateUtils.getNowDate());
        return groupMapper.insertBatchAiVideoGroup(group);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBatchAiVideoGroup(BatchAiVideoGroup group)
    {
        group.setUpdateTime(DateUtils.getNowDate());
        int rows = groupMapper.updateBatchAiVideoGroup(group);
        if (rows > 0 && group.getClips() != null)
        {
            saveClips(group.getGroupId(), group.getClips());
        }
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatchAiVideoGroupById(Long groupId)
    {
        clipMapper.deleteBatchAiVideoClipByGroupId(groupId);
        return groupMapper.deleteBatchAiVideoGroupById(groupId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long submitGenerate(String phone, BatchAiVideoGenerateBody body)
    {
        Long groupId = body.getGroupId();
        BatchAiVideoGroup group = groupMapper.selectBatchAiVideoGroupById(groupId);
        if (group == null)
        {
            throw new ServiceException("视频组不存在");
        }
        if (!phone.equals(group.getPhone()))
        {
            throw new ServiceException("无权操作该视频组");
        }

        // 算力检查
        BatchCustomer customer = customerMapper.selectBatchCustomerByPhone(phone);
        if (customer == null)
        {
            throw new ServiceException("账号信息异常");
        }
        BigDecimal remain = customer.getComputingPowerRemain() != null ? customer.getComputingPowerRemain() : BigDecimal.ZERO;
        int consume = body.getConsumeValue() != null ? body.getConsumeValue() : 1;
        if (remain.compareTo(new BigDecimal(consume)) < 0)
        {
            throw new ServiceException("当前算力已耗尽，请联系管理员增加算力额度");
        }

        // 保存最新分镜头
        if (body.getClips() != null && !body.getClips().isEmpty())
        {
            saveClips(groupId, body.getClips());
        }

        // 扣减算力
        int rows = customerMapper.consumeComputingPower(customer.getCustomerId(), new BigDecimal(consume));
        if (rows <= 0)
        {
            throw new ServiceException("算力扣减失败");
        }

        // 增加已生成次数
        groupMapper.incrementGeneratedCount(groupId);

        // 写入生成记录（同步视为成功）
        BatchAiVideoGenerateLog log = new BatchAiVideoGenerateLog();
        log.setPhone(phone);
        log.setVideoGroupName(group.getGroupName());
        log.setGenerateCount(1);
        log.setStatus(0);
        log.setCreateTime(DateUtils.getNowDate());
        generateLogMapper.insertBatchAiVideoGenerateLog(log);

        return log.getLogId();
    }

    /**
     * 覆盖保存分镜头
     */
    private void saveClips(Long groupId, List<BatchAiVideoClip> clips)
    {
        clipMapper.deleteBatchAiVideoClipByGroupId(groupId);
        if (clips == null || clips.isEmpty())
        {
            return;
        }
        int order = 0;
        for (BatchAiVideoClip clip : clips)
        {
            clip.setGroupId(groupId);
            clip.setSortOrder(order++);
            clipMapper.insertBatchAiVideoClip(clip);
        }
    }
}
