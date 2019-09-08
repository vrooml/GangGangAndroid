package com.example.huge.fzugang;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.huge.fzugang.RetrofitStuff.ChangeInfoRequest;
import com.example.huge.fzugang.RetrofitStuff.PerfectionRequest;
import com.example.huge.fzugang.TradeBlock.AddTradeActivity;
import com.example.huge.fzugang.Utils.RetrofitUtil;
import com.example.huge.fzugang.Utils.LoadingdialogUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zyao89.view.zloading.ZLoadingDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.huge.fzugang.Utils.constantUtil.BaseUrl;

public class PerfectionInfoActivity extends BaseActivity{
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
    @BindView(R.id.perfection_text)
    TextView perfectionTitle;
    @BindView(R.id.perfection_avatar)
    CircleImageView perfectionAvatar;

    ZLoadingDialog zLoadingDialog;
    private boolean selectGender;
    private boolean correctUserName;
    private boolean correctPassword;
    private boolean uploadAvatar;
    private String gender;
    private List<Uri> avatar;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfection_info);


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
        avatar=new ArrayList<>();
        selectGender=false;
        correctUserName=false;
        uploadAvatar=false;

        username=SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"username");

        //修改信息界面
        if(getIntent().getSerializableExtra("sourceClass").equals(MainActivity.class)){
            correctPassword=true;
            //修改标题
            perfectionTitle.setText("修改信息");
            Glide.with(PerfectionInfoActivity.this)
                    .load(BaseUrl+"/fdb1.0.0/user/download/avatar/id/"+SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"userId"))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(perfectionAvatar);
            //加载性别
            if(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"gender").equals("男")){
                perfectionBoyButton.setImageResource(R.drawable.boy_icon);
                gender="男";
                selectGender=true;
            }else{
                perfectionGirlButton.setImageResource(R.drawable.girl_icon);
                gender="女";
                selectGender=true;
            }
            //加载用户名
            perfectionUsernameEdit.setHint("原昵称："+SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"username"));
            perfectionPasswordEdit.setVisibility(View.GONE);
            //设置确定键监听
            perfectionButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    zLoadingDialog=LoadingdialogUtil.getZLoadingDialog(PerfectionInfoActivity.this);
                    ChangeInfoRequest changeInfoRequest=new ChangeInfoRequest(
                            SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),
                            username,
                            gender);
                    RetrofitUtil.postChangeInfo(PerfectionInfoActivity.this,changeInfoRequest,zLoadingDialog);//发送登录请求
                    if(uploadAvatar){
                        RetrofitUtil.postUploadAvatar(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),getImgList(avatar).get(0));//发送上传头像请求
                    }
                }
            });
        }
        //完善信息界面
        else{
            correctPassword=false;
            //先让按钮失效
            perfectionButton.setEnabled(false);
            //设置密码改变监听
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

            //设置确定键监听
            perfectionButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    zLoadingDialog=LoadingdialogUtil.getZLoadingDialog(PerfectionInfoActivity.this);
                    PerfectionRequest perfectionRequest=new PerfectionRequest(
                            perfectionUsernameEdit.getEditText().getText().toString()
                            ,perfectionPasswordEdit.getEditText().getText().toString()
                            ,SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"phoneNum")
                            ,gender);
                    RetrofitUtil.postPerfection(PerfectionInfoActivity.this,perfectionRequest,zLoadingDialog);//发送登录请求
                    if(uploadAvatar){
                        RetrofitUtil.postUploadAvatar(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),getImgList(avatar).get(0));//发送上传头像请求
                    }
                }
            });
        }

        //上传头像监听
        perfectionAvatar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Matisse.from(PerfectionInfoActivity.this)
                        .choose(MimeType.of(MimeType.JPEG,MimeType.PNG))
                        .countable(false)//true:选中后显示数字;false:选中后显示对号
                        .maxSelectable(1)//可选的最大数
                        .capture(true)//选择照片时，是否显示拍照
                        .captureStrategy(new CaptureStrategy(true,"PhotoPicker"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                        .imageEngine(new GlideEngine())//图片加载引擎
                        .forResult(1);
            }
        });
        //设置用户名改变监听
        perfectionUsernameEdit.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
            }
            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
                if(perfectionUsernameEdit.getEditText().getText().length()==0){
                    perfectionUsernameEdit.setError("昵称不能为空");
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
            @Override
            public void afterTextChanged(Editable s){
                username=perfectionUsernameEdit.getEditText().getText().toString();
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
                selectGender=true;
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
                selectGender=true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                avatar=Matisse.obtainResult(data);
                Glide.with(this).load(avatar.get(0)).into(perfectionAvatar);
                uploadAvatar=true;
            }
        }
    }

    //将contentUri转化为发送请求时的数据结构(发送图片)
    private List<MultipartBody.Part> getImgList(List<Uri> origin){
        List<MultipartBody.Part> result=new ArrayList<>();
        for(Uri i: origin){
            //将contentUri转化为真实路径Uri
            String res=null;
            String[] proj={MediaStore.Images.Media.DATA};
            Cursor cursor=getContentResolver().query(i,proj,null,null,null);
            if(cursor.moveToFirst()){
                int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                res=cursor.getString(column_index);
            }
            cursor.close();
            //将路径uri转化为file
            File file=new File(res);
            //将路径file转化为RequestBody
            RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
            //将RequestBody转化为MultipartBody.Part
            MultipartBody.Part finalRequest=MultipartBody.Part.createFormData("avatar",file.getName(),requestBody);//pics[]为后端的key
            result.add(finalRequest);
        }
        return result;
    }
}
