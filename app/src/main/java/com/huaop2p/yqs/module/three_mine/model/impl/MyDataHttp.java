package com.huaop2p.yqs.module.three_mine.model.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.Bus.BusConstant;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.model.BaseModel;
import com.huaop2p.yqs.module.three_mine.model.entity.ActiveBean;
import com.huaop2p.yqs.module.three_mine.model.entity.DetaBean;
import com.huaop2p.yqs.module.three_mine.model.entity.SiteBean;
import com.huaop2p.yqs.module.three_mine.model.entity.ThirtyDayPointBean;
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
 * Created by zgq on 2016/5/19.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/19 16:36
 * 功能:  我的资料所有网络请求
 */
public class MyDataHttp extends BaseModel {

    private static class SingletonHolder {
        private static final MyDataHttp INSTANCE = new MyDataHttp();
    }

    private MyDataHttp() {
    }

    public static MyDataHttp getInstance() {
        return SingletonHolder.INSTANCE;
    }
    /**
     * 修改头像
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
    public void PostHeadPhoto(Map map, final OnRequestLinstener<BaseResponseEntity<String>> basePresenter,String url,int what, RequestMethod method, TypeToken typeToken){
        Request<BaseResponseEntity<String>> request=new FastJsonRequest<BaseResponseEntity<String>>(url,method,typeToken);
        String reqBody= GsonUtils.getGson().toJson(map);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<String>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<String>> response) {
                basePresenter.success(response.get());
                LogUtils.e("/////////////+" + response.get());

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e("/////////////+"+exception);


            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 修改个人信息
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
    public void PostPersonInfos(Map map, final OnRequestLinstener<BaseResponseEntity> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken){
        Request<BaseResponseEntity> request=new FastJsonRequest<BaseResponseEntity>(url,method,typeToken);
        String reqBody=GsonUtils.getGson().toJson(map);
        try {
            reqBody=DigestUtils.encrypt(reqBody,BusConstant.despas,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody=getNewString(reqBody);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity>() {
            @Override
            public void onStart(int what) {


            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity> response) {
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 收入明细
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
    public void GetDetail(Map map, final OnRequestLinstener<BaseResponseEntity<List<List<DetaBean>>>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken){
        Request<BaseResponseEntity<List<List<DetaBean>>>> request=new FastJsonRequest<BaseResponseEntity<List<List<DetaBean>>>>(url,method,typeToken);
        String reqBody=GsonUtils.getGson().toJson(map);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<List<List<DetaBean>>>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<List<DetaBean>>>> response) {
                    basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 更多积分纪录
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
    public void GetHistorypoint(Map map, final OnRequestLinstener<BaseResponseEntity<List<List<ThirtyDayPointBean>>>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken){
        Request<BaseResponseEntity<List<List<ThirtyDayPointBean>>>> request=new FastJsonRequest<BaseResponseEntity<List<List<ThirtyDayPointBean>>>>(url,method,typeToken);
        String reqBody=GsonUtils.getGson().toJson(map);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<List<List<ThirtyDayPointBean>>>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<List<ThirtyDayPointBean>>>> response) {
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * re热门活动
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
    public void PostActivitys(Map map, final OnRequestLinstener<BaseResponseEntity<List<ActiveBean>>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken){
        Request<BaseResponseEntity<List<ActiveBean>>> request=new FastJsonRequest<BaseResponseEntity<List<ActiveBean>>>(url,method,typeToken);
        String reqBody=GsonUtils.getGson().toJson(map);
        try {
            reqBody=DigestUtils.encrypt(reqBody,BusConstant.despas,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody=getNewString(reqBody);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<List<ActiveBean>>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<ActiveBean>>> response) {
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 添加地址
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
    public void PostAddAddress(Map map, final OnRequestLinstener<BaseResponseEntity<List<SiteBean>>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken){
        Request<BaseResponseEntity<List<SiteBean>>> request=new FastJsonRequest<BaseResponseEntity<List<SiteBean>>>(url,method,typeToken);
        String reqBody=GsonUtils.getGson().toJson(map);
        try {
            reqBody=DigestUtils.encrypt(reqBody,BusConstant.despas,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody=getNewString(reqBody);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<List<SiteBean>>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<SiteBean>>> response) {
            basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(exception.toString()+"/*/*/*/*/*/*/*/*/*/*/*/*/*");

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 查询收货地址
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
    public void GetAddress(Map map, final OnRequestLinstener<BaseResponseEntity<List<SiteBean>>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken){
        Request<BaseResponseEntity<List<SiteBean>>> request=new FastJsonRequest<BaseResponseEntity<List<SiteBean>>>(url,method,typeToken);
        String reqBody=GsonUtils.getGson().toJson(map);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<List<SiteBean>>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<SiteBean>>> response) {
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

}
