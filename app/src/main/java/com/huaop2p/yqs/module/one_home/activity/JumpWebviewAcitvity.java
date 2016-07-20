package com.huaop2p.yqs.module.one_home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.one_home.bean.HomeActionBean;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.cookie.DiskCookieStore;

/**
 * Created by maoxiaofei on 2016/7/1.
 */
public class JumpWebviewAcitvity extends BaseActivity implements View.OnClickListener{

    private WebView webview_jump;
    private Intent intent;
    String url;
    int tag;

    private HomeActionBean homeActionBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);


        webview_jump = (WebView) findViewById(R.id.webview_jump);
        intent = getIntent();

        url = intent.getStringExtra("url");
        tag = intent.getIntExtra("tag",1);
        homeActionBean = (HomeActionBean)intent.getSerializableExtra("bean");

        if (tag == 1){
            SetActivityTitle(homeActionBean.Title);
            synCookies(this, homeActionBean.ActivityUrl);
            setWebview(homeActionBean.ActivityUrl);
        }else {
            SetActivityTitle("详情");
            synCookies(this, url);
            setWebview(url);
        }

    }

    private void setWebview(String url){
        /**
         * 设置载入页面自适应手机屏幕，居中显示
         * */
        WebSettings webset1 = webview_jump.getSettings();
        webset1.setJavaScriptEnabled(true);
        webset1.setAllowFileAccess(true);
        webset1.setBuiltInZoomControls(true);
        webset1.setUseWideViewPort(true);//设定支持viewport
        webset1.setLoadWithOverviewMode(true);

        webview_jump.loadUrl(url);
        LogUtils.e(url);

        webview_jump.getSettings().setJavaScriptEnabled(true);

//            webset1.setTextSize(WebSettings.TextSize.SMALLEST);//设置字体大小

        webview_jump.setWebViewClient(new WebViewClient() {
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

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String scheme = Uri.parse(url).getScheme();//还需要判断host
                if (TextUtils.equals("huaoyupenyuyue", scheme)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                    startActivity(intent);
                    finish();
                    return true;
                } else {
                    return false;
                }
            }

        });

    }

    /**
     * 同步一下cookie
     */
    public void synCookies(Context context, String url) {
        String cookie = null;
        if (DiskCookieStore.INSTANCE.getCookies().size() == 0)
            cookie = "";
        else
            cookie = DiskCookieStore.INSTANCE.getCookies().get(0).toString();
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        cookieManager.setCookie(url, cookie);//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
