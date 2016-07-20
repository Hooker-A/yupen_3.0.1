package com.huaop2p.yqs.module.three_mine.model.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.model.BaseModel;
import com.huaop2p.yqs.module.three_mine.model.entity.ThirtyDayPointBean;
import com.huaop2p.yqs.utils.net.CallServer;
import com.huaop2p.yqs.utils.net.FastJsonRequest;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.Response;

import java.util.List;
import java.util.Map;

/**
 * Created by maoxiaofei on 2016/6/16.
 */
public class PointMarketImpl extends BaseModel<String> {

    private static class singleTop {
        private static final PointMarketImpl INTENSE = new PointMarketImpl();
    }

    public static PointMarketImpl getintense(){
        return singleTop.INTENSE;
    }

    public PointMarketImpl() {

    }

    //得到总积分
    public void GetPoint(Map map, final OnRequestLinstener<BaseResponseEntity<String>> onRequestLinstener,String url,int what,RequestMethod method,TypeToken typeToken){

        Request<BaseResponseEntity<String>> request = new FastJsonRequest<BaseResponseEntity<String>>(url,method,typeToken);
        String rebody = GsonUtils.getGson().toJson(map);
        request.setRequestBody(rebody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<String>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<String>> response) {
                LogUtils.e(GsonUtils.getGson().toJson(response.get()));
                onRequestLinstener.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(exception+"");
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    //最近30天的纪录，
    public void GetThirtyPoint(Map map, final OnRequestLinstener<BaseResponseEntity<List<ThirtyDayPointBean>>> onRequestLinstener,String url,int what,RequestMethod method,TypeToken typeToken){

        Request<BaseResponseEntity<List<ThirtyDayPointBean>>> request = new FastJsonRequest<BaseResponseEntity<List<ThirtyDayPointBean>>>(url,method,typeToken);
        String rebody = GsonUtils.getGson().toJson(map);
        request.setRequestBody(rebody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<List<ThirtyDayPointBean>>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<ThirtyDayPointBean>>> response) {
                LogUtils.e(GsonUtils.getGson().toJson(response.get()));
                onRequestLinstener.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(exception+"");
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }
    //更多纪录
    public void GetAllPoint(Map map, final OnRequestLinstener<BaseResponseEntity<List<ThirtyDayPointBean>>> onRequestLinstener,String url,int what,RequestMethod method,TypeToken typeToken){

        Request<BaseResponseEntity<List<ThirtyDayPointBean>>> request = new FastJsonRequest<BaseResponseEntity<List<ThirtyDayPointBean>>>(url,method,typeToken);
        String rebody = GsonUtils.getGson().toJson(map);
        request.setRequestBody(rebody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<List<ThirtyDayPointBean>>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<ThirtyDayPointBean>>> response) {
                LogUtils.e(GsonUtils.getGson().toJson(response.get()));
                onRequestLinstener.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(exception+"");
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

}
