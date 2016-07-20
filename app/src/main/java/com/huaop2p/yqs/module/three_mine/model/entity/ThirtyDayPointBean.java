package com.huaop2p.yqs.module.three_mine.model.entity;

/**
 * Created by maoxiaofei on 2016/6/27.
 */
public class ThirtyDayPointBean  {
    /*
    * {
  "ReturnStatus": "0",
  "ReturnReason": "OK",
  "Remarks": "成功",
  "Total": "394",
  "ReturnMessage": [
    {
      "DiscountSource": 3,
      "Amount": 10,
      "CreateTime": "2016-06-27 09:41:32",
      "Type": 1
    },
    {
      "DiscountSource": null,
      "Amount": 250,
      "CreateTime": "2016-06-26 16:55:21",
      "Type": 1
    },
    * */

    public String DiscountSource;  //"1：购买 2：活动 3.签到 4.积分商城兑换"
    private String Amount;         //积分数量
    private String CreateTime;  //时间"
    private String Type;        //1= +  2= -

    public String title;

    public String getDiscountSource() {
        return DiscountSource;
    }

    public void setDiscountSource(String discountSource) {
        DiscountSource = discountSource;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
