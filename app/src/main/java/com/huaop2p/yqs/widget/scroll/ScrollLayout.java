package com.huaop2p.yqs.widget.scroll;

import android.content.Context;
import android.util.AttributeSet;

import com.huaop2p.yqs.widget.pulltorefresh.Pullable;

/**
 * Created by Administrator on 2016/4/26.
 * 作者：任洋
 * 功能：
 */
public class ScrollLayout extends ScrollableLayout implements Pullable {

    public ScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canPullDown() {
        if (getScrollY()==0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canPullUp() {
        return false;
    }
}
