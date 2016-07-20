package com.huaop2p.yqs.module.one_home.activity;

import android.content.Intent;
import android.os.Bundle;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;

/**
 * Created by maoxiaofei on 2016/7/12.
 */
public class CustomerActivity extends BaseActivity {


    private Intent intent;
    String keyid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customer);

        intent = getIntent();
        keyid = intent.getStringExtra("keyid");


    }
}
