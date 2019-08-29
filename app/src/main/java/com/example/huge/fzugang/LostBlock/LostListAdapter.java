package com.example.huge.fzugang.LostBlock;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.huge.fzugang.R;

import java.util.ArrayList;

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
            viewHolder.postTime=convertView.findViewById(R.id.lost_post_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
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

        return convertView;
    }

    private class ViewHolder{
        LinearLayout postLayout;
        ImageView postImage;
        TextView titie;
        TextView postTime;
        TextView lostPlace;
        TextView lostTime;
    }
}