package com.huaop2p.yqs.module.one_home.bean;

import java.io.Serializable;

/**
 * Created by maoxiaofei on 2016/7/1.
 */
public class YuPenBean implements Serializable{

    /**
     * {
     "ReturnStatus": "0",
     "ReturnReason": "OK",
     "Remarks": "成功",
     "Total": "1",
     "ReturnMessage": [
     {
     "Mode": 1,
     "Url": "http://219.143.6.91/Html/AboutPlatform/yp_contianer.html"
     },
     {
     "Mode": 2,
     "Url": "http://219.143.6.91/Html/AboutPlatform/cf_contianer.html"
     },
     {
     "Mode": 3,
     "Url": "http://219.143.6.91/Html/AboutPlatform/fk_contianer.html"
     },
     {
     "Mode": 4,
     "Url": "http://219.143.6.91/TimeSection/Index"
     }
     ]
     }
     */

    public int Mode;
    public String Url;
    public String name = "关于余盆";
}
