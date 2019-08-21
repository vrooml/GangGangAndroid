package com.example.huge.fzugang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.huge.fzugang.RetrofitStuff.RetrofitUtil;
import com.example.huge.fzugang.Utils.BarcolorUtil;
import com.example.huge.fzugang.Utils.FzuGangTextWatcher;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class LoginActivity extends AppCompatActivity{
    @BindView(R.id.login_phonenum_edit)
    EditText phoneNumEdit;
    @BindView(R.id.login_password_edit)
    EditText passwordEdit;
    @BindView(R.id.login_button)
    QMUIRoundButton loginButton;
    @BindView(R.id.forget_password_btn)
    TextView forgetPassButton;
    @BindView(R.id.register_entrance_btn)
    TextView registerButton;

    private long firstClickTime;//记录退出点击时间

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        init();
    }

    private void init(){
        //先让按钮失效
        loginButton.setEnabled(false);
        //设置文本框改变监听
        phoneNumEdit.addTextChangedListener(new FzuGangTextWatcher(this,loginButton,phoneNumEdit,passwordEdit));
        passwordEdit.addTextChangedListener(new FzuGangTextWatcher(this,loginButton,phoneNumEdit,passwordEdit));
        //设置其他监听
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(phoneNumEdit.getText().length()!=11){
                    Toast.makeText(LoginActivity.this,"手机号长度不正确",Toast.LENGTH_SHORT).show();
                }else if(passwordEdit.getText().length()<6||passwordEdit.getText().length()>12){
                    Toast.makeText(LoginActivity.this,"密码在6-12位之间",Toast.LENGTH_SHORT).show();
                }else{
//                    RetrofitUtil.postLogin(phoneNumEdit.getText().toString(),passwordEdit.getText().toString());//发送登录请求
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
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
