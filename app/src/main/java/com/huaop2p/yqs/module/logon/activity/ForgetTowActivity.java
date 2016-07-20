package com.huaop2p.yqs.module.logon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.huaop2p.yqs.module.logon.model.MyFinancialWeb;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgq on 2016/5/23.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/23 11:20
 * 功能:  忘记密码第二页
 */
public class ForgetTowActivity extends BaseActivity implements View.OnClickListener {
    private TextView txt_iphone,txt_fasong;
    private ClearEditText txt_yzmcode;
    private Button btn_forget;
    public static String IPHONE;
    private Intent mIntent;
    private Intent getmIntent;
    private String Miphoen;
    private String YZM;
    private WaitDialog waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgettow);
        SetActivityTitle("忘记密码");
        getmIntent=getIntent();
        Miphoen = getmIntent.getStringExtra(IPHONE);
        initData();
        linData();
    }
    private void initData() {
        txt_iphone = (TextView) findViewById(R.id.txt_iphone);
        txt_iphone.setText(Miphoen);
        txt_yzmcode = (ClearEditText) findViewById(R.id.txt_yzmcode);
        btn_forget = (Button) findViewById(R.id.btn_forget);
        btn_forget.setOnClickListener(this);
        txt_fasong= (TextView) findViewById(R.id.txt_fasong);
        txt_fasong.setOnClickListener(this);
       timer.start();

    }
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            txt_fasong.setText((millisUntilFinished / 1000) + "秒后可重发");
            txt_fasong.setEnabled(false);
            txt_fasong.setTextColor(getResources().getColor(R.color.texthui));

        }

        @Override
        public void onFinish() {
            txt_fasong.setText("重新获取验证码");
            txt_fasong.setEnabled(true);
            txt_fasong.setTextColor(getResources().getColor(R.color.yufangbao));
        }
    };

    /**
     * 控件监听
     */
    private void linData() {
        txt_yzmcode.addTextChangedListener(new MyTextWatcher());
        txt_yzmcode.setText("");
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
            String str1 = txt_yzmcode.getText().toString();
            if (str1.equals("")||str1.length()!=6) {
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
        switch (v.getId()) {
            case R.id.btn_forget:
                waitDialog=new WaitDialog(ForgetTowActivity.this);
                waitDialog.show();
                httpData();
                break;
            case R.id.txt_fasong:
                waitDialog=new WaitDialog(ForgetTowActivity.this);
                waitDialog.show();
                HttpData();
                break;
        }
    }

    /**
     * 网络请求
     * {YZM:"验证码","AppointEntity":{"LoginName":"手机号"}}
     */
    private void httpData() {
        YZM = txt_yzmcode.getText().toString();
        Map Smap = new HashMap();
        Smap.put("LoginName", Miphoen);
        Map Dmap = new HashMap();
        Dmap.put("AppointEntity", Smap);
        Dmap.put("YZM", YZM);
        LogUtils.e(YZM+"--------------------------");
        MyFinancialWeb.getInstance().PostSeekPass(Dmap, new OnRequestLinstener<BaseResponseEntity>() {
            @Override
            public void success(BaseResponseEntity baseResponseEntity) {
                waitDialog.dismiss();
                if (baseResponseEntity.ReturnStatus.equals("0")){

                    mIntent=new Intent(getApplicationContext(),ForfetThreeActivity.class);
                    mIntent.putExtra(ForfetThreeActivity.YZM,YZM);
                    LogUtils.e(YZM + "--------------------------");
                    mIntent.putExtra(ForfetThreeActivity.IPhone,Miphoen);
                    startActivity(mIntent);
                    finish();
                }else {
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
        }, HttpUrl.PostSeekNext, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity>() {
        });
    }
    /**
     *   短信验证码网络请求
     * {"AppointEntity":{"LoginName":"手机号"}}
     */
    private void HttpData() {
        Map Smap = new HashMap();
        Map Dmap = new HashMap();
        Smap.put("LoginName", Miphoen);
        Dmap.put("AppointEntity", Smap);
        MyFinancialWeb.getInstance().PostSeekPass(Dmap, new OnRequestLinstener<BaseResponseEntity>() {
            @Override
            public void success(BaseResponseEntity baseResponseEntity) {
                    String toas = baseResponseEntity.ReturnReason;
                    String star = baseResponseEntity.ReturnStatus;
                    if (("0").equals(star)) {
                        waitDialog.dismiss();
                        timer.start();
                        Toast toast = Toast.makeText(getApplicationContext(), "短信发送中...", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 10);
                        toast.show();
                    } else {
                        if (waitDialog!=null){
                            waitDialog.dismiss();
                        }
                        Toast toast = Toast.makeText(getApplicationContext(), toas, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 10);
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
