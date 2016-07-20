package com.huaop2p.yqs.module.three_mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.four_set.activity.ShareActivity;

/**
 * Created by zgq on 2016/6/30.
 * 作者:  zhu  guo qing
 * 时间:  2016/6/30 18:15
 * 功能:  活动跳转
 */
public class AllActiveActivity extends BaseActivity implements View.OnClickListener{

    public static final String TITLE="title";
    public static final String URL="url";
    private Intent mIntent;
    private String titll,url;
    private WebView wb_view;
    private TextView txt_titleee;
    private Button bt_fenxiang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allactive);
        mIntent=getIntent();
        titll=mIntent.getStringExtra("title");
        url=mIntent.getStringExtra("url");
        initData();
        WebSettings webset1 = wb_view.getSettings();
        webset1.setJavaScriptEnabled(true);
        webset1.setAllowFileAccess(true);
        webset1.setBuiltInZoomControls(true);
        webset1.setUseWideViewPort(true);//设定支持viewport
        webset1.setLoadWithOverviewMode(true);
        wb_view.getSettings().setJavaScriptEnabled(true);
        wb_view.loadUrl(url);
        txt_titleee.setText(titll);

    }

    private void initData() {
        wb_view= (WebView) findViewById(R.id.wb_view);
        txt_titleee= (TextView) findViewById(R.id.txt_titleee);
        bt_fenxiang= (Button) findViewById(R.id.bt_fenxiang);
        bt_fenxiang.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_fenxiang:
                Intent intentZF = new Intent(AllActiveActivity.this, ShareActivity.class);
                startActivity(intentZF);
                break;
        }
    }
}
