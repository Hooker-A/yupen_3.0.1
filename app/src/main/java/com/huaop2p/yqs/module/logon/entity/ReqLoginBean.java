package com.huaop2p.yqs.module.logon.entity;

/**
 * Created by zgq on 2016/4/22.
 * 作者:  zhu  guo qing
 * 时间:  2016/4/22 14:20
 * 功能:
 */
public class ReqLoginBean {
    /**
     * Api/Customer/Logins
     * {"DesEncToDes":"加密{"AppointEntity":{"LoginName":"账号","LoginPass":"密码"}}"}
     */
    public String DesEncToDes;

    public class DesEncToDesZ {
        public AppointEntityZ AppointEntity;
    }
    public class AppointEntityZ {
        public String LoginName;
        public String LoginPass;
    }
}
