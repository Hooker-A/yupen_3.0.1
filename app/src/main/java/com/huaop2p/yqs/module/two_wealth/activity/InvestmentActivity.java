package com.huaop2p.yqs.module.two_wealth.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.linstener.EventBusLinstener;
import com.huaop2p.yqs.module.three_mine.activity.LotteryTicketActivity;
import com.huaop2p.yqs.module.three_mine.activity.RechargeActivity;
import com.huaop2p.yqs.module.three_mine.activity.WebActivity;
import com.huaop2p.yqs.module.three_mine.model.entity.Balance;
import com.huaop2p.yqs.module.three_mine.model.entity.RedPackage;
import com.huaop2p.yqs.module.two_wealth.model.entity.Order;
import com.huaop2p.yqs.module.two_wealth.presenter.impl.InvestmentPresenterImpl;
import com.huaop2p.yqs.module.two_wealth.view.IInvestmentView;
import com.huaop2p.yqs.utils.NumberToCN;
import com.huaop2p.yqs.utils.ToastUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/5/3.
 * 作者：任洋
 * 功能：投资，购买界面
 */
public class InvestmentActivity extends BaseAutoActivity<Order> implements View.OnClickListener, IInvestmentView, EventBusLinstener<Map<String, Object>> {
    private Button btn_buy;
    private long minMoney;
    private TextView tv_value, tv_name, tv_jixi_way, tv_investment_date, tv_income, tv_pay_money_value, tv_red_hiht, tv_recharge, tv_total_money,tv_ketou_money;
    private long investmentMoney;   //投资金额 ，默认500
    private EditText et_money;
    private InvestmentPresenterImpl presenter;
    private HashMap<String, Object> map, childMap;
    private int type;
    private String name, investmentDate, jixiWay, keyId;
    private double yearRate;
    private View ll_money, rl_red, rl_lottery, rl_borrower;
    private TextView tv_money, tv_yue, tv_agreement,tv_inverstment_str;
    private float TransferMoney, TransferIncome;
    private Balance balance;
    private TextView tv1, tv2, tv_jian_money;
    private RedPackage red;

    @Override
    public int getLayoutId() {
        return R.layout.activity_investment;
    }

    @Override
    public void initViews() {
        btn_buy = (Button) findViewById(R.id.btn_buy);
        rl_borrower = findViewById(R.id.rl_borrower);
        tv_inverstment_str= (TextView) findViewById(R.id.tv_inverstment_str);
        rl_borrower.setOnClickListener(this);
        tv_agreement = (TextView) findViewById(R.id.tv_agreement);
        tv_total_money = (TextView) findViewById(R.id.tv_total_money);
        tv_agreement.setOnClickListener(this);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_ketou_money= (TextView) findViewById(R.id.tv_ketou_money);
        tv_value = (TextView) findViewById(R.id.tv_value);
        et_money = (EditText) findViewById(R.id.et_money);
        tv_income = (TextView) findViewById(R.id.tv_income);
        tv_jixi_way = (TextView) findViewById(R.id.tv_jixi_way);
        tv_jian_money = (TextView) findViewById(R.id.tv_jian_money);
        tv_investment_date = (TextView) findViewById(R.id.tv_investment_date);
        ll_money = findViewById(R.id.ll_money);
        tv_red_hiht = (TextView) findViewById(R.id.tv_red_hiht);
        rl_lottery = findViewById(R.id.rl_lottery);
        rl_lottery.setOnClickListener(this);
        rl_red = findViewById(R.id.rl_red);
        rl_red.setOnClickListener(this);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_recharge = (TextView) findViewById(R.id.tv_recharge);
        btn_buy.setOnClickListener(this);
        tv_recharge.setOnClickListener(this);
        tv_pay_money_value = (TextView) findViewById(R.id.tv_pay_money);
        tv_yue = (TextView) findViewById(R.id.tv_yue);
        tv1 = (TextView) findViewById(R.id.tv_ge1);
        tv2 = (TextView) findViewById(R.id.tv_ge);
        et_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = et_money.getText().toString();
                if (value.equals("")) {
                    investmentMoney = 0;
                } else {
                    investmentMoney = Long.valueOf(value);
                }
                updateNumByValue(investmentMoney);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra("flag",false)){
            presenter.getBalance();
        }
    }

    @Override
    public void initData() {
        type = getIntent().getIntExtra("type", State.YUXINBAO);
        investmentMoney = getIntent().getIntExtra("minMoney", 500);
        minMoney = investmentMoney;
        yearRate = getIntent().getDoubleExtra("yearRate", 0);
        name = getIntent().getStringExtra("name");
        investmentDate = getIntent().getStringExtra("investmentDate");
        jixiWay = getIntent().getStringExtra("jixiWay");
        keyId = getIntent().getStringExtra("keyId");
        tv_ketou_money.setText(getIntent().getStringExtra("ketou"));
        switch (type) {
            case State.YUXINBAO:
                updateNumByValue(investmentMoney);
                rl_borrower.setVisibility(View.VISIBLE);
                tv_inverstment_str.setText("封闭期");
                break;
            case State.YUFANGBAO:
                updateNumByValue(investmentMoney);
                tv_inverstment_str.setText("期限");
                break;
            case State.YUCHEBAO:
                updateNumByValue(investmentMoney);
                tv_inverstment_str.setText("期限");
                break;
            default:
                updateTransferStyle();
                break;
        }
        tv_name.setText(name);
        tv_red_hiht.setText("请输入投资金额," + minMoney + "起投,须是" + minMoney + "的倍数");
        tv_investment_date.setText(investmentDate);
        tv_jixi_way.setText(jixiWay);
        presenter = new InvestmentPresenterImpl(this);
        map = new HashMap<>();
        childMap = new HashMap<>();
        presenter.getBalance();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().registerSticky(this);
    }

    private void updateTransferStyle() {
        ll_money.setVisibility(View.GONE);
        findViewById(R.id.ll_value).setVisibility(View.GONE);

        tv_red_hiht.setVisibility(View.GONE);
        TransferMoney = getIntent().getFloatExtra("TransferMoney", 0);
        TransferIncome = getIntent().getFloatExtra("TransferIncome", 0);
        tv_income.setText(String.format("%.2f", TransferIncome));
        tv_money.setText(String.format("%.2f", TransferMoney) + "元");

        tv1.setText("不可用");
        tv2.setText("不可用");
        rl_red.setEnabled(false);
        rl_lottery.setEnabled(false);
        tv_pay_money_value.setText(String.format("%.2f", TransferMoney));
    }

    @Override
    public void onClick(View v) {
        String redId;
        if (red == null)
            redId = "";
        else
            redId = red.getKeyId();
        switch (v.getId()) {
            case R.id.btn_buy:
                if (balance == null || balance.ca_balance / 100f < investmentMoney) {
                    Intent intent = new Intent(this, BalanceHihtActivity.class);
                    startActivity(intent);
                    return;
                } else if (investmentMoney % minMoney != 0) {
                    ToastUtils.show(this, "投资金额必须是" + minMoney + "的倍数");
                    return;
                }
                if (type == State.YUXINBAO) {
                    if (investmentDate.indexOf("年") != -1) {
                        childMap.put("InvestmentMonth", Integer.valueOf(investmentDate.substring(0, investmentDate.length() - 1)) * 12);
                    } else if (investmentDate.indexOf("月") != -1) {
                        childMap.put("InvestmentMonth", Integer.valueOf(investmentDate.substring(0, investmentDate.length() - 2)));
                    }
                    childMap.put("InvestmentMoney", et_money.getText().toString());
                    childMap.put("PayWay", "0");

                    childMap.put("WYBM", redId);
                    childMap.put("MoneyCenterID", keyId);
                    map.put("AppointEntity", childMap);
                    map.put("PageIndex", "1");
                    presenter.payYuXin(map);
                } else if (type == State.YUFANGBAO || type == State.YUCHEBAO) {
                    childMap.put("StandardID", keyId);
                    childMap.put("LoanMoney", Integer.valueOf(et_money.getText().toString()));
                    childMap.put("PayWay", "0");
                    childMap.put("RedID", redId);
                    childMap.put("Remark", "");
                    map.put("AppointEntity", childMap);
                    presenter.payHouseCar(map);
                } else {
                    childMap.put("PayWay", "0");
                    map.put("AppointEntity", childMap);
                    map.put("KeyId", keyId);
                    presenter.payTransfer(map);
                }
                break;
            case R.id.tv_recharge:
                Intent intent = new Intent(this, RechargeActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_agreement:
                if (type == State.YUFANGBAO) {
                    presenter.getYuFangAgreement();
                } else if (type == State.YUXINBAO && name.equals("季度红")) {
                    presenter.getYuXinAgreement();             //季度红
                } else if (type == State.YUXINBAO && !name.equals("季度红")) {
                    startWebActivity("https://api.yupen.cn/Api/NewLoanServiceAgreement.html");  //余信宝
                } else {
                    presenter.getYuCheAgreement();
                }
                break;
            case R.id.rl_lottery:
                intent = new Intent(this, LotteryTicketActivity.class);
                intent.putExtra("flag", 2);
                intent.putExtra("money", investmentMoney);
                intent.putExtra("productType", type);
                intent.putExtra("keyId", keyId);
                startActivity(intent);
                break;
            case R.id.rl_red:
                intent = new Intent(this, LotteryTicketActivity.class);
                intent.putExtra("flag", 1);
                intent.putExtra("keyId", keyId);
                intent.putExtra("money", investmentMoney);
                intent.putExtra("productType", type);
                startActivity(intent);
                break;
            case R.id.rl_borrower:
                intent = new Intent(this, BorrowerListDialog.class);
                intent.putExtra("money", String.valueOf(investmentMoney));
                intent.putExtra("date", investmentDate.substring(0, investmentDate.length() - 2));
                startActivity(intent);
                break;
        }
    }

    public void subtract(View view) {
        if (investmentMoney >= minMoney * 2) {
            investmentMoney -= minMoney;
            updateNumByValue(investmentMoney);
        }
    }

    public void add(View view) {
        investmentMoney += minMoney;
        updateNumByValue(investmentMoney);

    }

    @Override
    public void updateNumByValue(long value) {
        String a = et_money.getText().toString();
        long b = 0;
        if (!a.equals(""))
            b = Long.valueOf(a);
        if (value != b)
            et_money.setText(String.valueOf(value));
        tv_value.setText(NumberToCN.number2CNMontrayUnit(new BigDecimal(value)));
        et_money.setSelection(et_money.length());
        tv_income.setText(String.format("%.2f", yearRate * value));
        tv_pay_money_value.setText(String.valueOf(value + Integer.valueOf(tv_jian_money.getText().toString())));

        tv_total_money.setText(String.valueOf(value));
    }

    @Override
    public void setBalance(Balance balance) {
        this.balance = balance;
        tv_yue.setText(String.format("%.2f", balance.ca_balance / 100f) + "元");
    }

    @Override
    public void startWebActivity(String url) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", "出借服务协议");
        startActivity(intent);
    }

    @Override
    public void showSuccess(Order s) {
        if (s != null && !s.equals("")) {
            Intent intent = new Intent(this, InvestmentSuccessActivity.class);
            intent.putExtra("DDH", s.DDH);
            intent.putExtra("time", s.BuyTime);
            intent.putExtra("jixiWay", jixiWay);
            intent.putExtra("type", type);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void startLoadData() {
        super.startLoadData();
        btn_buy.setEnabled(false);
    }

    @Override
    public void loadDataOver() {
        super.loadDataOver();
        btn_buy.setEnabled(true);
    }

    @Override
    public void onEventMainThread(Map<String, Object> map) {
        String type = map.get("type").toString();
//        ToastUtils.show(this,type);
        RedPackage red = (RedPackage) map.get("red");
        if (type.equals("1")) {  //红包
            tv1.setText(red.getType() + red.getMoney());
            tv2.setText("");
            if (red.getType().indexOf("抵") != -1) {
                Long value = Long.valueOf(tv_pay_money_value.getText().toString());
                tv_pay_money_value.setText(String.format("%.0f", value - Double.valueOf(red.getMoney())));
                tv_jian_money.setText(String.format("%.0f", -Double.valueOf(red.getMoney())));
            } else {
                tv_pay_money_value.setText(String.valueOf(investmentMoney));
                tv_jian_money.setText("-0");
            }
        } else {             //奖券
            tv2.setText(red.getType() + red.getMoney());
            tv1.setText("");
            tv_pay_money_value.setText(String.valueOf(investmentMoney));
            tv_jian_money.setText("-0");
        }
        this.red = red;
        EventBus.getDefault().removeStickyEvent(Map.class);
    }
    @Override
    public Object getObject() {
        return this;
    }
}
