package com.huaop2p.yqs.module.three_mine.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
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
import com.huaop2p.yqs.module.three_mine.activity.OverdueLotteryTicketActivity;
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
 * Created by maoxiaofei on 2016/5/16.
 * 和红包fragment一模一样
 */
public class LottetyTicketFragment extends BaseAutoFragment implements RecycleViewAdapter2.callbacktemp {

    private String type;//1 红包   2  奖券
    //    private RecyclerView recyclerView;
    private ListView listView;
    //    private RecycleViewAdapter adapter;
    private RecycleViewAdapter2 adapter;
    private List list = new ArrayList<>();

    private LinearLayoutManager mlayoutmanager;
    private int flag;
    private TextView textview_overRed;
    private Button btn_listvew;

    private View view;
    private View foot;

    private int productType;
    private TextView text_meihongbao;

    private Long money;
    private String keyId;

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
        }else if (flag == 2){
            initData2();
        }

        //设置固定大小
//        recyclerView.setHasFixedSize(true);
//        //创建线性布局
//        mlayoutmanager = new LinearLayoutManager(getActivity());
//        //垂直方向
//        mlayoutmanager.setOrientation(OrientationHelper.VERTICAL);
//        //给RecyclerView设置布局管理器
//        recyclerView.setLayoutManager(mlayoutmanager);



        onclicek();

    }

    @Override
    public View initViews(LayoutInflater inflater) {

        view = inflater.inflate(R.layout.fragment_lottetyticket, null);
        initui();
        return view;
    }

    private void initui() {
        listView = (ListView) view.findViewById(R.id.recyclerview);
        btn_listvew = (Button) view.findViewById(R.id.btn_listvew);
//        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerview);
        foot = getLayoutInflater(null).inflate(R.layout.recycleview_footerticket, null);
        textview_overRed = (TextView) foot.findViewById(R.id.textview_overRed);
        text_meihongbao = (TextView) view.findViewById(R.id.text_meihongbao);
        listView.addFooterView(foot);
        listView.setEmptyView(text_meihongbao);
    }

    //{"Type":"0：可使用 1：已过期"}
    private void initData() {
        Map map = new HashMap<>();
        String use = "0";
        map.put("Type", use);

        RedPackageImpl.getintense().getLottetyTicket(map, new OnRequestLinstener<BaseResponseEntity<List<RedPackage>>>() {
            @Override
            public void success(BaseResponseEntity<List<RedPackage>> redPackageBaseResponseEntity) {
                LogUtils.e(GsonUtils.getGson().toJson(redPackageBaseResponseEntity));
                if (adapter == null) {
                    for (int i = 0; i < redPackageBaseResponseEntity.ReturnMessage.size(); i++) {
                        list.add(redPackageBaseResponseEntity.ReturnMessage.get(i));
                    }
//                    adapter = new RecycleViewAdapter(getActivity(),list, type,flag);
                    adapter = new RecycleViewAdapter2(list, getActivity(),0,type, flag, LottetyTicketFragment.this);
                    listView.setAdapter(adapter);

                }
            }

            @Override
            public void error(int code, String error) {
                LogUtils.e(error);

            }
        }, HttpUrl.GetUserTicket, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity<List<RedPackage>>>() {
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
        RedPackageImpl.getintense().getLottetyTicket2(map, new OnRequestLinstener<BaseResponseEntity<List<RedPackage>>>() {
            @Override
            public void success(BaseResponseEntity<List<RedPackage>> redPackageBaseResponseEntity) {
                LogUtils.e(GsonUtils.getGson().toJson(redPackageBaseResponseEntity));
                if (adapter == null) {
                    for (int i = 0; i < redPackageBaseResponseEntity.ReturnMessage.size(); i++) {
                        list.add(redPackageBaseResponseEntity.ReturnMessage.get(i));
                    }
//                    adapter = new RecycleViewAdapter(getActivity(), list, type);

                    adapter = new RecycleViewAdapter2(list, getActivity(),0,type, flag, LottetyTicketFragment.this);
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
        }, HttpUrl.Get_SelectUseTicket, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity<List<RedPackage>>>() {
        });
    }
    //对红包底部的过期红包进行监听跳转
//    private void onclick(){
//        adapter.setOnClickListener(new RecycleViewAdapter.Onfooterclicklistener() {
//            @Override
//            public void onClickListener(View viewfooter, int position) {
//                //在这写你底部点击事件--跳转了什么的
//                Intent intent = new Intent(getActivity(), OverdueLotteryTicketActivity.class);
//                intent.putExtra("tag","2");
//                startActivity(intent);
//
//            }
//        });
//    }
    //对红包底部的过期红包进行监听跳转
    private void onclicek() {
        textview_overRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OverdueLotteryTicketActivity.class);
                intent.putExtra("tag", "2");
                startActivity(intent);
            }
        });

        text_meihongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OverdueLotteryTicketActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void click( final  int a) {
        btn_listvew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Object redPackage = list.get(a);

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("red", redPackage);
                map.put("type", type);
                EventBus.getDefault().post(map);
                getActivity().finish();
            }
        });
    }
}
