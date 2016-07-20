package com.huaop2p.yqs.module.three_mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.three_mine.model.entity.ThirtyDayPointBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by maoxiaofei on 2016/6/27.
 */
public class HistoryPointAdater extends BaseAdapter {

    private List<ThirtyDayPointBean> mData;
    public Context context;
    private LayoutInflater layoutInflater;

    private String DiscountSource;

    public HistoryPointAdater(Context context, List mData) {
        this.context = context;
        this.mData = mData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        if (mData != null)
            return mData.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        String s;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.deta_item, null);
            viewHolder = new ViewHolder();
            viewHolder.txt_cong = (TextView) convertView.findViewById(R.id.txt_cong);
            viewHolder.txt_data = (TextView) convertView.findViewById(R.id.txt_data);
            viewHolder.txt_meny = (TextView) convertView.findViewById(R.id.txt_meny);
            viewHolder.txt_nian = (TextView) convertView.findViewById(R.id.txt_nian);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ThirtyDayPointBean detaBean = mData.get(position);
        String type = detaBean.getType();
        String nian = detaBean.title;

        String time = detaBean.getCreateTime();
        String time2 = time.substring(time.indexOf(":") - 2, time.length());
        viewHolder.txt_data.setText(time2);

        if (detaBean.title!= null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            try {
                String nian2 = simpleDateFormat.format(simpleDateFormat.parse(nian));
                viewHolder.txt_nian.setText(nian2);
                viewHolder.txt_nian.setVisibility(View.VISIBLE);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            viewHolder.txt_nian.setText("");
            viewHolder.txt_nian.setVisibility(View.GONE);
        }


        if (type.equals("1")) {
            String meny = detaBean.getAmount();
            viewHolder.txt_meny.setText("+" + meny);
        } else if (type.equals("2")) {
            String meny = detaBean.getAmount();
            viewHolder.txt_meny.setText("-" + meny);
        }


        if (detaBean.getDiscountSource() == null || detaBean.getDiscountSource().equals("")) {
            DiscountSource = "5";
        } else {
            DiscountSource = detaBean.getDiscountSource();
        }

        switch (DiscountSource) {
            case "1":
                viewHolder.txt_cong.setText("购买");
                break;
            case "2":
                viewHolder.txt_cong.setText("活动");
                break;
            case "3":
                viewHolder.txt_cong.setText("签到");
                break;
            case "4":
                viewHolder.txt_cong.setText("积分商城兑换");
                break;
            case "5":
                viewHolder.txt_cong.setText("");
                break;
            default:
                viewHolder.txt_cong.setText("");
                break;
        }


        return convertView;
    }

    static class ViewHolder {

        TextView txt_data, txt_cong, txt_meny, txt_nian;
    }
}
