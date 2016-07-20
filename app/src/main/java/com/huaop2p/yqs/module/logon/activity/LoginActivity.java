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
 * Created by zgq on 2016/4/26.
 * 作者:  zhu  guo qing
 * 时间:  2016/4/26 10:43
 * 功能:  注册页面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    /**
     * 手机号,验证码,第一次密码,第二次密码
     */
    private ClearEditText txt_LoginName,txt_Code,txt_OnePass,txt_TowPass;
    private TextView text_web;
    /**
     * 注册按钮
     */
    private Button btn_login,btn_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SetActivityTitle("快速注册");
        initData();
        linData();
    }

    /**
     * 初始化控件
     */
    private void initData() {
        txt_LoginName= (ClearEditText) findViewById(R.id.txt_LoginName);
        txt_Code= (ClearEditText) findViewById(R.id.txt_Code);
        txt_OnePass= (ClearEditText) findViewById(R.id.txt_OnePass);
        txt_TowPass= (ClearEditText) findViewById(R.id.txt_TowPass);
        btn_login= (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_button= (Button) findViewById(R.id.btn_button);
        btn_button.setOnClickListener(this);
        text_web= (TextView) findViewById(R.id.text_web);
        text_web.setOnClickListener(this);
    }
    /**
     * 输入监听
     *
     */
    private void linData() {
        txt_LoginName.addTextChangedListener(new MyTextWatcher());
        txt_Code.addTextChangedListener(new MyTextWatcher());
        txt_OnePass.addTextChangedListener(new MyTextWatcher());
        txt_TowPass.addTextChangedListener(new MyTextWatcher());
        txt_LoginName.setText("");
        txt_Code.setText("");
        txt_OnePass.setText("");
        txt_TowPass.setText("");
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
            String str1 = txt_LoginName.getText().toString();
            String str2 = txt_Code.getText().toString();
            String str3 = txt_OnePass.getText().toString();
            String str4 = txt_TowPass.getText().toString();
            if (str1.equals("") || str2.equals("")||str3.equals("")||str4.equals("")) {
                btn_login.setBackgroundResource(R.drawable.huise_button);
                btn_login.setEnabled(false);
            }  else {
                btn_login.setBackgroundResource(R.drawable.btn_com_corner);
                btn_login.setEnabled(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        String name=txt_LoginName.getText().toString();
        String pass=txt_OnePass.getText().toString();
        String pass1=txt_TowPass.getText().toString();
        String yzm=txt_Code.getText().toString();
        switch (v.getId()){
            /**
             * 注册接口调用
             * {YZM:"验证码","AppointEntity":{"LoginName":"手机号","LoginPass":"密码"}}
             */
            case R.id.btn_login:
                if (name!=null && name.length()==11 && yzm.length()!=0 && pass.length()>=6 && pass1.length()>= 6 ){
                    Map map1=new HashMap();
                    Map map2=new HashMap();
                    map1.put("LoginName",name);
                    map1.put("LoginPass",pass);
                    map2.put("AppointEntity",map1);
                    map2.put("YZM",yzm);
                    MyFinancialWeb.getInstance().PostRegister(map2, new OnRequestLinstener<BaseResponseEntity<LoginedBean>>() {
                        @Override
                        public void success(BaseResponseEntity<LoginedBean> loginedBeanBaseResponseEntity) {
                            if (loginedBeanBaseResponseEntity.Total.equals("0")){
                                Toast toast=Toast.makeText(getApplicationContext(), "验证码错误!", Toast.LENGTH_SHORT);
                                toast .setGravity(Gravity.CENTER, 0, 10);
                                toast.show();
                                txt_Code.setText("");
                            }else {
                                Toast toast=Toast.makeText(getApplicationContext(), "注册成功!", Toast.LENGTH_SHORT);
                                toast .setGravity(Gravity.CENTER, 0, 10);
                                toast.show();
                                Intent intent=new Intent(getApplicationContext(),RankActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void error(int errorCode, String str) {
                        }
                    },HttpUrl.PostRegister,0,RequestMethod.POST,new TypeToken<BaseResponseEntity<LoginedBean>>(){});
                }else {
                    Toast toast=Toast.makeText(getApplicationContext(), "输入信息有误!", Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.show();
                }

                break;
            case R.id.btn_button:
                /**
                 * 验证码接口的调用
                 * {"AppointEntity":{"LoginName":"手机号"}}
                 */
                if (txt_LoginName.getText().length()!=0 && txt_LoginName.getText().length()==11 && txt_LoginName.getText().toString()!=null){
                    Map Dmap=new HashMap();
                    Map Smap=new HashMap();
                    Smap.put("LoginName",name);
                    Dmap.put("AppointEntity",Smap);
                    MyFinancialWeb.getInstance().SendCode(Dmap, new OnRequestLinstener<BaseResponseEntity>() {
                        @Override
                        public void success(BaseResponseEntity baseResponseEntity) {
                            if (baseResponseEntity.Total.equals("0")){
                                Toast toast=Toast.makeText(getApplicationContext(), "该手机号已注册!", Toast.LENGTH_SHORT);
                                toast .setGravity(Gravity.CENTER, 0, 10);
                                toast.show();
                                txt_LoginName.setText("");
                            }else {
                                Toast toast=Toast.makeText(getApplicationContext(), "短信发送中!", Toast.LENGTH_SHORT);
                                toast .setGravity(Gravity.CENTER, 0, 10);
                                toast.show();
                            }
                        }

                        @Override
                        public void error(int errorCode, String str) {
                        }
                    }, HttpUrl.PostSendCode, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity>() {
                    });
                }else {
                    Toast toast=Toast.makeText(getApplicationContext(), "手机号码不正确!", Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.show();
                    txt_LoginName.setText("");
                }

                break;
            case R.id.text_web:
                Intent intent=new Intent(LoginActivity.this,WebLoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
