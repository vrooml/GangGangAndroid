package com.example.huge.fzugang.RetrofitStuff;

public class LostListRequest{
    private String token;
    private String page;
    private String num;
    private String classify;

    public LostListRequest(String token,String page,String num,String classify){
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

    public String getClassify(){
        return classify;
    }

    public void setClassify(String classify){
        this.classify=classify;
    }
}
