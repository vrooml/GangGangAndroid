package com.example.huge.fzugang.CarpoolBlock;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.huge.fzugang.CarpoolBlock.FromJSON.CarpoolListData;
import com.example.huge.fzugang.R;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarpoolRecyclerViewAdapter extends RecyclerView.Adapter<CarpoolRecyclerViewAdapter.ViewHolder> {

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Locale locale= Locale.SIMPLIFIED_CHINESE;
        CarpoolListData listData = mCarpoolListData.get(position);
        holder.carpoolMainActivityMeetingPlace.setText(listData.getMeetPlace());
        holder.carpoolMainActivityTime.setText(listData.getTime());
        holder.carpoolMainActivityDate.setText(listData.getDate());
        holder.carpoolMainActivityNumOfPeople.setText(String.format(locale,"%01d人待拼",listData.getNumOfPeople()));
        holder.carpoolMainActivityDestination.setText(listData.getDestination());
        holder.carpoolMainActivityUserName.setText(listData.getAuthor());
        holder.carpoolMainActivityPrice.setText(String.format(locale,"约￥ %s",listData.getPrice()));
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("RV", "onClick: "+holder.getLayoutPosition());
//                CarpoolListData data = mCarpoolListData.get(holder.getPosition());
//                Intent intent = new Intent(mContext, CarpoolReleaseActivity.class);
//                intent.putExtra("carpool_data", data);
//                mContext.startActivity(intent);

//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mCarpoolListData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardView=(CardView)itemView;
        }
    }
}
