package com.huaop2p.yqs.module.three_mine.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.adapter.CommonAdapter;
import com.huaop2p.yqs.module.base.adapter.ViewHolder;
import com.huaop2p.yqs.module.three_mine.activity.RedGzActivity;
import com.huaop2p.yqs.module.three_mine.model.entity.RedPackage;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by maoxiaofei on 2016/6/28.
 */
public class RecycleViewAdapter2 extends CommonAdapter<RedPackage> {
//    private List<RedPackage> list;//红包和奖券用的同一个实体类
    String type;//这里的type是用来判断红包和奖券的，两个framgent用的同一个适配器
    int flag;//这里的flag是用来从购买里跳进来的红包 ，用于选择是否可用红包

    public static int temp = -1;

    private LayoutInflater inflater = null;//导入布局
    private Activity activity;

    private Map<Integer, Boolean> isSelected;

    private callbacktemp callbacktemp;

    public RecycleViewAdapter2(List list, Context context,int layoutId,String type, int flag,callbacktemp callbacktemp) {
        super(list, context, layoutId);
        this.type = type;
        this.flag = flag;
        this.activity = (Activity) context;
        inflater = LayoutInflater.from(context);
        this.callbacktemp = callbacktemp;
        this.context=context;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (type.equals("1")) {//这里的type是用来判断红包和奖券的，两个framgent用的同一个适配器
            viewHolder = ViewHolder.getViewHolder(context, convertView, parent, R.layout.item_redpackage, position);

        } else {//奖券的布局
            viewHolder = ViewHolder.getViewHolder(context, convertView, parent, R.layout.item_lottetyticket, position);

        }
        TextView tv_jiaxi = (TextView) viewHolder.get(R.id.textview_￥);
        TextView tv_redmoney = (TextView)  viewHolder.get(R.id.tv_redmoney);
        TextView tv_red_lastday = (TextView)  viewHolder.get(R.id.tv_red_lastday);
        TextView tv_red_dixian = (TextView)  viewHolder.get(R.id.tv_red_dixian);
        TextView tv_ben = (TextView)  viewHolder.get(R.id.tv_ben);
        TextView tv_red_manmoney = (TextView) viewHolder.get(R.id.tv_red_manmoney);
        TextView tv_red_date = (TextView) viewHolder.get(R.id.tv_red_date);
        LinearLayout linlayout_redpag = (LinearLayout) viewHolder.get(R.id.linlayout_redpag);
        ImageView image_dixianred_point = (ImageView) viewHolder.get(R.id.image_dixianred_point);
        ImageView image_dixianred_point2 = (ImageView) viewHolder.get(R.id.image_dixianred_point2);
        CheckBox image_red_false = (CheckBox) viewHolder.get(R.id.image_red_false);
        TextView tv_red_gz= (TextView) viewHolder.get(R.id.tv_red_gz);
        TextView tv_red_guiz= (TextView) viewHolder.get(R.id.tv_red_guiz);

        //这里如果添加头部的时候 ，position得变成position-1
        final RedPackage redPackage = list.get(position);
        if (type.equals("1")) {//这里的type是判断红包和奖券两个标题的
            if (flag == 0) {
                image_red_false.setVisibility(View.GONE);
            }
            if (flag == 1) {
                //红包 的布局
                image_red_false.setVisibility(View.VISIBLE);
            }
            if (flag == 2) {
                image_red_false.setVisibility(View.VISIBLE);
            }

            if (redPackage.getTypeId().equals("1")) {//这里的typeid是用来加载不同的背景图片  1和3是一样的图片
               linlayout_redpag.setBackgroundResource(R.drawable.dixian_redred);
//                    viewHolder.tv_jiaxi.setTextColor(android.graphics.Color.parseColor("#FFF0BB"));
                tv_jiaxi.setBackgroundResource(R.drawable.dixian_red);
                //第3种设置颜色方法：tText.setTextColor(android.graphics.Color.parseColor("#87CEFA")) ; //还是利用Color类；
                tv_ben.setTextColor(android.graphics.Color.parseColor("#E05B51"));
                image_dixianred_point.setImageResource(R.drawable.dixian_red_point);
                image_dixianred_point2.setImageResource(R.drawable.dixian_red_point);
                tv_red_dixian.setTextColor(android.graphics.Color.parseColor("#d9372c"));
                tv_redmoney.setTextColor(android.graphics.Color.parseColor("#FFF0BB"));
                tv_red_manmoney.setTextColor(android.graphics.Color.parseColor("#E05B51"));

            } else if (redPackage.getTypeId().equals("2")) {
                linlayout_redpag.setBackgroundResource(R.drawable.fanxian_redred);
                //这是一种设置颜色的方法
//                    viewHolder.tv_jiaxi.setTextColor(viewHolder.tv_jiaxi.getResources().getColor(R.color.redpack));
                tv_jiaxi.setBackgroundResource(R.drawable.fanxian_red);
                //第3种：tText.setTextColor(android.graphics.Color.parseColor("#87CEFA")) ; //还是利用Color类；
                tv_ben.setTextColor(android.graphics.Color.parseColor("#FFF0BB"));
                image_dixianred_point.setImageResource(R.drawable.fanxian_red_point);
                image_dixianred_point2.setImageResource(R.drawable.fanxian_red_point);
                tv_red_dixian.setTextColor(android.graphics.Color.parseColor("#FFF0BB"));
                tv_redmoney.setTextColor(android.graphics.Color.parseColor("#d9372c"));
                tv_red_manmoney.setTextColor(android.graphics.Color.parseColor("#FFF0BB"));

            } else if (redPackage.getTypeId().equals("3")) {
                linlayout_redpag.setBackgroundResource(R.drawable.dixian_redred);
//                    viewHolder.tv_jiaxi.setTextColor(android.graphics.Color.parseColor("#FFF0BB"));
                tv_jiaxi.setBackgroundResource(R.drawable.dixian_red);
                //第3种：tText.setTextColor(android.graphics.Color.parseColor("#87CEFA")) ; //还是利用Color类；
               tv_ben.setTextColor(android.graphics.Color.parseColor("#E05B51"));
               image_dixianred_point.setImageResource(R.drawable.dixian_red_point);
                image_dixianred_point2.setImageResource(R.drawable.dixian_red_point);
               tv_red_dixian.setTextColor(android.graphics.Color.parseColor("#d9372c"));
                tv_redmoney.setTextColor(android.graphics.Color.parseColor("#FFF0BB"));
                tv_red_manmoney.setTextColor(android.graphics.Color.parseColor("#E05B51"));
            }
            tv_redmoney.setText(redPackage.getMoney());
            tv_red_lastday.setText("还有" + getTwoDay(redPackage.getEndTime(), redPackage.getDateTimeNow()) + "天过期");
            if (!redPackage.getDetail().equals("")) {
                tv_ben.setText(redPackage.getDetail());
                image_dixianred_point.setVisibility(View.VISIBLE);
            } else {
                image_dixianred_point.setVisibility(View.GONE);
            }
            if (!redPackage.getUseDescription().equals("") && redPackage.getUseDescription() != null) {
                tv_red_manmoney.setText(redPackage.getUseDescription());
                image_dixianred_point2.setVisibility(View.VISIBLE);
            } else {
                tv_red_manmoney.setText("");
                image_dixianred_point2.setVisibility(View.GONE);
            }
            tv_red_dixian.setText(redPackage.getType());//这里的type字段是红包里的红包类型
            String endtime = redPackage.getEndTime();
            String sbutime=endtime.substring(0,10);
            tv_red_date.setText("有效期至" + sbutime);
            tv_red_gz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, RedGzActivity.class);
                    intent.putExtra(RedGzActivity.GZKID, redPackage.getKeyId());
                    intent.putExtra(RedGzActivity.TITEL1, redPackage.getMoney());
                    intent.putExtra(RedGzActivity.TITEL2, redPackage.getType());
                    context.startActivity(intent);

                }
            });

        } else {

            if (flag == 0) {
                image_red_false.setVisibility(View.GONE);
            }
            if (flag == 1) {
                //红包 的布局
                image_red_false.setVisibility(View.VISIBLE);
            }
            if (flag == 2) {
                image_red_false.setVisibility(View.VISIBLE);
            }

            if (redPackage.getTypeId().equals("4")) {//这里的typeid是用来加载不同的背景图片
                linlayout_redpag.setBackgroundResource(R.drawable.jiaxi_ticket);
                tv_jiaxi.setText(redPackage.getMoney());
                tv_redmoney.setText("%");
            } else if (redPackage.getTypeId().equals("5")) {
                linlayout_redpag.setBackgroundResource(R.drawable.tixian_ticket);
                tv_jiaxi.setText("提现");
                tv_redmoney.setVisibility(View.GONE);
            }

            tv_red_lastday.setText("还有" + getTwoDay(redPackage.getEndTime(), redPackage.getDateTimeNow()) + "天过期");
            if (!redPackage.getDetail().equals("")) {
                tv_ben.setText(redPackage.getDetail());
                image_dixianred_point.setVisibility(View.VISIBLE);
            } else {
                image_dixianred_point.setVisibility(View.GONE);
            }
            if (!redPackage.getUseDescription().equals("")) {
               tv_red_manmoney.setText(redPackage.getUseDescription());
                image_dixianred_point2.setVisibility(View.VISIBLE);
            } else {
                tv_red_manmoney.setText("");
                image_dixianred_point2.setVisibility(View.GONE);
            }
            tv_red_dixian.setText(redPackage.getType());//这里的type字段是红包里的红包类型

            String endtime = redPackage.getEndTime();
            String sbutime=endtime.substring(0,10);
            tv_red_date.setText("有效期至" + sbutime);
            tv_red_guiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, RedGzActivity.class);
                    intent.putExtra(RedGzActivity.GZKID, redPackage.getKeyId());
                    intent.putExtra(RedGzActivity.TITEL1, redPackage.getMoney());
                    intent.putExtra(RedGzActivity.TITEL2, redPackage.getType());
                    context.startActivity(intent);
                }
            });
        }

       image_red_false.setId(position);//对checkbox的id进行重新设置为当前的position
   image_red_false.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//实现checkbox的单选功能,同样适用于radiobutton
                    if (temp != -1) {
                        //找到上次点击的checkbox,并把它设置为false,对重新选择时可以将以前的关掉
                        CheckBox tempCheckBox = (CheckBox) activity.findViewById(temp);
                        if (tempCheckBox != null)
                            tempCheckBox.setChecked(false);
                    }
                    temp = buttonView.getId();//保存当前选中的checkbox的id值
                    callbacktemp.click(position);
                }
            }

        });

        if (position == temp)//比对position和当前的temp是否一致
          image_red_false.setChecked(true);
        else
           image_red_false.setChecked(false);
        return viewHolder.getView();
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

    public  interface callbacktemp{
       public void click(int position );
    }

}
