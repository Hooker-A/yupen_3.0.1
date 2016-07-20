package com.huaop2p.yqs.utils.auto;

import android.content.Context;
import android.widget.Toast;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.entity.BusConstants;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.utils.net.GsonUtils;

/**
 * Created by zgq on 2016/5/3.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/3 17:07
 * 功能:  访问WebService的工具类,
 */
public class SoapUtils {
    /**
     * 请求是否执行成功
     * 弹出网络异常，提示异常信息
     *
     * @param response
     * @return
     */
    public static boolean isResponseOk(Context context, BaseResponseEntity response) {
        if (response == null) {
            ToastUtils.show(context, R.string.str_error_net_conect, Toast.LENGTH_SHORT);
            return false;
        } else if (response.ReturnStatus.equals(BusConstants.Return_Status_0)) {// && response.ReturnReason.equals(BusConstants.Return_Reason_OK)
            return true;
        } else {
            ToastUtils.show(context, response.ReturnReason, Toast.LENGTH_SHORT);
        }
        return false;
    }

    public static boolean isResponseOK(Context context, String json) {
        if (json == null) {
            ToastUtils.show(context, R.string.str_error_net_conect, Toast.LENGTH_SHORT);
            return false;
        }
        BaseResponseEntity entity = GsonUtils.getDateGson(null).fromJson(json, BaseResponseEntity.class);
        if (entity.ReturnStatus.equals(BusConstants.Return_Status_0)) {
            return true;
        } else {
//            ToastUtils.show(context, "提示："+entity.ReturnReason, Toast.LENGTH_SHORT);
        }
        return false;
    }

    /**
     * 请求是否执行成功
     * 弹出网络异常，提示异常信息
     *
     * @param response
     * @return
     */
    public static boolean isResponseOkF(Context context, BaseResponseEntity response) {
        if (response == null) {
            ToastUtils.show(context, R.string.str_error_net_conect, Toast.LENGTH_SHORT);
            return false;
        } else if (response.ReturnStatus.equals(BusConstants.Return_Status_0)) {// && response.ReturnReason.equals(BusConstants.Return_Reason_OK)
            return true;
        } else {
//            ToastUtils.show(context, response.ReturnReason, Toast.LENGTH_SHORT);
        }
        return false;
    }

    public static String getErrorString(int code) {
        String str = null;
        switch (code) {
            case 401:
                str="访问被拒绝";
                break;
            case 404 :
                str="无法找到指定位置的资源";
                break;
            case 500 :
                str="服务器遇到了意料不到的情况，不能完成客户的请求。";
                break;
            case 503:
                str="服务不可用";
                break;
            case 0:
                str="亲，您的网络不给力";
                break;
        }
        return str;
    }
}
