package com.example.huge.fzugang.RetrofitStuff;

public class ChangeInfoRequest{
    private String token;
    private String username;
    private String gender;

    public ChangeInfoRequest(String token,String username,String gender){
        this.token=token;
        this.username=username;
        this.gender=gender;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token=token;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username=username;
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        this.gender=gender;
    }
}
