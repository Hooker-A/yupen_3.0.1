package com.huaop2p.yqs.module.three_mine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.adapter.CommonAdapter;
import com.huaop2p.yqs.module.base.adapter.ViewHolder;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.three_mine.model.entity.Investment;
import com.huaop2p.yqs.widget.CostomProgress;
import com.huaop2p.yqs.widget.CustomTextView;
import com.huaop2p.yqs.widget.MyTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/17.
 * 作者：任洋
 * 功能：投资记录历史
 */
public class HistoryAdapter extends CommonAdapter<Investment> {
    private int type;
    private List<Integer> positions = new ArrayList<>();

    public HistoryAdapter(List list, Context context, int layoutId, int type) {
        super(list, context, layoutId);
        this.type = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Investment dataItem = list.get(position);
        viewHolder = ViewHolder.getViewHolder(context, convertView, parent, layoutId, position);
        CostomProgress cp = (CostomProgress) viewHolder.get(R.id.progressBar);
        MyTextView tv_flag = (MyTextView) viewHolder.get(R.id.tv_flag);
        TextView tv_LoanName = (TextView) viewHolder.get(R.id.tv_LoanName);
        TextView tv_inverstment_str= (TextView) viewHolder.get(R.id.tv_inverstment_str);
        tv_LoanName.setText(dataItem.ProductName);
        CustomTextView ct1 = (CustomTextView) viewHolder.get(R.id.c_tv1);
        TextView ct2 = (TextView) viewHolder.get(R.id.c_tv2);
        TextView ct3 = (TextView) viewHolder.get(R.id.c_tv3);

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
        tv_flag.setBackgroundColor(color);
        ct3.setText(dataItem.LoanMoney + "元");
        ct1.setText(String.valueOf(dataItem.LoanRate), ".", color);
        ct2.setText(dataItem.LoanTerm + suffix);
        ct2.setTextColor(color);
        ct3.setTextColor(color);
        if (!positions.contains((Integer) position)) {
            cp.setProgress(100, 100);
            positions.add((Integer) position);
        }
        return viewHolder.getView();
    }
}
