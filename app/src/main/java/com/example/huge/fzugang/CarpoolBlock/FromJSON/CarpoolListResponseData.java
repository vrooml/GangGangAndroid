package com.example.huge.fzugang.CarpoolBlock.FromJSON;

import java.util.List;

public class CarpoolListResponseData {
    private int code;
    private String msg;
    private List<CarpoolListData> data;

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

    public List<CarpoolListData> getData() {
        return data;
    }

    public void setData(List<CarpoolListData> data) {
        this.data = data;
    }
}
