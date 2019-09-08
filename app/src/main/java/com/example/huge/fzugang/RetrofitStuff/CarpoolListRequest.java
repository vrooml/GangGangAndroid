package com.example.huge.fzugang.RetrofitStuff;

public class CarpoolListRequest{
    private String token;
    private String page;
    private String num;

    public CarpoolListRequest(String token,String page,String num){
        this.token=token;
        this.page=page;
        this.num=num;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
