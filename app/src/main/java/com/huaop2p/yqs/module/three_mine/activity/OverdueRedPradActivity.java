package com.huaop2p.yqs.module.three_mine.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.three_mine.adapter.OverdueRedAdapter;
import com.huaop2p.yqs.module.three_mine.model.entity.RedPackage;
import com.huaop2p.yqs.module.three_mine.model.impl.RedPackageImpl;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maoxiaofei on 2016/6/14.
 */
public class OverdueRedPradActivity extends BaseAutoActivity {

    private RecyclerView recyclerView;
    private OverdueRedAdapter adapter;
    private List<RedPackage> list = new ArrayList<>();
    private String tag="1";
    private TextView text_meihongbao;

    private LinearLayoutManager mlayoutmanager;

    @Override
    public Object getObject() {
        return this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_overduered;
    }


    @Override
    public void initViews() {
        SetActivityTitle("过期红包");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        text_meihongbao = (TextView) findViewById(R.id.text_meihongbao);

    }

    @Override
    public void initData() {
        itData();
    }

    //{"Type":"0：可使用 1：已过期"}
    public void itData() {
        //设置固定大小
        recyclerView.setHasFixedSize(true);
        //创建线性布局
        mlayoutmanager = new LinearLayoutManager(this);
        //垂直方向
        mlayoutmanager.setOrientation(OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        recyclerView.setLayoutManager(mlayoutmanager);

        Map map = new HashMap<>();
        String use = "1";
        map.put("Type", use);
        RedPackageImpl.getintense().getRedPackage(map, new OnRequestLinstener<BaseResponseEntity<List<RedPackage>>>() {
            @Override
            public void success(BaseResponseEntity<List<RedPackage>> redPackageBaseResponseEntity) {
                LogUtils.e(GsonUtils.getGson().toJson(redPackageBaseResponseEntity));
                if (redPackageBaseResponseEntity.ReturnMessage.size()==0){
                    text_meihongbao.setVisibility(View.VISIBLE);
                }else {
                    text_meihongbao.setVisibility(View.GONE);
                    if (adapter == null) {
                        for (int i = 0; i < redPackageBaseResponseEntity.ReturnMessage.size(); i++) {
                            list.add(redPackageBaseResponseEntity.ReturnMessage.get(i));
                        }
                        adapter = new OverdueRedAdapter(list,tag);
                        recyclerView.setAdapter(adapter);

                    }
                }

            }

            @Override
            public void error(int code, String error) {
                LogUtils.e(error);

            }
        }, HttpUrl.GetUserRedRecord, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity<List<RedPackage>>>() {
        });
    }
}
