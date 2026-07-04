package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * 算力消耗结果 DTO。
 */
public class ComputingConsumeDto {

    @SerializedName("remain")
    private Double remain;

    public Double getRemain() {
        return remain;
    }

    public void setRemain(Double remain) {
        this.remain = remain;
    }
}
