package com.ruoyi.batch.aivideo.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * AI 云创分镜头对象 batch_ai_video_clip
 *
 * @author ruoyi
 */
public class BatchAiVideoClip
{
    private static final long serialVersionUID = 1L;

    /** 分镜头ID */
    private Long clipId;

    /** 所属视频组ID */
    private Long groupId;

    /** 视频/图片素材 URL */
    private String videoUrl;

    /** 口播文案 */
    private String textContent;

    /** 时长（秒） */
    private Double duration;

    /** 排序 */
    private Integer sortOrder;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Long getClipId()
    {
        return clipId;
    }

    public void setClipId(Long clipId)
    {
        this.clipId = clipId;
    }

    public Long getGroupId()
    {
        return groupId;
    }

    public void setGroupId(Long groupId)
    {
        this.groupId = groupId;
    }

    public String getVideoUrl()
    {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl)
    {
        this.videoUrl = videoUrl;
    }

    public String getTextContent()
    {
        return textContent;
    }

    public void setTextContent(String textContent)
    {
        this.textContent = textContent;
    }

    public Double getDuration()
    {
        return duration;
    }

    public void setDuration(Double duration)
    {
        this.duration = duration;
    }

    public Integer getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("clipId", getClipId())
            .append("groupId", getGroupId())
            .append("videoUrl", getVideoUrl())
            .append("textContent", getTextContent())
            .append("duration", getDuration())
            .append("sortOrder", getSortOrder())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
