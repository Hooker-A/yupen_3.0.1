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
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.one_home.bean.HomeMseZiXunBean;
import com.lidroid.xutils.util.LogUtils;

/**
 * Created by maoxiaofei on 2016/6/30.
 */
public class ZiXun_WebViewActivity extends BaseActivity {

    private WebView webview_msg;

    private HomeMseZiXunBean homeMseZiXunBeans;
    private Intent intent;
    String url;
    private String InfoType;
    private String Url;

    int tag;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zixunwebview);

        webview_msg = (WebView) findViewById(R.id.webview_msg);

        intent = getIntent();

        tag = intent.getIntExtra("tag", 1);
        homeMseZiXunBeans = (HomeMseZiXunBean) intent.getSerializableExtra("bean");
        if (tag == 0) {//这是从首页listview跳转过来
            url = intent.getStringExtra("url");
            setWebview(url);
        } else {//这是从余盆资讯里跳转过来
            id = intent.getStringExtra("id");
            url = HttpUrl.Get_yupenzixun + id;
            setWebview(url);
        }


        SetActivityTitle(homeMseZiXunBeans.ClassCName);

    }

    private void setWebview(String url) {
        /**
         * 设置载入页面自适应手机屏幕，居中显示
         * */
        WebSettings webset1 = webview_msg.getSettings();
        webset1.setJavaScriptEnabled(true);
        webset1.setAllowFileAccess(true);
        webset1.setBuiltInZoomControls(true);
        webset1.setUseWideViewPort(true);//设定支持viewport
        webset1.setLoadWithOverviewMode(true);

        webview_msg.loadUrl(url);
        LogUtils.e(url);
        LogUtils.e(url);

        webview_msg.getSettings().setJavaScriptEnabled(true);

//            webset1.setTextSize(WebSettings.TextSize.SMALLEST);//设置字体大小

        webview_msg.setWebViewClient(new WebViewClient() {
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
