package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * 后台 /login 登录请求体。
 */
public class LoginBody {

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("code")
    private String code;

    @SerializedName("uuid")
    private String uuid;

    public LoginBody() {
    }

    public LoginBody(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginBody(String username, String password, String code, String uuid) {
        this.username = username;
        this.password = password;
        this.code = code;
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
