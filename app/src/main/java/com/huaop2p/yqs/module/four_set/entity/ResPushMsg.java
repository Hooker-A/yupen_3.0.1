package com.huaop2p.yqs.module.four_set.entity;

import java.io.Serializable;

/**
 * Created by zgq on 2016/5/19.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/19 15:02
 * 功能:  消息推送
 */


public class ResPushMsg implements Serializable {


    private String MessageType;
    private String SendTime;
    private String Title;
    private String Message;

    public void setMessageType(String MessageType) {
        this.MessageType = MessageType;
    }

    public void setSendTime(String SendTime) {
        this.SendTime = SendTime;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getMessageType() {
        return MessageType;
    }

    public String getSendTime() {
        return SendTime;
    }

    public String getTitle() {
        return Title;
    }

    public String getMessage() {
        return Message;
    }
}
