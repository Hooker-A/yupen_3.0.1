package com.huaop2p.yqs.module.logon.entity;

/**
 * Created by zgq on 2016/5/26.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/26 15:23
 * 功能:  富有支持的地区
 */
public class FuiouChinaAreaBean {
    public String City_ID;//": "城市代码",
    public String City_Name;//": "城市名称",
    public String ID;//": "主键",
    public String ParentID;//": "父ID"

    @Override
    public String toString() {
        return "FuiouChinaAreaBean{" +
                "City_ID='" + City_ID + '\'' +
                ", City_Name='" + City_Name + '\'' +
                ", ID='" + ID + '\'' +
                ", ParentID='" + ParentID + '\'' +
                '}';
    }
}
