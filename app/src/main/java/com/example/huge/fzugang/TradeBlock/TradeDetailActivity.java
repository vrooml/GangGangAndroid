package com.example.huge.fzugang.TradeBlock;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class TradeDetailActivity extends AppCompatActivity{
    @BindView(R.id.trade_detail_banner)
    Banner banner;
    @BindView(R.id.trade_detail_title)
    TextView title;
    @BindView(R.id.trade_detail_fineness)
    TextView fineness;
    @BindView(R.id.trade_detail_price)
    TextView price;
    @BindView(R.id.trade_detail_avatar)
    CircleImageView avatar;
    @BindView(R.id.trade_detail_username)
    TextView username;
    @BindView(R.id.trade_detail_post_date)
    TextView postDate;
    @BindView(R.id.trade_detail_post_time)
    TextView postTime;
    @BindView(R.id.trade_detail_content)
    TextView content;
    @BindView(R.id.trade_detail_contact)
    TextView contact;
    TradeInfo tradeInfo;
    int position;//传入数据位置
    String avatarUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_detail);

        ButterKnife.bind(this);
        init();
        initBanner();

    }

    private void init(){
        position=(int)getIntent().getSerializableExtra("position");
        tradeInfo=TradeFragment.tradeData.get(position);
        title.setText(tradeInfo.getTitle());
        fineness.setText(tradeInfo.getFineness());
        price.setText(tradeInfo.getPrice());
        postDate.setText(tradeInfo.getPostDate());
        postTime.setText(tradeInfo.getPostTime());
        content.setText(tradeInfo.getContent());
        username.setText(tradeInfo.getUsername());
        contact.setText(tradeInfo.getContact());
        try{
            avatarUrl=BaseUrl+"/fdb1.0.0/user/download/avatar/id/"+tradeInfo.getUserId();
            Glide.with(TradeDetailActivity.this).load(avatarUrl).placeholder(R.drawable.avatar_boy).into(avatar);
        }catch(Exception e){
        }
    }

    private void initBanner(){
        if(tradeInfo.getPictureUrls()!=null){
            banner.setImageLoader(new GlideImageLoader())
                    .setImages(tradeInfo.getPictureUrls())
                    .setOnBannerListener(new OnBannerListener(){
                        @Override
                        public void OnBannerClick(int position){
                            ImagePreview.getInstance()
                                    .setContext(TradeDetailActivity.this)
                                    .setIndex(0)
                                    .setImageList(tradeInfo.getPictureUrls())
                                    .start();
                        }
                    })
                    .isAutoPlay(false)
                    .start();
        }
    }



    private class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            Glide.with(context)
                    .load(path)
                    .fitCenter()
                    .placeholder(R.drawable.default_picture)
                    .into(imageView);
        }

    }

}
