package com.example.huge.fzugang.LostBlock;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.huge.fzugang.CarpoolBlock.CarpoolFragment;
import com.example.huge.fzugang.MyPostActivity;
import com.example.huge.fzugang.R;
import com.example.huge.fzugang.RetrofitStuff.DeletePostRequest;
import com.example.huge.fzugang.Utils.MyPopup;
import com.example.huge.fzugang.Utils.RetrofitUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import razerdp.basepopup.QuickPopupBuilder;
import razerdp.basepopup.QuickPopupConfig;

import java.util.ArrayList;

import static com.example.huge.fzugang.MyApplication.getContext;

public class LostListAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<LostInfo> data;

    public LostListAdapter(Context context,ArrayList<LostInfo> data){
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
        final LostInfo item=data.get(position);
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=View.inflate(context,R.layout.view_lost,null);
            viewHolder.postLayout=convertView.findViewById(R.id.lost_post_layout);
            viewHolder.postImage=convertView.findViewById(R.id.lost_picture);
            viewHolder.titie=convertView.findViewById(R.id.lost_title);
            viewHolder.lostPlace=convertView.findViewById(R.id.lost_place);
            viewHolder.lostTime=convertView.findViewById(R.id.lost_time);
            viewHolder.postDate=convertView.findViewById(R.id.lost_post_date);
            viewHolder.postTime=convertView.findViewById(R.id.lost_post_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        if(item.getPictureUrls()!=null){
            Glide.with(context).load(item.getPictureUrls().get(0)).centerCrop().into(viewHolder.postImage);
        }
        viewHolder.titie.setText(item.getTitle());
        viewHolder.lostPlace.setText(item.getLostPlace());
        viewHolder.lostTime.setText(item.getLostTime());
        viewHolder.postTime.setText(item.getPostTime());

        //进入详情页监听
        viewHolder.postLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(context,LostDetailActivity.class);
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });

        //长按弹出删除帖子
        if(context.getClass().equals(MyPostActivity.class)){
            ViewHolder finalViewHolder=viewHolder;
            viewHolder.postLayout.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    MyPopup popup=new MyPopup(context);
                    popup.showPopupWindow(finalViewHolder.lostTime);
                    popup.deleteButton.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            DeletePostRequest deletePostRequest=new DeletePostRequest(item.getId(),SharedPreferencesUtil.getStoredMessage(getContext(),"token"));
                            RetrofitUtil.postDeleteLost(deletePostRequest);
                            popup.dismiss();
                            LostFragment.refreshLayout.autoRefresh();
                        }
                    });
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
        TextView postDate;
        TextView postTime;
        TextView lostPlace;
        TextView lostTime;
    }
}