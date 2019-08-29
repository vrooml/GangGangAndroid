package com.example.huge.fzugang.RetrofitStuff;

public class UserInfo{
    private int id;
    private int isAdmin;
    private String username;
    private String password;
    private String gender;
    private String avatar;
    private String phone;
    private String name;
    private String studentId;

    public UserInfo(String username,String gender,String phone){
        this.username=username;
        this.gender=gender;
        this.phone=phone;
    }

    public UserInfo(int id,int isAdmin,String username,String password,String gender,String avatar,String phone,String name,String studentId){
        this.id=id;
        this.isAdmin=isAdmin;
        this.username=username;
        this.password=password;
        this.gender=gender;
        this.avatar=avatar;
        this.phone=phone;
        this.name=name;
        this.studentId=studentId;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
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

    public int getIsAdmin(){
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin){
        this.isAdmin=isAdmin;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getAvatar(){
        return avatar;
    }

    public void setAvatar(String avatar){
        this.avatar=avatar;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getStudentId(){
        return studentId;
    }

    public void setStudentId(String studentId){
        this.studentId=studentId;
    }
}
