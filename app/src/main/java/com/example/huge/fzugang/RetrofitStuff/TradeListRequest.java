package com.example.huge.fzugang.RetrofitStuff;

public class TradeListRequest{
    private String token;
    private int page;
    private int num;

    public TradeListRequest(String token,int page,int num){
        this.token=token;
        this.page=page;
        this.num=num;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token=token;
    }

    public int getPage(){
        return page;
    }

    public void setPage(int page){
        this.page=page;
    }

    public int getNum(){
        return num;
    }

    public void setNum(int num){
        this.num=num;
    }
}
