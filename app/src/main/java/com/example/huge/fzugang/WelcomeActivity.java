package com.example.huge.fzugang;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.example.huge.fzugang.Utils.RetrofitUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;

public class WelcomeActivity extends AppCompatActivity{

    private static final String TAG="debug";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        requestReadExternalPermission();

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


    @SuppressLint("NewApi")
    private void requestReadExternalPermission(){
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            Log.d(TAG,"READ permission IS NOT granted...");
            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
                Log.d(TAG,"11111111111111");
            }else{
                // 0 是自己定义的请求coude
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
                Log.d(TAG,"222222222222");
            }
        }else{
            init();
            Log.d(TAG,"READ permission is granted...");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults){
        switch(requestCode){
            case 0:{
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    init();
                    // permission was granted
                    // request successfully, handle you transactions
                }else{
                    System.exit(0);
                    // permission denied
                    // request failed
                }
                return;
            }
            default:
                break;
        }
    }
}
