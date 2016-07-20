package com.huaop2p.yqs.module.one_home.bean;

import java.io.Serializable;

/**
 * Created by maoxiaofei on 2016/6/29.
 */
public class HomeMseZiXunBean implements Serializable {
    /*
    *{
  "ReturnStatus": "0",
  "ReturnReason": "OK",
  "Remarks": "成功",
  "Total": "1",
  "ReturnMessage": [
    {
      "Id": 67,
      "NewsTitle": "华澳融信受邀出席2015·上海新金融年会暨互联网金融外滩峰会",
      "CreatTime": "2015-07-15 13:32:00",
      "ClassCName": "金融动态",
      "Content": null
    },
    {
      "Id": 263,
      "NewsTitle": "风控决定互联网金融成败，余盆用大数据构筑高效风控体系",
      "CreatTime": "2016-05-17 14:28:00",
      "ClassCName": "媒体报道",
      "Content": null
    },
    * */

    public String Id;
    public String NewsTitle;
    public String CreatTime;
    public String ClassCName;
    public String Content;
}
