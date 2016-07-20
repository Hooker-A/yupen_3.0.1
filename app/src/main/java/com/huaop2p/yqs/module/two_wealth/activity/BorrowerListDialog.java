package com.huaop2p.yqs.module.two_wealth.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.three_mine.adapter.BorrowerAdapter;
import com.huaop2p.yqs.module.three_mine.model.entity.Borrower;
import com.huaop2p.yqs.module.two_wealth.presenter.impl.BorrowerListPresenterImpl;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshBase;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshListView;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/29.
 * 作者：任洋
 * 功能：{"PageIndex":1,"AppointEntity":{"InvestmentMoney":1500.0,"InvestmentMonth":12}}
 */
public class BorrowerListDialog extends BaseAutoActivity<List<Borrower>> {
    private PullToRefreshListView mListView;
    private BorrowerListPresenterImpl presenter;
    private Map<String, Object> map, childMap;
    private int index = 1;
    private Button btn_change,btn_confim;
    private BorrowerAdapter adapter;
    private TextView tv_title;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_borrower_list;
    }

    @Override
    public void initViews() {
        mListView = (PullToRefreshListView) findViewById(R.id.mListView);
        mListView.setMode(PullToRefreshBase.Mode.DISABLED);
        mListView.getRefreshableView().setBackgroundColor(Color.WHITE);
        tv_title = (TextView) findViewById(R.id.tv_title);
        btn_change= (Button) findViewById(R.id.btn_change);

    }

    @Override
    public void initData() {
        presenter = new BorrowerListPresenterImpl(this);
        map = new HashMap<>();
        childMap = new HashMap<>();
        childMap.put("InvestmentMoney", getIntent().getStringExtra("money"));
        childMap.put("InvestmentMonth", getIntent().getStringExtra("date"));
        map.put("AppointEntity", childMap);
        map.put("PageIndex", index);
        presenter.loadData(map, HttpUrl.SelLoanPerson, 0, RequestMethod.POST);
        adapter = new BorrowerAdapter(null, this, R.layout.item_borrower_dialog);
        mListView.setAdapter(adapter);
    }

    @Override
    public void showSuccess(List<Borrower> borrowers) {
        adapter.refresh(borrowers);
        tv_title.setText(getResources().getString(R.string.str_borrowe_list) + "(" + borrowers.size() + ")");
       btn_change.setText("换一批(" + index + "/" + 3 + ")");
    }

    public void change(View view) {
        index++;
        if (index == 3) {
           LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) btn_change.getLayoutParams();
            layoutParams.width=0;
            layoutParams.weight=0;
            btn_change.setLayoutParams(layoutParams);
        }
        map.put("PageIndex", index);
        presenter.loadData(map, HttpUrl.SelLoanPerson, 0, RequestMethod.POST);
    }
    @Override
    public Object getObject() {
        return this;
    }
}
