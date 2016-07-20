package com.huaop2p.yqs.module.one_home.bean;

import java.io.Serializable;

/**
 * Created by maoxiaofei on 2016/5/23.
 */
public class MsgCenterBean  implements Serializable{
    /*{
    "ReturnStatus": "0",
    "ReturnReason": "OK",
    "Remarks": "成功",
    "Total": "611",
    "ReturnMessage": [
        {
            "KeyId": 91,
            "Title": "呵呵",
            "Content": "aa",
            "TypeName": "个人消息",
            "Time": "2016-05-12 00:00:00",
            "Url": "url",
            "IsRead": false
        },

    ]
},
    */
    private String Content;
    private boolean IsRead;
    private int KeyId;
    private String Time;
    private String Title;
    private String TypeName;
    private String Url;

    public boolean isRead() {
        return IsRead;
    }

    public void setIsRead(boolean isRead) {
        IsRead = isRead;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }


    public int getKeyId() {
        return KeyId;
    }

    public void setKeyId(int keyId) {
        KeyId = keyId;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
