package com.ruoyi.batch.watermark.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * AI 去水印解析记录对象 batch_watermark_parse
 *
 * @author ruoyi
 */
public class BatchWatermarkParse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 解析记录ID */
    private Long parseId;

    /** 账号手机号 */
    private String phone;

    /** 原始分享链接 */
    private String sourceLink;

    /** 解析平台 */
    private String platform;

    /** 去水印后视频 URL */
    private String videoUrl;

    /** 视频帧图片 URL 列表，JSON 数组 */
    private String imageUrls;

    /** 视频附带文案/标题 */
    private String videoText;

    /** 解析状态：0 待解析 / 1 解析中 / 2 已完成 / 9 失败 */
    private Integer parseStatus;

    /** 失败原因 */
    private String failReason;

    /** 删除标志：0 存在 / 2 删除 */
    private Integer delFlag;

    public Long getParseId()
    {
        return parseId;
    }

    public void setParseId(Long parseId)
    {
        this.parseId = parseId;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getSourceLink()
    {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink)
    {
        this.sourceLink = sourceLink;
    }

    public String getPlatform()
    {
        return platform;
    }

    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    public String getVideoUrl()
    {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl)
    {
        this.videoUrl = videoUrl;
    }

    public String getImageUrls()
    {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls)
    {
        this.imageUrls = imageUrls;
    }

    public String getVideoText()
    {
        return videoText;
    }

    public void setVideoText(String videoText)
    {
        this.videoText = videoText;
    }

    public Integer getParseStatus()
    {
        return parseStatus;
    }

    public void setParseStatus(Integer parseStatus)
    {
        this.parseStatus = parseStatus;
    }

    public String getFailReason()
    {
        return failReason;
    }

    public void setFailReason(String failReason)
    {
        this.failReason = failReason;
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
            .append("parseId", getParseId())
            .append("phone", getPhone())
            .append("sourceLink", getSourceLink())
            .append("platform", getPlatform())
            .append("videoUrl", getVideoUrl())
            .append("imageUrls", getImageUrls())
            .append("videoText", getVideoText())
            .append("parseStatus", getParseStatus())
            .append("failReason", getFailReason())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
