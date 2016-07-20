package com.huaop2p.yqs.module.three_mine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.three_mine.model.entity.DetaBean;
import com.huaop2p.yqs.utils.DecimalFormatUitls;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by zgq on 2016/5/27.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/27 10:12
 * 功能:  收入明细适配器
 */
public class DetaAdapter extends BaseAdapter {
    private List<DetaBean> mData;
    public Context context;
    private LayoutInflater layoutInflater;

    public DetaAdapter(Context context, List mData) {
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
        DetaBean detaBean = mData.get(position);
        String type = detaBean.Type;
        String nian = detaBean.title;
        String time = detaBean.Time;
        String time2 = time.substring(0, time.indexOf(":") - 2);
        viewHolder.txt_data.setText(time2);

        if (detaBean.title != null) {
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
            viewHolder.txt_cong.setText("充值");
            viewHolder.txt_cong.setTextColor(Color.BLACK);
            String meny = DecimalFormatUitls.DecimalFormat_zro(detaBean.Money);
            viewHolder.txt_meny.setText("+" + meny);
        } else {
            viewHolder.txt_cong.setText("提现");
            viewHolder.txt_cong.setTextColor(Color.RED);
            String meny = DecimalFormatUitls.DecimalFormat_zro(detaBean.Money);
            viewHolder.txt_meny.setText("-" + meny);
        }

        return convertView;
    }

    static class ViewHolder {

        TextView txt_data, txt_cong, txt_meny, txt_nian;
    }
}
