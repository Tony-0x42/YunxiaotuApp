package com.ruoyi.batch.contact.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.batch.contact.domain.BatchContact;
import com.ruoyi.batch.contact.mapper.BatchContactMapper;
import com.ruoyi.batch.contact.service.IBatchContactService;

/**
 * APP 客服联系 服务层实现
 *
 * @author ruoyi
 */
@Service
public class BatchContactServiceImpl implements IBatchContactService
{
    @Autowired
    private BatchContactMapper contactMapper;

    /**
     * 查询联系信息
     *
     * @param contactId 联系ID
     * @return 联系信息
     */
    @Override
    public BatchContact selectBatchContactById(Long contactId)
    {
        return contactMapper.selectBatchContactById(contactId);
    }

    /**
     * 查询联系列表
     *
     * @param contact 联系信息
     * @return 联系集合
     */
    @Override
    public List<BatchContact> selectBatchContactList(BatchContact contact)
    {
        return contactMapper.selectBatchContactList(contact);
    }

    /**
     * 新增联系
     *
     * @param contact 联系信息
     * @return 结果
     */
    @Override
    public int insertBatchContact(BatchContact contact)
    {
        return contactMapper.insertBatchContact(contact);
    }

    /**
     * 修改联系
     *
     * @param contact 联系信息
     * @return 结果
     */
    @Override
    public int updateBatchContact(BatchContact contact)
    {
        return contactMapper.updateBatchContact(contact);
    }

    /**
     * 删除联系对象
     *
     * @param contactId 联系ID
     * @return 结果
     */
    @Override
    public int deleteBatchContactById(Long contactId)
    {
        return contactMapper.deleteBatchContactById(contactId);
    }

    /**
     * 批量删除联系信息
     *
     * @param contactIds 需要删除的联系ID
     * @return 结果
     */
    @Override
    public int deleteBatchContactByIds(Long[] contactIds)
    {
        return contactMapper.deleteBatchContactByIds(contactIds);
    }
}
