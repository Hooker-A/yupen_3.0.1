package com.huaop2p.yqs.module.three_mine.db;

import java.util.Comparator;

/**
 * Created by zgq on 2016/6/14.
 * 作者:  zhu  guo qing
 * 时间:  2016/6/14 15:39
 * 功能:
 */
public class PinyinComp implements Comparator<LandDivide> {
    @Override
    public int compare(LandDivide lhs, LandDivide rhs) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (lhs.getSuperior().equals("@")
                || rhs.getSuperior().equals("#")) {
            return -1;
        } else if (lhs.getSuperior().equals("#")
                || rhs.getSuperior().equals("@")) {
            return 1;
        } else {
            return lhs.getSuperior().compareTo(rhs.getSuperior());
        }
    }

}
