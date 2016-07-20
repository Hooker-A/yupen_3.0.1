package com.huaop2p.yqs.module.three_mine.model.entity;

import com.huaop2p.yqs.module.two_wealth.model.entity.Style;

import java.util.List;

/**
 * Created by Administrator on 2016/4/21.
 */
public class Investment {
    //    DDH=订单号，LoanMoney=投资金额，LoanTermId=（1天2月3年），LoanTerm=多少（天月年），StartTime=开始时间，
//    EndTime=结束时间，ProductName=产品名称，LoanRate=年化收益率，TrueRate=真实收益率
    public String DDH;
    public int YuPenProductId;
    public int LoanMoney;
    public int LoanTermId;
    public int LoanTerm;
    public String StartTime;
    public String EndTime;
    public String ProductName;
    public String LoanRate;
    public double TrueRate;
    public int ProductId;
    public String DateTimeNow;  //当前日期
    public String qiXiWay;
    public List<Style> AssetStyles;
    public int RedType; //红包类型  1=抵现红包 2=返现红包 3=满减红包 4=加息券 5提现券
    public String RedMoney;//红包的钱数
    public String BuyTime;
}
