package com.huaop2p.yqs.module.three_mine.activity;

import android.content.Intent;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.three_mine.adapter.LeftRightAdapter;
import com.huaop2p.yqs.module.three_mine.model.entity.Balance;
import com.huaop2p.yqs.module.three_mine.model.entity.QuotaBank;
import com.huaop2p.yqs.widget.CustomGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 * 作者：任洋
 * 功能：
 */
public class CashActivity extends BaseRechargeCashActivity implements View.OnClickListener {
    private TextView tv_all_cash, tv_yue;
    private CustomGridView c_gv;
    private List<SparseArray<Object>> sas;
    private LeftRightAdapter adapter;

    @Override
    public Object getObject() {
        return this;
    }

    @Override
    protected String getformatString() {
        return "%.2f";
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cash;
    }

    @Override
    public void initViews() {
        super.initViews();
        tv_all_cash = (TextView) findViewById(R.id.tv_all_cash);
        tv_yue = (TextView) findViewById(R.id.tv_yue);
        tv_all_cash.setOnClickListener(this);
        c_gv = (CustomGridView) findViewById(R.id.c_gv);
    }

    @Override
    public void initData() {
        super.initData();
        sas = new ArrayList<>();
        SparseArray<Object> sa = new SparseArray<>();
        sa.put(0, "卡号");
        sa.put(1, a);
        sas.add(sa);
        sa = new SparseArray<>();
        sa.put(0, "银行");
        sa.put(1, a);
        sas.add(sa);
        adapter = new LeftRightAdapter(sas, this, 0);
        c_gv.setAdapter(adapter);
        presenter.getBalance();
    }

    @Override
    public String getURLString() {
        return HttpUrl.CASH;
    }


    @Override
    public void setBalance(Balance balance) {
        super.setBalance(balance);
        tv_yue.setText(String.format("%.2f", balance.ca_balance / 100));
    }

    @Override
    public void refresh() {
        QuotaBank bank = null;
        if (banks != null) {
            bank = banks;
        }
        if (bank != null) {
            sas.get(0).put(1, "尾号" + bank.GA_BankAccount.substring(bank.GA_BankAccount.length() - 4, bank.GA_BankAccount.length()));
            sas.get(1).put(1, bank.GA_BankName.trim());
        } else {
            sas.get(0).put(1, "没绑定银行卡");
            sas.get(1).put(1, "没绑定银行卡");
        }
        adapter.refresh(sas);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_all_cash:
                if (balance == null)
                    updateNumByValue(0);
                else {
                    investmentMoney = balance.ca_balance / 100f;
                    updateNumByValue(investmentMoney);
                }
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra("flag", false)) {
            presenter.getBalance();
        }
    }
}
