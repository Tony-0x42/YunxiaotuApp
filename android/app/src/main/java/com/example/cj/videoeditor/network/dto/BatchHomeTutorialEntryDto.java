package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

public class BatchHomeTutorialEntryDto {

    @SerializedName("entryId")
    private Long entryId;

    @SerializedName("title")
    private String title;

    @SerializedName("coverUrl")
    private String coverUrl;

    @SerializedName("documentId")
    private Long documentId;

    @SerializedName("documentTitle")
    private String documentTitle;

    @SerializedName("sortWeight")
    private Integer sortWeight;

    @SerializedName("status")
    private String status;

    public BatchHomeTutorialEntryDto() {
    }

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
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

    public Integer getSortWeight() {
        return sortWeight;
    }

    public void setSortWeight(Integer sortWeight) {
        this.sortWeight = sortWeight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
