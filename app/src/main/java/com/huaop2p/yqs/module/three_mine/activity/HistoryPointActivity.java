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
import com.huaop2p.yqs.module.three_mine.adapter.HistoryPointAdater;
import com.huaop2p.yqs.module.three_mine.model.entity.ThirtyDayPointBean;
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
 * Created by maoxiaofei on 2016/6/27.
 */
public class HistoryPointActivity extends BaseActivity {

    private PullToRefreshListView mListView;
    private HistoryPointAdater detaAdapter;
    private List<ThirtyDayPointBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deta);
        SetActivityTitle("更多积分纪录");
        initData();
        httpData();
        liner();
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Collections.sort(list, new Comparator<ThirtyDayPointBean>() {
                @Override
                public int compare(ThirtyDayPointBean lhs, ThirtyDayPointBean rhs) {
                    Date date1 = DateUtils.stringToDate(lhs.getCreateTime());
                    Date date2 = DateUtils.stringToDate(rhs.getCreateTime());
                    if (date1.before(date2)) {
                        return 1;
                    }
                    return -1;
                }
            });

            detaAdapter = new HistoryPointAdater(HistoryPointActivity.this, list2);
            mListView.setAdapter(detaAdapter);
            return false;
        }
    });


    private void liner() {
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
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
    private List<ThirtyDayPointBean> list2;
    /**
     * 网络请求
     */
    private void httpData() {
        Map map = new HashMap();
        MyDataHttp.getInstance().GetHistorypoint(map, new OnRequestLinstener<BaseResponseEntity<List<List<ThirtyDayPointBean>>>>() {
            @Override
            public void success(BaseResponseEntity<List<List<ThirtyDayPointBean>>> listBaseResponseEntity) {
                list2 = new ArrayList();
                for (int j = 0; j < listBaseResponseEntity.ReturnMessage.size(); j++) {
                    String key = null;
                    for (int i = 0; i < listBaseResponseEntity.ReturnMessage.get(j).size(); i++) {
                        ThirtyDayPointBean detaBean = listBaseResponseEntity.ReturnMessage.get(j).get(i);

                        if (key == null) {
                            key = detaBean.getCreateTime();
                            detaBean.title = key;
                        }
                        list2.add(detaBean);
                        LogUtils.e(list.size() + "");
                        handler.sendEmptyMessage(0);
                    }
                }

            }


            @Override
            public void error(int code, String error) {
                LogUtils.e(error + "768120757");

            }
        }, HttpUrl.GET_USERHISTORYPOINTS, 0, RequestMethod.GET, new TypeToken<BaseResponseEntity<List<List<ThirtyDayPointBean>>>>() {
        });
    }

   /**
      控件赋值初始化
    */
    private void initData() {
        mListView = (PullToRefreshListView) findViewById(R.id.mListView);
    }

}
