package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * 提交 AI 视频生成返回的 data 对象。
 */
public class BatchAiVideoGenerateResultDto {

    @SerializedName("logId")
    private Long logId;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }
}
