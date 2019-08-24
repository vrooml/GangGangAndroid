package com.example.huge.fzugang.RetrofitStuff;

import com.example.huge.fzugang.ForgetPasswordActivity;
import com.example.huge.fzugang.TradeBlock.TradeInfo;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

import java.util.List;
import java.util.Map;

public interface PostInterfaces{
    //获取验证码
    @POST("/fdb1.0.0/sms/send")
    Call<ResponseModel<String>> postAuthCode(@Body AuthCodeRequest token);

    //请求注册账户
    @POST("/fdbl1.0.0/user/vercode")
    Call<ResponseModel> postRegister(@Body RegisterRequest registerRequest);

    //请求完善信息
    @POST("/fdb1.0.0/user/info/fill")
    Call<ResponseModel> postPerfection(@Body PerfectionRequest perfectionRequest);

    //请求修改密码
    @POST("/fdb1.0.0/user/info/update/password")
    Call<ResponseModel> postForgetPassword(@Body ForgetPasswordRequest forgetPasswordRequest);

    //获取用户信息
    @POST("/fdb1.0.0/user/info/getId")
    Call<ResponseModel<UserInfo>> postUserInfo(@Body String token);

    //登录请求
    @POST("/fdbl1.0.0/user/login")
    Call<ResponseModel<String>> login(@Body LoginRequest loginRequest);

    //请求交易帖子列表
    @POST("/fdb1.0.0/trade/list")
    Call<ResponseModel<TradeInfo[]>> postTradeList(@Body TradeListRequest tradeListRequest);

    //请求发布交易
    @POST("/fdb1.0.0/trade/add")
    Call<ResponseModel> postAddTrade(@PartMap Map<String,RequestBody> tradeText,
                                     @Part List<MultipartBody.Part> tradePic);

}
