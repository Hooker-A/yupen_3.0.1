package com.huaop2p.yqs.module.base.contant;

/**
 * Created by maoxiaofei on 2016/4/13.
 */
public class HttpConnector {
    public static final String APP_HTTP_HOST = "http://106.39.123.229:8s2/Interface/";
    public static final  String HTTT_HOST="http://219.143.6.91";
    //"https://api.yupen.cn";
    //"http://219.143.6.91";
    public static final String APP_HTTP_HOST2=HTTT_HOST+"/Api/";

    public static final String CrowdHost ="https://zcapi.yupen.cn/Api/";//相当于以前的众筹Api接口
     //"https://zcapi.yupen.cn/Api/";
    //"http://219.143.6.89:86/Api/"
    /**
     * 版本更新
     */
    public static final String APP_UPDATE_VERSION_URL = "http://219.143.6.70/download/yp_version.xml";
    /**
     * 注册服务协议
     */
    public static final String proticalSoftUse ="https://api.yupen.cn/Api/RegisterAgreement.html";
    /**
     * 托管
     */
    public static final String huao_fuiou_agreement ="https://api.yupen.cn/Api/NewManagedServicesAgreement.html";

    /**
     * 获取客户经理
     */
    public static final String APP_HTTP_cj_AccountManager = APP_HTTP_HOST + "cj_AccountManager.ashx";
}
