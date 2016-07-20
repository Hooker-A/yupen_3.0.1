package com.huaop2p.yqs.module.three_mine.model.entity;

import java.util.Comparator;

/**
 * Created by Administrator on 2016/5/15 0015.
 * 作者 ：zhu  guo qing.
 * 创建时间 ：2016/5/15 0015-下午 10:17.
 */
public class PinyinComparator  implements Comparator<CitySortModel> {

    public int compare(CitySortModel o1, CitySortModel o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}