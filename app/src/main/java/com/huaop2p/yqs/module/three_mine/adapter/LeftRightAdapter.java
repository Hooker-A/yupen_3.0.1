package com.huaop2p.yqs.module.three_mine.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.adapter.CommonAdapter;
import com.huaop2p.yqs.module.base.adapter.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/6/14.
 * 作者：任洋
 * 功能：
 */
public class LeftRightAdapter extends CommonAdapter<SparseArray<Object>> {
    public LeftRightAdapter(List<SparseArray<Object>> list, Context context, int layoutId) {
        super(list, context, layoutId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder = ViewHolder.getViewHolder(context, convertView, parent, R.layout.item_left_right, position);
        SparseArray<Object> sa = list.get(position);
        Object left = sa.get(0);
        Object right = sa.get(1);
        TextView tv_left = (TextView) viewHolder.get(R.id.tv_left);
        TextView tv_right = (TextView) viewHolder.get(R.id.tv_right);
        if (left != null)
            tv_left.setText(left.toString());
        else
            tv_left.setText("");
        if (right != null)
            tv_right.setText(right.toString());
        else
            tv_right.setText("");
        return viewHolder.getView();
    }
}
