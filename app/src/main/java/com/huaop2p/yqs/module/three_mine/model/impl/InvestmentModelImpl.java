package com.huaop2p.yqs.module.three_mine.model.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.Bus.BusConstant;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.model.BaseModel;
import com.huaop2p.yqs.module.three_mine.model.IInvestmentModel;
import com.huaop2p.yqs.module.three_mine.model.entity.Investment;
import com.huaop2p.yqs.utils.DigestUtils;
import com.huaop2p.yqs.utils.auto.SoapUtils;
import com.huaop2p.yqs.utils.net.CallServer;
import com.huaop2p.yqs.utils.net.FastJsonRequest;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.Response;

import java.util.Map;

/**
 * Created by Administrator on 2016/4/21.
 */
public class InvestmentModelImpl extends BaseModel<Investment> implements IInvestmentModel {

    public void getContract(Map<String, Object> map, final OnRequestLinstener linstener, String url, int what, RequestMethod method) {
        final FastJsonRequest<BaseResponseEntity<String>> request = new FastJsonRequest<BaseResponseEntity<String>>(
                url, method, new TypeToken<BaseResponseEntity<String>>() {
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
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<String>>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<String>> response) {
                BaseResponseEntity<String> result = response.get();
                if (result != null) {
                    try {
                        linstener.success(result);
                    }catch (Exception e){
                        linstener.error(State.NULL, "");
                    }
                } else {
                    linstener.error(State.NULL, "");
                }

            }

            @Override
            public void onFailed(int what, String url, Object tag,
                                 Exception exception, int responseCode, long networkMillis) {
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
