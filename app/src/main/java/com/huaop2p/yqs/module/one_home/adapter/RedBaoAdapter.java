package com.huaop2p.yqs.module.one_home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.one_home.bean.HongbaoItemBean;
import com.huaop2p.yqs.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by maoxiaofei on 2016/6/28.
 */
public class RedBaoAdapter extends BaseAdapter {
    private Context context;
    private List<HongbaoItemBean> mList;

    public RedBaoAdapter(Context context, List<HongbaoItemBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.item_hongbao_list,null);
            holder.textAmount= (TextView) convertView.findViewById(R.id.txt_amount);
            holder.textDate= (TextView) convertView.findViewById(R.id.txt_start_time);
            holder.textExpiration= (TextView) convertView.findViewById(R.id.txt_end_time);
            holder.llBackground=(LinearLayout)convertView.findViewById(R.id.ll_bg);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        HongbaoItemBean bean= (HongbaoItemBean) getItem(position);
        String s=bean.RedStartTime.split(" ")[0];
        String t=bean.RedEndTime.split(" ")[0];
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String Xdate=sdf.format(new java.util.Date());
        String Edate=bean.RedEndTime;
        Date dateEndTime= DateUtils.Parse2Date(Edate, DateUtils.yyyyMMdd);
        Date dataStar=DateUtils.Parse2Date(Xdate,DateUtils.yyyyMMdd);
        holder.textAmount.setText(bean.RedMoney+"");
        holder.textDate.setText("（"+s+"）");
        holder.textExpiration.setText(t);
        if (dateEndTime.getTime() < dataStar.getTime()){
            holder.llBackground.setBackgroundResource(R.drawable.redtow);

        }else {
            switch (bean.IsUse){
                case 0:
                    holder.llBackground.setBackgroundResource(R.drawable.redone);
                    break;
                case 1:
                    holder.llBackground.setBackgroundResource(R.drawable.redthree);
                    break;
                case 2:
                    holder.llBackground.setBackgroundResource(R.drawable.redtow);
                    break;
            }
        }

        return convertView;
    }
    class ViewHolder{
        TextView textAmount;  //金额
        TextView textDate;    //日期
        TextView textExpiration; //有效日期
        LinearLayout llBackground;
    }
}
