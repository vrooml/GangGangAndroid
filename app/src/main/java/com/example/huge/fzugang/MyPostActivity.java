package com.example.huge.fzugang;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.huge.fzugang.CarpoolBlock.CarpoolFragment;
import com.example.huge.fzugang.LostBlock.LostFragment;
import com.example.huge.fzugang.TradeBlock.TradeFragment;

import java.util.ArrayList;
import java.util.List;

public class MyPostActivity extends AppCompatActivity{
    @BindView(R.id.my_post_tab_layout)
    TabLayout myPostTablayout;
    @BindView(R.id.my_post_view_pager)
    ViewPager myPostviewPager;
    private MyTabFragmentPagerAdapter myTabFragmentPagerAdapter;
    private List<Fragment> fragments;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);
        ButterKnife.bind(this);
        init();

    }

    private void init(){
        fragments=new ArrayList<Fragment>();
        fragments.add(new LostFragment());
        fragments.add(new TradeFragment());
        fragments.add(new CarpoolFragment());

        myTabFragmentPagerAdapter = new MyTabFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        myPostviewPager.setOffscreenPageLimit(3);
        myPostviewPager.setAdapter(myTabFragmentPagerAdapter);
        //将TabLayout和ViewPager绑定在一起，使双方各自的改变都能直接影响另一方，解放了开发人员对双方变动事件的监听
        myPostTablayout.setupWithViewPager(myPostviewPager);

        //指定Tab的位置
        one = myPostTablayout.getTabAt(0);
        two = myPostTablayout.getTabAt(1);
        three = myPostTablayout.getTabAt(2);

    }


}


class MyTabFragmentPagerAdapter extends FragmentPagerAdapter{
    private String[] mTitles = new String[]{"失物招领","二手交易","拼车服务"};
    private List<Fragment> fragments;

    public MyTabFragmentPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //用来设置tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
