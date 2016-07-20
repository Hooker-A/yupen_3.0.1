package com.huaop2p.yqs.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.CustomDialog;
import com.huaop2p.yqs.dialog.ShowDialog;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.one_home.entity.CustomToastDialog;

/**
 * Created by zgq on 2016/4/15.
 * 作者:  zhu  guo qing
 * 时间:  2016/4/15 17:10
 * 功能:
 */
public class AppInentUtils {

    /**
     * 拨打电话
     *
     * @param context
     */
    public static void openCallPhone(final Context context, String prompt, final String phoneNumber) {
        if (context == null)
            return;
        final CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setTitle(R.string.str_prompt);
        builder.setMessage(prompt + "\n" + phoneNumber);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String phonenum = phoneNumber.replaceAll("-", "");
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonenum));
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        builder.create(false).show();
    }

    /**
     * 直接拨打电话
     * @param context
     * @param phonenum
     */
    public static void callPhone(Context context, String phonenum) {
        if(phonenum!=null){
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonenum));
            context.startActivity(intent);
        }else{
            ToastUtils.show(context, "请拨打正确的电话号码！");
        }

    }
    /**
     * 取消设置手势面膜
     *
     * @param activity
     */
    public static void openGsPassNull(final Activity activity) {
        if (activity == null)
            return;
        final ShowDialog.Builder builder = new ShowDialog.Builder(activity);
        builder.setTitle(R.string.str_prompt);
        builder.setMessage(R.string.str_gs_pass_null);
        builder.setPositiveButton(R.string.str_cancel_set_gs_pass, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                activity.finish();//放弃
                ShareLocalUser.getInstance(activity).addGsPass(null);
                ShareLocalUser.getInstance(activity).addIsOpenGsPass(false);
            }
        });
        builder.setNegativeButton(R.string.str_reset_gs_pass, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
//重新设置
                dialog.dismiss();
            }

        });
        builder.create(false).show();
    }

    public static void openCusManagerIsLose(final Activity activity, String msg) {
        if (activity == null)
            return;

        final CustomToastDialog.Builder builder = new CustomToastDialog.Builder(activity);
        builder.setTitle(R.string.str_prompt);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
                dialog.dismiss();
            }
        });

        builder.create(false).show();
    }

    /**
     * 通过Dialog 代替Toast
     *
     * @param context
     * @param msg
     */
    public static void DialogToast(final Context context, String msg) {
        if (context == null)
            return;
        if (msg.equals(context.getString(R.string.str_net_time_long))) {
            final CustomToastDialog.Builder builder = new CustomToastDialog.Builder(context);
            builder.setTitle(R.string.str_prompt);
            builder.setMessage(msg);
            builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
//                MainActivity.instance.finish();//关闭Main 这个Activity
//                AppApplication.getInstance(context).exit();
                    dialog.dismiss();
                }
            });

            builder.create(false).show();
        } else {
            ToastUtils.show(context, msg);
        }


    }


}
