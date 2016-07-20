package com.huaop2p.yqs.module.three_mine.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.view.ScrollAbleFragment;
import com.huaop2p.yqs.module.three_mine.model.entity.TransferRecord;
import com.huaop2p.yqs.module.three_mine.presenter.impl.TransferRecordDetailsPresenterImpl;
import com.huaop2p.yqs.module.three_mine.view.ITransferRecordDetailsView;
import com.huaop2p.yqs.module.three_mine.view.fragment.BorrowerFragment;
import com.huaop2p.yqs.module.three_mine.view.fragment.LendListFragment;
import com.huaop2p.yqs.module.three_mine.view.fragment.PaymentHistoryFragment;
import com.huaop2p.yqs.module.three_mine.view.fragment.PersonalDataFragment;
import com.huaop2p.yqs.module.two_wealth.adapter.WealthCenterAdapter;
import com.huaop2p.yqs.module.two_wealth.view.fragment.WealthModelFragment;
import com.huaop2p.yqs.utils.DateUtils;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.utils.auto.AutoUtils;
import com.huaop2p.yqs.widget.CostomProgress;
import com.huaop2p.yqs.widget.CustomTextView;
import com.huaop2p.yqs.widget.MyTextView;
import com.huaop2p.yqs.widget.TabPageIndicator;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshLayout;
import com.huaop2p.yqs.widget.scroll.ScrollableLayout;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/5/13.
 * 作者：任洋
 * 功能：转让记录详情
 */
public class TransferRecordDetailsActivity extends BaseAutoActivity implements PullToRefreshLayout.OnRefreshListener, ITransferRecordDetailsView<TransferRecord>,View.OnClickListener {
    private CustomTextView c_tv;
    private CostomProgress cp;
    private ViewPager vp;
    private TabPageIndicator indicator;
    private ScrollableLayout mScrollLayout;
    private PullToRefreshLayout refreshLayout;
    private List<ScrollAbleFragment> fragments;
    private int index;
    private int type;
    public String DDH;
    String[] titles = null;
    private int color;
    private int color2;
    private MyTextView tv_flag;
    private TextView tv_title, tv_date, tv_day, tv_qixi_date, tv_expire_date, tv_InvestmentMoney, tv_YearRate, tv_OtherIncome, tv_BuyTime, tv_transfer_type, tv_DropRate, tv_AccrualMoney, top_title;
    private Button btn_cancel;
    private int drawableRes;
    private TransferRecord transferRecord;
    private LinearLayout ll_flag;
    private TransferRecordDetailsPresenterImpl presenter;
    private HashMap<String, Object> map;
    private String title;
    @Override
    public Object getObject() {
        return this;
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_transfer_record_details;
    }

    @Override
    public void initViews() {
        c_tv = (CustomTextView) findViewById(R.id.c_tv);
        cp = (CostomProgress) findViewById(R.id.cp);
        vp = (ViewPager) findViewById(R.id.vp);
        indicator = (TabPageIndicator) findViewById(R.id.indictor);
        mScrollLayout = (ScrollableLayout) findViewById(R.id.scrollableLayout);
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        refreshLayout.setOnRefreshListener(this);
        tv_flag = (MyTextView) findViewById(R.id.tv_flag);
        top_title = (TextView) findViewById(R.id.top_title);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_day = (TextView) findViewById(R.id.tv_day);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        tv_qixi_date = (TextView) findViewById(R.id.tv_qixi_date);
        tv_expire_date = (TextView) findViewById(R.id.tv_expire_date);
        tv_InvestmentMoney = (TextView) findViewById(R.id.tv_InvestmentMoney);
        tv_YearRate = (TextView) findViewById(R.id.tv_YearRate);
        tv_OtherIncome = (TextView) findViewById(R.id.tv_OtherIncome);
        tv_BuyTime = (TextView) findViewById(R.id.tv_BuyTime);
        tv_transfer_type = (TextView) findViewById(R.id.tv_transfer_type);
        tv_DropRate = (TextView) findViewById(R.id.tv_DropRate);
        tv_AccrualMoney = (TextView) findViewById(R.id.tv_AccrualMoney);
        ll_flag = (LinearLayout) findViewById(R.id.ll_flag);
    }

    @Override
    public void initData() {
        type = getIntent().getIntExtra("type", State.YUXINBAO);
        DDH = getIntent().getStringExtra("DDH");
        title = getIntent().getStringExtra("title");
        presenter = new TransferRecordDetailsPresenterImpl(this);
        map = new HashMap<String, Object>();
        map.put("KeyId", getIntent().getStringExtra("keyId"));
        presenter.loadDetail(map, HttpUrl.GET_TRANSFER_DETAILS, 0, RequestMethod.POST);
        initContentByType();
        initCp();
        initFragmentPager(vp, indicator, mScrollLayout);
        c_tv.setText("0.0", ".", color);
        tv_flag.setBackgroundColor(color);
        tv_date.setTextColor(color);
        tv_day.setTextColor(color);
        btn_cancel.setBackgroundColor(color);
    }

    private void initCp() {
        cp.setStyle(0);
        cp.setTextColor(getResources().getColor(R.color.black));
        cp.setBackgroundColorSelect(color);
        cp.setBackgroundColorDefault(color2);
        cp.setTextSize(60);
        cp.setProgress(0);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        presenter.loadDetail(map, HttpUrl.GET_TRANSFER_DETAILS, 0, RequestMethod.POST);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

    }

    public void initFragmentPager(ViewPager viewPager, TabPageIndicator indicator, final ScrollableLayout mScrollLayout) {
        vp.setAdapter(new WealthCenterAdapter(getSupportFragmentManager(), fragments, titles));
        vp.setOffscreenPageLimit(2);
        indicator.setViewPager(vp);
        /** 标注当前页面,从而ScrollableLayout可以从该ListFragment取到当前嵌套的listview,剩下的就交给ScrollableLayout去处理了 **/
        mScrollLayout.getHelper().setCurrentScrollableContainer(fragments.get(0));
        selectIndicator(0);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                /** 页面切换时,标注当前页面,从而ScrollableLayout可以从该ListFragment取到当前嵌套的listview,剩下的就交给ScrollableLayout去处理了 **/
                mScrollLayout.getHelper().setCurrentScrollableContainer(fragments.get(i));
                selectIndicator(i);
                index = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewPager.setCurrentItem(0);
    }

    private void initContentByType() {
        fragments = new ArrayList<>();
        ScrollAbleFragment fragment = null;
        switch (type) {
            case State.YUXINBAO:
                fragment = new BorrowerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("DDH", DDH);
                fragment.setArguments(bundle);
                fragments.add(fragment);
                fragment = new LendListFragment();
                bundle = new Bundle();
                bundle.putString("DDH", DDH);
                fragment.setArguments(bundle);
                fragments.add(fragment);
                fragments.add(new PaymentHistoryFragment());
                titles = new String[]{"借款人", "循环出借列表", "电子合同"};
                color = Color.RED;
                color2 = getResources().getColor(R.color.pink);
                drawableRes = R.drawable.vpi__tab_selected_holo;
                break;
            case State.YUCHEBAO:
                fragment = new PersonalDataFragment();
                bundle = new Bundle();
                bundle.putString("KeyId", getIntent().getStringExtra("StandardId"));
                fragment.setArguments(bundle);
                fragments.add(fragment);
                fragment = new PaymentHistoryFragment();
                bundle = new Bundle();
                bundle.putString("DDH", DDH);
                bundle.putInt("type", type);
                fragment.setArguments(bundle);
                fragments.add(fragment);
                fragments.add(new WealthModelFragment());
                titles = new String[]{"借款信息", "还款记录", "电子合同"};
                color = getResources().getColor(R.color.yuchebao);
                color2 = getResources().getColor(R.color.yuchebao2);
                drawableRes = R.drawable.vpi__tab_selected_holo_che;
                break;
            case State.YUFANGBAO:
                fragment = new PersonalDataFragment();
                bundle = new Bundle();
                bundle.putString("KeyId", getIntent().getStringExtra("StandardId"));
                fragment.setArguments(bundle);
                fragments.add(fragment);
                fragment = new PaymentHistoryFragment();
                bundle = new Bundle();
                bundle.putString("DDH", DDH);
                bundle.putInt("type", type);
                fragment.setArguments(bundle);
                fragments.add(fragment);
                fragments.add(new WealthModelFragment());
                titles = new String[]{"借款信息", "还款记录", "电子合同"};
                color = getResources().getColor(R.color.yufangbao);
                color2 = getResources().getColor(R.color.yufangbao2);
                drawableRes = R.drawable.vpi__tab_selected_holo_fang;
                break;
        }
        top_title.setText(title);
        if (title.equals("已转让"))
            btn_cancel.setVisibility(View.GONE);
    }

    public void selectIndicator(int position) {
        indicator.clearHeadTextColor(R.color.vpi_title_text_color);
        TextView tv = (TextView) indicator.getPositionView(position);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimensionPixelSize(R.dimen.select_text_content_size));
        tv.setTextColor(color);
        tv.setBackgroundResource(drawableRes);
    }

    @Override
    public void loadDetails(TransferRecord transferRecord) {
        this.transferRecord = transferRecord;
        c_tv.setText(String.format("%.2f", transferRecord.CurrentMoney), ".", color);
        tv_title.setText(transferRecord.Name);
        tv_flag.setBackgroundColor(color);
        tv_flag.setText(transferRecord.YupenProductInco.Font);
        tv_qixi_date.setText(DateUtils.StringToDateString(transferRecord.InvestmentStartTime));
        tv_expire_date.setText(DateUtils.StringToDateString(transferRecord.InvestmentEndTime));
        String suffix = null;
        int totalDay = 0;
        switch (transferRecord.InvestmentTermType) {
            case State.DAY:
                suffix = "天";
                totalDay = transferRecord.InvestmentTerm;
                break;
            case State.MONTH:
                suffix = "个月";
                totalDay = transferRecord.InvestmentTerm * 30;
                break;
            case State.YEAR:
                suffix = "年";
                totalDay = transferRecord.InvestmentTerm * 30 * 12;
                break;
        }
        tv_date.setText(transferRecord.InvestmentTerm + suffix);
        tv_day.setText(transferRecord.OtherDay + "天");
        if ( transferRecord.OtherDay<0){
            cp.setProgress(100, 100);
        }else{
            cp.setProgress(totalDay - transferRecord.OtherDay, totalDay);
        }
        tv_InvestmentMoney.setText(String.format("%.2f", Double.valueOf(transferRecord.InvestmentMoney)) + "元");
        tv_YearRate.setText(transferRecord.YearRate + "%");
        tv_OtherIncome.setText(String.format("%.2f", Double.valueOf(transferRecord.OtherIncome)) + "元");
        tv_BuyTime.setText(transferRecord.BuyTime);
        switch (transferRecord.Statetype) {
            case State.TRANSFER_JIAJI:
                tv_transfer_type.setText("加急");
                break;
            case State.TRANSFER_NORMAL:
                tv_transfer_type.setText("正常");
                break;
        }
        tv_DropRate.setText(String.format("%.2f", transferRecord.DropRate) + "%");
        tv_AccrualMoney.setText(transferRecord.AccrualMoney);

        ll_flag.removeAllViews();
        MyTextView textView = new MyTextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);
        textView.setLayoutParams(layoutParams);
        textView.setText(title);
        textView.setPadding(10, 10, 10, 10);
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundColor(getResources().getColor(R.color.h_b_bg));
        AutoUtils.auto(textView);
        ll_flag.addView(textView, 0);
    }

    @Override
    public void cancelTransfer(String string) {
        if (string==null){
            ToastUtils.show(this,"取消成功");
            finish();
        }
    }

    @Override
    public void loadDataOver() {
        refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:
                presenter.delTransferRecordById(map);
                break;
        }
    }
}
