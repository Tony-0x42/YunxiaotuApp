package com.ruoyi.batch.contact.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * APP 客服联系表 batch_contact
 *
 * @author ruoyi
 */
public class BatchContact extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 联系ID */
    private Long contactId;

    /** 联系人/名称 */
    private String contactName;

    /** 区域/说明 */
    private String region;

    /** 电话 */
    private String phone;

    /** 类型：1 在线客服 / 2 总部热线 / 3 区域联系 */
    private Integer contactType;

    /** 排序权重 */
    private Integer sortWeight;

    /** 状态：0 启用 / 1 禁用 */
    private Integer status;

    /** 删除标志：0 存在 / 2 删除 */
    private Integer delFlag;

    public Long getContactId()
    {
        return contactId;
    }

    public void setContactId(Long contactId)
    {
        this.contactId = contactId;
    }

    @NotBlank(message = "联系人/名称不能为空")
    @Size(min = 0, max = 100, message = "联系人/名称长度不能超过100个字符")
    public String getContactName()
    {
        return contactName;
    }

    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }

    @Size(min = 0, max = 100, message = "区域/说明长度不能超过100个字符")
    public String getRegion()
    {
        return region;
    }

    public void setRegion(String region)
    {
        this.region = region;
    }

    @NotBlank(message = "电话不能为空")
    @Size(min = 0, max = 20, message = "电话长度不能超过20个字符")
    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public Integer getContactType()
    {
        return contactType;
    }

    public void setContactType(Integer contactType)
    {
        this.contactType = contactType;
    }

    public Integer getSortWeight()
    {
        return sortWeight;
    }

    public void setSortWeight(Integer sortWeight)
    {
        this.sortWeight = sortWeight;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("contactId", getContactId())
            .append("contactName", getContactName())
            .append("region", getRegion())
            .append("phone", getPhone())
            .append("contactType", getContactType())
            .append("sortWeight", getSortWeight())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
