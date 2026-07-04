package com.ruoyi.batch.contact.controller;

import java.util.List;
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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.batch.contact.domain.BatchContact;
import com.ruoyi.batch.contact.service.IBatchContactService;

/**
 * APP 客服联系Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/batch/contact")
public class BatchContactController extends BaseController
{
    @Autowired
    private IBatchContactService batchContactService;

    /**
     * 查询联系列表（APP 端匿名可访问）
     */
    @PreAuthorize("isAnonymous() or @ss.hasPermi('batch:contact:list') or @ss.hasPermi('app:user')")
    @GetMapping("/list")
    public AjaxResult list(BatchContact batchContact)
    {
        if (batchContact.getStatus() == null)
        {
            batchContact.setStatus(0);
        }
        if (batchContact.getDelFlag() == null)
        {
            batchContact.setDelFlag(0);
        }
        List<BatchContact> list = batchContactService.selectBatchContactList(batchContact);
        return success(list);
    }

    /**
     * 根据联系编号获取详细信息
     */
    @PreAuthorize("isAnonymous() or @ss.hasPermi('batch:contact:query') or @ss.hasPermi('app:user')")
    @GetMapping(value = "/{contactId}")
    public AjaxResult getInfo(@PathVariable Long contactId)
    {
        return success(batchContactService.selectBatchContactById(contactId));
    }

    /**
     * 新增联系
     */
    @PreAuthorize("@ss.hasPermi('batch:contact:add')")
    @Log(title = "联系方式", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BatchContact batchContact)
    {
        batchContact.setCreateBy(getUsername());
        return toAjax(batchContactService.insertBatchContact(batchContact));
    }

    /**
     * 修改联系
     */
    @PreAuthorize("@ss.hasPermi('batch:contact:edit')")
    @Log(title = "联系方式", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BatchContact batchContact)
    {
        batchContact.setUpdateBy(getUsername());
        return toAjax(batchContactService.updateBatchContact(batchContact));
    }

    /**
     * 修改联系状态
     */
    @PreAuthorize("@ss.hasPermi('batch:contact:edit')")
    @Log(title = "联系方式", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody BatchContact batchContact)
    {
        batchContact.setUpdateBy(getUsername());
        return toAjax(batchContactService.updateBatchContact(batchContact));
    }

    /**
     * 删除联系
     */
    @PreAuthorize("@ss.hasPermi('batch:contact:remove')")
    @Log(title = "联系方式", businessType = BusinessType.DELETE)
    @DeleteMapping("/{contactIds}")
    public AjaxResult remove(@PathVariable Long[] contactIds)
    {
        return toAjax(batchContactService.deleteBatchContactByIds(contactIds));
    }
}
