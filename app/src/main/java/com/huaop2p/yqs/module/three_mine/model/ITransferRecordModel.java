package com.huaop2p.yqs.module.three_mine.model;

import com.huaop2p.yqs.utils.net.OnRequestLinstener;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/30.
 * 作者：任洋
 * 功能：
 */
public interface ITransferRecordModel
{
    public void  addTransferRecord(Map<String, Object> map,  OnRequestLinstener linstener);

    public void delTransferRecordById(Map<String, Object> map,  OnRequestLinstener linstener);
}
