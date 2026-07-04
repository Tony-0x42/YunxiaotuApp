package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BatchVipUpdateDto {

    @SerializedName("customerIds")
    private List<Long> customerIds;

    @SerializedName("vipExpireDate")
    private String vipExpireDate;

    public BatchVipUpdateDto() {
    }

    public List<Long> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(List<Long> customerIds) {
        this.customerIds = customerIds;
    }

    public String getVipExpireDate() {
        return vipExpireDate;
    }

    public void setVipExpireDate(String vipExpireDate) {
        this.vipExpireDate = vipExpireDate;
    }
}
