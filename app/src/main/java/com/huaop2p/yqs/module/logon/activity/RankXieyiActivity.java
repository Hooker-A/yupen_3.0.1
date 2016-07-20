package com.huaop2p.yqs.module.logon.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.WaitDialog;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.contant.HttpConnector;

/**
 * Created by zgq on 2016/7/1.
 * 作者:  zhu  guo qing
 * 时间:  2016/7/1 10:30
 * 功能: 托管服务协议
 */
public class RankXieyiActivity extends BaseActivity implements View.OnClickListener{
    private WebView wb_view;
    private WaitDialog waitDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xieyi);
        SetActivityTitle("托管服务协议");
        initData();
        waitDialog=new WaitDialog(RankXieyiActivity.this);
        waitDialog.show();
        /**
         * 设置载入页面自适应手机屏幕，居中显示
         * */
        WebSettings webset1 = wb_view.getSettings();
        webset1.setJavaScriptEnabled(true);
        webset1.setAllowFileAccess(true);
        webset1.setBuiltInZoomControls(true);
        webset1.setUseWideViewPort(true);//设定支持viewport
        webset1.setLoadWithOverviewMode(true);
        wb_view.loadUrl(HttpConnector.proticalSoftUse);
        wb_view.getSettings().setJavaScriptEnabled(true);

//            webset1.setTextSize(WebSettings.TextSize.SMALLEST);//设置字体大小

        wb_view.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                waitDialog.show();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                waitDialog.dismiss();

            }

        });
    }

    private void initData() {
        wb_view= (WebView) findViewById(R.id.wb_view);


    }

    @Override
    public void onClick(View v) {

    }
}
