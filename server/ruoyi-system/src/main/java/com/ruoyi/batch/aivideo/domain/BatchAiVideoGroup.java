package com.ruoyi.batch.aivideo.domain;

import java.util.Date;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * AI 云创视频组对象 batch_ai_video_group
 *
 * @author ruoyi
 */
public class BatchAiVideoGroup
{
    private static final long serialVersionUID = 1L;

    /** 视频组ID */
    private Long groupId;

    /** 所属账号手机号 */
    private String phone;

    /** 视频组名称 */
    @NotBlank(message = "视频组名称不能为空")
    private String groupName;

    /** 已生成次数 */
    private Integer generatedCount;

    /** 最大可生成次数 */
    private Integer maxLimit;

    /** 状态：0 启用 / 1 禁用 */
    private Integer status;

    /** 删除标志：0 存在 / 2 删除 */
    private Integer delFlag;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 分镜头列表（非持久化字段） */
    private List<BatchAiVideoClip> clips;

    public Long getGroupId()
    {
        return groupId;
    }

    public void setGroupId(Long groupId)
    {
        this.groupId = groupId;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public Integer getGeneratedCount()
    {
        return generatedCount;
    }

    public void setGeneratedCount(Integer generatedCount)
    {
        this.generatedCount = generatedCount;
    }

    public Integer getMaxLimit()
    {
        return maxLimit;
    }

    public void setMaxLimit(Integer maxLimit)
    {
        this.maxLimit = maxLimit;
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

    public List<BatchAiVideoClip> getClips()
    {
        return clips;
    }

    public void setClips(List<BatchAiVideoClip> clips)
    {
        this.clips = clips;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("groupId", getGroupId())
            .append("phone", getPhone())
            .append("groupName", getGroupName())
            .append("generatedCount", getGeneratedCount())
            .append("maxLimit", getMaxLimit())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
