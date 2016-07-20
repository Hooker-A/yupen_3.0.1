package com.huaop2p.yqs.module.four_set.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.WaitDialog;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.four_set.model.MyFinancialWeb;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgq on 2016/6/20.
 * 作者:  zhu  guo qing
 * 时间:  2016/6/20 15:53
 * 功能:  帮助中心
 */
public class MatterActivity extends BaseActivity implements View.OnClickListener{
    private WebView wb_hellp;
    private String url;
    private WaitDialog waitDialog;
    private ImageView img_Back;
    private TextView txt_titlewb;
    public static  String titletitle=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matter);
        waitDialog=new WaitDialog(MatterActivity.this);
        waitDialog.show();
        initData();
        httpData();
        txt_titlewb.setText("帮助中心");

        WebSettings webset1 = wb_hellp.getSettings();
        webset1.setJavaScriptEnabled(true);
        webset1.setAllowFileAccess(true);
        webset1.setBuiltInZoomControls(true);
        webset1.setUseWideViewPort(true);//设定支持viewport
        webset1.setLoadWithOverviewMode(true);
        wb_hellp.getSettings().setJavaScriptEnabled(true);
        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                txt_titlewb.setText(view.getTitle());

                titletitle=title.toString();
                txt_titlewb.setText(titletitle);
            }

        };


        // 设置setWebChromeClient对象
        wb_hellp.setWebChromeClient(wvcc);

        // 创建WebViewClient对象
        WebViewClient wvc = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
                wb_hellp.loadUrl(url);
                // 消耗掉这个事件。Android中返回True的即到此为止吧,事件就会不会冒泡传递了，我们称之为消耗掉
                return true;
            }
        };
        wb_hellp.setWebViewClient(wvc);

        wb_hellp.setWebViewClient(new WebViewClient() {
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



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wb_hellp.canGoBack()) {
            wb_hellp.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 网络加载
     */
    private void httpData() {
        Map map=new HashMap();
        MyFinancialWeb.getInstance().GetHelpCenter(map, new OnRequestLinstener<BaseResponseEntity>() {
            @Override
            public void success(BaseResponseEntity baseResponseEntity) {
                if (baseResponseEntity.ReturnStatus.equals("0")){
                    url=baseResponseEntity.ReturnMessage.toString();
                    wb_hellp.loadUrl(url);
                    waitDialog.dismiss();

                }else {
                    if (waitDialog!=null){
                        waitDialog.dismiss();
                    }
                    Toast toast=Toast.makeText(getApplicationContext(), baseResponseEntity.Remarks, Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.show();

                }



            }

            @Override
            public void error(int code, String error) {
                if (waitDialog!=null){
                    waitDialog.dismiss();
                }
            }
        }, HttpUrl.GetHelpCenter,0, RequestMethod.GET,new TypeToken<BaseResponseEntity>(){});

    }

    /**
     * 控件初始化
     */
    private void initData() {
        wb_hellp= (WebView) findViewById(R.id.wb_hellp);
        img_Back= (ImageView) findViewById(R.id.img_Back);
        img_Back.setOnClickListener(this);
        txt_titlewb= (TextView) findViewById(R.id.txt_titlewb);
    }


    @Override
    protected void onResume() {
        txt_titlewb.setText("帮助中心");
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_Back:
                if (wb_hellp.canGoBack()){
                    wb_hellp.goBack();
                    txt_titlewb.setText("帮助中心");
                    // 返回前一个页面

                }else {
                    finish();
                }
                break;
        }

    }

}
