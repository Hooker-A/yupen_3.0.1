package com.huaop2p.yqs.module.three_mine.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.view.BaseAutoFragment;
import com.huaop2p.yqs.module.three_mine.activity.OverdueRedPradActivity;
import com.huaop2p.yqs.module.three_mine.adapter.RecycleViewAdapter2;
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

import de.greenrobot.event.EventBus;

/**
 * Created by maoxiaofei on 2016/6/8.
 */
public class RedPagketFragment extends BaseAutoFragment implements RecycleViewAdapter2.callbacktemp {

    private String type;//1 红包   2  奖券
    private RecyclerView recyclerView;
    private ListView listView;
    //    private RecycleViewAdapter adapter;
    private RecycleViewAdapter2 adapter;
    private List<RedPackage> list = new ArrayList<>();
    private TextView textview_overRedb;
    private View foot;
    private TextView textview_overRed;
    private Button btn_listvew;

    private int flag;//1红包  2  是奖券
    private int productType;
    private Long money;
    private String keyId;
    private TextView text_meihongbao;


    private LinearLayoutManager mlayoutmanager;

    private View view;

    @Override
    public void showData() {
        type = getArguments().getString("type");
        flag = getArguments().getInt("flag");
        productType = getArguments().getInt("productType");
        money = getArguments().getLong("money");
        keyId = getArguments().getString("keyId");

        if (flag == 0){
            btn_listvew.setVisibility(View.GONE);
            initData();
        }else if (flag == 1){
            initData2();
        }


//        //设置固定大小
//        recyclerView.setHasFixedSize(true);
//        //创建线性布局
//        mlayoutmanager = new LinearLayoutManager(getActivity());
//        //垂直方向
//        mlayoutmanager.setOrientation(OrientationHelper.VERTICAL);
//        //给RecyclerView设置布局管理器
//        recyclerView.setLayoutManager(mlayoutmanager);



        onclick();

    }

    @Override
    public View initViews(LayoutInflater inflater) {
//        view = inflater.inflate(R.layout.fragment_lottetyticket, null);
        view = inflater.inflate(R.layout.fragment_lottetyticket2, null);
        initui();

        return view;
    }

    private void initui() {

//        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        listView = (ListView) view.findViewById(R.id.recyclerview);
        btn_listvew = (Button) view.findViewById(R.id.btn_listvew);
        text_meihongbao = (TextView) view.findViewById(R.id.text_meihongbao);
        textview_overRedb = (TextView) view.findViewById(R.id.textview_overRed);
        foot = getLayoutInflater(null).inflate(R.layout.recycleview_footer, null);
        textview_overRed = (TextView) foot.findViewById(R.id.textview_overRed);
        listView.addFooterView(foot);
        listView.setEmptyView(text_meihongbao);

    }

    //{"Type":"0：可使用 1：已过期"}
    private void initData() {
        Map map = new HashMap<>();
        String use = "0";
        map.put("Type", use);
        RedPackageImpl.getintense().getRedPackage(map, new OnRequestLinstener<BaseResponseEntity<List<RedPackage>>>() {
            @Override
            public void success(BaseResponseEntity<List<RedPackage>> redPackageBaseResponseEntity) {
                LogUtils.e(GsonUtils.getGson().toJson(redPackageBaseResponseEntity));
                if (adapter == null) {
                    for (int i = 0; i < redPackageBaseResponseEntity.ReturnMessage.size(); i++) {
                        list.add(redPackageBaseResponseEntity.ReturnMessage.get(i));
                    }
//                    adapter = new RecycleViewAdapter(getActivity(), list, type);

                    adapter = new RecycleViewAdapter2(list, getActivity(),0,type, flag, RedPagketFragment.this);
                    listView.setAdapter(adapter);


                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        }
                    });

                }
            }

            @Override
            public void error(int code, String error) {
                LogUtils.e(error);

            }
        }, HttpUrl.GetUserRedRecord, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity<List<RedPackage>>>() {
        });
    }

    //{"Type":"0：可使用 1：已过期"}
    private void initData2() {
        Map map = new HashMap<>();
        String use = "0";
        Long Money = money;
        String MoneyCenterId = keyId;
        int  ProductId = productType;
        map.put("Type", use);
        map.put("Money",Money);
        map.put("MoneyCenterId",MoneyCenterId);
        map.put("ProductId",ProductId);
        RedPackageImpl.getintense().getRedPackage2(map, new OnRequestLinstener<BaseResponseEntity<List<RedPackage>>>() {
            @Override
            public void success(BaseResponseEntity<List<RedPackage>> redPackageBaseResponseEntity) {
                LogUtils.e(GsonUtils.getGson().toJson(redPackageBaseResponseEntity));
                if (adapter == null) {
                    for (int i = 0; i < redPackageBaseResponseEntity.ReturnMessage.size(); i++) {
                        list.add(redPackageBaseResponseEntity.ReturnMessage.get(i));
                    }
//                    adapter = new RecycleViewAdapter(getActivity(), list, type);

                    adapter = new RecycleViewAdapter2(list, getActivity(),0,type, flag, RedPagketFragment.this);
                    listView.setAdapter(adapter);


                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        }
                    });

                }
            }

            @Override
            public void error(int code, String error) {
                LogUtils.e(error);

            }
        }, HttpUrl.GetUserRedRecord2, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity<List<RedPackage>>>() {
        });
    }

    //对红包底部的过期红包进行监听跳转
//    private void onclick(){
//        adapter.setOnClickListener(new RecycleViewAdapter.Onfooterclicklistener() {
//            @Override
//            public void onClickListener(View viewfooter, int position) {
//                //在这写你底部点击事件--跳转了什么的
//                Intent intent = new Intent(getActivity(), OverdueRedPradActivity.class);
//                startActivity(intent);
//
//            }
//        });
//    }

    //跳转到过期红包
    private void onclick() {
        textview_overRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OverdueRedPradActivity.class);
                startActivity(intent);
            }
        });

        text_meihongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OverdueRedPradActivity.class);
                startActivity(intent);
            }
        });



    }




    @Override
    public void click(final int position) {
        btn_listvew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RedPackage redPackage = list.get(position);

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("red", redPackage);
                map.put("type", type);
                EventBus.getDefault().post(map);
                getActivity().finish();
            }
        });
    }
}
