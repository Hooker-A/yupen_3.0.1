package com.huaop2p.yqs.module.two_wealth.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public class WealthCenterAdapter<T extends Fragment> extends FragmentPagerAdapter {
    private List<T> fragments;
    private String[] titles;
    public WealthCenterAdapter(FragmentManager fm, List<T> fragments, String[] titles) {
        super(fm);
        this.fragments=fragments;
        this.titles=titles;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if(fragments==null)
            return 0;
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
       if (titles==null)
          return super.getPageTitle(position);
        return titles[position];
    }


}
