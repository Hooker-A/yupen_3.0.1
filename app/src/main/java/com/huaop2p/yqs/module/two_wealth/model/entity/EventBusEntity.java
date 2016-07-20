package com.huaop2p.yqs.module.two_wealth.model.entity;

/**
 * Created by Administrator on 2016/7/1.
 * 作者：任洋
 * 功能：
 */
public class EventBusEntity<T> {
    public int type;
    public T objecy;

    public EventBusEntity(T objecy,int type) {
        this.objecy = objecy;
        this.type=type;
    }
}
