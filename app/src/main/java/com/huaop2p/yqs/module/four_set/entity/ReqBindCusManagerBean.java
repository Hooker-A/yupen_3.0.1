package com.huaop2p.yqs.module.four_set.entity;

import com.huaop2p.yqs.module.base.entity.BaseRequestEntity;

/**
 * Created by zgq on 2016/7/5.
 * 作者:  zhu  guo qing
 * 时间:  2016/7/5 14:26
 * 功能:  绑定客户经理
 */
public class ReqBindCusManagerBean extends BaseRequestEntity {
    public String jsonEntity;

    public static class JsonEntity {
        public String CustomerManagerID;
    }
}
