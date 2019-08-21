package com.example.huge.fzugang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.huge.fzugang.CarpoolBlock.CarpoolFragment;
import com.example.huge.fzugang.DeliveryBlock.DeliveryFragment;
import com.example.huge.fzugang.LostBlock.LostFragment;
import com.example.huge.fzugang.TradeBlock.TradeFragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private CircleImageView slideAvatar;
    private TextView slideUserName;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private FragmentTabHost mTabHost;
    private LayoutInflater mLayoutInflater;
    private LinearLayout userInfo;
    private long firstClickTime;//记录退出点击时间

    //4个切换的页面的fragment.
    private Class mFragmentArray[]={
            LostFragment.class,//失物招领页面
            TradeFragment.class,//二手交易页面
            DeliveryFragment.class,//代领快递界面
            CarpoolFragment.class//拼车界面
    };


    //tab栏图标
    private int mImageArray[]={
            R.drawable.tab_lost,
            R.drawable.tab_trade,
            R.drawable.tab_delivery,
            R.drawable.tab_carpool
    };

    //tab栏的字
    private String mTextArray[]={"失物招领","二手交易","代领快递","拼车"};

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
        slideAvatar=findViewById(R.id.user_avatar);
        slideUserName=findViewById(R.id.user_name);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        userInfo=navigationView.getHeaderView(0).findViewById(R.id.user_info_layout);

        //设置工具栏
        setSupportActionBar(toolbar);
        //侧滑栏设置
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,0,0);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();

        //设置侧滑栏
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //设置监听事件
        userInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(MainActivity.this,PerfectionInfoActivity.class);
                startActivity(intent);
            }
        });
    }

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
            case R.id.feed_item:

                break;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture){

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
