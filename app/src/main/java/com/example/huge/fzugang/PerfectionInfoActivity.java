package com.example.huge.fzugang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.ButterKnife;
import com.example.huge.fzugang.Utils.BarcolorUtil;

public class PerfectionInfoActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfection_info);

        ButterKnife.bind(this);
        init();
    }

    private void init(){

    }
}
