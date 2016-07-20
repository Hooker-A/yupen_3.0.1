package com.huaop2p.yqs.module.three_mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.adapter.CommonAdapter;
import com.huaop2p.yqs.module.base.adapter.ViewHolder;
import com.huaop2p.yqs.module.three_mine.model.entity.PaymentHistory;

import java.util.List;

/**
 * Created by Administrator on 2016/5/13.
 * 作者：任洋
 * 功能：
 */
public class PaymentHistoryAdapter extends CommonAdapter<PaymentHistory> {

    public PaymentHistoryAdapter(List list, Context context, int layoutId) {
        super(list, context, layoutId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PaymentHistory pa   =list.get(position);
        viewHolder= ViewHolder.getViewHolder(context,convertView,parent,layoutId,position);

        TextView tv_count= (TextView) viewHolder.get(R.id.tv_count);
        TextView tv_date= (TextView) viewHolder.get(R.id.tv_date);
        TextView tv_benjin= (TextView) viewHolder.get(R.id.tv_benjin);
        TextView tv_interest= (TextView) viewHolder.get(R.id.tv_interest);

        tv_count.setText(position+1+"/"+list.size());
        tv_date.setText(pa.RepaymenTime);
        tv_benjin.setText(String.format("%.2f",pa.RepayMoney)+"元");
        tv_interest.setText(String.format("%.2f",pa.RepayInterest)+"元");
        return viewHolder.getView();
    }
}
