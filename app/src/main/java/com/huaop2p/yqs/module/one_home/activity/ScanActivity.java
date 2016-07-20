package com.huaop2p.yqs.module.one_home.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.one_home.Module.android.BeepManager;
import com.huaop2p.yqs.module.one_home.Module.android.InactivityTimer;

/**
 * Created by maoxiaofei on 2016/4/26.
 */
public class ScanActivity extends BaseActivity {

    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_scan);
        SetActivityTitle("扫描二维码");

    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);

    }
}
