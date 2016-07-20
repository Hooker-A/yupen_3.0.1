package com.huaop2p.yqs.module.three_mine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.adapter.CommonAdapter;
import com.huaop2p.yqs.module.base.adapter.ViewHolder;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.three_mine.model.entity.Investment;
import com.huaop2p.yqs.module.two_wealth.model.entity.Style;
import com.huaop2p.yqs.utils.DateUtils;
import com.huaop2p.yqs.utils.auto.AutoUtils;
import com.huaop2p.yqs.widget.CostomProgress;
import com.huaop2p.yqs.widget.CustomTextView;
import com.huaop2p.yqs.widget.MyTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/4/21.
 */
public class InvestmentAdapter extends CommonAdapter<Investment> {
    private int type;
    private List<Integer> positions = new ArrayList<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private LinearLayout.LayoutParams layoutParams;

    public InvestmentAdapter(List<Investment> list, Context context, int layoutId, int type) {
        super(list, context, layoutId);
        this.type = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Investment dataItem = list.get(position);
        viewHolder = ViewHolder.getViewHolder(context, convertView, parent, layoutId, position);
        CostomProgress cp = (CostomProgress) viewHolder.get(R.id.progressBar);
        TextView tv_surplus_day = (TextView) viewHolder.get(R.id.tv_surplus_day);
        MyTextView tv_flag = (MyTextView) viewHolder.get(R.id.tv_flag);
        TextView tv_LoanName = (TextView) viewHolder.get(R.id.tv_LoanName);
        TextView tv_sy = (TextView) viewHolder.get(R.id.tv_sy);
        tv_LoanName.setText(dataItem.ProductName);
        CustomTextView ct1 = (CustomTextView) viewHolder.get(R.id.c_tv1);
        TextView ct2 = (TextView) viewHolder.get(R.id.c_tv2);
        TextView ct3 = (TextView) viewHolder.get(R.id.c_tv3);
        TextView tv_inverstment_str = (TextView) viewHolder.get(R.id.tv_inverstment_str);
        String suffix = null;
        LinearLayout ll_flag = (LinearLayout) viewHolder.get(R.id.ll_flag);
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
        int color = Color.RED;
        switch (type) {
            case 1:    //房
                color = context.getResources().getColor(R.color.yufangbao);
                tv_flag.setText("房");
                cp.setBackgroundColorSelect(context.getResources().getColor(R.color.yufangbao));
                cp.setBackgroundColorDefault(context.getResources().getColor(R.color.yufangbao2));
                tv_inverstment_str.setText("期限");
                break;
            case 2:   //车
                color = context.getResources().getColor(R.color.yuchebao);
                tv_flag.setText("车");
                cp.setBackgroundColorSelect(context.getResources().getColor(R.color.yuchebao));
                cp.setBackgroundColorDefault(context.getResources().getColor(R.color.yuchebao2));
                tv_inverstment_str.setText("期限");
                break;
            case 3:   //信
                color = Color.RED;
                tv_flag.setText("信");
                cp.setBackgroundColorSelect(context.getResources().getColor(R.color.red));
                cp.setBackgroundColorDefault(context.getResources().getColor(R.color.pink));
                tv_inverstment_str.setText("封闭期");
                break;
        }

        if (layoutParams == null) {
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        ll_flag.removeAllViews();
        if (dataItem.AssetStyles != null) {
            for (int i = 0; i < dataItem.AssetStyles.size(); i++) {
                layoutParams.setMargins(5, 0, 5, 0);
                MyTextView textView = new MyTextView(context);
                textView.setLayoutParams(layoutParams);
                textView.setMinWidth(AutoUtils.getPercentHeightSize(30));
                textView.setMinHeight(AutoUtils.getPercentHeightSize(30));
                textView.setGravity(Gravity.CENTER);
                textView.setText(dataItem.AssetStyles.get(i).Font);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.small_content_size));
                textView.setPadding(5, 0, 5, 0);
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundColor(Color.parseColor("#" + dataItem.AssetStyles.get(i).Color));
                AutoUtils.autoTextSize(textView);
                ll_flag.addView(textView, 0);
            }
        }
        tv_flag.setBackgroundColor(color);
        ct1.setText(dataItem.LoanRate, ".", color);
        ct3.setText(String.valueOf(dataItem.LoanMoney) + "元");
        ct2.setText(dataItem.LoanTerm + suffix);
        ct2.setTextColor(color);
        ct3.setTextColor(color);
        float totalDay = 0;
        float NowDay = 0;
        int surplus = 0;
        if (!(dataItem.StartTime == null || dataItem.EndTime == null || dataItem.DateTimeNow == null)) {
            try {
                Date dateStart = DateUtils.sdf.parse(dataItem.StartTime);
                Date dateEnd = DateUtils.sdf.parse(dataItem.EndTime);
                Date dateNow = DateUtils.sdf.parse(dataItem.DateTimeNow);
                totalDay = DateUtils.subtract1(dateEnd, dateStart);
                NowDay = DateUtils.subtract1(dateNow, dateStart);
                surplus = DateUtils.subtract(dateEnd, dateNow);
                if (surplus < 0)
                    surplus = 0;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tv_surplus_day.setText(surplus + "天");

        } else {
            tv_surplus_day.setText("未起息");
            tv_sy.setVisibility(View.GONE);
            totalDay = -1;
            surplus=-1;
        }
        //   if (!positions.contains((Integer) position)) {
        if (surplus == 0) {
            cp.setProgress(100, 100);
        } else {
            cp.setProgress(NowDay, totalDay);
        }
        //  }
        //     positions.add((Integer) position);
        //       }

        tv_surplus_day.setTextColor(color);

        return viewHolder.getView();
    }

}
