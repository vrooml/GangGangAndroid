package com.example.huge.fzugang.CarpoolBlock;

import java.io.Serializable;

public class CarpoolInfo{
    private int id;
    private int userId;
    private String content;
    private String dateTime;
    private String postDate;
    private String postTime;
    private String author;
    private String date;
    private String time;
    private String destination;
    private String meetPlace;
    private String price;
    private int numOfPeople;
    private String contact;

    public CarpoolInfo(int id,int userId,String content,String dateTime,String postDate,String postTime,String author,String date,String time,String destination,String meetPlace,String price,int numOfPeople,String contact){
        this.id=id;
        this.userId=userId;
        this.content=content;
        this.dateTime=dateTime;
        this.postDate=postDate;
        this.postTime=postTime;
        this.author=author;
        this.date=date;
        this.time=time;
        this.destination=destination;
        this.meetPlace=meetPlace;
        this.price=price;
        this.numOfPeople=numOfPeople;
        this.contact=contact;
    }

    public String getContact(){
        return contact;
    }

    public void setContact(String contact){
        this.contact=contact;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId=userId;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content=content;
    }

    public String getPostDate(){
        return postDate;
    }

    public void setPostDate(String postDate){
        this.postDate=postDate;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author=author;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date=date;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time=time;
    }

    public String getDestination(){
        return destination;
    }

    public void setDestination(String destination){
        this.destination=destination;
    }

    public String getMeetPlace(){
        return meetPlace;
    }

    public void setMeetPlace(String meetPlace){
        this.meetPlace=meetPlace;
    }

    public String getPrice(){
        return price;
    }

    public void setPrice(String price){
        this.price=price;
    }

    public int getNumOfPeople(){
        return numOfPeople;
    }

    public void setNumOfPeople(int numOfPeople){
        this.numOfPeople=numOfPeople;
    }

    public String getDateTime(){
        return dateTime;
    }

    public void setDateTime(String dateTime){
        this.dateTime=dateTime;
    }

    public String getPostTime(){
        return postTime;
    }

    public void setPostTime(String postTime){
        this.postTime=postTime;
    }
}
