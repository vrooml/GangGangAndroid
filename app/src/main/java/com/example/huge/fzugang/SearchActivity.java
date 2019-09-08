package com.example.huge.fzugang;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.huge.fzugang.CarpoolBlock.CarpoolListAdapter;
import com.example.huge.fzugang.CarpoolBlock.CarpoolInfo;
import com.example.huge.fzugang.LostBlock.LostInfo;
import com.example.huge.fzugang.LostBlock.LostListAdapter;
import com.example.huge.fzugang.RetrofitStuff.PostSearchRequest;
import com.example.huge.fzugang.TradeBlock.TradeInfo;
import com.example.huge.fzugang.TradeBlock.TradeListAdapter;
import com.example.huge.fzugang.Utils.LoadingdialogUtil;
import com.example.huge.fzugang.Utils.RetrofitUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.util.ArrayList;

public class SearchActivity extends BaseActivity{
   private Toolbar toolbar;
    public static ArrayList<TradeInfo> tradeData;
    public static ArrayList<LostInfo> lostData;
    public static ArrayList<CarpoolInfo> carpoolData;
    public static ListView searchList;
    public static TradeListAdapter tradeListAdapter;
    public static LostListAdapter lostListAdapter;
    public static CarpoolListAdapter carpoolListAdapter;
    @BindView(R.id.search_refresh_layout)
    RefreshLayout refreshLayout;
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
        initRefresh();

    }

    private void init(){
        page=1;
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
                String token=SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token");
                ZLoadingDialog zLoadingDialog=LoadingdialogUtil.getZLoadingDialog(searchList.getContext());
                if(keyWord.equals("")){
                    Toast.makeText(searchList.getContext(),"查询词不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    if(block==0){
                        PostSearchRequest postSearchRequest=new PostSearchRequest(token,String.valueOf(page),keyWord);
                        RetrofitUtil.postSearchLost(postSearchRequest,zLoadingDialog);
                    }else if(block==1){
                        PostSearchRequest postSearchRequest=new PostSearchRequest(token,String.valueOf(page),keyWord);
                        RetrofitUtil.postSearchTrade(postSearchRequest,zLoadingDialog);
                    }else if(block==2){
                        PostSearchRequest postSearchRequest=new PostSearchRequest(token,String.valueOf(page),keyWord);
                        RetrofitUtil.postSearchCarpool(postSearchRequest,zLoadingDialog);
                    }
                }
            }
        });
    }

    private void initRefresh(){
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                String token=SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token");
                ZLoadingDialog zLoadingDialog=LoadingdialogUtil.getZLoadingDialog(searchList.getContext());
                if(block==0){
                    PostSearchRequest postSearchRequest=new PostSearchRequest(token,String.valueOf(page),keyWord);
                    RetrofitUtil.postSearchLost(postSearchRequest,zLoadingDialog);
                }else if(block==1){
                    PostSearchRequest postSearchRequest=new PostSearchRequest(token,String.valueOf(page),keyWord);
                    RetrofitUtil.postSearchTrade(postSearchRequest,zLoadingDialog);
                }else if(block==2){
                    PostSearchRequest postSearchRequest=new PostSearchRequest(token,String.valueOf(page),keyWord);
                    RetrofitUtil.postSearchCarpool(postSearchRequest,zLoadingDialog);
                }
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                String token=SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token");
                ZLoadingDialog zLoadingDialog=LoadingdialogUtil.getZLoadingDialog(searchList.getContext());
                if(block==0){
                    PostSearchRequest postSearchRequest=new PostSearchRequest(token,String.valueOf(++page),keyWord);
                    RetrofitUtil.postSearchLost(postSearchRequest,zLoadingDialog);
                }else if(block==1){
                    PostSearchRequest postSearchRequest=new PostSearchRequest(token,String.valueOf(++page),keyWord);
                    RetrofitUtil.postSearchTrade(postSearchRequest,zLoadingDialog);
                }else if(block==2){
                    PostSearchRequest postSearchRequest=new PostSearchRequest(token,String.valueOf(++page),keyWord);
                    RetrofitUtil.postSearchCarpool(postSearchRequest,zLoadingDialog);
                }
                refreshlayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
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
            this.finish();
        }

        return true;
    }

}
