package com.example.huge.fzugang.TradeBlock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.huge.fzugang.R;
import com.example.huge.fzugang.Utils.LoadingdialogUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class TradeSearchActivity extends AppCompatActivity{
   private Toolbar toolbar;
    public static ArrayList<TradeInfo> data;
    public static ListView searchList;
    public static TradeListAdapter tradeListAdapter;
    @BindView(R.id.search_editText)
    EditText searchEditText;
    @BindView(R.id.search_button)
    Button searchButton;
    public static TextView title;

    public static int page=1;
    public static int classify=-1;
    public static String keyWord="";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //设置toolbar
        toolbar=(Toolbar)findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        title=findViewById(R.id.toolbar_title);

        //设置list
        searchList=findViewById(R.id.search_listview);
        data=new ArrayList<>();
        tradeListAdapter=new TradeListAdapter(this,data);
        searchList.setAdapter(tradeListAdapter);

        searchEditText.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence,int i,int i1,int i2){

            }

            @Override
            public void onTextChanged(CharSequence charSequence,int i,int i1,int i2){

            }

            @Override
            public void afterTextChanged(Editable editable){
                try{
                    keyWord=URLEncoder.encode(searchEditText.getText().toString().trim(),"utf-8");
                }catch(UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(keyWord.equals("")){
                    Toast.makeText(searchList.getContext(),"查询词不能为空哦~",Toast.LENGTH_SHORT).show();
                }else{
                    data.clear();
                    String token=SharedPreferencesUtil.getStoredMessage(getApplicationContext(),"token");
                    ZLoadingDialog zLoadingDialog=LoadingdialogUtil.getZLoadingDialog(searchList.getContext());
//                    PostSearchRequest postSearchRequest=new PostSearchRequest(token,keyWord,String.valueOf(classify),"1");
//                    RetrofitUtil.postSearchPost(searchList.getContext(),postSearchRequest,data,zLoadingDialog);
                }
            }
        });
    }

    /**
     * 监听返回按键的事件处理
     *
     * @param keyCode 点击事件的代码
     * @param event   事件
     * @return 有无处理
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            page=1;
            tradeListAdapter=null;
            this.finish();
        }

        return true;
    }

}
