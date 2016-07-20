package com.huaop2p.yqs.module.one_home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.one_home.bean.HomeActionBean;
import com.huaop2p.yqs.module.one_home.bean.YieldBean;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.cookie.DiskCookieStore;

/**
 * Created by maoxiaofei on 2016/6/30.
 */
public class SiMuZhaiActivity extends BaseActivity implements View.OnClickListener {

    private WebView webview_simuzhai;

    private Intent intent;
    private YieldBean yieldBean;
    private String url;
    private String name;
    private WebSettings settings;
    int tag;
    private HomeActionBean homeActionBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simuzhai);

        webview_simuzhai = (WebView) findViewById(R.id.webview_simuzhai);


        intent = getIntent();

        tag = intent.getIntExtra("tag", 1);//这个tag值  1 是从首页左上角 活动，2 是点击首页binner跳转 3 是点击固定收益率



        if (tag == 1){
            homeActionBean = (HomeActionBean)intent.getSerializableExtra("bean");
            SetActivityTitle(homeActionBean.Title);
            synCookies(this, homeActionBean.ActivityUrl);
            setWebview(homeActionBean.ActivityUrl);
        }else if (tag == 2){
            SetActivityTitle("详情");
            synCookies(this, intent.getStringExtra("url"));
            setWebview(intent.getStringExtra("url"));
        }else {
            yieldBean = (YieldBean) intent.getSerializableExtra("yieldbean");
            url = yieldBean.Url;
            name = yieldBean.Name;
            SetActivityTitle(name);
            synCookies(this, url);
            setWebview(url);
        }



    }

    private void setWebview(String url){
        webview_simuzhai.loadUrl(url);

        settings = webview_simuzhai.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);

        webview_simuzhai.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
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
