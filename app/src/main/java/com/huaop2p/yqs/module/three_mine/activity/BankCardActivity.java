package com.huaop2p.yqs.module.three_mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.logon.activity.RankActivity;
import com.huaop2p.yqs.module.three_mine.model.entity.BankCardBean;
import com.huaop2p.yqs.module.three_mine.model.entity.BankCardListBean;
import com.huaop2p.yqs.module.three_mine.model.impl.BankCardImpl;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maoxiaofei on 2016/5/19.
 */
public class BankCardActivity extends BaseAutoActivity {

    private TextView btn_bankcard;
    public TextView tv_bankname;
    public TextView tv_bankcardnumber;
    private RelativeLayout relay_bank;
    private RelativeLayout relay_bankbutton;
    private List<BankCardBean> list;

    @Override
    public Object getObject() {
        return this;
    }
    @Override
    public int getLayoutId() {

        return R.layout.item_bankcard;
    }

    @Override
    public void initViews() {
//        SetActivityTitle("我的银行卡");
        btn_bankcard = (TextView) findViewById(R.id.btn_bankcard);
        tv_bankname = (TextView) findViewById(R.id.tv_bankname);
        tv_bankcardnumber = (TextView) findViewById(R.id.tv_bankcardnumber);
        relay_bank = (RelativeLayout) findViewById(R.id.relay_bank);
        relay_bankbutton = (RelativeLayout) findViewById(R.id.relay_bankbutton);
    }

    @Override
    public void initData() {


        getbankcard();

    }

    private void getbankcard() {
        Map map = new HashMap<>();
        //获取我的银行卡列表地址
        BankCardImpl.getintence().Getbankcard(map, new OnRequestLinstener<BaseResponseEntity<BankCardListBean>>() {
            @Override
            public void success(BaseResponseEntity<BankCardListBean> bankCardBeanBaseResponseEntity) {
                LogUtils.e(GsonUtils.getGson().toJson(bankCardBeanBaseResponseEntity));
                if (bankCardBeanBaseResponseEntity.ReturnMessage.Fuyou.size() != 0 || bankCardBeanBaseResponseEntity.ReturnMessage.UMPay.size() != 0) {
                    relay_bank.setVisibility(View.VISIBLE);
                    for (int i = 0; i < bankCardBeanBaseResponseEntity.ReturnMessage.Fuyou.size(); i++) {

                        String bankaccount = bankCardBeanBaseResponseEntity.ReturnMessage.Fuyou.get(i).getBankAccount();
                        String s = bankaccount.substring(0, bankaccount.length() - 4);//银行卡号前15位
                        String ss = bankaccount.substring(bankaccount.length() - 4);//银行卡号后4位

                        tv_bankcardnumber.setText("****   ****   ****" + ss);

                        tv_bankname.setText(bankCardBeanBaseResponseEntity.ReturnMessage.Fuyou.get(i).getBankName());

                        switch (bankCardBeanBaseResponseEntity.ReturnMessage.Fuyou.get(i).getGa_BankId()){
                            case "0102":
                                relay_bank.setBackgroundResource(R.drawable.gongshang);
                                break;
                            case "0103":
                                relay_bank.setBackgroundResource(R.drawable.nongye);
                                break;
                            case "0104":
                                relay_bank.setBackgroundResource(R.drawable.zhonghang);
                                break;
                            case "0105":
                                relay_bank.setBackgroundResource(R.drawable.jianhang);
                                break;
                            case "0308":
                                relay_bank.setBackgroundResource(R.drawable.zhaoshang);
                                break;
                            case "0309":
                                relay_bank.setBackgroundResource(R.drawable.xingye);
                                break;
                            case "0305":
                                relay_bank.setBackgroundResource(R.drawable.minsheng);
                                break;
                            case "0304":
                                relay_bank.setBackgroundResource(R.drawable.huaxia);
                                break;
                            case "0403":
                                relay_bank.setBackgroundResource(R.drawable.youzheng);
                                break;
                            case "0303":
                                relay_bank.setBackgroundResource(R.drawable.guangda);
                                break;
                            case "0302":
                                 relay_bank.setBackgroundResource(R.drawable.zhongxin);
                                break;
                            case "0306":
                                relay_bank.setBackgroundResource(R.drawable.guangfa);
                                break;
                            case "0307":
                                relay_bank.setBackgroundResource(R.drawable.pingan);
                                break;
                            case "0310":
                                relay_bank.setBackgroundResource(R.drawable.pufa);
                                break;
                            default:
                                break;
                        }

                    }
                    btn_bankcard.setEnabled(false);
                    btn_bankcard.setVisibility(View.GONE);
                } else {
                    relay_bank.setVisibility(View.GONE);
                    relay_bankbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(BankCardActivity.this, RankActivity.class);
                            startActivity(intent);
                        }
                    });

                }
            }


            @Override
            public void error(int code, String error) {
                LogUtils.e(error);
            }
        }, HttpUrl.GetBankCard, 0, RequestMethod.GET, new TypeToken<BaseResponseEntity<BankCardListBean>>() {
        });
    }
}
