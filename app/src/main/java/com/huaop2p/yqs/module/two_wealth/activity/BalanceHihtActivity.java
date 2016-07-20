package com.huaop2p.yqs.module.two_wealth.activity;

import android.content.Intent;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.three_mine.activity.RechargeActivity;

/**
 * Created by Administrator on 2016/5/23.
 * 作者：任洋
 * 功能：余额不足提示
 */
public class BalanceHihtActivity extends BaseAutoActivity{


    @Override
    public int getLayoutId() {
        return R.layout.activity_balance_hiht;
    }

    @Override
    public void initViews() {

    }
    @Override
    public Object getObject() {
        return this;
    }
    @Override
    public void initData() {
       initWindows();
    }

    private void initWindows() {
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.8);
        getWindow().setAttributes(p);
    }
    public void recharge(View view){
        Intent intent=new Intent(this, RechargeActivity.class);
        startActivity(intent);
        finish();
    }
}
