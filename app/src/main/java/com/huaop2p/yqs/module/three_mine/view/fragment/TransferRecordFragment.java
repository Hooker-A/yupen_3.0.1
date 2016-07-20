package com.huaop2p.yqs.module.three_mine.view.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.view.BaseAutoFragment;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.three_mine.activity.TransferRecordDetailsActivity;
import com.huaop2p.yqs.module.three_mine.adapter.TransferAdapter;
import com.huaop2p.yqs.module.three_mine.model.entity.TransferRecord;
import com.huaop2p.yqs.module.three_mine.presenter.impl.TransferRecordPresenterImpl;
import com.huaop2p.yqs.module.three_mine.view.IInVestmentView;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshBase;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/5/13.
 * 作者：任洋
 * 功能： 转让记录
 */
public class TransferRecordFragment extends BaseAutoFragment<BaseResponseEntity<List<TransferRecord>>> implements IInVestmentView {
    private List<TransferRecord> transferRecords;
    private TransferAdapter adapter;
    @ViewInject(R.id.mListView)
    private PullToRefreshListView mListView;
    private TransferRecordPresenterImpl investmentPresenter;
    private int pageIndex = 1;
    private static final int PAGESIZE = 10;
    private String transferStatus;
    private HashMap<String, Object> map;
    private HashMap<String, Object> child;
    @ViewInject(R.id.tv_hiht)
    private TextView tv_hiht;

    @Override
    public void showData() {
        if (adapter == null) {
            transferRecords = new ArrayList<>();
            map = new HashMap<>();
            child = new HashMap<>();
            transferStatus = getArguments().getString("type");
            adapter = new TransferAdapter(transferRecords, getActivity(), R.layout.item_investment, transferStatus);
            mListView.setAdapter(adapter);
            investmentPresenter = new TransferRecordPresenterImpl(this);
            setLinstener();
            child.put("TransferStatus", transferStatus);
            map.put("PageIndex", pageIndex);
            map.put("PageSize", PAGESIZE);
            map.put("AppointEntity", child);
            investmentPresenter.loadData(map, HttpUrl.GET_TRANSFER_LIST, 0, RequestMethod.POST);
        }
    }

    private void setLinstener() {
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TransferRecord transferRecord = transferRecords.get(position - 1);
                Intent intent = new Intent(getActivity(), TransferRecordDetailsActivity.class);
                if (transferRecord.YuPenProductId == 1) {
                    intent.putExtra("type", State.YUXINBAO);
                } else if (transferRecord.YuPenProductId == 5) {
                    intent.putExtra("type", State.YUCHEBAO);
                } else {
                    intent.putExtra("type", State.YUFANGBAO);
                }
                if (transferStatus.equals("2")) {
                    intent.putExtra("title", "转让中");
                } else {
                    intent.putExtra("title", "已转让");
                }
                intent.putExtra("DDH", transferRecord.DDH);
                intent.putExtra("keyId", transferRecord.TransferId);
                intent.putExtra("StandardId", transferRecord.StandardId);
                startActivity(intent);
            }
        });
        switch (Integer.valueOf(transferStatus)) {
            case State.YUFANGBAO:
                tv_hiht.setText("暂无余房宝转让记录");
                break;
            case State.YUXINBAO:
                tv_hiht.setText("暂无余信宝转让记录");
                break;
            case State.YUCHEBAO:
                tv_hiht.setText("暂无余车宝转让记录");
                break;
        }
        mListView.getRefreshableView().setEmptyView(tv_hiht);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                pageIndex = 1;
                map.put("PageIndex", pageIndex);
                try {
                    investmentPresenter.loadData(map, HttpUrl.GET_TRANSFER_LIST, 0, RequestMethod.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                map.put("PageIndex", String.valueOf(++pageIndex));
                try {
                    investmentPresenter.loadData(map, HttpUrl.GET_TRANSFER_LIST, 0, RequestMethod.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public View initViews(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_transfer_record, null);
        return view;
    }

    @Override
    public void startLoadData() {

    }

    @Override
    public void loadDataOver() {
        mListView.stopLoadOrRefresh();
    }


    @Override
    public void showSuccess(BaseResponseEntity<List<TransferRecord>> transfers) {
        if (transfers.Total.equals("0") || transfers.ReturnMessage.size() == 0) {
            ToastUtils.show(AppApplication.mContext, "没有数据了");
            return;
        }
        if (pageIndex == 1)
            this.transferRecords.clear();
        Log.i("eee", transfers.ReturnMessage.size() + "");
        this.transferRecords.addAll(transfers.ReturnMessage);
        adapter.refresh(this.transferRecords);
    }

    @Override
    public void getContract(String str) {
        EventBus.getDefault().post(str);
    }

}
