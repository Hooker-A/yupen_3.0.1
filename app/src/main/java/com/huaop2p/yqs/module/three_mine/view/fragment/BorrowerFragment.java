package com.huaop2p.yqs.module.three_mine.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.view.ScrollAbleFragment;
import com.huaop2p.yqs.module.three_mine.adapter.BorrowerAdapter;
import com.huaop2p.yqs.module.three_mine.model.entity.Borrower;
import com.huaop2p.yqs.module.three_mine.presenter.impl.BorrowerPresenterImpl;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshBase;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshListView;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/13.
 * 作者：任洋
 * 功能：借款人界面
 */
public class BorrowerFragment extends ScrollAbleFragment<BaseResponseEntity<List<Borrower>>>  {
    private PullToRefreshListView mListView;
    private BorrowerAdapter adapter;
    private BorrowerPresenterImpl borrowerPresenter;
    private Map<String, Object> map;
    private List<Borrower> borrwers;
    private TextView tv_num;
    private String DDH;

    @Override
    public void showData() {
        if (adapter == null) {
            DDH=getArguments().getString("DDH");
            borrwers = new ArrayList<>();
            adapter = new BorrowerAdapter(null, getActivity(), R.layout.item_borrower);
            mListView.setAdapter(adapter);
            borrowerPresenter = new BorrowerPresenterImpl(this);
            setListener();

            map = new HashMap<String, Object>();
            Map<String, Object> childMap = new HashMap<>();
            childMap.put("ddh",DDH);
            map.put("AppointEntity", childMap);
            borrowerPresenter.loadDetail(map, HttpUrl.GET_SELLOAN_PERSON_LIST, 0, RequestMethod.POST);
        }
    }

    private void setListener() {
        mListView.setMode(PullToRefreshBase.Mode.DISABLED);
    }

    @Override
    public View initViews(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_borrower, null);
        mListView = (PullToRefreshListView) view.findViewById(R.id.mListView);
        tv_num= (TextView) view.findViewById(R.id.tv_num);
        return view;
    }

    @Override
    public View getScrollableView() {
        return mListView.getRefreshableView();
    }

    @Override
    public void showSuccess(BaseResponseEntity<List<Borrower>> borrowerBaseResponseEntity) {
        borrwers.clear();
        borrwers.addAll(borrowerBaseResponseEntity.ReturnMessage);
        adapter.refresh(borrwers);
        tv_num.setText(String.valueOf(borrwers.size()));
    }

}
