package com.huaop2p.yqs.module.one_home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by maoxiaofei on 2016/4/12.
 */
public class BinnerViewPagerAdapter<T extends Fragment> extends PagerAdapter {

    public List<View> pagerViews;
    boolean isLoop;
    private String[] titles;

    public BinnerViewPagerAdapter() {

    }


    /*
    * 设置是否循环
    * */
    public void setpagview(List<View> pagerViews, boolean isLoop) {
        this.pagerViews = pagerViews;
        this.isLoop = isLoop;
    }

    public int getSize() {
        return pagerViews == null ? 0 : pagerViews.size();
    }

    @Override
    public int getCount() {
        if (isLoop) {
            if (pagerViews != null || !pagerViews.isEmpty()) {
                if (pagerViews.size() == 1) {
                    return 1;
                } else {
                    return Integer.MAX_VALUE;
                }
            } else {
                return 0;
            }
        }
        return pagerViews.size();
    }

    //滑动切换的时候销毁当前的组件
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
//        ((ViewPager)container).removeView(pagerViews.get(position));
    }

    //每次滑动的时候生成的组件
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
//        container.addView(pagerViews.get(position));
//        return pagerViews.get(position);
        try {
            ((ViewPager) container).addView(pagerViews.get(position % pagerViews.size()), 0);
        } catch (Exception e) {

        }
        return pagerViews.get(position % pagerViews.size());
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}





















