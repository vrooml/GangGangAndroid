package com.example.huge.fzugang.RetrofitStuff;

import com.example.huge.fzugang.ForgetPasswordActivity;
import com.example.huge.fzugang.LostBlock.LostInfo;
import com.example.huge.fzugang.TradeBlock.TradeInfo;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface PostInterfaces{
    //获取验证码
    @POST("/fdb1.0.0/sms/send")
    Call<ResponseModel<String>> postAuthCode(@Body AuthCodeRequest token);

    //请求注册账户
    @POST("/fdb1.0.0/user/vercode")
    Call<ResponseModel> postRegister(@Body RegisterRequest registerRequest);

    //请求完善信息
    @POST("/fdb1.0.0/user/info/fill")
    Call<ResponseModel> postPerfection(@Body PerfectionRequest perfectionRequest);

    //请求修改信息
    @POST("/fdb1.0.0/user/info/update")
    Call<ResponseModel> postChangeInfo(@Body ChangeInfoRequest changeInfoRequest);

    //请求修改密码
    @POST("/fdb1.0.0/user/info/update/password")
    Call<ResponseModel> postForgetPassword(@Body ForgetPasswordRequest forgetPasswordRequest);

    //获取本用户信息
    @POST("/fdb1.0.0/user/info/getMessage")
    Call<ResponseModel<UserInfo>> postUserInfo(@Body UserInfoRequest userInfoRequest);

    //获取指定用户简略信息
    @POST("/fdb1.0.0/user/info/get")
    Call<ResponseModel<UserInfo>> postUserBriefInfo(@Body UserInfoRequest userInfoRequest);

    //登录请求
    @POST("/fdb1.0.0/user/login")
    Call<ResponseModel<String>> login(@Body LoginRequest loginRequest);

    //退出登录请求
    @POST("/fdb1.0.0/user/out")
    Call<ResponseModel> postSignOut(@Body SignOutRequest signOutRequest);

    //请求上传头像
    @Multipart
    @POST("/fdb1.0.0/user/info/upload/avatar")
    Call<ResponseModel> postUploadAvatar(@Part("token") String token,@Part() MultipartBody.Part avatar);

    //请求交易帖子列表
    @POST("/fdb1.0.0/trade/list")
    Call<ResponseModel<TradeInfo[]>> postTradeList(@Body TradeListRequest tradeListRequest);

    //请求我的交易帖子列表
    @POST("/fdb1.0.0/trade/me")
    Call<ResponseModel<TradeInfo[]>> postMyTradeList(@Body TradeListRequest tradeListRequest);

    //请求发布交易
    @Multipart
    @POST("/fdb1.0.0/trade/add")
    Call<ResponseModel> postAddTrade(@PartMap Map<String,RequestBody> tradeText,
                                     @Part List<MultipartBody.Part> tradePic);

    //请求删除交易帖子
    @POST("/fdb1.0.0/trade/del")
    Call<ResponseModel> postDeleteTrade(@Body DeletePostRequest deletePostRequest);

    //请求失物招领帖子列表
    @POST("/fdb1.0.0/lostProperty/list")
    Call<ResponseModel<LostInfo[]>> postLostList(@Body LostListRequest lostListRequest);

    //请求我的失物招领帖子列表
    @POST("/fdb1.0.0/lostProperty/list")
    Call<ResponseModel<LostInfo[]>> postMyLostList(@Body LostListRequest lostListRequest);

    //请求发布失物招领
    @Multipart
    @POST("/fdb1.0.0/lostProperty/add")
    Call<ResponseModel> postAddLost(@PartMap Map<String,RequestBody> lostText,
                                     @Part List<MultipartBody.Part> lostPic);

    //请求删除交易帖子
    @POST("/fdb1.0.0/lostProperty/del")
    Call<ResponseModel> postDeleteLost(@Body DeletePostRequest deletePostRequest);

}
