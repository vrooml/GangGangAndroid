package com.example.huge.fzugang.TradeBlock;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.huge.fzugang.MyApplication;
import com.example.huge.fzugang.R;
import com.example.huge.fzugang.Utils.RetrofitUtil;
import com.example.huge.fzugang.RetrofitStuff.TradeListRequest;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.util.ArrayList;

public class TradeFragment extends Fragment{
    TextView title;
    @BindView(R.id.trade_refresh_layout)
    RefreshLayout refreshLayout;
    @BindView(R.id.trade_list_view)
    ListView tradeListView;
    TradeListAdapter tradeListAdapter;
    public static ArrayList<TradeInfo> tradeData;
    public static ZLoadingDialog zLoadingDialog;
    public static int page;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_trade,container,false);
        ButterKnife.bind(this,view);

        init();
        initRefresh();
        return view;
    }

    private void init(){
        page=1;
        tradeData=new ArrayList<TradeInfo>();
        tradeListAdapter=new TradeListAdapter(this.getContext(),tradeData);
        tradeListView.setAdapter(tradeListAdapter);
        title=getActivity().findViewById(R.id.block_title);
        title.setText("二手交易");
        TradeListRequest tradeListRequest=new TradeListRequest(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),String.valueOf(page),"10");
        RetrofitUtil.postTradeList(tradeListRequest,zLoadingDialog);

    }

    private void initRefresh(){
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=1;
                TradeListRequest tradeListRequest=new TradeListRequest(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),String.valueOf(page),"10");
                Log.d("debug","onRefresh: token   "+SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"));
                RetrofitUtil.postTradeList(tradeListRequest,zLoadingDialog);
                tradeListAdapter.notifyDataSetChanged();
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                TradeListRequest tradeListRequest=new TradeListRequest(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),String.valueOf(++page),"10");
                RetrofitUtil.postTradeList(tradeListRequest,zLoadingDialog);
                tradeListAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
            }
        });
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }
}
