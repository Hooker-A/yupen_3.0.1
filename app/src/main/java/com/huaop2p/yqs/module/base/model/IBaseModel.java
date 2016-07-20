package com.huaop2p.yqs.module.base.model;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface IBaseModel<T> {
    public void loadDatas(Map<String, Object> map, OnRequestLinstener linstener, String url, int what, RequestMethod method,TypeToken typeToken);
    public void loadDatas(Map<String, Object> map, OnRequestLinstener linstener, String url, int what, RequestMethod method,TypeToken typeToken,Object tag);
    public void loadDetailById(Map<String, Object> map,OnRequestLinstener basePresenter, String url, int what, RequestMethod method,TypeToken typeToken);
    public void loadDetailById(Map<String, Object> map,OnRequestLinstener basePresenter, String url, int what, RequestMethod method,TypeToken typeToken,Object tag);
    public List<T> loadNativeData();
    public T loadNativeDataById(String id);
    public void saveDatas(List<T> t);
    public void delAllDatas();
    public void loadErrorWEB();
}
