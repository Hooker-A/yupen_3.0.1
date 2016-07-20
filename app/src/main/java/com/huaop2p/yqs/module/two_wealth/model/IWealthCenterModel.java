package com.huaop2p.yqs.module.two_wealth.model;

import com.huaop2p.yqs.module.two_wealth.model.entity.WealthCenter;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/10.
 * 作者：任洋
 * 功能：
 */
public interface IWealthCenterModel {
    public List<WealthCenter> loadNativeData(int type);
    public void delAllDatas(int type);
    public void saveDatas(List<WealthCenter> t,int type);
    public  void pay(Map<String,Object> map,String url,OnRequestLinstener linstener);
}
