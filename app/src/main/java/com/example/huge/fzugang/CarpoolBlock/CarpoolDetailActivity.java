package com.example.huge.fzugang.CarpoolBlock;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cc.shinichi.library.ImagePreview;
import com.bumptech.glide.Glide;
import com.example.huge.fzugang.BaseActivity;
import com.example.huge.fzugang.CarpoolBlock.CarpoolFragment;
import com.example.huge.fzugang.CarpoolBlock.CarpoolInfo;
import com.example.huge.fzugang.R;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.huge.fzugang.Utils.constantUtil.BaseUrl;

public class CarpoolDetailActivity extends BaseActivity{
    @BindView(R.id.carpool_detail_start)
    TextView start;
    @BindView(R.id.carpool_detail_destination)
    TextView destination;
    @BindView(R.id.carpool_detail_date)
    TextView date;
    @BindView(R.id.carpool_detail_time)
    TextView time;
    @BindView(R.id.carpool_detail_price)
    TextView price;
    @BindView(R.id.carpool_detail_people_num)
    TextView peopleNum;
    @BindView(R.id.carpool_detail_avatar)
    CircleImageView avatar;
    @BindView(R.id.carpool_detail_username)
    TextView username;
    @BindView(R.id.carpool_detail_post_date)
    TextView postDate;
    @BindView(R.id.carpool_detail_post_time)
    TextView postTime;
    @BindView(R.id.carpool_detail_content)
    TextView content;
    @BindView(R.id.carpool_detail_contact)
    TextView contact;
    CarpoolInfo carpoolInfo;
    int position;//传入数据位置
    String avatarUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpool_detail);
        ButterKnife.bind(this);
        init();

    }

    private void init(){
        position=(int)getIntent().getSerializableExtra("position");
        carpoolInfo=CarpoolFragment.carpoolData.get(position);
        start.setText(carpoolInfo.getMeetPlace());
        destination.setText(carpoolInfo.getDestination());
        date.setText(carpoolInfo.getDate());
        time.setText(carpoolInfo.getTime());
        price.setText(carpoolInfo.getPrice());
        peopleNum.setText("待拼"+carpoolInfo.getNumOfPeople()+"人");
        postDate.setText(carpoolInfo.getPostDate());
        postTime.setText(carpoolInfo.getPostTime());
        content.setText(carpoolInfo.getContent());
        username.setText(carpoolInfo.getAuthor());
        contact.setText(carpoolInfo.getContact());
        try{
            avatarUrl=BaseUrl+"/fdb1.0.0/user/download/avatar/id/"+carpoolInfo.getUserId();
            Glide.with(CarpoolDetailActivity.this).load(avatarUrl).into(avatar);
        }catch(Exception e){
        }
    }



}
