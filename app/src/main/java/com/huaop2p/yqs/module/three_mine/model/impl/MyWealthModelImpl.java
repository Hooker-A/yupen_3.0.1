package com.huaop2p.yqs.module.three_mine.model.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.Bus.BusConstant;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.model.BaseModel;
import com.huaop2p.yqs.module.three_mine.model.IMyWealthModel;
import com.huaop2p.yqs.module.three_mine.model.entity.Balance;
import com.huaop2p.yqs.module.three_mine.model.entity.MyWealth;
import com.huaop2p.yqs.module.three_mine.model.entity.QuotaBank;
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

/**
 * Created by Administrator on 2016/4/13.
 */
public class MyWealthModelImpl extends BaseModel<MyWealth> implements IMyWealthModel {
    @Override
    public void sign(final OnRequestLinstener basePresenter) {
        Request<BaseResponseEntity<String>> request = new FastJsonRequest<BaseResponseEntity<String>>(
                HttpUrl.GET_SIGN, RequestMethod.GET, new TypeToken<BaseResponseEntity<String>>() {
        });
        CallServer.getRequestInstance().add(0, request, new OnResponseListener<BaseResponseEntity<String>>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<String>> response) {
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag,
                                 Exception exception, int responseCode, long networkMillis) {
                basePresenter.error(responseCode, exception.getMessage());
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    @Override
    public void getBalance(final OnRequestLinstener basePresenter) {
        Request<BaseResponseEntity<List<Balance>>> request = new FastJsonRequest<BaseResponseEntity<List<Balance>>>(
                HttpUrl.GET_BALANCE, RequestMethod.GET, new TypeToken<BaseResponseEntity<List<Balance>>>() {
        });
        CallServer.getRequestInstance().add(0, request, new OnResponseListener<BaseResponseEntity<List<Balance>>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<Balance>>> response) {
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag,
                                 Exception exception, int responseCode, long networkMillis) {
                basePresenter.error(responseCode, exception.getMessage());
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    @Override
    public void selectIsSign(final OnRequestLinstener basePresenter) {
        Request<BaseResponseEntity<String>> request = new FastJsonRequest<BaseResponseEntity<String>>(
                HttpUrl.SELECT_IS_SIGN, RequestMethod.GET, new TypeToken<BaseResponseEntity<String>>() {
        });
        CallServer.getRequestInstance().add(0, request, new OnResponseListener<BaseResponseEntity<String>>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<String>> response) {
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag,
                                 Exception exception, int responseCode, long networkMillis) {
                basePresenter.error(responseCode, exception.getMessage());
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /***
     * 获取签名
     **/
    @Override
    public void getSign(final OnRequestLinstener basePresenter, Map<String, Object> map) {
        Request<BaseResponseEntity<String>> request = new FastJsonRequest<BaseResponseEntity<String>>(
                HttpUrl.GETSIGN, RequestMethod.POST, new TypeToken<BaseResponseEntity<String>>() {
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
        CallServer.getRequestInstance().add(0, request, new OnResponseListener<BaseResponseEntity<String>>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<String>> response) {
                BaseResponseEntity<String> result = response.get();
                if (result == null) {
                    basePresenter.error(State.NULL, "数据为空");
                } else if (result.ReturnStatus.equals("0")) {
                    basePresenter.success(result);
                } else {
                    basePresenter.error(State.NULL, result.ReturnReason);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
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
    public void getBank(final OnRequestLinstener basePresenter) {
        Request<BaseResponseEntity<Object>> request = new FastJsonRequest<BaseResponseEntity<Object>>(
                HttpUrl.FyTopUp, RequestMethod.GET, new TypeToken<BaseResponseEntity<QuotaBank>>() {
        });
        CallServer.getRequestInstance().add(0, request, new OnResponseListener<BaseResponseEntity<Object>>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<Object>> response) {
                BaseResponseEntity<Object> result = response.get();
                if (result == null) {
                    basePresenter.error(State.NULL, "数据为空");
                } else if (result.ReturnStatus.equals("0")) {
                    basePresenter.success(result);
                } else {
                    basePresenter.error(State.NULL, result.ReturnReason);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
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
    public void getAgreement(final OnRequestLinstener basePresenter,String url) {
        Request<BaseResponseEntity<String>> request = new FastJsonRequest<BaseResponseEntity<String>>(
                url, RequestMethod.GET, new TypeToken<BaseResponseEntity<String>>() {
        });
        CallServer.getRequestInstance().add(0, request, new OnResponseListener<BaseResponseEntity<String>>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<String>> response) {
                BaseResponseEntity<String> result = response.get();
                if (result == null) {
                    basePresenter.error(State.NULL, "数据为空");
                } else if (result.ReturnStatus.equals("0")) {
                    basePresenter.success(result);
                } else {
                    basePresenter.error(State.NULL, result.ReturnReason);
                }
            }
            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
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
}
