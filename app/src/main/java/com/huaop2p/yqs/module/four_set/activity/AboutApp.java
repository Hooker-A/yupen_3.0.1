package com.huaop2p.yqs.module.four_set.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.utils.auto.AppUtils;

/**
 * Created by zgq on 2016/4/12.
 * 作者:  zhu  guo qing
 * 时间:  2016/4/12 11:46
 * 功能:  关于余盆
 */
public class AboutApp extends BaseActivity {

    private TextView txt_cur_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        SetActivityTitle("关于余盆");
        initData();
    }

    private void initData() {
        txt_cur_version= (TextView) findViewById(R.id.txt_cur_version);
        try {
            txt_cur_version.setText(AppUtils.getVersionName(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
