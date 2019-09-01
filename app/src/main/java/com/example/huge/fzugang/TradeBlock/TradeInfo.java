package com.example.huge.fzugang.TradeBlock;

import java.util.List;

public class TradeInfo{
    private String id;//帖子id
    private String userId;//发布者id
    private String postDate;//发布时间
    private String postTime;//发布时间
    private String dateTime;//龚海旭憨憨数据
    private String username;//发布者昵称
    private String title;//标题
    private String content;//详情
    private String price;//价格
    private List<String> pictureUrls;//宝贝图片url
    private String fineness;//成色
    private String contact;//联系方式
    private int classify;//类别（暂时没用）

    public TradeInfo(String id,String userId,String postDate,String postTime,String dateTime,String username,String title,String content,String price,List<String> pictureUrls,String fineness,String contact,int classify){
        this.id=id;
        this.userId=userId;
        this.postDate=postDate;
        this.postTime=postTime;
        this.dateTime=dateTime;
        this.username=username;
        this.title=title;
        this.content=content;
        this.price=price;
        this.pictureUrls=pictureUrls;
        this.fineness=fineness;
        this.contact=contact;
        this.classify=classify;
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

    public String getPostDate(){
        return postDate;
    }

    public void setPostDate(String postDate){
        this.postDate=postDate;
    }

    public String getPrice(){
        return price;
    }

    public void setPrice(String price){
        this.price=price;
    }

    public int getClassify(){
        return classify;
    }

    public void setClassify(int classify){
        this.classify=classify;
    }

    public String getPostTime(){
        return postTime;
    }

    public void setPostTime(String postTime){
        this.postTime=postTime;
    }
}
