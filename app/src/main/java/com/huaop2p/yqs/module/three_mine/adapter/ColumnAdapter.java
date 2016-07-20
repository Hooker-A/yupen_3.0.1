package com.huaop2p.yqs.module.three_mine.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.adapter.CommonAdapter;
import com.huaop2p.yqs.module.base.adapter.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public class ColumnAdapter extends CommonAdapter<SparseArray> {
    public ColumnAdapter(List<SparseArray> list, Context context, int layoutId) {
        super(list, context, layoutId);
    }

    @Override
    public int getCount() {
        return super.getCount() + 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (position >= list.size()) {
            view = new LinearLayout(context);
        } else {
            viewHolder = ViewHolder.getViewHolder(context, convertView, parent, layoutId, position);
            SparseArray sa = list.get(position);
            String name = (String) sa.get(0);
            int res = (int) sa.get(1);
            TextView tv_name = (TextView) viewHolder.get(R.id.tv_name);
            ImageView iv_head = (ImageView) viewHolder.get(R.id.iv_head);
            tv_name.setText(name);
            iv_head.setImageResource(res);
            view = viewHolder.getView();
        }
        if ((position + 1) % 4 == 0) {
            view.setBackgroundResource(R.drawable.downline);
        } else {
            view.setBackgroundResource(R.drawable.rightdown);
        }
        return view;
    }
}
