package com.example.huge.fzugang.RetrofitStuff;

public class PostSearchRequest{
    private String token;
    private String page;
    private String key;

    public PostSearchRequest(String token,String page,String key){
        this.token=token;
        this.page=page;
        this.key=key;
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

    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key=key;
    }
}
