package com.example.huge.fzugang.RetrofitStuff;

public class LostListRequest{
    private String token;
    private String page;
    private String num;
    private int classify;

    public LostListRequest(String token,String page,String num,int classify){
        this.token=token;
        this.page=page;
        this.num=num;
        this.classify=classify;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token=token;
    }

    public String getPage(){
        return page;
    }

    public void setPage(String page){
        this.page=page;
    }

    public String getNum(){
        return num;
    }

    public void setNum(String num){
        this.num=num;
    }

    public int getClassify(){
        return classify;
    }

    public void setClassify(int classify){
        this.classify=classify;
    }
}
