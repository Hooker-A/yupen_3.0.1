package com.huaop2p.yqs.module.base.view;

import com.huaop2p.yqs.widget.scroll.ScrollableHelper;


public abstract class ScrollAbleFragment<T> extends BaseAutoFragment<T> implements ScrollableHelper.ScrollableContainer {
    public boolean isBack(){
        return true;
    }
}