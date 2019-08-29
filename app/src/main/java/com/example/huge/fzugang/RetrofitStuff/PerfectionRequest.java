package com.example.huge.fzugang.RetrofitStuff;

public class PerfectionRequest{
    private String username;
    private String password;
    private String phone;
    private String gender;

    public PerfectionRequest(String username,String password,String phone,String gender){
        this.username=username;
        this.password=password;
        this.phone=phone;
        this.gender=gender;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username=username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        this.gender=gender;
    }
}
