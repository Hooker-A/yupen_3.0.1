package com.huaop2p.yqs.module.four_set.entity;

import com.huaop2p.yqs.module.base.entity.BusConstants;

import java.io.Serializable;

/**
 * Created by zgq on 2016/7/5.
 * 作者:  zhu  guo qing
 * 时间:  2016/7/5 14:47
 * 功能:
 */
public class ProductBean implements Serializable {
    public String SellTypeM;
    public int SellType;
    public int KeyId;//产品id
    public String GeneralName;//总名称
    public String MoneyName;//分期名称
    public String MoneyDescription;//描述
    public String RateDescription;//收益率描述
    public String TrueRate;//准确利率
    public String MinimumTerm;//起投期限
    public String MinimumMoney;//起投金额
    public String FinancialIntroduction;//理财介绍
    public String RiskController;//风险控制
    public String DetailsInfo;//更多详情
    public int IsDisable;//是否禁用
    public String Remarks;//备用
    public int LoanType;
    public int sum;//总共销售额都
    public int haveing;//还有额度 份
    public String TopoOne;//标题1
    public String TopTwo;//标题2

    public int IsLotteryDraw;//":"是否抽奖（0/No,1/Yes）",
    public int IsRedPacket;//":"是否红包（0/No,1/Yes）",
    public int IsRecommend;//":"是否推荐（0/No,1/Yes）"
    public String imgurl;// 图片地址
    public int isNew;//是否为新上产品

    public String AlreadyBuy;//投资金额



    /**
     * 获取首页期限名称
     *
     * @return
     */
    public String getMiniTermStr() {
        if (MoneyName != null && MoneyName.equals(BusConstants.str_fix_day)) {
            return MoneyName;
        }

        return MinimumTerm + "个月";
    }

    /**
     * 获取起投金额
     *
     * @return
     */
    public String getMinimumMoney() {
        return MinimumMoney.split(BusConstants.SplitDot)[0];
    }

    public String getStartWhole() {
//        Float trueRate = Float.parseFloat(this.TrueRate) * 100;
//        String trRt = Float.toString(trueRate);
//        return trRt.split(BusConstants.SplitDot)[0];
        return TopoOne.split(BusConstants.SplitDot)[0];
    }
    public String getMinimumTerm(){
        return MinimumTerm.split(BusConstants.SplitDash)[0].trim();
    }

    public String getStartDecimal() {
//        Float trueRate = (float) MoneyUtils.round(Double.parseDouble(this.TrueRate) * 100, 2);
//        String trRt = Float.toString(trueRate);
//        return trRt.split(BusConstants.SplitDot)[1];
        String[] toparr = TopoOne.split(BusConstants.SplitDot);
        if (toparr.length == 2)
            return "." + toparr[1];
        else
            return "";
    }

    /**
     * 获取开始范围整数
     */
//    public static String getFixStartWhole() {
////        String[] ps1 = Float.toString(BusDataConstants.getFixMinRate()).split(BusConstants.SplitDot);
//        return ps1[0];
//    }

    /**
     * 取出 开始分为 小数部分
     *
     * @return
     */
//    public static String getFixStartDecimal() {
////        String[] ps1 = Float.toString(BusDataConstants.getFixMinRate()).split(BusConstants.SplitDot);
//        return ps1[1];
//    }

//    /**
//     * 取出 利率 结束 部分 整数
//     *
//     * @return
//     */
//    public static String getFixEndWhole() {
//        String[] ps1 = Float.toString(BusDataConstants.getFixMaxRate()).split(BusConstants.SplitDot);
//        return ps1[0];
//    }
//
//    /**
//     * 取出利率 结束部分 小数
//     *
//     * @return
//     */
//    public static String getFixEndDecimal() {
//        String[] ps1 = Float.toString(BusDataConstants.getFixMaxRate()).split(BusConstants.SplitDot);
//        return ps1[1];
//    }
//
//    /**
//     * 获取销售的百分比
//     *
//     * @return
//     */
//    public int getSealPercent() {
//        return (int) ((getRealHaveing() / getRealSum()) * 100);
//    }
//
//    public float getSealPercentFloat() {
//        return ((getRealHaveing() / getRealSum()) * 100);
//    }
//
//    /**
//     * 获取未售出的百分比
//     *
//     * @return
//     */
//    public int getNoSalePercent() {
//        if (sum - haveing == 0)
//            return 0;
//        float floatHaveing = getRealHaveing() / getRealSum() * 100;
//        double result = 100 - Math.ceil(floatHaveing);
//        return (int) result;
//    }
//
//
//    public float getRealSum() {
//        return sum * BusConstants.percentMoney;
//    }
//
//    public float getRealHaveing() {
//        return haveing * BusConstants.percentMoney;
//    }
//
////    /**
////     * @return
////     */
////    public float getPercentMoney() {
////        return sum / 100;
////    }
//
//    public int progressTemp = 0;
//
//    public synchronized void setProgress(final RoundProgressBar itemBar) {
//
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                if (haveing == 0) {
//                    itemBar.setProgress(100);
//                    itemBar.setShowValue(0);
//                }
//                int nosalePercent = getNoSalePercent();
//                itemBar.setShowValue(getRealHaveing() / 10000);
//                while (progressTemp < nosalePercent && haveing != 0) {
//                    progressTemp += 1;
//                    itemBar.setProgress(progressTemp);
//
//                    try {
//                        Thread.sleep(25);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        }).start();
//    }

    @Override
    public String toString() {
        return "ProductBean{" +
                "SellTypeM='" + SellTypeM + '\'' +
                ", SellType=" + SellType +
                ", KeyId=" + KeyId +
                ", GeneralName='" + GeneralName + '\'' +
                ", MoneyName='" + MoneyName + '\'' +
                ", MoneyDescription='" + MoneyDescription + '\'' +
                ", RateDescription='" + RateDescription + '\'' +
                ", TrueRate='" + TrueRate + '\'' +
                ", MinimumTerm=" + MinimumTerm +
                ", MinimumMoney='" + MinimumMoney + '\'' +
                ", FinancialIntroduction='" + FinancialIntroduction + '\'' +
                ", RiskController='" + RiskController + '\'' +
                ", DetailsInfo='" + DetailsInfo + '\'' +
                ", IsDisable=" + IsDisable +
                ", Remarks='" + Remarks + '\'' +
                ", LoanType=" + LoanType +
                ", sum=" + sum +
                ", haveing=" + haveing +
                ", TopoOne='" + TopoOne + '\'' +
                ", TopTwo='" + TopTwo + '\'' +
                ", IsLotteryDraw=" + IsLotteryDraw +
                ", IsRedPacket=" + IsRedPacket +
                ", IsRecommend=" + IsRecommend +
                ", imgurl='" + imgurl + '\'' +
                ", AlreadyBuy='" + AlreadyBuy + '\'' +
//                ", progressTemp=" + progressTemp +
                '}';
    }
}
