package com.huaop2p.yqs.module.three_mine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.three_mine.model.entity.BankCardBean;

import java.util.List;

/**
 * Created by maoxiaofei on 2016/6/1.
 */
public class RecycleviewBackcardAdapter extends RecyclerView.Adapter {
    private String TAG = RecycleViewAdapter.class.getSimpleName();
    private List<BankCardBean> list;

    public RecycleviewBackcardAdapter(List<BankCardBean > list){
            this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bankcard, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.position = position;
        BankCardBean bean = new BankCardBean();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_bankname;
        public TextView tv_bankcardnumber;

        public int position;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_bankname = (TextView) itemView.findViewById(R.id.tv_bankname);
            tv_bankcardnumber = (TextView) itemView.findViewById(R.id.tv_bankcardnumber);

        }
    }
}
