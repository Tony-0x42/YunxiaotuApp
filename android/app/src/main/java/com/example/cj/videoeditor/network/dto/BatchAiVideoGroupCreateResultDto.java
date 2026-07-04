package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * 新增视频组返回的 data 对象。
 */
public class BatchAiVideoGroupCreateResultDto {

    @SerializedName("groupId")
    private Long groupId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
