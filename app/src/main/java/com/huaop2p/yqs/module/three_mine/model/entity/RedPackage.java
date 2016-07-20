package com.huaop2p.yqs.module.three_mine.model.entity;

import com.huaop2p.yqs.utils.DecimalFormatUitls;

/**
 * Created by maoxiaofei on 2016/5/17.
 */
public class RedPackage {

    /*
    *  "Remarks": "成功",
    "ReturnMessage": [
        {
            "DateTimeNow": "2016-06-12 11:07:56",
            "Detail": "投资期满返回余额",
            "EndTime": "2016-07-18 16:43:14",
            "KeyId": "1",
            "Money": "1.04",
            "Type": "到期返现",
            "TypeId": "2",
            "UseDescription": ""
        },
}

    * */

    private String DateTimeNow;//系统返回时间
    private String Detail;//冲抵投资资本
    private String EndTime;//红包到期时间
    private String KeyId;
    private String Money;//金额
    private String Type;//红包类型
    private String TypeId;//红包背景样式
    private String UseDescription;//潢500可用

    public String getDateTimeNow() {
        return DateTimeNow;
    }

    public void setDateTimeNow(String dateTimeNow) {
        DateTimeNow = dateTimeNow;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public String getKeyId() {
        return KeyId;
    }

    public void setKeyId(String keyId) {
        KeyId = keyId;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getMoney() {
        return DecimalFormatUitls.DecimalFormat_two(Double.parseDouble(Money));
    }

    public void setMoney(String money) {
        Money = money;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTypeId() {
        return TypeId;
    }

    public void setTypeId(String typeId) {
        TypeId = typeId;
    }

    public String getUseDescription() {
        return UseDescription;
    }

    public void setUseDescription(String useDescription) {
        UseDescription = useDescription;
    }
}
