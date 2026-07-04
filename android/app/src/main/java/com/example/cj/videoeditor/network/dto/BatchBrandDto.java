package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * 品牌专区 DTO。
 */
public class BatchBrandDto {

    @SerializedName("brandId")
    private Long brandId;

    @SerializedName("brandName")
    private String brandName;

    @SerializedName("logoUrl")
    private String logoUrl;

    @SerializedName("intro")
    private String intro;

    @SerializedName("detail")
    private String detail;

    @SerializedName("mediaUrls")
    private String mediaUrls;

    @SerializedName("sortWeight")
    private Integer sortWeight;

    @SerializedName("status")
    private Integer status;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMediaUrls() {
        return mediaUrls;
    }

    public void setMediaUrls(String mediaUrls) {
        this.mediaUrls = mediaUrls;
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
