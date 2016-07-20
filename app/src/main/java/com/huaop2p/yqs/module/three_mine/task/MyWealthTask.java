package com.huaop2p.yqs.module.three_mine.task;

import android.app.Activity;

import com.huaop2p.yqs.module.three_mine.presenter.impl.MyWealthPresenterImpl;

import java.util.TimerTask;

/**
 * Created by Administrator on 2016/5/25.
 * 作者：任洋
 * 功能：
 */
public class MyWealthTask extends TimerTask {
    private MyWealthPresenterImpl presenter;
    private boolean flag;
    private Activity activity;
    public MyWealthTask(MyWealthPresenterImpl presenter,Activity activity) {
        this.presenter = presenter;
        this.activity=activity;
    }


    @Override
    public void run() {
        if (!flag) {
            flag = true;
            presenter.getTotalMoney();
        } else {
                    presenter.jisuan();
        }
    }
}
