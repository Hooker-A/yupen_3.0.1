package com.huaop2p.yqs.module.three_mine.activity.dataactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
 * 时间:  2016/5/9 14:29
 * 功能:  昵称
 */
public class NickActivity extends BaseActivity implements View.OnClickListener{
    private Button imgOk;
    private ClearEditText txt_nick;
    private String nickName;
    private WaitDialog waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick);

        initData();
        if (ShareLocalUser.getInstance(this).getLoginState()){
            String nnme=ShareLocalUser.getInstance(this).getLoginUser().Nickname;
            if (nnme!=null){
                txt_nick.setText(nnme);
            }

        }
    }

    private void initData() {
        imgOk= (Button) findViewById(R.id.imgOk);
        imgOk.setOnClickListener(this);
        txt_nick= (ClearEditText) findViewById(R.id.txt_nick);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ShareLocalUser.getInstance(this).getLoginState()){
            String nnme=ShareLocalUser.getInstance(this).getLoginUser().Nickname;
            txt_nick.setText(nnme);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.imgOk:
                waitDialog=new WaitDialog(NickActivity.this);
                waitDialog.show();
                PostHttp();
                String nnme=ShareLocalUser.getInstance(this).getLoginUser().Nickname;
                break;
        }
    }
    /**
     * ：{"Type":"EditName","NickName":"新昵称"}
     */

    private void PostHttp() {
        Map map=new HashMap();
        nickName=txt_nick.getText().toString();
        map.put("Type","EditName");
        map.put("NickName", nickName);
        MyDataHttp.getInstance().PostPersonInfos(map, new OnRequestLinstener<BaseResponseEntity>() {
            @Override
            public void success(BaseResponseEntity baseResponseEntity) {
//                String name=baseResponseEntity.ReturnMessage+"";
                LoginedBean bean=ShareLocalUser.getInstance(NickActivity.this).getLoginUser();
                bean.Nickname=baseResponseEntity.ReturnMessage+"";
                ShareLocalUser.getInstance(NickActivity.this).addLoginUser(bean);
                txt_nick.setText(bean.Nickname);
                waitDialog.dismiss();
                finish();

            }

            @Override
            public void error(int code, String error) {
                LogUtils.e(code + "768120");
            }
        }, HttpUrl.PostPersonInfos, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity>() {
        });
    }
}
