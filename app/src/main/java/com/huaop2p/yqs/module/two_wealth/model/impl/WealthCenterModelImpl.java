package com.huaop2p.yqs.module.two_wealth.model.impl;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.Bus.BusConstant;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.entity.DBEntity;
import com.huaop2p.yqs.module.base.model.BaseModel;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.two_wealth.model.IWealthCenterModel;
import com.huaop2p.yqs.module.two_wealth.model.entity.Order;
import com.huaop2p.yqs.module.two_wealth.model.entity.WealthCenter;
import com.huaop2p.yqs.utils.DigestUtils;
import com.huaop2p.yqs.utils.auto.SoapUtils;
import com.huaop2p.yqs.utils.net.CallServer;
import com.huaop2p.yqs.utils.net.FastJsonRequest;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.Response;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/11.
 */
public class WealthCenterModelImpl extends BaseModel<WealthCenter> implements IWealthCenterModel {
    /***
     * 加载本地数据 type:区分余信余房余车
     **/
    @Override
    public List<WealthCenter> loadNativeData(int type) {
        List<WealthCenter> yuXinBaos = null;
        try {
            if (AppApplication.dbUtils.tableIsExist(DBEntity.class)) {
                DBEntity dbEntity = AppApplication.dbUtils.findFirst(Selector.from(DBEntity.class).where("className", "=", WealthCenter.class.getName()).and("type", "=", type));
                if (dbEntity != null)
                    yuXinBaos = gson.fromJson(dbEntity.content, new TypeToken<List<WealthCenter>>() {
                    }.getType());
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return yuXinBaos;
    }

    public void getH5(Map<String, Object> map, final OnRequestLinstener basePresenter) {
        //, HttpUrl.xinInvestIntroduce, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity<String>>() {
        Request<BaseResponseEntity<Map<String, String>>> request = new FastJsonRequest<BaseResponseEntity<Map<String, String>>>(
                HttpUrl.xinInvestIntroduce, RequestMethod.POST, new TypeToken<BaseResponseEntity<Map<String, String>>>() {
        });
        if (map != null)

        {
            String str = gson.toJson(map);
            try {
                str = DigestUtils.encrypt(str, BusConstant.despas, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            str = getNewString(str);
            request.setRequestBody(str);
        }
        CallServer.getRequestInstance().
                add(0, request, new OnResponseListener<BaseResponseEntity<Map<String, String>>>() {
                            @Override
                            public void onStart(int what) {
                            }

                            @Override
                            public void onSucceed(int what, Response<BaseResponseEntity<Map<String, String>>> response) {
                                BaseResponseEntity<Map<String, String>> result = response.get();
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
                                String str = SoapUtils.getErrorString(responseCode);
                                if (str == null) {
                                    str = exception.getMessage();
                                }
                                basePresenter.error(responseCode, str);
                            }

                            @Override
                            public void onFinish(int what) {

                            }
                        }

                );
    }

    /***
     * 删除本地数据
     **/
    @Override
    public void delAllDatas(int type) {
        try {
            AppApplication.dbUtils.delete(DBEntity.class, WhereBuilder.b("className", "=", WealthCenter.class.getName()).and("type", "=", type));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /***
     * 保存数据到本地
     **/
    @Override
    public void saveDatas(List<WealthCenter> t, int type) {
        try {
            DBEntity dbEntity = new DBEntity();
            dbEntity.className = WealthCenter.class.getName();
            dbEntity.type = type;
            dbEntity.content = gson.toJson(t);
            AppApplication.dbUtils.save(dbEntity);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pay(Map<String, Object> map, String url, final OnRequestLinstener linstener) {
        final FastJsonRequest<BaseResponseEntity<Order>> request = new FastJsonRequest<BaseResponseEntity<Order>>(
                url, RequestMethod.POST, new TypeToken<BaseResponseEntity<Order>>() {
        });
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
        CallServer.getRequestInstance().add(0, request, new OnResponseListener<BaseResponseEntity<Order>>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<Order>> response) {
                BaseResponseEntity<Order> result = response.get();

                if (result != null && result.ReturnStatus.equals("0")) {
                    linstener.success(result);
                } else {
                    linstener.error(State.NULL, result.ReturnReason);
                }

            }

            @Override
            public void onFailed(int what, String url, Object tag,
                                 Exception exception, int responseCode, long networkMillis) {
                Log.i("eee", exception.getMessage());
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
}
