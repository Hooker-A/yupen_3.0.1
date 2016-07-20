package com.huaop2p.yqs.module.three_mine.view.fragment;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.view.ScrollAbleFragment;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.three_mine.adapter.MyAdapter;
import com.huaop2p.yqs.module.three_mine.model.entity.LendInfo;
import com.huaop2p.yqs.module.three_mine.presenter.impl.LendListPresenterImpl;
import com.huaop2p.yqs.utils.ToastUtils;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/10.
 * 作者：任洋
 * 功能：循环出借列表
 */
public class LendListFragment extends ScrollAbleFragment<List<LendInfo>> implements ExpandableListView.OnGroupExpandListener, ExpandableListView.OnGroupCollapseListener {
    private ExpandableListView mListView;
    private MyAdapter adapter;
    private List<List<LendInfo>> lendInfos;
    private LendListPresenterImpl lendListPresenter;
    private int groupIndex = -1;
    private String DDH;

    @Override
    public void showData() {
        if (adapter == null) {
            DDH = getArguments().getString("DDH");
            lendInfos = new ArrayList<>();
            adapter = new MyAdapter(lendInfos, getActivity());
            mListView.setAdapter(adapter);
            lendListPresenter = new LendListPresenterImpl(this);
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> childMap = new HashMap<>();
            childMap.put("ddh", DDH);
            map.put("AppointEntity", childMap);
            lendListPresenter.loadData(map, HttpUrl.GET_ROUND_LEND, 0, RequestMethod.POST);
        }
    }


    @Override
    public View initViews(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_lend_list, null);
        mListView = (ExpandableListView) view.findViewById(R.id.mListView);
        mListView.setGroupIndicator(null);
        mListView.setOnGroupExpandListener(this);
        mListView.setOnGroupCollapseListener(this);
        return view;
    }

    @Override
    public View getScrollableView() {
        return mListView;
    }

    @Override
    public void showError(int errorCode, String str) {
        ToastUtils.show(AppApplication.mContext, str);
    }

    @Override
    public void showSuccess(List<LendInfo> lendInfos) {
        Collections.sort(lendInfos, new Comparator<LendInfo>() {
            @Override
            public int compare(LendInfo lendInfo, LendInfo t1) {
                return lendInfo.LendMoneyType - t1.LendMoneyType;
            }
        });
        List<LendInfo> lists = null;
        for (int i = 0; i < lendInfos.size(); i++) {
            if (i == 0 || lendInfos.get(i).LendMoneyType != lendInfos.get(i - 1).LendMoneyType) {
                if (lists!=null)
                    this.lendInfos.add(lists);
                lists = new ArrayList<>();
                lists.add(lendInfos.get(i));
            }else if (lendInfos.get(i).LendMoneyType == lendInfos.get(i - 1).LendMoneyType||i==lendInfos.size()-1) {
                lists.add(lendInfos.get(i));
            }
            if (i==lendInfos.size()-1)
                this.lendInfos.add(lists);
        }
        Log.i("dddd",lendInfos.size()+"");
        adapter.refresh(this.lendInfos);
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        if (groupIndex != -1) {
            mListView.collapseGroup(groupIndex);
        }
        View listItem = adapter.views.get(groupPosition);
        View view = listItem.findViewById(R.id.iv_down);
        startAnimator(180f, 360f, view);
        groupIndex = groupPosition;
    }

    public void startAnimator(float start, float end, View view) {
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("rotation", start, end);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, pvhZ);
        objectAnimator.setDuration(100).start();

    }

    @Override
    public void onGroupCollapse(int groupPosition) {
        if (groupIndex == groupPosition)
            groupIndex = -1;
        View listItem = adapter.views.get(groupPosition);
        View view = listItem.findViewById(R.id.iv_down);
        startAnimator(180f, 0f, view);
    }
}
