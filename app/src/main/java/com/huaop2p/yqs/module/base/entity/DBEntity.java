package com.huaop2p.yqs.module.base.entity;

/**
 * Created by Administrator on 2016/4/27.
 * 作者：任洋
 * 功能：保存在本地数据库的实体
 */
public class DBEntity {
    public int _id;
    public String className;
    public int type;   //一个标记，用来区分一个实体类代表不同的对象(余信宝，余房，余车)
    public String content;
}
