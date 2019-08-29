package com.example.huge.fzugang.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import com.example.huge.fzugang.R;

import static com.example.huge.fzugang.R.color.qmui_config_color_white;

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
        for(TextInputLayout i: textInputLayouts){
            i.getEditText().getBackground().clearColorFilter();
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onTextChanged(CharSequence s,int start,int before,int count){
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
            i.getEditText().getBackground().clearColorFilter();
        }

        if(enabled){
            button.setEnabled(true);
        }else{
            button.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s){
        for(TextInputLayout i: textInputLayouts){
            i.getEditText().getBackground().clearColorFilter();
        }
    }


}