package com.huaop2p.yqs.module.one_home.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.view.BaseAutoFragment;
import com.lidroid.xutils.util.LogUtils;

/**
 * Created by maoxiaofei on 2016/7/1.
 */
public class AboutYuPenFragment extends BaseAutoFragment {

    private WebView webview_msg;

    private String type;//1 关于余盆", 2 "业务模式", 3 "风险控件", 4 "合作伙伴"

    private Intent intent;
    String url;


    @Override
    public void showData() {

        type = getArguments().getString("type");
        url = getArguments().getString("url");

        setWebview();


    }

    @Override
    public View initViews(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_aboutyupen, null);
        webview_msg = (WebView) view.findViewById(R.id.webview_msg);

        return view;
    }

    private void setWebview(){
        /**
         * 设置载入页面自适应手机屏幕，居中显示
         * */
        WebSettings webset1 = webview_msg.getSettings();
        webset1.setJavaScriptEnabled(true);
        webset1.setAllowFileAccess(true);
        webset1.setBuiltInZoomControls(true);
        webset1.setUseWideViewPort(true);//设定支持viewport
        webset1.setLoadWithOverviewMode(true);
        if (type.equals("1")){
            webview_msg.loadUrl(url);
            LogUtils.e(url);
        }else if (type.equals("2")){
            webview_msg.loadUrl(url);
            LogUtils.e(url);
        }else if (type.equals("3")){
            webview_msg.loadUrl(url);
            LogUtils.e(url);
        }else {
            webview_msg.loadUrl(url);
            LogUtils.e(url);
        }
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
