package com.huaop2p.yqs.module.three_mine.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.three_mine.view.fragment.TransferRecordFragment;
import com.huaop2p.yqs.module.two_wealth.adapter.WealthCenterAdapter;
import com.huaop2p.yqs.widget.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/13.
 * 作者：任洋
 * 功能：转让记录页面
 */
public class TransferRecordActivity extends BaseAutoActivity implements ViewPager.OnPageChangeListener {
    private ViewPager vp;
    private WealthCenterAdapter adapter;
    private TabPageIndicator indicator;

    @Override
    public int getLayoutId() {
        return R.layout.activity_transfer_record;
    }

    @Override
    public void initViews() {
        vp = (ViewPager) findViewById(R.id.vp);
        indicator = (TabPageIndicator) findViewById(R.id.indictor);
    }
    @Override
    public Object getObject() {
        return this;
    }
    @Override
    public void initData() {
        if (adapter == null) {
            Bundle bundle = null;
            Fragment fragment;
            List<Fragment> fragments = new ArrayList<Fragment>();
            fragment = new TransferRecordFragment();
            bundle = new Bundle();
            bundle.putString("type",State.TRANSFERING);
            fragment.setArguments(bundle);
            fragments.add(fragment);
            fragment = new TransferRecordFragment();
            bundle = new Bundle();
            bundle.putString("type",State.TRANSFER_END);
            fragment.setArguments(bundle);
            fragments.add(fragment);
            adapter = new WealthCenterAdapter(getSupportFragmentManager(), fragments, new String[]{"转让中", "已转让"});
            vp.setAdapter(adapter);
            indicator.setViewPager(vp);
            vp.setOffscreenPageLimit(1);
            selectIndicator(0);
            indicator.setOnPageChangeListener(this);
        }
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

    public void selectIndicator(int position) {
        indicator.clearHeadTextColor(R.color.vpi_title_text_color);
        TextView tv = (TextView) indicator.getPositionView(position);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimensionPixelSize(R.dimen.select_text_content_size));
        tv.setTextColor(getResources().getColor(R.color.red));
        tv.setBackgroundResource(R.drawable.vpi__tab_selected_holo);
    }
}
