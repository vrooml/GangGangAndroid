package com.example.huge.fzugang.RetrofitStuff;

public class UserInfo{
    private String userId;
    private String username;
    private String gender;

    public UserInfo(String userId,String username,String gender){
        this.userId=userId;
        this.username=username;
        this.gender=gender;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId=userId;
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
