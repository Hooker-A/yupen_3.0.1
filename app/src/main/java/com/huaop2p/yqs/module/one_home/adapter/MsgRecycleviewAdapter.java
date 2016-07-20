package com.huaop2p.yqs.module.one_home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.one_home.bean.MsgCenterBean;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by maoxiaofei on 2016/5/19.
 */
public class MsgRecycleviewAdapter extends RecyclerView.Adapter {

    private List<MsgCenterBean> list;
    public MsgRecycleviewAdapter(List<MsgCenterBean> list){
        this.list = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msgrecycleview,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;


        viewHolder.position = position;
        MsgCenterBean msgCenterBean = list.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        viewHolder.tv_protitle.setText("【"+msgCenterBean.getTypeName()+"】");
        viewHolder.tv_msg_time.setText(msgCenterBean.getTime());
        viewHolder.tv_msg_proname.setText(msgCenterBean.getTitle());
        viewHolder.tv_msg_content.setText(msgCenterBean.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_msg_time;
        public TextView tv_msg_proname;
        public TextView tv_msg_content;
        private TextView tv_protitle;

        public int position;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_msg_time = (TextView) itemView.findViewById(R.id.tv_msg_time);
            tv_msg_proname = (TextView) itemView.findViewById(R.id.tv_msg_proname);
            tv_msg_content = (TextView) itemView.findViewById(R.id.tv_msg_content);
            tv_protitle = (TextView) itemView.findViewById(R.id.tv_protitle);
        }
    }
}
