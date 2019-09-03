package com.example.huge.fzugang.CarpoolBlock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huge.fzugang.CarpoolBlock.FromJSON.CarpoolListData;
import com.example.huge.fzugang.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarpoolDetailActivity extends AppCompatActivity {

    CarpoolListData carpoolListData;

    @BindView(R.id.block_title)
    TextView blockTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.carpool_main_activity_meeting_place)
    TextView carpoolMainActivityMeetingPlace;
    @BindView(R.id.carpool_main_activity_date)
    TextView carpoolMainActivityDate;
    @BindView(R.id.carpool_card_view_arrow)
    ImageView carpoolCardViewArrow;
    @BindView(R.id.carpool_main_activity_num_of_people)
    TextView carpoolMainActivityNumOfPeople;
    @BindView(R.id.carpool_main_activity_destination)
    TextView carpoolMainActivityDestination;
    @BindView(R.id.carpool_main_activity_time)
    TextView carpoolMainActivityTime;
    @BindView(R.id.carpool_main_activity_user_name)
    TextView carpoolMainActivityUserName;
    @BindView(R.id.carpool_main_activity_price)
    TextView carpoolMainActivityPrice;
    @BindView(R.id.carpool_detail_content)
    TextView carpoolDetailContent;
    @BindView(R.id.carpool_card_post_date)
    TextView carpoolCardPostDate;
    @BindView(R.id.carpool_card_post_time)
    TextView carpoolCardPostTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpool_detail);
        ButterKnife.bind(this);

        carpoolListData = (CarpoolListData) getIntent()
                .getSerializableExtra("carpool_list_data");

        init();

    }

    private void init() {
        carpoolMainActivityMeetingPlace.setText(carpoolListData.getMeetPlace());
        carpoolMainActivityDate.setText("日期: " + carpoolListData.getDate());
        carpoolMainActivityTime.setText("时间: " + carpoolListData.getTime());
        carpoolMainActivityDestination.setText(carpoolListData.getDestination());
        carpoolMainActivityNumOfPeople.setText(carpoolListData.getNumOfPeople() + "人待拼");
        carpoolMainActivityUserName.setText(carpoolListData.getAuthor());
        carpoolMainActivityPrice.setText(carpoolListData.getPrice());
        carpoolCardPostDate.setText("创建时间: " + carpoolListData.getPostDate());
        carpoolCardPostTime.setText(carpoolListData.getPostTime());


        carpoolDetailContent.setText(carpoolListData.getContent());
    }
}
