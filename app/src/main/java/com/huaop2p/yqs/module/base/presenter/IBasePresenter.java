package com.huaop2p.yqs.module.base.presenter;

import com.yolanda.nohttp.RequestMethod;

import java.util.Map;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface IBasePresenter {
    /**
     * 加载集合数据
     **/
    public void loadData(Map<String, Object> map, String url, int what, RequestMethod method);

    /**
     * 加载详情
     **/
    public void loadDetail(Map<String, Object> map, String url, int what, RequestMethod method);

}
