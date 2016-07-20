package com.huaop2p.yqs.module.base.bases;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zgq on 2016/5/19.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/19 15:09
 * 功能:
 */
public class PreferencesUtils {

    public static String PREFERENCE_NAME = "TrineaAndroidCommon";

    private PreferencesUtils() {
        throw new AssertionError();
    }
    public static boolean putString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }
    public static String getString(Context context, String key) {
        return getString(context, key,null);
    }
    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }
}
