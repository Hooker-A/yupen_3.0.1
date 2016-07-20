package com.huaop2p.yqs.module.three_mine.model.entity;

/**
 * Created by zgq on 2016/5/12.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/12 10:01
 * 功能:
 */
public class CityBean {
    private String name="北京";
    private String firstAlpha="B";

    public String getCityName() {
        return name;
    }

    public void setCityName(String cityName) {
        name = cityName;
    }

    public String getNameSort() {
        return firstAlpha;
    }

    public void setNameSort(String nameSort) {
        firstAlpha = nameSort;
    }
}
