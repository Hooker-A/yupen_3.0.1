package com.huaop2p.yqs.module.base.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.huaop2p.yqs.module.base.bases.ScreenListener;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.logon.activity.LockValidatorActivity;
import com.huaop2p.yqs.module.two_wealth.model.entity.EventBusEntity;
import com.lidroid.xutils.util.LogUtils;
import com.huaop2p.yqs.R;

import java.util.List;
import java.util.Map;

/**
 * Created by yindongli on 2014/12/25.
 */
public class BaseActivity extends NoAniActivity {
    public TextView TXT_APP_TITLE;
    public boolean isStop;
    protected AppApplication application;
    public String SelfTitle;
    public ScreenListener screenListener;

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (AppApplication) getApplication();
        application.addActivity(this);
//        this.setClipToPadding(true);
//        this.setFitsSystemWindows(true);
//        ActionBar actionBar = getActionBar();
//        if (actionBar != null)
//            actionBar.hide();
//        mScreenReceiver = new ScreenBroadcastReceiver();
//        registerScreen();
        screenListener = new ScreenListener(this);
    }

    public int titlewidth = 0;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

    }

    void validateGsPass() {
        //开启手势密码
        if (ShareLocalUser.getInstance(this).getIsOpenGsPass()) {
            String gspass = ShareLocalUser.getInstance(getApplicationContext()).getGsPass();
            if (gspass != null && gspass.length() > 0) {
                Intent intent = new Intent(this, LockValidatorActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    protected void SetActivityTitle(String title) {
        TXT_APP_TITLE = (TextView) findViewById(R.id.TXT_APP_TITLE);
        if (TXT_APP_TITLE != null)
            TXT_APP_TITLE.setText(title);

        if (title.length() > 10)
            TXT_APP_TITLE.setTextSize(16);
    }

    /**
     * 隐藏返回按钮
     */
    protected void hideBackImg() {
        ImageButton imgBack = (ImageButton) findViewById(R.id.imgBack);
        if (imgBack != null)
            imgBack.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        INSTANCE = null;
        if (screenListener != null) {
            try {
                screenListener.unregisterListener();
            } catch (IllegalArgumentException e) {

            }

        }
    }

    public void finish() {
        super.finish();
        application.removeActivity(this);
//        overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
    }

    public void returnBack(View view) {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();

        isStop = true;
        if (!isAppOnForeground()) {
            //app 进入后台

//            全局变量isActive = false 记录当前已经进入后台
            AppApplication.isActive = false;
            try {
                if (screenListener != null)
                    screenListener.unregisterListener();
            } catch (Exception e) {
                LogUtils.e("解除监听错误");
            }

        }
//        LogUtils.e(AppApplication.isActive + "");
    }

    public void onEventMainThread(EventBusEntity<Map<String, String>> mapEventBusEntity) {
        if (mapEventBusEntity.type == 2) {
            Map<String, String> map = mapEventBusEntity.objecy;
            String type = map.get("InfoType");
            boolean flag = false;
            if (type.equals("1")) {
                flag = true;
            } else if (type.equals("2")) {
                flag = false;
            }
            if (!type.equals("0")) {
                Intent intent = new Intent(this, ErrorActivity.class);
                intent.putExtra("url", map.get("Url"));
                intent.putExtra("flag", flag);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        isStop = false;
        if (!AppApplication.isActive) {
            //app 从后台唤醒，进入前台


            AppApplication.isActive = true;
//            validateGsPass();
            screenListener.begin(new ScreenListener.ScreenStateListener() {
                @Override
                public void onScreenOn() {
                    validateGsPass();
                }

                @Override
                public void onScreenOff() {

                }

                @Override
                public void onUserPresent() {

                }
            });
        }
    }


    /**
     * 程序是否在前台运行
     *
     * @return
     */

    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }

        }
        return false;
    }


}
