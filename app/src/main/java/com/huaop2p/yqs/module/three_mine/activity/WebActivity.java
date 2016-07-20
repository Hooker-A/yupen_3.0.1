package com.huaop2p.yqs.module.three_mine.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/6/7.
 * 作者：任洋
 * 功能：网页界面
 */
public class WebActivity extends BaseAutoActivity {
    private WebView mWebView;
    private WebSettings settings;
    private String param;
    private TextView tv_title;
    private ProgressBar bar;
    private TextView tv_hiht;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initViews() {
        mWebView = (WebView) findViewById(R.id.mWebView);
        tv_title = (TextView) findViewById(R.id.tv_title);
        bar = (ProgressBar) findViewById(R.id.myProgressBar);
        tv_hiht= (TextView) findViewById(R.id.tv_hiht);
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void initData() {
        tv_title.setText(getIntent().getStringExtra("title"));
        param = getIntent().getStringExtra("param");
        settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);  //设置可以访问文件
        settings.setBuiltInZoomControls(true);
        /**自适应屏幕**/
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        settings.setDisplayZoomControls(false); //隐藏webview缩放按钮

        settings.setDefaultTextEncodingName("UTF-8");
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    bar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                String scheme = Uri.parse(url).getScheme();//还需要判断host
                if (TextUtils.equals("huaoyupen", scheme)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.putExtra("money", getIntent().getDoubleExtra("money", 0));
                    intent.putExtra("url", getIntent().getStringExtra("url"));
                    intent.putExtra("bank", getIntent().getStringExtra("bank"));
                    intent.putExtra("flag",getIntent().getBooleanExtra("flag",false));
                    intent.putExtra("cardNum", getIntent().getStringExtra("cardNum"));
                    startActivity(intent);
                    finish();
                    return;
                } else {

                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        String url=getIntent().getStringExtra("url");
        if (url==null)
            return;
        if (param == null) {
            mWebView.loadUrl(url);
        } else {
            try {
                mWebView.postUrl(url, param.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public Object getObject() {
        return this;
    }
    public void onEventMainThread(String s) {
        EventBus.getDefault().removeStickyEvent(String.class);
        EventBus.getDefault().unregister(this);
        Pattern p = Pattern.compile("^(http|www|ftp|https|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(s);
        if (m.find()) {
            mWebView.loadUrl(s);
        } else {
            tv_hiht.setVisibility(View.VISIBLE);
        }
    }

}
