package com.huaop2p.yqs.module.three_mine.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.one_home.activity.MFActivity;
import com.huaop2p.yqs.module.three_mine.model.entity.Balance;
import com.huaop2p.yqs.module.three_mine.presenter.impl.RechargeCashPresenterImpl;
import com.huaop2p.yqs.module.two_wealth.activity.InvestmentActivity;
import com.huaop2p.yqs.utils.NumberToCN;
import com.huaop2p.yqs.utils.auto.AutoUtils;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/6/16.
 * 作者：任洋
 * 功能：充值提现结果
 */
public class RechargeCashActivity extends BaseAutoActivity<Balance> {
    private double oldMoney;
    private RechargeCashPresenterImpl presenter;
    private ViewStub vb_recharge_success, vb_recharge_error, vb_cash_success, vb_cash_error;
    private TextView tv_recharge_money, tv_yue, tv_result, tv_title;
    private AnimationDrawable frameAnim;
    private ImageView iv_result;
    private String url;
    private TextView tv_card_num_value, tv_bank;
    private boolean flag;
    private TextView tv_value;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge_cash;
    }
    @Override
    public Object getObject() {
        return this;
    }
    @Override
    public void initViews() {
        vb_recharge_success = (ViewStub) findViewById(R.id.vb_recharge_success);
        vb_recharge_error = (ViewStub) findViewById(R.id.vb_recharge_error);
        vb_cash_success = (ViewStub) findViewById(R.id.vb_cash_success);
        vb_cash_error = (ViewStub) findViewById(R.id.vb_cash_error);
        iv_result = (ImageView) findViewById(R.id.iv_result);
        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_title = (TextView) findViewById(R.id.tv_title);
    }

    @Override
    public void initData() {
        oldMoney = getIntent().getDoubleExtra("money", 0);
        url = getIntent().getStringExtra("url");
        flag = getIntent().getBooleanExtra("flag", false);
        presenter = new RechargeCashPresenterImpl(this);
        presenter.getBalance();
    }

    @Override
    public void showSuccess(Balance balance) {
        if (balance.ca_balance == oldMoney && url.equals(HttpUrl.QUICK_RECHARGE)) {
            tv_result.setText("充值失败");
            iv_result.setBackgroundResource(R.drawable.hotopupfail);
            tv_result.setText("充值失败");
            tv_title.setText("充值失败");
            ViewGroup vg = (ViewGroup) vb_recharge_error.inflate();
            AutoUtils.initLayoutSize(vg, this);
        } else if (balance.ca_balance != oldMoney && url.equals(HttpUrl.QUICK_RECHARGE)) {
            frameAnim = addFrame("hobuy_s_", 12, 50);
            frameAnim.setOneShot(true);
            iv_result.setBackgroundDrawable(frameAnim);
            frameAnim.start();


            ViewGroup vg = (ViewGroup) vb_recharge_success.inflate();
            AutoUtils.initLayoutSize(vg, this);
            tv_recharge_money = (TextView) findViewById(R.id.tv_recharge_money);
            tv_yue = (TextView) findViewById(R.id.tv_yue);
            tv_yue.setText("￥" + (String.format("%.2f", balance.ca_balance / 100)));
            tv_recharge_money.setText("￥" + String.format("%.2f", (Double.valueOf(balance.ca_balance) - Double.valueOf(oldMoney)) / 100));
            tv_value= (TextView) findViewById(R.id.tv_value);
          //  int value= (int) ((Double.valueOf(balance.ca_balance) - Double.valueOf(oldMoney)) / 100);
            tv_value.setText(NumberToCN.number2CNMontrayUnit(new BigDecimal(((Double.valueOf(balance.ca_balance) - Double.valueOf(oldMoney)) / 100))));
            tv_result.setText("充值成功");
            tv_title.setText("充值成功");
        } else if (balance.ca_balance == oldMoney && url.equals(HttpUrl.CASH)) {
            tv_result.setText("提现失败");
            iv_result.setBackgroundResource(R.drawable.hotopupfail);
            tv_result.setText("提现失败");
            tv_title.setText("提现失败");
            ViewGroup vg = (ViewGroup) vb_cash_error.inflate();
            AutoUtils.initLayoutSize(vg, this);
        } else {
            frameAnim = addFrame("hobuy_s_", 12, 50);
            frameAnim.setOneShot(true);
            iv_result.setBackgroundDrawable(frameAnim);
            frameAnim.start();


            ViewGroup vg = (ViewGroup) vb_cash_success.inflate();
            AutoUtils.initLayoutSize(vg, this);
            tv_card_num_value = (TextView) findViewById(R.id.tv_card_num_value);
            tv_bank = (TextView) findViewById(R.id.tv_bank);
            tv_bank.setText(getIntent().getStringExtra("bank"));
            tv_card_num_value.setText("尾号" + getIntent().getStringExtra("cardNum"));
            tv_recharge_money = (TextView) findViewById(R.id.tv_recharge_money);
            tv_recharge_money.setText("￥" + String.format("%.2f", (Double.valueOf(balance.ca_balance) - Double.valueOf(oldMoney)) / 100));
        }
    }

    public void retry(View view) {
        Intent intent = new Intent(this, RechargeActivity.class);
        intent.putExtra("flag", false);
        startActivity(intent);
    }

    public void cash(View view) {
        Intent intent = new Intent(this, CashActivity.class);
        intent.putExtra("flag", false);
        startActivity(intent);
    }

    public void end(View view) {
        Intent intent = new Intent(this, MFActivity.class);
        intent.putExtra("flag",true);
        startActivity(intent);
    }

    public void continuedInvestment(View view) {
        if (flag) {
            end(view);
        } else {
            Intent intent = new Intent(this, InvestmentActivity.class);
            intent.putExtra("flag",true);
            startActivity(intent);
        }
    }

    public AnimationDrawable addFrame(String name, int count, int time) {
        String packageName = getPackageName();
        AnimationDrawable frameAnim = new AnimationDrawable();
        for (int i = 1; i <= count; i++) {
            int imageResId = getResources().getIdentifier(name + i, "drawable", packageName);
            frameAnim.addFrame(getResources().getDrawable(imageResId), time);
        }
        return frameAnim;
    }
}
