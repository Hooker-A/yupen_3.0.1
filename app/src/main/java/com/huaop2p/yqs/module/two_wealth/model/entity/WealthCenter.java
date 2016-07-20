package com.huaop2p.yqs.module.two_wealth.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public class WealthCenter {


    public int _id;
    public String KeyId;//": 41,
    public  String LoanName;
    public String Name;//标的名称
    public String TopoOne;//年华收益率
    public String TopoTwo;//预期年化收益
    public double TrueRate;//实际收益率
    public String MinimumMoney;//起投金额
    public String Amount;//剩余可投金额
    public String AlreadyBuy;//已投金额
    public int LoanTermId;//期限ID  1：天 2：月 3：年
    public String LoanTerm;//期限数据
    public String LoanState;//产品状态
    public String ReturnWay;//": 1//还款方式
    public List<Style> AssetStyles;
    public float Money;  //投资金额
    public List<ProductRate> ProductsRate;
    public  String qiXiWay;
}
