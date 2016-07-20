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
import com.huaop2p.yqs.module.three_mine.model.entity.TransferRecord;
import com.huaop2p.yqs.widget.CostomProgress;
import com.huaop2p.yqs.widget.CustomTextView;
import com.huaop2p.yqs.widget.MyTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/17.
 * 作者：任洋
 * 功能：
 */
public class TransferAdapter extends CommonAdapter<TransferRecord> {
    private List<Integer> positions = new ArrayList<>();
    private String type;

    public TransferAdapter(List list, Context context, int layoutId,String type) {
        super(list, context, layoutId);
        this.type=type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder = ViewHolder.getViewHolder(context, convertView, parent, layoutId, position);
        TransferRecord dataItem = list.get(position);
        CostomProgress cp = (CostomProgress) viewHolder.get(R.id.progressBar);
        TextView tv_surplus_day = (TextView) viewHolder.get(R.id.tv_surplus_day);
        MyTextView tv_flag = (MyTextView) viewHolder.get(R.id.tv_flag);
        TextView tv_LoanName = (TextView) viewHolder.get(R.id.tv_LoanName);
        tv_LoanName.setText(dataItem.Name);
        CustomTextView ct1 = (CustomTextView) viewHolder.get(R.id.c_tv1);
        CustomTextView ct2 = (CustomTextView) viewHolder.get(R.id.c_tv2);
        CustomTextView ct3 = (CustomTextView) viewHolder.get(R.id.c_tv3);
        String suffix = null;
        int totalDay = 0;
        switch (dataItem.InvestmentTermType) {
            case State.DAY:
                suffix = "天";
                totalDay = dataItem.InvestmentTerm;
                break;
            case State.MONTH:
                suffix = "个月";
                totalDay = dataItem.InvestmentTerm * 30;
                break;
            case State.YEAR:
                suffix = "年";
                totalDay = dataItem.InvestmentTerm * 30 * 12;
                break;
        }
        int color = 0;
        if (type.equals(State.TRANSFER_END)) {
            color = context.getResources().getColor(R.color.textcolor);
            cp.setBackgroundColorSelect(context.getResources().getColor(R.color.textcolor));
            cp.setBackgroundColorDefault(context.getResources().getColor(R.color.qian_grey));
        }else if (dataItem.YupenProductInco.Font.equals("信")) {
            color = Color.RED;
            tv_flag.setText("信");
            cp.setBackgroundColorSelect(context.getResources().getColor(R.color.red));
            cp.setBackgroundColorDefault(context.getResources().getColor(R.color.pink));
        } else if (dataItem.YupenProductInco.Font.equals("房")) {
            color = context.getResources().getColor(R.color.yufangbao);
            tv_flag.setText("房");
            cp.setBackgroundColorSelect(context.getResources().getColor(R.color.yufangbao));
            cp.setBackgroundColorDefault(context.getResources().getColor(R.color.yufangbao2));
        } else if (dataItem.YupenProductInco.Font.equals("车")) {
            color = context.getResources().getColor(R.color.yuchebao);
            tv_flag.setText("车");
            cp.setBackgroundColorSelect(context.getResources().getColor(R.color.yuchebao));
            cp.setBackgroundColorDefault(context.getResources().getColor(R.color.yuchebao2));
        }
        tv_flag.setBackgroundColor(color);
        ct1.setText(Float.valueOf(dataItem.InvestmentMoney) + "元", "元", color);
        ct2.setText(dataItem.YearRate + "%", "%", color);
        ct3.setText(dataItem.InvestmentTerm + suffix, suffix, color);
        tv_surplus_day.setText(dataItem.OtherDay + "天");
        tv_surplus_day.setTextColor(color);
        if (!positions.contains((Integer) position)) {
            cp.setProgress(totalDay - dataItem.OtherDay, totalDay);
            positions.add((Integer) position);
        }
        return viewHolder.getView();
    }
}
