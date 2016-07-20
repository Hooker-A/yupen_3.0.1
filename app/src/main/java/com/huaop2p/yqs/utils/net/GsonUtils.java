package com.huaop2p.yqs.utils.net;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huaop2p.yqs.module.base.Bus.BusConstant;

/**
 * 获取gson单例
 * Created by maoxiaofei on 2016/4/13.
 */
public class GsonUtils {

    private static class SingletonHolder {
        private static final Gson INSTANCE = new Gson();
        private static GsonBuilder gsonBuilder = new GsonBuilder();

    }

    public static Gson getGson() {
        return SingletonHolder.INSTANCE;
    }

    public static Gson getDateGson(String format) {
        if (format == null)
            SingletonHolder.gsonBuilder.setDateFormat(BusConstant.data_format_ss);
        else
            SingletonHolder.gsonBuilder.setDateFormat(format);
        return SingletonHolder.gsonBuilder.create();
    }
}
