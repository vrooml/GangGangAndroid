package com.example.huge.fzugang.LostBlock;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.huge.fzugang.MyApplication;
import com.example.huge.fzugang.R;
import com.example.huge.fzugang.RetrofitStuff.LostListRequest;
import com.example.huge.fzugang.Utils.RetrofitUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.util.ArrayList;

public class LostFragment extends Fragment{
    TextView title;
    @BindView(R.id.lost_refresh_layout)
    RefreshLayout refreshLayout;
    @BindView(R.id.lost_list_view)
    ListView lostListView;
    LostListAdapter lostListAdapter;
    @BindView(R.id.found_list_button)
    Button foundListButton;
    @BindView(R.id.lost_list_button)
    Button lostListButton;
    public static ArrayList<LostInfo> lostData;
    public static ZLoadingDialog zLoadingDialog;
    public static int page;
    public static int classify;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_lost,container,false);
        ButterKnife.bind(this,view);

        init();
        initRefresh();
        return view;
    }

    private void init(){
        //默认请求为拾到物品
        classify=1;
        page=1;
        lostData=new ArrayList<LostInfo>();
        foundListButton.setTextColor(getContext().getResources().getColor(R.color.basicColor));
        foundListButton.setTextSize(18);
        title=getActivity().findViewById(R.id.block_title);
        title.setText("失物招领");
        lostListAdapter=new LostListAdapter(this.getContext(),lostData);
        lostListView.setAdapter(lostListAdapter);
        LostListRequest lostListRequest=new LostListRequest(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),String.valueOf(page),"10",classify);
        RetrofitUtil.postLostList(lostListRequest,zLoadingDialog);

        foundListButton.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v){
                lostData.clear();
                classify=1;
                page=1;
                foundListButton.setTextColor(getContext().getResources().getColor(R.color.basicColor));
                foundListButton.setTextSize(18);
                lostListButton.setTextColor(getContext().getResources().getColor(R.color.qmui_config_color_gray_3));
                lostListButton.setTextSize(15);
                LostListRequest lostListRequest=new LostListRequest(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),String.valueOf(page),"10",classify);
                RetrofitUtil.postLostList(lostListRequest,zLoadingDialog);
            }
        });

        lostListButton.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v){
                lostData.clear();
                classify=0;
                page=1;
                lostListButton.setTextColor(getContext().getResources().getColor(R.color.basicColor));
                lostListButton.setTextSize(18);
                foundListButton.setTextColor(getContext().getResources().getColor(R.color.qmui_config_color_gray_3));
                foundListButton.setTextSize(15);
                LostListRequest lostListRequest=new LostListRequest(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),String.valueOf(page),"10",classify);
                RetrofitUtil.postLostList(lostListRequest,zLoadingDialog);
            }
        });
    }

    private void initRefresh(){
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=1;
                LostListRequest lostListRequest=new LostListRequest(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),String.valueOf(page),"10",classify);
                RetrofitUtil.postLostList(lostListRequest,zLoadingDialog);
                lostListAdapter.notifyDataSetChanged();
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                LostListRequest lostListRequest=new LostListRequest(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),String.valueOf(++page),"10",classify);
                RetrofitUtil.postLostList(lostListRequest,zLoadingDialog);
                lostListAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
            }
        });
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }
}
