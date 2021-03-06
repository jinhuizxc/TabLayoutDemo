package com.liang.tablayoutdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Android TabLayout 在宽屏幕上tab不能平均分配的问题解决:
 * https://blog.csdn.net/chenli_001/article/details/72844139
 */
public class TabLayoutActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager vp;

    //当标签数目小于等于4个时，标签栏不可滑动
    public static final int MOVABLE_COUNT = 4;

    private int tabCount = 10;
    private List<String> tabs;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);

        mTabLayout = findViewById(R.id.mTabLayout);
        vp = findViewById(R.id.vp);

        initDatas();
        initViewPager();
        initTabLayout();
    }

    private void initTabLayout() {
        //MODE_FIXED标签栏不可滑动，各个标签会平分屏幕的宽度
        mTabLayout.setTabMode(tabCount <= MOVABLE_COUNT ? TabLayout.MODE_FIXED : TabLayout.MODE_SCROLLABLE);
        //指示条的颜色
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(android.R.color.holo_blue_dark));
        mTabLayout.setSelectedTabIndicatorHeight((int) getResources().getDimension(R.dimen.indicatorHeight));
        //设置tabLayout文字选中和未选中效果
        mTabLayout.setTabTextColors(getResources().getColor(R.color.c_333333), getResources().getColor(R.color.c_e53333));


        //关联tabLayout和ViewPager,两者的选择和滑动状态会相互影响
        mTabLayout.setupWithViewPager(vp);
        //自定义标签布局
//        for (int i = 0; i < tabs.size(); i++) {
//            TabLayout.Tab tab = mTabLayout.getTabAt(i);
//            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.tabview_main, mTabLayout, false);
//            tv.setText(tabs.get(i));
//            assert tab != null;
//            tab.setCustomView(tv);
//        }
    }

    private void initViewPager() {
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    private void initDatas() {
        tabs = new ArrayList<>();
        for (int i = 0; i < tabCount; i++) {
            tabs.add("标签" + i);
        }

        fragments = new ArrayList<>();
        for (int i = 0; i < tabs.size(); i++) {
            fragments.add(TabFragment.newInstance(tabs.get(i)));
        }

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        /**
         * 如果不是自定义标签布局，需要重写该方法
         */
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }
    }

}
