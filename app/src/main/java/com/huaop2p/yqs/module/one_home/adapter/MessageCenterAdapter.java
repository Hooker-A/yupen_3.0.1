package com.huaop2p.yqs.module.one_home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.one_home.bean.MsgCenterBean;

import java.util.List;

/**
 * Created by maoxiaofei on 2016/7/1.
 */
public class MessageCenterAdapter extends BaseAdapter{

    private List<MsgCenterBean> list;
    private LayoutInflater layoutInflater;
    private Context context;

    public MessageCenterAdapter(Context context,List<MsgCenterBean> list){
        this.list = list;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
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

        Viewholder viewholder = null;
        if (convertView == null){
            viewholder = new Viewholder();
            // 获取组件布局
            convertView = layoutInflater.inflate(R.layout.item_msgrecycleview,null);


            viewholder.tv_msg_time = (TextView) convertView.findViewById(R.id.tv_msg_time);
            viewholder.tv_msg_proname = (TextView) convertView.findViewById(R.id.tv_msg_proname);
            viewholder.tv_msg_content = (TextView) convertView.findViewById(R.id.tv_msg_content);
            viewholder.tv_protitle = (TextView) convertView.findViewById(R.id.tv_protitle);

            // 这里要注意，是使用的tag来存储数据的。
            convertView.setTag(viewholder);
        }else{
            viewholder = (Viewholder) convertView.getTag();
        }
        MsgCenterBean msgCenterBean = list.get(position);
        viewholder.tv_protitle.setText("【"+msgCenterBean.getTypeName()+"】");
        viewholder.tv_msg_time.setText(msgCenterBean.getTime());
        viewholder.tv_msg_proname.setText(msgCenterBean.getTitle());
        viewholder.tv_msg_content.setText(msgCenterBean.getContent());

        return convertView;
    }

    class Viewholder{
        public TextView tv_msg_time;
        public TextView tv_msg_proname;
        public TextView tv_msg_content;
        private TextView tv_protitle;
    }
}
