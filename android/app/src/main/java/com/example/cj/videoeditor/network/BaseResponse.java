package com.example.cj.videoeditor.network;

import com.google.gson.annotations.SerializedName;

/**
 * 统一响应封装（对应后端 AjaxResult）
 *
 * 普通接口返回结构：
 * {
 *   "code": 200,
 *   "msg": "操作成功",
 *   "data": { ... },
 *   "token": "..."   // 登录接口特有
 * }
 */
public class BaseResponse<T> {

    @SerializedName("code")
    private int code;

    @SerializedName("msg")
    private String msg;

    @SerializedName("data")
    private T data;

    /**
     * 登录接口返回的 token，与 data 同级。
     */
    @SerializedName("token")
    private String token;

    public BaseResponse() {
    }

    public boolean isSuccess() {
        return code == 200;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
