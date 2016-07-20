package com.huaop2p.yqs.module.two_wealth.Js;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.huaop2p.yqs.module.three_mine.activity.WebActivity;

public class JavaScriptObject {
    private  Context mContxt;

    public JavaScriptObject(Context mContxt) {
        this.mContxt = mContxt;
    }
    @JavascriptInterface
    public void loadUrl(String url) {
        Intent intent = new Intent(mContxt, WebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title","出借服务协议");
        mContxt.startActivity(intent);
    }
}
