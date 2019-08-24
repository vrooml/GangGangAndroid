package com.example.huge.fzugang.TradeBlock;

import java.util.List;

public class TradeInfo{
    private String postId;//帖子id
    private String userId;//发布者id
    private String postTime;//发布时间
    private String username;//发布者昵称
    private String title;//标题
    private String content;//详情
    private String price;//价格
    private List<String> pictureUrls;//宝贝图片url
    private String fineness;//成色
    private String contact;//联系方式

    public TradeInfo(String postId,String userId,String postTime,String username,String title,String content,String price,List<String> pictureUrls,String fineness,String contact){
        this.postId=postId;
        this.userId=userId;
        this.postTime=postTime;
        this.username=username;
        this.title=title;
        this.content=content;
        this.price=price;
        this.pictureUrls=pictureUrls;
        this.fineness=fineness;
        this.contact=contact;
    }

    public String getPostId(){
        return postId;
    }

    public void setPostId(String postId){
        this.postId=postId;
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

    public List<String> getPictureUrls(){
        return pictureUrls;
    }

    public void setPictureUrls(List<String> pictureUrls){
        this.pictureUrls=pictureUrls;
    }

    public String getFineness(){
        return fineness;
    }

    public void setFineness(String fineness){
        this.fineness=fineness;
    }

    public String getContact(){
        return contact;
    }

    public void setContact(String contact){
        this.contact=contact;
    }

    public String getPostTime(){
        return postTime;
    }

    public void setPostTime(String postTime){
        this.postTime=postTime;
    }

    public String getPrice(){
        return price;
    }

    public void setPrice(String price){
        this.price=price;
    }
}
