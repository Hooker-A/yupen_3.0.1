package com.huaop2p.yqs.module.four_set.entity;

/**
 * Created by zgq on 2016/4/15.
 * 作者:  zhu  guo qing
 * 时间:  2016/4/15 14:39
 * 功能:
 */
public class ReqBaseBean<T> {

    // {"DesEncToDes":"加密{"CustomerPass":"新密码","AppointEntity":{"LoginPass":"登陆密码"}"}
    public String DesEncToDes;

    public class DesEncToDesZ {
        public T AppointEntity;
    }
}
