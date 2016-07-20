package com.huaop2p.yqs.module.two_wealth.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.adapter.CommonAdapter;
import com.huaop2p.yqs.module.base.adapter.ViewHolder;
import com.huaop2p.yqs.module.two_wealth.model.entity.Style;
import com.huaop2p.yqs.module.two_wealth.model.entity.Transfer;
import com.huaop2p.yqs.widget.CustomTextView;
import com.huaop2p.yqs.widget.MyTextView;

import java.util.List;

/**
 * Created by Administrator on 2016/5/12.
 * 作者：任洋
 * 功能：
 */
public class TransferAdapter extends CommonAdapter<Transfer> {
    private  int color;
    public TransferAdapter(List<Transfer> list, Context context, int layoutId) {
        super(list, context, layoutId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder=ViewHolder.getViewHolder(context,convertView,parent,layoutId,position);
        Transfer dataItem=list.get(position);
        if (dataItem.TransferInco.Font.equals("信")){
            color=context.getResources().getColor(R.color.red);
        }else  if (dataItem.TransferInco.Font.equals("房")){
            color=context.getResources().getColor(R.color.yufangbao);
        }else{
            color=context.getResources().getColor(R.color.yuchebao);
        }
        TextView tv_money_type= (TextView) viewHolder.get(R.id.tv_money_type);
        tv_money_type.setText("转让金额");
        TextView tv_LoanName = (TextView) viewHolder.get(R.id.tv_LoanName);
        tv_LoanName.setText(dataItem.Name);
        CustomTextView c_tv1 = (CustomTextView) viewHolder.get(R.id.c_tv1);
        TextView c_tv2 = (TextView) viewHolder.get(R.id.c_tv2);
        TextView c_tv3 = (TextView) viewHolder.get(R.id.c_tv3);
        c_tv1.setText(String.valueOf(dataItem.YearRate)+"+"+dataItem.DropRate+"%", "+",color);
        c_tv2.setTextColor(color);
        c_tv3.setTextColor(color);

        double text = Double.valueOf(dataItem.TransferMoney);
        c_tv3.setText(String.format("%.2f", text) + "元");
        c_tv2.setText(String.valueOf(dataItem.OtherDay) + "天");

        ImageView iv_state = (ImageView) viewHolder.get(R.id.iv_state);
        iv_state.setBackgroundResource(R.drawable.zhuan);
        LinearLayout ll_top= (LinearLayout) viewHolder.get(R.id.ll_top);
        LinearLayout ll_flag= (LinearLayout) viewHolder.get(R.id.ll_flag);
        ll_top.removeAllViews();
        addStyleView(dataItem.TransferInco, ll_top);
        ll_flag.removeAllViews();
        for (int i=0;i<dataItem.YupenProductInco.size();i++){
            addStyleView(dataItem.YupenProductInco.get(i), ll_flag);
        }
        return viewHolder.getView();
    }
    public void addStyleView(Style style,ViewGroup viewGroup){
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5,0,5,0);
        MyTextView textView=new MyTextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setText(style.Font);
        textView.setPadding(5, 5, 5, 5);
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundColor(color);
        viewGroup.addView(textView, 0);
    }
}
