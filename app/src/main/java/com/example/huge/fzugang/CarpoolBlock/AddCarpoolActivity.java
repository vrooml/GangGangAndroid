package com.example.huge.fzugang.CarpoolBlock;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.huge.fzugang.*;
import com.example.huge.fzugang.RetrofitStuff.AddCarpoolRequest;
import com.example.huge.fzugang.Utils.LoadingdialogUtil;
import com.example.huge.fzugang.Utils.RetrofitUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zyao89.view.zloading.ZLoadingDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class AddCarpoolActivity extends BaseActivity{
    @BindView(R.id.add_carpool_post_button)
    Button addButton;
    @BindView(R.id.add_carpool_start)
    TextInputLayout startPlace;
    @BindView(R.id.add_carpool_destination)
    TextInputLayout destination;
    @BindView(R.id.add_carpool_price)
    TextInputLayout price;
    @BindView(R.id.add_carpool_contact)
    TextInputLayout contact;
    @BindView(R.id.add_carpool_content)
    TextInputLayout content;

    public static String date;
    public static String time;
    public static String peopleNum;
    private ArrayList<String> peopleNumItems=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_carpool);

        ButterKnife.bind(this);
        init();
    }

    private void init(){
        //初始化人数选择器
        peopleNumItems.add("1人");
        peopleNumItems.add("2人");
        peopleNumItems.add("3人");
        peopleNumItems.add("4人");
        peopleNumItems.add("5人");
        peopleNumItems.add("6人");
        peopleNumItems.add("7人");
        peopleNumItems.add("8人");
        peopleNumItems.add("9人");

        //先让按钮失效
        addButton.setEnabled(false);
        //设置文本框改变监听
        startPlace.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
            }

            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
                if(startPlace.getEditText().getText().toString().length()==0){
                    startPlace.setError("请输入内容");
                    startPlace.setErrorEnabled(true);
                }else{
                    startPlace.setErrorEnabled(false);
                }
                startPlace.getEditText().getBackground().clearColorFilter();
                isNullText(startPlace,destination,price,content,contact);
            }

            @Override
            public void afterTextChanged(Editable s){
            }
        });
        destination.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
                destination.getEditText().getBackground().clearColorFilter();
            }

            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
                if(destination.getEditText().getText().toString().length()==0){
                    destination.setError("请输入内容");
                    destination.setErrorEnabled(true);
                }else{
                    destination.setErrorEnabled(false);
                }
                destination.getEditText().getBackground().clearColorFilter();
                isNullText(startPlace,destination,price,content,contact);
            }

            @Override
            public void afterTextChanged(Editable s){
            }
        });
        price.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
            }

            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
                if(price.getEditText().getText().toString().length()==0){
                    price.setError("请输入内容");
                    price.setErrorEnabled(true);
                }else{
                    price.setErrorEnabled(false);
                }
                price.getEditText().getBackground().clearColorFilter();
                isNullText(startPlace,destination,price,content,contact);
            }

            @Override
            public void afterTextChanged(Editable s){
            }
        });
        content.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){
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
                isNullText(startPlace,destination,price,content,contact);
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
                isNullText(startPlace,destination,price,content,contact);
            }

            @Override
            public void afterTextChanged(Editable s){
            }
        });

        //初始化选择框
        QMUIGroupListView selector=findViewById(R.id.add_carpool_select);
        QMUICommonListItemView dateItem=selector.createItemView("出发日期");
        dateItem.setDetailText("选择日期");
        dateItem.getTextView().setTextSize(14);
        dateItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView timeItem=selector.createItemView("出发时间");
        timeItem.setDetailText("选择时间");
        timeItem.getTextView().setTextSize(14);
        timeItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView numOfPeopleItem=selector.createItemView("拼车人数");
        numOfPeopleItem.setDetailText("选择人数");
        numOfPeopleItem.getTextView().setTextSize(14);
        numOfPeopleItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        View.OnClickListener onClickListener=new View.OnClickListener(){
            @Override
            public void onClick(View view){
                QMUICommonListItemView v=(QMUICommonListItemView)view;
                switch(v.getText().toString()){
                    case "出发日期":
                        showTimePicker(v,new boolean[]{true,true,true,false,false,false});
                        break;
                    case "出发时间":
                        showTimePicker(v,new boolean[]{false,false,false,true,true,false});
                        break;
                    case "拼车人数":
                        showPeopleNumPicker(v);
                        break;
                }
            }

        };

        QMUIGroupListView.newSection(AddCarpoolActivity.this)
                .addItemView(dateItem, onClickListener)
                .addItemView(timeItem, onClickListener)
                .addItemView(numOfPeopleItem, onClickListener)
                .addTo(selector);

        //发布按钮监听
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String token=SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token");
                ZLoadingDialog zLoadingDialog=LoadingdialogUtil.getZLoadingDialog(AddCarpoolActivity.this);
                AddCarpoolRequest addCarpoolRequest=new AddCarpoolRequest(token,
                        date,
                        time,
                        content.getEditText().getText().toString(),
                        destination.getEditText().getText().toString(),
                        startPlace.getEditText().getText().toString(),
                        price.getEditText().getText().toString(),
                        peopleNum.substring(0,peopleNum.length()-1),
                        contact.getEditText().getText().toString());
                RetrofitUtil.postAddCarpool(AddCarpoolActivity.this,addCarpoolRequest,zLoadingDialog);
            }
        });
    }


    private void showTimePicker(final QMUICommonListItemView listItemView,final boolean[] pickerType){
        TimePickerView timePickerView=new TimePickerBuilder(AddCarpoolActivity.this,new OnTimeSelectListener(){
            @Override
            public void onTimeSelect(Date date,View v){
                SimpleDateFormat dateFormat;
                String result;
                String postResult;
                if(pickerType[0]==true){
                    dateFormat=new SimpleDateFormat("yyyy.MM.dd");
                    result=SimpleDateFormat.getDateInstance().format(date);
                    postResult=dateFormat.format(date);
                    AddCarpoolActivity.date=postResult;
                    isNullText(startPlace,destination,price,content,contact);


                }else{
                    dateFormat=new SimpleDateFormat("HH:mm");
                    postResult=dateFormat.format(date);
                    result=postResult;
                    time=postResult;
                    isNullText(startPlace,destination,price,content,contact);
                }
                listItemView.setDetailText(result);
            }
        }).isDialog(true)
                .setBgColor(Color.WHITE)
                .setTitleBgColor(getResources().getColor(R.color.basicColor))
                .setCancelColor(Color.WHITE)
                .setSubmitColor(Color.WHITE)
                .setOutSideColor(0x00000000) //设置外部遮罩颜色
                .setType(pickerType)
                .build();
        timePickerView.setDate(Calendar.getInstance());

        Dialog mDialog=timePickerView.getDialog();
        if(mDialog!=null){

            FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin=0;
            params.rightMargin=0;
            timePickerView.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow=mDialog.getWindow();
            if(dialogWindow!=null){
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.1f);
            }
        }


        timePickerView.show();
    }

    private void showPeopleNumPicker(final QMUICommonListItemView listItemView){
        OptionsPickerView pvOptions=new OptionsPickerBuilder(this,new OnOptionsSelectListener(){
            @Override
            public void onOptionsSelect(int options1,int options2,int options3,View v){
                //返回的是选中位置
                String tx=peopleNumItems.get(options1);
                listItemView.setDetailText(tx);
                peopleNum=tx;
            }
        })
                .setContentTextSize(20)//设置滚轮文字大小
//                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(getResources().getColor(R.color.basicColor))
                .setCancelColor(Color.WHITE)
                .setSubmitColor(Color.WHITE)
//                .setTextColorCenter(Color.LTGRAY)
//                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .setOutSideColor(0x00000000) //设置外部遮罩颜色
                .isDialog(true)
                .build();
        pvOptions.setPicker(peopleNumItems);//一级选择器

        Dialog mDialog=pvOptions.getDialog();
        if(mDialog!=null){

            FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin=0;
            params.rightMargin=0;
            pvOptions.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow=mDialog.getWindow();
            if(dialogWindow!=null){
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.1f);
            }
        }

        pvOptions.show();
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
        if(date==null||time==null||peopleNum==null){
            enabled=false;
        }
        addButton.setEnabled(enabled);
    }

}
