package com.example.huge.fzugang.RetrofitStuff;

public class ForgetPasswordRequest{
    private String token;
    private String password;
    private String vercode;
    private String phone;

    public ForgetPasswordRequest(String token,String password,String vercode,String phone){
        this.token=token;
        this.password=password;
        this.vercode=vercode;
        this.phone=phone;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token=token;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getVercode(){
        return vercode;
    }

    public void setVercode(String vercode){
        this.vercode=vercode;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }
}
