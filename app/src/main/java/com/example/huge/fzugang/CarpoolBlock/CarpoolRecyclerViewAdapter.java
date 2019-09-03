package com.example.huge.fzugang.CarpoolBlock;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huge.fzugang.CarpoolBlock.FromJSON.CarpoolDelResponse;
import com.example.huge.fzugang.CarpoolBlock.FromJSON.CarpoolListData;
import com.example.huge.fzugang.CarpoolBlock.ToJSON.CarpoolDeleteMyPost;
import com.example.huge.fzugang.MyPostActivity;
import com.example.huge.fzugang.R;
import com.example.huge.fzugang.RetrofitStuff.DeletePostRequest;
import com.example.huge.fzugang.Utils.LoadingdialogUtil;
import com.example.huge.fzugang.Utils.OkHttpUtil;
import com.example.huge.fzugang.Utils.RetrofitUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import razerdp.basepopup.QuickPopupBuilder;
import razerdp.basepopup.QuickPopupConfig;
import www.linwg.org.lib.LCardView;

import static com.example.huge.fzugang.MyApplication.getContext;
import static com.example.huge.fzugang.Utils.constantUtil.SUCCESS_CODE;

public class CarpoolRecyclerViewAdapter extends RecyclerView.Adapter<CarpoolRecyclerViewAdapter.ViewHolder> {
    String urlSfxOfDel="fdb1.0.0/carpooling/del";
    private Context mContext;
    private List<CarpoolListData> mCarpoolListData;

    public CarpoolRecyclerViewAdapter(List<CarpoolListData> dataList) {
        mCarpoolListData = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.carpool_card_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Locale locale = Locale.SIMPLIFIED_CHINESE;
        CarpoolListData listData = mCarpoolListData.get(position);
        holder.carpoolMainActivityMeetingPlace.setText(listData.getMeetPlace());
        holder.carpoolMainActivityTime.setText("时间: " + listData.getTime());
        holder.carpoolMainActivityDate.setText("日期: " + listData.getDate());
        holder.carpoolMainActivityNumOfPeople.setText(String.format(locale, "%01d人待拼", listData.getNumOfPeople()));
        holder.carpoolMainActivityDestination.setText(listData.getDestination());
        holder.carpoolMainActivityUserName.setText(listData.getAuthor());
        holder.carpoolMainActivityPrice.setText(String.format(locale, "%s", listData.getPrice()));
        holder.carpoolCardPostDate.setText("创建时间: " + listData.getPostDate());
        holder.carpoolCardPostTime.setText(listData.getPostTime());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CarpoolListData data = mCarpoolListData.get(position);
                Intent intent = new Intent(view.getContext(), CarpoolDetailActivity.class);
                intent.putExtra("carpool_list_data", data);
                view.getContext().startActivity(intent);
            }
        });

        if(holder.itemView.getContext().getClass().equals(MyPostActivity.class)){
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    QuickPopupBuilder.with(getContext())
                            .contentView(R.layout.popup_layout)
                            .config(new QuickPopupConfig()
                                    .blurBackground(true)
                                    .gravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL)
                                    .withClick(R.id.delete_item, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            deleteMyCarpool(mCarpoolListData.get(position).getId(),v);
                                        }

                                        private void deleteMyCarpool(int id, View v){
                                            CarpoolDeleteMyPost carpoolDeleteMyPost=new CarpoolDeleteMyPost();
                                            carpoolDeleteMyPost.setPostId(Integer.toString(id));
                                            carpoolDeleteMyPost.setToken(SharedPreferencesUtil.getStoredMessage(v.getContext(),"token"));
                                            String postJSON=generateDelPost(carpoolDeleteMyPost);
                                            sendRequestWithOkhttpToDel(postJSON);
                                        }

                                        private void sendRequestWithOkhttpToDel(String postJSON) {
                                            OkHttpUtil.sendOkHttpRequeat(urlSfxOfDel, postJSON, new Callback() {
                                                @Override
                                                public void onFailure(Call call, IOException e) {
                                                    Looper.prepare();
                                                    Toast.makeText(getContext(),"请求异常,请检查网络",Toast.LENGTH_SHORT).show();
                                                    Looper.loop();
                                                }

                                                @Override
                                                public void onResponse(Call call, Response response) throws IOException {
                                                    Gson gson=new Gson();
                                                    CarpoolDelResponse carpoolDelResponse=gson.fromJson(response.body().string(),CarpoolDelResponse.class);
                                                    if (carpoolDelResponse.getCode()==SUCCESS_CODE){
                                                        Looper.prepare();
                                                        Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                                                        Looper.loop();
                                                    }else {
                                                        Looper.prepare();
                                                        Toast.makeText(getContext(),"删除失败,错误代码:"+carpoolDelResponse.getCode(),Toast.LENGTH_SHORT).show();
                                                        Looper.loop();
                                                    }

                                                }
                                            });
                                        }

                                        private String generateDelPost(CarpoolDeleteMyPost carpoolDeleteMyPost) {
                                            Gson gson=new Gson();
                                            return gson.toJson(carpoolDeleteMyPost);
                                        }
                                    }))
                            .show(view);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCarpoolListData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LCardView cardView;
        @BindView(R.id.carpool_main_activity_meeting_place)
        TextView carpoolMainActivityMeetingPlace;
        @BindView(R.id.carpool_main_activity_time)
        TextView carpoolMainActivityTime;
        @BindView(R.id.carpool_main_activity_date)
        TextView carpoolMainActivityDate;
        @BindView(R.id.carpool_main_activity_num_of_people)
        TextView carpoolMainActivityNumOfPeople;
        @BindView(R.id.carpool_main_activity_destination)
        TextView carpoolMainActivityDestination;
        @BindView(R.id.carpool_main_activity_user_name)
        TextView carpoolMainActivityUserName;
        @BindView(R.id.carpool_main_activity_price)
        TextView carpoolMainActivityPrice;
        @BindView(R.id.carpool_card_post_date)
        TextView carpoolCardPostDate;
        @BindView(R.id.carpool_card_post_time)
        TextView carpoolCardPostTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardView = (LCardView) itemView;
        }
    }
}
