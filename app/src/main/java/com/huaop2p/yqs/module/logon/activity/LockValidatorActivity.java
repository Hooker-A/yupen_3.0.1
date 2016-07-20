package com.huaop2p.yqs.module.logon.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.widget.LockPatternView;
import com.huaop2p.yqs.widget.SwitchManager;
import com.lidroid.xutils.util.LogUtils;

import java.util.List;

/**
 * Created by zgq on 2016/5/11.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/11 11:11
 * 功能:  使用手势密码登录
 */
public class LockValidatorActivity extends BaseActivity implements LockPatternView.OnPatternListener{
    public static LockValidatorActivity instance;
    public static final String TAG = "LockActivity";
    public List<LockPatternView.Cell> lockPattern;
    public LockPatternView lockPatternView;
    TextView txt_error_prompt;
    public int step;
    public int errores;
    public static int maxError = 3;

    public static String IS_MAIN_GS_STR = "IS_MAIN_GS_STR";
    public static boolean is_main_gs = false;
    String is_main_gs_str = "";
    Button forgetpass;
    public static int FORGGETPASS = 1254156;
    public static String FORGGETPASS_TAG = "forget_result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_lockva);
        SetActivityTitle("手势密码登录");

        is_main_gs_str = getIntent().getStringExtra(IS_MAIN_GS_STR);
        if (is_main_gs_str != null && is_main_gs_str.equals(IS_MAIN_GS_STR))
            is_main_gs = true;
        txt_error_prompt = (TextView) findViewById(R.id.txt_error_prompt);
        forgetpass = (Button) findViewById(R.id.txt_forgetpass);
        forgetpass.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LockValidatorActivity.this, LogonActivity.class);
                intent.putExtra(FORGGETPASS_TAG,true);
                startActivity(intent);
            }
        });

        String patternString = ShareLocalUser.getInstance(getApplicationContext()).getGsPass();
        if (patternString == null) {
            finish();
            return;
        }
        lockPattern = LockPatternView.stringToPattern(patternString);
        lockPatternView = (LockPatternView) findViewById(R.id.lock_pattern);
        lockPatternView.setOnPatternListener(this);
        if (SwitchManager.isMyAccount) {   //如果是我的账户的开关密码
            SetActivityTitle("请输入手势密码");
        } else {
            hideBackImg();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onPatternStart() {
        Log.d(TAG, "onPatternStart");
    }

    @Override
    public void onPatternCleared() {
        Log.d(TAG, "onPatternCleared");
    }

    @Override
    public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {
        LogUtils.d("onPatternCellAdded");
    }

    @Override
    public void onPatternDetected(List<LockPatternView.Cell> pattern) {
        if (pattern.equals(lockPattern)) {

            if (SwitchManager.isMyAccount) {
                ShareLocalUser.getInstance(getApplicationContext()).addIsOpenGsPass(false);
                SwitchManager.mSlidSwitch.setStatus(false);
            }
            finish();
        } else {
            lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            txt_error_prompt.setText("手势密码不正确");
            lockPatternView.clearPattern();
            errores++;
            if (errores >= maxError) {
                forgetpass.setVisibility(View.VISIBLE);
            }
            if (SwitchManager.isMyAccount) {
                SwitchManager.mSlidSwitch.setStatus(true);
                ShareLocalUser.getInstance(getApplicationContext()).addIsOpenGsPass(true);
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void finish() {
        super.finish();
        instance = null;
        SwitchManager.isMyAccount = false;
        if (is_main_gs_str != null && is_main_gs_str.equals(IS_MAIN_GS_STR))
            is_main_gs = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!ShareLocalUser.getInstance(this).getIsOpenGsPass())
            finish();
    }
}
