package com.huaop2p.yqs.utils;

import android.content.res.Resources;

/**
 * Created by Administrator on 2016/4/15.
 */
public class ResourceUtil {
    public static int getColor(Resources resources,int resId){
       return  resources.getColor(resId);
    }
    public static String getString(Resources resources,int resId){
        return  resources.getString(resId);
    }
}
