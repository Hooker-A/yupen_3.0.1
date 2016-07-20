package com.huaop2p.yqs.module.logon.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.InvestStepCache;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.widget.LockPatternView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgq on 2016/5/11.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/11 11:14
 * 功能:  设置手势密码
 */
public class LockGSModifyActivity extends BaseActivity implements LockPatternView.OnPatternListener{

    private static final String TAG = "LockSetupActivity";
    private LockPatternView lockPatternView;
    private static final int STEP_1 = 1;//开始
    private static final int STEP_2 = 2;//第一次设置手势完成
    private static final int STEP_3 = 3;//按下继续按钮
    private static final int STEP_4 = 4;//第二次设置完成

    private int step;
    private List<LockPatternView.Cell> choosePattern;
    private boolean confirm = false, passOldPass = false/*通过原始密码验证*/;
    TextView txt_forgetpass, txt_gesture_pass_prompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockgsm);
        lockPatternView = (LockPatternView) findViewById(R.id.lock_pattern);
        lockPatternView.setOnPatternListener(this);
        txt_gesture_pass_prompt = (TextView) findViewById(R.id.txt_gesture_pass_prompt);
        txt_forgetpass = (TextView) findViewById(R.id.txt_forgetpass);
        txt_forgetpass.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        txt_forgetpass.setText(getResources().getString(R.string.str_forg_gs_pass));
        txt_forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent startModifyGsPass = new Intent(LockGSModifyActivity.this, LogonActivity.class);
                InvestStepCache.login_for = InvestStepCache.login_for_modify_gs_password;
                startActivity(startModifyGsPass);
            }
        });
        SetActivityTitle("修改手势密码");
    }


    private void updateView() {
        switch (step) {
            case STEP_1:
                choosePattern = null;
                confirm = false;
                lockPatternView.clearPattern();
                lockPatternView.enableInput();
                break;
            case STEP_2://比较 旧密码
                String oldPass = ShareLocalUser.getInstance(getApplicationContext()).getGsPass();
                String newpass = LockPatternView.patternToString(choosePattern);
                choosePattern = null;
                lockPatternView.clearPattern();
                lockPatternView.enableInput();
                if (oldPass.equals(newpass)) {
                    txt_gesture_pass_prompt.setText(R.string.str_input_new_gs_pass);
                    passOldPass = true;
                } else {
                    txt_gesture_pass_prompt.setText("原始密码不正确");
                    passOldPass = false;
                }
                break;
            case STEP_3:
                step = STEP_3;
                lockPatternView.clearPattern();
                lockPatternView.enableInput();
                break;
            case STEP_4:
                ShareLocalUser.getInstance(getApplicationContext()).addGsPass(LockPatternView.patternToString(choosePattern));
                txt_gesture_pass_prompt.setText(R.string.str_getsture_seccuss);//手势密码设置成功
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onPatternStart() {
    }

    @Override
    public void onPatternCleared() {
    }

    @Override
    public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {
    }

    /**
     * 获取 本地密码
     *
     * @return
     */
    public List<LockPatternView.Cell> getOlderGsPass() {
        String patternString = ShareLocalUser.getInstance(getApplicationContext()).getGsPass();
        if (patternString == null) {
            return null;
        }
        return LockPatternView.stringToPattern(patternString);
    }

    /**
     * 比较两个手势密码
     *
     * @param one
     * @param two
     * @return
     */
    public boolean compare2Password(List<LockPatternView.Cell> one, List<LockPatternView.Cell> two) {
        try {
            String oneStr = LockPatternView.patternToString(one);
            String twoStr = LockPatternView.patternToString(two);
            return oneStr.equals(twoStr);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onPatternDetected(List<LockPatternView.Cell> pattern) {

        if (pattern.size() < LockPatternView.MIN_LOCK_PATTERN_SIZE) {
            txt_gesture_pass_prompt.setText(R.string.lockpattern_recording_incorrect_too_short);
            lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            return;
        }

        if (choosePattern == null) {
            choosePattern = new ArrayList<LockPatternView.Cell>(pattern);

            step = STEP_2;

            if (passOldPass) {
                step = STEP_3;
                txt_gesture_pass_prompt.setText(R.string.str_second_gesture);
            } else {
                step = STEP_2;
                txt_gesture_pass_prompt.setText(R.string.str_input_new_gs_pass);
            }
            updateView();
            return;
        }

        if (choosePattern.equals(pattern)) {
            confirm = true;
        } else {
            confirm = false;
            txt_gesture_pass_prompt.setText(R.string.str_input_pass_not_sure);
            lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            return;
        }

        step = STEP_4;
        updateView();
    }

}
