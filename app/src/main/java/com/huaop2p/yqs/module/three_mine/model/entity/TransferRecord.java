package com.huaop2p.yqs.module.three_mine.model.entity;

import com.huaop2p.yqs.module.two_wealth.model.entity.Style;

/**
 * Created by Administrator on 2016/5/20.
 * 作者：任洋
 * 功能：
 */
public class TransferRecord {
    public String TransferId;//": "转让Id",
    public String Name;//": "产品名称",
    public String YearRate;//": "年化收益率",
    public float DropRate;//": "折让率",
    public int  OtherDay;//": "剩余投资期限",
    public float TransferMoney;//": "转让金额",
    public float TransferIncome;//": "转让收益",
    public String BuyTime;//": "购买时间",
    public String InvestmentMoney;//": "投资本金",
    public String OtherIncome;//": "预期收益",
    public String DDH;//": "订单号",
    public Style TransferInco;   //   转让的样式
    public Style YupenProductInco;  // 余盆产品的样式
    public String InvestmentStartTime;//": "理财开始时间",
    public String InvestmentEndTime;//: "理财结束时间"
    public int InvestmentTerm;   //  投资时间
    public int InvestmentTermType;  //投资类型type
    public int YuPenProductId; //(1,余信2,余信5,余车)
    public  float CurrentMoney;
    public  int Statetype;  //转让方式
    public  String AccrualMoney;//手续费
    public  String StandardId;
}
