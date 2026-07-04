package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * 客户 / APP 账号 DTO。
 */
public class BatchCustomerDto {

    @SerializedName("customerId")
    private Long customerId;

    @SerializedName("customerType")
    private Integer customerType;

    @SerializedName("customerName")
    private String customerName;

    @SerializedName("contactName")
    private String contactName;

    @SerializedName("phone")
    private String phone;

    @SerializedName("parentPhone")
    private String parentPhone;

    @SerializedName("branchPhone")
    private String branchPhone;

    @SerializedName("maxServiceProvider")
    private Integer maxServiceProvider;

    @SerializedName("totalIndividualCapacity")
    private Integer totalIndividualCapacity;

    @SerializedName("maxIndividual")
    private Integer maxIndividual;

    @SerializedName("computingPowerTotal")
    private Double computingPowerTotal;

    @SerializedName("computingPowerUsed")
    private Double computingPowerUsed;

    @SerializedName("computingPowerRemain")
    private Double computingPowerRemain;

    @SerializedName("vipExpireDate")
    private String vipExpireDate;

    @SerializedName("qrCodeUrl")
    private String qrCodeUrl;

    @SerializedName("qrCodeKey")
    private String qrCodeKey;

    @SerializedName("avatarUrl")
    private String avatarUrl;

    @SerializedName("status")
    private Integer status;

    @SerializedName("subordinateCount")
    private Integer subordinateCount;

    @SerializedName("usedSubordinateCount")
    private Integer usedSubordinateCount;

    @SerializedName("vipStatus")
    private Integer vipStatus;

    @SerializedName("createTime")
    private String createTime;

    @SerializedName("updateTime")
    private String updateTime;

    public BatchCustomerDto() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public String getBranchPhone() {
        return branchPhone;
    }

    public void setBranchPhone(String branchPhone) {
        this.branchPhone = branchPhone;
    }

    public Integer getMaxServiceProvider() {
        return maxServiceProvider;
    }

    public void setMaxServiceProvider(Integer maxServiceProvider) {
        this.maxServiceProvider = maxServiceProvider;
    }

    public Integer getTotalIndividualCapacity() {
        return totalIndividualCapacity;
    }

    public void setTotalIndividualCapacity(Integer totalIndividualCapacity) {
        this.totalIndividualCapacity = totalIndividualCapacity;
    }

    public Integer getMaxIndividual() {
        return maxIndividual;
    }

    public void setMaxIndividual(Integer maxIndividual) {
        this.maxIndividual = maxIndividual;
    }

    public Double getComputingPowerTotal() {
        return computingPowerTotal;
    }

    public void setComputingPowerTotal(Double computingPowerTotal) {
        this.computingPowerTotal = computingPowerTotal;
    }

    public Double getComputingPowerUsed() {
        return computingPowerUsed;
    }

    public void setComputingPowerUsed(Double computingPowerUsed) {
        this.computingPowerUsed = computingPowerUsed;
    }

    public Double getComputingPowerRemain() {
        return computingPowerRemain;
    }

    public void setComputingPowerRemain(Double computingPowerRemain) {
        this.computingPowerRemain = computingPowerRemain;
    }

    public String getVipExpireDate() {
        return vipExpireDate;
    }

    public void setVipExpireDate(String vipExpireDate) {
        this.vipExpireDate = vipExpireDate;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getQrCodeKey() {
        return qrCodeKey;
    }

    public void setQrCodeKey(String qrCodeKey) {
        this.qrCodeKey = qrCodeKey;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSubordinateCount() {
        return subordinateCount;
    }

    public void setSubordinateCount(Integer subordinateCount) {
        this.subordinateCount = subordinateCount;
    }

    public Integer getUsedSubordinateCount() {
        return usedSubordinateCount;
    }

    public void setUsedSubordinateCount(Integer usedSubordinateCount) {
        this.usedSubordinateCount = usedSubordinateCount;
    }

    public Integer getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(Integer vipStatus) {
        this.vipStatus = vipStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
