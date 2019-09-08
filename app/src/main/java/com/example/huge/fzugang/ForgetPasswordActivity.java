package com.example.huge.fzugang;

import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.huge.fzugang.RetrofitStuff.AuthCodeRequest;
import com.example.huge.fzugang.RetrofitStuff.ForgetPasswordRequest;
import com.example.huge.fzugang.Utils.RetrofitUtil;
import com.example.huge.fzugang.Utils.LoadingdialogUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ForgetPasswordActivity extends BaseActivity{
    @BindView(R.id.forget_confirm_button)
    QMUIRoundButton forgetConfirmButton;
    @BindView(R.id.forget_phonenum_edit)
    TextInputLayout forgetPhonenumEdit;
    @BindView(R.id.forget_send_authcode_button)
    QMUIRoundButton forgetSendCodeButton;
    @BindView(R.id.forget_authcode_edit)
    TextInputLayout forgetCodeEdit;
    @BindView(R.id.forget_password_edit)
    TextInputLayout forgetPasswordEdit;
    @BindView(R.id.forget_confirm_password_edit)
    TextInputLayout forgetConfirmPasswordEdit;

    private Boolean correctPhone;
    private Boolean correctCode;
    private Boolean correctPassword;
    private Boolean correctConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        correctPhone=false;
        correctCode=false;
        correctPassword=false;
        correctConfirmPassword=false;
        //先让按钮失效
        forgetConfirmButton.setEnabled(false);
        //设置文本框改变监听
        forgetPhonenumEdit.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
            }
            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
            }
            @Override
            public void afterTextChanged(Editable s){
                if(forgetPhonenumEdit.getEditText().getText().length()!=11){
                    forgetPhonenumEdit.setError("手机号长度不正确");
                    forgetPhonenumEdit.setErrorEnabled(true);
                }else if(forgetPhonenumEdit.getEditText().getText().length()==0){
                    forgetPhonenumEdit.setError("手机号不能为空");
                    forgetPhonenumEdit.setErrorEnabled(true);
                }else{
                    correctPhone=true;
                    forgetPhonenumEdit.setErrorEnabled(false);
                }
                if(correctPhone&&correctCode&&correctConfirmPassword&&correctPassword){
                    forgetConfirmButton.setEnabled(true);
                }else{
                    forgetConfirmButton.setEnabled(false);
                }
            }
        });

        forgetCodeEdit.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
            }
            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
            }
            @Override
            public void afterTextChanged(Editable s){
                if(forgetCodeEdit.getEditText().getText().length()==0){
                    forgetCodeEdit.setError("验证码不能为空");
                    forgetCodeEdit.setErrorEnabled(true);
                }else{
                    correctCode=true;
                    forgetCodeEdit.setErrorEnabled(false);
                }
                if(correctPhone&&correctCode&&correctConfirmPassword&&correctPassword){
                    forgetConfirmButton.setEnabled(true);
                }else{
                    forgetConfirmButton.setEnabled(false);
                }
            }
        });

        forgetPasswordEdit.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
            }
            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
            }
            @Override
            public void afterTextChanged(Editable s){
                if(!checkPassword(forgetPasswordEdit)){
                    forgetPasswordEdit.setError("密码须由字母、数字组成，可使用下划线");
                    forgetPasswordEdit.setErrorEnabled(true);
                }else if(forgetPasswordEdit.getEditText().getText().length()<6||forgetPasswordEdit.getEditText().getText().length()>12){
                    forgetPasswordEdit.setError("密码在6-12位之间");
                    forgetPasswordEdit.setErrorEnabled(true);
                }else if(forgetPasswordEdit.getEditText().getText().length()==0){
                    forgetPasswordEdit.setError("密码不能为空");
                    forgetPasswordEdit.setErrorEnabled(true);
                }else{
                    correctPassword=true;
                    forgetPasswordEdit.setErrorEnabled(false);
                }
                if(correctPhone&&correctCode&&correctConfirmPassword&&correctPassword){
                    forgetConfirmButton.setEnabled(true);
                }else{
                    forgetConfirmButton.setEnabled(false);
                }
            }
        });

        forgetConfirmPasswordEdit.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
            }
            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
            }
            @Override
            public void afterTextChanged(Editable s){
                if(!checkPassword(forgetPasswordEdit)){
                    forgetPasswordEdit.setError("密码须由字母、数字组成，可使用下划线");
                    forgetPasswordEdit.setErrorEnabled(true);
                }else if(forgetPasswordEdit.getEditText().getText().length()<6||forgetPasswordEdit.getEditText().getText().length()>12){
                    forgetPasswordEdit.setError("密码在6-12位之间");
                    forgetPasswordEdit.setErrorEnabled(true);
                }else if(!forgetPasswordEdit.getEditText().getText().toString().equals(forgetConfirmPasswordEdit.getEditText().getText().toString())){
                    forgetPasswordEdit.setError("两次输入密码不一致，请检查");
                    forgetPasswordEdit.setErrorEnabled(true);
                }else if(forgetPasswordEdit.getEditText().getText().length()==0){
                    forgetPasswordEdit.setError("密码不能为空");
                    forgetPasswordEdit.setErrorEnabled(true);
                }else{
                    correctConfirmPassword=true;
                    forgetPasswordEdit.setErrorEnabled(false);
                }
                if(correctPhone&&correctCode&&correctConfirmPassword&&correctPassword){
                    forgetConfirmButton.setEnabled(true);
                }else{
                    forgetConfirmButton.setEnabled(false);
                }
            }
        });

        //设置确认键监听
        forgetConfirmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ZLoadingDialog zLoadingDialog=LoadingdialogUtil.getZLoadingDialog(ForgetPasswordActivity.this);
                ForgetPasswordRequest forgetPasswordRequest=new ForgetPasswordRequest(
                        SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"authCodeToken")
                        ,forgetPhonenumEdit.getEditText().getText().toString()
                        ,forgetPasswordEdit.getEditText().getText().toString()
                        ,forgetCodeEdit.getEditText().getText().toString());
                RetrofitUtil.postForgetPassWord(ForgetPasswordActivity.this,forgetPasswordRequest,zLoadingDialog);//发送修改密码请求
            }
        });

        final CountDownTimer mCountDownTimer = new CountDownTimer(60 * 1000, 1000) {

            @Override
            public void onTick(long time) {
                forgetSendCodeButton.setEnabled(false);
                forgetSendCodeButton.setText(+(time / 1000) + "秒后重新发送");
            }

            @Override
            public void onFinish() {
                forgetSendCodeButton.setEnabled(true);
                forgetSendCodeButton.setText("重新发送");
            }
        };

        //设置发送验证码键监听
        forgetSendCodeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(forgetPhonenumEdit.getEditText().getText().length()!=11){
                    Toast.makeText(ForgetPasswordActivity.this,"手机号长度不正确",Toast.LENGTH_SHORT).show();
                }else{
                    String codeMsg="修改密码";
                    AuthCodeRequest authCodeRequest=new AuthCodeRequest(forgetPhonenumEdit.getEditText().getText().toString(),codeMsg);
                    RetrofitUtil.postAuthCode(authCodeRequest);
                    mCountDownTimer.start();
                }
            }
        });
    }

    private boolean checkPassword(TextInputLayout textInputLayout){
        boolean isDigit=false;
        boolean isAlpha=false;
        boolean isRight=true;
        String password=textInputLayout.getEditText().getText().toString();
        for(int i=0;i<password.length();i++){
            if(password.charAt(i)>='a'&&password.charAt(i)<='z'||password.charAt(i)>='A'&&password.charAt(i)<='Z'){
                isAlpha=true;
            }else if(password.charAt(i)>='0'&&password.charAt(i)<='9'){
                isDigit=true;
            }else{
                if(password.charAt(i)!='_')
                isRight=false;
            }
        }
        return isDigit&&isAlpha&&isRight;
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        finish();
        return super.onKeyDown(keyCode,event);
    }
}
