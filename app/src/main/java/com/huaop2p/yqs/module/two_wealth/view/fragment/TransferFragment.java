package com.huaop2p.yqs.module.two_wealth.view.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.view.BaseAutoFragment;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.three_mine.model.entity.Borrower;
import com.huaop2p.yqs.module.two_wealth.activity.TransferDetailsActivity;
import com.huaop2p.yqs.module.two_wealth.adapter.TransferAdapter;
import com.huaop2p.yqs.module.two_wealth.model.entity.Transfer;
import com.huaop2p.yqs.module.two_wealth.presenter.impl.TransferPresenterImpl;
import com.huaop2p.yqs.module.two_wealth.view.IWealthCenterView;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshBase;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshListView;
import com.yolanda.nohttp.RequestMethod;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/12.
 * 作者：任洋
 * 功能：财富中心转让列表
 */
public class TransferFragment extends BaseAutoFragment<BaseResponseEntity<List<Transfer>>> implements IWealthCenterView<Transfer> {
    private PullToRefreshListView mListView;
    private BaseResponseEntity<List<Transfer>> transfers;
    private int pageIndex;
    private final static int PAGESIZE = 10;
    private int totalPage;
    private TransferPresenterImpl presenter;
    private TransferAdapter adapter;
    private Map<String, Object> map;


    @Override
    public void showData() {
        if (presenter == null) {
            presenter = new TransferPresenterImpl(this);
            adapter = new TransferAdapter(null, getActivity(), R.layout.item_yuxinbao);
            mListView.setAdapter(adapter);
            setLinstener();
            map = new LinkedHashMap<String, Object>();
            map.put("PageSize", PAGESIZE);
            mListView.setRefreshing();
        }
    }

    @Override
    public View initViews(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_yuxinbao, null);
        mListView = (PullToRefreshListView) view.findViewById(R.id.mListView);
        return view;
    }

    private void setLinstener() {
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                pageIndex = 1;
                presenter.setPageIndex(pageIndex);
                map.put("PageIndex", String.valueOf(pageIndex));
                try {
                    presenter.loadData(map, HttpUrl.POST_GET_TRANSFER_LIST, 0, RequestMethod.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                if (totalPage <= pageIndex) {
                    ToastUtils.show(getActivity(), "沒有更多了");
                    loadDataOver();
                    return;
                }
                map.put("PageIndex", String.valueOf(++pageIndex));
                try {
                    presenter.setPageIndex(pageIndex);
                    presenter.loadData(map, HttpUrl.POST_GET_TRANSFER_LIST, 0, RequestMethod.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadDetailView(adapter.getItem(position - 1));
            }
        });
    }

    @Override
    public void loadDetailView(final Transfer transfer) {
        Intent intent = new Intent(getActivity(), TransferDetailsActivity.class);
        switch (transfer.YuPenProductId){
            case 1:
                intent.putExtra("type", State.YUXINBAO);
                break;
            case 2:
                intent.putExtra("type", State.YUFANGBAO);
                break;
            case 5:
                intent.putExtra("type", State.YUCHEBAO);
                break;
        }
        intent.putExtra("keyId",transfer.TransferId);
        intent.putExtra("StandardId",transfer.StandardId);
        startActivity(intent);
    }

    @Override
    public void clearData() {
        adapter.refresh(null);
        if (transfers != null)
            transfers.ReturnMessage.clear();
    }

    @Override
    public List<Transfer> getData() {
        if (transfers == null)
            return null;
        return transfers.ReturnMessage;
    }

    @Override
    public void loadDataOver() {
        mListView.stopLoadOrRefresh();
    }

    @Override
    public void setData(List<Transfer> list) {
        if (transfers == null) {
            adapter.refresh(list);
            return;
        }
        this.transfers.ReturnMessage.addAll(list);
        adapter.refresh(this.transfers.ReturnMessage);
    }

    @Override
    public void showBorrowerInfo(List<String> bs) {

    }

    @Override
    public void showSuccess(BaseResponseEntity<List<Transfer>> transfers) {
        if (this.transfers == null) {
            adapter.refresh(transfers.ReturnMessage);
            this.transfers = transfers;
            return;
        }
        setData(transfers.ReturnMessage);
        int total = Integer.valueOf(transfers.Total);
        totalPage = total % PAGESIZE == 0 ? total / PAGESIZE : total / PAGESIZE + 1;
    }

    @Override
    public void showError(int errorCode, String str) {
        ToastUtils.show(AppApplication.mContext, str);
    }
}
