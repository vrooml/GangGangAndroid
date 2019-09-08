package com.example.huge.fzugang.RetrofitStuff;

import java.io.Serializable;

public final class AddCarpoolRequest{
    public String token;
    public String date;
    public String time;
    public String content;
    public String destination;
    public String meetPlace;
    public String price;
    public String numOfPeople;
    public String contact;

    public AddCarpoolRequest(String token,String date,String time,String content,String destination,String meetPlace,String price,String numOfPeople,String contact){
        this.token=token;
        this.date=date;
        this.time=time;
        this.content=content;
        this.destination=destination;
        this.meetPlace=meetPlace;
        this.price=price;
        this.numOfPeople=numOfPeople;
        this.contact=contact;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMeetPlace() {
        return meetPlace;
    }

    public void setMeetPlace(String meetPlace) {
        this.meetPlace = meetPlace;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(String numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    public String getContact(){
        return contact;
    }

    public void setContact(String contact){
        this.contact=contact;
    }
}
