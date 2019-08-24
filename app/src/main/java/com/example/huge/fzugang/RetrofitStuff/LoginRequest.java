package com.example.huge.fzugang.RetrofitStuff;

public class LoginRequest{
    private String phone;
    private String password;
    private String token;

    public LoginRequest(String phone,String password){
        this.phone=phone;
        this.password=password;
    }

    public LoginRequest(String token){
        this.token=token;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token=token;
    }
}
