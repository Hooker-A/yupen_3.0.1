package com.huaop2p.yqs.module.logon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.WaitDialog;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.view.ClearEditText;
import com.huaop2p.yqs.module.logon.entity.LoginedBean;
import com.huaop2p.yqs.module.logon.model.MyFinancialWeb;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgq on 2016/5/17.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/17 14:25
 * 功能:  身份认证第三页
 */
public class RankThreeActivity extends BaseActivity implements View.OnClickListener{
    private ClearEditText cet_logopass1,cet_logopass2,cet_logopass3,cet_logopass4;
    private Button btn_ok;
    private TextView txt_xieyi;
    public static final String CET_BANKCODE="cet_bankcode";
    public static final String CET_BANK="cet_bank";
    public static final String CET_WEIZHI="cet_weizhi";
    public static final String CET_NAMET="cet_namet";
    public static final String CET_CODET="cet_codet";
    public static final String GET_TYPET="typeCodet";
    private Intent mIntent;
    private String cet_bankcode,cet_bank,cet_weizhi,namet,codet,typecode;
    private WaitDialog waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankthree);
        SetActivityTitle("身份认证");
        this.mIntent=getIntent();
        cet_bankcode=mIntent.getStringExtra(CET_BANKCODE);
        cet_bank=mIntent.getStringExtra(CET_BANK);
        cet_weizhi=mIntent.getStringExtra(CET_WEIZHI);
        namet=mIntent.getStringExtra(CET_NAMET);
        codet=mIntent.getStringExtra(CET_CODET);
        typecode=mIntent.getStringExtra(GET_TYPET);
        initData();
        linData();
    }

    private void linData() {
        cet_logopass1.addTextChangedListener(new MyTextWatcher());
        cet_logopass2.addTextChangedListener(new MyTextWatcher());
        cet_logopass3.addTextChangedListener(new MyTextWatcher());
        cet_logopass4.addTextChangedListener(new MyTextWatcher());
        cet_logopass1.setText("");
        cet_logopass2.setText("");
        cet_logopass3.setText("");
        cet_logopass4.setText("");
    }

    private void initData() {
        cet_logopass1= (ClearEditText) findViewById(R.id.cet_logopass1);
        cet_logopass2= (ClearEditText) findViewById(R.id.cet_logopass2);
        cet_logopass3= (ClearEditText) findViewById(R.id.cet_logopass3);
        cet_logopass4= (ClearEditText) findViewById(R.id.cet_logopass4);
        btn_ok= (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        txt_xieyi= (TextView) findViewById(R.id.txt_xieyi);
        txt_xieyi.setOnClickListener(this);
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
            String str1 = cet_logopass1.getText().toString();
            String str2 = cet_logopass2.getText().toString();
            String str3 = cet_logopass3.getText().toString();
            String str4 = cet_logopass4.getText().toString();
            if (str1.equals("") || str2.equals("")||str3.equals("")|| str4.equals("")) {
                btn_ok.setBackgroundResource(R.drawable.huise_button);
                btn_ok.setEnabled(false);
            } else{
                btn_ok.setBackgroundResource(R.drawable.btn_com_corner);
                btn_ok.setEnabled(true);
            }
        }
    }


    @Override
    public void onClick(View v) {
        String a1=cet_logopass1.getText().toString();
        String a2=cet_logopass2.getText().toString();
        String b1=cet_logopass3.getText().toString();
        String b2=cet_logopass4.getText().toString();
        switch (v.getId()){
            case R.id.btn_ok:
                waitDialog=new WaitDialog(RankThreeActivity.this);
                waitDialog.show();
                if (!a1.equals(a2)){
                    Toast toast=Toast.makeText(getApplicationContext(), "登录密码两次输入不一致!", Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.show();
                    return;
                }
                if (!b1.equals(b2)){
                    Toast toast=Toast.makeText(getApplicationContext(), "提现密码两次输入不一致!", Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.show();
                    return;
                }
                httpData();
                break;
            case R.id.txt_xieyi:
                Intent intent=new Intent(RankThreeActivity.this,RankXieyiActivity.class);
                startActivity(intent);
                break;

        }
    }

    /**
     * PostUpdateCardID
     * 网络加载
     * {"AppointEntity":{"CertifType":"0：身份证 7：港澳护照等""TrueName":"姓名",
     * "CardID":"身份证号","Email":"邮箱","GA_CityID":"金账户城市代码","GA_BankID":"开户行代码","GA_BankName":"开户行名称",
     * "GA_BankAccount":"银行卡号","GA_WithdrawalsPassWord":"金账户提现密码","GA_LoginPassWord":"金账户登录密码","UrlPhotoback":"背面照片"}}
     */
    String bankid,typec;

    private void httpData() {
        if (typecode.equals("身份证")){
            typec="0";
        }else {
            typec="7";
        }
        switch (cet_bankcode){
            case "中国工商银行":
                bankid="0102";
                break;
            case "中国农业银行":
                bankid="0103";
                break;
            case "中国银行":
                bankid="0104";
                break;
            case "中国建设银行":
                bankid="0105";
                break;
            case "招商银行":
                bankid="0308";
                break;
            case "兴业银行":
                bankid="0309";
                break;
            case "中国民生银行":
                bankid="0305";
                break;
            case "华夏银行":
                bankid="0304";
                break;
            case "国家邮政局邮政储汇局":
                bankid="0403";
                break;
            case "中国光大银行":
                bankid="0303";
                break;
            case "中信实业银行":
                bankid="0302";
                break;
            case "广东发展银行":
                bankid="0306";
                break;
            case "平安银行股份有限公司":
                bankid="0307";
                break;
            case "上海浦东发展银行":
                bankid="0310";
                break;
            default:
                break;
        }
        String pass1=cet_logopass1.getText().toString();
        String pass2=cet_logopass3.getText().toString();
        Map Smap=new HashMap();
        Map Dmap=new HashMap();
        Smap.put("CertifType",typec);
        Smap.put("TrueName",namet);
        Smap.put("CardID",codet);
        Smap.put("Email","");
        Smap.put("GA_CityID",cet_weizhi);
        Smap.put("GA_BankID",bankid);
        Smap.put("GA_BankName",cet_bankcode);
        Smap.put("GA_BankAccount",cet_bank);
        Smap.put("GA_WithdrawalsPassWord",pass1);
        Smap.put("GA_LoginPassWord",pass2);
        Smap.put("UrlPhotoback","");
        Dmap.put("AppointEntity",Smap);
        MyFinancialWeb.getInstance().PostUpdateCardID(Dmap, new OnRequestLinstener<BaseResponseEntity<LoginedBean>>() {
            @Override
            public void success(BaseResponseEntity<LoginedBean> loginedBeanBaseResponseEntity) {
                if (loginedBeanBaseResponseEntity.ReturnStatus.equals("0")){
                    waitDialog.dismiss();
                    Toast toast=Toast.makeText(getApplicationContext(), "绑定成功!", Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.show();
                    Intent intent=new Intent(getApplicationContext(),AeestActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast toast=Toast.makeText(getApplicationContext(), loginedBeanBaseResponseEntity.Remarks, Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.show();
                    waitDialog.dismiss();
                }

            }

            @Override
            public void error(int code, String error) {

            }
        }, HttpUrl.PostUpdateCardID,0, RequestMethod.POST,new TypeToken<BaseResponseEntity<LoginedBean>>(){});


    }
}
