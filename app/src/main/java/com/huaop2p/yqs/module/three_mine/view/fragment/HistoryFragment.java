package com.huaop2p.yqs.module.three_mine.view.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.view.BaseAutoFragment;
import com.huaop2p.yqs.module.three_mine.activity.InvestmentDetailsActivity;
import com.huaop2p.yqs.module.three_mine.adapter.HistoryAdapter;
import com.huaop2p.yqs.module.three_mine.model.entity.Investment;
import com.huaop2p.yqs.module.three_mine.presenter.impl.HistoryPresenterImpl;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshBase;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/17.
 * 作者：任洋
 * 功能：投资历史界面
 */
public class HistoryFragment extends BaseAutoFragment<BaseResponseEntity<List<Investment>>> {
    @ViewInject(R.id.mListView)
    private PullToRefreshListView mListView;
    private HistoryAdapter adapter;
    private int type;
    private Map<String, Object> paramMap;
    private HistoryPresenterImpl historyPresenter;
    private List<Investment> investments;
    @ViewInject(R.id.tv_hiht)
    private TextView tv_hiht;
    @Override
    public void showData() {
        if (adapter == null) {
            type = getArguments().getInt("type");
            paramMap = new HashMap<>();
            investments = new ArrayList<>();
            adapter = new HistoryAdapter(investments, getActivity(), R.layout.item_history,type);
            mListView.setAdapter(adapter);
            historyPresenter = new HistoryPresenterImpl(this);
            paramMap.put("Type", String.valueOf(type));
            paramMap.put("Progress", "3");
            historyPresenter.loadData(paramMap, HttpUrl.GET_INVERSTMENT_RECORD, 0, RequestMethod.POST);
            setLinstener();
            switch (type) {
                case State.YUFANGBAO:
                    tv_hiht.setText("暂无余房宝历史投资记录");
                    break;
                case State.YUXINBAO:
                    tv_hiht.setText("暂无余信宝历史投资记录");
                    break;
                case State.YUCHEBAO:
                    tv_hiht.setText("暂无余车宝历史投资记录");
                    break;
            }
            mListView.setEmptyView(tv_hiht);
        }
    }
    private void setLinstener() {
        mListView.setMode(PullToRefreshBase.Mode.DISABLED);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), InvestmentDetailsActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("isHistory",true);
                intent.putExtra("DDH",investments.get(position-1).DDH);
                intent.putExtra("keyId",String.valueOf(investments.get(position - 1).ProductId));
                startActivity(intent);
            }
        });

    }
    @Override
    public View initViews(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_history, null);
        return view;
    }

    @Override
    public void showSuccess(BaseResponseEntity<List<Investment>> investments) {
        this.investments.addAll(investments.ReturnMessage);
        adapter.refresh(this.investments);
    }
}
