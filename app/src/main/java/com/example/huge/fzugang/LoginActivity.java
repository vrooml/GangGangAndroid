package com.example.huge.fzugang;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.huge.fzugang.RetrofitStuff.LoginRequest;
import com.example.huge.fzugang.Utils.RetrofitUtil;
import com.example.huge.fzugang.Utils.LoadingdialogUtil;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.zyao89.view.zloading.ZLoadingDialog;

public class LoginActivity extends BaseActivity{
    @BindView(R.id.login_phonenum_edit)
    TextInputLayout phoneNumEdit;
    @BindView(R.id.login_password_edit)
    TextInputLayout passwordEdit;
    @BindView(R.id.login_button)
    QMUIRoundButton loginButton;
    @BindView(R.id.forget_password_btn)
    TextView forgetPassButton;
    @BindView(R.id.register_entrance_btn)
    TextView registerButton;
    ZLoadingDialog zLoadingDialog;
    private long firstClickTime;//记录退出点击时间
    private boolean correctPhone;
    private boolean correctPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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
        //先让按钮失效
        loginButton.setEnabled(false);
        //设置文本框改变监听
        phoneNumEdit.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
            }
            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
            }
            @Override
            public void afterTextChanged(Editable s){
                if(phoneNumEdit.getEditText().getText().length()!=11){
                    phoneNumEdit.setError("手机号长度不正确");
                    phoneNumEdit.setErrorEnabled(true);
                }else if(phoneNumEdit.getEditText().getText().length()==0){
                    phoneNumEdit.setError("手机号不能为空");
                    phoneNumEdit.setErrorEnabled(true);
                }else{
                    correctPhone=true;
                    phoneNumEdit.setErrorEnabled(false);
                }
                if(correctPhone&&correctPassword){
                    loginButton.setEnabled(true);
                }else{
                    loginButton.setEnabled(false);
                }
            }
        });

        passwordEdit.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
            }
            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
            }
            @Override
            public void afterTextChanged(Editable s){
                if(passwordEdit.getEditText().getText().length()==0){
                    passwordEdit.setError("密码不能为空");
                    passwordEdit.setErrorEnabled(true);
                }else{
                    correctPassword=true;
                    passwordEdit.setErrorEnabled(false);
                }
                if(correctPhone&&correctPassword){
                    loginButton.setEnabled(true);
                }else{
                    loginButton.setEnabled(false);
                }
            }
        });

        //设置其他监听
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                zLoadingDialog=LoadingdialogUtil.getZLoadingDialog(LoginActivity.this);
                LoginRequest loginRequest=new LoginRequest(phoneNumEdit.getEditText().getText().toString(),passwordEdit.getEditText().getText().toString());
                RetrofitUtil.postLogin(LoginActivity.this,loginRequest,zLoadingDialog);//发送登录请求
            }
        });

        forgetPassButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
            long secondTime=System.currentTimeMillis();
            if(secondTime-firstClickTime>2000){
                Toast.makeText(LoginActivity.this,"再次点击退出福大帮",Toast.LENGTH_SHORT).show();
                firstClickTime=secondTime;
                return true;
            }else{
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode,event);
    }
}
