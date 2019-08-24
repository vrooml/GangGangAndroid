package com.example.huge.fzugang.RetrofitStuff;

import com.example.huge.fzugang.Utils.SharedPreferencesUtil;

public class RegisterRequest{
    private String phone;
    private String vercode;
    private String token;

    public RegisterRequest(String phone,String vercode,String token){
        this.phone=phone;
        this.vercode=vercode;
        this.token=token;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }

    public String getVercode(){
        return vercode;
    }

    public void setVercode(String vercode){
        this.vercode=vercode;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token=token;
    }
}
