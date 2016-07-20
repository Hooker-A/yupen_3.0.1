package com.huaop2p.yqs.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zgq on 2016/4/15.
 * 作者:  zhu  guo qing
 * 时间:  2016/4/15 17:33
 * 功能:  toast工具类
 */
public class ToastUtils {

    public ToastUtils() {
        throw new AssertionError();
    }

    public static void show(Context context, int resId) {
        if (context != null)
            show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        if (context != null && resId != 0) ;
        show(context, context.getResources().getText(resId), duration);
    }

    public static void show(Context context, CharSequence text) {
        if (context != null)
            show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, CharSequence text, int duration) {
        if (context != null)
            Toast.makeText(context, text, duration).show();
    }

    public static void show(Context context, int resId, Object... args) {
        if (context != null)
            show(context, String.format(context.getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String format, Object... args) {
        if (context != null)
            show(context, String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration, Object... args) {
        if (context != null)
            show(context, String.format(context.getResources().getString(resId), args), duration);
    }

    public static void show(Context context, String format, int duration, Object... args) {
        if (context != null)
            show(context, String.format(format, args), duration);
    }

}
