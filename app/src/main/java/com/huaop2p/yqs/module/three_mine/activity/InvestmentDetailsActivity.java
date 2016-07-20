package com.huaop2p.yqs.module.three_mine.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.contant.HttpConnector;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.view.ScrollAbleFragment;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.three_mine.model.entity.Investment;
import com.huaop2p.yqs.module.three_mine.presenter.impl.InvestmentDetailsPresenter;
import com.huaop2p.yqs.module.three_mine.view.IInVestmentView;
import com.huaop2p.yqs.module.three_mine.view.fragment.BorrowerFragment;
import com.huaop2p.yqs.module.three_mine.view.fragment.LendListFragment;
import com.huaop2p.yqs.module.three_mine.view.fragment.PaymentHistoryFragment;
import com.huaop2p.yqs.module.three_mine.view.fragment.PersonalDataFragment;
import com.huaop2p.yqs.module.two_wealth.adapter.WealthCenterAdapter;
import com.huaop2p.yqs.module.two_wealth.assist.BaseCalculator;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/5/9.
 * 作者：任洋
 * 功能：投资详情
 */
public class InvestmentDetailsActivity extends BaseAutoActivity<Investment> implements PullToRefreshLayout.OnRefreshListener, IInVestmentView {

    private CustomTextView c_tv;
    private CostomProgress cp;
    private ViewPager vp;
    private TabPageIndicator indicator;
    private ScrollableLayout mScrollLayout;
    private PullToRefreshLayout refreshLayout;
    private List<ScrollAbleFragment> fragments;
    private int index;
    private int type;
    private String ddh;
    String[] titles = null;
    private int color;
    private int color2;
    private MyTextView tv_flag;
    private TextView tv_title, tv_inverstment_time, tv_qixi_date, tv_red_name, tv_expire_date, tv_date, tv_day, tv_InvestmentMoney, tv_YearRate, tv_OtherIncome, tv_investment_date, tv_sy_day, tv_red_value1, tv_red_value2, tv_red_value3;
    private InvestmentDetailsPresenter presenter;
    private Map<String, Object> paramMap;
    private int drawableRes;
    private Investment in;
    private boolean isHistory;
    private Button btn_transfer;
    private String url;

    @Override
    public int getLayoutId() {
        return R.layout.activity_investment_details;
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
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_qixi_date = (TextView) findViewById(R.id.tv_qixi_date);
        tv_expire_date = (TextView) findViewById(R.id.tv_expire_date);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_day = (TextView) findViewById(R.id.tv_day);
        tv_InvestmentMoney = (TextView) findViewById(R.id.tv_InvestmentMoney);
        tv_YearRate = (TextView) findViewById(R.id.tv_YearRate);
        tv_OtherIncome = (TextView) findViewById(R.id.tv_OtherIncome);
        tv_investment_date = (TextView) findViewById(R.id.tv_investment_date);
        btn_transfer = (Button) findViewById(R.id.btn_transfer);
        tv_sy_day = (TextView) findViewById(R.id.tv_sy_day);
        tv_red_value1 = (TextView) findViewById(R.id.tv_red_value1);
        tv_red_value2 = (TextView) findViewById(R.id.tv_red_value2);
        tv_red_value3 = (TextView) findViewById(R.id.tv_red_value3);
        tv_red_name = (TextView) findViewById(R.id.tv_red_name);
        tv_inverstment_time = (TextView) findViewById(R.id.tv_inverstment_time);
    }

    @Override
    public Object getObject() {
        return this;
    }

    @Override
    public void initData() {
        type = getIntent().getIntExtra("type", State.YUXINBAO);
        isHistory = getIntent().getBooleanExtra("isHistory", false);
        ddh = getIntent().getStringExtra("DDH");
        paramMap = new HashMap<>();
        presenter = new InvestmentDetailsPresenter(this);
        paramMap.put("DDH", ddh);
        paramMap.put("Type", type);
        presenter.loadDetail(paramMap, HttpUrl.GET_INVERSTMENT_RECORD, 0, RequestMethod.POST);
        initContentByType();
        initCp();
        initFragmentPager(vp, indicator, mScrollLayout);
        c_tv.setText("0.000", ".", color);
        tv_flag.setBackgroundColor(color);
        tv_day.setTextColor(color);
        tv_date.setTextColor(color);
        if (isHistory) {
            btn_transfer.setVisibility(View.GONE);
            tv_day.setVisibility(View.INVISIBLE);
            tv_sy_day.setText("已收益");
        }

        HashMap<String, Object> child = new HashMap<String, Object>();
        child.put("DDH", ddh);
        presenter.getContract(child, HttpUrl.GET_CONTRACT, 0, RequestMethod.POST);
    }

    private void initCp() {
        cp.setStyle(0);
        cp.setTextColor(getResources().getColor(R.color.black));
        cp.setBackgroundColorSelect(color);
        cp.setBackgroundColorDefault(color2);
        cp.setTextSize(40);
        cp.setProgress(0);
    }

    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // 千万别忘了告诉控件刷新完毕了哦！
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 1000);
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
                bundle.putString("DDH", ddh);
                fragment.setArguments(bundle);
                fragments.add(fragment);
                fragment = new LendListFragment();
                bundle = new Bundle();
                bundle.putString("DDH", ddh);
                fragment.setArguments(bundle);
                fragments.add(fragment);
                //    fragments.add(new WealthModelFragment());
                titles = new String[]{"借款人", "循环出借列表"};
                color = Color.RED;
                color2 = getResources().getColor(R.color.pink);
                drawableRes = R.drawable.vpi__tab_selected_holo;
                tv_investment_date.setText("封闭期");
                break;
            case State.YUCHEBAO:
                fragment = new PersonalDataFragment();
                bundle = new Bundle();
                bundle.putString("DDH", ddh);
                bundle.putString("KeyId", getIntent().getStringExtra("keyId"));
                fragment.setArguments(bundle);
                fragments.add(fragment);
                fragment = new PaymentHistoryFragment();
                bundle = new Bundle();
                bundle.putString("DDH", ddh);
                bundle.putInt("type", type);
                fragment.setArguments(bundle);
                fragments.add(fragment);
                // fragments.add(new WealthModelFragment());
                titles = new String[]{"借款信息", "还款记录"};
                color = getResources().getColor(R.color.yuchebao);
                color2 = getResources().getColor(R.color.yuchebao2);
                drawableRes = R.drawable.vpi__tab_selected_holo_che;
                tv_investment_date.setText("期限");
                break;
            case State.YUFANGBAO:
                fragment = new PersonalDataFragment();
                bundle = new Bundle();
                bundle.putString("DDH", ddh);
                bundle.putString("KeyId", getIntent().getStringExtra("keyId"));
                fragment.setArguments(bundle);
                fragments.add(fragment);
                fragment = new PaymentHistoryFragment();
                bundle = new Bundle();
                bundle.putString("DDH", ddh);
                bundle.putInt("type", type);
                fragment.setArguments(bundle);
                fragments.add(fragment);
                //    fragments.add(new WealthModelFragment());
                titles = new String[]{"借款信息", "还款记录"};
                color = getResources().getColor(R.color.yufangbao);
                color2 = getResources().getColor(R.color.yufangbao2);
                drawableRes = R.drawable.vpi__tab_selected_holo_fang;
                tv_investment_date.setText("期限");
                break;
        }

    }

    public void selectIndicator(int position) {
        indicator.clearHeadTextColor(R.color.vpi_title_text_color);
        TextView tv = (TextView) indicator.getPositionView(position);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimensionPixelSize(R.dimen.select_text_content_size));
        tv.setTextColor(color);
        tv.setBackgroundResource(drawableRes);
        AutoUtils.autoTextSize(tv);
    }

    public void transfer(View view) {
        ToastUtils.show(AppApplication.mContext, "投资期满3个月后才能转让！");
//        if (in == null)
//            return;
//        Intent intent = new Intent(this, TransferApplicationActivity.class);
//        startActivity(intent);
//        EventBus.getDefault().postSticky(in);
    }

    @Override
    public void showSuccess(Investment in) {
        this.in = in;
        double value = in.TrueRate * Double.valueOf(in.LoanMoney);      //每天获得的钱
        float NowDay = 0;
        float totalDay = 0;
        double total_shouyi = 0;
        int surplus = 0;
        if (!(in.StartTime == null || in.DateTimeNow == null || in.EndTime == null)) {
            try {
                Date dateStart = DateUtils.sdf.parse(in.StartTime);
                Date dateNow = DateUtils.sdf.parse(in.DateTimeNow);
                Date dateEnd = DateUtils.sdf.parse(in.EndTime);
                totalDay = DateUtils.subtract1(dateEnd, dateStart);
                if (dateNow.getTime() <= dateEnd.getTime())
                    NowDay = DateUtils.subtract1(dateNow, dateStart);
                else
                    NowDay = DateUtils.subtract1(dateEnd, dateStart);
                surplus = DateUtils.subtract(dateEnd, dateNow);
                total_shouyi = BaseCalculator.getClaculator().divide(BaseCalculator.getClaculator().multiply(NowDay, value), totalDay);
                if (surplus < 0)
                    surplus = 0;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tv_qixi_date.setText(DateUtils.StringToDateString(in.StartTime));
            tv_expire_date.setText(DateUtils.StringToDateString(in.EndTime));
            tv_day.setText(surplus + "天");
        } else {
            tv_qixi_date.setText("待定");
            tv_expire_date.setText("待定");
            tv_day.setText("未起息");
            totalDay = -1;
            surplus=-1;
        }
        if (surplus == 0) {
            cp.setProgress(100, 100);
        } else {
            cp.setProgress(NowDay, totalDay);
        }

        if (isHistory)
            c_tv.setText(String.format("%.4f", total_shouyi), ".", getResources().getColor(R.color.textcolor));
        else
            c_tv.setText(String.format("%.4f", total_shouyi), ".", color);
        tv_title.setText(in.ProductName);
        if (!isHistory && in.ProductName.equals("季度红"))
            btn_transfer.setVisibility(View.VISIBLE);
        tv_flag.setBackgroundColor(color);
        switch (type) {
            case State.YUFANGBAO:
                tv_flag.setText("房");
                break;
            case State.YUCHEBAO:
                tv_flag.setText("车");
                break;
            case State.YUXINBAO:
                tv_flag.setText("信");
                break;
        }
        String suffix = null;
        int total = 0;
        switch (in.LoanTermId) {
            case State.DAY:
                suffix = "天";
                totalDay = in.LoanTerm;
                break;
            case State.MONTH:
                suffix = "个月";
                totalDay = in.LoanTerm * 30;
                break;
            case State.YEAR:
                suffix = "年";
                totalDay = in.LoanTerm * 30 * 12;
                break;
        }
        tv_date.setText(in.LoanTerm + suffix);
        tv_InvestmentMoney.setText(String.format("%.2f", Double.valueOf(in.LoanMoney)) + "元");
        tv_YearRate.setText(in.LoanRate);
        StringBuffer sb = new StringBuffer();
        tv_OtherIncome.setText(String.format("%.2f", BaseCalculator.getClaculator().multiply(Double.valueOf(in.LoanMoney), Double.valueOf(in.TrueRate))) + "元");
        switch (in.RedType) {
            case 1:
                sb.append("含").append(in.RedMoney).append("元");
                tv_red_value1.setText(sb);
                sb.delete(0, sb.length());
                sb.append(in.RedMoney).append("元抵现红包");
                tv_red_name.setText(sb);
                break;
            case 2:
                sb.append("含").append(in.RedMoney).append("元");
                tv_red_value3.setText(sb);
                sb.delete(0, sb.length());
                sb.append(in.RedMoney).append("元返现红包");
                tv_red_name.setText(sb);
                tv_OtherIncome.setText(String.format("%.2f", BaseCalculator.getClaculator().multiply(Double.valueOf(in.LoanMoney), Double.valueOf(in.TrueRate)) + Float.valueOf(in.RedMoney)) + "元");
                break;
            case 3:
                sb.append("含").append(in.RedMoney).append("元");
                tv_red_value1.setText(sb);
                sb.delete(0, sb.length());
                sb.append(in.RedMoney).append("元满减红包");
                tv_red_name.setText(sb);
                break;
            case 4:
                sb.append("\u002B").append(Float.valueOf(in.RedMoney)).append("%");
                tv_red_value2.setText(sb);
                sb.delete(0, sb.length());
                sb.append(Float.valueOf(in.RedMoney)).append("%加息券");
                tv_red_name.setText(sb);
                break;
            default:
                tv_red_name.setText("未使用");
                break;
        }
        tv_inverstment_time.setText(in.BuyTime);
    }

    public void startWeb(View view) {
        if (url == null) {
            ToastUtils.show(AppApplication.mContext, "合同正在获取...");
            return;
        }
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("title", "电子合同");
        intent.putExtra("url", url);
        startActivity(intent);
    }

    @Override
    public void getContract(String str) {
        if (!str.equals("")) {
            Pattern p = Pattern.compile("^(http|www|ftp|https|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(str);
            if (!m.find()) {
                str = HttpConnector.HTTT_HOST + "/" + str;
            }
            url = str;

        }
    }
}
