package com.huaop2p.yqs.module.logon.adapter;

import com.huaop2p.yqs.module.logon.entity.FuiuoBankBean;

import java.util.List;

/**
 * Created by zgq on 2016/5/26.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/26 17:03
 * 功能:  银行卡适配器
 */
public class FuiouBankWhellAdapter implements WheelAdapter {
    public static final int DEFAULT_LENGTH = -1;

    // items
    public List<FuiuoBankBean> items;
    // length
    public int length;

    /**
     * Constructor
     *
     * @param items  the items
     * @param length the max items length
     */
    public FuiouBankWhellAdapter(List<FuiuoBankBean> items, int length) {
        this.items = items;
        this.length = length;
    }

    /**
     * Contructor
     *
     * @param items the items
     */
    public FuiouBankWhellAdapter(List<FuiuoBankBean> items) {
        this(items, DEFAULT_LENGTH);
    }

    @Override
    public String getItem(int index) {
        if (index >= 0 && index < items.size()) {
            int i = index;
            String res = items.get(index).BankChineseName.toString().trim();
            return res;
//            return items.get(index).BankChineseName.toString();
        }
        return "";
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }

    @Override
    public int getMaximumLength() {
        return length;
    }
}
