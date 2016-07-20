package com.huaop2p.yqs.module.three_mine.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.adapter.ViewHolder;
import com.huaop2p.yqs.module.three_mine.model.entity.LendInfo;


public class MyAdapter extends BaseExpandableListAdapter {
    List<List<LendInfo>> lendInfos = null;
    private Context context;
    public int position = -1;
    private StringBuffer sb;

    public MyAdapter(List<List<LendInfo>> lendInfos,
                     Context context) {
        this.lendInfos = lendInfos;
        this.context = context;
        views = new SparseArray<>();
        sb = new StringBuffer();
    }

    public SparseArray<View> views;

    // 得到子item需要关联的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return lendInfos.get(groupPosition).get(childPosition);
    }

    // 得到子item的ID
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    // 设置子item的组件
    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        LendInfo lendInfo = lendInfos.get(groupPosition).get(childPosition);
        ViewHolder viewHolder = ViewHolder.getViewHolder(context, convertView, parent, R.layout.item_children, childPosition);
        TextView tv_name_value = (TextView) viewHolder.get(R.id.tv_name_value);
        TextView tv_loan_value = (TextView) viewHolder.get(R.id.tv_loan_value);
        TextView tv_purpose_value = (TextView) viewHolder.get(R.id.tv_purpose_value);
        TextView tv_ID_card_value = (TextView) viewHolder.get(R.id.tv_ID_card_value);
        TextView tv_hold_date_value = (TextView) viewHolder.get(R.id.tv_hold_date_value);
        sb.append(lendInfo.LoanPersonName.substring(0, 1));
        for (int j = 0; j < lendInfo.LoanPersonName.length() - 1; j++) {
            sb.append("*");
        }
        tv_name_value.setText(sb);
        sb.delete(0, sb.length());
        tv_loan_value.setText(String.format("%.2f", Double.valueOf(lendInfo.LendMoney)) + "元");
        tv_purpose_value.setText(lendInfo.LoanApplication);
        String[] LoanCards = lendInfo.LoanCard.split(" ");
        for (int i = 0; i < LoanCards.length; i++) {
            sb.append(LoanCards[i].substring(0, 3)).append("***********").append(LoanCards[i].substring(14));
            sb.append("\t");
        }
        tv_ID_card_value.setText(sb);
        sb.delete(0, sb.length());
        tv_hold_date_value.setText(lendInfo.Stime + "~" + lendInfo.Etime);
        return viewHolder.getView();
    }

    // 获取当前父item下的子item的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return lendInfos.get(groupPosition).size();
    }

    // 获取当前父item的数据
    @Override
    public Object getGroup(int groupPosition) {
        return lendInfos.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return lendInfos.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // 设置父item组件
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(context, convertView, parent, R.layout.item_parent, groupPosition);
        TextView tv = (TextView) viewHolder.get(R.id.tv_title);
        tv.setText("第" + (groupPosition + 1) + "个月");
        ImageView iv = (ImageView) viewHolder.get(R.id.iv_down);
        if (isExpanded) {
            iv.setImageResource(R.drawable.black_up);
        } else {
            iv.setImageResource(R.drawable.black_down);
        }
        views.append(groupPosition, viewHolder.getView());
        return views.get(groupPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void refresh(List<List<LendInfo>> lendInfos) {
        this.lendInfos = lendInfos;
        this.notifyDataSetChanged();
    }

}