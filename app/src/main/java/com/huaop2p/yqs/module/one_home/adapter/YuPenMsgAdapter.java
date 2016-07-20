package com.huaop2p.yqs.module.one_home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.one_home.bean.HomeMseZiXunBean;

import java.util.List;

/**
 * Created by maoxiaofei on 2016/5/10.
 */
public class YuPenMsgAdapter extends BaseAdapter {

    private int type;
    List<HomeMseZiXunBean> list;
    Context context;
    int layoutId;

    public YuPenMsgAdapter(List<HomeMseZiXunBean> list, Context context,int type) {
        this.type = type;
        this.list = list;
        this.context = context;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HomeMseZiXunBean listviewBean = (HomeMseZiXunBean) getItem(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.home_listviewitem2,null);
            viewHolder = new ViewHolder();
            viewHolder.textview1 = (TextView) convertView.findViewById(R.id.tv_gonggao);
            viewHolder.textview2 = (TextView) convertView.findViewById(R.id.tv_love);
            viewHolder.textview3 = (TextView) convertView.findViewById(R.id.text_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.textview1.setText("【" + listviewBean.ClassCName + "】");
        viewHolder.textview2.setText(listviewBean.NewsTitle);
        viewHolder.textview2.setSingleLine(true);
        viewHolder.textview2.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.textview3.setText(listviewBean.CreatTime);


        return convertView;
    }

    class ViewHolder{
        TextView textview1;
        TextView textview2;
        TextView textview3;
    }
}
