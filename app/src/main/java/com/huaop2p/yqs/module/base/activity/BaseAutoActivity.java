package com.huaop2p.yqs.module.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.WaitDialog;
import com.huaop2p.yqs.module.base.view.IBaseView;
import com.huaop2p.yqs.module.four_set.activity.ShareActivity;
import com.huaop2p.yqs.module.two_wealth.model.entity.EventBusEntity;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.utils.auto.AutoUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/4/11.
 */
public abstract class BaseAutoActivity<T> extends BaseActivity implements IBaseView<T> {
    private WaitDialog waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup view = (ViewGroup) getLayoutInflater().inflate(getLayoutId(), null);
//        view.setClipToPadding(true);
//        view.setFitsSystemWindows(true);
        AutoUtils.initLayoutSize(view, this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//        }
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.white);
        setContentView(view);
        initViews();
        initData();

    }

    @Override
    protected void onStart() {
        super.onStart();
       if (!EventBus.getDefault().isRegistered(getObject()))
           EventBus.getDefault().registerSticky(getObject());
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().removeStickyEvent(EventBusEntity.class);
        EventBus.getDefault().unregister(getObject());
    }

    public abstract Object getObject();

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public abstract int getLayoutId();

    public abstract void initViews();

    public abstract void initData();

    @Override
    public void loadDataOver() {
        waitDialog.dismiss();

    }


    @Override
    public void showError(int errorCode, String str) {
        ToastUtils.show(this, str);
    }

    @Override
    public void showSuccess(T t) {

    }

    @Override
    public void startLoadData() {
        if (waitDialog == null) {
            waitDialog = new WaitDialog(this, R.style.aa);
        }
        waitDialog.show();
    }

    public void back(View view) {
        this.finish();
    }

    public void share(View view) {
        Intent intentZF = new Intent(this, ShareActivity.class);
        startActivity(intentZF);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

//    public void onEventMainThread(EventBusEntity<Map<String, String>> mapEventBusEntity) {
//        if (mapEventBusEntity.type == 2) {
//            Map<String, String> map = mapEventBusEntity.objecy;
//            String type = map.get("InfoType");
//            boolean flag = false;
//            if (type.equals("2")) {
//                flag = true;
//            } else if (type.equals("3")) {
//                flag = false;
//            }
//            if (!type.equals("1")) {
//                Intent intent=new Intent(this,ErrorActivity.class);
//                intent.putExtra("url",map.get("Url"));
//                intent.putExtra("flag",flag);
//                startActivity(intent);
//            }
//        }
//    }

}
