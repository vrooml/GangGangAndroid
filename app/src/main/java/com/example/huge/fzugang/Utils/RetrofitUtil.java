package com.example.huge.fzugang.Utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;
import com.example.huge.fzugang.*;
import com.example.huge.fzugang.LostBlock.LostFragment;
import com.example.huge.fzugang.LostBlock.LostInfo;
import com.example.huge.fzugang.RetrofitStuff.*;
import com.example.huge.fzugang.TradeBlock.TradeFragment;
import com.example.huge.fzugang.TradeBlock.TradeInfo;
import com.zyao89.view.zloading.ZLoadingDialog;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.example.huge.fzugang.Utils.constantUtil.FAILED;
import static com.example.huge.fzugang.Utils.constantUtil.SUCCESS_CODE;

public class RetrofitUtil{

    private static OkHttpClient okHttpClient=new OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            .connectTimeout(10000,TimeUnit.MILLISECONDS)
            .build();
    private static Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("http://2457sx9279.qicp.vip")
            .addConverterFactory(GsonConverterFactory.create())//设置使用Gson解析
            .client(okHttpClient)
            .build();


/*
主界面、登录等界面
 */

    //获取验证码
    public static void postAuthCode(AuthCodeRequest authCodeRequest){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<String>> call=request.postAuthCode(authCodeRequest);
        call.enqueue(new Callback<ResponseModel<String>>(){
            @Override
            public void onResponse(Call<ResponseModel<String>> call,Response<ResponseModel<String>> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        Toast.makeText(MyApplication.getContext(),"验证码发送成功，注意查收",Toast.LENGTH_SHORT).show();
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"authCodeToken",response.body().getData());
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<String>> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //账号登录请求
    public static void postLogin(final Activity startActivity,final LoginRequest loginRequest,final ZLoadingDialog zLoadingDialog){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<String>> call=request.login(loginRequest);
        call.enqueue(new Callback<ResponseModel<String>>(){
            @Override
            public void onResponse(Call<ResponseModel<String>> call,Response<ResponseModel<String>> response){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        RetrofitUtil.postUserInfo(response.body().getData());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"phoneNum",loginRequest.getPhone());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"token",response.body().getData());
                        Intent intent=new Intent(startActivity,MainActivity.class);
                        startActivity.startActivity(intent);
                        startActivity.finish();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<String>> call,Throwable t){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //退出登录请求
    public static void postSignOut(final Activity startActivity,final SignOutRequest signOutRequest){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postSignOut(signOutRequest);
        call.enqueue(new Callback<ResponseModel>(){
            @Override
            public void onResponse(Call<ResponseModel> call,Response<ResponseModel> response){
                if(response.code()==SUCCESS_CODE){
                    Toast.makeText(MyApplication.getContext(),"退出登录成功",Toast.LENGTH_SHORT).show();
                    Intent reloginIntent=new Intent(startActivity,LoginActivity.class);
                    reloginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity.startActivity(reloginIntent);
                    startActivity.finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //请求本用户信息
    public static void postUserInfo(String token){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Log.d("debug","请求信息！！！！！"+token);
        Call<ResponseModel<UserInfo>> call=request.postUserInfo(new UserInfoRequest(token));
        call.enqueue(new Callback<ResponseModel<UserInfo>>(){
            @Override
            public void onResponse(Call<ResponseModel<UserInfo>> call,Response<ResponseModel<UserInfo>> response){
                if(response.body()!=null){
                    Log.d("debug","请求信息没失败哦");
                    if(response.body().getCode()==SUCCESS_CODE){
                        UserInfo userInfo=response.body().getData();
                        Log.d("debug","onResponse: " +userInfo.getId()+userInfo.getUsername());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"username",userInfo.getUsername());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"userId",String.valueOf(userInfo.getId()));
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"gender",userInfo.getGender());
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserInfo>> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //请求指定用户简略信息
    public static UserInfo postUserBriefInfo(String token,String userId){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<UserInfo>> call=request.postUserBriefInfo(new UserInfoRequest(token,userId));
        final UserInfo[] userInfo=new UserInfo[1];
        call.enqueue(new Callback<ResponseModel<UserInfo>>(){
            @Override
            public void onResponse(Call<ResponseModel<UserInfo>> call,Response<ResponseModel<UserInfo>> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        userInfo[0]=response.body().getData();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserInfo>> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
        return userInfo[0];
    }

    //注册账号请求
    public static void postRegister(final Activity startActivity,final RegisterRequest registerRequest,final ZLoadingDialog zLoadingDialog){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postRegister(registerRequest);
        call.enqueue(new Callback<ResponseModel>(){
            @Override
            public void onResponse(Call<ResponseModel> call,Response<ResponseModel> response){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        if(response.body().getData()==null){
                            SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"phoneNum",registerRequest.getPhone());
                            Toast.makeText(MyApplication.getContext(),"注册成功",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(startActivity,PerfectionInfoActivity.class);
                            startActivity.startActivity(intent);
                            startActivity.finish();
                        }else if(response.body().getData().equals("难顶")){
                            Toast.makeText(MyApplication.getContext(),"验证码错误",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call,Throwable t){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //完善信息请求
    public static void postPerfection(final Activity startActivity,final PerfectionRequest perfectionRequest,final ZLoadingDialog zLoadingDialog){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postPerfection(perfectionRequest);
        call.enqueue(new Callback<ResponseModel>(){
            @Override
            public void onResponse(Call<ResponseModel> call,Response<ResponseModel> response){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        RetrofitUtil.postUserInfo(response.body().getData().toString());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"gender",perfectionRequest.getGender());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"token",response.body().getData().toString());
                        Toast.makeText(MyApplication.getContext(),"注册成功",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(startActivity,MainActivity.class);
                        startActivity.startActivity(intent);
                        startActivity.finish();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call,Throwable t){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //修改信息请求
    public static void postChangeInfo(final Activity startActivity,final ChangeInfoRequest changeInfoRequest,final ZLoadingDialog zLoadingDialog){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postChangeInfo(changeInfoRequest);
        call.enqueue(new Callback<ResponseModel>(){
            @Override
            public void onResponse(Call<ResponseModel> call,Response<ResponseModel> response){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        RetrofitUtil.postUserInfo(response.body().getData().toString());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"gender",changeInfoRequest.getGender());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"username",changeInfoRequest.getUsername());
                        Toast.makeText(MyApplication.getContext(),"注册成功",Toast.LENGTH_SHORT).show();
                        startActivity.finish();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call,Throwable t){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //修改密码请求
    public static void postForgetPassWord(final Activity startActivity,ForgetPasswordRequest forgetPasswordRequest,final ZLoadingDialog zLoadingDialog){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postForgetPassword(forgetPasswordRequest);
        call.enqueue(new Callback<ResponseModel>(){
            @Override
            public void onResponse(Call<ResponseModel> call,Response<ResponseModel> response){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                if(response.body().getCode()==SUCCESS_CODE){
                    if(response.body()!=null){
                        if(response.body().getData().equals("难顶")){
                            Toast.makeText(MyApplication.getContext(),"验证码错误",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MyApplication.getContext(),"修改密码成功",Toast.LENGTH_SHORT).show();
                            startActivity.finish();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call,Throwable t){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }


    //上传头像请求
    public static void postUploadAvatar(String token,MultipartBody.Part file){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postUploadAvatar(token,file);
        call.enqueue(new Callback<ResponseModel>(){
            @Override
            public void onResponse(Call<ResponseModel> call,Response<ResponseModel> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        Toast.makeText(MyApplication.getContext(),"上传头像成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }
/*
二手交易
 */

    //请求交易帖子列表
    public static void postTradeList(final TradeFragment tradeFragment,final TradeListRequest tradeListRequest,final ZLoadingDialog zLoadingDialog){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<TradeInfo[]>> call=request.postTradeList(tradeListRequest);
        call.enqueue(new Callback<ResponseModel<TradeInfo[]>>(){
            @Override
            public void onResponse(Call<ResponseModel<TradeInfo[]>> call,Response<ResponseModel<TradeInfo[]>> response){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        if(response.body().getData().length==0){
                            Toast.makeText(MyApplication.getContext(),"没有更多发布了",Toast.LENGTH_SHORT).show();
                        }else{
                            if(tradeListRequest.getPage().equals("1")){
                                tradeFragment.tradeData.clear();
                            }
                            for(TradeInfo i:response.body().getData()){
                                tradeFragment.tradeData.add(i);
                                Log.d("debug","tradeData"+i.getTitle());
                            }
                            tradeFragment.tradeListAdapter.notifyDataSetChanged();
                        }
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MyApplication.getContext(),"服务器出了点小问题",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<TradeInfo[]>> call,Throwable t){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //请求我的交易帖子列表
    public static void postMyTradeList(final TradeFragment tradeFragment,final TradeListRequest tradeListRequest,final ZLoadingDialog zLoadingDialog){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<TradeInfo[]>> call=request.postMyTradeList(tradeListRequest);
        call.enqueue(new Callback<ResponseModel<TradeInfo[]>>(){
            @Override
            public void onResponse(Call<ResponseModel<TradeInfo[]>> call,Response<ResponseModel<TradeInfo[]>> response){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        if(response.body().getData().length==0){
                            Toast.makeText(MyApplication.getContext(),"没有更多发布了",Toast.LENGTH_SHORT).show();
                        }else{
                            if(tradeListRequest.getPage().equals("1")){
                                tradeFragment.tradeData.clear();
                            }
                            for(TradeInfo i:response.body().getData()){
                                tradeFragment.tradeData.add(i);
                            }
                            tradeFragment.tradeListAdapter.notifyDataSetChanged();
                        }
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MyApplication.getContext(),"服务器出了点小问题",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<TradeInfo[]>> call,Throwable t){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //请求发布帖子
    public static void postAddTrade(final Activity startActivity,Map tradeText,List tradePic,final ZLoadingDialog zLoadingDialog){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postAddTrade(tradeText,tradePic);
        call.enqueue(new Callback<ResponseModel>(){
            @Override
            public void onResponse(Call<ResponseModel> call,Response<ResponseModel> response){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        Toast.makeText(MyApplication.getContext(),"发布成功！",Toast.LENGTH_SHORT).show();
                        startActivity.finish();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call,Throwable t){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //请求删除帖子
    public static void postDeleteTrade(final DeletePostRequest deletePostRequest){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postDeleteTrade(deletePostRequest);
        call.enqueue(new Callback<ResponseModel>(){
            @Override
            public void onResponse(Call<ResponseModel> call,Response<ResponseModel> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        Toast.makeText(MyApplication.getContext(),"删除成功！",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }



/*
失物招领
 */

    //请求失物招领帖子列表
    public static void postLostList(final LostListRequest lostListRequest,final ZLoadingDialog zLoadingDialog){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<LostInfo[]>> call=request.postLostList(lostListRequest);
        call.enqueue(new Callback<ResponseModel<LostInfo[]>>(){
            @Override
            public void onResponse(Call<ResponseModel<LostInfo[]>> call,Response<ResponseModel<LostInfo[]>> response){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        if(response.body().getData().length==0){
                            Toast.makeText(MyApplication.getContext(),"没有更多发布了",Toast.LENGTH_SHORT).show();
                        }else{
                            if(lostListRequest.getPage().equals("1")){
                                LostFragment.lostData.clear();
                            }
                            for(LostInfo i:response.body().getData()){
                                LostFragment.lostData.add(i);
                            }
                            LostFragment.lostListAdapter.notifyDataSetChanged();
                        }
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MyApplication.getContext(),"服务器出了点小问题",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<LostInfo[]>> call,Throwable t){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //请求我的失物招领帖子列表
    public static void postMyLostList(final LostListRequest lostListRequest,final ZLoadingDialog zLoadingDialog){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<LostInfo[]>> call=request.postMyLostList(lostListRequest);
        call.enqueue(new Callback<ResponseModel<LostInfo[]>>(){
            @Override
            public void onResponse(Call<ResponseModel<LostInfo[]>> call,Response<ResponseModel<LostInfo[]>> response){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        if(response.body().getData().length==0){
                            Toast.makeText(MyApplication.getContext(),"没有更多发布了",Toast.LENGTH_SHORT).show();
                        }else{
                            if(lostListRequest.getPage().equals("1")){
                                LostFragment.lostData.clear();
                                Log.d("debug","onResponse: 清空！！！！！！！！！！！！！！！！！！！！！！");
                            }
                            for(LostInfo i:response.body().getData()){
                                LostFragment.lostData.add(i);
                            }
                            LostFragment.lostListAdapter.notifyDataSetChanged();
                        }
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MyApplication.getContext(),"服务器出了点小问题",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<LostInfo[]>> call,Throwable t){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //请求发布帖子
    public static void postAddLost(final Activity startActivity,Map lostText,List lostPic,final ZLoadingDialog zLoadingDialog){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postAddLost(lostText,lostPic);
        call.enqueue(new Callback<ResponseModel>(){
            @Override
            public void onResponse(Call<ResponseModel> call,Response<ResponseModel> response){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        Toast.makeText(MyApplication.getContext(),"发布成功！",Toast.LENGTH_SHORT).show();
                        startActivity.finish();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call,Throwable t){
                if(zLoadingDialog!=null){
                    zLoadingDialog.dismiss();
                }
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //请求删除帖子
    public static void postDeleteLost(final DeletePostRequest deletePostRequest){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postDeleteLost(deletePostRequest);
        call.enqueue(new Callback<ResponseModel>(){
            @Override
            public void onResponse(Call<ResponseModel> call,Response<ResponseModel> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        Toast.makeText(MyApplication.getContext(),"删除成功！",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
