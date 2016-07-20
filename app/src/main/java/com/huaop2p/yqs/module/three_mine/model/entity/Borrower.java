package com.huaop2p.yqs.module.three_mine.model.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/13.
 * 作者：任洋
 * 功能：借款人
 */
public class Borrower {
    public String KeyId;//": "12914",
    public String LoanPersonName;//": "许荣荣",
    public int LoanOverIntMone;//": "56000.000000",
    public String LoanCard;//": "350582198502015041",
    public String LoanApplication;//": "消费",
    public String Age;//": "30",
    public String Sex;//": "女",
    public double LoanMone;  //借款金额
    public double TrueRate;  //收益率
    public String LoanType; //借款期限
    public String LoaningIntMone;

    public String Title;
    public List<Map<String, String>> List;

//    "KeyId": "主键",
//            "LoanPersonName": "借款人姓名",
//            "LoanOverIntMone": "借款金额",
//            "LoanCard": "身份证号",
//            "LoanApplication": "借款用途",
//            "Age": "年龄",
//            "Sex": "性别（0是女1是男）"
}
