package com.huaop2p.yqs.module.base.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.lidroid.xutils.ViewUtils;

/**
 * Created by yindongli on 2015/1/27.
 */
public class NoAniActivity extends FragmentActivity {
    protected int activityCloseEnterAnimation;
    protected int activityCloseExitAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //启动activity时不自动弹出软键盘
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowAnimationStyle});
//
//        int windowAnimatinoStyleResId = activityStyle.getResourceId(0, 0);
//        activityStyle.recycle();
//        activityStyle = getTheme().obtainStyledAttributes(windowAnimatinoStyleResId, new int[]{android.R.attr.activityCloseEnterAnimation,
//                android.R.attr.activityCloseExitAnimation});
//
//        activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
//        activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
//        activityStyle.recycle();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ViewUtils.inject(this);
    }
}
