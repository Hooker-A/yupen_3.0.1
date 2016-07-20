package com.huaop2p.yqs.module.base.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.huaop2p.yqs.R;

/**
 * Created by Administrator on 2016/7/1.
 * 作者：任洋
 * 功能：
 */
public class ErrorActivity extends  BaseAutoActivity  {
    private WebView mWebView;
    private WebSettings settings;
    private Button btn_colse;

    @Override
    public Object getObject() {
        return this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_error_web;
    }

    @Override
    public void initViews() {
        mWebView= (WebView) findViewById(R.id.mWebView);
        btn_colse= (Button) findViewById(R.id.btn_colse);
        settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);  //设置可以访问文件
        settings.setBuiltInZoomControls(true);
        /**自适应屏幕**/
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        settings.setDisplayZoomControls(false); //隐藏webview缩放按钮
        mWebView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void initData() {
        mWebView.loadUrl(getIntent().getStringExtra("url"));
        boolean flag=getIntent().getBooleanExtra("flag",false);
        if (flag){
            btn_colse.setEnabled(true);
        }else{
            btn_colse.setEnabled(false);
        }
        btn_colse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
ErrorActivity.this.finish();
            }
        });
    }

}
