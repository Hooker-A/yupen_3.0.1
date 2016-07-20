package com.huaop2p.yqs.module.base.view;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


import com.huaop2p.yqs.module.base.bases.ScreenListener;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.logon.activity.LockValidatorActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;

import java.util.List;

/**
 * fragment 基类
 * Created by yindongli on 2015/4/24.
 */
public abstract class BaseFragment extends Fragment {

    ScreenListener screenListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewUtils.inject(this, view);
        screenListener = new ScreenListener(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!AppApplication.isActive) {
            AppApplication.isActive = true;
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

    @Override
    public void onStop() {
        super.onStop();


        if (!isAppOnForeground()) {
            //app 进入后台

//            全局变量isActive = false 记录当前已经进入后台
            AppApplication.isActive = false;
            try {
                if (screenListener != null)
                    screenListener.unregisterListener();
            } catch (IllegalArgumentException e) {
                LogUtils.e("解除监听错误");
            }

        }

    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */

    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getActivity().getPackageName();

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

    void validateGsPass() {
        //开启手势密码
        if (ShareLocalUser.getInstance(getActivity()).getIsOpenGsPass()) {
            String gspass = ShareLocalUser.getInstance(getActivity()).getGsPass();
            if (gspass != null && gspass.length() > 0) {
                Intent intent = new Intent(getActivity(), LockValidatorActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(screenListener!=null){
            try {
                screenListener.unregisterListener();
            }catch (Exception e){

            }

        }
    }

    protected abstract boolean onTouchEvent(MotionEvent event);


}
