package com.huaop2p.yqs.module.three_mine.model.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.Bus.BusConstant;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.model.BaseModel;
import com.huaop2p.yqs.module.three_mine.model.entity.RedPackage;
import com.huaop2p.yqs.utils.DigestUtils;
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
 * Created by maoxiaofei on 2016/6/8.
 */
public class RedPackageImpl extends BaseModel {

    private static class singleTop{
        private static final RedPackageImpl INTENST = new RedPackageImpl();
    }
    public static RedPackageImpl getintense(){
        return singleTop.INTENST;
    }
    public RedPackageImpl(){

    }

    //红包接口
    public void getRedPackage(Map map, final OnRequestLinstener<BaseResponseEntity<List<RedPackage>>> requestLinstener, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity<List<RedPackage>>> request = new FastJsonRequest<BaseResponseEntity<List<RedPackage>>>(url,method,typeToken);
         String reqBody = GsonUtils.getGson().toJson(map);

        try {
            reqBody= DigestUtils.encrypt(reqBody, BusConstant.despas, true);//这是加密和解密的
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody=getNewString(reqBody);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<List<RedPackage>>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<RedPackage>>> response) {
                requestLinstener.success(response.get());
                LogUtils.e(GsonUtils.getGson().toJson(response.get()));
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

    //红包接口
    public void getRedPackage2(Map map, final OnRequestLinstener<BaseResponseEntity<List<RedPackage>>> requestLinstener, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity<List<RedPackage>>> request = new FastJsonRequest<BaseResponseEntity<List<RedPackage>>>(url,method,typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);

        try {
            reqBody= DigestUtils.encrypt(reqBody, BusConstant.despas, true);//这是加密和解密的
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody=getNewString(reqBody);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<List<RedPackage>>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<RedPackage>>> response) {
                requestLinstener.success(response.get());
                LogUtils.e(GsonUtils.getGson().toJson(response.get()));
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

    //奖券接口
    public void getLottetyTicket(Map map, final OnRequestLinstener<BaseResponseEntity<List<RedPackage>>> requestLinstener, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity<List<RedPackage>>> request = new FastJsonRequest<BaseResponseEntity<List<RedPackage>>>(url,method,typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);

        try {
            reqBody= DigestUtils.encrypt(reqBody, BusConstant.despas, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody=getNewString(reqBody);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<List<RedPackage>>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<RedPackage>>> response) {
                requestLinstener.success(response.get());
                LogUtils.e(GsonUtils.getGson().toJson(response.get()));
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

    //奖券接口
    public void getLottetyTicket2(Map map, final OnRequestLinstener<BaseResponseEntity<List<RedPackage>>> requestLinstener, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity<List<RedPackage>>> request = new FastJsonRequest<BaseResponseEntity<List<RedPackage>>>(url,method,typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);

        try {
            reqBody= DigestUtils.encrypt(reqBody, BusConstant.despas, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody=getNewString(reqBody);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<List<RedPackage>>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<RedPackage>>> response) {
                requestLinstener.success(response.get());
                LogUtils.e(GsonUtils.getGson().toJson(response.get()));
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
