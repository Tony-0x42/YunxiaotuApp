package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

public class BatchDocumentDto {

    @SerializedName("documentId")
    private Long documentId;

    @SerializedName("documentTitle")
    private String documentTitle;

    @SerializedName("documentType")
    private Integer documentType;

    @SerializedName("applyPages")
    private String applyPages;

    @SerializedName("category")
    private String category;

    @SerializedName("content")
    private String content;

    @SerializedName("sortWeight")
    private Integer sortWeight;

    @SerializedName("status")
    private Integer status;

    @SerializedName("isSystem")
    private Integer isSystem;

    @SerializedName("updateTime")
    private String updateTime;

    public BatchDocumentDto() {
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public Integer getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Integer documentType) {
        this.documentType = documentType;
    }

    public String getApplyPages() {
        return applyPages;
    }

    public void setApplyPages(String applyPages) {
        this.applyPages = applyPages;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSortWeight() {
        return sortWeight;
    }

    public void setSortWeight(Integer sortWeight) {
        this.sortWeight = sortWeight;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Integer isSystem) {
        this.isSystem = isSystem;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
