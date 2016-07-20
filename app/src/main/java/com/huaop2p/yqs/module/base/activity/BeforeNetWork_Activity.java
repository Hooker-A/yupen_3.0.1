package com.huaop2p.yqs.module.base.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.huaop2p.yqs.R;

/**
 * Created by maoxiaofei on 2016/7/1.
 */
public class BeforeNetWork_Activity extends BaseActivity {

    private WebView web_before;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beforenetwork_acitivty);

        web_before = (WebView) findViewById(R.id.web_before);
    }
}
