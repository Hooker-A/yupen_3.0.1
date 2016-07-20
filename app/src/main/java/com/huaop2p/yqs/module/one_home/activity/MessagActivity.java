package com.huaop2p.yqs.module.one_home.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.one_home.adapter.MessageCenterAdapter;
import com.huaop2p.yqs.module.one_home.bean.MsgCenterBean;
import com.huaop2p.yqs.module.one_home.entity.Web;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.RequestMethod;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maoxiaofei on 2016/5/19.
 */
public class MessagActivity extends BaseAutoActivity {

    private ListView msg_recycleview;
    private MessageCenterAdapter adapter;
    private List<MsgCenterBean> list;
    private LinearLayoutManager mlayoutmanager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void initViews() {
//        SetActivityTitle("消息中心");
        msg_recycleview = (ListView) findViewById(R.id.msg_recycleview);
    }
    @Override
    public Object getObject() {
        return this;
    }
    @Override
    public void initData() {



        //设置固定大小
//        msg_recycleview.setHasFixedSize(true);
//        //创建线性布局
//        mlayoutmanager = new LinearLayoutManager(this);
//        //垂直方向
//        mlayoutmanager.setOrientation(OrientationHelper.VERTICAL);
//        //给RecyclerView设置布局管理器
//        msg_recycleview.setLayoutManager(mlayoutmanager);
//
//        //添加分割线
//        msg_recycleview.addItemDecoration(new DividerItemDecoration(
//                this, DividerItemDecoration.VERTICAL_LIST));

        //添加消息中心的假数据
//        if (adapter == null) {
//            list = new ArrayList<>();
//            for (int i = 0; i < 20; i++) {
//                adapter = new MsgRecycleviewAdapter(list);
//                msg_recycleview.setAdapter(adapter);
//            }
//        }

        getMessageData();


    }

    /*
    *{"AppointEntity":{"KeyId":"0"}}
            KeyId=0：获取所有消息
            KeyId=1：获取指定消息

    * */
    //获取消息中心消息调用的接口和上传的参数
    private void getMessageData() {


        Map map = new HashMap();
        Map map1 = new HashMap();
        map1.put("KeyId", "0");
        map.put("AppointEntity", map1);
        Web.getInstance().getMsgQue(map, new OnRequestLinstener<BaseResponseEntity<List<MsgCenterBean>>>() {
            @Override
            public void success(BaseResponseEntity<List<MsgCenterBean>> msgCenterBeanBaseResponseEntity) {
                LogUtils.e(msgCenterBeanBaseResponseEntity.ReturnMessage.size() + "");

                list = msgCenterBeanBaseResponseEntity.ReturnMessage;
                if (adapter==null){
                    adapter = new MessageCenterAdapter(MessagActivity.this,list);
                    msg_recycleview.setAdapter(adapter);
                }

                msg_recycleview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if (list.get(position).getUrl()==null||list.get(position).getUrl().equals("")){
                            Intent intent = new Intent(MessagActivity.this,SecondMsgActivity.class);
                            intent.putExtra("beann", (Serializable) list.get(position));
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(MessagActivity.this,MseProduceActivity.class);
                            intent.putExtra("url", list.get(position).getUrl());
                            startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public void error(int code, String error) {
                LogUtils.e(error);
            }
        }, HttpUrl.GetMessage, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity<List<MsgCenterBean>>>() {
        });
    }
}
