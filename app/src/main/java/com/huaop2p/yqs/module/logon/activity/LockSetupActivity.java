package com.huaop2p.yqs.module.logon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.utils.AppInentUtils;
import com.huaop2p.yqs.utils.LockPatternUtils;
import com.huaop2p.yqs.widget.LockPatternView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgq on 2016/5/6.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/6 14:48
 * 功能:  绘制手势密码
 */
public class LockSetupActivity extends BaseActivity implements LockPatternView.OnPatternListener{

    private static final String TAG = "LockSetupActivity";
    private LockPatternView lockPatternView;
    private static final int STEP_1 = 1;//开始
    private static final int STEP_2 = 2;//第一次设置手势完成
    private static final int STEP_3 = 3;//按下继续按钮
    private static final int STEP_4 = 4;//第二次设置完成
    private static final String KEY_UI_STAGE = "uiStage";
    private static final String KEY_PATTERN_CHOICE = "chosenPattern";

    private int step;
    private List<LockPatternView.Cell> choosePattern;
    private boolean confirm = false;
    TextView  txt_gesture_pass_prompt;
    public static  String IS_SETSUCESS="is_sucess";
    private View mPreviewViews[][] = new View[3][3];
    protected List<LockPatternView.Cell> mChosenPattern = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockset);
        SetActivityTitle("设置手势密码");
        initPreviewViews();
    }
    private void initPreviewViews() {
        mPreviewViews = new View[3][3];
        mPreviewViews[0][0] = findViewById(R.id.gesturepwd_setting_preview_0);
        mPreviewViews[0][1] = findViewById(R.id.gesturepwd_setting_preview_1);
        mPreviewViews[0][2] = findViewById(R.id.gesturepwd_setting_preview_2);
        mPreviewViews[1][0] = findViewById(R.id.gesturepwd_setting_preview_3);
        mPreviewViews[1][1] = findViewById(R.id.gesturepwd_setting_preview_4);
        mPreviewViews[1][2] = findViewById(R.id.gesturepwd_setting_preview_5);
        mPreviewViews[2][0] = findViewById(R.id.gesturepwd_setting_preview_6);
        mPreviewViews[2][1] = findViewById(R.id.gesturepwd_setting_preview_7);
        mPreviewViews[2][2] = findViewById(R.id.gesturepwd_setting_preview_8);
        lockPatternView = (LockPatternView) findViewById(R.id.gesturepwd_create_lockview);
        lockPatternView.setOnPatternListener(this);
        txt_gesture_pass_prompt = (TextView) findViewById(R.id.gesturepwd_create_text);
    }
    private void updatePreviewViews() {
        if (mChosenPattern == null)
            return;
        Log.i("way", "result = " + mChosenPattern.toString());
        String result=mChosenPattern.toString();
        for (LockPatternView.Cell cell : mChosenPattern) {
            Log.i("way", "cell.getRow() = " + cell.getRow()
                    + ", cell.getColumn() = " + cell.getColumn());
            mPreviewViews[cell.getRow()][cell.getColumn()]
                    .setBackgroundResource(R.drawable.dian);

        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putInt(KEY_UI_STAGE, mUiStage.ordinal());
        if (mChosenPattern != null) {
            outState.putString(KEY_PATTERN_CHOICE,
                    LockPatternUtils.patternToString(mChosenPattern));
        }
    }

    @Override
    public void returnBack(View view) {
        String gspass = ShareLocalUser.getInstance(LockSetupActivity.this).getGsPass();
        if (gspass == null || gspass.length() < 4) {
            AppInentUtils.openGsPassNull(LockSetupActivity.this);
        }else{
            finish();
        }
//        super.returnBack(view);
    }

    private void updateView() {
        switch (step) {
            case STEP_1:
                choosePattern = null;
                confirm = false;
                lockPatternView.clearPattern();
                lockPatternView.enableInput();
                break;
            case STEP_2:
                lockPatternView.clearPattern();
                lockPatternView.enableInput();
                break;
            case STEP_3:

                break;
            case STEP_4:
                ShareLocalUser.getInstance(getApplicationContext()).addGsPass(LockPatternView.patternToString(choosePattern));
                txt_gesture_pass_prompt.setText(R.string.str_getsture_seccuss);//手势密码设置成功
                Intent intent=new Intent();
                intent.putExtra(IS_SETSUCESS,true);
                setResult(RESULT_OK,intent);
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
        Log.d(TAG, "onPatternStart");
    }

    @Override
    public void onPatternCleared() {
        Log.d(TAG, "onPatternCleared");
    }

    @Override
    public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {
        Log.d(TAG, "onPatternCellAdded");
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

    @Override
    public void onPatternDetected(List<LockPatternView.Cell> pattern) {

        if (pattern.size() < LockPatternView.MIN_LOCK_PATTERN_SIZE) {
            txt_gesture_pass_prompt.setText(R.string.lockpattern_recording_incorrect_too_short);
            lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            return;
        }

        if (choosePattern == null) {
            choosePattern = new ArrayList<LockPatternView.Cell>(pattern);
            txt_gesture_pass_prompt.setText(R.string.str_second_gesture);
            step = STEP_2;
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
