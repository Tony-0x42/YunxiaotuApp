package com.ruoyi.batch.customer.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户导入结果
 */
public class BatchCustomerImportResult
{
    private static final long serialVersionUID = 1L;

    /** 成功条数 */
    private int successCount;

    /** 失败条数 */
    private int failCount;

    /** 失败明细 */
    private List<BatchCustomerImportFail> failList = new ArrayList<>();

    public int getSuccessCount()
    {
        return successCount;
    }

    public void setSuccessCount(int successCount)
    {
        this.successCount = successCount;
    }

    public int getFailCount()
    {
        return failCount;
    }

    public void setFailCount(int failCount)
    {
        this.failCount = failCount;
    }

    public List<BatchCustomerImportFail> getFailList()
    {
        return failList;
    }

    public void setFailList(List<BatchCustomerImportFail> failList)
    {
        this.failList = failList;
    }

    public void addSuccess()
    {
        this.successCount++;
    }

    public void addFail(BatchCustomerImportFail fail)
    {
        this.failCount++;
        this.failList.add(fail);
    }
}
