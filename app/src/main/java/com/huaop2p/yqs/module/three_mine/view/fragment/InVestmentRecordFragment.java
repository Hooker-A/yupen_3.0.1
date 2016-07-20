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
import com.huaop2p.yqs.module.base.view.ScrollAbleFragment;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.three_mine.activity.InvestmentDetailsActivity;
import com.huaop2p.yqs.module.three_mine.adapter.InvestmentAdapter;
import com.huaop2p.yqs.module.three_mine.model.entity.Investment;
import com.huaop2p.yqs.module.three_mine.presenter.impl.InvestmentPresenterImpl;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshBase;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/21.
 * <p/>
 * 投资记录
 */
public class InVestmentRecordFragment extends ScrollAbleFragment<BaseResponseEntity<List<Investment>>>  {
    private List<Investment> investments;
    private InvestmentAdapter adapter;
    @ViewInject(R.id.mListView)
    private PullToRefreshListView mListView;
    private InvestmentPresenterImpl investmentPresenter;
    private int type;
    private Map<String, Object> paramMap;
    @ViewInject(R.id.tv_hiht)
    private TextView tv_hiht;

    @Override
    public void showData() {
        if (adapter == null) {
            type = getArguments().getInt("type");
            paramMap = new HashMap<>();
            investments = new ArrayList<>();
            adapter = new InvestmentAdapter(investments, getActivity(), R.layout.item_investment, type);
            mListView.setAdapter(adapter);
            investmentPresenter = new InvestmentPresenterImpl(this);
            setConfig();
            paramMap.put("Type", String.valueOf(type));
            investmentPresenter.loadData(paramMap, HttpUrl.GET_INVERSTMENT_RECORD, 0, RequestMethod.POST);
        }
    }

    private void setConfig() {
        mListView.setMode(PullToRefreshBase.Mode.DISABLED);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent intent = new Intent(getActivity(), InvestmentDetailsActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("DDH", investments.get(position-1).DDH);
                intent.putExtra("keyId", String.valueOf(investments.get(investments.size()-position).ProductId));
                startActivity(intent);
            }
        });
        switch (type) {
            case State.YUFANGBAO:
                tv_hiht.setText("暂无余房宝投资记录");
                break;
            case State.YUXINBAO:
                tv_hiht.setText("暂无余信宝投资记录");
                break;
            case State.YUCHEBAO:
                tv_hiht.setText("暂无余车宝投资记录");
                break;
        }
        mListView.getRefreshableView().setEmptyView(tv_hiht);
    }

    @Override
    public View initViews(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_investment, null);
        return view;
    }

    @Override
    public void startLoadData() {

    }

    @Override
    public void loadDataOver() {

    }


    @Override
    public void showSuccess(BaseResponseEntity<List<Investment>> investments) {
        this.investments.addAll(investments.ReturnMessage);
        adapter.refresh(this.investments);
    }

    /***
     * 这里处理异常
     **/
    @Override
    public void showError(int errorCode, String str) {
        ToastUtils.show(AppApplication.mContext, str);
    }

    @Override
    public View getScrollableView() {
        return mListView.getRefreshableView();
    }

}
