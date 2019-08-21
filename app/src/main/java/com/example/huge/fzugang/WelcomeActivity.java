package com.example.huge.fzugang;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;

public class WelcomeActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();
    }

    private void init(){
        //判断之前是否登录过决定下一页面
        if(SharedPreferencesUtil.getStoredMessage(WelcomeActivity.this,"token")==null||
                SharedPreferencesUtil.getStoredMessage(WelcomeActivity.this,"token").equals("false")){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            WelcomeActivity.this.finish();
                        }
                    });
                }
            }).start();


        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            WelcomeActivity.this.finish();
                        }
                    });
                }
            }).start();

        }
    }
}
