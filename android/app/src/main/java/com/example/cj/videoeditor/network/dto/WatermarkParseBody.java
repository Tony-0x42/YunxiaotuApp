package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * AI 去水印解析请求体。
 */
public class WatermarkParseBody {

    @SerializedName("sourceLink")
    private String sourceLink;

    public WatermarkParseBody() {
    }

    public WatermarkParseBody(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }
}
