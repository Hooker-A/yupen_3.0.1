package com.huaop2p.yqs.utils;

import com.huaop2p.yqs.module.two_wealth.assist.BaseCalculator;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/17.
 * 作者：任洋
 * 功能：
 */
public class DateUtils {

    public static final String yyyyMMdd = "yyyy-MM-dd";
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";

    public static int subtract(Date date1, Date date2) {
        float a = ((date1.getTime() - date2.getTime()) / 24f / 60f / 60f / 1000f);
        return Integer.valueOf(String.format("%.0f", a));
    }

    public static float subtract1(Date date1, Date date2) {
        return (float) BaseCalculator.getClaculator().divide(Double.valueOf(date1.getTime() - date2.getTime() + ""), 24 * 60 * 60 * 1000);
    }

    public static float add(Date date1, Date date2) {
        return (float) BaseCalculator.getClaculator().divide(Double.valueOf(date1.getTime() + date2.getTime() + ""), 24 * 60 * 60 * 1000);
    }

    public static String StringToDateString(String str) {
        if (str == null)
            return "";
        try {
            return sdfDay.format(sdfDay.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
            return str;
        }
    }

    public static Date stringToDate(String dateString) {
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateValue = simpleDateFormat.parse(dateString, position);
        return dateValue;
    }

    /*
   * 日期字符串转换为日期
   * @strDate String： 日期字符串
   * @format String:   转换格式
    */
    public static Date Parse2Date(String strDate, String format) {
        SimpleDateFormat sampleDateFormat = new SimpleDateFormat();
        sampleDateFormat.applyPattern(format);
        Date date = null;
        if (strDate == null || strDate.length() < 8) {
            return null;
        }
        try {
            date = sampleDateFormat.parse(strDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date;
    }
}
