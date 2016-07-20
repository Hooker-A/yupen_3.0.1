package com.huaop2p.yqs.module.two_wealth.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.adapter.CommonAdapter;
import com.huaop2p.yqs.module.base.adapter.ViewHolder;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.two_wealth.model.entity.Style;
import com.huaop2p.yqs.module.two_wealth.model.entity.WealthCenter;
import com.huaop2p.yqs.utils.auto.AutoUtils;
import com.huaop2p.yqs.widget.CustomTextView;
import com.huaop2p.yqs.widget.MyTextView;

import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public class WealthAdapter extends CommonAdapter<WealthCenter> {
    private int type;
    private StringBuffer sb;
    private LinearLayout.LayoutParams layoutParams;

    public WealthAdapter(List<WealthCenter> list, Context context, int layoutId, int type) {
        super(list, context, layoutId);
        this.type = type;
        sb = new StringBuffer();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder = ViewHolder.getViewHolder(context, convertView, parent, layoutId, position);
        WealthCenter dataItem = list.get(position);
        TextView tv_LoanName = (TextView) viewHolder.get(R.id.tv_LoanName);
        MyTextView tv_flag = (MyTextView) viewHolder.get(R.id.tv_flag);
        tv_LoanName.setText(dataItem.Name);
        TextView c_tv3 = (TextView) viewHolder.get(R.id.c_tv3);
        TextView tv_income = (TextView) viewHolder.get(R.id.tv_income);
        TextView tv_inverstment_str= (TextView) viewHolder.get(R.id.tv_inverstment_str);
        tv_income.setText(dataItem.TopoTwo);
        if (dataItem.Amount == null)
            dataItem.Amount = "0";
        double text = Double.valueOf(dataItem.Amount);
        TextView c_tv2 = (TextView) viewHolder.get(R.id.c_tv2);
        String suffix = null;
        switch (dataItem.LoanTermId) {
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
        c_tv2.setText(String.valueOf(dataItem.LoanTerm) + suffix);
        CustomTextView c_tv1 = (CustomTextView) viewHolder.get(R.id.c_tv1);

        ImageView iv_state = (ImageView) viewHolder.get(R.id.iv_state);

        int color = Color.RED;
        switch (type) {
            case 1:    //房
                color = context.getResources().getColor(R.color.yufangbao);
                c_tv3.setText(sb.append(String.format("%.2f", text)).append("元").toString());
                updateState(dataItem, iv_state, true);
                tv_flag.setText("房");
                tv_inverstment_str.setText("期限");
                break;
            case 2:   //车
                color = context.getResources().getColor(R.color.yuchebao);
                c_tv3.setText(sb.append(String.format("%.2f", text)).append("元").toString());
                updateState(dataItem, iv_state, true);
                tv_flag.setText("车");
                tv_inverstment_str.setText("期限");
                break;
            case 3:   //信
                color = Color.RED;
                c_tv3.setText(sb.append(String.format("%.2f", text / 20)).append("万元").toString());
                updateState(dataItem, iv_state, false);
                tv_flag.setText("信");
                tv_inverstment_str.setText("封闭期");
                break;
        }
        sb.delete(0, sb.length());
        tv_flag.setBackgroundColor(color);
        LinearLayout ll_flag = (LinearLayout) viewHolder.get(R.id.ll_flag);
        ll_flag.removeAllViews();
        if (dataItem.AssetStyles != null) {
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
            layoutParams.setMargins(5, 0, 5, 0);
            for (int i = 0; i < dataItem.AssetStyles.size(); i++) {

                Style style = dataItem.AssetStyles.get(i);
                if (style.Font.equals("信") || style.Font.equals("车") || style.Font.equals("房")) {
                    continue;
                }
                MyTextView textView = new MyTextView(context);
                textView.setLayoutParams(layoutParams);
                textView.setMinWidth(AutoUtils.getPercentHeightSize(30));
                textView.setMinHeight(AutoUtils.getPercentHeightSize(30));
                textView.setGravity(Gravity.CENTER);
                textView.setText(style.Font);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.small_content_size));
                textView.setPadding(5, 0, 5, 0);
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundColor(Color.parseColor(sb.append("#").append(style.Color).toString()));
                AutoUtils.autoTextSize(textView);
                sb.delete(0, sb.length());
                ll_flag.addView(textView, 0);
            }
        }
        String flag = null;
        if (dataItem.TopoOne.indexOf(".") != -1 && dataItem.TopoOne.indexOf("%") > dataItem.TopoOne.indexOf(".")) {
            flag = ".";
        } else {
            flag = "%";
        }
        Log.i("ggg",dataItem.TopoOne);
        c_tv1.setText(dataItem.TopoOne, flag, color);
        c_tv2.setTextColor(color);
        c_tv3.setTextColor(color);

        return viewHolder.getView();
    }

    /**
     * 根据状态显示图片
     **/
    public void updateState(WealthCenter dataItem, ImageView iv_state, boolean isTrue) {
        if (!isTrue) {
            if (dataItem.LoanState.equals(State.BUY)) {
                iv_state.setBackgroundResource(R.drawable.tou);
            } else if (dataItem.LoanState.equals(State.MAN_BIAO)) {
                iv_state.setBackgroundResource(R.drawable.man);
            } else if (dataItem.LoanState.equals(State.DAI_SHOU)) {
                iv_state.setBackgroundResource(R.drawable.jijiangqishou);
            }
        } else {
            if (dataItem.LoanState.equals(State.YU_SHOU)) {
                iv_state.setBackgroundResource(R.drawable.tou);
            } else if (dataItem.LoanState.equals(State.INVESTMENT)) {
                iv_state.setBackgroundResource(R.drawable.tou);
            } else if (dataItem.LoanState.equals(State.SETTLE)) {
                iv_state.setBackgroundResource(R.drawable.yiwancheng);
            } else if (dataItem.LoanState.equals(State.REPAYMENT)) {
                iv_state.setBackgroundResource(R.drawable.huankuanzhong);
            } else if (dataItem.LoanState.equals(State.TRANSFER)) {
                iv_state.setBackgroundResource(R.drawable.yimanbiao);
            }
        }
    }
}
