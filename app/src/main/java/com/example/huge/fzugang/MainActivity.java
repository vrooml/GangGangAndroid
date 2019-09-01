package com.example.huge.fzugang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.*;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.example.huge.fzugang.CarpoolBlock.CarpoolBasicAddActivity;
import com.example.huge.fzugang.CarpoolBlock.CarpoolFragment;
import com.example.huge.fzugang.DeliveryBlock.DeliveryFragment;
import com.example.huge.fzugang.LostBlock.AddLostActivity;
import com.example.huge.fzugang.LostBlock.LostFragment;
import com.example.huge.fzugang.RetrofitStuff.SignOutRequest;
import com.example.huge.fzugang.TradeBlock.AddTradeActivity;
import com.example.huge.fzugang.TradeBlock.TradeFragment;
import com.example.huge.fzugang.Utils.RetrofitUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

import static com.example.huge.fzugang.Utils.constantUtil.BaseUrl;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private View headerview;
    private CircleImageView slideAvatar;
    private TextView slideUsername;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private FragmentTabHost mTabHost;
    private LayoutInflater mLayoutInflater;
    private LinearLayout userInfo;
    private Button searchButton;
    private Button addButton;
    private long firstClickTime;//记录退出点击时间
    private String avatarUrl;//头像地址

    //4个切换的页面的fragment.
    private Class mFragmentArray[]={
            LostFragment.class,//失物招领页面
            TradeFragment.class,//二手交易页面
//            DeliveryFragment.class,//代领快递界面
            CarpoolFragment.class//拼车界面
    };


    //tab栏图标
    private int mImageArray[]={
            R.drawable.tab_lost,
            R.drawable.tab_trade,
//            R.drawable.tab_delivery,
            R.drawable.tab_carpool
    };

    //tab栏的字
    private String mTextArray[]={"失物招领"
                                ,"二手交易"
//                                ,"代领快递"
                                ,"拼车"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        init();
        initFragmentTabHost();

    }

    private void init(){
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        headerview=navigationView.getHeaderView(0);
        slideAvatar=headerview.findViewById(R.id.user_avatar);
        slideUsername=headerview.findViewById(R.id.user_name);
        toolbar=findViewById(R.id.toolbar);
        searchButton=findViewById(R.id.search_button);
        addButton=findViewById(R.id.add_button);
        userInfo=navigationView.getHeaderView(0).findViewById(R.id.user_info_layout);

        //设置工具栏
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //侧滑栏设置
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,0,0);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();

        Log.d("debug","init: aaaaaaaaaaa"+SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"username")+SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"userId"));
        //设置侧滑用户信息
        try{
            avatarUrl=BaseUrl+"/fdb1.0.0/user/download/avatar/id/"+SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"userId");
            Glide.with(MainActivity.this).load(avatarUrl).into(slideAvatar);
            slideUsername.setText(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"username"));
        }catch(Exception e){
        }

        //设置菜单栏按键
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //设置监听事件
        userInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(MainActivity.this,PerfectionInfoActivity.class);
                intent.putExtra("sourceClass",PerfectionInfoActivity.class);
                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
//                if(getVisibleFragment() instanceof LostFragment){
//                    Intent intent=new Intent(MainActivity.this,SearchLostActivity.class);
//                    startActivity(intent);
//                }else if(getVisibleFragment() instanceof DeliveryFragment){
//                    Intent intent=new Intent(MainActivity.this,SearchDeliveryActivity.class);
//                    startActivity(intent);
//                }else if(getVisibleFragment() instanceof TradeFragment){
//                    Intent intent=new Intent(MainActivity.this,SearchTradeActivity.class);
//                    startActivity(intent);
//                }else if(getVisibleFragment() instanceof CarpoolFragment){
//                    Intent intent=new Intent(MainActivity.this,SearchCarpoolActivity.class);
//                    startActivity(intent);
//                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(getVisibleFragment() instanceof LostFragment){
                    Intent intent=new Intent(MainActivity.this,AddLostActivity.class);
                    startActivity(intent);
                }
//                else if(getVisibleFragment() instanceof DeliveryFragment){
//                    Intent intent=new Intent(MainActivity.this,AddDeliveryActivity.class);
//                    startActivity(intent);
//                }else
                if(getVisibleFragment() instanceof TradeFragment){
                    Intent intent=new Intent(MainActivity.this,AddTradeActivity.class);
                    startActivity(intent);
                }
                else if(getVisibleFragment() instanceof CarpoolFragment){
                    Intent intent=new Intent(MainActivity.this, CarpoolBasicAddActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    //初始化底部栏
    private void initFragmentTabHost(){
        mLayoutInflater=LayoutInflater.from(this);

        //设置底部tab控件
        mTabHost=findViewById(android.R.id.tabhost);
        mTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        mTabHost.getTabWidget().setShowDividers(0);

        int count=mFragmentArray.length;
        for(int i=0;i<count;i++){
            //为Tabhost中的分页tabSpec设置对应的分页按钮对象
            TabHost.TabSpec tabSpec=mTabHost.newTabSpec(mTextArray[i]).setIndicator(getTabItemView(i));
            //将Tabhost中的分页tabSpec和对应的Fragment界面对应起来
            mTabHost.addTab(tabSpec,mFragmentArray[i],null);
            //设置Tabhost的Tab按钮设置背景资源
            //其中：mTabHost.getTabWidget().getChildAt(i)就是：底部的tab按钮对象。
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_background);
        }
    }

    private View getTabItemView(int index){
        @SuppressLint("InflateParams") View view=mLayoutInflater.inflate(R.layout.tab_item_view,null);
        ImageView imageView=view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageArray[index]);
        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
        Intent intent;
        switch(menuItem.getItemId()){
            case R.id.my_post_item:
                Intent menuIntent=new Intent(MainActivity.this,MyPostActivity.class);
                startActivity(menuIntent);
                break;
            case R.id.feed_item:

                break;
            case R.id.sign_out_item:
                //清除登录信息
                SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("FzuGang", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                //发送退出登录请求
                SignOutRequest signOutRequest=new SignOutRequest(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"username"));
                RetrofitUtil.postSignOut(MainActivity.this,signOutRequest);
                break;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture){

    }

    //得到当前显示的fragment
    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(navigationView.getVisibility()==View.VISIBLE){
                //当左边的侧滑栏是可见的，则关闭
                drawerLayout.closeDrawer(navigationView);

            }else if(event.getAction()==KeyEvent.ACTION_DOWN){
                long secondTime=System.currentTimeMillis();
                if(secondTime-firstClickTime>2000){
                    Toast.makeText(MainActivity.this,"再次点击退出福大帮",Toast.LENGTH_SHORT).show();
                    firstClickTime=secondTime;
                }else{
                    System.exit(0);
                }
            }
        }
        return true;
    }
}
