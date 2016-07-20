package com.huaop2p.yqs.module.one_home.entity;

import java.io.Serializable;

/**
 * Created by zgq on 2016/7/5.
 * 作者:  zhu  guo qing
 * 时间:  2016/7/5 11:31
 * 功能:
 */
public class AgreeMentVersionBean implements Serializable {


    /**
     * KeyId : 2
     * AgreeMentVersion : 2
     * AgreeMentUrl : http://106.39.123.229:8123/Api/NewAgreement.html
     * IsDisabled : 1
     * AgreeMentDateTime : 2015-11-21 07:23:49
     * AgreeMentType : 1
     * AgreeMentTitle : 协议更新
     * AgreeMentContent : 请您阅读并同意以上协议，以便我们能继续为您提供服务
     * AgreeMentBottom : 我已阅读并同意以上协议
     */

    public int KeyId;
    public String AgreeMentVersion;
    public String AgreeMentUrl;
    public int IsDisabled;
    public String AgreeMentDateTime;
    public String AgreeMentType;
    public String AgreeMentTitle;
    public String AgreeMentContent;
    public String AgreeMentBottom;
}
