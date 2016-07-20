package com.huaop2p.yqs.module.three_mine.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.view.ScrollAbleFragment;
import com.huaop2p.yqs.module.three_mine.adapter.PaymentHistoryAdapter;
import com.huaop2p.yqs.module.three_mine.model.entity.PaymentHistory;
import com.huaop2p.yqs.module.three_mine.presenter.impl.PaymentHistoryPresenterImpl;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshBase;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshListView;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/5/13.
 * 作者：任洋
 * 功能：还款记录
 */
public class PaymentHistoryFragment extends ScrollAbleFragment<List<PaymentHistory>> {
    private PullToRefreshListView mListView;
    private PaymentHistoryAdapter adapter;
    private List<PaymentHistory> list;
    private PaymentHistoryPresenterImpl presenter;
    private HashMap<String, Object> map;
    private String ddh;
    private int type;
    private TextView tv_count;

    @Override
    public void showData() {
        if (adapter == null) {
            map = new HashMap<>();
            ddh = getArguments().getString("DDH");
            type = getArguments().getInt("type");
            presenter = new PaymentHistoryPresenterImpl(this);
            map.put("DDH", ddh);
            presenter.loadData(map, HttpUrl.GET_PAYMENT_HISTORY, 0, RequestMethod.POST);
            adapter = new PaymentHistoryAdapter(list, getActivity(), R.layout.item_payment_history);
            mListView.setAdapter(adapter);
            switch (type) {
                case State.YUXINBAO:
                    tv_count.setTextColor(getResources().getColor(R.color.red));
                    break;
                case State.YUFANGBAO:
                    tv_count.setTextColor(getResources().getColor(R.color.yufangbao));
                    break;
                case State.YUCHEBAO:
                    tv_count.setTextColor(getResources().getColor(R.color.yuchebao));
                    break;
            }
        }
    }

    @Override
    public View initViews(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_payment_history, null);
        mListView = (PullToRefreshListView) view.findViewById(R.id.mListView);
        mListView.setMode(PullToRefreshBase.Mode.DISABLED);
        tv_count = (TextView) view.findViewById(R.id.tv_count);
        return view;
    }

    @Override
    public View getScrollableView() {
        return mListView.getRefreshableView();
    }

    @Override
    public void showSuccess(List<PaymentHistory> phs) {
        tv_count.setText(String.valueOf(phs.size()));
        adapter.refresh(phs);
    }
}
