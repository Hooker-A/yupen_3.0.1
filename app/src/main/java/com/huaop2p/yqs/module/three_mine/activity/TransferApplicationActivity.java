package com.huaop2p.yqs.module.three_mine.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.linstener.EventBusLinstener;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.three_mine.model.entity.Investment;
import com.huaop2p.yqs.module.three_mine.presenter.impl.TransferApplicationPresenterImpl;
import com.huaop2p.yqs.module.three_mine.view.ITransferApplicationView;
import com.huaop2p.yqs.module.two_wealth.assist.BaseCalculator;
import com.huaop2p.yqs.utils.DateUtils;
import com.huaop2p.yqs.utils.ResourceUtil;
import com.huaop2p.yqs.utils.ToastUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/5/16.
 * 作者：任洋
 * 功能：申请转让
 */
public class TransferApplicationActivity extends BaseAutoActivity<String> implements EventBusLinstener<Investment>, ITransferApplicationView {
    private TextView tv_name, tv_InvestmentMoney, tv_YearRate, tv_gains, tv_remaining_maturity, tv_qixi_day, tv_maturities, tv_investment_date, tv_fee_value, tv_zr, tv_huikuan;
    private View rl_discount, rl_discount_value;
    private RadioGroup rg;
    private RadioButton rb_line_top;
    private EditText et_value;
    private float value = 0;   //折让年化收益率
    private TextView tv_Transfer_fee, tv_total_money;
    private float fee;          //手续费利率
    private Investment investment;
    private double total_shouyi;
    private int tranferType;  //转让类型
    private TransferApplicationPresenterImpl presenter;
    private HashMap<String, Object> map, childMap;

    @Override
    public int getLayoutId() {
        return R.layout.activity_transfer_application;
    }

    @Override
    public void initViews() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_InvestmentMoney = (TextView) findViewById(R.id.tv_InvestmentMoney);
        tv_YearRate = (TextView) findViewById(R.id.tv_YearRate);
        tv_gains = (TextView) findViewById(R.id.tv_gains);
        tv_remaining_maturity = (TextView) findViewById(R.id.tv_remaining_maturity);
        tv_qixi_day = (TextView) findViewById(R.id.tv_qixi_day);
        tv_maturities = (TextView) findViewById(R.id.tv_maturities);
        tv_investment_date = (TextView) findViewById(R.id.tv_investment_date);
        rl_discount = findViewById(R.id.rl_discount);
        rl_discount_value = findViewById(R.id.rl_discount_value);
        rg = (RadioGroup) findViewById(R.id.rg);
        rb_line_top = (RadioButton) findViewById(R.id.rb_line_top);
        et_value = (EditText) findViewById(R.id.et_value);
        tv_total_money = (TextView) findViewById(R.id.tv_total_money);
        tv_Transfer_fee = (TextView) findViewById(R.id.tv_Transfer_fee);
        tv_fee_value = (TextView) findViewById(R.id.tv_fee_value);
        tv_zr = (TextView) findViewById(R.id.tv_zr);
        tv_huikuan = (TextView) findViewById(R.id.tv_huikuan);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkTransferType(checkedId);
                updateZrAndPayment();
                tv_fee_value.setText(String.format("%.2f", BaseCalculator.getClaculator().multiply(investment.LoanMoney + total_shouyi, fee)));
            }
        });
        et_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateInputContent(et_value.getText().toString());

            }
        });
    }

    @Override
    public void initData() {
        presenter = new TransferApplicationPresenterImpl(this);
        map = new HashMap<>();
        childMap = new HashMap<>();

    }
    @Override
    public Object getObject() {
        return this;
    }
    @Override
    public void onEventMainThread(Investment investment) {
        EventBus.getDefault().registerSticky(investment.getClass());
        this.investment = investment;
        tv_name.setText(investment.ProductName);
        tv_InvestmentMoney.setText(String.format("%.2f", investment.LoanMoney));
        tv_YearRate.setText(investment.LoanRate + "元");
        double value = BaseCalculator.getClaculator().multiply(investment.TrueRate, Double.valueOf(investment.LoanMoney));      //每天获得的钱
        double NowDay = 0;
        double totalDay = 0;
        int surplus = 0;
        if (!(investment.StartTime == null || investment.DateTimeNow == null || investment.EndTime == null)) {
            try {
                Date dateStart = DateUtils.sdf.parse(investment.StartTime);
                Date dateNow = DateUtils.sdf.parse(investment.DateTimeNow);
                Date dateEnd = DateUtils.sdf.parse(investment.EndTime);
                totalDay = DateUtils.subtract1(dateEnd, dateStart);
                NowDay = DateUtils.subtract1(dateNow, dateStart);
                surplus = DateUtils.subtract(dateEnd, dateNow);
                total_shouyi = BaseCalculator.getClaculator().multiply(BaseCalculator.getClaculator().divide(NowDay, totalDay), value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tv_qixi_day.setText(DateUtils.StringToDateString(investment.StartTime));
            tv_maturities.setText(DateUtils.StringToDateString(investment.EndTime));
            tv_remaining_maturity.setText(surplus + "天");
        } else {
            tv_qixi_day.setText("待定");
            tv_maturities.setText("待定");
            tv_remaining_maturity.setText("0天");
        }
        tv_gains.setText(String.format("%.2f", total_shouyi) + "元");
        tv_total_money.setText(String.format("%.2f", investment.LoanMoney) + "+" + String.format("%.2f", total_shouyi));
        tv_investment_date.setText(investment.StartTime);
        rb_line_top.setChecked(true);
    }

    @Override
    public void add(View view) {
        value = BaseCalculator.getClaculator().add(value, 0.1f);
        et_value.setText(String.valueOf(value));
        et_value.setSelection(et_value.length());
        updateZrAndPayment();
    }

    @Override
    public void reduce(View view) {
        if (value > 0) {
            value = BaseCalculator.getClaculator().reduce(value, 0.1f);
            et_value.setText(String.valueOf(value));
            et_value.setSelection(et_value.length());
            updateZrAndPayment();
        }
    }

    @Override
    public void checkTransferType(int id) {
        switch (id) {
            case R.id.rb_line_top:
                rl_discount.setVisibility(View.VISIBLE);
                rl_discount_value.setVisibility(View.VISIBLE);
                tv_Transfer_fee.setText(ResourceUtil.getString(getResources(), R.string.Transfer_fee));
                fee = 0.005f;
                tranferType = 1;
                break;
            case R.id.rb_jiaji:
                rl_discount.setVisibility(View.GONE);
                rl_discount_value.setVisibility(View.GONE);
                tv_Transfer_fee.setText(ResourceUtil.getString(getResources(), R.string.Transfer_fee2));
                fee = 0.02f;
                tranferType = 0;
                break;
        }
    }

    /**
     * 输入框内容发送改变时更改value
     **/
    @Override
    public void updateInputContent(String s) {
        if ("".equals(s)) {
            value = 0;
        } else {
            value = Float.valueOf(et_value.getText().toString());
        }
        updateZrAndPayment();
        et_value.setSelection(et_value.length());
    }

    @Override
    public void showSuccess(String s) {
        ToastUtils.show(AppApplication.mContext,"转让成功");
        finish();
    }

    /**
     * 修改折让和回款
     **/
    private void updateZrAndPayment() {
        Double value = BaseCalculator.getClaculator().multiply(BaseCalculator.getClaculator().add(investment.LoanMoney, total_shouyi), this.value / 100f);
        double huikuan = 0;
        if (rb_line_top.isChecked()) {
            huikuan = BaseCalculator.getClaculator().add(investment.LoanMoney, total_shouyi) - value - BaseCalculator.getClaculator().multiply(investment.LoanMoney + total_shouyi, fee);
        } else {
            huikuan = BaseCalculator.getClaculator().add(investment.LoanMoney, total_shouyi) - BaseCalculator.getClaculator().multiply(investment.LoanMoney + total_shouyi, fee);
        }
        tv_zr.setText(String.format("%.2f", value));
        tv_huikuan.setText(String.format("%.2f", huikuan));
    }

    public void applicationTransfer(View view) {
        childMap.put("YuPenProductId", investment.YuPenProductId);
        childMap.put("DDH", investment.DDH);
        childMap.put("Statetype", tranferType);
        childMap.put("DropMoney", value / 100f);
        map.put("AppointEntity", childMap);
        presenter.applicationTransfer(map);
    }
}
