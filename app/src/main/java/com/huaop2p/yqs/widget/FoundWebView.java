package com.huaop2p.yqs.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class FoundWebView extends WebView {
    ScrollInterface web;
    public boolean isTop=true;

    public FoundWebView(Context context) {
        super(context);
    }

    public FoundWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        isTop = true;
    }

    public FoundWebView(Context context, AttributeSet attrs) {

        super(context, attrs);
// TODO Auto-generated constructor stub   
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//Log.e("hhah",""+l+" "+t+" "+oldl+" "+oldt);        
        web.onSChanged(l, t, oldl, oldt);
    }

    public void setOnCustomScroolChangeListener(ScrollInterface t) {
        this.web = t;
    }

    /**
     * 定义滑动接口
     *
     * @param
     */
    interface ScrollInterface {

        public void onSChanged(int l, int t, int oldl, int oldt);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.setOnCustomScroolChangeListener(new ScrollInterface() {
            @Override
            public void onSChanged(int l, int t, int oldl, int oldt) {
                    isTop = false;
               //已经处于顶端
                if (FoundWebView.this.getScrollY() == 0) {
                    isTop = true;
                }

            }
        });
    }

}