package com.huaop2p.yqs.module.two_wealth.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.three_mine.model.entity.Borrower;
import com.huaop2p.yqs.module.two_wealth.adapter.BorrowerInfoAdapter;
import com.huaop2p.yqs.module.two_wealth.presenter.impl.BorrowerInfoPresenterImpl;
import com.huaop2p.yqs.module.two_wealth.view.IBorrowerInfoView;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshBase;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshListView;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/15.
 * 作者：任洋
 * 功能：财富中心点击进来的借款人信息界面
 */
public class BorrowerInfoActivity extends BaseAutoActivity<BaseResponseEntity<List<Borrower>>> implements IBorrowerInfoView {
    private PullToRefreshListView mListView;
    private BorrowerInfoAdapter adapter;
    private List<Borrower> borrowers;
    private int pageIndex;
    private static final int PAGESIZE = 10;
    private BorrowerInfoPresenterImpl presenter;
    private Map<String, Object> map;

    @Override
    public int getLayoutId() {
        return R.layout.activity_borrower_info;
    }

    @Override
    public void initViews() {
        mListView = (PullToRefreshListView) findViewById(R.id.mListView);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BorrowerInfoActivity.this, BorrowerInfoDetailsActivity.class);
                intent.putExtra("keyId",borrowers.get(position-1).KeyId);
                startActivity(intent);
            }
        });
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                pageIndex = 1;
                presenter.setPageIndex(pageIndex);
                map.put("PageIndex", String.valueOf(pageIndex));
                try {
                    presenter.loadData(map, HttpUrl.SELECT_LOAN_PERSONINFO, 0, RequestMethod.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                map.put("PageIndex", String.valueOf(++pageIndex));
                try {
                    Log.i("ddd", "" + pageIndex);
                    presenter.setPageIndex(pageIndex);
                    presenter.loadData(map, HttpUrl.SELECT_LOAN_PERSONINFO, 0, RequestMethod.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public Object getObject() {
        return this;
    }
    @Override
    public void initData() {
        map = new HashMap<>();
        map.put("PageSize", PAGESIZE);
        adapter = new BorrowerInfoAdapter(borrowers, this, R.layout.item_borrower_info);
        mListView.setAdapter(adapter);
        presenter = new BorrowerInfoPresenterImpl(this);
        mListView.setRefreshing();
    }

    @Override
    public void showSuccess(BaseResponseEntity<List<Borrower>> borrowers) {
        if (borrowers.ReturnMessage.size() == 0) {
            ToastUtils.show(this, "没有数据了");
            return;
        }
        if (this.borrowers == null) {
            adapter.refresh(borrowers.ReturnMessage);
            this.borrowers = borrowers.ReturnMessage;
            return;
        }
        this.borrowers.addAll(borrowers.ReturnMessage);
        adapter.refresh(this.borrowers);
    }

    @Override
    public void clearData() {
        if (borrowers != null)
            borrowers.clear();
    }

    @Override
    public void loadDataOver() {
        mListView.stopLoadOrRefresh();
    }
}
