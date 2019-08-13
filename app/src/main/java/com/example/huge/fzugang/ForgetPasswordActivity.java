package com.example.huge.fzugang;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.huge.fzugang.RetrofitStuff.RetrofitUtil;
import com.example.huge.fzugang.Utils.FzuGangTextWatcher;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class ForgetPasswordActivity extends AppCompatActivity{
    @BindView(R.id.forget_confirm_button)
    QMUIRoundButton forgetConfirmButton;
    @BindView(R.id.forget_phonenum_edit)
    EditText forgetPhonenumEdit;
    @BindView(R.id.forget_send_authcode_button)
    QMUIRoundButton forgetSendCodeButton;
    @BindView(R.id.forget_authcode_edit)
    EditText forgetCodeEdit;
    @BindView(R.id.forget_password_edit)
    EditText forgetPasswordEdit;
    @BindView(R.id.forget_confirm_password_edit)
    EditText forgetConfirmPasswordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        ButterKnife.bind(this);
        Init();
    }

    private void Init(){
        //先让按钮失效
        forgetConfirmButton.setEnabled(false);
        //设置文本框改变监听
        forgetPhonenumEdit.addTextChangedListener(new FzuGangTextWatcher(this,forgetConfirmButton,forgetPhonenumEdit,forgetCodeEdit,forgetPasswordEdit,forgetConfirmPasswordEdit));
        forgetCodeEdit.addTextChangedListener(new FzuGangTextWatcher(this,forgetConfirmButton,forgetPhonenumEdit,forgetCodeEdit,forgetPasswordEdit,forgetConfirmPasswordEdit));
        forgetPasswordEdit.addTextChangedListener(new FzuGangTextWatcher(this,forgetConfirmButton,forgetPhonenumEdit,forgetCodeEdit,forgetPasswordEdit,forgetConfirmPasswordEdit));
        forgetConfirmPasswordEdit.addTextChangedListener(new FzuGangTextWatcher(this,forgetConfirmButton,forgetPhonenumEdit,forgetCodeEdit,forgetPasswordEdit,forgetConfirmPasswordEdit));

        //设置确认键监听
        forgetConfirmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(forgetPhonenumEdit.getText().length()!=11){
                    Toast.makeText(ForgetPasswordActivity.this,"手机号长度不正确",Toast.LENGTH_SHORT).show();
                }else if(forgetCodeEdit.getText().length()<6||forgetCodeEdit.getText().length()>12){
                    Toast.makeText(ForgetPasswordActivity.this,"密码在6-12位之间",Toast.LENGTH_SHORT).show();
                }else if(!forgetPasswordEdit.getText().toString().equals(forgetConfirmPasswordEdit.getText().toString())){
                    Toast.makeText(ForgetPasswordActivity.this,"两次输入密码不一致，请检查",Toast.LENGTH_SHORT).show();
                }else if(!checkPassword()){
                    Toast.makeText(ForgetPasswordActivity.this,"密码须由字母、数字组成，可使用下划线",Toast.LENGTH_SHORT).show();
                }else{
//                    RetrofitUtil.postResetPassWord(forgetPhonenumEdit.getText().toString(),forgetCodeEdit.getText().toString());//发送登录请求
                    Intent intent=new Intent(ForgetPasswordActivity.this,MainActivity.class);
                    startActivity(intent);
                    ForgetPasswordActivity.this.finish();
                }
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
                if(forgetPhonenumEdit.getText().length()!=11){
                    Toast.makeText(ForgetPasswordActivity.this,"手机号长度不正确",Toast.LENGTH_SHORT).show();
                }else{
//                    RetrofitUtil.postForgetCode();
                    mCountDownTimer.start();
                }
            }
        });
    }

    private boolean checkPassword(){
        boolean isDigit=false;
        boolean isAlpha=false;
        boolean isRight=true;
        String password=forgetPasswordEdit.getText().toString();
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
