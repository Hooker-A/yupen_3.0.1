package com.huaop2p.yqs.module.two_wealth.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/5/12.
 * 作者：任洋
 * 功能：
 */
public class Transfer {
    public String TransferId;//": "转让Id",
    public int YuPenProductId;
    public String Name;//": "产品名称",
    public String YearRate;//": "年化收益率",
    public float DropRate;//": "折让率",
    public String OtherDay;//": "剩余投资期限",
    public float TransferMoney;//": "转让金额",
    public float TransferIncome;//": "转让收益",
    public String BuyTime;//": "购买时间",
    public double InvestmentMoney;//": "投资本金",
    public double OtherIncome;//": "预期收益",
    public String DDH;//": "订单号",
    public Style TransferInco;   //   转让的样式
    public List<Style> YupenProductInco;  // 余盆产品的样式
    public String InvestmentStartTime;//": "理财开始时间",
    public String InvestmentEndTime;//: "理财结束时间"
    public List<ProductRate>  ProductsRate;
    public  String StandardId;
}
