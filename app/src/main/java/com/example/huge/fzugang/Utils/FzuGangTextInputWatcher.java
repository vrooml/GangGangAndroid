package com.example.huge.fzugang.Utils;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class FzuGangTextInputWatcher implements TextWatcher{

    private Context context;
    private Button button;
    private TextInputLayout[] textInputLayouts;

    public FzuGangTextInputWatcher(Context context,Button button,TextInputLayout... textInputLayouts){
        this.context=context;
        this.button=button;
        this.textInputLayouts=textInputLayouts;
    }

    @Override
    public void beforeTextChanged(CharSequence s,int start,int count,int after){
    }

    @Override
    public void onTextChanged(CharSequence s,int start,int before,int count){
    }

    @Override
    public void afterTextChanged(Editable s){
        boolean enabled=true;
        for(TextInputLayout i: textInputLayouts){
            if(i.getEditText().getText().toString().length()==0){
                button.setEnabled(false);
                enabled=false;
                i.setError("请输入内容");
                i.setErrorEnabled(true);
            }else{
                i.setErrorEnabled(false);
            }
        }

        if(enabled){
            button.setEnabled(true);
        }else{
            button.setEnabled(false);
        }
    }


}