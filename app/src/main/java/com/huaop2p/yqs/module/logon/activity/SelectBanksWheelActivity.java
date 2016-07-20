package com.huaop2p.yqs.module.logon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.logon.adapter.FuiouBankWhellAdapter;
import com.huaop2p.yqs.module.logon.entity.FuiuoBankBean;
import com.huaop2p.yqs.module.logon.model.MyFinancialWeb;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.huaop2p.yqs.widget.OnWheelChanged;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgq on 2016/5/26.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/26 16:38
 * 功能:  银行卡
 */
public class SelectBanksWheelActivity extends BaseActivity{
    WheelViewT wheel_view_banks;
    public static final String INTENT_DEFAULT_BANK = "INTENT_DEFAULT_BANK";
    public static final String RERURN_SELECT_BANK = "RERURN_SELECT_BANK";

    List<FuiuoBankBean> banks = null;
    FuiuoBankBean selectBank = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.financial_select_banks_wheel);
        wheel_view_banks = (WheelViewT) findViewById(R.id.wheel_view_banks);
        Button bt_ok= (Button) findViewById(R.id.bt_ok);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                String bank = GsonUtils.getGson().toJson(selectBank);
                result.putExtra(RERURN_SELECT_BANK, bank);
                SelectBanksWheelActivity.this.setResult(RESULT_OK, result);
                finish();
            }
        });
        String defBanStr = getIntent().getStringExtra(INTENT_DEFAULT_BANK);
        if (defBanStr != null && defBanStr.length() > 0) {
            selectBank = GsonUtils.getGson().fromJson(defBanStr, FuiuoBankBean.class);
        }
        if (banks == null) {
            Map map=new HashMap();
            map.put("action","");
            map.put("CustomerName","");
            map.put("CustomerPass","");
            MyFinancialWeb.getInstance().PostAccountBank(map, new OnRequestLinstener<BaseResponseEntity<List<FuiuoBankBean>>>() {
                @Override
                public void success(BaseResponseEntity<List<FuiuoBankBean>> listBaseResponseEntity) {
                    banks = listBaseResponseEntity.ReturnMessage;
                    bindBankWheel();
                }

                @Override
                public void error(int code, String error) {
                    LogUtils.e(error+"768120     757");

                }
            }, HttpUrl.PostAccountBank,0, RequestMethod.POST,new TypeToken<BaseResponseEntity<List<FuiuoBankBean>>>(){});

        } else {
            bindBankWheel();
        }
    }


    /**
     * 绑定银行卡数据
     */
    void bindBankWheel() {
        wheel_view_banks.setVisibleItems(5);
        wheel_view_banks.setAdapter(new FuiouBankWhellAdapter(banks));
        wheel_view_banks.addChangingListener(new OnWheelChanged() {
            public void onChanged(WheelViewT wheel, int oldValue, int newValue) {
                if (banks != null)
                    selectBank = banks.get(newValue);
            }
        });
        int index = getIndex(banks, selectBank);
        wheel_view_banks.setCurrentItem(index);
        selectBank = banks.get(index);
    }

    int getIndex(List<FuiuoBankBean> banks, FuiuoBankBean bank) {
        if (banks != null && bank != null)
            for (int i = 0; i < banks.size(); i++) {
                if (banks.get(i).GA_BankNO.equals(bank.GA_BankNO))
                    return i;
            }
        return 0;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent result = new Intent();
        String bank = GsonUtils.getGson().toJson(selectBank);
        result.putExtra(RERURN_SELECT_BANK, bank);
        SelectBanksWheelActivity.this.setResult(RESULT_OK, result);
        finish();
        return true;
    }

    @Override
    public void finish() {
        super.finish();
    }

}
