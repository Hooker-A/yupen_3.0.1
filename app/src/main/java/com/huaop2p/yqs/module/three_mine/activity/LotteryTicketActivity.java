package com.huaop2p.yqs.module.three_mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.three_mine.view.fragment.LottetyTicketFragment;
import com.huaop2p.yqs.module.three_mine.view.fragment.RedPagketFragment;
import com.huaop2p.yqs.module.two_wealth.adapter.WealthCenterAdapter;
import com.huaop2p.yqs.utils.auto.AutoUtils;
import com.huaop2p.yqs.widget.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maoxiaofei on 2016/5/16.
 */
public class LotteryTicketActivity extends BaseAutoActivity implements ViewPager.OnPageChangeListener {


    private ViewPager vp_lottery_ticket;
    private WealthCenterAdapter pagerAdapter;
    private TabPageIndicator indictor;
    private LottetyTicketFragment fragment;
    private RedPagketFragment fragmentred;

    private int flag;//任洋逗逼传过来的值  0是从我的财富奖券管理中传过来的，表示红包不可选 1是红包 ，2是奖券，其它的是我跳的不能点击 的
    private int productType;
    private Long money;
    private String keyId;


    private List<Fragment> fragmentList;

    @Override
    public int getLayoutId() {

        return R.layout.activity_lotterytcket;
    }
    @Override
    public Object getObject() {
        return this;
    }
    @Override
    public void initViews() {
//        SetActivityTitle("红包奖券");
        vp_lottery_ticket = (ViewPager) findViewById(R.id.vp_lottery_ticket);
        indictor = (TabPageIndicator) findViewById(R.id.indictor);



    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 1);
        productType = intent.getIntExtra("productType",productType);
        money = intent.getLongExtra("money", 0);
        keyId = intent.getStringExtra("keyId");


        if (pagerAdapter == null) {
            Bundle bundle = null;
            fragmentred = new RedPagketFragment();//红包fragment
            fragmentList = new ArrayList<Fragment>();
            bundle = new Bundle();
            bundle.putString("type", "1");
            bundle.putInt("productType", productType);
            bundle.putLong("money", money);
            bundle.putString("keyId",keyId);

            bundle.putInt("flag", flag);

            fragmentred.setArguments(bundle);
            fragmentList.add(fragmentred);

            fragment = new LottetyTicketFragment();//奖券fragment
            bundle = new Bundle();
            bundle.putString("type", "2");
            bundle.putInt("flag", flag);
            bundle.putInt("productType",productType);
            bundle.putLong("money", money);
            bundle.putString("keyId",keyId);

            fragment.setArguments(bundle);
            fragmentList.add(fragment);

            setPagerAdapter();
            indictor.setViewPager(vp_lottery_ticket);


            vp_lottery_ticket.setOffscreenPageLimit(2);
            selectIndicator(0);
            indictor.setOnPageChangeListener(this);
        }
        if (flag==2)
            vp_lottery_ticket.setCurrentItem(1);
    }

    //设置viewpager适配器
    private void setPagerAdapter() {
        pagerAdapter = new WealthCenterAdapter(getSupportFragmentManager(), fragmentList, new String[]{"红包", "奖券"});
        vp_lottery_ticket.setAdapter(pagerAdapter);
    }


    //选择头部两个textview的指示器
    public void selectIndicator(int position) {
        indictor.clearHeadTextColor(R.color.vpi_title_text_color);
        indictor.setBackgroundResource(R.color.white);
        TextView tv = (TextView) indictor.getPositionView(position);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimensionPixelSize(R.dimen.select_text_content_size));
        if (position == 0) {
            tv.setTextColor(getResources().getColor(R.color.orangered));
            tv.setBackgroundResource(R.drawable.vpi__tab_selected_holo);
        } else {
            tv.setTextColor(getResources().getColor(R.color.yuchebao));
            tv.setBackgroundResource(R.drawable.vpi__tab_selected_holo_che);
        }

        AutoUtils.auto(tv);
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
