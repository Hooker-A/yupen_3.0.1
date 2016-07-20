package com.huaop2p.yqs.module.three_mine.model.entity;

/**
 * Created by maoxiaofei on 2016/6/2.
 */
public class BankCardBean {
    /*
    *{
  "ReturnStatus": "0",
  "ReturnReason": "OK",
  "Remarks": "成功",
  "Total": "1",
  "ReturnMessage": {
    "Fuyou": [
      {
        "CardId": "513030199203266426",
        "BankName": "中国工商银行",
        "BankAccount": "6212260200061508186",
        "UserName": "张曼",
        "PhoneNum": "18511892429",
        "Ga_BankId": "0102",
                            0102 中国工商银行
                            0103 中国农业银行
                            0104 中国银行
                            0105 中国建设银行
                            0308 招商银行
                            0309 兴业银行
                            0305 中国民生银行
                            0306 广东发展银行
                            0307 平安银行股份有限公司
                            0310 上海浦东发展银行
                            0304 华夏银行
                            0403 国家邮政局邮政储汇局
                            0303 中国光大银行
                            0302 中信实业银行
        "KeyId": 21
      }
    ],
    "UMPay": []
  }
}
    * */
    private String CardId;
    private String BankName;
    private String BankAccount;
    private String UserName;
    private String PhoneNum;
    private String Ga_BankId;
    private String KeyId;

    public String getCardId() {
        return CardId;
    }

    public void setCardId(String cardId) {
        CardId = cardId;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getBankAccount() {
        return BankAccount;
    }

    public void setBankAccount(String bankAccount) {
        BankAccount = bankAccount;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }

    public String getGa_BankId() {
        return Ga_BankId;
    }

    public void setGa_BankId(String ga_BankId) {
        Ga_BankId = ga_BankId;
    }

    public String getKeyId() {
        return KeyId;
    }

    public void setKeyId(String keyId) {
        KeyId = keyId;
    }
}
