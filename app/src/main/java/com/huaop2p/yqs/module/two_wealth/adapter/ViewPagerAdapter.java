package com.huaop2p.yqs.module.two_wealth.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private List<View> list;
    private boolean isMax;

    public ViewPagerAdapter(List<View> list, boolean isMax) {
        this.list = list;
        this.isMax = isMax;
    }

    @Override
    public int getCount() {

        if (list != null && list.size() > 0 && isMax) {
            return Integer.MAX_VALUE;
        } else if (list != null && list.size() > 0 && !isMax) {
            return list.size();
        } else {
            return 0;
        }
    }


    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position % list.size()));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position % list.size()));
        if (list.get(position % list.size()) instanceof ImageView) {
            ImageView iv = (ImageView) list.get(position % list.size());
            String url = (String) iv.getTag();
            ImageLoader.getInstance().displayImage(url, iv);
        }
        return list.get(position % list.size());
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}