package com.huaop2p.yqs.module.one_home.activity;

import android.os.Bundle;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;

/**
 * Created by maoxiaofei on 2016/5/11.
 */
public class Market_Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_market);
        SetActivityTitle("积分商城");
        super.onCreate(savedInstanceState);
    }
}
