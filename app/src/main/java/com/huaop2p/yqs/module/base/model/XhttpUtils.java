package com.huaop2p.yqs.module.base.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.ProgressDialogUtils;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.four_set.service.MQTTService;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;

/**
 * Created by zgq on 2016/7/5.
 * 作者:  zhu  guo qing
 * 时间:  2016/7/5 11:22
 * 功能:
 */
public class XhttpUtils extends HttpUtils {

    private static final XhttpUtils http = new XhttpUtils(30000);
    public static XhttpUtils getInstance() {
        return http;
    }

    public XhttpUtils() {
        super();
    }

    public XhttpUtils(int connTimeout) {
        super(connTimeout);
    }

    public static String cookies = "";

    public XhttpUtils(String userAgent) {
        super(userAgent);
    }

    public XhttpUtils(int connTimeout, String userAgent) {
        super(connTimeout, userAgent);
    }

    public <T> HttpHandler<T> send(HttpRequest.HttpMethod method, String url, String reqBody,
                                   RequestCallBack<T> callBack) {
        if (reqBody == null)
            reqBody = "";
        RequestParams reqParam = new RequestParams();
        reqParam.setContentType("application/json");
        reqParam.addHeader("Accept", "application/json");
        reqParam.addHeader("X-Equipment", "Android");
        reqParam.addHeader("X-Equiment-Id", MQTTService.getMqttClientId());
        HttpEntity reqEntity = null;
        try {
            reqEntity = new StringEntity(reqBody, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            Log.d("请求格式", e.getMessage().toString());
        }
        reqParam.setBodyEntity(reqEntity);
        return super.send(method, url, reqParam, callBack);
    }

    /**
     * post 请求
     *
     * @param context
     * @param url
     * @param reqBody
     * @param progress
     * @param netSuccess
     * @throws UnsupportedEncodingException
     */
    public void post(final Context context, String url, String reqBody, boolean isProgress, String progress, final INetSuccess netSuccess) throws UnsupportedEncodingException {
        send(HttpRequest.HttpMethod.POST, context, url, reqBody, isProgress, progress, netSuccess);
    }
    /**
     * send 请求
     *
     * @param method
     * @param context
     * @param url
     * @param reqBody
     * @param progress
     * @param netSuccess
     * @throws UnsupportedEncodingException
     */
    public void send(HttpRequest.HttpMethod method, final Context context, String url, String reqBody, boolean isProgress, String progress, final INetSuccess netSuccess) throws UnsupportedEncodingException {
        if (!isNetworkConnected(context)) {
            Toast.makeText(context, context.getString(R.string.net_error), Toast.LENGTH_SHORT).show();
            return;
        }
        if (isProgress) {
            if (progress == null || "".equals(progress)) {
                ProgressDialogUtils.showProgressDialog(context, context.getString(R.string.loading));
            } else {
                ProgressDialogUtils.showProgressDialog(context, progress);
            }
        }
        send(method, url, reqBody, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                netSuccess.netSuccess(responseInfo);
                ProgressDialogUtils.dismissProgressDialog();
            }

            @Override
            public void onFailure(HttpException error, String msg) {

                netSuccess.netFailure(msg);
                ProgressDialogUtils.dismissProgressDialog();
            }
        });
    }

    /**
     * post 请求
     *
     * @param url
     * @param reqBody
     * @param callBack
     * @param <T>
     * @return
     * @throws UnsupportedEncodingException
     */
    public <T> HttpHandler<T> post(String url, String reqBody, RequestCallBack<T> callBack) throws UnsupportedEncodingException {

        return send(HttpRequest.HttpMethod.POST, url, reqBody, callBack);
    }



    /**
     * get 请求
     *
     * @param url
     * @param callBack
     * @param <T>
     * @return
     * @throws UnsupportedEncodingException
     */
    public <T> HttpHandler<T> get(String url, RequestCallBack<T> callBack) throws UnsupportedEncodingException {
        return send(HttpRequest.HttpMethod.GET, url, "", callBack);
    }

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager == null) {
                return false;
            }
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }









    /**
     * 绑定红包
     *
     * @param method
     * @param url
     * @param reqBody
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> HttpHandler<T> send3(HttpRequest.HttpMethod method, String url, String reqBody,
                                    RequestCallBack<T> callBack) {
        if (reqBody == null)
            reqBody = "";
        RequestParams reqParam = new RequestParams();
        reqParam.setContentType("application/json");
        reqParam.addHeader("Accept", "application/json");
//        postMethod.addHeader("X-Equipment", "Android");
        reqParam.addHeader("X-Equipment", "Android");
        String cookie = ShareLocalUser.getInstance(AppApplication.getContext()).getCookie2();
        if (cookie != null)
            reqParam.addHeader("Cookie", cookie);
        HttpEntity reqEntity = null;
        try {
            reqEntity = new StringEntity(reqBody, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            Log.d("请求格式", e.getMessage().toString());
        }
        reqParam.setBodyEntity(reqEntity);
        return super.send(method, url, reqParam, callBack);
    }

    /**
     * 访问服务器回调接口
     */
    public interface INetSuccess {
        void netSuccess(ResponseInfo<String> responseInfo);

        void netFailure(String msg);
    }

    /**
     * 请求响应回调接口
     *
     * @param <T>
     */
    public interface IResponse<T> {
        void onSuccess(T resBean);

        void onFailure(String msg);
    }
}
