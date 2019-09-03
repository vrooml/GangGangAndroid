package com.example.huge.fzugang.CarpoolBlock;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huge.fzugang.CarpoolBlock.FromJSON.CarpoolListData;
import com.example.huge.fzugang.CarpoolBlock.FromJSON.CarpoolListResponseData;
import com.example.huge.fzugang.CarpoolBlock.ToJSON.CarpoolDeleteMyPost;
import com.example.huge.fzugang.CarpoolBlock.ToJSON.CarpoolListPost;
import com.example.huge.fzugang.CarpoolBlock.ToJSON.CarpoolMyListPost;
import com.example.huge.fzugang.MainActivity;
import com.example.huge.fzugang.MyPostActivity;
import com.example.huge.fzugang.MainActivity;
import com.example.huge.fzugang.R;
import com.example.huge.fzugang.Utils.OkHttpUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.huge.fzugang.Utils.constantUtil.SUCCESS_CODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarpoolFragment extends Fragment {


    TextView title;
    String urlSfxOfList = "fdb1.0.0/carpooling/list";
    String urlSfxOfMe ="fdb1.0.0/carpooling/del";
    String token;
    Unbinder unbinder;
    private CarpoolRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    CarpoolListPost carpoolListPost;
    CarpoolMyListPost carpoolMyListPost;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carpool, container, false);
        if(getActivity().getClass().equals(MainActivity.class)) {
            title = getActivity().findViewById(R.id.block_title);
            title.setText("拼车服务");
        }

        carpoolListPost = new CarpoolListPost();

        token=SharedPreferencesUtil.getStoredMessage(getContext(), "token");

        carpoolListPost.setToken(token);
        carpoolListPost.setPage("1");
        carpoolListPost.setNum("10");

        carpoolMyListPost=new CarpoolMyListPost();
        carpoolMyListPost.setToken(SharedPreferencesUtil.getStoredMessage(getContext(),"token"));

        final SmartRefreshLayout refreshLayout=(SmartRefreshLayout) view.findViewById(R.id.carpool_refresh_layout);
        refreshLayout.setRefreshHeader(new TaurusHeader(getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (getActivity().getClass().equals(MainActivity.class)) {
                    sendRequestWithOkHttp(urlSfxOfList, generateListPost(carpoolListPost));
                }else {
                    sendRequestWithOkHttp(urlSfxOfMe,generateMyListPost(carpoolMyListPost));
                }
                refreshLayout.finishRefresh(1000/*,false*/);
            }
        });
        refreshLayout.autoRefresh();

        recyclerView = (RecyclerView) view.findViewById(R.id.carpool_main_activity_recycler_view);
        layoutManager = new GridLayoutManager(inflater.getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String postJSON;
//        Log.d("listpost", "onCreateView: " + postJSON);
        if(getActivity().getClass().equals(MainActivity.class)) {
            postJSON = generateListPost(carpoolListPost);
            sendRequestWithOkHttp(urlSfxOfList,postJSON);
        }else if (getActivity().getClass().equals(MyPostActivity.class)){
            postJSON=generateMyListPost(carpoolMyListPost);
            sendRequestWithOkHttp(urlSfxOfMe,postJSON);
        }

    }

    private String generateMyListPost(CarpoolMyListPost carpoolMyListPost) {
        Gson gson=new Gson();
        return gson.toJson(carpoolMyListPost);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private String generateListPost(CarpoolListPost carpoolListPost) {
        Gson gson = new Gson();
        return gson.toJson(carpoolListPost);
    }

    private void sendRequestWithOkHttp(String urlSfx,String postJSON) {
        Log.d("okhttp", "sendRequestWithOkHttp: " + postJSON);
            OkHttpUtil.sendOkHttpRequeat(urlSfx, postJSON, new Callback() {
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                    String listResponse = response.body().string();

                    Log.d("response", "onResponse: " + listResponse);

                    CarpoolListResponseData carpoolListResponseData;
                    carpoolListResponseData = parseJSOBWithGSON(listResponse);
                    if (carpoolListResponseData.getCode() == SUCCESS_CODE) {

                        Log.d("listresponse", "onResponse: " + listResponse);

                        final List<CarpoolListData> carpoolListData = carpoolListResponseData.getData();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new CarpoolRecyclerViewAdapter(carpoolListData);
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    } else {
                        showToast("返回异常,错误代码:  " + carpoolListResponseData.getCode());
                    }
                }

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    showToast("请求异常,请检查网络");
                }
            });

    }

    private CarpoolListResponseData parseJSOBWithGSON(String responseData) {
        Gson gson = new Gson();
        return gson.fromJson(responseData, CarpoolListResponseData.class);

    }

    private void showToast(final String s) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
