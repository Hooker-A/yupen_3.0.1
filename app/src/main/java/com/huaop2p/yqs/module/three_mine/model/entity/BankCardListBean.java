package com.huaop2p.yqs.module.three_mine.model.entity;

import java.util.List;

/**
 * Created by maoxiaofei on 2016/6/2.
 */
public class BankCardListBean {
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
        "KeyId": 21
      }
    ],
    "UMPay": []
  }
 }   * */
    public List<BankCardBean> Fuyou;
    public List<BankCardBean> UMPay;
}
