package com.huaop2p.yqs.module.one_home.view;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.view.BaseAutoFragment;
import com.huaop2p.yqs.module.one_home.activity.ZiXun_WebViewActivity;
import com.huaop2p.yqs.module.one_home.adapter.YuPenMsgAdapter;
import com.huaop2p.yqs.module.one_home.bean.HomeMseZiXunBean;
import com.huaop2p.yqs.module.one_home.entity.Web;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshBase;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshListView;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.RequestMethod;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maoxiaofei on 2016/5/10.
 */
public class YuPenMsgFragment extends BaseAutoFragment {

    private YuPenMsgAdapter adapter;
    private String type;//1 余盆公告  2  媒体报道   3  金融动态
    private List<HomeMseZiXunBean> msglist;
    private PullToRefreshListView mListView;


    @Override
    public void showData() {

        if (adapter == null) {
            type = getArguments().getString("type");
//            for (int i =0;i < 10;i++ ){
//                msglist.add(null);
//            }
            getData();
            setrefash();

        }
    }

    @Override
    public View initViews(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.fragment_yupenmsg, null);
        mListView = (PullToRefreshListView) view.findViewById(R.id.mListView);

        return view;
    }

    //{"Type":"余盆公告=1,媒体报道=2,金融动态=3","PageIndex":"当前页","PageSize":"每页显示多少个"}   上传的参数
    //获取首页资讯的三条数据
    private void getData() {
        Map map = new HashMap();
        map.put("Type", type);
        map.put("PageIndex", 1);
        map.put("PageSize", 1000);
        Web.getInstance().getmorezixun(map, new OnRequestLinstener<BaseResponseEntity<List<HomeMseZiXunBean>>>() {
            @Override
            public void success(BaseResponseEntity<List<HomeMseZiXunBean>> msgCenterBeanBaseResponseEntity) {
                LogUtils.e(msgCenterBeanBaseResponseEntity.ReturnMessage.size() + "");

                msglist = msgCenterBeanBaseResponseEntity.ReturnMessage;
                LogUtils.e(msglist.size() + "");
                if (adapter == null) {
                    adapter = new YuPenMsgAdapter(msglist, getActivity(), Integer.parseInt(type));
                    mListView.getRefreshableView().setAdapter(adapter);
                }

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        LogUtils.e(position - 1 + "");
                        int postiti = position-1;
                        String idd = msglist.get(postiti).Id;
                        LogUtils.e(msglist.get(postiti) + "");
                        //正式接口   测试接口
                        String url = HttpUrl.Get_yupenzixun + idd;//https://api.yupen.cn
                        HomeMseZiXunBean bean = msglist.get(postiti);


                        Intent intent = new Intent(getActivity(), ZiXun_WebViewActivity.class);
//                        intent.putExtra("url", url);
                        intent.putExtra("tag", 1);
                        intent.putExtra("id", idd);
                        intent.putExtra("bean", (Serializable) bean);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void error(int code, String error) {
                LogUtils.e(error);
            }
        }, HttpUrl.Get_InfomationsByName, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity<List<HomeMseZiXunBean>>>() {
        });
    }

    //下拉刷新
    private void setrefash() {

        mListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                getData();
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
            }
        });

    }

    @Override
    public void loadDataOver() {
        mListView.stopLoadOrRefresh();
    }


}
