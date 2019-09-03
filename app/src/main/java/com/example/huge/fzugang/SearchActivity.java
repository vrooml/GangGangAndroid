package com.example.huge.fzugang;

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
import com.example.huge.fzugang.LostBlock.LostInfo;
import com.example.huge.fzugang.LostBlock.LostListAdapter;
import com.example.huge.fzugang.MyApplication;
import com.example.huge.fzugang.R;
import com.example.huge.fzugang.TradeBlock.TradeInfo;
import com.example.huge.fzugang.TradeBlock.TradeListAdapter;
import com.example.huge.fzugang.Utils.LoadingdialogUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity{
   private Toolbar toolbar;
    public static ArrayList<TradeInfo> tradeData;
    public static ArrayList<LostInfo> lostData;
    public static ArrayList<CarpoolInfo> carpoolData;
    public static ListView searchList;
    public static TradeListAdapter tradeListAdapter;
    public static LostListAdapter lostListAdapter;
    public static CarpoolAdapter carpoolListAdapter;
    public static
    @BindView(R.id.search_editText)
    EditText searchEditText;
    @BindView(R.id.search_button)
    Button searchButton;
    @BindView(R.id.search_block_title)
    TextView title;

    public static int page=1;
    public static String keyWord="";
    private int block;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //设置toolbar
        toolbar=(Toolbar)findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        //获取来源版块
        block=Integer.parseInt(getIntent().getSerializableExtra("sourceFragment").toString());
        init();
    }

    private void init(){
        searchList=findViewById(R.id.search_listview);
        //设置标题
        if(block==0){
            title.setText("失物招领");
        }else if(block==1){
            title.setText("二手交易");
        }else if(block==2){
            title.setText("拼车服务");
        }
        //设置list
        if(block==0){
            lostData=new ArrayList<>();
            lostListAdapter=new LostListAdapter(this,lostData);
            searchList.setAdapter(lostListAdapter);
        }else if(block==1){
            tradeData=new ArrayList<>();
            tradeListAdapter=new TradeListAdapter(this,tradeData);
            searchList.setAdapter(tradeListAdapter);
        }else if(block==2){
            carpoolData=new ArrayList<>();
            carpoolListAdapter=new CarpoolListAdapter(this,carpoolData);
            searchList.setAdapter(carpoolListAdapter);
        }

        searchEditText.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence,int i,int i1,int i2){

            }

            @Override
            public void onTextChanged(CharSequence charSequence,int i,int i1,int i2){

            }

            @Override
            public void afterTextChanged(Editable editable){
                keyWord=searchEditText.getText().toString();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(keyWord.equals("")){
                    Toast.makeText(searchList.getContext(),"查询词不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    if(block==0){
                        lostData.clear();
                    }else if(block==1){
                        tradeData.clear();
                    }else if(block==2){
                        carpoolData.clear();
                    }
                    String token=SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token");
                    ZLoadingDialog zLoadingDialog=LoadingdialogUtil.getZLoadingDialog(searchList.getContext());
                    PostSearchRequest postSearchRequest=new PostSearchRequest(token,keyWord,String.valueOf(classify),String.valueOf(page));
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
