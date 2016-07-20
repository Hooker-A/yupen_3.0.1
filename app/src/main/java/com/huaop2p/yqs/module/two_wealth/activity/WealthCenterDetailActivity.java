package com.huaop2p.yqs.module.two_wealth.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.view.ScrollAbleFragment;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.logon.activity.LogonActivity;
import com.huaop2p.yqs.module.three_mine.view.fragment.PersonalDataFragment;
import com.huaop2p.yqs.module.two_wealth.adapter.WealthCenterAdapter;
import com.huaop2p.yqs.module.two_wealth.assist.BaseCalculator;
import com.huaop2p.yqs.module.two_wealth.model.entity.ProductRate;
import com.huaop2p.yqs.module.two_wealth.model.entity.Style;
import com.huaop2p.yqs.module.two_wealth.model.entity.WealthCenter;
import com.huaop2p.yqs.module.two_wealth.presenter.impl.WealthCenterDetailsPresenterImpl;
import com.huaop2p.yqs.module.two_wealth.view.IWealthDetailView;
import com.huaop2p.yqs.module.two_wealth.view.fragment.WealthModelFragment;
import com.huaop2p.yqs.utils.ResourceUtil;
import com.huaop2p.yqs.utils.auto.AutoUtils;
import com.huaop2p.yqs.widget.CostomProgress;
import com.huaop2p.yqs.widget.CustomTextView;
import com.huaop2p.yqs.widget.MyTextView;
import com.huaop2p.yqs.widget.TabPageIndicator;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshLayout;
import com.huaop2p.yqs.widget.scroll.ScrollableLayout;
import com.yolanda.nohttp.RequestMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;


/**
 * Created by Administrator on 2016/4/28.
 * 作者：任洋
 * 功能：财富中心的详情
 */
public class WealthCenterDetailActivity extends BaseAutoActivity<WealthCenter> implements PullToRefreshLayout.OnRefreshListener, IWealthDetailView<WealthCenter> {
    private CustomTextView tv_gains, tv_date;
    private ViewPager vp;
    private TabPageIndicator indicator;
    private ScrollableLayout mScrollLayout;
    private PullToRefreshLayout refreshLayout;
    private List<ScrollAbleFragment> fragments;
    private TextView tv_name,
            tv_shouyilv_value,
            tv_shouyilv,
            tv_left, tv_right,
            dosage, //已投金额
            residual_amount,
            home_date,
            caifutong_value,
            yuebao_value, tv_regular_rate_value, tv_current_interest_rate_value, qixi_way, shengyu;
    private int index;
    private WealthCenter wealthCenter;
    private Handler handler = new Handler();
    private int type;  //区分余xx类型
    private CostomProgress progressBar2, progressBar3, progressBar4, progressBar5, progressBar6;
    private ProgressBar progressBar1;
    private final static int TIME = 10;
    private LinearLayout ll_flag;
    private int textColor;
    private int select_Bg;
    String[] titles = null;
    private Drawable topProgressbg;
    private Drawable bottomProgressbg;

    private WealthCenterDetailsPresenterImpl presenter;
    private Map<String, Object> map = new HashMap<>();
    private String id;
    private double caifutong, yuebao, dingqi, huoqi, topOne;
    private int investmentTime;
    private double trueRate;
    private String keyId;
    private float bei = 5;
    private int minMoney;
    private Button ll_bottom;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wealth_center_detail;
    }

    @Override
    public void initViews() {
        tv_gains = (CustomTextView) findViewById(R.id.tv_gains);
        tv_date = (CustomTextView) findViewById(R.id.tv_date);
        tv_shouyilv_value = (TextView) findViewById(R.id.tv_shouyilv_value);
        tv_shouyilv = (TextView) findViewById(R.id.tv_shouyilv);
        vp = (ViewPager) findViewById(R.id.vp);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_right = (TextView) findViewById(R.id.tv_right);
        indicator = (TabPageIndicator) findViewById(R.id.indictor);
        mScrollLayout = (ScrollableLayout) findViewById(R.id.scrollableLayout);
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        refreshLayout.setOnRefreshListener(this);
        home_date= (TextView) findViewById(R.id.home_date);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar);
        progressBar2 = (CostomProgress) findViewById(R.id.progressBar2);
        progressBar3 = (CostomProgress) findViewById(R.id.progressBar3);
        progressBar4 = (CostomProgress) findViewById(R.id.progressBar4);
        progressBar5 = (CostomProgress) findViewById(R.id.progressBar5);
        progressBar6 = (CostomProgress) findViewById(R.id.progressBar6);
        tv_name = (TextView) findViewById(R.id.tv_name);
        dosage = (TextView) findViewById(R.id.dosage);
        residual_amount = (TextView) findViewById(R.id.residual_amount);
        ll_flag = (LinearLayout) findViewById(R.id.ll_flag);
        caifutong_value = (TextView) findViewById(R.id.caifutong_value);
        yuebao_value = (TextView) findViewById(R.id.yuebao_value);
        tv_regular_rate_value = (TextView) findViewById(R.id.tv_regular_rate_value);
        tv_current_interest_rate_value = (TextView) findViewById(R.id.tv_current_interest_rate_value);
        qixi_way = (TextView) findViewById(R.id.qixi_way);
        shengyu = (TextView) findViewById(R.id.shengyu);
        ll_bottom = (Button) findViewById(R.id.ll_bottom);
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

    public void selectIndicator(int position) {
        indicator.clearHeadTextColor(R.color.vpi_title_text_color);
        TextView tv = (TextView) indicator.getPositionView(position);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimensionPixelSize(R.dimen.select_text_content_size));
        tv.setTextColor(textColor);
        tv.setBackgroundResource(select_Bg);
        AutoUtils.autoTextSize(tv);
    }

    @Override
    public void initData() {
        type = getIntent().getIntExtra("type", State.YUXINBAO);
        id = getIntent().getStringExtra("id");
        presenter = new WealthCenterDetailsPresenterImpl(this);
        initContent();
        initFragmentPager(vp, indicator, mScrollLayout);
        map.put("Type", type);
        map.put("KeyId", id);

        presenter.loadDetail(map, HttpUrl.POST_GET_YUFANG_DETAILS, 0, RequestMethod.POST);
       if (type==State.YUXINBAO)
           presenter.getH5(map);
        //  setProgress(progressBar1, 50);
        progressBar2.setStyle(CostomProgress.STYLE2);
        progressBar3.setStyle(CostomProgress.STYLE2);
        progressBar4.setStyle(CostomProgress.STYLE2);
        progressBar5.setStyle(CostomProgress.STYLE2);
        progressBar6.setStyle(CostomProgress.STYLE2);
    }


    private void initContent() {
        fragments = new ArrayList<>();
        switch (type) {
            case State.YUXINBAO:
                textColor = getResources().getColor(R.color.red);
                select_Bg = R.drawable.vpi__tab_selected_holo;
                bottomProgressbg = getResources().getDrawable(R.drawable.progress0dp);
                topProgressbg = getResources().getDrawable(R.drawable.progress5dp);
                titles = new String[]{"投资介绍"};
                ScrollAbleFragment fragment = new WealthModelFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("type", type);
                fragment.setArguments(bundle);
                fragments.add(fragment);
                home_date.setText("封闭期");
//                fragment = new InvestorListFragment();
//                bundle = new Bundle();
//                bundle.putString("KeyId", id);
//                bundle.putInt("type", type);
//                fragment.setArguments(bundle);
//                fragments.add(fragment);
//                fragment = new WealthModelFragment();
//                bundle = new Bundle();
//                bundle.putInt("type", type);
//                fragment.setArguments(bundle);
//                fragments.add(fragment);
                break;
            case State.YUFANGBAO:
                textColor = getResources().getColor(R.color.yufangbao);
                select_Bg = R.drawable.vpi__tab_selected_holo_fang;
                bottomProgressbg = getResources().getDrawable(R.drawable.progress0dpyufangbao);
                topProgressbg = getResources().getDrawable(R.drawable.progress5dpyufangbao);
                tv_right.setText("还款方式");
                tv_left.setText("借款总金额");
                titles = new String[]{"借款信息"};
                fragment = new PersonalDataFragment();
                bundle = new Bundle();
                bundle.putString("KeyId", id);
                bundle.putInt("color", textColor);
                bundle.putBoolean("flag", true);
                fragment.setArguments(bundle);
                fragments.add(fragment);
                home_date.setText("期限");
//                fragment = new InvestorListFragment();
//                bundle = new Bundle();
//                bundle.putInt("type", type);
//                fragment.setArguments(bundle);
//                fragments.add(fragment);
//                fragment = new WealthModelFragment();
//                bundle = new Bundle();
//                bundle.putInt("type", type);
//                fragment.setArguments(bundle);
//                fragments.add(fragment);
                break;
            case State.YUCHEBAO:
                textColor = getResources().getColor(R.color.yuchebao);
                select_Bg = R.drawable.vpi__tab_selected_holo_che;
                bottomProgressbg = getResources().getDrawable(R.drawable.progress0dpyuchebao);
                topProgressbg = getResources().getDrawable(R.drawable.progress5dpyuchebao);
                tv_right.setText("还款方式");
                tv_left.setText("借款总金额");
                titles = new String[]{"借款信息"};
                fragment = new PersonalDataFragment();
                bundle = new Bundle();
                bundle.putString("KeyId", id);
                bundle.putBoolean("flag", true);
                bundle.putInt("color", textColor);
                fragment.setArguments(bundle);
                fragments.add(fragment);
                home_date.setText("期限");
//                fragment = new InvestorListFragment();
//                bundle = new Bundle();
//                bundle.putInt("type", type);
//                fragment.setArguments(bundle);
//                fragments.add(fragment);
//                fragment = new WealthModelFragment();
//                bundle = new Bundle();
//                bundle.putInt("type", type);
//                fragment.setArguments(bundle);
//                fragments.add(fragment);
                break;
        }
        tv_name.setTextColor(textColor);
        dosage.setTextColor(textColor);
        residual_amount.setTextColor(textColor);
        shengyu.setTextColor(textColor);
        tv_shouyilv_value.setTextColor(textColor);
        tv_shouyilv.setTextColor(textColor);
        progressBar1.setProgressDrawable(topProgressbg);
        progressBar2.setBackgroundColorSelect(textColor);
        progressBar3.setBackgroundColorSelect(getResources().getColor(R.color.textcolor));
        progressBar4.setBackgroundColorSelect(getResources().getColor(R.color.textcolor));
        progressBar5.setBackgroundColorSelect(getResources().getColor(R.color.textcolor));
        progressBar6.setBackgroundColorSelect(getResources().getColor(R.color.textcolor));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
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

    /***
     * enevtBus 传值给view赋值
     **/
    public void onEventMainThread(WealthCenter wealthCenter) {
        setView(wealthCenter);
        EventBus.getDefault().removeStickyEvent(wealthCenter.getClass());
    }

    public void setView(WealthCenter wealthCenter) {
        if (this.wealthCenter == null) {
            trueRate = wealthCenter.TrueRate;
            keyId = wealthCenter.KeyId;
            tv_name.setText(wealthCenter.Name);
            String[] ones = wealthCenter.TopoOne.split("\\+");
            float arg1 = 0;
            for (int i = 0; i < ones.length; i++) {
                int index = ones[i].indexOf("%");
                if (index != -1) {
                    arg1 += Float.valueOf(ones[i].substring(0, index));
                } else {
                    arg1 += Float.valueOf(ones[i]);
                }
            }
            progressBar2.setProgress(arg1 * bei);
            tv_shouyilv_value.setText(String.format("%.2f", arg1) + "%");
        } else {
            if (wealthCenter.Amount != null && type == State.YUXINBAO)
                residual_amount.setText(String.format("%.2f", BaseCalculator.getClaculator().divide(Double.valueOf(wealthCenter.Amount), 20f)) + "万元");
            if (wealthCenter.ReturnWay != null && type != State.YUXINBAO) {
                residual_amount.setText(wealthCenter.ReturnWay);
            }
            tv_name.setText(wealthCenter.LoanName);
        }
        float arg = new BigDecimal(BaseCalculator.getClaculator().divide(Float.valueOf(wealthCenter.AlreadyBuy) * 100f, (Float.valueOf(wealthCenter.Amount) + Float.valueOf(wealthCenter.AlreadyBuy)))).setScale(2, BigDecimal.ROUND_UP).floatValue();
        setProgress(progressBar1, (int) arg);
        shengyu.setText(String.format("%.2f", 100f - arg));
        qixi_way.setText(wealthCenter.qiXiWay);

        if (wealthCenter.AlreadyBuy != null && type == State.YUXINBAO)
            dosage.setText(String.format("%.2f", Float.valueOf(wealthCenter.AlreadyBuy) / 20f) + "万");
        else
            dosage.setText(String.format("%.2f",( Float.valueOf(wealthCenter.AlreadyBuy)+Float.valueOf(wealthCenter.Amount))/10000) + "万");

        if (wealthCenter.TopoOne != null) {
            String flag = null;
            if (wealthCenter.TopoOne.indexOf(".") != -1 && wealthCenter.TopoOne.indexOf("%") > wealthCenter.TopoOne.indexOf(".")) {
                flag = ".";
            } else {
                flag = "%";
            }
            tv_gains.setText(wealthCenter.TopoOne, flag, textColor);
            topOne = Double.valueOf(wealthCenter.TopoOne.substring(0, wealthCenter.TopoOne.indexOf("%")));
        }
        if (wealthCenter.LoanState != null) {
            if (type == State.YUXINBAO && !wealthCenter.LoanState.equals(State.MAN_BIAO)) {
                ll_bottom.setBackgroundColor(getResources().getColor(R.color.red));
                ll_bottom.setEnabled(true);
            } else if (type != State.YUXINBAO && !wealthCenter.LoanState.equals(State.TRANSFER)) {
                ll_bottom.setBackgroundColor(getResources().getColor(R.color.red));
                ll_bottom.setEnabled(true);
            }
        }
        if (wealthCenter.LoanTerm != null) {
            switch (wealthCenter.LoanTermId) {
                case State.YEAR:
                    tv_date.setText(wealthCenter.LoanTerm + "年", "年", textColor);
                    investmentTime = Integer.valueOf(wealthCenter.LoanTerm) * 12 * 30;
                    break;
                case State.DAY:
                    tv_date.setText(wealthCenter.LoanTerm + "天", "天", textColor);
                    investmentTime = Integer.valueOf(wealthCenter.LoanTerm);
                    break;
                case State.MONTH:
                    tv_date.setText(wealthCenter.LoanTerm + "个月", "个", textColor);
                    investmentTime = Integer.valueOf(wealthCenter.LoanTerm) * 30;
                    break;
            }
        }
        if (wealthCenter.AssetStyles != null) {
            ll_flag.removeAllViews();
            for (int i = 0; i < wealthCenter.AssetStyles.size(); i++) {
                Style style = wealthCenter.AssetStyles.get(i);
                MyTextView textView = new MyTextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(5, 5, 5, 5);
                textView.setMinWidth(AutoUtils.getPercentHeightSize(30));
                textView.setMinHeight(AutoUtils.getPercentHeightSize(30));
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(layoutParams);
                textView.setText(style.Font);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.small_content_size));
                textView.setPadding(5, 0, 5, 0);
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundColor(Color.parseColor("#" + style.Color));
                AutoUtils.auto(textView);
                ll_flag.addView(textView, 0);
                int index = style.Font.indexOf("元起投");
                if (index != -1) {
                    minMoney = Integer.valueOf(style.Font.substring(0, index));
                }
            }
        }
        if (wealthCenter.ProductsRate != null) {
            for (int i = 0; i < wealthCenter.ProductsRate.size(); i++) {
                ProductRate productRate = wealthCenter.ProductsRate.get(i);
                if (productRate.Name.equals(ResourceUtil.getString(getResources(), R.string.Money_paid_through_rate_of_return))) {
                    progressBar5.setProgress(BaseCalculator.getClaculator().multiply(productRate.LoanRate, 100f) * bei);
                    caifutong = productRate.LoanRate;
                    caifutong_value.setText(String.format("%.2f", BaseCalculator.getClaculator().multiply(productRate.LoanRate, 100f)) + "%");
                } else if (productRate.Name.equals(ResourceUtil.getString(getResources(), R.string.balance_treasure_yield))) {
                    progressBar6.setProgress(BaseCalculator.getClaculator().multiply(productRate.LoanRate, 100f) * bei);
                    yuebao_value.setText(String.format("%.2f", BaseCalculator.getClaculator().multiply(productRate.LoanRate, 100f)) + "%");
                    yuebao = productRate.LoanRate;
                } else if (productRate.Name.equals(ResourceUtil.getString(getResources(), R.string.regular_rate_of_bank_interest_rates))) {
                    progressBar3.setProgress(BaseCalculator.getClaculator().multiply(productRate.LoanRate, 100f) * bei);
                    tv_regular_rate_value.setText(String.format("%.2f", BaseCalculator.getClaculator().multiply(productRate.LoanRate, 100f)) + "%");
                    dingqi = productRate.LoanRate;
                } else if (productRate.Name.equals(ResourceUtil.getString(getResources(), R.string.bank_current_interest_rate))) {
                    progressBar4.setProgress(BaseCalculator.getClaculator().multiply(productRate.LoanRate, 100f) * bei);
                    tv_current_interest_rate_value.setText(String.format("%.2f", BaseCalculator.getClaculator().multiply(productRate.LoanRate, 100f)) + "%");
                    huoqi = productRate.LoanRate;
                }
            }
        }
    }
    @Override
    public Object getObject() {
        return this;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 点击购买
     **/
    public void buy(View view) {
        if (!AppApplication.isLogin) {
            Intent intent = new Intent(this, LogonActivity.class);
            startActivity(intent);
            return;
        }
        if (wealthCenter == null)
            return;
        Intent intent = new Intent(this, InvestmentActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("name", tv_name.getText().toString());
        intent.putExtra("investmentDate", tv_date.getText().toString());
        intent.putExtra("jixiWay", wealthCenter.qiXiWay);
        intent.putExtra("yearRate", trueRate);
        intent.putExtra("keyId", keyId);
        intent.putExtra("minMoney", minMoney);
        intent.putExtra("ketou",String.format("%.2f", BaseCalculator.getClaculator().divide(Double.valueOf(wealthCenter.Amount), 20f)) + "万元");
        startActivity(intent);
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && !fragments.get(index).isBack()) {
//            return super.onKeyDown(keyCode, event);
//        } else {
//            return false;
//        }
//    }

    @Override
    public void setProgress(final ProgressBar progressBar, final int pro) {
        Runnable runnable = new Runnable() {
            int count = 0;

            @Override
            public void run() {
                if (count+2 > pro) {
                    count=pro;
                    progressBar.setProgress(count);
                    return;
                }
                count+=2;
                progressBar.setProgress(count);
                handler.post(this);
            }
        };
        handler.post(runnable);
    }

    @Override
    public void loadH5(String url) {
        EventBus.getDefault().postSticky(url);
    }

    @Override
    public void showSuccess(WealthCenter wealthCenter) {
        this.wealthCenter = wealthCenter;
        setView(wealthCenter);
    }

    /***
     * 打开计算器
     **/
    public void startCalculator(final View view) {
//        final float y=-view.getTop() / 2f;
//        Log.i("eee","y"+y);
//        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("translationX", 0,
//                ((View) view.getParent()).getWidth() / 2, 0);
//        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("translationY",0,
//                y, 0);
//        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY);
//        final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Log.i("eee",animator.getCurrentPlayTime()+"--"+animator.getStartDelay());
//                if (animator.getAnimatedValue("y")==y){
//                    Intent intent = new Intent(WealthCenterDetailActivity.this, CalculatorActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
//        animator.setDuration(3000).start();
        Intent intent = new Intent(WealthCenterDetailActivity.this, CalculatorActivity.class);
        intent.putExtra("caifutong", caifutong);
        intent.putExtra("yuebao", yuebao);
        intent.putExtra("dingqi", dingqi);
        intent.putExtra("huoqi", huoqi);
        intent.putExtra("topOne", topOne / 100f);
        intent.putExtra("investmentTime", investmentTime);
        intent.putExtra("date", tv_date.getText().toString());
        intent.putExtra("type",type);
        startActivity(intent);

    }

}
