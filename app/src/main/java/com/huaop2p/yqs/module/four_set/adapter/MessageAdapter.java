package com.huaop2p.yqs.module.four_set.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.four_set.entity.ResPushMsg;

import java.util.List;

/**
 * Created by zgq on 2016/5/19.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/19 15:01
 * 功能:  消息推送 adapter
 */
public class MessageAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ResPushMsg> list;

    public MessageAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<ResPushMsg> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public ResPushMsg getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.message_list_item, null);
            vh = new ViewHolder();
            vh.name = (TextView) convertView.findViewById(R.id.txt_name);
            vh.date = (TextView) convertView.findViewById(R.id.txt_date);
            vh.content = (TextView) convertView.findViewById(R.id.txt_content);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        ResPushMsg message = getItem(position);
        vh.name.setText(message.getTitle());
        vh.date.setText(message.getSendTime());
        vh.content.setText(message.getMessage());
        return convertView;
    }

    private class ViewHolder {
        TextView name;
        TextView date;
        TextView content;
    }

}
