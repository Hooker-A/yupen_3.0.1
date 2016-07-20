package com.huaop2p.yqs.module.three_mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.four_set.activity.MatterActivity;
import com.huaop2p.yqs.module.one_home.activity.Market_Activity;
import com.huaop2p.yqs.module.three_mine.adapter.PointMarketAdapter;
import com.huaop2p.yqs.module.three_mine.model.entity.ThirtyDayPointBean;
import com.huaop2p.yqs.module.three_mine.model.impl.PointMarketImpl;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maoxiaofei on 2016/5/13.
 */
public class PointMarketActivity extends BaseAutoActivity{

    private ListView lv_point;
    private PointMarketAdapter adapter;
    private List<ThirtyDayPointBean> msglist ;

    private TextView tv_allpoint;
    private View head;
    private Button btn_buy;
    private TextView text_point_more;
    private TextView market_state;

    @Override
    public Object getObject() {
        return this;
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_pointmarket;
    }

    @Override
    public void initViews() {
//        SetActivityTitle("我的积分");
        lv_point = (ListView) findViewById(R.id.lv_point);

        //给listview添加一个头布局
        head = getLayoutInflater().inflate(R.layout.pointmarkethead,null);
        lv_point.setVerticalScrollBarEnabled(false);
        tv_allpoint = (TextView) head.findViewById(R.id.tv_allpoint);
        btn_buy = (Button) head.findViewById(R.id.btn_buy);
        text_point_more = (TextView) head.findViewById(R.id.text_point_more);
        market_state = (TextView) head.findViewById(R.id.market_state);


    }

    @Override
    public void initData() {

        getpoint();
        addhead();


    }

    //得到总积分
    private void getpoint(){
        Map map = new HashMap();
        PointMarketImpl.getintense().GetPoint(map, new OnRequestLinstener<BaseResponseEntity<String>>() {
            @Override
            public void success(BaseResponseEntity<String> stringBaseResponseEntity) {
                LogUtils.e(GsonUtils.getGson().toJson(stringBaseResponseEntity + "000000000"));
                String point = stringBaseResponseEntity.ReturnMessage;
                tv_allpoint.setText(point);
            }

            @Override
            public void error(int code, String error) {
                LogUtils.e(error);
            }
        }, HttpUrl.GET_USERSUMCREDITS, 0, RequestMethod.GET, new TypeToken<BaseResponseEntity<String>>() {
        });


    }

    //得到最近三十天的积分
    private void getThirtypiont(){
        Map map = new HashMap();
        PointMarketImpl.getintense().GetThirtyPoint(map, new OnRequestLinstener<BaseResponseEntity<List<ThirtyDayPointBean>>>() {
            @Override
            public void success(BaseResponseEntity<List<ThirtyDayPointBean>> stringBaseResponseEntity) {
                LogUtils.e(GsonUtils.getGson().toJson(stringBaseResponseEntity.ReturnMessage));
                msglist = stringBaseResponseEntity.ReturnMessage;
                if (adapter == null) {
//                    for (int i =0;i < 10;i++ ){
//                        msglist.add(null);
//                    }


                    //这里加载适配器的时候传入的是一个实体item的布局msglist,this,R.layout.item_pointmarket
                    adapter = new PointMarketAdapter(msglist, PointMarketActivity.this, R.layout.item_pointmarket);
                    lv_point.setAdapter(adapter);

                }
            }

            @Override
            public void error(int code, String error) {
                LogUtils.e(error);
            }
        }, HttpUrl.GET_THIRTYUSERPOINTS, 0, RequestMethod.GET, new TypeToken<BaseResponseEntity<List<ThirtyDayPointBean>>>() {
        });
    }

    //添加头部并给头部加载数据
    private void addhead(){
        lv_point.addHeaderView(head);

        click();

        getThirtypiont();

    }

    //点击事件
    private void click(){
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PointMarketActivity.this, Market_Activity.class);
                startActivity(intent);
            }
        });

        text_point_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PointMarketActivity.this, HistoryPointActivity.class);
                startActivity(intent);
            }
        });

        market_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PointMarketActivity.this, MatterActivity.class);
                startActivity(intent);
            }
        });
    }



}
