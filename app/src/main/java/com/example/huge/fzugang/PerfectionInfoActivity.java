package com.example.huge.fzugang;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.huge.fzugang.RetrofitStuff.LoginRequest;
import com.example.huge.fzugang.RetrofitStuff.PerfectionRequest;
import com.example.huge.fzugang.RetrofitStuff.RetrofitUtil;
import com.example.huge.fzugang.Utils.BarcolorUtil;
import com.example.huge.fzugang.Utils.LoadingdialogUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.zyao89.view.zloading.ZLoadingDialog;

public class PerfectionInfoActivity extends AppCompatActivity{
    @BindView(R.id.perfection_username_edit)
    TextInputLayout perfectionUsernameEdit;
    @BindView(R.id.perfection_password_edit)
    TextInputLayout perfectionPasswordEdit;
    @BindView(R.id.image_boy)
    ImageView perfectionBoyButton;
    @BindView(R.id.image_girl)
    ImageView perfectionGirlButton;
    @BindView(R.id.perfection_button)
    QMUIRoundButton perfectionButton;

    ZLoadingDialog zLoadingDialog;
    private boolean selectGender;
    private boolean correctUserName;
    private boolean correctPassword;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfection_info);

        ButterKnife.bind(this);
        init();
    }

    private void init(){
        selectGender=false;
        correctPassword=false;
        correctPassword=false;
        //先让按钮失效
        perfectionButton.setEnabled(false);
        //设置文本框改变监听
        perfectionUsernameEdit.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
            }
            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
            }
            @Override
            public void afterTextChanged(Editable s){
                if(perfectionUsernameEdit.getEditText().getText().length()!=11){
                    perfectionUsernameEdit.setError("手机号长度不正确");
                    perfectionUsernameEdit.setErrorEnabled(true);
                }else if(perfectionUsernameEdit.getEditText().getText().length()==0){
                    perfectionUsernameEdit.setError("手机号不能为空");
                    perfectionUsernameEdit.setErrorEnabled(true);
                }else{
                    correctUserName=true;
                }
                if(correctUserName&&correctPassword&&selectGender){
                    perfectionButton.setEnabled(true);
                }else{
                    perfectionButton.setEnabled(false);
                }
            }
        });

        perfectionPasswordEdit.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
            }
            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
            }
            @Override
            public void afterTextChanged(Editable s){
                if(perfectionPasswordEdit.getEditText().getText().length()==0){
                    perfectionPasswordEdit.setError("密码不能为空");
                    perfectionPasswordEdit.setErrorEnabled(true);
                }else{
                    correctPassword=true;
                }
                if(correctUserName&&correctPassword&&selectGender){
                    perfectionButton.setEnabled(true);
                }else{
                    perfectionButton.setEnabled(false);
                }
            }
        });

        //设置其他监听
        perfectionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                zLoadingDialog=LoadingdialogUtil.getZLoadingDialog(PerfectionInfoActivity.this);
                PerfectionRequest perfectionRequest=new PerfectionRequest(
                        SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token")
                        ,perfectionUsernameEdit.getEditText().getText().toString()
                        ,perfectionPasswordEdit.getEditText().getText().toString()
                        ,SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"phoneNum")
                        ,gender);
                RetrofitUtil.postPerfection(PerfectionInfoActivity.this,perfectionRequest,zLoadingDialog);//发送登录请求
            }
        });

        //选择男监听
        perfectionBoyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(gender!=null&&gender.equals("女")){
                    perfectionGirlButton.setImageResource(R.drawable.girl_unselect_icon);
                }
                perfectionBoyButton.setImageResource(R.drawable.boy_icon);
                gender="男";
            }
        });

        //选择女监听
        perfectionGirlButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(gender!=null&&gender.equals("男")){
                    perfectionBoyButton.setImageResource(R.drawable.boy_unselect_icon);
                }
                perfectionGirlButton.setImageResource(R.drawable.girl_icon);
                gender="女";
            }
        });

    }
}
