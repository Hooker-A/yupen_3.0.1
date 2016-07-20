package com.huaop2p.yqs.module.three_mine.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.adapter.CommonAdapter;
import com.huaop2p.yqs.module.base.adapter.ViewHolder;
import com.huaop2p.yqs.module.three_mine.model.entity.BorrowersData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ImageTextAdapter extends CommonAdapter<BorrowersData.MaterialInfo> {
    private DisplayImageOptions options;

    public ImageTextAdapter(List list, Context context, int layoutId) {
        super(list, context, layoutId);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher) // 设置图片加载或解码过程中发生错误显示的图片
                .resetViewBeforeLoading(false)  // default 设置图片在加载前是否重置、复位
                .delayBeforeLoading(1000)  // 下载前的延迟时间
                .cacheInMemory(false) // default  设置下载的图片是否缓存在内存中
                .considerExifParams(false) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default 设置图片的解码类型
                .displayer(new FadeInBitmapDisplayer(100)) // default  还可以设置圆角图片new RoundedBitmapDisplayer(20)
                .build();
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        BorrowersData.MaterialInfo it = list.get(arg0);
        viewHolder = ViewHolder.getViewHolder(context, arg1, arg2, layoutId, arg0);
        ImageView iv = (ImageView) viewHolder.get(R.id.iv);
        TextView tv = (TextView) viewHolder.get(R.id.tv);
        tv.setText(it.MaterialName);
        ImageLoader.getInstance().displayImage(it.MaterialUrl, iv, options);
        return viewHolder.getView();
    }

}
