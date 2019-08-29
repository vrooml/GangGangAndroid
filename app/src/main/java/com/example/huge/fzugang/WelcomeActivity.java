package com.example.huge.fzugang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.example.huge.fzugang.Utils.RetrofitUtil;
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
        if(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token")==null||
                SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token").equals("false")){
            new Thread(new Runnable(){
                @Override
                public void run(){
                    try{
                        Thread.sleep(2000);
                    }catch(InterruptedException e){
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
            new Thread(new Runnable(){
                @Override
                public void run(){
                    try{
                        Thread.sleep(2000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            RetrofitUtil.postUserInfo(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"));
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
