package com.huaop2p.yqs.module.base.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.utils.auto.AutoUtils;

/**
 * Created by Administrator on 2016/4/11.
 */
public abstract class BaseAutoFragment<T> extends BaseFragment implements IBaseView<T> {
    protected View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = initViews(inflater);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        AutoUtils.initLayoutSize((ViewGroup) view,getActivity());
        return view;
    }

    public abstract void showData();

    public abstract View initViews(LayoutInflater inflater);

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            showData();
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        // 通过这两个判断，就可以知道什么时候去加载数据了
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible()) {
            showData(); // 加载数据的方法
        }
    }

    @Override
    public void startLoadData() {

    }

    @Override
    public void loadDataOver() {

    }

    @Override
    public void showSuccess(T t) {

    }

    @Override
    public void showError(int errorCode,String str) {
        switch (errorCode){
            case State.NULL:
                break;
            default:
                ToastUtils.show(AppApplication.mContext, str);
                break;
        }
    }

    @Override
    protected boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
