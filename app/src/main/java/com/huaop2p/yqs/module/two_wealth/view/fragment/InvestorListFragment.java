package com.huaop2p.yqs.module.two_wealth.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.view.ScrollAbleFragment;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.two_wealth.adapter.InvestorAdapter;
import com.huaop2p.yqs.module.two_wealth.model.entity.Investor;
import com.huaop2p.yqs.module.two_wealth.presenter.impl.InvestorPresenterImpl;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshBase;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshListView;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/11.
 * 作者：任洋
 * 功能：投资人列表
 */
public class InvestorListFragment extends ScrollAbleFragment<BaseResponseEntity<List<Investor>>> {
    private PullToRefreshListView mListView;
    private InvestorAdapter adapter;
    private List<Investor> list;
    private InvestorPresenterImpl presenter;
    private Map<String, Object> map;
    private  int pageIndex=1;
    @Override
    public void showData() {
        if (adapter==null){
            presenter=new InvestorPresenterImpl(this);
            adapter=new InvestorAdapter(list,getActivity(),R.layout.item_investor);
            mListView.setAdapter(adapter);
            map = new HashMap<>();
            map.put("KeyId",getArguments().getString("KeyId"));
            map.put("PageIndex",pageIndex);
            map.put("PageSize","10");
            presenter.loadData(map,HttpUrl.GET_InvestmentList,0,RequestMethod.POST);
        }
    }

    @Override
    public View initViews(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_investor_list, null);
        mListView= (PullToRefreshListView) view.findViewById(R.id.mListView);
        mListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                map.put("PageIndex",++pageIndex);
                presenter.loadData(map, HttpUrl.GET_InvestmentList, 0, RequestMethod.POST);
            }
        });
        return view;
    }

    @Override
    public View getScrollableView() {
        return mListView;
    }

    @Override
    public void showSuccess(BaseResponseEntity<List<Investor>> investor) {
        if (investor.Total.equals("0") || investor.ReturnMessage.size() == 0) {
            ToastUtils.show(AppApplication.mContext, "没有数据了");
            return;
        }
        if (this.list == null) {
            adapter.refresh(investor.ReturnMessage);
            this.list = investor.ReturnMessage;
            return;
        }
        this.list.addAll(investor.ReturnMessage);
        adapter.refresh(list);
    }
    /**
     * 加载数据之后
     **/
    @Override
    public void loadDataOver() {
        mListView.stopLoadOrRefresh();
    }
}
