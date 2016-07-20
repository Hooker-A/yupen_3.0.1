package com.huaop2p.yqs.dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by zgq on 2016/6/23.
 * 作者:  zhu  guo qing
 * 时间:  2016/6/23 10:39
 * 功能: 显示关闭dialog
 */
public class ProgressDialogUtils {

    public static ProgressDialog mProgressDialog;

    /**
     * 显示ProgressDialog
     *
     * @param context
     * @param message
     */
    public static void showProgressDialog(Context context, CharSequence message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
            mProgressDialog.setTitle("");
//            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        } else {
//            mProgressDialog.show();
        }
    }

    /**
     * 关闭ProgressDialog
     */
    public static void dismissProgressDialog() {

        try{
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        }catch (Exception e){

        }
    }

    public static boolean isShowing() {
        if (mProgressDialog != null)
            return mProgressDialog.isShowing();
        return false;
    }
}
