package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

public class BatchSystemConfigDto {

    @SerializedName("configId")
    private Long configId;

    @SerializedName("configKey")
    private String configKey;

    @SerializedName("configValue")
    private String configValue;

    @SerializedName("configGroup")
    private String configGroup;

    public BatchSystemConfigDto() {
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getConfigGroup() {
        return configGroup;
    }

    public void setConfigGroup(String configGroup) {
        this.configGroup = configGroup;
    }
}
