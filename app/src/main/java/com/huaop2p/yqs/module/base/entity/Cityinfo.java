package com.huaop2p.yqs.module.base.entity;

import java.io.Serializable;

/**
 * Created by zgq on 2016/5/25.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/25 17:04
 * 功能:  城市
 */
public class Cityinfo implements Serializable {

    private String id;
    private String city_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    @Override
    public String toString() {
        return "Cityinfo [id=" + id + ", city_name=" + city_name + "]";
    }
}
