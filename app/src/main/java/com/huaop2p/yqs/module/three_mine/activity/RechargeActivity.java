package com.huaop2p.yqs.module.three_mine.activity;

import android.content.Intent;
import android.util.SparseArray;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.three_mine.adapter.LeftRightAdapter;
import com.huaop2p.yqs.module.three_mine.model.entity.QuotaBank;
import com.huaop2p.yqs.widget.CustomGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/14.
 * 作者：任洋
 * 功能：
 */
public class RechargeActivity extends BaseRechargeCashActivity {
    private CustomGridView c_gv;
    private List<SparseArray<Object>> sas;
    private LeftRightAdapter adapter;
    private Map<String, Object> map;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge;
    }
    @Override
    public Object getObject() {
        return this;
    }
    @Override
    public void initViews() {
        super.initViews();
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
        sa = new SparseArray<>();
        sa.put(0, "单次充值限额");
        sa.put(1, danci+"元");
        sas.add(sa);
        sa = new SparseArray<>();
        sa.put(0, "单日充值限额");
        sa.put(1, "0元");
        sas.add(sa);
        adapter = new LeftRightAdapter(sas, this, 0);
        c_gv.setAdapter(adapter);
    }

    @Override
    public void updateNumByValue(double value) {
        super.updateNumByValue(value);
    }

    @Override
    protected String getformatString() {
        return "%.2f";
    }

    @Override
    public String getURLString() {
        return HttpUrl.QUICK_RECHARGE;
    }

    @Override
    public void refresh() {
        QuotaBank bank = null;
        if (banks!= null) {
            bank = banks;
        }
        if (bank != null) {
            this.danci=bank.OnceCeiling;
            sas.get(0).put(1, "尾号" + bank.GA_BankAccount.substring(bank.GA_BankAccount.length() - 4, bank.GA_BankAccount.length()));
            sas.get(1).put(1, bank.GA_BankName.trim());
            sas.get(2).put(1,bank.OnceCeiling+"元");
            sas.get(3).put(1,bank.DayCeiling+"元");
        } else {
            sas.get(0).put(1, "没绑定银行卡");
            sas.get(1).put(1, "没绑定银行卡");
        }
        adapter.refresh(sas);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
       if (intent.getBooleanExtra("flag",false)){
           presenter.getBalance();
       }
    }
}
