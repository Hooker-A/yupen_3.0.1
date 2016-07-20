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
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.view.ClearEditText;
import com.huaop2p.yqs.module.logon.model.MyFinancialWeb;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgq on 2016/5/17.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/17 16:13
 * 功能:  修改登录密码
 */
public class LogoPassActivity extends BaseActivity implements View.OnClickListener {
    private ClearEditText cet_pass1, cet_pass2, cet_pass3;
    private Button btn_passok;
    private TextView txt_ti2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logopass);
        SetActivityTitle("修改登录密码");
        initData();
    }

    private void initData() {
        cet_pass1 = (ClearEditText) findViewById(R.id.cet_pass1);
        cet_pass2 = (ClearEditText) findViewById(R.id.cet_pass2);
        cet_pass2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (cet_pass2.getText().toString().length()<6){
                        setPhoneError("密码长度不能小于6位数!");
                        cet_pass3.setEnabled(false);
                        btn_passok.setClickable(false);
                    }else {
                        txt_ti2.setText("");
                        cet_pass3.setEnabled(true);
                        btn_passok.setClickable(true);

                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cet_pass3 = (ClearEditText) findViewById(R.id.cet_pass3);
        cet_pass3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cet_pass3.getText().toString().length()<6){
                    setPhoneError("密码长度不能小于6位数!");
                    btn_passok.setClickable(false);

                }else {
                    txt_ti2.setText("");
                    btn_passok.setClickable(false);
                    btn_passok.setClickable(true);
                    btn_passok.setBackgroundResource(R.drawable.btn_com_corner);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txt_ti2= (TextView) findViewById(R.id.txt_ti2);
        btn_passok = (Button) findViewById(R.id.btn_passok);
        btn_passok.setOnClickListener(this);
    }
    void setPhoneError(String errorstr) {
        txt_ti2.setText(errorstr);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * {"CustomerPass":"新密码","AppointEntity":{"LoginPass":"登陆密码"}
             */
            case R.id.btn_passok:
                    String pass0=cet_pass1.getText().toString();
                    String pass1 = cet_pass2.getText().toString();
                    String pass2=cet_pass3.getText().toString();
                    if (ShareLocalUser.getInstance(this).getLoginPass().equals(pass0)){
                        if (pass1.equals(pass2)){
                            Map oldMap=new HashMap();
                            oldMap.put("LoginPass", pass0);
                            Map newMap=new HashMap();
                            newMap.put("CustomerPass", pass2);
                            newMap.put("AppointEntity",oldMap);
                            MyFinancialWeb.getInstance().PostLogoPass(newMap, new OnRequestLinstener<BaseResponseEntity>() {
                                @Override
                                public void success(BaseResponseEntity baseResponseEntity) {
                                    if (baseResponseEntity.ReturnStatus.equals("0")){
                                        String a = baseResponseEntity.Remarks.toString();
                                        Toast toast=Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT);
                                        toast .setGravity(Gravity.CENTER, 0, 10);
                                        toast.show();
                                        ShareLocalUser.getInstance(LogoPassActivity.this).addLoginPass(cet_pass3.getText().toString());
                                        Intent intent = new Intent(getApplicationContext(), LogonActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast toast=Toast.makeText(getApplicationContext(), baseResponseEntity.Remarks, Toast.LENGTH_SHORT);
                                        toast .setGravity(Gravity.CENTER, 0, 10);
                                        toast.show();
                                    }

                                }

                                @Override
                                public void error(int code, String error) {
                                    LogUtils.e(code + "768120757");

                                }
                            }, HttpUrl.PostLogoPass, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity>(){});
                        }else {
                            Toast toast=Toast.makeText(getApplicationContext(), "两次密码输入不一致!请重新输入", Toast.LENGTH_SHORT);
                            toast .setGravity(Gravity.CENTER, 0, 10);
                            toast.show();
                            cet_pass2.setText("");
                            cet_pass3.setText("");
                        }
                    }
                    else {
                        Toast toast=Toast.makeText(getApplicationContext(), "原密码不正确!", Toast.LENGTH_SHORT);
                        toast .setGravity(Gravity.CENTER, 0, 10);
                        toast.show();
                        ToastUtils.show(getApplicationContext(), "原密码不正确!");
                        cet_pass1.setText("");
                    }
                break;
        }
    }
}
