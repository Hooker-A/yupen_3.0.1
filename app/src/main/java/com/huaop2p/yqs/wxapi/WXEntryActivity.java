package com.huaop2p.yqs.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.huaop2p.yqs.module.base.entity.BusConstants;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


/**
 * Created by zgq on 2016/7/1.
 * 作者:  zhu  guo qing
 * 时间:  2016/7/1 14:32
 * 功能:  微信分享支持类
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        api = WXAPIFactory.createWXAPI(this, BusConstants.WX_APP_ID, false);
        api.handleIntent(getIntent(), this);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onReq(BaseReq arg0) { }

    @Override
    public void onResp(BaseResp resp) {
//        LogManager.show(TAG, "resp.errCode:" + resp.errCode + ",resp.errStr:"
//                + resp.errStr, 1);
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                //分享成功
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //分享取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //分享拒绝
                break;
        }
    }
}
