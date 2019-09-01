package com.example.huge.fzugang.RetrofitStuff;

public class DeletePostRequest{
    private String postId;
    private String token;

    public DeletePostRequest(String postId,String token){
        this.postId=postId;
        this.token=token;
    }

    public String getPostId(){
        return postId;
    }

    public void setPostId(String postId){
        this.postId=postId;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token=token;
    }
}
