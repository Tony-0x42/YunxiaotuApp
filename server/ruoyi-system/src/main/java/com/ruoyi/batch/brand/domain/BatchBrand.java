package com.ruoyi.batch.brand.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 品牌专区对象 batch_brand
 *
 * @author ruoyi
 */
public class BatchBrand extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 品牌ID */
    private Long brandId;

    /** 品牌名称 */
    @Size(min = 0, max = 100, message = "品牌名称长度不能超过100个字符")
    private String brandName;

    /** 品牌LOGO */
    @Size(min = 0, max = 500, message = "LOGO URL长度不能超过500个字符")
    private String logoUrl;

    /** 品牌简介 */
    @Size(min = 0, max = 500, message = "品牌简介长度不能超过500个字符")
    private String intro;

    /** 品牌详细介绍 */
    private String detail;

    /** 品牌宣传图/视频，逗号分隔 */
    private String mediaUrls;

    /** 排序权重 */
    private Integer sortWeight;

    /** 状态：0 启用 / 1 禁用 */
    private Integer status;

    /** 删除标志：0 存在 / 2 删除 */
    private Integer delFlag;

    public Long getBrandId()
    {
        return brandId;
    }

    public void setBrandId(Long brandId)
    {
        this.brandId = brandId;
    }

    @NotBlank(message = "品牌名称不能为空")
    public String getBrandName()
    {
        return brandName;
    }

    public void setBrandName(String brandName)
    {
        this.brandName = brandName;
    }

    public String getLogoUrl()
    {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl)
    {
        this.logoUrl = logoUrl;
    }

    public String getIntro()
    {
        return intro;
    }

    public void setIntro(String intro)
    {
        this.intro = intro;
    }

    public String getDetail()
    {
        return detail;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    public String getMediaUrls()
    {
        return mediaUrls;
    }

    public void setMediaUrls(String mediaUrls)
    {
        this.mediaUrls = mediaUrls;
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
            .append("brandId", getBrandId())
            .append("brandName", getBrandName())
            .append("logoUrl", getLogoUrl())
            .append("intro", getIntro())
            .append("detail", getDetail())
            .append("mediaUrls", getMediaUrls())
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
