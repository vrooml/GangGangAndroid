package com.example.huge.fzugang.CarpoolBlock;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.huge.fzugang.MainActivity;
import com.example.huge.fzugang.MyApplication;
import com.example.huge.fzugang.MyPostActivity;
import com.example.huge.fzugang.R;
import com.example.huge.fzugang.RetrofitStuff.DeletePostRequest;
import com.example.huge.fzugang.Utils.MyPopup;
import com.example.huge.fzugang.Utils.RetrofitUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import de.hdodenhof.circleimageview.CircleImageView;
import razerdp.basepopup.QuickPopupBuilder;
import razerdp.basepopup.QuickPopupConfig;

import java.util.ArrayList;

import static com.example.huge.fzugang.MyApplication.getContext;
import static com.example.huge.fzugang.Utils.constantUtil.BaseUrl;

public class CarpoolListAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<CarpoolInfo> data;

    public CarpoolListAdapter(Context context,ArrayList<CarpoolInfo> data){
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
        final CarpoolInfo item=data.get(position);
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=View.inflate(context,R.layout.view_carpool,null);
            viewHolder.postLayout=convertView.findViewById(R.id.carpool_post_layout);
            viewHolder.start=convertView.findViewById(R.id.carpool_start);
            viewHolder.destination=convertView.findViewById(R.id.carpool_destination);
            viewHolder.date=convertView.findViewById(R.id.carpool_depart_date);
            viewHolder.time=convertView.findViewById(R.id.carpool_depart_time);
            viewHolder.peopleNum=convertView.findViewById(R.id.carpool_num_of_people);
            viewHolder.price=convertView.findViewById(R.id.carpool_price);
            viewHolder.userName=convertView.findViewById(R.id.carpool_user_name);
            viewHolder.avatar=convertView.findViewById(R.id.carpool_user_avatar);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.start.setText(item.getMeetPlace());
        viewHolder.destination.setText(item.getDestination());
        viewHolder.date.setText(item.getDate());
        viewHolder.time.setText(item.getTime());
        viewHolder.peopleNum.setText("待拼"+item.getNumOfPeople()+"人");
        viewHolder.price.setText(item.getPrice());
        viewHolder.userName.setText(item.getAuthor());
        try{
            String avatarUrl=BaseUrl+"/fdb1.0.0/user/download/avatar/id/"+item.getUserId();
            Glide.with(getContext())
                    .load(avatarUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(viewHolder.avatar);
        }catch(Exception e){
        }

        //进入详情页监听
        viewHolder.postLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(context,CarpoolDetailActivity.class);
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
                    popup.showPopupWindow(finalViewHolder.time);
                    popup.deleteButton.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            DeletePostRequest deletePostRequest=new DeletePostRequest(String.valueOf(item.getId()),SharedPreferencesUtil.getStoredMessage(getContext(),"token"));
                            RetrofitUtil.postDeleteCarpool(deletePostRequest);
                            popup.dismiss();
                            CarpoolFragment.refreshLayout.autoRefresh();
                        }
                    });
                    return false;
                }
            });
        }

        return convertView;
    }

    private class ViewHolder{
        RelativeLayout postLayout;
        TextView start;
        TextView destination;
        TextView date;
        TextView time;
        TextView peopleNum;
        TextView price;
        TextView userName;
        CircleImageView avatar;
    }
}
