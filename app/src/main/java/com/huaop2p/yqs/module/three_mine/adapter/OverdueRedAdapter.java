package com.huaop2p.yqs.module.three_mine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.three_mine.model.entity.RedPackage;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by maoxiaofei on 2016/6/14.
 */
public class OverdueRedAdapter extends RecyclerView.Adapter {

    private List<RedPackage> list;//红包和奖券用的同一个实体类
    String tag;//这里的type是用来判断红包和奖券的，两个activity用的同一个适配器 1是红包  2是奖券

    public OverdueRedAdapter(List<RedPackage> list, String tag) {
        this.list = list;
        this.tag = tag;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if (tag.equals("2")) {//奖券的布局
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_ovdelotterytivket, null);
        } else {//红包的布局
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_overdueredprac, null);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.position = position;
        RedPackage redPackage = list.get(position);//这里如果添加头部的时候 ，position得变成position-1


        if (tag.equals("1")) {
            if (redPackage.getTypeId().equals("1")) {//这里的typeid是用来加载不同的背景图片  1和3是一样的图片
                viewHolder.linlayout_redpag.setBackgroundResource(R.drawable.dixian_overredred);
//            viewHolder.tv_jiaxi.setTextColor(android.graphics.Color.parseColor("#EFEFEF"));
                viewHolder.tv_jiaxi.setBackgroundResource(R.drawable.fanxian_redover);
                //第3种设置颜色方法：tText.setTextColor(android.graphics.Color.parseColor("#87CEFA")) ; //还是利用Color类；
                viewHolder.tv_ben.setTextColor(android.graphics.Color.parseColor("#C9C9C9"));
                viewHolder.image_dixianred_point.setImageResource(R.drawable.dixian_overred_point);
                viewHolder.image_dixianred_point2.setImageResource(R.drawable.dixian_overred_point);
                viewHolder.tv_red_dixian.setTextColor(android.graphics.Color.parseColor("#646464"));
                viewHolder.tv_redmoney.setTextColor(android.graphics.Color.parseColor("#EFEFEF"));
                viewHolder.tv_red_manmoney.setTextColor(android.graphics.Color.parseColor("#818181"));

            } else if (redPackage.getTypeId().equals("2")) {
                viewHolder.linlayout_redpag.setBackgroundResource(R.drawable.fanxian_overredred);
                //这是一种设置颜色的方法
//            viewHolder.tv_jiaxi.setTextColor(android.graphics.Color.parseColor("#646464"));
                viewHolder.tv_jiaxi.setBackgroundResource(R.drawable.fanxian_redover);
                //第3种：tText.setTextColor(android.graphics.Color.parseColor("#87CEFA")) ; //还是利用Color类；
                viewHolder.tv_ben.setTextColor(android.graphics.Color.parseColor("#EFEFEF"));
                viewHolder.image_dixianred_point.setImageResource(R.drawable.fanxian_overred_point);
                viewHolder.image_dixianred_point2.setImageResource(R.drawable.fanxian_overred_point);
                viewHolder.tv_red_dixian.setTextColor(android.graphics.Color.parseColor("#EFEFEF"));
                viewHolder.tv_redmoney.setTextColor(android.graphics.Color.parseColor("#646464"));
                viewHolder.tv_red_manmoney.setTextColor(android.graphics.Color.parseColor("#EFEFEF"));

            } else if (redPackage.getTypeId().equals("3")) {
                viewHolder.linlayout_redpag.setBackgroundResource(R.drawable.dixian_overredred);
//            viewHolder.tv_jiaxi.setTextColor(android.graphics.Color.parseColor("#EFEFEF"));
                viewHolder.tv_jiaxi.setBackgroundResource(R.drawable.fanxian_redover);
                //第3种设置颜色方法：tText.setTextColor(android.graphics.Color.parseColor("#87CEFA")) ; //还是利用Color类；
                viewHolder.tv_ben.setTextColor(android.graphics.Color.parseColor("#818181"));
                viewHolder.image_dixianred_point.setImageResource(R.drawable.dixian_overred_point);
                viewHolder.image_dixianred_point2.setImageResource(R.drawable.dixian_overred_point);
                viewHolder.tv_red_dixian.setTextColor(android.graphics.Color.parseColor("#646464"));
                viewHolder.tv_redmoney.setTextColor(android.graphics.Color.parseColor("#EFEFEF"));
                viewHolder.tv_red_manmoney.setTextColor(android.graphics.Color.parseColor("#818181"));
            }
            viewHolder.tv_redmoney.setText(redPackage.getMoney());
            viewHolder.tv_red_lastday.setText("已过期");
            viewHolder.tv_red_dixian.setText(redPackage.getType());//这里的type字段是红包里的红包类型
//            viewHolder.tv_ben.setText(redPackage.getDetail());
//            viewHolder.tv_red_manmoney.setText(redPackage.getUseDescription());
            viewHolder.tv_red_date.setText("有效期至" + redPackage.getEndTime());
            if (!redPackage.getDetail().equals("")){
                viewHolder.tv_ben.setText(redPackage.getDetail());
                viewHolder.image_dixianred_point.setVisibility(View.VISIBLE);
            }else {
                viewHolder.image_dixianred_point.setVisibility(View.GONE);
            }
            if (!redPackage.getUseDescription().equals("")){
                viewHolder.tv_red_manmoney.setText(redPackage.getUseDescription());
                viewHolder.image_dixianred_point2.setVisibility(View.VISIBLE);
            }else {
                viewHolder.tv_red_manmoney.setText("");
                viewHolder.image_dixianred_point2.setVisibility(View.GONE);
            }
        } else {
            if (redPackage.getTypeId().equals("4")) {//这里的typeid是用来加载不同的背景图片
                viewHolder.linlayout_redpag.setBackgroundResource(R.drawable.overticket);
                viewHolder.tv_jiaxi.setText(redPackage.getMoney());
                viewHolder.tv_redmoney.setText("%");
            } else if (redPackage.getTypeId().equals("5")) {
                viewHolder.linlayout_redpag.setBackgroundResource(R.drawable.overticket);
                viewHolder.tv_jiaxi.setText("提现");
                viewHolder.tv_redmoney.setVisibility(View.GONE);
            }

            viewHolder.tv_red_lastday.setText("已过期");
            viewHolder.tv_red_dixian.setText(redPackage.getType());//这里的type字段是红包里的红包类型
//            viewHolder.tv_ben.setText(redPackage.getDetail());
//            viewHolder.tv_red_manmoney.setText(redPackage.getUseDescription());
            viewHolder.tv_red_date.setText("有效期至" + redPackage.getEndTime());
            if (!redPackage.getDetail().equals("")){
                viewHolder.tv_ben.setText(redPackage.getDetail());
                viewHolder.image_dixianred_point.setVisibility(View.VISIBLE);
            }else {
                viewHolder.image_dixianred_point.setVisibility(View.GONE);
            }
            if (!redPackage.getUseDescription().equals("")){
                viewHolder.tv_red_manmoney.setText(redPackage.getUseDescription());
                viewHolder.image_dixianred_point2.setVisibility(View.VISIBLE);
            }else {
                viewHolder.tv_red_manmoney.setText("");
                viewHolder.image_dixianred_point2.setVisibility(View.GONE);
            }
        }
    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            java.util.Date date = myFormatter.parse(sj1);
            java.util.Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

//内容的viewholder
class ViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout linlayout_redpag;

    public TextView tv_redmoney;
    public TextView tv_red_lastday;
    public TextView tv_red_dixian;
    public TextView tv_ben;
    public TextView tv_red_manmoney;
    public TextView tv_red_date;
    public TextView tv_jiaxi;
    public ImageView image_dixianred_point;
    public ImageView image_dixianred_point2;
    public int position;

    public ViewHolder(View itemView) {
        super(itemView);
        tv_jiaxi = (TextView) itemView.findViewById(R.id.textview_￥);
        tv_redmoney = (TextView) itemView.findViewById(R.id.tv_redmoney);
        tv_red_lastday = (TextView) itemView.findViewById(R.id.tv_red_lastday);
        tv_red_dixian = (TextView) itemView.findViewById(R.id.tv_red_dixian);
        tv_ben = (TextView) itemView.findViewById(R.id.tv_ben);
        tv_red_manmoney = (TextView) itemView.findViewById(R.id.tv_red_manmoney);
        tv_red_date = (TextView) itemView.findViewById(R.id.tv_red_date);
        linlayout_redpag = (LinearLayout) itemView.findViewById(R.id.linlayout_redpag);
        image_dixianred_point = (ImageView) itemView.findViewById(R.id.image_dixianred_point);
        image_dixianred_point2 = (ImageView) itemView.findViewById(R.id.image_dixianred_point2);

    }
}
}
