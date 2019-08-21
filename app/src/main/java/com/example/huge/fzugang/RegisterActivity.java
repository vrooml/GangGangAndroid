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
import com.example.huge.fzugang.Utils.BarcolorUtil;
import com.example.huge.fzugang.Utils.FzuGangTextWatcher;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class RegisterActivity extends AppCompatActivity{
    @BindView(R.id.register_button)
    QMUIRoundButton registerButton;
    @BindView(R.id.register_send_authcode_button)
    QMUIRoundButton registerSendCodeButton;
    @BindView(R.id.register_phonenum_edit)
    EditText registerPhonenumEdit;
    @BindView(R.id.register_authcode_edit)
    EditText registerCodeEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        init();
    }

    private void init(){
        //先让按钮失效
        registerButton.setEnabled(false);
        //设置文本框改变监听
        registerPhonenumEdit.addTextChangedListener(new FzuGangTextWatcher(this,registerButton,registerPhonenumEdit,registerCodeEdit));
        registerCodeEdit.addTextChangedListener(new FzuGangTextWatcher(this,registerButton,registerPhonenumEdit,registerCodeEdit));

        //设置确认键监听
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(registerPhonenumEdit.getText().length()!=11){
                    Toast.makeText(RegisterActivity.this,"手机号长度不正确",Toast.LENGTH_SHORT).show();
                }else{
//                    RetrofitUtil.postRegister(registerPhonenumEdit.getText().toString(),registerCodeEdit.getText().toString());//发送登录请求
                    Intent intent=new Intent(RegisterActivity.this,PerfectionInfoActivity.class);
                    startActivity(intent);
                    RegisterActivity.this.finish();
                }
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
                if(registerPhonenumEdit.getText().length()!=11){
                    Toast.makeText(RegisterActivity.this,"手机号长度不正确",Toast.LENGTH_SHORT).show();
                }else{
//                    RetrofitUtil.postRegisterCode();
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
