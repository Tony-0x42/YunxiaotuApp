package com.ruoyi.batch.aivideo.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * AI 视频生成记录对象（对应 batch_video_generate_log 表）
 */
public class BatchAiVideoGenerateLog
{
    private Long logId;

    private String phone;

    private String videoGroupName;

    private Integer generateCount;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getLogId()
    {
        return logId;
    }

    public void setLogId(Long logId)
    {
        this.logId = logId;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getVideoGroupName()
    {
        return videoGroupName;
    }

    public void setVideoGroupName(String videoGroupName)
    {
        this.videoGroupName = videoGroupName;
    }

    public Integer getGenerateCount()
    {
        return generateCount;
    }

    public void setGenerateCount(Integer generateCount)
    {
        this.generateCount = generateCount;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
}
