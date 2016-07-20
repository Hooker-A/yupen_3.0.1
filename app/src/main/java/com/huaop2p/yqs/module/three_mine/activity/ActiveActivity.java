package com.huaop2p.yqs.module.three_mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.three_mine.adapter.ActiveAdapter;
import com.huaop2p.yqs.module.three_mine.model.entity.ActiveBean;
import com.huaop2p.yqs.module.three_mine.model.impl.MyDataHttp;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshBase;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshListView;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgq on 2016/5/31.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/31 11:05
 * 功能: 热门活动
 */
public class ActiveActivity extends BaseActivity implements View.OnClickListener {
    private PullToRefreshListView mListView;
    private List<ActiveBean> list;
    private TextView txt_active;
    private LinearLayout txt_text;
    private ActiveAdapter adapter;
    private String tilte,url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active);
        initData();
        httpData();
        linData();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ActiveActivity.this, AllActiveActivity.class);
                intent.putExtra(AllActiveActivity.TITLE, tilte);
                intent.putExtra(AllActiveActivity.URL, url);
                startActivity(intent);
            }
        });
        stop();
    }


    /**
     * 下拉刷新
     */
    private void linData(){
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
    private void stop(){
        mListView.stopLoadOrRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListView.stopLoadOrRefresh();
    }

    /**
     * 网络加载
     * {"Type":"0：未过期活动   1：已过期活动"}
     */
    private void httpData() {
        Map map = new HashMap();
        map.put("Type", 0);
        MyDataHttp.getInstance().PostActivitys(map, new OnRequestLinstener<BaseResponseEntity<List<ActiveBean>>>() {
            @Override
            public void success(BaseResponseEntity<List<ActiveBean>> listBaseResponseEntity) {
                if (listBaseResponseEntity.ReturnMessage.size()==0){
                    txt_text.setVisibility(View.VISIBLE);
                }else {
                    txt_text.setVisibility(View.GONE);
                    for (int i=0;i<listBaseResponseEntity.ReturnMessage.size();i++){
                        tilte=listBaseResponseEntity.ReturnMessage.get(i).Title;
                        url=listBaseResponseEntity.ReturnMessage.get(i).ActivityUrl;

                    }
                    list = listBaseResponseEntity.ReturnMessage;
                    adapter=new ActiveAdapter(ActiveActivity.this,list);
                    mListView.setAdapter(adapter);
                    stop();

                }

//                LogUtils.e(listBaseResponseEntity.ReturnMessage.StartTime+"*************");
            }

            @Override
            public void error(int code, String error) {

            }
        }, HttpUrl.PostActivitys, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity<List<ActiveBean>>>() {
        });
    }

    /**
     * 初始化控件
     */
    private void initData() {
        txt_active = (TextView) findViewById(R.id.txt_active);
        txt_active.setOnClickListener(this);
        mListView = (PullToRefreshListView) findViewById(R.id.mListView);
        txt_text= (LinearLayout) findViewById(R.id.txt_text);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_active:
                Intent intent=new Intent(getApplicationContext(),OldActiveActivity.class);
                startActivity(intent);
                break;
        }
    }
}

