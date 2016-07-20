package com.huaop2p.yqs.module.three_mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.three_mine.model.entity.RedItemBean;
import com.huaop2p.yqs.module.three_mine.model.entity.SiteBean;

import java.util.List;

/**
 * Created by maoxiaofei on 2016/7/7.
 */
public class RedItemAdapter extends BaseAdapter{

    private Context context;
    private List<RedItemBean> list;
    private LayoutInflater layoutInflater;
    private RedItemBean itemBean;

    public RedItemAdapter(List<RedItemBean> list,Context context){
        this.context=context;
        this.list=list;
        this.layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list!=null?list.size():0;
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
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.activity_redgz_item, null);
            viewHolder.txt_cp= (TextView) convertView.findViewById(R.id.txt_cp);
            viewHolder.txt_je= (TextView) convertView.findViewById(R.id.txt_je);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        itemBean=list.get(position);
        viewHolder.txt_cp.setText(itemBean.Name);

        viewHolder.txt_je.setText(itemBean.Money);
        return convertView;
    }
    static class ViewHolder {

        TextView txt_cp, txt_je;
    }
}
