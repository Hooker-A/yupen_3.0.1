package com.huaop2p.yqs.module.two_wealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.adapter.CommonAdapter;
import com.huaop2p.yqs.module.base.adapter.ViewHolder;
import com.huaop2p.yqs.module.three_mine.model.entity.Borrower;
import com.huaop2p.yqs.utils.auto.AutoUtils;
import com.huaop2p.yqs.widget.CustomTextView;
import com.huaop2p.yqs.widget.MyTextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/15.
 * 作者：任洋
 * 功能：借款人信息
 */
public class BorrowerInfoAdapter extends CommonAdapter<Borrower> {

    public BorrowerInfoAdapter(List<Borrower> list, Context context, int layoutId) {
        super(list, context, layoutId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Borrower b = list.get(position);
        viewHolder = ViewHolder.getViewHolder(context, convertView, parent, layoutId, position);
        MyTextView m_tv = (MyTextView) viewHolder.get(R.id.tv_mtextView);
        m_tv.setBackgroundColor(context.getResources().getColor(R.color.red));
        TextView tv_LoanName = (TextView) viewHolder.get(R.id.tv_LoanName);
        tv_LoanName.setText(b.Title);
        LinearLayout ll = (LinearLayout) viewHolder.get(R.id.ll_list);
        List<Map<String, String>> maps = b.List;
        ll.removeAllViews();
        if (maps != null) {
            for (int i = 0; i < maps.size(); i++) {
                LayoutInflater.from(context).inflate(R.layout.item_borrower_adapter, ll);
                View view = ll.getChildAt(i);
                TextView tv_key = (TextView) view.findViewById(R.id.tv_key);
                TextView tv_value = (TextView) view.findViewById(R.id.tv_value);
                String key = maps.get(i).get("Key");
                String value = maps.get(i).get("Value");
                tv_key.setText(key);
                tv_value.setText(value);

            }
            AutoUtils.initLayoutSize(ll,context);
        }
        return viewHolder.getView();
    }
}
