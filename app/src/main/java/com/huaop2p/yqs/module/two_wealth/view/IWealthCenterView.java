package com.huaop2p.yqs.module.two_wealth.view;

import com.huaop2p.yqs.module.three_mine.model.entity.Borrower;

import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public interface IWealthCenterView<T>{

    /**
     * 查看详情
     **/
    public void loadDetailView(T t);
    /***
     * 清空数据
     *
     * **/
    public void clearData();
    /***
     *
     * 获取数据
     * **/
    public List<T> getData();

    /***
     *
     * 获取数据
     * **/
    public void setData(List<T> ts);

    public void showBorrowerInfo(List<String> bs);
}
