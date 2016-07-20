package com.huaop2p.yqs.module.four_set.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.logon.entity.LoginedBean;
import com.huaop2p.yqs.module.one_home.activity.CaptureActivity;

/**
 * Created by zgq on 2016/5/25.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/25 11:39
 * 功能:  我的客户经理
 */
public class CustomerManagerNoneActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custnone);
        SetActivityTitle("我的客户经理");
    }
    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoginedBean loginUser = ShareLocalUser.getInstance(this).getLoginUser();
        if (loginUser != null && loginUser.CustomerManagerID != null && loginUser.CustomerManagerID.length() > 0) {
            finish();
            Intent haveManger = new Intent(this, CaptureActivity.class);
            startActivity(haveManger);
        }
    }

    /**
     * 申请客服经理
     * 扫描二维码
     *
     * @param view
     */
    public void requestCustomerManager(View view) {
        Intent startTwoDesc = new Intent(this, CaptureActivity.class);
        startTwoDesc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(startTwoDesc);
    }
}
