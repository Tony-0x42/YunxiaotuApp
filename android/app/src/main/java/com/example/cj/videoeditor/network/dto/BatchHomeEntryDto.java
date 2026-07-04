package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

public class BatchHomeEntryDto {

    @SerializedName("entryId")
    private Long entryId;

    @SerializedName("entryName")
    private String entryName;

    @SerializedName("iconUrl")
    private String iconUrl;

    @SerializedName("targetType")
    private String targetType;

    @SerializedName("targetValue")
    private String targetValue;

    @SerializedName("sortWeight")
    private Integer sortWeight;

    @SerializedName("status")
    private String status;

    public BatchHomeEntryDto() {
    }

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
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
