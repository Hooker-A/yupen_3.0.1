package com.huaop2p.yqs.module.three_mine.activity.dataactivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.WaitDialog;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.view.ClearEditText;
import com.huaop2p.yqs.module.logon.entity.LoginedBean;
import com.huaop2p.yqs.module.three_mine.model.impl.MyDataHttp;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgq on 2016/5/9.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/9 16:30
 * 功能: 邮箱
 */
public class E_mailActivity extends BaseActivity implements View.OnClickListener{
    private ClearEditText txt_emil;
    private Button imgOk;
    private WaitDialog waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        initData();
        if (ShareLocalUser.getInstance(this).getLoginState()){
            String nnme=ShareLocalUser.getInstance(this).getLoginUser().Email;
            if (nnme!=null){
                txt_emil.setText(nnme);

            }
        }

    }

    private void initData() {
        txt_emil= (ClearEditText) findViewById(R.id.txt_emil);
        imgOk= (Button) findViewById(R.id.imgOk);
        imgOk.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ShareLocalUser.getInstance(this).getLoginState()){
            String nnme=ShareLocalUser.getInstance(this).getLoginUser().Email;
            if (nnme!=null){
                txt_emil.setText(nnme);
            }
        }
    }

    @Override
    public void onClick(View v) {
        String emss=txt_emil.getText().toString();
        /**
         * ：{"Type":"EditEmail","Email":"新邮箱"}
         */
        switch (v.getId()){
            case R.id.imgOk:
                    waitDialog=new WaitDialog(E_mailActivity.this);
                    waitDialog.show();
                    httpData();
                break;
        }
    }

    private void httpData() {
        Map mMap=new HashMap();
        String emil=txt_emil.getText().toString();
        mMap.put("Type","EditEmail");
        mMap.put("Email", emil);
        MyDataHttp.getInstance().PostPersonInfos(mMap, new OnRequestLinstener<BaseResponseEntity>() {
            @Override
            public void success(BaseResponseEntity baseResponseEntity) {
                if (baseResponseEntity.ReturnStatus.equals("0")){
                    LoginedBean bean = ShareLocalUser.getInstance(E_mailActivity.this).getLoginUser();
                    bean.Email = baseResponseEntity.ReturnMessage + "";
                    ShareLocalUser.getInstance(E_mailActivity.this).addLoginUser(bean);
                    txt_emil.setText(bean.Sex);
                    waitDialog.dismiss();
                    finish();
                }else {
                    Toast toast=Toast.makeText(getApplicationContext(), baseResponseEntity.ReturnReason, Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.show();
                    txt_emil.setText("");
                    waitDialog.dismiss();
                }


            }

            @Override
            public void error(int code, String error) {
                LogUtils.e(code + "768120");
            }
        }, HttpUrl.PostPersonInfos, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity>() {
        });
    }
}
