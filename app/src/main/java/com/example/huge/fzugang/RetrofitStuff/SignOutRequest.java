package com.example.huge.fzugang.RetrofitStuff;

public class SignOutRequest{
    private String username;

    public SignOutRequest(String username){
        this.username=username;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username=username;
    }
}
