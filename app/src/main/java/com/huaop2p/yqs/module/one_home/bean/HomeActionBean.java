package com.huaop2p.yqs.module.one_home.bean;

import java.io.Serializable;

/**
 * Created by maoxiaofei on 2016/7/2.
 */
public class HomeActionBean  implements Serializable{
    /**
     * {
     "ReturnStatus": "0",
     "ReturnReason": "OK",
     "Remarks": "成功",
     "Total": "1",
     "ReturnMessage": {
                         "KeyId": 1,
                         "HomeTitle": "余盆",
                         "IconUrl": "https://www.baidu.com/img/bd_logo1.png",
                         "BannerUrl": "https://www.baidu.com/img/bd_logo1.png",
                         "ActivityUrl": "https://www.baidu.com",
                         "Title": "端午节快乐",
                         "StartTime": "2016-06-01 00:00:00",
                         "EndTime": "2016-07-15 00:00:00"
                    }
     }
     {""}  ""
     */
    public String KeyId;
    public String HomeTitle;
    public String IconUrl;
    public String BannerUrl;
    public String ActivityUrl;
    public String Title;
    public String StartTime;
    public String EndTime;
}
