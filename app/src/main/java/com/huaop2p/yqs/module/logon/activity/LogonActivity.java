package com.huaop2p.yqs.module.logon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.WaitDialog;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.InvestStepCache;
import com.huaop2p.yqs.module.base.bases.PreferencesUtils;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.four_set.entity.ReqMQTbean;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.logon.entity.LoginedBean;
import com.huaop2p.yqs.module.base.view.ClearEditText;
import com.huaop2p.yqs.module.logon.model.MyFinancialWeb;
import com.huaop2p.yqs.utils.auto.SoapUtils;
import com.huaop2p.yqs.utils.net.CallServer;
import com.huaop2p.yqs.utils.net.FastJsonRequest;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.Response;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by zgq on 2016/4/22.
 * 作者:  zhu  guo qing
 * 时间:  2016/4/22 11:25
 * 功能:  登陆界面
 */
public class LogonActivity extends BaseActivity implements View.OnClickListener {

    private ClearEditText txtLoginName, txtLoginPass;
    private Button btn_login;
    private TextView txt_logon, txt_wpass;
    private boolean isForgetPass;
    public static String LOGIN_STATE = "LOGIN_STATE";//登录状态
    private WaitDialog waitDialog;


    public static final String pushserver = "http://219.143.6.72:96/Api/Client/Connect";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);
        SetActivityTitle("登录");

        //初始化控件
        initData();
        linData();
    }

    /**
     * 控件初始化
     */
    private void initData() {
        txtLoginName = (ClearEditText) findViewById(R.id.txtLoginName);
        txtLoginName.setOnClickListener(this);
        txtLoginPass = (ClearEditText) findViewById(R.id.txtLoginPass);
        txtLoginPass.setOnClickListener(this);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        txt_logon = (TextView) findViewById(R.id.txt_logon);
        txt_logon.setOnClickListener(this);
        txt_wpass = (TextView) findViewById(R.id.txt_wpass);
        txt_wpass.setOnClickListener(this);
    }

    /**
     * 输入监听
     *
     */
    private void linData() {
        txtLoginName.addTextChangedListener(new MyTextWatcher());
        txtLoginPass.addTextChangedListener(new MyTextWatcher());
        txtLoginName.setText("");
        txtLoginPass.setText("");
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
            String str1 = txtLoginName.getText().toString();
            String str2 = txtLoginPass.getText().toString();
            if (str1.equals("") || str2.equals("")) {
                btn_login.setBackgroundResource(R.drawable.huise_button);
                btn_login.setEnabled(false);
            }  else {
                btn_login.setBackgroundResource(R.drawable.btn_com_corner);
                btn_login.setEnabled(true);
            }
        }
    }

    @Override
    public void onClick(final View v) {
        String loginName = txtLoginName.getText().toString();
        final String loginPass = txtLoginPass.getText().toString();
        switch (v.getId()) {
            case R.id.btn_login:
                waitDialog=new WaitDialog(LogonActivity.this);
                waitDialog.show();
                if (txtLoginName.getText() != null && txtLoginName.getText().length() != 11) {
                    Toast toast=Toast.makeText(getApplicationContext(), "手机号不正确 !", Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.show();
                    waitDialog.dismiss();
                    return;
                } else if (txtLoginPass.getText() == null) {
                    Toast toast=Toast.makeText(getApplicationContext(), "密码不能为空!", Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.show();
                    waitDialog.dismiss();

                    return;
                } else if (txtLoginPass.getText().length() < 6) {
                    Toast toast=Toast.makeText(getApplicationContext(), "密码长度应该大于6位!", Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.show();
                    waitDialog.dismiss();

                    return;
                }

                /**
                 * 登录接口
                 * {"AppointEntity":{"LoginName":"账号","LoginPass":"密码"}}
                 */
                Map map = new HashMap();
                Map map1 = new HashMap();
                map1.put("LoginName", loginName);
                map1.put("LoginPass", loginPass);
                map.put("AppointEntity", map1);
                MyFinancialWeb.getInstance().logo(map, new OnRequestLinstener<BaseResponseEntity<LoginedBean>>() {
                    @Override
                    public void success(BaseResponseEntity<LoginedBean> o) {
//                        super.success(o);
                        if (SoapUtils.isResponseOk(LogonActivity.this, o)) {
                            if (o.Total.equals("1")) {
                                Toast toast = Toast.makeText(LogonActivity.this,
                                        "登录成功", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                LinearLayout toastView = (LinearLayout) toast.getView();
                                ImageView imageCodeProject = new ImageView(LogonActivity.this);
                                imageCodeProject.setImageResource(R.drawable.dh001);
                                toastView.addView(imageCodeProject, 0);
                                toast.show();
                                LoginedBean bean = o.ReturnMessage;
                                ShareLocalUser.getInstance(LogonActivity.this).addLoginUser(bean);
                                ShareLocalUser.getInstance(LogonActivity.this).addUserIcon(bean.UrlHeadPhoto);
                                ShareLocalUser.getInstance(LogonActivity.this).addLoginPass(loginPass);
                                if (InvestStepCache.login_for == InvestStepCache.login_for_modify_gs_password) {
                                    ShareLocalUser.getInstance(LogonActivity.this).addGsPass(null);
                                    Intent startSetgspass = new Intent(LogonActivity.this, LockSetupActivity.class);
                                    startActivity(startSetgspass);
                                }
                                if (isForgetPass) {
                                    ShareLocalUser.getInstance(getApplicationContext()).addIsOpenGsPass(false);
                                }
                                AppApplication.isLogin=true;
                                AppApplication.user=o.ReturnMessage;
                                EventBus.getDefault().post(o.ReturnMessage);
                                Intent mIntent = new Intent();
                                mIntent.putExtra(LOGIN_STATE, true);
                                // 设置结果，并进行传送

                                setResult(0, mIntent);

                                Intent intent = new Intent();
                                intent.putExtra(AccountActivity.LOGIN_PHOTO,bean.UrlHeadPhoto);
                                setResult(12, intent);
                                waitDialog.dismiss();
                                finish();
                            }else {
                                Toast toast=Toast.makeText(getApplicationContext(), o.ReturnReason, Toast.LENGTH_SHORT);
                                toast .setGravity(Gravity.CENTER, 0, 10);
                                toast.show();
                                if (waitDialog!=null){
                                    waitDialog.dismiss();

                                }

                            }

                        }else {
                            Toast toast=Toast.makeText(getApplicationContext(), "登录信息有误!", Toast.LENGTH_SHORT);
                            toast .setGravity(Gravity.CENTER, 0, 10);
                            toast.show();
                            if (waitDialog!=null){
                                waitDialog.dismiss();

                            }


                        }
                    }

                    @Override
                    public void error(int errorCode, String str) {

                    }
                }, HttpUrl.Logins, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity<LoginedBean>>() {
                });
                if (waitDialog!=null){
                    waitDialog.dismiss();

                }
                break;
            case R.id.txt_logon://快速注册
                Intent LoginItent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(LoginItent);
                break;
            case R.id.txt_wpass://忘记密码
                Intent WpassIntent = new Intent(getApplicationContext(), ForgetActivity.class);
                startActivity(WpassIntent);
                break;
        }

    }
    /**
     * 修改客户资料信息
     */
//    class AsyncModifyPushMsgTask extends AsyncTask<ReqMQTbean, Integer, String> {
//
//        @Override
//        protected String doInBackground(ReqMQTbean... params) {
//            String res = modifyPushUser(pushserver, params[0]);
//            if (res != null && res.toLowerCase() == "true") {
//                PreferencesUtils.putString(LogonActivity.this, ShareLocalUser.SHARE_KEY_PUSH_CLIENT_ID, res);
//            }
//            LogUtils.e("推送：" + res);
//            return res;
//        }
//    }

    public void lognpost(LoginedBean userBean){

        ReqMQTbean mqTbean = new ReqMQTbean();
        mqTbean.DeviceId = PreferencesUtils.getString(this, ShareLocalUser.SHARE_KEY_PUSH_CLIENT_ID, null);
        mqTbean.DeviceType = "Android";
        if (userBean.LoginName != null) {
            mqTbean.UserId = userBean.LoginName;
        } else {
            mqTbean.UserId = "no null";
        }
        mqTbean.UserName = userBean.TrueName+"android";
        if (userBean.TrueName == null)
            mqTbean.UserName = userBean.LoginName;


        Map map = new HashMap();
        modifyPushUser(map, new OnRequestLinstener<BaseResponseEntity<ReqMQTbean>>() {
            @Override
            public void success(BaseResponseEntity<ReqMQTbean> reqMQTbeanBaseResponseEntity) {

                String res = GsonUtils.getGson().toJson(reqMQTbeanBaseResponseEntity.ReturnMessage);
                if (res != null && res.toLowerCase() == "true") {
                    PreferencesUtils.putString(LogonActivity.this, ShareLocalUser.SHARE_KEY_PUSH_CLIENT_ID, res);
                }
                LogUtils.e("推送：" + res);
//                return res;
            }

            @Override
            public void error(int code, String error) {

            }
        }, pushserver, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity<ReqMQTbean>>() {
        });
    }

    /**
     * 发送注册客户信息请求
     *
     * @param url
     * @param
     * @return
     */
    public String modifyPushUser(Map map,final OnRequestLinstener<BaseResponseEntity<ReqMQTbean>> requestLinstener,String url,int what,RequestMethod method,TypeToken typeToken) {

//            String reqBody = GsonUtils.getGson().toJson(reqBean);
        Request<BaseResponseEntity<ReqMQTbean>> request = new FastJsonRequest<BaseResponseEntity<ReqMQTbean>>(url, method, typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<ReqMQTbean>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<ReqMQTbean>> response) {
                requestLinstener.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });

        return null;

    }

}
