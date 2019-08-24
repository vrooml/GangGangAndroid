package com.example.huge.fzugang.RetrofitStuff;

/**
 *      通用的请求返回
 * @param <T>
 */
public class ResponseModel<T> {

    private int code;
    private T data;
    private String msg;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
