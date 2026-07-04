package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

public class BatchStatisticsOverviewDto {

    @SerializedName("branchTotal")
    private Long branchTotal;

    @SerializedName("providerTotal")
    private Long providerTotal;

    @SerializedName("individualTotal")
    private Long individualTotal;

    @SerializedName("accountTotal")
    private Long accountTotal;

    @SerializedName("accountTodayNew")
    private Long accountTodayNew;

    @SerializedName("computingTodayConsume")
    private Double computingTodayConsume;

    @SerializedName("videoTodayGenerate")
    private Long videoTodayGenerate;

    @SerializedName("qrScanToday")
    private Long qrScanToday;

    @SerializedName("qrDownloadToday")
    private Long qrDownloadToday;

    @SerializedName("qrRegisterToday")
    private Long qrRegisterToday;

    @SerializedName("newsSalesAmount")
    private Double newsSalesAmount;

    @SerializedName("branchServiceProviderCount")
    private Long branchServiceProviderCount;

    @SerializedName("branchIndividualCount")
    private Long branchIndividualCount;

    @SerializedName("branchServiceProviderRemain")
    private Long branchServiceProviderRemain;

    @SerializedName("branchMaxServiceProviderRemain")
    private Long branchMaxServiceProviderRemain;

    public BatchStatisticsOverviewDto() {
    }

    public Long getBranchTotal() {
        return branchTotal;
    }

    public void setBranchTotal(Long branchTotal) {
        this.branchTotal = branchTotal;
    }

    public Long getProviderTotal() {
        return providerTotal;
    }

    public void setProviderTotal(Long providerTotal) {
        this.providerTotal = providerTotal;
    }

    public Long getIndividualTotal() {
        return individualTotal;
    }

    public void setIndividualTotal(Long individualTotal) {
        this.individualTotal = individualTotal;
    }

    public Long getAccountTotal() {
        return accountTotal;
    }

    public void setAccountTotal(Long accountTotal) {
        this.accountTotal = accountTotal;
    }

    public Long getAccountTodayNew() {
        return accountTodayNew;
    }

    public void setAccountTodayNew(Long accountTodayNew) {
        this.accountTodayNew = accountTodayNew;
    }

    public Double getComputingTodayConsume() {
        return computingTodayConsume;
    }

    public void setComputingTodayConsume(Double computingTodayConsume) {
        this.computingTodayConsume = computingTodayConsume;
    }

    public Long getVideoTodayGenerate() {
        return videoTodayGenerate;
    }

    public void setVideoTodayGenerate(Long videoTodayGenerate) {
        this.videoTodayGenerate = videoTodayGenerate;
    }

    public Long getQrScanToday() {
        return qrScanToday;
    }

    public void setQrScanToday(Long qrScanToday) {
        this.qrScanToday = qrScanToday;
    }

    public Long getQrDownloadToday() {
        return qrDownloadToday;
    }

    public void setQrDownloadToday(Long qrDownloadToday) {
        this.qrDownloadToday = qrDownloadToday;
    }

    public Long getQrRegisterToday() {
        return qrRegisterToday;
    }

    public void setQrRegisterToday(Long qrRegisterToday) {
        this.qrRegisterToday = qrRegisterToday;
    }

    public Double getNewsSalesAmount() {
        return newsSalesAmount;
    }

    public void setNewsSalesAmount(Double newsSalesAmount) {
        this.newsSalesAmount = newsSalesAmount;
    }

    public Long getBranchServiceProviderCount() {
        return branchServiceProviderCount;
    }

    public void setBranchServiceProviderCount(Long branchServiceProviderCount) {
        this.branchServiceProviderCount = branchServiceProviderCount;
    }

    public Long getBranchIndividualCount() {
        return branchIndividualCount;
    }

    public void setBranchIndividualCount(Long branchIndividualCount) {
        this.branchIndividualCount = branchIndividualCount;
    }

    public Long getBranchServiceProviderRemain() {
        return branchServiceProviderRemain;
    }

    public void setBranchServiceProviderRemain(Long branchServiceProviderRemain) {
        this.branchServiceProviderRemain = branchServiceProviderRemain;
    }

    public Long getBranchMaxServiceProviderRemain() {
        return branchMaxServiceProviderRemain;
    }

    public void setBranchMaxServiceProviderRemain(Long branchMaxServiceProviderRemain) {
        this.branchMaxServiceProviderRemain = branchMaxServiceProviderRemain;
    }
}
