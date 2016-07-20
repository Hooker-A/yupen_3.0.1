package com.huaop2p.yqs.module.three_mine.model.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.Bus.BusConstant;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.model.BaseModel;
import com.huaop2p.yqs.module.three_mine.model.ITransferRecordModel;
import com.huaop2p.yqs.module.three_mine.model.entity.TransferRecord;
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
 * Created by Administrator on 2016/5/20.
 * 作者：任洋
 * 功能：
 */
public class TransferModelImpl extends BaseModel<TransferRecord> implements ITransferRecordModel {


    @Override
    public void addTransferRecord(Map<String, Object> map, final OnRequestLinstener linstener) {
         FastJsonRequest<BaseResponseEntity<String>> request = new FastJsonRequest<BaseResponseEntity<String>>(
                HttpUrl.APPLICATION_TRANSFER, RequestMethod.POST, new TypeToken<BaseResponseEntity<String>>() {
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
                if (result.ReturnStatus.equals("0")) {
                    linstener.success(result);
                } else {
                    linstener.error(State.NULL, result.ReturnReason);
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

    @Override
    public void delTransferRecordById(Map<String, Object> map, final OnRequestLinstener linstener) {
        FastJsonRequest<BaseResponseEntity<String>> request = new FastJsonRequest<BaseResponseEntity<String>>(
                HttpUrl.CANCEL_TRANSFER, RequestMethod.POST, new TypeToken<BaseResponseEntity<String>>() {
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
                if (result.ReturnStatus.equals("0")) {
                    linstener.success(result);
                } else {
                    linstener.error(State.NULL, result.ReturnReason);
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
