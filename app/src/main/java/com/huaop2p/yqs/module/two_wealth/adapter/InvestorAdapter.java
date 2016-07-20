package com.huaop2p.yqs.module.two_wealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.adapter.CommonAdapter;
import com.huaop2p.yqs.module.base.adapter.ViewHolder;
import com.huaop2p.yqs.module.two_wealth.model.entity.Investor;

import java.util.List;

/**
 * Created by Administrator on 2016/5/11.
 * 作者：任洋
 * 功能：
 */
public class InvestorAdapter extends CommonAdapter<Investor> {
    private static final String YUAN="元";
    public InvestorAdapter(List list, Context context, int layoutId) {
        super(list, context, layoutId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Investor investor=list.get(position);
        viewHolder= ViewHolder.getViewHolder(context,convertView,parent,layoutId,position);
        TextView tv_name= (TextView) viewHolder.get(R.id.tv_name);
        TextView tv_date= (TextView) viewHolder.get(R.id.tv_date);
        TextView tv_money= (TextView) viewHolder.get(R.id.tv_money);
        tv_name.setText(investor.Name);
        tv_money.setText(String.format("%.2f",investor.Money)+YUAN);
        tv_date.setText(investor.Time);
        return viewHolder.getView();
    }
}
