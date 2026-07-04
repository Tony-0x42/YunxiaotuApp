package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * 后台 /login 登录成功后的 data 字段。
 */
public class LoginData {

    @SerializedName("token")
    private String token;

    public LoginData() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
