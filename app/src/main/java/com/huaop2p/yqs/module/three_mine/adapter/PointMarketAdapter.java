package com.huaop2p.yqs.module.three_mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.adapter.CommonAdapter;
import com.huaop2p.yqs.module.base.adapter.ViewHolder;
import com.huaop2p.yqs.module.three_mine.model.entity.ThirtyDayPointBean;

import java.util.List;

/**
 * Created by maoxiaofei on 2016/5/16.
 */
public class PointMarketAdapter extends CommonAdapter<ThirtyDayPointBean> {

    private String DiscountSource;

    public PointMarketAdapter(List<ThirtyDayPointBean> list, Context context, int layoutId) {
        super(list, context, layoutId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //这里viewholder进行封装。传入几个形参，在调用adapter的时候 进行传入具体的实例
        viewHolder = ViewHolder.getViewHolder(context, convertView, parent, layoutId, position);
        ThirtyDayPointBean  thirtyDayPointBean = list.get(position);
        TextView text1 = (TextView) viewHolder.get(R.id.tv_historytime);
        TextView text2 = (TextView) viewHolder.get(R.id.tv_state);
        TextView text3 = (TextView) viewHolder.get(R.id.tv_poinnt);


        text1.setText(thirtyDayPointBean.getCreateTime());

        if (thirtyDayPointBean.getDiscountSource()==null||thirtyDayPointBean.getDiscountSource().equals("")) {
            DiscountSource = "5";
        }else  {
            DiscountSource= thirtyDayPointBean.getDiscountSource();
        }

        switch (DiscountSource){
            case "1":
                text2.setText("购买");
                break;
            case "2":
                text2.setText("活动");
                break;
            case "3":
                text2.setText("签到");
                break;
            case "4":
                text2.setText("积分商城兑换");
                break;
            case "5":
                text2.setText("");
                break;
            default:
                text2.setText("");
                break;
        }

        switch (thirtyDayPointBean.getType()){
            case "1":
                text3.setText("+"+thirtyDayPointBean.getAmount());
                break;
            case "2":
                text3.setText("-"+thirtyDayPointBean.getAmount());
        }

        return viewHolder.getView();
    }
}
