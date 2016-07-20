package com.huaop2p.yqs.module.two_wealth.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.contant.HttpConnector;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.linstener.EventBusLinstener;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.one_home.activity.MFActivity;
import com.huaop2p.yqs.module.three_mine.activity.InvestmentDetailsActivity;
import com.huaop2p.yqs.module.three_mine.activity.WebActivity;
import com.huaop2p.yqs.module.three_mine.model.entity.Investment;
import com.huaop2p.yqs.module.three_mine.view.IInVestmentView;
import com.huaop2p.yqs.module.two_wealth.assist.BaseCalculator;
import com.huaop2p.yqs.module.two_wealth.model.entity.Order;
import com.huaop2p.yqs.module.two_wealth.presenter.impl.InvestmentSuccessPresenterImpl;
import com.huaop2p.yqs.utils.ToastUtils;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/5/4.
 * 作者：任洋
 * 功能：投资成功
 */
public class InvestmentSuccessActivity extends BaseAutoActivity<Investment> implements EventBusLinstener<Order>, View.OnClickListener, IInVestmentView {
    private Order order;
    private TextView tv_orderNumber, tv_productName, tv_investor, tv_investorMoney, tv_startTime, tv_jixiWay, tv_investorDate, tv_expectedReturn,tv_use_red;
    private ImageView iv_success;
    private AnimationDrawable ad;
    private Button btn_look_details, btn_again_buy;
    private InvestmentSuccessPresenterImpl presenter;
    private Map<String, Object> paramMap;
    private int type;
    private String ddh, jixiWay;
    private Investment investment;
    private Button btn_Look_Contract;

    @Override
    public int getLayoutId() {
        return R.layout.activity_investment_success;
    }

    @Override
    public void initViews() {
        tv_orderNumber = (TextView) findViewById(R.id.tv_orderNumber);
        tv_productName = (TextView) findViewById(R.id.tv_productName);
        tv_investor = (TextView) findViewById(R.id.tv_investor);
        tv_investorMoney = (TextView) findViewById(R.id.tv_investorMoney);
        tv_startTime = (TextView) findViewById(R.id.tv_startTime);
        tv_jixiWay = (TextView) findViewById(R.id.tv_jixiWay);
        tv_investorDate = (TextView) findViewById(R.id.tv_investorDate);
        tv_expectedReturn = (TextView) findViewById(R.id.tv_expectedReturn);
        iv_success = (ImageView) findViewById(R.id.iv_success);
        btn_look_details = (Button) findViewById(R.id.btn_look_details);
        btn_again_buy = (Button) findViewById(R.id.btn_again_buy);
        btn_Look_Contract = (Button) findViewById(R.id.btn_Look_Contract);
        tv_use_red= (TextView) findViewById(R.id.tv_use_red);
        btn_look_details.setOnClickListener(this);
        btn_again_buy.setOnClickListener(this);
        btn_Look_Contract.setOnClickListener(this);
    }

    @Override
    public void initData() {
        ad = (AnimationDrawable) iv_success.getBackground();
        presenter = new InvestmentSuccessPresenterImpl(this);
        paramMap = new HashMap<>();
        type = getIntent().getIntExtra("type", State.YUXINBAO);
        ddh = getIntent().getStringExtra("DDH");
        jixiWay = getIntent().getStringExtra("jixiWay");
        paramMap.put("DDH", ddh);
        paramMap.put("Type", type);
        presenter.loadDetail(paramMap, HttpUrl.GET_INVERSTMENT_RECORD, 0, RequestMethod.POST);
    }

    @Override
    public void onEventMainThread(Order order) {
        this.order = order;
        EventBus.getDefault().removeStickyEvent(Order.class);
        initViews();
    }

    @Override
    public void onClick(View v) {
        if (investment == null)
            return;
        switch (v.getId()) {
            case R.id.btn_look_details:
                Intent intent = new Intent(this, InvestmentDetailsActivity.class);
                intent.putExtra("keyId", String.valueOf(investment.ProductId));
                intent.putExtra("type", type);
                intent.putExtra("DDH", investment.DDH);
                startActivity(intent);
                break;
            case R.id.btn_again_buy:
                intent = new Intent(this, MFActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_Look_Contract:
                HashMap<String, Object> map = new HashMap<String, Object>();
                HashMap<String, Object> child = new HashMap<String, Object>();
                child.put("DDH", investment.DDH);
                map.put("AppointEntity", child);
                presenter.getContract(map, HttpUrl.GET_CONTRACT, 0, RequestMethod.POST);
                break;
        }
    }
    @Override
    public Object getObject() {
        return this;
    }

    @Override
    public void showSuccess(Investment investment) {
        this.investment = investment;
        tv_orderNumber.setText(ddh);
        tv_productName.setText(investment.ProductName);
        tv_investor.setText(AppApplication.user.Nickname);
        tv_startTime.setText(getIntent().getStringExtra("time"));
        tv_jixiWay.setText(jixiWay);
        tv_investorMoney.setText(String.valueOf(investment.LoanMoney));
        String suffix = null;
        switch (investment.LoanTermId) {
            case State.DAY:
                suffix = "天";
                break;
            case State.MONTH:
                suffix = "个月";
                break;
            case State.YEAR:
                suffix = "年";
                break;
        }
        tv_investorDate.setText(investment.LoanTerm + suffix);
        tv_expectedReturn.setText(investment.LoanRate);
        tv_expectedReturn.setText(String.format("%.2f", BaseCalculator.getClaculator().multiply(Double.valueOf(investment.LoanMoney), Double.valueOf(investment.TrueRate))) + "元");
        iv_success.setVisibility(View.VISIBLE);
        StringBuffer sb=new StringBuffer();
        switch (investment.RedType) {
            case 1:
                sb.append(investment.RedMoney).append("元抵现红包");
                tv_use_red.setText(sb);
                break;
            case 2:
                sb.append(investment.RedMoney).append("元返现红包");
                tv_use_red.setText(sb);
                break;
            case 3:
                sb.append(investment.RedMoney).append("元满减红包");
                tv_use_red.setText(sb);
                break;
            case 4:
                sb.append(Float.valueOf(investment.RedMoney)).append("%加息券");
                tv_use_red.setText(sb);
                break;
            default:
                tv_use_red.setText("未使用");
                break;
        }
        ad.start();
    }

    @Override
    public void getContract(String str) {
        if (str.equals("")) {
            ToastUtils.show(AppApplication.mContext,"合同正在生成");
            return;
        } else {
            str = HttpConnector.HTTT_HOST + "/" + str;
        }
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("url", str);
        intent.putExtra("title", "合同");
        startActivity(intent);
    }
}
