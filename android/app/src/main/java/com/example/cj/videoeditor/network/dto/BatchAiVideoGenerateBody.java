package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * AI 视频生成请求体（对应后端 BatchAiVideoGenerateBody）。
 */
public class BatchAiVideoGenerateBody {

    @SerializedName("groupId")
    private Long groupId;

    @SerializedName("consumeValue")
    private Integer consumeValue;

    @SerializedName("clips")
    private List<BatchAiVideoClipDto> clips;

    public BatchAiVideoGenerateBody() {
    }

    public BatchAiVideoGenerateBody(Long groupId, Integer consumeValue, List<BatchAiVideoClipDto> clips) {
        this.groupId = groupId;
        this.consumeValue = consumeValue;
        this.clips = clips;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Integer getConsumeValue() {
        return consumeValue;
    }

    public void setConsumeValue(Integer consumeValue) {
        this.consumeValue = consumeValue;
    }

    public List<BatchAiVideoClipDto> getClips() {
        return clips;
    }

    public void setClips(List<BatchAiVideoClipDto> clips) {
        this.clips = clips;
    }
}
