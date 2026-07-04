package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * APP 端 /batch/app/register 注册请求体。
 */
public class AppRegisterBody {

    @SerializedName("phone")
    private String phone;

    @SerializedName("password")
    private String password;

    @SerializedName("parentPhone")
    private String parentPhone;

    public AppRegisterBody() {
    }

    public AppRegisterBody(String phone, String password, String parentPhone) {
        this.phone = phone;
        this.password = password;
        this.parentPhone = parentPhone;
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

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }
}
