package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * AI 去水印解析结果 DTO。
 */
public class WatermarkParseDto {

    @SerializedName("parseId")
    private Long parseId;

    @SerializedName("phone")
    private String phone;

    @SerializedName("sourceLink")
    private String sourceLink;

    @SerializedName("platform")
    private String platform;

    @SerializedName("videoUrl")
    private String videoUrl;

    @SerializedName("imageUrls")
    private String imageUrls;

    @SerializedName("videoText")
    private String videoText;

    @SerializedName("parseStatus")
    private Integer parseStatus;

    @SerializedName("failReason")
    private String failReason;

    @SerializedName("createTime")
    private String createTime;

    public Long getParseId() {
        return parseId;
    }

    public void setParseId(Long parseId) {
        this.parseId = parseId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getVideoText() {
        return videoText;
    }

    public void setVideoText(String videoText) {
        this.videoText = videoText;
    }

    public Integer getParseStatus() {
        return parseStatus;
    }

    public void setParseStatus(Integer parseStatus) {
        this.parseStatus = parseStatus;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 将逗号分隔的图片 URL 字符串转换为列表。
     */
    public List<String> getImageList() {
        if (imageUrls == null || imageUrls.isEmpty()) {
            return Collections.emptyList();
        }
        String[] arr = imageUrls.split(",");
        List<String> list = new ArrayList<>();
        for (String url : arr) {
            String trim = url.trim();
            if (!trim.isEmpty()) {
                list.add(trim);
            }
        }
        return list;
    }
}
