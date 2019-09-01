package com.example.huge.fzugang.Utils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpUtil {
    private String baseUrl;
    private String json;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    public static void sendOkHttpRequeat(String address, String postJson, okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body= RequestBody.create(JSON,postJson);
        Request request=new Request.Builder()
                .url("http://2457sx9279.qicp.vip/"+address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
