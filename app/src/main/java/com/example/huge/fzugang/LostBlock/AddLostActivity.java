package com.example.huge.fzugang.LostBlock;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.huge.fzugang.AddPictureGridViewAdapter;
import com.example.huge.fzugang.BaseActivity;
import com.example.huge.fzugang.MyApplication;
import com.example.huge.fzugang.R;
import com.example.huge.fzugang.Utils.RetrofitUtil;
import com.example.huge.fzugang.Utils.FzuGangTextInputWatcher;
import com.example.huge.fzugang.Utils.LoadingdialogUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zyao89.view.zloading.ZLoadingDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.huge.fzugang.MyApplication.getContext;

public class AddLostActivity extends BaseActivity{
    @BindView(R.id.found_classify_button)
    Button foundClassifyButton;
    @BindView(R.id.lost_classify_button)
    Button lostClassifyButton;
    @BindView(R.id.add_lost_post_button)
    Button addButton;
    @BindView(R.id.add_lost_title)
    TextInputLayout title;
    @BindView(R.id.add_lost_content)
    TextInputLayout content;
    @BindView(R.id.add_lost_place)
    TextInputLayout lostPlace;
    @BindView(R.id.add_lost_time)
    TextInputLayout lostTime;
    @BindView(R.id.add_lost_contact)
    TextInputLayout contact;
    @BindView(R.id.add_lost_pick_picture)
    GridView addPictureGrid;
    AddPictureGridViewAdapter addPictureAdapter;

    List<Uri> pictures=new ArrayList<>();//图片路径列表
    int classify;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lost);

        ButterKnife.bind(this);
        init();
    }

    private void init(){
        classify=1;
        foundClassifyButton.setTextColor(getContext().getResources().getColor(R.color.basicColor));
        foundClassifyButton.setTextSize(15);

        //设置图片添加adapter
        addPictureAdapter=new AddPictureGridViewAdapter(pictures,this);
        addPictureGrid.setAdapter(addPictureAdapter);

        //设置Gridview点击事件
        addPictureGrid.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView,View view,int position,long id){
                Matisse.from(AddLostActivity.this)
                        .choose(MimeType.allOf())
                        .countable(true)//true:选中后显示数字;false:选中后显示对号
                        .maxSelectable(9)//可选的最大数
                        .capture(false)//选择照片时，是否显示拍照
                        .imageEngine(new GlideEngine())//图片加载引擎
                        .forResult(1);
            }
        });

        //先让按钮失效
        addButton.setEnabled(false);
        //设置文本框改变监听
        title.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
            }

            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
                if(title.getEditText().getText().toString().length()==0){
                    title.setError("请输入内容");
                    title.setErrorEnabled(true);
                }else{
                    title.setErrorEnabled(false);
                }
                title.getEditText().getBackground().clearColorFilter();
                isNullText(title,content,lostTime,lostPlace,contact);
            }

            @Override
            public void afterTextChanged(Editable s){
            }
        });
        content.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
                content.getEditText().getBackground().clearColorFilter();
            }

            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
                if(content.getEditText().getText().toString().length()==0){
                    content.setError("请输入内容");
                    content.setErrorEnabled(true);
                }else{
                    content.setErrorEnabled(false);
                }
                content.getEditText().getBackground().clearColorFilter();
                isNullText(title,content,lostTime,lostPlace,contact);
            }

            @Override
            public void afterTextChanged(Editable s){
            }
        });
        lostPlace.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
            }

            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
                if(lostPlace.getEditText().getText().toString().length()==0){
                    lostPlace.setError("请输入内容");
                    lostPlace.setErrorEnabled(true);
                }else{
                    lostPlace.setErrorEnabled(false);
                }
                lostPlace.getEditText().getBackground().clearColorFilter();
                isNullText(title,content,lostTime,lostPlace,contact);
            }

            @Override
            public void afterTextChanged(Editable s){
            }
        });
        lostTime.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
            }

            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
                if(lostTime.getEditText().getText().toString().length()==0){
                    lostTime.setError("请输入内容");
                    lostTime.setErrorEnabled(true);
                }else{
                    lostTime.setErrorEnabled(false);
                }
                lostTime.getEditText().getBackground().clearColorFilter();
                isNullText(title,content,lostTime,lostPlace,contact);
            }

            @Override
            public void afterTextChanged(Editable s){
            }
        });
        contact.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
            }

            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
                if(contact.getEditText().getText().toString().length()==0){
                    contact.setError("请输入内容");
                    contact.setErrorEnabled(true);
                }else{
                    contact.setErrorEnabled(false);
                }
                contact.getEditText().getBackground().clearColorFilter();
                isNullText(title,content,lostTime,lostPlace,contact);
            }

            @Override
            public void afterTextChanged(Editable s){
            }
        });

        //发布按钮监听
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String token=SharedPreferencesUtil.getStoredMessage(getContext(),"token");
                Map<String,RequestBody> lostText=new HashMap<>();
                RequestBody tokenRequest=RequestBody.create(MediaType.parse("multipart/form-data"),token);
                RequestBody titleRequest=RequestBody.create(MediaType.parse("multipart/form-data"),title.getEditText().getText().toString());
                RequestBody contentRequest=RequestBody.create(MediaType.parse("multipart/form-data"),content.getEditText().getText().toString());
                RequestBody lostTimeRequest=RequestBody.create(MediaType.parse("multipart/form-data"),lostTime.getEditText().getText().toString());
                RequestBody lostPlaceRequest=RequestBody.create(MediaType.parse("multipart/form-data"),lostPlace.getEditText().getText().toString());
                RequestBody contactRequest=RequestBody.create(MediaType.parse("multipart/form-data"),contact.getEditText().getText().toString());
                RequestBody classifyRequest=RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(classify));
                lostText.put("token",tokenRequest);
                lostText.put("title",titleRequest);
                lostText.put("content",contentRequest);
                lostText.put("lostTime",lostTimeRequest);
                lostText.put("lostPlace",lostPlaceRequest);
                lostText.put("contact",contactRequest);
                lostText.put("classify",classifyRequest);
                ZLoadingDialog zLoadingDialog=LoadingdialogUtil.getZLoadingDialog(AddLostActivity.this);
                RetrofitUtil.postAddLost(AddLostActivity.this,lostText,getImgList(pictures),zLoadingDialog);
            }
        });

        //选择板块按钮
        foundClassifyButton.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v){
                classify=1;
                foundClassifyButton.setTextColor(getContext().getResources().getColor(R.color.basicColor));
                foundClassifyButton.setTextSize(15);
                lostClassifyButton.setTextColor(getContext().getResources().getColor(R.color.qmui_config_color_gray_3));
                lostClassifyButton.setTextSize(12);
            }
        });

        lostClassifyButton.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v){
                classify=0;
                lostClassifyButton.setTextColor(getContext().getResources().getColor(R.color.basicColor));
                lostClassifyButton.setTextSize(15);
                foundClassifyButton.setTextColor(getContext().getResources().getColor(R.color.qmui_config_color_gray_3));
                foundClassifyButton.setTextSize(12);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                pictures=Matisse.obtainResult(data);
                addPictureAdapter.notifyDataSetChanged(pictures);
            }
        }
    }

    //是否有文本为空
    private void isNullText(TextInputLayout... textInputLayouts){
        boolean enabled=true;
        for(TextInputLayout i: textInputLayouts){
            if(i.getEditText().getText().toString().length()==0){
                addButton.setEnabled(false);
                enabled=false;
                break;
            }
        }
        addButton.setEnabled(enabled);
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
            MultipartBody.Part finalRequest=MultipartBody.Part.createFormData("pictures[]",file.getName(),requestBody);//pictures[]为后端的key
            result.add(finalRequest);
        }
        return result;
    }

}
