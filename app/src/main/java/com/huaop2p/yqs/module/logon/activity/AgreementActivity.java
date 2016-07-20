package com.huaop2p.yqs.module.logon.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.logon.entity.LoginedBean;
import com.huaop2p.yqs.module.logon.entity.NewAgreeMentIdBean;
import com.huaop2p.yqs.module.logon.model.MyFinancialWeb;
import com.huaop2p.yqs.module.one_home.entity.AgreeMentVersionBean;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgq on 2016/7/8.
 * 作者:  zhu  guo qing
 * 时间:  2016/7/8 15:50
 * 功能:  显示协议
 */
public class AgreementActivity extends BaseActivity implements View.OnClickListener{
    private WebView mWebView;
    private WebSettings settings;
    public static final String STRING="bean";
    public static final String ID="id";
    private String reqLoginUrl = "";
    String id;
    private Button mButtonConfirm;
    AgreeMentVersionBean bean;
    ImageView imgBack;
    TextView title;
    TextView middle;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement_pre);
        initData();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.goBack();
                imgBack.setVisibility(View.GONE);
                settings.setTextSize(WebSettings.TextSize.NORMAL);
            }
        });
        bean = (AgreeMentVersionBean) getIntent().getSerializableExtra("bean");
        title.setText(bean.AgreeMentTitle);
        middle.setText(bean.AgreeMentContent);
        mButtonConfirm.setText(bean.AgreeMentBottom);
        mWebView.loadUrl(bean.AgreeMentUrl);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
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
                settings.setTextSize(WebSettings.TextSize.SMALLER);
                imgBack.setVisibility(View.VISIBLE);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setLoadWithOverviewMode(true);
        settings.setAllowFileAccess(true);



    }

    private void initData() {
        mButtonConfirm = (Button) findViewById(R.id.btn_confirm);
        mButtonConfirm.setOnClickListener(this);

        mWebView = (WebView) findViewById(R.id.wb_html);
        settings = mWebView.getSettings();
        mWebView.getSettings().setDefaultFontSize(1);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setVisibility(View.GONE);
        title= (TextView) findViewById(R.id.txt_title);
        middle=(TextView)findViewById(R.id.txt_m);
    }

    /**
     * {"AppointEntity":{NewAgreeMentId":"1"}}
     */
    private  void httpData(){
        Map sMap=new HashMap();
        sMap.put("NewAgreeMentId", bean.KeyId);
        Map dmap=new HashMap();
        dmap.put("AppointEntity", sMap);
        MyFinancialWeb.getInstance().setNewAgreement(dmap, new OnRequestLinstener<BaseResponseEntity<AgreeMentVersionBean>>() {
            @Override
            public void success(BaseResponseEntity<AgreeMentVersionBean> agreeMentVersionBeanBaseResponseEntity) {
//                LogUtils.e(agreeMentVersionBeanBaseResponseEntity.ReturnMessage.AgreeMentVersion.toString());
//                LoginedBean bean= ShareLocalUser.getInstance(AgreementActivity.this).getLoginUser();
//                bean.=baseResponseEntity.ReturnMessage+"";
//                ShareLocalUser.getInstance(NickActivity.this).addLoginUser(bean);
//                txt_nick.setText(bean.Nickname);
                LogUtils.e("-------------agr------------------");
            }

            @Override
            public void error(int code, String error) {

            }
        }, HttpUrl.SetNewAgreement, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity<AgreeMentVersionBean>>() {
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm:
                httpData();

                finish();
                break;
        }
    }
}
