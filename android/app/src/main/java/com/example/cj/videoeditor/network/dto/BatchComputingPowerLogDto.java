package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

public class BatchComputingPowerLogDto {

    @SerializedName("id")
    private Long id;

    @SerializedName("phone")
    private String phone;

    @SerializedName("operationType")
    private Integer operationType;

    @SerializedName("consumeValue")
    private Double consumeValue;

    @SerializedName("remainValue")
    private Double remainValue;

    @SerializedName("videoGroupName")
    private String videoGroupName;

    @SerializedName("createTime")
    private String createTime;

    public BatchComputingPowerLogDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Double getConsumeValue() {
        return consumeValue;
    }

    public void setConsumeValue(Double consumeValue) {
        this.consumeValue = consumeValue;
    }

    public Double getRemainValue() {
        return remainValue;
    }

    public void setRemainValue(Double remainValue) {
        this.remainValue = remainValue;
    }

    public String getVideoGroupName() {
        return videoGroupName;
    }

    public void setVideoGroupName(String videoGroupName) {
        this.videoGroupName = videoGroupName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
