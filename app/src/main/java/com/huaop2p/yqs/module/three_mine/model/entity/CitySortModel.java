package com.huaop2p.yqs.module.three_mine.model.entity;

/**
 * Created by Administrator on 2016/5/15 0015.
 * 作者 ：zhu  guo qing.
 * 创建时间 ：2016/5/15 0015-下午 10:16.
 */
public class CitySortModel {
    private String name;//显示的数据
    private String sortLetters;//显示数据拼音的首字母

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
