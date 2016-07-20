package com.huaop2p.yqs.module.base.contant;

/**
 * Created by Administrator on 2016/5/3.
 * 作者：任洋
 * 功能：状态
 */
public class State {
    public final static String YU_SHOU = "-1"; //待发售
    public final static String INVESTMENT = "1"; //可以投资
    public final static String SETTLE = "4"; //已结清
    public final static String REPAYMENT = "3"; //还款中
    public final static String TRANSFER = "2"; //满标

    public final static String MAN_BIAO = "1"; //满标
    public final static String BUY = "0"; //可以投资
    public final static String DAI_SHOU = "-1"; //待发售

    public final static int YUXINBAO = 3;    //余信宝
    public final static int YUCHEBAO = 2;   //余车宝
    public final static int YUFANGBAO = 1;  //余房宝


    public final static int DAY = 1;  //天
    public final static int MONTH = 2;  //月
    public final static int YEAR = 3;  //年


    public final static int NULL = 0;  //空

    public final static int NO_LOGIN = 1;  //没登录


    public final static String TRANSFERING = "2";  //转让中
    public final static String TRANSFER_END = "3";  //已转让


    public final static int TRANSFER_JIAJI = 0;  //加急
    public final static int TRANSFER_NORMAL = 1;  //正常


    public final static int UNMARRIED = 0;  //未婚
    public final static int MARRIED = 1;  //已婚
    public final static int DIVORCE = 2;  //离异
    public final static int DIE = 3;  //丧偶
}
