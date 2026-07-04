package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * APP 端 /batch/app/login 登录请求体。
 */
public class AppLoginBody {

    @SerializedName("phone")
    private String phone;

    @SerializedName("password")
    private String password;

    public AppLoginBody() {
    }

    public AppLoginBody(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
