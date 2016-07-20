package com.huaop2p.yqs.module.two_wealth.view;

import android.widget.ProgressBar;

/**
 * Created by Administrator on 2016/5/4.
 * 作者：任洋
 * 功能：财富详细的接口
 */
public interface IWealthDetailView<T>  {
    /**
     * 进度条赋值
     * **/
    public void setProgress(ProgressBar progressBar,int pro);

    public  void loadH5(String url);
}
