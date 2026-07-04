package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * AI 云创分镜头 DTO（对应后端 BatchAiVideoClip）。
 */
public class BatchAiVideoClipDto {

    @SerializedName("clipId")
    private Long clipId;

    @SerializedName("groupId")
    private Long groupId;

    @SerializedName("videoUrl")
    private String videoUrl;

    @SerializedName("textContent")
    private String textContent;

    @SerializedName("duration")
    private Double duration;

    @SerializedName("sortOrder")
    private Integer sortOrder;

    @SerializedName("createTime")
    private String createTime;

    public BatchAiVideoClipDto() {
    }

    public Long getClipId() {
        return clipId;
    }

    public void setClipId(Long clipId) {
        this.clipId = clipId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
