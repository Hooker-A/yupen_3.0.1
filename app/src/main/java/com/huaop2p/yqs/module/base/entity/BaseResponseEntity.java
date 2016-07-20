package com.huaop2p.yqs.module.base.entity;

/**
 * Created by zgq on 2016/4/15.
 * 作者:  zhu  guo qing
 * 时间:  2016/4/15 14:33
 * 功能:  网络请求基类
 */
public class BaseResponseEntity<T> {
    public String ReturnStatus;//0 成功  1 失败  2 异常
    public String ReturnReason;//失败的原因
    public T ReturnMessage;
    public String Remarks;// 成功  失败
    public String Total;  // 失败和异常为0  成功为成功的条数


    public String getReturnReason() {
        if (!ReturnStatus.equals("0") && !ReturnStatus.equals("1") && !ReturnStatus.equals("2")) {
            return "当前用户操作异常！";
        } else {

            return ReturnReason;
        }
    }

    public void setReturnReason(String returnReason) {
        ReturnReason = returnReason;
    }


}
