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
import com.example.huge.fzugang.RetrofitStuff.RegisterRequest;
import com.example.huge.fzugang.Utils.RetrofitUtil;
import com.example.huge.fzugang.Utils.LoadingdialogUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.zyao89.view.zloading.ZLoadingDialog;

public class RegisterActivity extends BaseActivity{
    @BindView(R.id.register_button)
    QMUIRoundButton registerButton;
    @BindView(R.id.register_send_authcode_button)
    QMUIRoundButton registerSendCodeButton;
    @BindView(R.id.register_phonenum_edit)
    TextInputLayout registerPhonenumEdit;
    @BindView(R.id.register_authcode_edit)
    TextInputLayout registerCodeEdit;

    private boolean correctPhone;
    private boolean correctCode;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
        registerButton.setEnabled(false);
        //设置文本框改变监听
        registerPhonenumEdit.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
            }
            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
            }
            @Override
            public void afterTextChanged(Editable s){
                if(registerPhonenumEdit.getEditText().getText().length()!=11){
                    registerPhonenumEdit.setError("手机号长度不正确");
                    registerPhonenumEdit.setErrorEnabled(true);
                }else if(registerPhonenumEdit.getEditText().getText().length()==0){
                    registerPhonenumEdit.setError("手机号不能为空");
                    registerPhonenumEdit.setErrorEnabled(true);
                }else{
                    correctPhone=true;
                    registerPhonenumEdit.setErrorEnabled(false);
                }
                if(correctPhone&&correctCode){
                    registerButton.setEnabled(true);
                }else{
                    registerButton.setEnabled(false);
                }
            }
        });

        registerCodeEdit.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
            }
            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
            }
            @Override
            public void afterTextChanged(Editable s){
                if(registerCodeEdit.getEditText().getText().length()==0){
                    registerCodeEdit.setError("验证码不能为空");
                    registerCodeEdit.setErrorEnabled(true);
                }else{
                    correctCode=true;
                    registerCodeEdit.setErrorEnabled(false);
                }
                if(correctPhone&&correctCode){
                    registerButton.setEnabled(true);
                }else{
                    registerButton.setEnabled(false);
                }
            }
        });

        //设置确认键监听
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ZLoadingDialog zLoadingDialog=LoadingdialogUtil.getZLoadingDialog(RegisterActivity.this);
                RegisterRequest registerRequest=new RegisterRequest(registerPhonenumEdit.getEditText().getText().toString(),registerCodeEdit.getEditText().getText().toString()
                        ,SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"authCodeToken"));
                RetrofitUtil.postRegister(RegisterActivity.this,registerRequest,zLoadingDialog);//发送登录请求
            }
        });

        final CountDownTimer mCountDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long time) {
                registerSendCodeButton.setEnabled(false);
                registerSendCodeButton.setText(+(time / 1000) + "秒后重新发送");
            }

            @Override
            public void onFinish() {
                registerSendCodeButton.setEnabled(true);
                registerSendCodeButton.setText("重新发送");
            }
        };

        //设置发送验证码键监听
        registerSendCodeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(registerPhonenumEdit.getEditText().getText().length()!=11){
                    Toast.makeText(RegisterActivity.this,"手机号长度不正确",Toast.LENGTH_SHORT).show();
                }else{
                    String codeMsg="注册";
                    AuthCodeRequest authCodeRequest=new AuthCodeRequest(registerPhonenumEdit.getEditText().getText().toString(),codeMsg);
                    RetrofitUtil.postAuthCode(authCodeRequest);
                    mCountDownTimer.start();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        finish();
        return super.onKeyDown(keyCode,event);
    }
}
