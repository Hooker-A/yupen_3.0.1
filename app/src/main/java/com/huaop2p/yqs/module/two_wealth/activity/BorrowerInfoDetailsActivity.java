package com.huaop2p.yqs.module.two_wealth.activity;

import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.three_mine.adapter.LeftRightAdapter;
import com.huaop2p.yqs.module.two_wealth.model.entity.BorrowerDetails;
import com.huaop2p.yqs.module.two_wealth.presenter.impl.BorrowerDetailsPresenterImpl;
import com.huaop2p.yqs.utils.auto.AutoUtils;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/15.
 * 作者：任洋
 * 功能：借款人详情
 */
public class BorrowerInfoDetailsActivity extends BaseAutoActivity<List<BorrowerDetails>> {
    private GridView gv1, gv2, gv3;
    private List<SparseArray<Object>> personalInfos, jkbInfos, works;
    private LeftRightAdapter personalInfoAdapter, jkbInfoAdapter, workAdapter;
    private String[] lefts = new String[]{"学历:", "个人健康情况:", "固定电话:", "放款金额:", "用途:", "职业:", "单位性质:", "职称:", "专业特长:", "主要经济来源:", "月收入:", "个人综合年收入:", "家庭年收入:", "其他收入:", "家庭年均支付:"};
    private ScrollView mScrollView;
    private LinearLayout ll_card;
    private BorrowerDetailsPresenterImpl presenter;
    private String keyId;
    private Map<String, Object> map;

    @Override
    public int getLayoutId() {
        return R.layout.activity_borrower_details;
    }

    @Override
    public void initViews() {
        gv1 = (GridView) findViewById(R.id.c_gv1);
        gv2 = (GridView) findViewById(R.id.c_gv2);
        gv3 = (GridView) findViewById(R.id.c_gv3);
        mScrollView = (ScrollView) findViewById(R.id.mScrollView);
        ll_card = (LinearLayout) findViewById(R.id.ll_card);

        disableAutoScrollToBottom();
    }
    @Override
    public Object getObject() {
        return this;
    }
    @Override
    public void initData() {
        map = new HashMap<>();
        keyId = getIntent().getStringExtra("keyId");
        map.put("KeyId", keyId);
        presenter = new BorrowerDetailsPresenterImpl(this);
        presenter.loadDetail(map, HttpUrl.LoanPersonInfoById, 0, RequestMethod.POST);
        personalInfos = new ArrayList<>();
        jkbInfos = new ArrayList<>();
        works = new ArrayList<>();
        initListContent(null);
        personalInfoAdapter = new LeftRightAdapter(personalInfos, this, 0);
        jkbInfoAdapter = new LeftRightAdapter(jkbInfos, this, 0);
        workAdapter = new LeftRightAdapter(works, this, 0);
        gv1.setAdapter(personalInfoAdapter);
        gv2.setAdapter(jkbInfoAdapter);
        gv3.setAdapter(workAdapter);
    }

    /***
     * 防止scrollview回滚到底部
     **/
    private void disableAutoScrollToBottom() {
        mScrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        mScrollView.setFocusable(true);
        mScrollView.setFocusableInTouchMode(true);
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });
    }

    private void initListContent(String[] strings) {
        personalInfos.clear();
        jkbInfos.clear();
        works.clear();
        for (int i = 0; i < lefts.length; i++) {
            SparseArray<Object> sa = new SparseArray<>();
            if (strings == null) {
                sa.put(1, null);
            } else {
                sa.put(1, strings[i]);
            }
            sa.put(0, lefts[i]);
            if (i < 3) {
                personalInfos.add(sa);
            } else if (i < 5) {
                jkbInfos.add(sa);
            } else {
                works.add(sa);
            }
        }
    }

    @Override
    public void showSuccess(List<BorrowerDetails> borrowerDetails) {
        BorrowerDetails bd = borrowerDetails.get(0);
        String[] names = bd.LoanPersonName.split(" ");
        for (int i = 0; i < names.length; i++) {
            View view = getLayoutInflater().inflate(R.layout.item_card, ll_card, true);
            AutoUtils.initLayoutSize((ViewGroup)ll_card.getChildAt(i),this);
            TextView tv_name_value = (TextView) ll_card.getChildAt(i).findViewById(R.id.tv_name_value);
            TextView tv_sex_value = (TextView) ll_card.getChildAt(i).findViewById(R.id.tv_sex_value);
            TextView tv_card_type = (TextView) ll_card.getChildAt(i).findViewById(R.id.tv_card_type);
            ImageView iv_sex = (ImageView) ll_card.getChildAt(i).findViewById(R.id.iv_sex);
            TextView tv_card_num = (TextView) ll_card.getChildAt(i).findViewById(R.id.tv_card_num);
            tv_name_value.setText(names[i]);
            String sex = bd.Sex.split(" ")[i];
            tv_sex_value.setText(sex);
            if (sex.equals("男"))
                iv_sex.setImageResource(R.drawable.man_);
            else
                iv_sex.setImageResource(R.drawable.woman);
            if (bd.IDType == null)
                tv_card_type.setText("身份证");
            else if (bd.IDType.split(" ")[i].equals("1")) {
                tv_card_type.setText("身份证");
            } else {
                tv_card_type.setText("其他证件");
            }
            tv_card_num.setText(bd.LoanCard.split(" ")[i]);
            String[] strs = new String[15];
            strs[0] = bd.Education;
            strs[1] = bd.Health;
            strs[2] = bd.Fixed_Phone;
            strs[3] = "￥" + String.format("%.2f", bd.LoanMone);
            strs[4] = bd.LoanApplication;
            strs[5] = bd.Job;
            strs[6] = bd.Unit;
            strs[7] = bd.Job_Title;
            strs[8] = bd.Professional;
            strs[9] = bd.SourceOfIncome;
            strs[10] = "￥" + String.format("%.2f", bd.Month_Revenues);
            strs[11] = "￥" + String.format("%.2f", bd.Year_Revenues);
            strs[12] = "￥" + String.format("%.2f", bd.Family_Year_Revenues);
            strs[13] = "￥" + String.format("%.2f", bd.Family_Year_Expenses);
            strs[14] = "￥" + String.format("%.2f", bd.Other_Revenues);
            initListContent(strs);
            personalInfoAdapter.refresh(personalInfos);
            jkbInfoAdapter.refresh(jkbInfos);
            workAdapter.refresh(works);
        }
    }
}
