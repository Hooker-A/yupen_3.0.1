package com.huaop2p.yqs.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.huaop2p.yqs.R;

/**
 * Created by zgq on 2016/5/4.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/4 11:08
 * 功能:  头像选择dig
 */
public class ShowIOSDialog {

    public static void showDialog(Activity activity, boolean isDele, final IntakePic intakePic) {


        View view = activity.getLayoutInflater().inflate(R.layout.demo_dialog_menu_ios, null);
        final Dialog dialog = new Dialog(activity, R.style.transparentFrameWindowStyle);

        /**
         * 处理 按钮事件
         */
        Button btn = (Button) view.findViewById(R.id.btn_cancel);
        Button btn_photo = (Button) view.findViewById(R.id.btn_photo);
        Button btn_pt_data = (Button) view.findViewById(R.id.btn_pt_data);
        if (isDele) {

        } else {
            btn_photo.setText("重新拍摄");
            btn_pt_data.setText("重新选择");
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intakePic.onFinish();
                dialog.dismiss();

            }
        });
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intakePic.onTakePic();
                dialog.dismiss();
            }
        });
        btn_pt_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intakePic.onGellayClick();
                dialog.dismiss();
            }
        });


        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.dialog_qq_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    /**
     * onTakePic  拍照回调
     * onGellayClick  图库回调
     */
    public interface IntakePic {

        /**
         * 拍照回调
         */
        void onTakePic();

        /**
         * 图册回调
         */
        void onGellayClick();

        /**
         *
         */
        void onFinish();

    }
}
