package com.huaop2p.yqs.module.two_wealth.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.linstener.EventBusLinstener;
import com.huaop2p.yqs.module.base.view.BaseAutoFragment;
import com.huaop2p.yqs.module.two_wealth.activity.BorrowerInfoActivity;
import com.huaop2p.yqs.module.two_wealth.adapter.WealthCenterAdapter;
import com.huaop2p.yqs.module.two_wealth.model.entity.EventBusEntity;
import com.huaop2p.yqs.utils.auto.AutoUtils;
import com.huaop2p.yqs.widget.TabPageIndicator;
import com.huaop2p.yqs.widget.scroll.FixedSpeedScroller;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by zgq on 2016/4/6.
 * 作者:  zhu  guo qing
 * 时间:  2016/4/6 17:21
 * 功能:  财富中心
 */
public class FragmentCentre extends BaseAutoFragment implements ViewPager.OnPageChangeListener, View.OnClickListener, EventBusLinstener<EventBusEntity<String>> {
    @ViewInject(R.id.indictor)
    private TabPageIndicator indicator;
    @ViewInject(R.id.vp)
    private ViewPager vp;
    public WealthCenterAdapter adapter;

    private Fragment yuXinBaoFragment;


    @Override
    public void showData() {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().registerSticky(this);
        if (adapter == null) {
            Bundle bundle = null;
            List<Fragment> fragments = new ArrayList<Fragment>();
            yuXinBaoFragment = new WealthCenterFragment();
            bundle = new Bundle();
            bundle.putString("type", String.valueOf(State.YUXINBAO));
            yuXinBaoFragment.setArguments(bundle);
            fragments.add(yuXinBaoFragment);
            yuXinBaoFragment = new WealthCenterFragment();
            bundle = new Bundle();
            bundle.putString("type", String.valueOf(State.YUFANGBAO));
            yuXinBaoFragment.setArguments(bundle);
            fragments.add(yuXinBaoFragment);

            yuXinBaoFragment = new WealthCenterFragment();
            bundle = new Bundle();
            bundle.putString("type", String.valueOf(State.YUCHEBAO));
            yuXinBaoFragment.setArguments(bundle);
            fragments.add(yuXinBaoFragment);

            yuXinBaoFragment = new WealthModelFragment();
            bundle.putString("url", HttpUrl.PrivateRaise);
            yuXinBaoFragment.setArguments(bundle);
            fragments.add(yuXinBaoFragment);
//            yuXinBaoFragment = new TransferFragment();
//            fragments.add(yuXinBaoFragment);

            //  adapter = new WealthCenterAdapter(getChildFragmentManager(), fragments, new String[]{"余信宝", "余房宝", "余车宝", "转让社区"});
            adapter = new WealthCenterAdapter(getChildFragmentManager(), fragments, new String[]{"余信宝", "余房宝", "余车宝", "固定收益类"});
            vp.setAdapter(adapter);
            indicator.setViewPager(vp);
            vp.setOffscreenPageLimit(3);
            try {
                Field field = ViewPager.class.getDeclaredField("mScroller");
                field.setAccessible(true);
                FixedSpeedScroller scroller = new FixedSpeedScroller(vp.getContext(),
                        new AccelerateInterpolator());
                field.set(vp, scroller);
                scroller.setmDuration(50);
            } catch (Exception e) {
            }
            selectIndicator(0);
            indicator.setOnPageChangeListener(this);
        }
    }

    @Override
    public View initViews(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_wealthcenter, null);
        return view;
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
        TabPageIndicator.TabView tv = (TabPageIndicator.TabView) indicator.getPositionView(position);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.text_content_size));
        if (position == 0) {
            tv.setTextColor(getResources().getColor(R.color.red));
            tv.setBackgroundResource(R.drawable.vpi__tab_selected_holo);
        } else if (position == 1) {
            tv.setTextColor(getResources().getColor(R.color.yufangbao));
            tv.setBackgroundResource(R.drawable.vpi__tab_selected_holo_fang);
        } else if (position == 3) {
            tv.setTextColor(getResources().getColor(R.color.h_b_bg));
            tv.setBackgroundResource(R.drawable.vpi__tab_selected_holo_transferr);
        } else {
            tv.setTextColor(getResources().getColor(R.color.yuchebao));
            tv.setBackgroundResource(R.drawable.vpi__tab_selected_holo_che);
        }
        AutoUtils.autoTextSize(tv);
    }

    @Override
    protected boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), BorrowerInfoActivity.class);
        startActivity(intent);
    }


    @Override
    public void onEventMainThread(EventBusEntity<String> stringEventBusEntity) {
        if (stringEventBusEntity.type != 1)
            return;
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().removeStickyEvent(String.class);
        TabPageIndicator.TabView tv = (TabPageIndicator.TabView) indicator.getPositionView(3);
        tv.setText(stringEventBusEntity.objecy);
    }


}
