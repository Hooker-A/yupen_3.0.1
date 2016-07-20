package com.huaop2p.yqs.utils.auto;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.huaop2p.yqs.utils.ObjectUtils;

import java.util.List;

/**
 * Created by yindongli on 2014/12/18.
 */
public class AppUtils {
    private AppUtils() {
        throw new AssertionError();
    }

    /**
     * whether this process is named with processName
     *
     * @param context
     * @param processName
     * @return <ul>return whether this process is named with processName
     * <li>if context is null,return false</li>
     * <li>if {@link android.app.ActivityManager#getRunningAppProcesses()} is null,return false</li>
     * <li>if one process of{@link android.app.ActivityManager#getRunningAppProcesses()} is equal to processName
     * return true,otherwise return false
     * </li>
     * </ul>
     */
    public static boolean isNamedProcess(Context context, String processName) {
        if (context == null)
            return false;
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfoList = manager.getRunningAppProcesses();
        if (processInfoList == null)
            return true;
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid && ObjectUtils.isEquals(processName, processInfo.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * whether application is in background
     * <ul>
     * <li>need use permission android.permission.GET_TASKS in Manifest.xml</li>
     * </ul>
     *
     * @param context
     * @return if application is in background return true,otherwise return false
     */
    public static boolean isApplicationInBackGround(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasklist = am.getRunningTasks(1);
        if (tasklist != null && !tasklist.isEmpty()) {
            ComponentName topActivity = tasklist.get(0).topActivity;
            if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName()))
                return true;
        }
        return false;
    }

    /**
     * 获取 app版本
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getVersionName(Context context) throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

    public static int getVersionCode(Context context) throws Exception {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        return packInfo.versionCode;
    }
}
