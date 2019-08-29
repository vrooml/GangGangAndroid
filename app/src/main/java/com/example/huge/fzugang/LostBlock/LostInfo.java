package com.example.huge.fzugang.LostBlock;

import java.util.List;

public class LostInfo{
    private String id;//帖子id
    private String userId;//发布者id
    private String postTime;//发布时间
    private String username;//发布者昵称
    private String title;//标题
    private String content;//详情
    private int classify;//0=丢失 1=捡到
    private String lostTime;//拾取/丢失时间
    private String lostPlace;//拾取/丢失地点
    private List<String> pictureUrls;//物品图片url
    private String contact;//联系方式

    public LostInfo(String id,String userId,String postTime,String username,String title,String content,int classify,String lostTime,String lostPlace,List<String> pictureUrls,String contact){
        this.id=id;
        this.userId=userId;
        this.postTime=postTime;
        this.username=username;
        this.title=title;
        this.content=content;
        this.classify=classify;
        this.lostTime=lostTime;
        this.lostPlace=lostPlace;
        this.pictureUrls=pictureUrls;
        this.contact=contact;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId=userId;
    }

    public String getPostTime(){
        return postTime;
    }

    public void setPostTime(String postTime){
        this.postTime=postTime;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username=username;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content=content;
    }

    public String getLostTime(){
        return lostTime;
    }

    public void setLostTime(String lostTime){
        this.lostTime=lostTime;
    }

    public List<String> getPictureUrls(){
        return pictureUrls;
    }

    public void setPictureUrls(List<String> pictureUrls){
        this.pictureUrls=pictureUrls;
    }

    public String getLostPlace(){
        return lostPlace;
    }

    public void setLostPlace(String lostPlace){
        this.lostPlace=lostPlace;
    }

    public String getContact(){
        return contact;
    }

    public void setContact(String contact){
        this.contact=contact;
    }

    public int getClassify(){
        return classify;
    }

    public void setClassify(int classify){
        this.classify=classify;
    }
}
