package com.huaop2p.yqs.module.one_home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.one_home.bean.YuPenBean;
import com.huaop2p.yqs.module.one_home.view.AboutYuPenFragment;
import com.huaop2p.yqs.module.two_wealth.adapter.WealthCenterAdapter;
import com.huaop2p.yqs.utils.auto.AutoUtils;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.huaop2p.yqs.widget.TabPageIndicator;
import com.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maoxiaofei on 2016/7/1.
 */
public class AboutYuPenActivtity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private YuPenBean yuPenBean;
    private TextView TXT_APP_TITLE;
    private int mode;
    private Intent intent;
    String url;
    int tag;
    private List<YuPenBean> aboutyupen;

    private ViewPager vp_yupen_msg;
    private List<Fragment> fragments;
    private TabPageIndicator indictor;
    private WealthCenterAdapter pagerAdapter;
    private AboutYuPenFragment aboutyupenfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutyupen);
        SetActivityTitle("关于余盆");



        intent = getIntent();
        url = intent.getStringExtra("url");
        tag = intent.getIntExtra("tag", tag);
        aboutyupen = (List<YuPenBean>) intent.getSerializableExtra("aboutyupen");
        LogUtils.e(aboutyupen.size() + "");
        LogUtils.e(GsonUtils.getGson().toJson(aboutyupen.get(0)) + "");

        mode = intent.getIntExtra("mode", mode);

        vp_yupen_msg = (ViewPager) findViewById(R.id.vp_yupen_msg);
        indictor = (TabPageIndicator) findViewById(R.id.indictor);

        getData();

    }

    //viewpager套fragment
    private void getData() {
        if (pagerAdapter == null) {
            Bundle bundle = null;
            fragments = new ArrayList<>();
            aboutyupenfragment = new AboutYuPenFragment();
            bundle = new Bundle();
            bundle.putString("type", "1");
            bundle.putString("url", aboutyupen.get(0).Url);


            aboutyupenfragment.setArguments(bundle);
            fragments.add(aboutyupenfragment);

            aboutyupenfragment = new AboutYuPenFragment();
            bundle = new Bundle();
            bundle.putString("type", "2");
            bundle.putString("url", aboutyupen.get(1).Url);

            aboutyupenfragment.setArguments(bundle);
            fragments.add(aboutyupenfragment);

            aboutyupenfragment = new AboutYuPenFragment();
            bundle = new Bundle();
            bundle.putString("type", "3");
            bundle.putString("url", aboutyupen.get(2).Url);

            aboutyupenfragment.setArguments(bundle);
            fragments.add(aboutyupenfragment);

            aboutyupenfragment = new AboutYuPenFragment();
            bundle = new Bundle();
            bundle.putString("type", "4");
            bundle.putString("url", aboutyupen.get(3).Url);

            aboutyupenfragment.setArguments(bundle);
            fragments.add(aboutyupenfragment);

            setPagerAdapter();
            indictor.setViewPager(vp_yupen_msg);

            vp_yupen_msg.setOffscreenPageLimit(4);
            selectIndicator(0);
            indictor.setOnPageChangeListener(this);

        }
    }

    //设置viewpager适配器
    private void setPagerAdapter() {
        pagerAdapter = new WealthCenterAdapter(getSupportFragmentManager(), fragments, new String[]{"余盆金融", "业务模式", "风控模式", "公益年志"});
        vp_yupen_msg.setAdapter(pagerAdapter);
    }

    //选择头部三个textview的指示器
    public void selectIndicator(int position) {
        indictor.clearHeadTextColor(R.color.vpi_title_text_color);
        TextView tv = (TextView) indictor.getPositionView(position);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimensionPixelSize(R.dimen.select_text_content_size));
        if (position == 0) {
            tv.setTextColor(getResources().getColor(R.color.red));
            tv.setBackgroundResource(R.drawable.vpi__tab_selected_holo);
        } else if (position == 1) {
            tv.setTextColor(getResources().getColor(R.color.yufangbao));
            tv.setBackgroundResource(R.drawable.vpi__tab_selected_holo_fang);
        } else if (position == 2) {
            tv.setTextColor(getResources().getColor(R.color.yuchebao));
            tv.setBackgroundResource(R.drawable.vpi__tab_selected_holo_che);
        } else {
            tv.setTextColor(getResources().getColor(R.color.h_b_bg));
            tv.setBackgroundResource(R.drawable.vpi__tab_selected_holo_transferr);
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
