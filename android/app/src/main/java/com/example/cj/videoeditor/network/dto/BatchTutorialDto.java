package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

public class BatchTutorialDto {

    @SerializedName("tutorialId")
    private Long tutorialId;

    @SerializedName("tutorialTitle")
    private String tutorialTitle;

    @SerializedName("tutorialType")
    private String tutorialType;

    @SerializedName("categoryId")
    private Long categoryId;

    @SerializedName("categoryName")
    private String categoryName;

    @SerializedName("coverUrl")
    private String coverUrl;

    @SerializedName("videoUrl")
    private String videoUrl;

    @SerializedName("documentContent")
    private String documentContent;

    @SerializedName("intro")
    private String intro;

    @SerializedName("sortWeight")
    private Integer sortWeight;

    @SerializedName("viewCount")
    private Integer viewCount;

    @SerializedName("status")
    private String status;

    @SerializedName("updateTime")
    private String updateTime;

    public BatchTutorialDto() {
    }

    public Long getTutorialId() {
        return tutorialId;
    }

    public void setTutorialId(Long tutorialId) {
        this.tutorialId = tutorialId;
    }

    public String getTutorialTitle() {
        return tutorialTitle;
    }

    public void setTutorialTitle(String tutorialTitle) {
        this.tutorialTitle = tutorialTitle;
    }

    public String getTutorialType() {
        return tutorialType;
    }

    public void setTutorialType(String tutorialType) {
        this.tutorialType = tutorialType;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDocumentContent() {
        return documentContent;
    }

    public void setDocumentContent(String documentContent) {
        this.documentContent = documentContent;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getSortWeight() {
        return sortWeight;
    }

    public void setSortWeight(Integer sortWeight) {
        this.sortWeight = sortWeight;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
