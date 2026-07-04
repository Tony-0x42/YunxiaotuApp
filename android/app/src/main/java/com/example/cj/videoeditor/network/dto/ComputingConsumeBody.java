package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * 算力消耗请求体。
 */
public class ComputingConsumeBody {

    /** 操作类型：1 生成 / 2 下载 */
    @SerializedName("operationType")
    private Integer operationType;

    /** 消耗算力值 */
    @SerializedName("consumeValue")
    private Double consumeValue;

    /** 业务单号/说明 */
    @SerializedName("bizNo")
    private String bizNo;

    public ComputingConsumeBody() {
    }

    public ComputingConsumeBody(Integer operationType, Double consumeValue, String bizNo) {
        this.operationType = operationType;
        this.consumeValue = consumeValue;
        this.bizNo = bizNo;
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

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }
}
