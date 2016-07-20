package com.huaop2p.yqs.module.three_mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.adapter.CommonAdapter;
import com.huaop2p.yqs.module.base.adapter.ViewHolder;
import com.huaop2p.yqs.module.three_mine.model.entity.Borrower;

import java.util.List;

/**
 * Created by Administrator on 2016/5/13.
 * 作者：任洋
 * 功能：
 */
public class BorrowerAdapter extends CommonAdapter<Borrower> {
    private StringBuffer sb;

    public BorrowerAdapter(List<Borrower> list, Context context, int layoutId) {
        super(list, context, layoutId);
        sb = new StringBuffer();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder = ViewHolder.getViewHolder(context, convertView, parent, layoutId, position);
        Borrower borrower = list.get(position);
        TextView tv_name_value = (TextView) viewHolder.get(R.id.tv_name_value);
        TextView tv_loan_value = (TextView) viewHolder.get(R.id.tv_loan_value);
        TextView tv_purpose_value = (TextView) viewHolder.get(R.id.tv_purpose_value);
        TextView tv_ID_card_value = (TextView) viewHolder.get(R.id.tv_ID_card_value);
        String[] LoanPersonNames = borrower.LoanPersonName.split(" ");
        for (int i = 0; i < LoanPersonNames.length; i++) {
            sb.append(LoanPersonNames[i].substring(0, 1));
            for (int j = 0; j < LoanPersonNames[i].length() - 1; j++) {
                sb.append("*");
                sb.append("\t");
            }
        }
        tv_name_value.setText(sb);
        sb.delete(0, sb.length());
        String[] LoanCards = borrower.LoanCard.split(" ");
        for (int i = 0; i < LoanCards.length; i++) {
            sb.append(LoanCards[i].substring(0, 3)).append("***********").append(LoanCards[i].substring(14));
            sb.append("\t");
        }
        tv_ID_card_value.setText(sb);
        sb.delete(0, sb.length());
        if (borrower.LoaningIntMone == null)
            tv_loan_value.setText(String.valueOf(borrower.LoanOverIntMone));
        tv_purpose_value.setText(borrower.LoanApplication);
        return viewHolder.getView();
    }
}
