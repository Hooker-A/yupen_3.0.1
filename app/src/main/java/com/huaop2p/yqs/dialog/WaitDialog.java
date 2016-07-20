package com.huaop2p.yqs.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.utils.auto.AutoUtils;

public class WaitDialog extends AlertDialog {
    private ImageView pull_icon;
    private AnimationDrawable ad;

    public WaitDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
    }

    public WaitDialog(Context context, int theme) {
        super(context, theme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_wait);
        pull_icon = (ImageView) findViewById(R.id.pull_icon);
        AutoUtils.auto(pull_icon);
        ad = (AnimationDrawable) pull_icon.getBackground();
    }

    @Override
    public void onStart() {
        super.onStart();
        ad.start();
    }

    @Override
    public void dismiss() {
        ad.setVisible(true,true);
        ad.stop();
        super.dismiss();
    }

    @Override
    public void onBackPressed() {
    }
}