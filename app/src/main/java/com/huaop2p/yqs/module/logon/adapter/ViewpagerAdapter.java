package com.huaop2p.yqs.module.logon.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zgq on 2016/4/6.
 * 作者:  zhu  guo qing
 * 时间:  2016/4/6 16:28
 * 功能:  引导页适配器
 */
public class ViewpagerAdapter extends PagerAdapter {

    private List<View> views;

    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) (container)).addView(views.get(position), 0);
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) (container)).removeView(views.get(position));
    }
}
