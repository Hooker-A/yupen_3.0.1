package com.huaop2p.yqs.module.three_mine.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.three_mine.view.fragment.HistoryFragment;
import com.huaop2p.yqs.module.two_wealth.adapter.WealthCenterAdapter;
import com.huaop2p.yqs.utils.auto.AutoUtils;
import com.huaop2p.yqs.widget.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/17.
 * 作者：任洋
 * 功能：投资记录历史
 */
public class HistoryActivity extends BaseAutoActivity implements ViewPager.OnPageChangeListener {

    private TabPageIndicator indicator;

    private ViewPager vp;

    private WealthCenterAdapter adapter;

    private Fragment fragment;
    @Override
    public int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    public void initViews() {
        indicator= (TabPageIndicator) findViewById(R.id.indictor);
        vp= (ViewPager) findViewById(R.id.vp);
    }
    @Override
    public Object getObject() {
        return this;
    }
    @Override
    public void initData() {
        Bundle bundle=null;
        List<Fragment> fragments=new ArrayList<Fragment>();
        fragment=new HistoryFragment();
        bundle=new Bundle();
        bundle.putInt("type", State.YUXINBAO);
        fragment.setArguments(bundle);
        fragments.add(fragment);
        fragment=new HistoryFragment();
        bundle=new Bundle();
        bundle.putInt("type", State.YUFANGBAO);
        fragment.setArguments(bundle);
        fragments.add(fragment);
        fragment=new HistoryFragment();
        bundle=new Bundle();
        bundle.putInt("type", State.YUCHEBAO);
        fragment.setArguments(bundle);;
        fragments.add(fragment);
        adapter = new WealthCenterAdapter(getSupportFragmentManager(), fragments, new String[]{"余信宝", "余房宝", "余车宝"});
        vp.setAdapter(adapter);
        indicator.setViewPager(vp);
        vp.setOffscreenPageLimit(3);
        selectIndicator(0);
        indicator.setOnPageChangeListener(this);
    }
    public void selectIndicator(int position) {
        indicator.clearHeadTextColor(R.color.vpi_title_text_color);
        TextView tv = (TextView) indicator.getPositionView(position);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimensionPixelSize(R.dimen.select_text_content_size));
        if (position==0||position==3){
            tv.setTextColor(getResources().getColor(R.color.red));
            tv.setBackgroundResource(R.drawable.vpi__tab_selected_holo);
        }else if(position==1){
            tv.setTextColor(getResources().getColor(R.color.yufangbao));
            tv.setBackgroundResource(R.drawable.vpi__tab_selected_holo_fang);
        }else{
            tv.setTextColor(getResources().getColor(R.color.yuchebao));
            tv.setBackgroundResource(R.drawable.vpi__tab_selected_holo_che);
        }
        AutoUtils.autoTextSize(tv);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectIndicator(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
