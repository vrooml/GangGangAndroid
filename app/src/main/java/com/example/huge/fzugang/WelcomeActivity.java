package com.example.huge.fzugang;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;

public class WelcomeActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //根据hasLogin判断之前是否登录过决定下一启动活动
        if(SharedPreferencesUtil.getStoredMessage(WelcomeActivity.this,"loginStatus").equals("false")){
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WelcomeActivity.this.finish();
                    Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    WelcomeActivity.this.startActivity(intent);
                }
            });
        }
        else {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run(){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WelcomeActivity.this.finish();
                    Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    WelcomeActivity.this.startActivity(intent);

                }
            });
        }

    }
}
