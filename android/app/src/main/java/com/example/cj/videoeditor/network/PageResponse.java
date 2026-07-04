package com.example.cj.videoeditor.network;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

/**
 * 分页列表响应封装（对应后端 TableDataInfo）
 *
 * 列表接口返回结构：
 * {
 *   "code": 200,
 *   "msg": "查询成功",
 *   "total": 100,
 *   "rows": [ ... ]
 * }
 */
public class PageResponse<T> {

    @SerializedName("code")
    private int code;

    @SerializedName("msg")
    private String msg;

    @SerializedName("total")
    private long total;

    @SerializedName("rows")
    private List<T> rows;

    public PageResponse() {
    }

    public boolean isSuccess() {
        return code == 200;
    }

    public List<T> getSafeRows() {
        return rows != null ? rows : Collections.emptyList();
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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
