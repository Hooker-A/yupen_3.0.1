package com.huaop2p.yqs.module.three_mine.adapter;

import android.content.Context;
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
 * Created by maoxiaofei on 2016/5/17.
 * <p/>
 * 、、这里所有注释掉的代码全是recycleview添加头部时的代码
 */
public class RecycleViewAdapter extends RecyclerView.Adapter {

    //item类型
//    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;

    //    private int mHeaderCount = 1;//头部View个数
    private int mBottomCount = 1;//底部View个数
    private Context context;
    private LayoutInflater mLayoutInflater;

    private List<RedPackage> list;//红包和奖券用的同一个实体类
    String type;//这里的type是用来判断红包和奖券的，两个framgent用的同一个适配器
    int flag;//这里的flag是用来从购买里跳进来的红包 ，用于选择是否可用红包
    private ViewHolder viewHolder;

    //足部的点击效果
    public interface Onfooterclicklistener {
        void onClickListener(View viewfooter, int position);
    }

    private Onfooterclicklistener onfooterclicklistener;

    public void setOnClickListener(Onfooterclicklistener onfooterclicklistener) {
        this.onfooterclicklistener = onfooterclicklistener;
    }

    //item的点击效果
    public interface Onitemclicklistener{
        void onitemclick(View view,int position);
    }
    private Onitemclicklistener onitemclicklistener;

    public void setOnitemclicklistener(Onitemclicklistener onitemclicklistener){
        this.onitemclicklistener = onitemclicklistener;
    }

    public RecycleViewAdapter(Context context, List<RedPackage> list, String type) {
        this.list = list;
        this.type = type;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    //内容长度
    public int getContentItemCount() {
        return list.size();
    }

    //判断当前item是否是HeadView
//    public boolean isHeaderView(int position) {
//        return mHeaderCount != 0 && position < mHeaderCount;
//    }

    //判断当前item是否是FooterView
    public boolean isBottomView(int position) {
//        return mBottomCount != 0 && position >= (mHeaderCount + getContentItemCount());
        return mBottomCount != 0 && position >= (getContentItemCount());
    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
//        if (mHeaderCount != 0 && position < mHeaderCount) {
//            //头部View
//            return ITEM_TYPE_HEADER;
//        } else if (mBottomCount != 0 && position >= (mHeaderCount + dataItemCount)) {
//            //底部View
//            return ITEM_TYPE_BOTTOM;
//        } else {
//            //内容View
//            return ITEM_TYPE_CONTENT;
//        }
        if (mBottomCount != 0 && position >= (dataItemCount)) {
            //底部View
            return ITEM_TYPE_BOTTOM;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
//          这里是recycleview加头部的时候的代码
//        if (viewType == ITEM_TYPE_HEADER) {
//            return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.recycleview_header, parent, false));
//            //else if(viewType == mHeaderCount)
//        } else if (viewType == mHeaderCount) {
//            if (type.equals("1")) {//这里的type是用来判断红包和奖券的，两个framgent用的同一个适配器
//                //红包 的布局
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_redpackage, null);
//
//            } else {//奖券的布局
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lottetyticket, null);
//            }
//
//            return new ViewHolder(view);
//        } else if (viewType == ITEM_TYPE_BOTTOM) {
//            return new BottomViewHolder(mLayoutInflater.inflate(R.layout.recycleview_footer, parent, false));
//        }

        if (viewType == 1) {
            if (type.equals("1")) {//这里的type是用来判断红包和奖券的，两个framgent用的同一个适配器
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_redpackage, null);

            } else {//奖券的布局
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lottetyticket, null);

            }

            return new ViewHolder(view);
        } else if (viewType == ITEM_TYPE_BOTTOM) {//底部布局
            if (type.equals("1")) {
                return new BottomViewHolder(mLayoutInflater.inflate(R.layout.recycleview_footer, parent, false));
            } else {
                return new BottomViewHolder(mLayoutInflater.inflate(R.layout.recycleview_footerticket, parent, false));
            }

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

//        if (holder instanceof HeaderViewHolder) {
//        } else
        if (holder instanceof ViewHolder) {

            viewHolder = (ViewHolder) holder;

            viewHolder.position = position;
            RedPackage redPackage = list.get(position);//这里如果添加头部的时候 ，position得变成position-1

            if (type.equals("1")) {//这里的type是判断红包和奖券两个标题的
                if (flag == 1) {
                    //红包 的布局
                    viewHolder.image_red_false.setVisibility(View.VISIBLE);
                    viewHolder.image_red_false.setImageResource(R.drawable.red_false);
                } else {
                    viewHolder.image_red_false.setVisibility(View.GONE);
                }

                if (redPackage.getTypeId().equals("1")) {//这里的typeid是用来加载不同的背景图片  1和3是一样的图片
                    viewHolder.linlayout_redpag.setBackgroundResource(R.drawable.dixian_redred);
//                    viewHolder.tv_jiaxi.setTextColor(android.graphics.Color.parseColor("#FFF0BB"));
                    viewHolder.tv_jiaxi.setBackgroundResource(R.drawable.dixian_red);
                    //第3种设置颜色方法：tText.setTextColor(android.graphics.Color.parseColor("#87CEFA")) ; //还是利用Color类；
                    viewHolder.tv_ben.setTextColor(android.graphics.Color.parseColor("#E05B51"));
                    viewHolder.image_dixianred_point.setImageResource(R.drawable.dixian_red_point);
                    viewHolder.image_dixianred_point2.setImageResource(R.drawable.dixian_red_point);
                    viewHolder.tv_red_dixian.setTextColor(android.graphics.Color.parseColor("#d9372c"));
                    viewHolder.tv_redmoney.setTextColor(android.graphics.Color.parseColor("#FFF0BB"));
                    viewHolder.tv_red_manmoney.setTextColor(android.graphics.Color.parseColor("#E05B51"));

                } else if (redPackage.getTypeId().equals("2")) {
                    viewHolder.linlayout_redpag.setBackgroundResource(R.drawable.fanxian_redred);
                    //这是一种设置颜色的方法
//                    viewHolder.tv_jiaxi.setTextColor(viewHolder.tv_jiaxi.getResources().getColor(R.color.redpack));
                    viewHolder.tv_jiaxi.setBackgroundResource(R.drawable.fanxian_red);
                    //第3种：tText.setTextColor(android.graphics.Color.parseColor("#87CEFA")) ; //还是利用Color类；
                    viewHolder.tv_ben.setTextColor(android.graphics.Color.parseColor("#FFF0BB"));
                    viewHolder.image_dixianred_point.setImageResource(R.drawable.fanxian_red_point);
                    viewHolder.image_dixianred_point2.setImageResource(R.drawable.fanxian_red_point);
                    viewHolder.tv_red_dixian.setTextColor(android.graphics.Color.parseColor("#FFF0BB"));
                    viewHolder.tv_redmoney.setTextColor(android.graphics.Color.parseColor("#d9372c"));
                    viewHolder.tv_red_manmoney.setTextColor(android.graphics.Color.parseColor("#FFF0BB"));

                } else if (redPackage.getTypeId().equals("3")) {
                    viewHolder.linlayout_redpag.setBackgroundResource(R.drawable.dixian_redred);
//                    viewHolder.tv_jiaxi.setTextColor(android.graphics.Color.parseColor("#FFF0BB"));
                    viewHolder.tv_jiaxi.setBackgroundResource(R.drawable.dixian_red);
                    //第3种：tText.setTextColor(android.graphics.Color.parseColor("#87CEFA")) ; //还是利用Color类；
                    viewHolder.tv_ben.setTextColor(android.graphics.Color.parseColor("#E05B51"));
                    viewHolder.image_dixianred_point.setImageResource(R.drawable.dixian_red_point);
                    viewHolder.image_dixianred_point2.setImageResource(R.drawable.dixian_red_point);
                    viewHolder.tv_red_dixian.setTextColor(android.graphics.Color.parseColor("#d9372c"));
                    viewHolder.tv_redmoney.setTextColor(android.graphics.Color.parseColor("#FFF0BB"));
                    viewHolder.tv_red_manmoney.setTextColor(android.graphics.Color.parseColor("#E05B51"));
                }
                viewHolder.tv_redmoney.setText(redPackage.getMoney());
                viewHolder.tv_red_lastday.setText("还有" + getTwoDay(redPackage.getEndTime(), redPackage.getDateTimeNow()) + "天过期");
                if (!redPackage.getDetail().equals("")) {
                    viewHolder.tv_ben.setText(redPackage.getDetail());
                    viewHolder.image_dixianred_point.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.image_dixianred_point.setVisibility(View.GONE);
                }
                if (!redPackage.getUseDescription().equals("") && redPackage.getUseDescription() != null) {
                    viewHolder.tv_red_manmoney.setText(redPackage.getUseDescription());
                    viewHolder.image_dixianred_point2.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.tv_red_manmoney.setText("");
                    viewHolder.image_dixianred_point2.setVisibility(View.GONE);
                }
                viewHolder.tv_red_dixian.setText(redPackage.getType());//这里的type字段是红包里的红包类型
                viewHolder.tv_red_date.setText("有效期至" + redPackage.getEndTime());
            } else {
                if (flag == 2) {
                    viewHolder.image_red_false.setVisibility(View.VISIBLE);
                    viewHolder.image_red_false.setImageResource(R.drawable.red_false);
                }
                viewHolder.image_red_false.setVisibility(View.GONE);
                if (redPackage.getTypeId().equals("4")) {//这里的typeid是用来加载不同的背景图片
                    viewHolder.linlayout_redpag.setBackgroundResource(R.drawable.jiaxi_ticket);
                } else if (redPackage.getTypeId().equals("5")) {
                    viewHolder.linlayout_redpag.setBackgroundResource(R.drawable.tixian_ticket);
                }
                viewHolder.tv_jiaxi.setText(redPackage.getMoney());
                viewHolder.tv_redmoney.setText("%");
                viewHolder.tv_red_lastday.setText("还有" + getTwoDay(redPackage.getEndTime(), redPackage.getDateTimeNow()) + "天过期");
                if (!redPackage.getDetail().equals("")) {
                    viewHolder.tv_ben.setText(redPackage.getDetail());
                    viewHolder.image_dixianred_point.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.image_dixianred_point.setVisibility(View.GONE);
                }
                if (!redPackage.getUseDescription().equals("")) {
                    viewHolder.tv_red_manmoney.setText(redPackage.getUseDescription());
                    viewHolder.image_dixianred_point2.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.tv_red_manmoney.setText("");
                    viewHolder.image_dixianred_point2.setVisibility(View.GONE);
                }
                viewHolder.tv_red_dixian.setText(redPackage.getType());//这里的type字段是红包里的红包类型
                viewHolder.tv_red_date.setText("有效期至" + redPackage.getEndTime());
            }
        } else if (holder instanceof BottomViewHolder) {

            //这里是底部里tetview的监听   接口回调
            final BottomViewHolder holder1 = (BottomViewHolder) holder;
            if (onfooterclicklistener != null) {
                holder1.textview_overRedb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder1.getLayoutPosition();
                        onfooterclicklistener.onClickListener(holder1.textview_overRedb, pos);
                    }
                });
            }
        }


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

    @Override
    public int getItemCount() {
//        return list.size();
//        return mHeaderCount + getContentItemCount() + mBottomCount;
        return getContentItemCount() + mBottomCount;
    }

    public interface MyItemClickListener {
        public void onItemClick(View view,int postion);
    }

    //内容的viewholder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

        private MyItemClickListener mListener;

        public ImageView image_red_false;

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
         //   image_red_false = (ImageView) itemView.findViewById(R.id.image_red_false);

            this.mListener = mListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,getPosition());
            }
        }

    }

    //头部 ViewHolder
//    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
//        public HeaderViewHolder(View itemView) {
//            super(itemView);
//        }
//    }

    //底部 ViewHolder
    public static class BottomViewHolder extends RecyclerView.ViewHolder {

        public TextView textview_overRedb;

        public BottomViewHolder(View itemView) {
            super(itemView);
            textview_overRedb = (TextView) itemView.findViewById(R.id.textview_overRed);
        }


    }

}
