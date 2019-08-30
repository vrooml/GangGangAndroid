package com.example.huge.fzugang.CarpoolBlock;


import android.os.Bundle;
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
import com.example.huge.fzugang.CarpoolBlock.ToJSON.CarpoolListPost;
import com.example.huge.fzugang.R;
import com.example.huge.fzugang.Utils.LoadingdialogUtil;
import com.example.huge.fzugang.Utils.OkHttpUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.zyao89.view.zloading.ZLoadingDialog;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.huge.fzugang.Utils.constantUtil.SUCCESS_CODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarpoolFragment extends Fragment{



    TextView title;
    String urlSfix="fdb1.0.0/carpooling/list";
    String responseData;
    private CarpoolRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    ZLoadingDialog zLoadingDialog;
    GridLayoutManager layoutManager;
    CarpoolListPost carpoolListPost;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carpool, container, false);
        title=getActivity().findViewById(R.id.block_title);
        title.setText("拼车服务");

        carpoolListPost=new CarpoolListPost();

        carpoolListPost.setToken(SharedPreferencesUtil.getStoredMessage(getContext(),"token"));
        carpoolListPost.setPage("1");
        carpoolListPost.setNum("10");

        zLoadingDialog= LoadingdialogUtil.getZLoadingDialog(getContext());

        recyclerView=(RecyclerView)view.findViewById(R.id.carpool_main_activity_recycler_view);
        layoutManager=new GridLayoutManager(inflater.getContext(),1);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String postJSON=generatePost(carpoolListPost);
        Log.d("listpost", "onCreateView: "+postJSON);
        sendRequestWithOkHttp(postJSON);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private String generatePost(CarpoolListPost carpoolListPost) {
        Gson gson = new Gson();
        return gson.toJson(carpoolListPost);
    }

    private void sendRequestWithOkHttp(String postJSON) {

        Log.d("okhttp", "sendRequestWithOkHttp: "+postJSON);
        OkHttpUtil.sendOkHttpRequeat(urlSfix,postJSON,new okhttp3.Callback(){
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String listResponse=response.body().string();

                CarpoolListResponseData carpoolListResponseData;
                carpoolListResponseData = parseJSOBWithGSON(listResponse);
                if (carpoolListResponseData.getCode()==SUCCESS_CODE) {

                    Log.d("listresponse", "onResponse: "+listResponse);

                    final List<CarpoolListData> carpoolListData = carpoolListResponseData.getData();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new CarpoolRecyclerViewAdapter(carpoolListData);
                            recyclerView.setAdapter(adapter);
                            zLoadingDialog.dismiss();
                        }
                    });
                }else {
                    showToast("返回异常,错误代码:  "+carpoolListResponseData.getCode());
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                showToast("请求异常,请检查网络");
            }
        });
    }

    private CarpoolListResponseData parseJSOBWithGSON(String responseData) {
        Gson gson=new Gson();
        return gson.fromJson(responseData, CarpoolListResponseData.class);

    }

    private void showToast(final String s) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(),s, Toast.LENGTH_SHORT).show();
                zLoadingDialog.dismiss();
            }
        });
    }
}
