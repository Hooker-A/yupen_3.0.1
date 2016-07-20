package com.huaop2p.yqs.module.two_wealth.model.entity;

/**
 * Created by Administrator on 2016/6/24.
 * 作者：任洋
 * 功能：借款人详细
 */
public class BorrowerDetails {
    //LoanPersonName=姓名,Sex=性别,IDType=证件类型,LoanCard=证件号码,Education=学历,Health=个人健康状况,Fixed_Phone=固定电话,LoanMone=放款金额,LoanApplication=用途,Job=职业,Unit=单位性质,Job_Title=职称
    // Professional=专业特长,SourceOfIncome=主要经济来源,Month_Revenues=月收入,Year_Revenues=个人综合年收入,Family_Year_Revenues=家庭人均年收入,Family_Year_Expenses=家庭年均支出,Other_Revenues=其它收入

    public String LoanPersonName;
    public String Sex;
    public String IDType;
    public String LoanCard;
    public String Education;
    public String Health;
    public String Fixed_Phone;

    public double LoanMone;
    public String LoanApplication;
    public String Job;
    public String Unit;
    public String Job_Title;
    public String Professional;
    public String SourceOfIncome;
    public double Month_Revenues;

    public double Year_Revenues;
    public double Family_Year_Revenues;
    public double Family_Year_Expenses;
    public double Other_Revenues;



}
