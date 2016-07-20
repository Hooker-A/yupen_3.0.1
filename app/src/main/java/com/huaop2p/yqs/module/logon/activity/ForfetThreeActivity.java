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
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgq on 2016/5/23.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/23 16:13
 * 功能:  忘记密码 三
 */
public class ForfetThreeActivity extends BaseActivity implements View.OnClickListener {

    private ClearEditText txt_forgetpass1, txt_forgetpass2;
    private Button btn_forget;
    public final static String IPhone="iphone";
    public final static String YZM="yzm";
    private Intent intent;
//    private Intent intent2;
    private String Miphone;
    private String YZCode;
    private WaitDialog waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetthree);
        SetActivityTitle("忘记密码");
        intent = getIntent();
//        intent2=getIntent();
        if (intent!=null){
            YZCode = intent.getStringExtra(YZM);
            Miphone = intent.getStringExtra(IPhone);

        }

        initData();
        linData();
    }

    private void initData() {
        txt_forgetpass1 = (ClearEditText) findViewById(R.id.txt_forgetpass1);
        txt_forgetpass2 = (ClearEditText) findViewById(R.id.txt_forgetpass2);
        btn_forget = (Button) findViewById(R.id.btn_forget);
        btn_forget.setOnClickListener(this);
    }
    /**
     * 控件监听
     */
    private void linData() {
        txt_forgetpass1.addTextChangedListener(new MyTextWatcher());
        txt_forgetpass2.addTextChangedListener(new MyTextWatcher());
        txt_forgetpass1.setText("");
        txt_forgetpass2.setText("");
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
            String str1 = txt_forgetpass1.getText().toString();
            String str2 = txt_forgetpass2.getText().toString();
            if (str1.equals("")||str1.length()<6||str2.equals("")||str2.length()<6) {
                btn_forget.setBackgroundResource(R.drawable.huise_button);
                btn_forget.setEnabled(false);
            } else {
                btn_forget.setBackgroundResource(R.drawable.btn_com_corner);
                btn_forget.setEnabled(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        String p1=txt_forgetpass1.getText().toString();
        String p2=txt_forgetpass2.getText().toString();
        switch (v.getId()) {
            case R.id.btn_forget:
                waitDialog=new WaitDialog(ForfetThreeActivity.this);
                waitDialog.show();
                if (!p1.equals(p2)){
                    Toast toast=Toast.makeText(getApplicationContext(), "两次输入不一致,请重新输入!", Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.show();
                    txt_forgetpass1.setText("");
                    txt_forgetpass2.setText("");
                    waitDialog.dismiss();
                }else {
                    httpData();
                }
                break;
        }
    }

    /**
     * 网络请求
     * {YZM:"验证码","AppointEntity":{"LoginName":"手机号","LoginPass":"密码"}}
     */
    private void httpData() {
        String pass=txt_forgetpass2.getText().toString();
        Map Smap=new HashMap();
        Map Dmap=new HashMap();

        Smap.put("LoginName",Miphone);
        Smap.put("LoginPass",pass);
        Dmap.put("YZM",YZCode);
        LogUtils.e(YZCode+"----------------->");
        Dmap.put("AppointEntity",Smap);
        MyFinancialWeb.getInstance().PostSeekPass(Dmap, new OnRequestLinstener<BaseResponseEntity>() {
            @Override
            public void success(BaseResponseEntity baseResponseEntity) {
                if (baseResponseEntity.ReturnStatus.equals("0")) {
                    waitDialog.dismiss();
                    Toast toast=Toast.makeText(getApplicationContext(), "密码修改成功,请重新登录!", Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.show();
                    intent=new Intent(ForfetThreeActivity.this,LogonActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    if (waitDialog!=null){
                        waitDialog.dismiss();
                    }
                    Toast toast=Toast.makeText(getApplicationContext(), baseResponseEntity.ReturnReason, Toast.LENGTH_SHORT);
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
        }, HttpUrl.PostSeekPassWord, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity>() {
        });

    }
}
