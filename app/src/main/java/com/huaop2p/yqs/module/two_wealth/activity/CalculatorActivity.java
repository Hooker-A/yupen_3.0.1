package com.huaop2p.yqs.module.two_wealth.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseAutoActivity;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.two_wealth.view.ICalculatorView;

/**
 * Created by Administrator on 2016/5/5.
 * 作者：任洋
 * 功能：计算器
 */
public class CalculatorActivity extends BaseAutoActivity implements ICalculatorView {
    private EditText et_money;
    private TextView title, tv_date,tv_caifutong,tv_yuebao,tv_dingqi,tv_huoqi,tv_topOne,tv_inverstment_str;

    private double caifutong, yuebao, dingqi, huoqi, topOne;
    private  int investmentTime;
    public  String date;

    @Override
    public int getLayoutId() {
        return R.layout.activity_calculator;
    }
    @Override
    public Object getObject() {
        return this;
    }

    @Override
    public void initViews() {
        tv_date= (TextView) findViewById(R.id.tv_date);
        tv_caifutong= (TextView) findViewById(R.id.tv_caifutong);
        tv_inverstment_str= (TextView) findViewById(R.id.tv_inverstment_str);
        tv_yuebao= (TextView) findViewById(R.id.tv_yuebao);
        tv_dingqi= (TextView) findViewById(R.id.tv_dingqi);
        tv_huoqi= (TextView) findViewById(R.id.tv_huoqi);
        tv_topOne= (TextView) findViewById(R.id.tv_topOne);
        title = (TextView) findViewById(R.id.title);
        et_money = (EditText) findViewById(R.id.et_money);
        et_money.setSelection(et_money.length());
        et_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double value=0;
                if (!"".equals(et_money.getText().toString())) {
                     value = Double.valueOf(et_money.getText().toString());
                }
                updateIncomeByContent(value);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        rootView.setPadding((root.getHeight() - root.getWidth() + 1000) / 2, 500, (root.getHeight() - root.getWidth() + 1000) / 2, 500);
//        rootView.getLayoutParams().height =  root.getHeight()+1000;
//        rootView.getLayoutParams().width= root.getHeight()+1000;
//        root.requestLayout();
//        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 0f,0.3f,
//              1f);
//        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY",0f,0.3f,
//                1f);
//        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(rootView, pvhX, pvhY);
//        animator.setInterpolator(new AccelerateInterpolator());
//        animator.setDuration(1000).start();
//        ValueAnimator valueAnimator = ValueAnimator.ofInt(AutoUtils.getPercentWidthSize(100), root.getMeasuredWidth(), root.getMeasuredHeight(), root.getMeasuredHeight() + 1000);
//        valueAnimator.setDuration(500);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int value = Integer.parseInt(animation.getAnimatedValue().toString());
//                rootView.getLayoutParams().height = value;
//                if (value > root.getMeasuredHeight()) {
//                    rootView.setPadding(rootView.getPaddingLeft(), (value - root.getMeasuredHeight()) / 2, rootView.getPaddingRight(), (value - root.getMeasuredHeight()) / 2);
//                }
//                rootView.requestLayout();
//            }
//        });
//        ValueAnimator valueAnimator2 = ValueAnimator.ofInt(AutoUtils.getPercentWidthSize(100), root.getMeasuredWidth(), root.getMeasuredHeight(), root.getMeasuredWidth() + 1000);
//        valueAnimator2.setDuration(500);
//        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int value = Integer.parseInt(animation.getAnimatedValue().toString());
//                rootView.getLayoutParams().width = value;
//                if (value > root.getMeasuredWidth()) {
//                    rootView.setPadding((value - root.getMeasuredWidth()) / 2, rootView.getPaddingTop(), (value - root.getMeasuredWidth()) / 2, rootView.getPaddingBottom());
//                }
//
//                rootView.requestLayout();
//            }
//        });
//        valueAnimator.setInterpolator(new AccelerateInterpolator());
//        valueAnimator2.setInterpolator(new AccelerateInterpolator());
//        valueAnimator.start();
//        valueAnimator2.start();
    }

    @Override
    public void initData() {
        caifutong = getIntent().getDoubleExtra("caifutong", 0);
        yuebao = getIntent().getDoubleExtra("yuebao", 0);
        dingqi = getIntent().getDoubleExtra("dingqi", 0);
        huoqi = getIntent().getDoubleExtra("huoqi", 0);
        topOne = getIntent().getDoubleExtra("topOne", 0);
        investmentTime=getIntent().getIntExtra("investmentTime", 0);
        date=getIntent().getStringExtra("date");
        int type=getIntent().getIntExtra("type", State.YUXINBAO);
        switch (type){
            case  State.YUXINBAO:
                tv_inverstment_str.setText("封闭期");
                break;
            default:
                tv_inverstment_str.setText("期限");
                break;
        }
        tv_date.setText(date);
    }

    @Override
    public void updateIncomeByContent(double value) {
        tv_topOne.setText(String.format("%.2f",value*topOne*investmentTime/360));
        tv_caifutong.setText(String.format("%.2f",value*caifutong*investmentTime/360));
        tv_dingqi.setText(String.format("%.2f",value*dingqi*investmentTime/360));
        tv_huoqi.setText(String.format("%.2f",value*huoqi*investmentTime/360));
        tv_yuebao.setText(String.format("%.2f",value*yuebao*investmentTime/360));
        et_money.setSelection(et_money.length());

    }
}
