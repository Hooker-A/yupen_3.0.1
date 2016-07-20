package com.huaop2p.yqs.module.one_home.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.lidroid.xutils.util.LogUtils;

/**
 * Created by maoxiaofei on 2016/7/1.
 */
public class MseProduceActivity extends BaseActivity {

    private WebView webview_msgproduce;
    private Intent intent;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msgproduce);
        SetActivityTitle("消息详情");

        webview_msgproduce = (WebView) findViewById(R.id.webview_msgproduce);

        intent = getIntent();
        url = intent.getStringExtra("url");
        setWebview();

    }

    private void setWebview(){
        /**
         * 设置载入页面自适应手机屏幕，居中显示
         * */
        WebSettings webset1 = webview_msgproduce.getSettings();
        webset1.setJavaScriptEnabled(true);
        webset1.setAllowFileAccess(true);
        webset1.setBuiltInZoomControls(true);
        webset1.setUseWideViewPort(true);//设定支持viewport
        webset1.setLoadWithOverviewMode(true);

        webview_msgproduce.loadUrl(url);
        LogUtils.e(url);

        webview_msgproduce.getSettings().setJavaScriptEnabled(true);

//            webset1.setTextSize(WebSettings.TextSize.SMALLEST);//设置字体大小

        webview_msgproduce.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

        });

    }
}
