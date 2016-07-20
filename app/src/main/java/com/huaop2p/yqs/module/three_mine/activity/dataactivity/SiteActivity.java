package com.huaop2p.yqs.module.three_mine.activity.dataactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.WaitDialog;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.three_mine.adapter.SiteAdapter;
import com.huaop2p.yqs.module.three_mine.model.entity.SiteBean;
import com.huaop2p.yqs.module.three_mine.model.impl.MyDataHttp;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgq on 2016/5/10.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/10 10:59
 * 功能:  收货地址
 */
public class SiteActivity extends BaseActivity implements View.OnClickListener{
    private ListView lv_site;
    private Button bt_add;
    private Intent mIntent;
    private SiteAdapter siteAdapter;
    private List<SiteBean> mList;
    private WaitDialog waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);
        SetActivityTitle("收货地址");
        waitDialog=new WaitDialog(SiteActivity.this);
        waitDialog.show();
        initData();
        httpData();


    }

    /**
     * 查询地址
     */
    private void httpData() {
        Map map=new HashMap();
        MyDataHttp.getInstance().GetAddress(map, new OnRequestLinstener<BaseResponseEntity<List<SiteBean>>>() {
            @Override
            public void success(BaseResponseEntity<List<SiteBean>> listBaseResponseEntity) {
                mList = listBaseResponseEntity.ReturnMessage;
                siteAdapter = new SiteAdapter(mList, SiteActivity.this);
                lv_site.setAdapter(siteAdapter);
                siteAdapter.notifyDataSetChanged();
                waitDialog.dismiss();

            }

            @Override
            public void error(int code, String error) {

            }
        }, HttpUrl.GetAddress, 0, RequestMethod.GET, new TypeToken<BaseResponseEntity<List<SiteBean>>>() {
        });

    }



    private void initData() {
        lv_site= (ListView) findViewById(R.id.lv_site);
        bt_add= (Button) findViewById(R.id.bt_add);
        bt_add.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        httpData();
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_add:
                    mIntent=new Intent(getApplicationContext(),NewSiteActivity.class);
                    startActivity(mIntent);
                    break;
            }
    }



}
