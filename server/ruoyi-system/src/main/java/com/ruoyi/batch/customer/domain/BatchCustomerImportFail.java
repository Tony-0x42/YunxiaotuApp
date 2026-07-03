package com.ruoyi.batch.customer.domain;

/**
 * 客户导入失败记录
 */
public class BatchCustomerImportFail
{
    private static final long serialVersionUID = 1L;

    /** Excel 行号 */
    private Integer rowNum;

    /** 账号名称 */
    private String customerName;

    /** 手机号 */
    private String phone;

    /** 失败原因 */
    private String reason;

    public Integer getRowNum()
    {
        return rowNum;
    }

    public void setRowNum(Integer rowNum)
    {
        this.rowNum = rowNum;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }
}
