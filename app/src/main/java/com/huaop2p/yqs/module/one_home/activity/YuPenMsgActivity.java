package com.huaop2p.yqs.module.one_home.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.one_home.view.YuPenMsgFragment;
import com.huaop2p.yqs.module.two_wealth.adapter.WealthCenterAdapter;
import com.huaop2p.yqs.utils.auto.AutoUtils;
import com.huaop2p.yqs.widget.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maoxiaofei on 2016/5/9.
 */
public class YuPenMsgActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    private ViewPager vp_yupen_msg;
    private WealthCenterAdapter pagerAdapter;

    private YuPenMsgFragment yuPenMsgFragment;
    private List<Fragment> fragments;

    private TabPageIndicator indictor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yupen_msg);

        initui();
        getData();
    }

    //初始化ui
    private void initui() {
        SetActivityTitle("余盆资讯");
        vp_yupen_msg = (ViewPager) findViewById(R.id.vp_yupen_msg);
        indictor = (TabPageIndicator) findViewById(R.id.indictor);
    }

    //viewpager套fragment
    private void getData(){
        if (pagerAdapter == null){
            Bundle bundle = null;
            fragments = new ArrayList<>();
            yuPenMsgFragment = new YuPenMsgFragment();
            bundle = new Bundle();
            bundle.putString("type", "1");
            yuPenMsgFragment.setArguments(bundle);
            fragments.add(yuPenMsgFragment);

            yuPenMsgFragment = new YuPenMsgFragment();
            bundle = new Bundle();
            bundle.putString("type", "2");
            yuPenMsgFragment.setArguments(bundle);
            fragments.add(yuPenMsgFragment);

            yuPenMsgFragment = new YuPenMsgFragment();
            bundle = new Bundle();
            bundle.putString("type", "3");
            yuPenMsgFragment.setArguments(bundle);
            fragments.add(yuPenMsgFragment);

            setPagerAdapter();
            indictor.setViewPager(vp_yupen_msg);

            vp_yupen_msg.setOffscreenPageLimit(3);
            selectIndicator(0);
            indictor.setOnPageChangeListener(this);

        }
    }

    //设置viewpager适配器
    private void setPagerAdapter() {
        pagerAdapter = new WealthCenterAdapter(getSupportFragmentManager(), fragments,new String[]{"余盆公告","媒体报道","金融动态"});
        vp_yupen_msg.setAdapter(pagerAdapter);
    }


        //选择头部三个textview的指示器
    public void selectIndicator(int position) {
        indictor.clearHeadTextColor(R.color.vpi_title_text_color);
        TextView tv = (TextView) indictor.getPositionView(position);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimensionPixelSize(R.dimen.select_text_content_size));
        if (position==0){
            tv.setTextColor(getResources().getColor(R.color.red));
            tv.setBackgroundResource(R.drawable.vpi__tab_selected_holo);
        }else if(position==1){
            tv.setTextColor(getResources().getColor(R.color.yufangbao));
            tv.setBackgroundResource(R.drawable.vpi__tab_selected_holo_fang);
        }else{
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
