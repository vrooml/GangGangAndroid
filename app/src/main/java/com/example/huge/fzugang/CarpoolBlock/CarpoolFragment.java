package com.example.huge.fzugang.CarpoolBlock;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.huge.fzugang.RetrofitStuff.CarpoolListRequest;
import com.example.huge.fzugang.MainActivity;
import com.example.huge.fzugang.MyApplication;
import com.example.huge.fzugang.R;
import com.example.huge.fzugang.Utils.RetrofitUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.util.ArrayList;


public class CarpoolFragment extends Fragment {
    TextView title;
    public static RefreshLayout refreshLayout;
    @BindView(R.id.carpool_list_view)
    ListView carpoolListView;
    public static CarpoolListAdapter carpoolListAdapter;
    public static ArrayList<CarpoolInfo> carpoolData=new ArrayList<CarpoolInfo>();;
    public static ZLoadingDialog zLoadingDialog;
    public static int page=1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_carpool,container,false);
        ButterKnife.bind(this,view);
        refreshLayout=view.findViewById(R.id.carpool_refresh_layout);
        init();
        initRefresh();
        return view;
    }

    private void init(){
        carpoolListAdapter=new CarpoolListAdapter(this.getActivity(),carpoolData);
        carpoolListView.setAdapter(carpoolListAdapter);
        if(this.getActivity().getClass().equals(MainActivity.class)){
            title=getActivity().findViewById(R.id.block_title);
            title.setText("拼车服务");
        }
        CarpoolListRequest carpoolListRequest=new CarpoolListRequest(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),"1","10");
        //根据调用位置判断请求方式
//        if(this.getActivity().getClass().equals(MainActivity.class)){
//            RetrofitUtil.postCarpoolList(CarpoolFragment.this,carpoolListRequest,zLoadingDialog);
//        }else{
//            RetrofitUtil.postMyCarpoolList(CarpoolFragment.this,carpoolListRequest,zLoadingDialog);
//        }
//        carpoolListAdapter.notifyDataSetChanged();
    }

    private void initRefresh(){
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=1;
                carpoolData.clear();
                CarpoolListRequest carpoolListRequest=new CarpoolListRequest(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),String.valueOf(page),"10");
                //根据调用位置判断请求方式
                if(CarpoolFragment.this.getActivity().getClass().equals(MainActivity.class)){
                    RetrofitUtil.postCarpoolList(carpoolListRequest,zLoadingDialog);
                }else{
                    RetrofitUtil.postMyCarpoolList(carpoolListRequest,zLoadingDialog);
                }
                carpoolListAdapter.notifyDataSetChanged();
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                CarpoolListRequest carpoolListRequest=new CarpoolListRequest(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),String.valueOf(++page),"10");
                //根据调用位置判断请求方式
                if(CarpoolFragment.this.getActivity().equals(MainActivity.class)){
                    RetrofitUtil.postCarpoolList(carpoolListRequest,zLoadingDialog);
                }else{
                    RetrofitUtil.postMyCarpoolList(carpoolListRequest,zLoadingDialog);
                }
                carpoolListAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
            }
        });

        refreshLayout.autoRefresh();
        carpoolListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }
}
