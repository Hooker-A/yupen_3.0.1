package com.huaop2p.yqs.module.two_wealth.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.view.ScrollAbleFragment;
import com.huaop2p.yqs.module.three_mine.view.fragment.PersonalDataFragment;
import com.huaop2p.yqs.module.two_wealth.adapter.WealthCenterAdapter;
import com.huaop2p.yqs.module.two_wealth.assist.BaseCalculator;
import com.huaop2p.yqs.module.two_wealth.model.entity.ProductRate;
import com.huaop2p.yqs.module.two_wealth.model.entity.Style;
import com.huaop2p.yqs.module.two_wealth.model.entity.Transfer;
import com.huaop2p.yqs.module.two_wealth.presenter.impl.TransferDetailsPresenterImpl;
import com.huaop2p.yqs.module.two_wealth.view.ITransferView;
import com.huaop2p.yqs.utils.DateUtils;
import com.huaop2p.yqs.utils.ResourceUtil;
import com.huaop2p.yqs.utils.auto.AutoUtils;
import com.huaop2p.yqs.widget.CostomProgress;
import com.huaop2p.yqs.widget.CustomTextView;
import com.huaop2p.yqs.widget.MyTextView;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshLayout;
import com.huaop2p.yqs.widget.scroll.ScrollableLayout;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/5/12.
 * 作者：任洋
 * 功能：转让社区详情
 */
public class TransferDetailsActivity extends BaseAutoActivity<Transfer> implements ITransferView, PullToRefreshLayout.OnRefreshListener {
    private Transfer transfer;
    private TextView tv_name,
            tv_shengyu, caifutong_value,
            yuebao_value, tv_regular_rate_value, tv_current_interest_rate_value,
            tv_shouyilv_value,
            tv_shouyilv,tv_wd_benjin,tv_bottom_title,tv_Income_description,
            tv_BuyTime,
            tv_left, tv_right,tv_wd_shouyi,
            dosage, //已投金额
            residual_amount; //剩余金额
    private ViewPager vp;
    private CustomTextView tv_gains, tv_date;
    private CostomProgress progressBar2, progressBar3, progressBar4, progressBar5, progressBar6;
    private LinearLayout ll_flag;
    private Handler handler = new Handler();
    private ScrollableLayout mScrollLayout;
    private PullToRefreshLayout refreshLayout;
    private final static int TIME = 10;
    private TransferDetailsPresenterImpl presenter;
    private int type;  //区分余xx类型
    private String[] titles = null;
    private int textColor;
    private int select_Bg;
    private List<ScrollAbleFragment> fragments;
    private String keyId,StandardId;
    private HashMap<String,Object> map;
    private Button btn_bottom;
    @Override
    public int getLayoutId() {
        return R.layout.activity_transfer_details;
    }
    @Override
    public Object getObject() {
        return this;
    }
    @Override
    public void initViews() {
        tv_gains = (CustomTextView) findViewById(R.id.tv_gains);
        tv_date = (CustomTextView) findViewById(R.id.tv_date);
        tv_shengyu = (TextView) findViewById(R.id.shengyu);
        tv_shouyilv_value = (TextView) findViewById(R.id.tv_shouyilv_value);
        tv_shouyilv = (TextView) findViewById(R.id.tv_shouyilv);
        tv_bottom_title= (TextView) findViewById(R.id.tv_bottom_title);
        btn_bottom= (Button) findViewById(R.id.btn_bottom);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_Income_description= (TextView) findViewById(R.id.tv_Income_description);
        progressBar2 = (CostomProgress) findViewById(R.id.progressBar2);
        progressBar3 = (CostomProgress) findViewById(R.id.progressBar3);
        progressBar4 = (CostomProgress) findViewById(R.id.progressBar4);
        progressBar5 = (CostomProgress) findViewById(R.id.progressBar5);
        progressBar6 = (CostomProgress) findViewById(R.id.progressBar6);
        caifutong_value = (TextView) findViewById(R.id.caifutong_value);
        yuebao_value = (TextView) findViewById(R.id.yuebao_value);
        tv_regular_rate_value = (TextView) findViewById(R.id.tv_regular_rate_value);
        tv_current_interest_rate_value = (TextView) findViewById(R.id.tv_current_interest_rate_value);
        tv_name = (TextView) findViewById(R.id.tv_name);
        dosage = (TextView) findViewById(R.id.dosage);
        residual_amount = (TextView) findViewById(R.id.residual_amount);
        tv_wd_shouyi= (TextView) findViewById(R.id.tv_wd_shouyi);
        tv_wd_benjin= (TextView) findViewById(R.id.tv_wd_benjin);
        ll_flag = (LinearLayout) findViewById(R.id.ll_flag);
        vp = (ViewPager) findViewById(R.id.vp);
        tv_BuyTime= (TextView) findViewById(R.id.tv_BuyTime);
        mScrollLayout = (ScrollableLayout) findViewById(R.id.scrollableLayout);
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        refreshLayout.setOnRefreshListener(this);

    }

    public void initFragmentPager(ViewPager viewPager, final ScrollableLayout mScrollLayout) {
        vp.setAdapter(new WealthCenterAdapter(getSupportFragmentManager(), fragments, titles));
        /** 标注当前页面,从而ScrollableLayout可以从该ListFragment取到当前嵌套的listview,剩下的就交给ScrollableLayout去处理了 **/
        mScrollLayout.getHelper().setCurrentScrollableContainer(fragments.get(0));
        viewPager.setCurrentItem(0);
    }

    public void initContentByType() {
        fragments = new ArrayList<>();
        switch (type) {
            case State.YUXINBAO:
                textColor = getResources().getColor(R.color.red);
                titles = new String[]{"原始借贷信息"};
                ScrollAbleFragment fragment = new PersonalDataFragment();
                Bundle bundle = new Bundle();
                bundle.putString("KeyId", StandardId);
                bundle.putInt("color", textColor);
                bundle.putBoolean("flag",true);
                fragment.setArguments(bundle);
                fragments.add(fragment);
                break;
            case State.YUFANGBAO:
                textColor = getResources().getColor(R.color.yufangbao);
                titles = new String[]{"原始借贷信息"};
                fragment = new PersonalDataFragment();
                bundle = new Bundle();
                bundle.putString("KeyId", StandardId);
                bundle.putInt("color", textColor);
                bundle.putBoolean("flag", true);
                fragment.setArguments(bundle);
                fragments.add(fragment);
                break;
            case State.YUCHEBAO:
                textColor = getResources().getColor(R.color.yuchebao);
                titles = new String[]{"原始借贷信息"};
                fragment = new PersonalDataFragment();
                bundle = new Bundle();
                bundle.putString("KeyId", StandardId);
                bundle.putInt("color", textColor);
                bundle.putBoolean("flag",true);
                fragment.setArguments(bundle);
                fragments.add(fragment);
                break;
        }
        tv_name.setTextColor(textColor);
        tv_shouyilv.setTextColor(textColor);
        tv_bottom_title.setTextColor(textColor);
        tv_shouyilv_value.setTextColor(textColor);
        btn_bottom.setBackgroundColor(textColor);
    }

    @Override
    public void initData() {
        type = getIntent().getIntExtra("type", State.YUXINBAO);
        keyId = getIntent().getStringExtra("keyId");
        StandardId= getIntent().getStringExtra("StandardId");
        presenter=new TransferDetailsPresenterImpl(this);
        map=new HashMap<>();
        map.put("KeyId",keyId);
        presenter.loadDetail(map, HttpUrl.TRANSFER_DETAILS,0, RequestMethod.POST);
        progressBar2.setStyle(CostomProgress.STYLE2);
        progressBar3.setStyle(CostomProgress.STYLE2);
        progressBar4.setStyle(CostomProgress.STYLE2);
        progressBar5.setStyle(CostomProgress.STYLE2);
        progressBar6.setStyle(CostomProgress.STYLE2);
        initContentByType();
        initFragmentPager(vp, mScrollLayout);

    }

    /***
     * enevtBus 传值给view赋值
     **/
    public void onEventMainThread(Transfer transfer) {
        this.transfer = transfer;
        setView(this.transfer);
    }

    @Override
    public void setView(Transfer transfer) {
        tv_name.setText(transfer.Name);
        tv_gains.setText(String.valueOf(transfer.YearRate) + "+" + transfer.DropRate + "%", "+",textColor);
        tv_date.setText(transfer.OtherDay + "天", "", textColor);
        if (transfer.YupenProductInco != null) {
            for (int i = 0; i < transfer.YupenProductInco.size(); i++) {
                Style style = transfer.YupenProductInco.get(i);
                MyTextView textView = new MyTextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10, 10, 10, 10);
                textView.setLayoutParams(layoutParams);
                textView.setText(style.Font);
                textView.setPadding(10, 10, 10, 10);
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundColor(textColor);
                AutoUtils.auto(textView);
                ll_flag.addView(textView, 0);
            }
        }
        tv_BuyTime.setText(DateUtils.StringToDateString(transfer.BuyTime));
        residual_amount.setText(String.format("%.2f", Double.valueOf(transfer.TransferIncome)));
        tv_wd_benjin.setText(String.format("%.2f", transfer.InvestmentMoney));
        dosage.setText(String.format("%.2f", Double.valueOf(transfer.TransferMoney)));
        dosage.setTextColor(textColor);
        residual_amount.setTextColor(textColor);
        tv_wd_shouyi.setText(String.format("%.2f", transfer.OtherIncome));
        progressBar2.setBackgroundColorSelect(textColor);
        progressBar3.setBackgroundColorSelect(getResources().getColor(R.color.textcolor));
        progressBar4.setBackgroundColorSelect(getResources().getColor(R.color.textcolor));
        progressBar5.setBackgroundColorSelect(getResources().getColor(R.color.textcolor));
        progressBar6.setBackgroundColorSelect(getResources().getColor(R.color.textcolor));
        float arg1 =Float.valueOf(transfer.YearRate)+Float.valueOf(transfer.DropRate);
        progressBar2.setProgress(arg1);
        tv_shouyilv_value.setText(String.format("%.2f", arg1) + "%");
        if (transfer.ProductsRate != null) {
            for (int i = 0; i < transfer.ProductsRate.size(); i++) {
                ProductRate productRate = transfer.ProductsRate.get(i);
                if (productRate.Name.equals(ResourceUtil.getString(getResources(), R.string.Money_paid_through_rate_of_return))) {
                    progressBar5.setProgress(BaseCalculator.getClaculator().multiply(productRate.LoanRate, 100f));
                    caifutong_value.setText(String.format("%.2f", BaseCalculator.getClaculator().multiply(productRate.LoanRate, 100f)) + "%");
                } else if (productRate.Name.equals(ResourceUtil.getString(getResources(), R.string.balance_treasure_yield))) {
                    progressBar6.setProgress(BaseCalculator.getClaculator().multiply(productRate.LoanRate, 100f));
                    yuebao_value.setText(String.format("%.2f", BaseCalculator.getClaculator().multiply(productRate.LoanRate, 100f)) + "%");
                } else if (productRate.Name.equals(ResourceUtil.getString(getResources(), R.string.regular_rate_of_bank_interest_rates))) {
                    progressBar3.setProgress(BaseCalculator.getClaculator().multiply(productRate.LoanRate, 100f));
                    tv_regular_rate_value.setText(String.format("%.2f", BaseCalculator.getClaculator().multiply(productRate.LoanRate, 100f)) + "%");
                } else if (productRate.Name.equals(ResourceUtil.getString(getResources(), R.string.bank_current_interest_rate))) {
                    progressBar4.setProgress(BaseCalculator.getClaculator().multiply(productRate.LoanRate, 100f));
                    tv_current_interest_rate_value.setText(String.format("%.2f", BaseCalculator.getClaculator().multiply(productRate.LoanRate, 100f)) + "%");
                }
            }
        }
    }

    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        // 下拉刷新操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // 千万别忘了告诉控件刷新完毕了哦！
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        // 加载操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // 千万别忘了告诉控件加载完毕了哦！
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }
    public void buy(View view){
        if (transfer == null)
            return;
        Intent intent = new Intent(this, InvestmentActivity.class);
        intent.putExtra("type", 4);
        intent.putExtra("name", tv_name.getText().toString());
        intent.putExtra("investmentDate", tv_date.getText().toString());
        intent.putExtra("jixiWay","");
        intent.putExtra("keyId", keyId);
        intent.putExtra("TransferMoney", transfer.TransferMoney);
        intent.putExtra("TransferIncome", transfer.TransferIncome);
        startActivity(intent);
    }

    @Override
    public void showSuccess(Transfer transfer) {
        this.transfer=transfer;
        setView(transfer);
    }
}
