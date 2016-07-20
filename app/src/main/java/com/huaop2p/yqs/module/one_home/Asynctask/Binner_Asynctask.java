package com.huaop2p.yqs.module.one_home.Asynctask;

import android.os.AsyncTask;

import com.huaop2p.yqs.module.one_home.bean.BinnerListBean;

/**
 * Created by maoxiaofei on 2016/4/14.
 */
public class Binner_Asynctask extends AsyncTask<String,Integer,BinnerListBean>{


    @Override
    protected BinnerListBean doInBackground(String... params) {
//        BinnerListBean baseResponseEntity = Web.getInstance().getBinnerList(params[0]);
//        LogUtils.e(baseResponseEntity.toString());
        return null;
    }

    @Override
    protected void onPostExecute(BinnerListBean listBaseResponseEntity) {
        super.onPostExecute(listBaseResponseEntity);
    }
}
