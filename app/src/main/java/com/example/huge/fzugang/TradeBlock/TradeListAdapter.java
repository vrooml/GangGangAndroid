package com.example.huge.fzugang.TradeBlock;

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
            viewHolder.postTime=convertView.findViewById(R.id.trade_post_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.titie.setText(item.getTitle());
        viewHolder.content.setText(item.getContent());
        viewHolder.fineness.setText(item.getFineness());
        viewHolder.postTime.setText(item.getPostTime());
        viewHolder.price.setText(item.getPrice());

        //进入详情页监听
        viewHolder.postLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(context,TradeDetailActivity.class);
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
        TextView fineness;
        TextView content;
        TextView postTime;
        TextView price;
    }
}
