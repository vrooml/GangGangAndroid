package com.example.huge.fzugang.TradeBlock;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.example.huge.fzugang.MyPostActivity;
import com.example.huge.fzugang.R;
import com.example.huge.fzugang.RetrofitStuff.DeletePostRequest;
import com.example.huge.fzugang.Utils.LoadingdialogUtil;
import com.example.huge.fzugang.Utils.MyPopup;
import com.example.huge.fzugang.Utils.RetrofitUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import razerdp.basepopup.QuickPopupBuilder;
import razerdp.basepopup.QuickPopupConfig;

import java.util.ArrayList;

import static com.example.huge.fzugang.MyApplication.getContext;

public class TradeListAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<TradeInfo> data;

    public TradeListAdapter(Context context,ArrayList<TradeInfo> data){
        this.context=context;
        this.data=data;
    }

    @Override
    public int getCount(){
        if(data!=null){
            return data.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position){
        return null;
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(final int position,View convertView,ViewGroup parent){
        final TradeInfo item=data.get(position);
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=View.inflate(context,R.layout.view_trade,null);
            viewHolder.postLayout=convertView.findViewById(R.id.trade_post_layout);
            viewHolder.postImage=convertView.findViewById(R.id.trade_picture);
            viewHolder.titie=convertView.findViewById(R.id.trade_title);
            viewHolder.fineness=convertView.findViewById(R.id.trade_fineness);
            viewHolder.content=convertView.findViewById(R.id.trade_content);
            viewHolder.price=convertView.findViewById(R.id.trade_price);
            viewHolder.postTime1=convertView.findViewById(R.id.trade_post_time_1);
            viewHolder.postTime2=convertView.findViewById(R.id.trade_post_time_2);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.titie.setText(item.getTitle());
        viewHolder.content.setText(item.getContent());
        viewHolder.fineness.setText(item.getFineness());
        viewHolder.postTime1.setText(item.getPostDate());
        viewHolder.postTime2.setText(item.getPostTime());
        viewHolder.price.setText(item.getPrice());
        if(item.getPictureUrls()!=null){
            Glide.with(context).load(item.getPictureUrls().get(0)).centerCrop().into(viewHolder.postImage);
        }

        //进入详情页监听
        viewHolder.postLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(context,TradeDetailActivity.class);
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });

        //长按弹出删除帖子
        if(context.getClass().equals(MyPostActivity.class)){
            viewHolder.postLayout.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    QuickPopupBuilder.with(getContext())
                            .contentView(R.layout.popup_layout)
                            .config(new QuickPopupConfig()
                                    .blurBackground(true)
                                    .gravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL)
                                    .withClick(R.id.delete_item, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DeletePostRequest deletePostRequest=new DeletePostRequest(item.getId(),SharedPreferencesUtil.getStoredMessage(getContext(),"token"));
                                            RetrofitUtil.postDeleteTrade(deletePostRequest,LoadingdialogUtil.getZLoadingDialog(getContext()));
                                        }
                                    }))
                            .show(v);
                    return false;
                }
            });
        }

        return convertView;
    }

    private class ViewHolder{
        LinearLayout postLayout;
        ImageView postImage;
        TextView titie;
        TextView fineness;
        TextView content;
        TextView postTime1;
        TextView postTime2;
        TextView price;
    }
}
