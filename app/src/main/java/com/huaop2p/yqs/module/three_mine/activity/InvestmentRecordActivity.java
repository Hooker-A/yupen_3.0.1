package com.huaop2p.yqs.module.three_mine.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.linstener.EventBusLinstener;
import com.huaop2p.yqs.module.three_mine.model.entity.Investment;
import com.huaop2p.yqs.module.three_mine.model.entity.MyWealth;
import com.huaop2p.yqs.module.three_mine.view.fragment.InVestmentRecordFragment;
import com.huaop2p.yqs.module.three_mine.view.impl.ProgressViewImpl;
import com.huaop2p.yqs.module.two_wealth.adapter.WealthCenterAdapter;
import com.huaop2p.yqs.utils.ResourceUtil;
import com.huaop2p.yqs.widget.ProgressView;
import com.huaop2p.yqs.widget.scroll.ScrollableLayout;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/4/21.
 * <p/>
 * 功能 ：投资记录页面
 */
public class InvestmentRecordActivity extends BaseAutoActivity implements PullToRefreshLayout.OnRefreshListener,EventBusLinstener<BaseResponseEntity<MyWealth>> {
    private ViewPager vp;
    private List<InVestmentRecordFragment> fragments;
    private ScrollableLayout mScrollLayout;
    private PullToRefreshLayout refreshLayout;
    private ImageView iv_bg;
    private ProgressViewImpl progressViewImpl;
    private ProgressView progressView;
    private TextView tv_yufangbao_money,tv_yuchebao_money,tv_yuxinbao_money,tv_total_money;
    private  BaseResponseEntity<MyWealth> myWealthBaseResponseEntity;
    public Investment investment;
    @Override
    public int getLayoutId() {
        return R.layout.activity_investment_record;
    }

    @Override
    public void initViews() {
        tv_yufangbao_money= (TextView) findViewById(R.id.tv_yufangbao_money);
        tv_yuchebao_money= (TextView) findViewById(R.id.tv_yuchebao_money);
        tv_yuxinbao_money= (TextView) findViewById(R.id.tv_yuxinbao_money);
        vp = (ViewPager) findViewById(R.id.vp);
        mScrollLayout = (ScrollableLayout) findViewById(R.id.scrollableLayout);
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        refreshLayout.setOnRefreshListener(this);
        iv_bg = (ImageView) findViewById(R.id.iv_bg);
        progressView = (ProgressView) findViewById(R.id.pv);
        tv_total_money= (TextView) findViewById(R.id.tv_total_money);
    }

    public void initFragmentPager(ViewPager viewPager, final ScrollableLayout mScrollLayout) {
        fragments = new ArrayList<>();
        Bundle bundle = null;
        InVestmentRecordFragment fragment;
        fragment = new InVestmentRecordFragment();
        bundle = new Bundle();
        bundle.putInt("type", State.YUXINBAO);
        fragment.setArguments(bundle);
        fragments.add(fragment);
        fragment = new InVestmentRecordFragment();
        bundle = new Bundle();
        bundle.putInt("type", State.YUFANGBAO);
        fragment.setArguments(bundle);
        fragments.add(fragment);
        fragment = new InVestmentRecordFragment();
        bundle = new Bundle();
        bundle.putInt("type",State.YUCHEBAO);
        fragment.setArguments(bundle);
        ;
        fragments.add(fragment);
        vp.setAdapter(new WealthCenterAdapter(getSupportFragmentManager(), fragments, new String[]{"余信宝", "余房宝", "余车宝"}));
        vp.setOffscreenPageLimit(2);
        mScrollLayout.getHelper().setCurrentScrollableContainer(fragments.get(0));
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }
            @Override
            public void onPageSelected(int i) {
                mScrollLayout.getHelper().setCurrentScrollableContainer(fragments.get(i));
                switch (i) {
                    case 0:
                        switchHeadById(R.id.ll_yuxinbao);
                        iv_bg.setBackgroundResource(R.drawable.vpi__tab_selected_holo);
                        break;
                    case 1:
                        switchHeadById(R.id.ll_yufangbao);
                        iv_bg.setBackgroundResource(R.drawable.vpi__tab_selected_holo_fang);
                        break;
                    case 2:
                        switchHeadById(R.id.ll_yuchebao);
                        iv_bg.setBackgroundResource(R.drawable.vpi__tab_selected_holo_che);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewPager.setCurrentItem(0);
    }

    @Override
    public void initData() {
        initFragmentPager(vp, mScrollLayout);
        progressViewImpl = new ProgressViewImpl(progressView);
        progressViewImpl.setMargin(5);
    }

    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        // 下拉刷新操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                onEventMainThread(InvestmentRecordActivity.this.myWealthBaseResponseEntity);
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        // 加载操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }

    public void switchFragment(View view) {
        switchHeadById(view.getId());
        switchFragmentById(view.getId());
    }

    /***
     * 切换头部导航
     */
    public void switchHeadById(int id) {
        View view = findViewById(id);
        final int l = iv_bg.getLeft();
        final int r = iv_bg.getRight();
        final int ll = view.getLeft();
        ValueAnimator animator = ValueAnimator.ofInt(0, ll - l);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (Integer) animation.getAnimatedValue();
                iv_bg.layout(l + curValue, iv_bg.getTop(), r + curValue, iv_bg.getBottom());
            }
        });
        animator.start();
    }
    /***
     * 切换底部Fragment
     */
    private void switchFragmentById(int id) {
        switch (id) {
            case R.id.ll_yuxinbao:
                vp.setCurrentItem(0);
                break;
            case R.id.ll_yufangbao:
                vp.setCurrentItem(1);
                break;
            case R.id.ll_yuchebao:
                vp.setCurrentItem(2);
                break;
        }
    }
    public void startHistory(View view){
        Intent intent=new Intent(this,HistoryActivity.class);
        startActivity(intent);
    }
    @Override
    public Object getObject() {
        return this;
    }
    @Override
    public void onEventMainThread(BaseResponseEntity<MyWealth> myWealthBaseResponseEntity) {
        this.myWealthBaseResponseEntity=myWealthBaseResponseEntity;
        progressViewImpl.setProgress(new float[]{myWealthBaseResponseEntity.ReturnMessage.Xin.Money, myWealthBaseResponseEntity.ReturnMessage.House.Money, myWealthBaseResponseEntity.ReturnMessage.Car.Money});
        progressViewImpl.setColors(new int[]{ResourceUtil.getColor(getResources(), R.color.yuxinbao),
                ResourceUtil.getColor(getResources(), R.color.yufangbao),
                ResourceUtil.getColor(getResources(), R.color.yuchebao)});
        progressViewImpl.startLoad();
        tv_yufangbao_money.setText(String.valueOf(myWealthBaseResponseEntity.ReturnMessage.House.Money) + "元");
        tv_yuchebao_money.setText(String.valueOf(myWealthBaseResponseEntity.ReturnMessage.Car.Money)+"元");
        tv_yuxinbao_money.setText(String.valueOf(myWealthBaseResponseEntity.ReturnMessage.Xin.Money) + "元");
        tv_total_money.setText(String.valueOf(myWealthBaseResponseEntity.ReturnMessage.Xin.Money+myWealthBaseResponseEntity.ReturnMessage.Car.Money+myWealthBaseResponseEntity.ReturnMessage.House.Money));
        EventBus.getDefault().unregister(BaseResponseEntity.class);
        EventBus.getDefault().unregister(myWealthBaseResponseEntity.getClass());
    }



}
