package com.huaop2p.yqs.module.three_mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.three_mine.model.entity.ActiveBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by zgq on 2016/5/31.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/31 11:48
 * 功能: 热门活动适配器
 */
public class ActiveAdapter extends BaseAdapter{
    private List<ActiveBean> mData;
    private Context mContext;
    private LayoutInflater layoutInflater;

    public ActiveAdapter(Context mContext,List<ActiveBean> mList){
        this.mContext=mContext;
        this.mData=mList;
        this.layoutInflater=LayoutInflater.from(mContext);

    }


    @Override
    public int getCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return  mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolerd viewHolerd=null;
        if (convertView==null){
            viewHolerd=new ViewHolerd();
            convertView=layoutInflater.inflate(R.layout.active_item,null);
            viewHolerd.imageView= (ImageView) convertView.findViewById(R.id.img_color);
            convertView.setTag(viewHolerd);
        }else {
            viewHolerd= (ViewHolerd) convertView.getTag();
        }
        ActiveBean activeBean=mData.get(position);
        ImageLoader.getInstance().displayImage(activeBean.BannerUrl, viewHolerd.imageView);
        viewHolerd.imageView.setImageResource(R.drawable.back);
        return convertView;
    }
    class ViewHolerd{
    ImageView imageView;
    }
}
