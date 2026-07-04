package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * APP 联系方式 DTO。
 */
public class BatchContactDto {

    @SerializedName("contactId")
    private Long contactId;

    @SerializedName("contactName")
    private String contactName;

    @SerializedName("region")
    private String region;

    @SerializedName("phone")
    private String phone;

    @SerializedName("contactType")
    private Integer contactType;

    @SerializedName("sortWeight")
    private Integer sortWeight;

    @SerializedName("status")
    private Integer status;

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getContactType() {
        return contactType;
    }

    public void setContactType(Integer contactType) {
        this.contactType = contactType;
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
}
