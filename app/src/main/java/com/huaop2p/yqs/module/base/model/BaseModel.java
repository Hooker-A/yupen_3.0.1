package com.huaop2p.yqs.module.base.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.Bus.BusConstant;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.two_wealth.model.entity.EventBusEntity;
import com.huaop2p.yqs.utils.DigestUtils;
import com.huaop2p.yqs.utils.auto.SoapUtils;
import com.huaop2p.yqs.utils.net.CallServer;
import com.huaop2p.yqs.utils.net.FastJsonRequest;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.Response;

import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/4/18.
 */
public class BaseModel<T> implements IBaseModel<T> {
    public Gson gson = new Gson();

    @Override
    public void loadDatas(Map<String, Object> map, final OnRequestLinstener linstener, String url, int what, RequestMethod method, TypeToken typeToken) {
        loadDatas(map, linstener, url, what, method, typeToken, null);
    }

    @Override
    public void loadDatas(Map<String, Object> map, final OnRequestLinstener linstener, String url, int what, RequestMethod method, TypeToken typeToken, Object tag) {
        final FastJsonRequest<BaseResponseEntity<List<T>>> request = new FastJsonRequest<BaseResponseEntity<List<T>>>(
                url, method, typeToken);
        if (map != null) {
            String str = gson.toJson(map);
            try {
                str = DigestUtils.encrypt(str, BusConstant.despas, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            str = getNewString(str);
            request.setRequestBody(str);
        }
        if (tag != null)
            request.setTag(tag);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<List<T>>>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public  void onSucceed(int what, Response<BaseResponseEntity<List<T>>> response) {
                BaseResponseEntity<List<T>> result = response.get();
                if (result != null && result.ReturnStatus.equals("0")) {
                    linstener.success(result);
                } else {
                    linstener.error(State.NULL, result.ReturnReason);
                }

            }

            @Override
            public void onFailed(int what, String url, Object tag,
                                 Exception exception, int responseCode, long networkMillis) {
                Log.i("ddd",exception.getMessage());
                if (responseCode!=0){
                    loadErrorWEB();

                }
                String str = SoapUtils.getErrorString(responseCode);
                if (str == null) {
                    str = exception.getMessage();
                }
                linstener.error(responseCode, str);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    @Override
    public void loadDetailById(Map<String, Object> map, final OnRequestLinstener linstener, String url, int what, RequestMethod method, TypeToken typeToken) {
        loadDetailById(map, linstener, url, what, method, typeToken, null);
    }

    @Override
    public void loadDetailById(Map<String, Object> map, final OnRequestLinstener basePresenter, String url, int what, RequestMethod method, TypeToken typeToken, Object tag) {
        Request<BaseResponseEntity<T>> request = new FastJsonRequest<BaseResponseEntity<T>>(
                url, method, typeToken);
        if (map != null) {
            String str = gson.toJson(map);
            try {
                str = DigestUtils.encrypt(str, BusConstant.despas, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            str = getNewString(str);
            request.setRequestBody(str);
        }
        if (tag != null)
            request.setTag(tag);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<T>>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<T>> response) {
                BaseResponseEntity<T> result = response.get();
                if (result == null) {
                    basePresenter.error(State.NULL, "数据为空");
                } else if (result.ReturnStatus.equals("0")) {
                    basePresenter.success(result);
                } else {
                    basePresenter.error(State.NULL, result.ReturnReason);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag,
                                 Exception exception, int responseCode, long networkMillis) {
                if (responseCode!=0){
                    loadErrorWEB();

                }

                String str = SoapUtils.getErrorString(responseCode);
                if (str == null) {
                    str = exception.getMessage();
                }
                basePresenter.error(responseCode, str);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    @Override
    public List<T> loadNativeData() {
        return null;
    }

    @Override
    public T loadNativeDataById(String id) {
        return null;
    }

    @Override
    public void saveDatas(List<T> t) {

    }


    @Override
    public void delAllDatas() {

    }


    @Override
    public void loadErrorWEB() {
        final Request<Map<String, String>> request = new FastJsonRequest<Map<String, String>>(
                "http://infotip.yupen.cn/api/values", RequestMethod.GET, new TypeToken<Map<String, String>>() {
        });
        CallServer.getRequestInstance().add(0, request, new OnResponseListener<Map<String, String>>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<Map<String, String>> response) {
                Map<String, String> result = response.get();
                EventBusEntity<Map<String, String>> entity = new EventBusEntity<Map<String, String>>(result, 2);
                EventBus.getDefault().postSticky(entity);
            }

            @Override
            public void onFailed(int what, String url, Object tag,
                                 Exception exception, int responseCode, long networkMillis) {
            }

            @Override
            public void onFinish(int what) {
            }
        });
    }

    public String getNewString(String str) {
        String newStr = "{\"DesEncToDes\":\"" + str.trim() + "\"}";
        return newStr;
    }
}
