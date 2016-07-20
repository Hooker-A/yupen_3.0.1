package com.huaop2p.yqs.module.three_mine.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.three_mine.adapter.DetaAdapter;
import com.huaop2p.yqs.module.three_mine.model.entity.DetaBean;
import com.huaop2p.yqs.module.three_mine.model.impl.MyDataHttp;
import com.huaop2p.yqs.utils.DateUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshBase;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshListView;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgq on 2016/5/27.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/27 10:04
 * 功能:  收入明细
 */
public class DetaActivity extends BaseActivity {
    private PullToRefreshListView mListView;
    private DetaAdapter detaAdapter;
    private List<DetaBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deta);
        SetActivityTitle("收支明细");
        initData();
        httpData();
        liner();
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Collections.sort(list, new Comparator<DetaBean>() {
                @Override
                public int compare(DetaBean lhs, DetaBean rhs) {
                    Date date1 = DateUtils.stringToDate(lhs.Time);
                    Date date2 = DateUtils.stringToDate(rhs.Time);
                    if (date1.before(date2)) {
                        return 1;
                    }
                    return -1;
                }
            });

            detaAdapter = new DetaAdapter(DetaActivity.this, list2);
            mListView.setAdapter(detaAdapter);
            return false;
        }
    });

    private void liner() {
        mListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                httpData();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                httpData();

            }
        });
    }

    private List<DetaBean> list2;

    /**
     * 网络请求
     */
    private void httpData() {
        Map map = new HashMap();
        MyDataHttp.getInstance().GetDetail(map, new OnRequestLinstener<BaseResponseEntity<List<List<DetaBean>>>>() {
            @Override
            public void success(BaseResponseEntity<List<List<DetaBean>>> listBaseResponseEntity) {
                if (listBaseResponseEntity.ReturnStatus.equals("0")){
                    mListView.stopLoadOrRefresh();
                    DetaBean bean = new DetaBean();
                    list2 = new ArrayList();
                    for (int j = 0; j < listBaseResponseEntity.ReturnMessage.size(); j++) {
                        String key = null;
                        for (int i = 0; i < listBaseResponseEntity.ReturnMessage.get(j).size(); i++) {
                            DetaBean detaBean = listBaseResponseEntity.ReturnMessage.get(j).get(i);

                            if (key == null) {
                                key = detaBean.Time;
                                detaBean.title = key;
                            }
                            list2.add(detaBean);
                            LogUtils.e(list.size() + "");
                            handler.sendEmptyMessage(0);
                        }
                    }
                }


            }


            @Override
            public void error(int code, String error) {
                LogUtils.e(error + "768120757");

            }
        }, HttpUrl.GetDetail, 0, RequestMethod.GET, new TypeToken<BaseResponseEntity<List<List<DetaBean>>>>() {
        });
    }

    /**
     * 控件赋值初始化
     */
    private void initData() {
        mListView = (PullToRefreshListView) findViewById(R.id.mListView);
    }


}
