package com.example.huge.fzugang;

import android.app.Application;
import android.content.Context;

//便于获取全局context
public class MyApplication extends Application{
    private static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
