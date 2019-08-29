package com.example.huge.fzugang.LostBlock;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cc.shinichi.library.ImagePreview;
import com.bumptech.glide.Glide;
import com.example.huge.fzugang.R;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.huge.fzugang.Utils.constantUtil.BaseUrl;

public class LostDetailActivity extends AppCompatActivity{
    @BindView(R.id.lost_detail_banner)
    Banner banner;
    @BindView(R.id.lost_detail_title)
    TextView title;
    @BindView(R.id.lost_detail_lost_time)
    TextView lostTime;
    @BindView(R.id.lost_detail_lost_place)
    TextView lostPlace;
    @BindView(R.id.lost_detail_avatar)
    CircleImageView avatar;
    @BindView(R.id.lost_detail_username)
    TextView username;
    @BindView(R.id.lost_detail_post_time)
    TextView postTime;
    @BindView(R.id.lost_detail_content)
    TextView content;
    @BindView(R.id.lost_detail_contact)
    TextView contact;
    LostInfo lostInfo;
    int position;//传入数据位置
    String avatarUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_detail);
        ButterKnife.bind(this);
        init();
        initBanner();

    }

    private void init(){
        position=(int)getIntent().getSerializableExtra("position");
        lostInfo=LostFragment.lostData.get(position);
        title.setText(lostInfo.getTitle());
        lostPlace.setText(lostInfo.getLostPlace());
        lostTime.setText(lostInfo.getLostTime());
        postTime.setText(lostInfo.getPostTime());
        content.setText(lostInfo.getContent());
        username.setText(lostInfo.getUsername());
        contact.setText(lostInfo.getContact());
        try{
            avatarUrl=BaseUrl+"/fdb1.0.0/user/download/avatar/id/"+lostInfo.getUserId();
            Glide.with(LostDetailActivity.this).load(avatarUrl).into(avatar);
        }catch(Exception e){
        }
    }

    private void initBanner(){
        banner.setImageLoader(new GlideImageLoader())
                .setImages(lostInfo.getPictureUrls())
                .setOnBannerListener(new OnBannerListener(){
                    @Override
                    public void OnBannerClick(int position){
                        ImagePreview.getInstance()
                                .setContext(LostDetailActivity.this)
                                .setIndex(0)
                                .setImageList(lostInfo.getPictureUrls())
                                .start();
                    }
                })
                .isAutoPlay(false)
                .start();
    }



    private class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            Glide.with(context)
                    .load(path)
                    .placeholder(R.drawable.default_picture)
                    .into(imageView);
        }

    }

}
