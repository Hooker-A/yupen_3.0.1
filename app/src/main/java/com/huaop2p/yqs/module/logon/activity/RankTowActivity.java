package com.huaop2p.yqs.module.logon.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.XinDialog;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.view.ClearEditText;
import com.huaop2p.yqs.module.logon.entity.FuiouChinaAreaBean;
import com.huaop2p.yqs.module.logon.entity.FuiuoBankBean;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.lidroid.xutils.util.LogUtils;

/**
 * Created by zgq on 2016/5/17.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/17 10:37
 * 功能:  身份认证第二页
 */
public class RankTowActivity extends BaseActivity implements View.OnClickListener {
    private ClearEditText cet_bankcode;
    private TextView cet_weizhi,cet_bank;
//    private TextView cet_weizhi, cet_bank;
    private Button btn_tiaoguo, btn_banktijiao;
    private Intent mIntent;
    private String bank,bankCode,addres,City_id;
    public static final String CET_NAME="cet_name";
    public static final String CET_CODE="cet_code";
    public static final String GET_TYPE="typeCode";
    private String name,code,typecoe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranktow);
        Intent inten=getIntent();
        name=inten.getStringExtra(CET_NAME);
        code=inten.getStringExtra(CET_CODE);
        typecoe=inten.getStringExtra(GET_TYPE);
        initData();
        linData();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        cet_bankcode = (ClearEditText) findViewById(R.id.cet_bankcode);
        cet_bank = (TextView) findViewById(R.id.cet_bank);
        cet_bank.setOnClickListener(this);
        cet_weizhi = (TextView) findViewById(R.id.cet_weizhi);
        cet_weizhi.setOnClickListener(this);
        btn_tiaoguo = (Button) findViewById(R.id.btn_tiaoguo);
        btn_tiaoguo.setOnClickListener(this);
        btn_banktijiao = (Button) findViewById(R.id.btn_banktijiao);
        btn_banktijiao.setOnClickListener(this);

    }

    /**
     * 输入框监听
     */
    private void linData() {
        cet_bankcode.addTextChangedListener(new MyTextWatcher());
        cet_bank.addTextChangedListener(new MyTextWatcher());
        cet_weizhi.addTextChangedListener(new MyTextWatcher());
        cet_bankcode.setText("");
        cet_bank.setText("");
        cet_weizhi.setText("");
    }
    class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String str1 = cet_bankcode.getText().toString();
            String str2 = cet_bank.getText().toString();
            String str3 = cet_weizhi.getText().toString();
            if (str1.equals("") || str2.equals("")||str3.equals("")) {
                btn_banktijiao.setBackgroundResource(R.drawable.huise_button);
                btn_banktijiao.setEnabled(false);
            }else if (cet_bankcode.length()<14){
                btn_banktijiao.setBackgroundResource(R.drawable.huise_button);
                btn_banktijiao.setEnabled(false);

            }else {
                btn_banktijiao.setBackgroundResource(R.drawable.btn_com_corner);
                btn_banktijiao.setEnabled(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tiaoguo:
                finish();
                break;
            case R.id.btn_banktijiao:
                bank = cet_bankcode.getText().toString();
                bankCode = cet_bank.getText().toString();
                addres = cet_weizhi.getText().toString();
                XinDialog.Builder builder=new XinDialog.Builder(RankTowActivity.this);
                builder.setMessage(bank);
                builder.setMessage2(bankCode);
                builder.setMessage3(addres);
                builder.setMessage4("银行卡号:");
                builder.setMessage5("发卡银行:");
                builder.setMessage6("开户地区:");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mIntent = new Intent(getApplicationContext(), RankThreeActivity.class);
                        mIntent.putExtra(RankThreeActivity.CET_BANK,bank);
                        mIntent.putExtra(RankThreeActivity.CET_BANKCODE,bankCode);
                        mIntent.putExtra(RankThreeActivity.CET_WEIZHI,City_id);
                        mIntent.putExtra(RankThreeActivity.CET_CODET,code);
                        mIntent.putExtra(RankThreeActivity.CET_NAMET,name);
                        mIntent.putExtra(RankThreeActivity.GET_TYPET,typecoe);
                        startActivity(mIntent);
                        finish();
                    }
                });
                builder.setNegativeButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create(true).show();

                break;
            case R.id.cet_bank://富有支持的银行
                Intent startBanks = new Intent(RankTowActivity.this, SelectBanksWheelActivity.class);
                String bankStr = "";
                if (selectBank != null)
                    bankStr = GsonUtils.getGson().toJson(selectBank);
                    startBanks.putExtra(SelectBanksWheelActivity.INTENT_DEFAULT_BANK, bankStr);
                    startActivityForResult(startBanks, 0);


                break;
            case R.id.cet_weizhi://富有支持的地区
                Intent startProvince = new Intent(RankTowActivity.this, SelectProvinceAreaWheelActivity.class);
                startProvince.putExtra(SelectProvinceAreaWheelActivity.INTENT_DEFAULT_PROFINCE_KEY, backChinaProvince == null ? "" : backChinaProvince.City_ID);
                startProvince.putExtra(SelectProvinceAreaWheelActivity.INTENT_DEFAULT_AREA_KEY, backChinaArea == null ? "" : backChinaArea.City_ID);
                startActivityForResult(startProvince, 1);
                break;
        }
    }

    FuiouChinaAreaBean backChinaProvince = null, backChinaArea = null;
    FuiuoBankBean selectBank = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;

        if (requestCode == 0) {//银行
            String bankstr = data.getExtras().getString(SelectBanksWheelActivity.RERURN_SELECT_BANK);
            if (bankstr != null && bankstr.length() > 0) {
                selectBank = GsonUtils.getGson().fromJson(bankstr, FuiuoBankBean.class);
                if (selectBank != null)
                    cet_bank.setText(selectBank.BankChineseName.trim());
//                cet_bank.setGravity(Gravity.CENTER);
            }
        } else if (requestCode == 1) {//地区
            try {
                String provincee = data.getStringExtra(SelectProvinceAreaWheelActivity.RETURN_SELECT_PROVINCE);
                if (provincee != null && provincee.length() > 0)
                    backChinaProvince = GsonUtils.getGson().fromJson(provincee, FuiouChinaAreaBean.class);
                String area = data.getStringExtra(SelectProvinceAreaWheelActivity.RETURN_SELECT_AREA);
                if (area != null && area.length() > 0)
                    backChinaArea = GsonUtils.getGson().fromJson(area, FuiouChinaAreaBean.class);
                if (backChinaArea != null && backChinaProvince != null)
                    cet_weizhi.setText(backChinaProvince.City_Name + "       " + backChinaArea.City_Name);
                 City_id=backChinaArea.City_ID;
                String  na=backChinaProvince.City_Name;
                LogUtils.e(na+"-----------------------"+City_id);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void finish() {
        super.finish();
    }
}
