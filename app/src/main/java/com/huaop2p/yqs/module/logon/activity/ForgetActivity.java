package com.huaop2p.yqs.module.logon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.WaitDialog;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.view.ClearEditText;
import com.huaop2p.yqs.module.logon.model.MyFinancialWeb;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgq on 2016/4/26.
 * 作者:  zhu  guo qing
 * 时间:  2016/4/26 14:52
 * 功能:  忘记密码
 */
public class ForgetActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 发送短信
     */
    private Button btn_forget;
    private ClearEditText txt_forgetName;
    private Intent mIntent;
    private WaitDialog waitDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        SetActivityTitle("忘记密码");
        initData();
        linData();
    }

    private void initData() {
        btn_forget = (Button) findViewById(R.id.btn_forget);
        btn_forget.setOnClickListener(this);
        txt_forgetName = (ClearEditText) findViewById(R.id.txt_forgetName);

    }
    /**
     * 输入监听
     *
     */
    private void linData() {
        txt_forgetName.addTextChangedListener(new MyTextWatcher());
        txt_forgetName.setText("");
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
            String str1 = txt_forgetName.getText().toString();
            if (str1.equals("")|| str1.length()!=11) {
                btn_forget.setBackgroundResource(R.drawable.huise_button);
                btn_forget.setEnabled(false);
            }  else {
                btn_forget.setBackgroundResource(R.drawable.btn_com_corner);
                btn_forget.setEnabled(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_forget:
                waitDialog=new WaitDialog(ForgetActivity.this);
                waitDialog.show();
                HttpData();

                break;
        }
    }

    /**
     * 网络请求
     * {"AppointEntity":{"LoginName":"手机号"}}
     */
    private void HttpData() {
        final String iphone = txt_forgetName.getText().toString();
        Map Smap = new HashMap();
        Smap.put("LoginName", iphone);
        Map Dmap = new HashMap();
        Dmap.put("AppointEntity", Smap);
        MyFinancialWeb.getInstance().PostSeekPass(Dmap, new OnRequestLinstener<BaseResponseEntity>() {
            @Override
            public void success(BaseResponseEntity baseResponseEntity) {
                String toas = baseResponseEntity.ReturnReason;
                String star=baseResponseEntity.ReturnStatus;
                waitDialog.dismiss();
                if (("0").equals(star)) {
                    mIntent = new Intent(getApplicationContext(), ForgetTowActivity.class);
                    mIntent.putExtra(ForgetTowActivity.IPHONE,iphone);
                    mIntent.putExtra("aa", iphone);
                    startActivity(mIntent);
                    finish();

                    Toast toast=Toast.makeText(getApplicationContext(), "短信发送中...", Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.show();
                } else {
                    if (waitDialog!=null){
                        waitDialog.dismiss();
                    }
                    Toast toast=Toast.makeText(getApplicationContext(),toas, Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.show();

                }
            }

            @Override
            public void error(int code, String error) {
                if (waitDialog!=null){
                    waitDialog.dismiss();
                }

            }
        }, HttpUrl.PostSeekPass, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity>() {
        });
    }
}
