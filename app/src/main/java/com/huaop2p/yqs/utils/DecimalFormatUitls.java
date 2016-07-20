package com.huaop2p.yqs.utils;

import java.text.DecimalFormat;

/**
 * Created by maoxiaofei on 2016/5/4.
 *
 * 保留两位有效数字的公共方法
 */
public class DecimalFormatUitls {

    //两位有效数字
    public static String DecimalFormat_two(Double double_value){
        DecimalFormat df = new DecimalFormat("##0.00");
        return df.format(double_value);
    }

    //没有小数位数
    public static String DecimalFormat_zro(Double double_value){
        DecimalFormat df = new DecimalFormat("###0.##");
        return df.format(double_value);
    }

    //一位有效数字
    public static String DecimalFormat_one(Double double_value){
        DecimalFormat df = new DecimalFormat("##0.0");
        return df.format(double_value);
    }

}
