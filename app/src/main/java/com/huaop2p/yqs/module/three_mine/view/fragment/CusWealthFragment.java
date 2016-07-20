package com.huaop2p.yqs.module.three_mine.view.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.contant.HttpConnector;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.linstener.EventBusLinstener;
import com.huaop2p.yqs.module.base.view.BaseAutoFragment;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.logon.activity.AccountActivity;
import com.huaop2p.yqs.module.logon.activity.LoginActivity;
import com.huaop2p.yqs.module.logon.activity.LogonActivity;
import com.huaop2p.yqs.module.logon.entity.LoginedBean;
import com.huaop2p.yqs.module.three_mine.activity.ActiveActivity;
import com.huaop2p.yqs.module.three_mine.activity.CashActivity;
import com.huaop2p.yqs.module.three_mine.activity.DetaActivity;
import com.huaop2p.yqs.module.three_mine.activity.InvestmentRecordActivity;
import com.huaop2p.yqs.module.three_mine.activity.LotteryTicketActivity;
import com.huaop2p.yqs.module.three_mine.activity.PointMarketActivity;
import com.huaop2p.yqs.module.three_mine.activity.RechargeActivity;
import com.huaop2p.yqs.module.three_mine.activity.BankCardActivity;
import com.huaop2p.yqs.module.three_mine.adapter.ColumnAdapter;
import com.huaop2p.yqs.module.three_mine.model.entity.Balance;
import com.huaop2p.yqs.module.three_mine.model.entity.MyWealth;
import com.huaop2p.yqs.module.three_mine.presenter.impl.MyWealthPresenterImpl;
import com.huaop2p.yqs.module.three_mine.view.IMyWealthView;
import com.huaop2p.yqs.module.three_mine.view.impl.ProgressViewImpl;
import com.huaop2p.yqs.module.two_wealth.assist.BaseCalculator;
import com.huaop2p.yqs.module.two_wealth.model.entity.EventBusEntity;
import com.huaop2p.yqs.utils.ResourceUtil;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.utils.auto.AutoUtils;
import com.huaop2p.yqs.widget.AutoTextView;
import com.huaop2p.yqs.widget.CostomButton;
import com.huaop2p.yqs.widget.CustomGridView;
import com.huaop2p.yqs.widget.ProgressView;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshBase;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by zgq on 2016/4/6.
 * 作者:  ren yang
 * 时间:  2016/4/6 17:21
 * 功能:  我的财富
 */
public class CusWealthFragment extends BaseAutoFragment<BaseResponseEntity<MyWealth>> implements IMyWealthView, View.OnClickListener, EventBusLinstener<LoginedBean> {
    private MyWealthPresenterImpl myWealthPresenter;
    @ViewInject(R.id.pv)
    private ProgressView progressView;
    @ViewInject(R.id.mScrollView)
    private PullToRefreshScrollView mScrollView;
    @ViewInject(R.id.cgd)
    private CustomGridView customGridView;
    @ViewInject(R.id.tv_sign)
    private TextView tv_sigon;
    @ViewInject(R.id.tv_sign_result)
    private TextView tv_sign_result;
    @ViewInject(R.id.ll_head_image)
    private ImageView ll_head_image;
    @ViewInject(R.id.cb)
    private CostomButton cb;
    private ProgressViewImpl progressViewImpl;
    private TextView tv_yuxinbao, tv_yufangbao, tv_yuchebao, tv_recharge;
    private BaseResponseEntity<MyWealth> myWealth;
    @ViewInject(R.id.ll_shade)
    private View ll_shade;
    @ViewInject(R.id.rl_content)
    private View rl_content;
    @ViewInject(R.id.btn_register)
    private Button btn_register;
    @ViewInject(R.id.btn_login)
    private Button btn_login;
    @ViewInject(R.id.tv_available_balance)
    private TextView tv_available_balance;    //可用余额
    @ViewInject(R.id.tv_frozen_money)
    private TextView tv_frozen_money;         //冻结资金
    @ViewInject(R.id.tv_expectedReturn)
    private TextView tv_expectedReturn;       //预期收益
    @ViewInject(R.id.tv_total_profit)
    private TextView tv_total_profit;       //总收益
    @ViewInject(R.id.tv_availableBalance)
    private TextView tv_availableBalance;      //可用余额
    @ViewInject(R.id.tv_total_money)
    private AutoTextView tv_total_money;      //总资产
    @ViewInject(R.id.tv_dangqianshouyi)
    private TextView tv_dangqianshouyi;
    private Balance balance;   //余额
    private TextView tv_cash;
    private BaseCalculator calculator;


    @Override
    public void showData() {
        if (myWealthPresenter == null) {
            EventBus.getDefault().register(this);
            myWealthPresenter = new MyWealthPresenterImpl(this);
            progressViewImpl = new ProgressViewImpl(progressView);
            setLinstener();
            setGridView();
            disableAutoScrollToBottom();
            clearUiData();
            myWealthPresenter.loadDetail(null, HttpUrl.GET_ASSETS, 0, RequestMethod.GET);
            progressViewImpl.setMargin(5);
            calculator = BaseCalculator.getClaculator();
            if (AppApplication.isLogin) {
                ImageLoader.getInstance().displayImage(HttpConnector.HTTT_HOST + AppApplication.user.UrlHeadPhoto, ll_head_image);
            }
        }
    }


    private void setGridView() {
        List<SparseArray> arrays = new ArrayList<>();
        SparseArray array = new SparseArray();
        array.put(0, "投资记录");
        array.append(1, R.drawable.homenubtn_0);
        arrays.add(array);
//        array = new SparseArray();
//        array.put(0, "转让记录");
//        array.append(1, R.drawable.homenubtn_1);
//        arrays.add(array);
        array = new SparseArray();
        array.put(0, "活动专栏");
        array.append(1, R.drawable.homenubtn_2);
        arrays.add(array);
        array = new SparseArray();
        array.put(0, "收支明细");
        array.append(1, R.drawable.incomedetail);
        arrays.add(array);
        array = new SparseArray();
        array.put(0, "红包奖券");
        array.append(1, R.drawable.homenubtn_3);
        arrays.add(array);
        array = new SparseArray();
        array.put(0, "我的积分");
        array.append(1, R.drawable.homenubtn_4);
        arrays.add(array);
        array = new SparseArray();
        array.put(0, "我的银行卡");
        array.append(1, R.drawable.homenubtn_5);
        arrays.add(array);

        ColumnAdapter adapter = new ColumnAdapter(arrays, getActivity(), R.layout.item_column);
        customGridView.setAdapter(adapter);

        customGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        if (myWealth != null) {
                            intent = new Intent(getActivity(), InvestmentRecordActivity.class);
                            startActivity(intent);
                            EventBus.getDefault().postSticky(myWealth);
                        }
                        break;
//                    case 1:
//                        intent = new Intent(getActivity(), TransferRecordActivity.class);
//                        startActivity(intent);
//                        break;
                    case 3://奖券管理
                        intent = new Intent(getActivity(), LotteryTicketActivity.class);
                        intent.putExtra("flag", 0);
                        startActivity(intent);
                        break;
                    case 4://积分管理
                        intent = new Intent(getActivity(), PointMarketActivity.class);
                        startActivity(intent);
                        break;
                    case 5://银行卡
                        intent = new Intent(getActivity(), BankCardActivity.class);
                        startActivity(intent);
                        break;
                    case 2://收入明细
                        intent = new Intent(getActivity(), DetaActivity.class);
                        startActivity(intent);
                        break;
                    case 1://活动专栏
                        intent = new Intent(getActivity(), ActiveActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
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

    private void setLinstener() {
        mScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                myWealthPresenter.clearTimer();
                myWealthPresenter.loadDetail(null, HttpUrl.GET_ASSETS, 0, RequestMethod.GET);
            }
        });
        tv_sigon.setOnClickListener(this);
        ll_head_image.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public View initViews(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_my_wealth, null);
        tv_yuxinbao = (TextView) view.findViewById(R.id.tv_yuxinbao);
        tv_yuchebao = (TextView) view.findViewById(R.id.tv_yuchebao);
        tv_yufangbao = (TextView) view.findViewById(R.id.tv_yufangbao);
        tv_recharge = (TextView) view.findViewById(R.id.tv_recharge);
        tv_cash = (TextView) view.findViewById(R.id.tv_cash);
        tv_recharge.setOnClickListener(this);
        tv_cash.setOnClickListener(this);
        return view;
    }

    @Override
    public void showSuccess(BaseResponseEntity<MyWealth> myWealth) {
        this.myWealth = myWealth;
        mScrollView.stopRefresh();
        if (this.myWealth == null)
            return;
        tv_yuxinbao.setText(String.format("%.2f", Float.valueOf(myWealth.ReturnMessage.Xin.Money)));
        tv_yufangbao.setText(String.format("%.2f", Float.valueOf(myWealth.ReturnMessage.House.Money)));
        tv_yuchebao.setText(String.format("%.2f", Float.valueOf(myWealth.ReturnMessage.Car.Money)));
        tv_total_profit.setText(String.format("%.2f", myWealth.ReturnMessage.UserTrades));
        tv_expectedReturn.setText(String.format("%.2f", myWealth.ReturnMessage.Xin.TrueRate + myWealth.ReturnMessage.House.TrueRate + myWealth.ReturnMessage.Car.TrueRate));
        tv_frozen_money.setText(String.format("%.2f", myWealth.ReturnMessage.Xin.Money + myWealth.ReturnMessage.House.Money + myWealth.ReturnMessage.Car.Money));
    }

    @Override
    public void showError(int errorCode, String str) {
        mScrollView.stopRefresh();
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    /***
     * 清除界面数据
     **/
    @Override
    public void clearUiData() {
        tv_yuxinbao.setText("0.00");
        tv_yufangbao.setText("0.00");
        tv_yuchebao.setText("0.00");
        progressViewImpl.clearProgerss();
        cb.setProgress(0);
        tv_sigon.setText("签到");
        tv_available_balance.setText("0.00");
        tv_frozen_money.setText("0.00");
        tv_expectedReturn.setText("0.00");
        tv_total_profit.setText("0.00");
        tv_availableBalance.setText("0.00");
        tv_total_money.setText("0.00");
        tv_dangqianshouyi.setText("0.00");
        ll_head_image.setImageResource(R.drawable.head_image);
    }

    @Override
    public void setBalance(Balance balance) {
        this.balance = balance;
        if (this.balance == null)
            return;
        tv_availableBalance.setText(String.format("%.2f", balance.ca_balance / 100f));
        tv_available_balance.setText(String.format("%.2f", balance.ca_balance / 100f) + "元");
    }

    @Override
    public void updateSignButton(String string) {
        if (string.equals("true")) {
            if (isAdded()) {
                cb.setBackgroundColor(getResources().getColor(R.color.qian_grey));
            } else {
                cb.setBackgroundColor(Color.GRAY);
            }
            tv_sigon.setText("已签到");
        } else {
            cb.setProgress(0);
            tv_sigon.setText("签到");
        }
    }

    @Override
    public void onStop() {
        myWealthPresenter.stopTask();
        super.onStop();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    /**
     * 用户看不见的情况下停止任务，否则开启任务
     **/
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            myWealthPresenter.stopTask();
        } else {
            myWealthPresenter.startTask();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (myWealthPresenter != null) {
            myWealthPresenter.startTask();
        }
    }

    @Override
    public void setTotalMoney(final String string) {
        if (balance == null)
            return;
        double result = calculator.add(0, Double.valueOf(string));
        result = calculator.add(result, Double.valueOf(myWealth.ReturnMessage.Xin.Money));
        result = calculator.add(result, Double.valueOf(myWealth.ReturnMessage.Car.Money));
        result = calculator.add(result, Double.valueOf(myWealth.ReturnMessage.House.Money));
        result = calculator.add(result, balance.ca_balance / 100f);
        final double finalResult = result;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                tv_total_money.setText(String.format("%.2f", Double.valueOf(finalResult)));
                tv_dangqianshouyi.setText(String.format("%.2f", Double.valueOf(string)));
                if (isAdded()) {
                    progressViewImpl.setProgress(new float[]{Float.valueOf(string), (float) (balance.ca_balance / 100f), Float.valueOf(myWealth.ReturnMessage.Xin.Money),
                            Float.valueOf(myWealth.ReturnMessage.House.Money), Float.valueOf(myWealth.ReturnMessage.Car.Money)});
                    progressViewImpl.setColors(new int[]{ResourceUtil.getColor(getResources(), R.color.dangqianshouyi),
                            ResourceUtil.getColor(getResources(), R.color.h_b_bg),
                            ResourceUtil.getColor(getResources(), R.color.red),
                            ResourceUtil.getColor(getResources(), R.color.yufangbao),
                            ResourceUtil.getColor(getResources(), R.color.yuchebao)});
                    progressViewImpl.startLoad();
                }
            }
        });
    }

    @Override

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_sign:
                if (tv_sigon.getText().toString().equals("已签到")) {
                    ToastUtils.show(AppApplication.mContext,"请明日再来签到");
                    return;
                }
                cb.setColor(getResources().getColor(R.color.qian_grey));
                cb.setProgress(80);
                myWealthPresenter.sign();
                break;
            case R.id.ll_head_image:
                intent = new Intent(getActivity(), AccountActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                intent = new Intent(getActivity(), LogonActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_register:
                intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_recharge:
//                    String str = "mchnt_cd=" + mchnt_cd + "&mchnt_txn_ssn=" + orderNum + "&login_id=" + phone + "&amt=" + money + "&page_notify_url=" + backUrl + "&back_notify_url=" + url + "&signature=" + sign;
//                    intent = new Intent(getActivity(),WebActivity.class);
//                    intent.putExtra("url", HttpUrl.QUICK_RECHARGE);
//                    intent.putExtra("param", str);
//                    startActivity(intent);
                intent = new Intent(getActivity(), RechargeActivity.class);
                intent.putExtra("flag", true);
                startActivity(intent);
                break;
            case R.id.tv_cash:
                intent = new Intent(getActivity(), CashActivity.class);
                startActivity(intent);
                break;
        }
    }

    /***
     * 签到动画
     **/
    private void setSignResult(String str) {
        tv_sign_result.setText(str);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(tv_sign_result, "alpha", 0.f, 0.2f, 0.f);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(tv_sign_result, "translationY", 0, -10, -50);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alpha, translateY);
        animatorSet.setDuration(2500);
        animatorSet.start();
    }

    @Override
    public void signSuccess(String str) {
        setSignResult(str);
        cb.setFull();
        tv_sigon.setText("已签到");
    }

    @Override
    public void signError(String str) {
        if (str.equals("已签到")) {
            cb.setProgress(100);
            tv_sigon.setText("已签到");
        } else {
            cb.setProgress(0);
            ToastUtils.show(AppApplication.mContext, str);
        }
        setSignResult(str);
    }

    /**
     * 打开弹窗
     **/
    @Override
    public void openShade() {
        ll_shade.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        ll_shade.setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(0, 0, AutoUtils.getPercentHeightSize(1200), 0);
        animation.setDuration(500);//设置动画持续时间
        ll_shade.setAnimation(animation);
        animation.startNow();
    }

    /***
     * 关闭弹窗
     **/
    @Override
    public void closeShade() {
        if (ll_shade.getVisibility() == View.GONE)
            return;
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, AutoUtils.getPercentHeightSize(1200));
        animation.setDuration(500);//设置动画持续时间
        ll_shade.setAnimation(animation);
        animation.startNow();
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ll_shade.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    /***
     * 监听用户状态刷新界面
     **/
    @Override
    public void onEventMainThread(LoginedBean loginedBean) {
        myWealthPresenter.clearTimer();
        myWealthPresenter.loadDetail(null, HttpUrl.GET_ASSETS, 0, RequestMethod.GET);
        ImageLoader.getInstance().displayImage(HttpConnector.HTTT_HOST + AppApplication.user.UrlHeadPhoto, ll_head_image);
    }

    /***
     * 监听用户状态刷新界面
     **/
    public void onEventMainThread(EventBusEntity<String> entity) {
        if (entity.type == 3)
            ImageLoader.getInstance().displayImage(HttpConnector.HTTT_HOST + AppApplication.user.UrlHeadPhoto, ll_head_image);
        else if(entity.type==10) {
            cb.setBackgroundColor(getResources().getColor(R.color.qian_grey));
            tv_sigon.setText("已签到");
        }
    }
}
