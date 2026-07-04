package com.ruoyi.batch.contact.service;

import java.util.List;
import com.ruoyi.batch.contact.domain.BatchContact;

/**
 * APP 客服联系 服务层
 *
 * @author ruoyi
 */
public interface IBatchContactService
{
    /**
     * 查询联系信息
     *
     * @param contactId 联系ID
     * @return 联系信息
     */
    public BatchContact selectBatchContactById(Long contactId);

    /**
     * 查询联系列表
     *
     * @param contact 联系信息
     * @return 联系集合
     */
    public List<BatchContact> selectBatchContactList(BatchContact contact);

    /**
     * 新增联系
     *
     * @param contact 联系信息
     * @return 结果
     */
    public int insertBatchContact(BatchContact contact);

    /**
     * 修改联系
     *
     * @param contact 联系信息
     * @return 结果
     */
    public int updateBatchContact(BatchContact contact);

    /**
     * 删除联系信息
     *
     * @param contactId 联系ID
     * @return 结果
     */
    public int deleteBatchContactById(Long contactId);

    /**
     * 批量删除联系信息
     *
     * @param contactIds 需要删除的联系ID
     * @return 结果
     */
    public int deleteBatchContactByIds(Long[] contactIds);
}
