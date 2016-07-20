package com.huaop2p.yqs.module.two_wealth.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.linstener.EventBusLinstener;
import com.huaop2p.yqs.module.base.model.BaseModel;
import com.huaop2p.yqs.module.base.view.ScrollAbleFragment;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.logon.entity.LoginedBean;
import com.huaop2p.yqs.module.two_wealth.Js.JavaScriptObject;
import com.huaop2p.yqs.module.two_wealth.model.entity.EventBusEntity;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.cookie.DiskCookieStore;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/4/28.
 * 作者：任洋
 * 功能：WebviewFragment
 */
public class WealthModelFragment extends ScrollAbleFragment implements EventBusLinstener<String> {
    @ViewInject(R.id.mWebView)
    private WebView mWebView;
    private WebSettings settings;
    private String url;
    private boolean flag;
    private String url2;
    private TextView tv_hiht;

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    public void showData() {
        if (settings == null) {
            settings = mWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setAllowFileAccess(true);

            //  settings.setBuiltInZoomControls(true);
            /**自适应屏幕**/
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);
            // mWebView.getSettings().setBlockNetworkImage(true);
            settings.setDisplayZoomControls(false); //隐藏webview缩放按钮
            if (Build.VERSION.SDK_INT >= 19) {
                settings.setLoadsImagesAutomatically(true);
            } else {
                settings.setLoadsImagesAutomatically(false);
            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//            }
            mWebView.requestFocusFromTouch();
            settings.setDefaultTextEncodingName("utf-8");
            mWebView.addJavascriptInterface(new JavaScriptObject(getActivity()), "myObj");
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                }

                @Override
                public void onLoadResource(WebView view, String url) {

                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    String scheme = Uri.parse(url).getScheme();//还需要判断host
                    if (TextUtils.equals("huaoyupenyuyue", scheme)) {
                        //   mWebView.loadUrl(WealthModelFragment.this.url);
                        mWebView.goBackOrForward(-2);   //后退两页
                        return true;
                    } else {
                        url2 = url;
                        return false;
                    }
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    if (!settings.getLoadsImagesAutomatically()) {
                        settings.setLoadsImagesAutomatically(true);
                    }
                }

                @Override
                public void onReceivedError(WebView view, int errorCode,
                                            String description, String failingUrl) {
                    ToastUtils.show(AppApplication.mContext, "网页加载失败" + failingUrl);
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }
            });
            if (getArguments() == null || getArguments().getString("url") == null) {
                EventBus.getDefault().registerSticky(this);
            } else if (getArguments() != null)
            // mWebView.loadUrl(getArguments().getString("url"));
            {
                EventBus.getDefault().registerSticky(this);
                String url = getArguments().getString("url");
                BaseModel<Map<String, String>> model = new BaseModel<>();
                model.loadDetailById(null, new OnRequestLinstener<BaseResponseEntity<Map<String, String>>>() {
                    @Override
                    public void success(BaseResponseEntity<Map<String, String>> mapBaseResponseEntity) {
                        WealthModelFragment.this.url = mapBaseResponseEntity.ReturnMessage.get("Url");
                        EventBus.getDefault().post(new EventBusEntity<String>(mapBaseResponseEntity.ReturnMessage.get("Name"), 1));
                        synCookies(getActivity(), WealthModelFragment.this.url);
                        mWebView.loadUrl(WealthModelFragment.this.url);
                    }

                    @Override
                    public void error(int code, String error) {
                        ToastUtils.show(getActivity(), error);
                    }
                }, url, 0, RequestMethod.GET, new TypeToken<BaseResponseEntity<Map<String, String>>>() {
                });
            }

        }
    }

    /**
     * 同步一下cookie
     */
    public void synCookies(Context context, String url) {
        flag = true;
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
    public View initViews(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_wealth_model, null);
        tv_hiht = (TextView) view.findViewById(R.id.tv_hiht);
        return view;
    }

    @Override
    public View getScrollableView() {
        return mWebView;
    }

    @Override
    public boolean isBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mWebView.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mWebView.destroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    @Override
    public void onEventMainThread(String s) {
        if (getArguments() != null && getArguments().getString("url") != null)
            return;
        EventBus.getDefault().removeStickyEvent(String.class);
        EventBus.getDefault().unregister(this);
        Pattern p = Pattern.compile("^(http|www|ftp|https|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(s);
        if (m.find()) {
            url = s;
            mWebView.loadUrl(url);
        } else {
            tv_hiht.setVisibility(View.VISIBLE);
        }
    }

    public void onEventMainThread(LoginedBean bean) {
        if (flag && url2 != null) {
            synCookies(getActivity(), url2);
            mWebView.reload();
        }
    }

}
