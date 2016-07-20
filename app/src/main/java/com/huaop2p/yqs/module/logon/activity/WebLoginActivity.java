package com.huaop2p.yqs.module.logon.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
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
 * 时间:  2016/7/1 10:53
 * 功能:  注册服务协议
 */
public class WebLoginActivity extends BaseActivity{

    private WebView wb_viewlogin;
    private WaitDialog waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weblogin);
        SetActivityTitle("注册服务协议");
        waitDialog=new WaitDialog(WebLoginActivity.this);
        waitDialog.show();
        initData();
        /**
         * 设置载入页面自适应手机屏幕，居中显示
         * */
        WebSettings webset1 = wb_viewlogin.getSettings();
        webset1.setJavaScriptEnabled(true);
        webset1.setAllowFileAccess(true);
        webset1.setBuiltInZoomControls(true);
        webset1.setUseWideViewPort(true);//设定支持viewport
        webset1.setLoadWithOverviewMode(true);
        wb_viewlogin.loadUrl(HttpConnector.proticalSoftUse);
        wb_viewlogin.getSettings().setJavaScriptEnabled(true);

//            webset1.setTextSize(WebSettings.TextSize.SMALLEST);//设置字体大小

        wb_viewlogin.setWebViewClient(new WebViewClient() {
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
        wb_viewlogin= (WebView) findViewById(R.id.wb_viewlogin);
    }
}
