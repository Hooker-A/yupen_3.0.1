package com.huaop2p.yqs.module.three_mine.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.three_mine.model.entity.Balance;
import com.huaop2p.yqs.module.three_mine.model.entity.QuotaBank;
import com.huaop2p.yqs.module.three_mine.presenter.impl.RechargePresenterImpl;
import com.huaop2p.yqs.module.three_mine.view.IRechargeView;
import com.huaop2p.yqs.utils.NumberToCN;
import com.huaop2p.yqs.utils.ToastUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2016/6/23.
 * 作者：任洋
 * 功能：
 */
public abstract class BaseRechargeCashActivity extends BaseAutoActivity implements IRechargeView {
    protected TextView tv_value;
    protected EditText et_money;
    protected double investmentMoney = 500;   //投资金额 ，默认500
    protected RechargePresenterImpl presenter;
    protected Random random = new Random();
    protected String orderNum;
    protected Map<String, Object> map;
    protected final static String a = "未绑定银行卡";
    protected Balance balance;   //余额
    protected QuotaBank banks;
    protected int danci = 0;
    public final static String url = "http://219.143.6.91/Api/FYCallBack/RechargeCallBack";
    public final static String backUrl = "huaoyupen://";

    @Override
    public void initViews() {
        et_money = (EditText) findViewById(R.id.et_money);
        tv_value = (TextView) findViewById(R.id.tv_value);
        et_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        et_money.setText(s);
                        et_money.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    et_money.setText(s);
                    et_money.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        et_money.setText(s.subSequence(0, 1));
                        et_money.setSelection(1);
                    }
                }
                String value = et_money.getText().toString();
                if (value.equals("") || value.equals(".")) {
                    investmentMoney = 0;
                } else {
                    investmentMoney = Double.valueOf(value);

                }
                updateNumByValue(investmentMoney);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_money.setText("500.00");
    }

    @Override
    public void initData() {
        presenter = new RechargePresenterImpl(this);
        presenter.getBalance();
        presenter.getBankCard();
    }

    @Override
    public void setSign(String sign) {
        sign = sign.replaceAll("\\+", "%2B");
        String str = "mchnt_cd=" + AppApplication.mchnt_cd + "&mchnt_txn_ssn=" + orderNum + "&login_id=" + AppApplication.user.PhoneNum + "&amt=" + (long) (investmentMoney * 100) + "&page_notify_url=" + backUrl + "&back_notify_url=" + "&signature=" + sign;
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("url", getURLString());
        intent.putExtra("param", str);
        intent.putExtra("flag", getIntent().getBooleanExtra("flag", false));
        QuotaBank bank = banks;
        if (bank != null) {
            intent.putExtra("cardNum", bank.GA_BankAccount.substring(bank.GA_BankAccount.length() - 4, bank.GA_BankAccount.length()));
            intent.putExtra("bank", bank.GA_BankName);
        }
        intent.putExtra("money", balance.ca_balance);
        if (getURLString().equals(HttpUrl.QUICK_RECHARGE)) {
            intent.putExtra("title", "充值");
        } else {
            intent.putExtra("title", "提现");
        }
        startActivity(intent);
    }

    @Override
    public Object getObject() {
        return this;
    }

    @Override
    public void updateNumByValue(double value) {
        String a = et_money.getText().toString();
        double b = 0;
        if (!a.equals("") && !a.equals("."))
            b = Double.valueOf(a);
        if (value != b)
            et_money.setText(String.format(getformatString(), value));
        et_money.setSelection(et_money.length());
        tv_value.setText(NumberToCN.number2CNMontrayUnit(new BigDecimal(value)));
    }

    protected abstract String getformatString();

    @Override
    public void recharge(View view) {
        if (AppApplication.mchnt_cd == null || banks == null) {
            ToastUtils.show(AppApplication.mContext, "未绑定银行卡");
            return;
        }
//        if (investmentMoney > danci&&getURLString().equals(HttpUrl.QUICK_RECHARGE)) {
//            ToastUtils.show(AppApplication.mContext, "单次金额不能大于" + danci+"元");
//            return;
//        }
        if (investmentMoney < 5) {
            ToastUtils.show(AppApplication.mContext, "金额不能小于5元");
            return;
        }
        if (balance == null) {
            ToastUtils.show(AppApplication.mContext, "网络不稳定");
            return;
        }
        map = new HashMap<>();
        orderNum = "" + new Date().getTime() + random.nextInt(9999) % (9999 - 1000 + 1) + 1000;
        Map<String, Object> childMap = new HashMap<>();
        String str = (long) (investmentMoney * 100) + "|" + "|" + AppApplication.user.PhoneNum + "|" + AppApplication.mchnt_cd + "|" + orderNum + "|" + backUrl;
        childMap.put("Signature", str);
        map.put("AppointEntity", childMap);
        presenter.getSign(map);
    }

    public abstract String getURLString();

    @Override
    public void cash(View view) {
        if (balance == null || investmentMoney <= balance.ca_balance / 100) {
            recharge(view);
        } else {
            ToastUtils.show(this, "提现金额不能大于余额");
        }
    }

    @Override
    public void setBalance(Balance balance) {
        this.balance = balance;

    }

    public void subtract(View view) {
        if (investmentMoney >= 1000) {
            investmentMoney -= 500;
            updateNumByValue(investmentMoney);
        }
    }

    public void add(View view) {
        investmentMoney += 500;
        updateNumByValue(investmentMoney);

    }

    @Override
    public void setBank(QuotaBank banks) {
        this.banks = banks;
        refresh();
    }

    public abstract void refresh();
}
