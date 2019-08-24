package com.example.huge.fzugang.Utils;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.huge.fzugang.R;

public class FzuGangEditTextWatcher implements TextWatcher{

    private Context context;
    private Button button;
    private EditText[] editTexts;

    public FzuGangEditTextWatcher(Context context,Button button,EditText... editTexts){
        this.context=context;
        this.button=button;
        this.editTexts=editTexts;
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
        for(EditText i:editTexts){
            if(i.getText().toString().length()==0){
                button.setEnabled(false);
                enabled=false;
            }
        }
        if(enabled){
            button.setEnabled(true);
        }else{
            button.setEnabled(false);
        }
    }
}