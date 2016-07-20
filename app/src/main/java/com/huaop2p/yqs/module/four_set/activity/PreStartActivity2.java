package com.huaop2p.yqs.module.four_set.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgq on 2016/5/19.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/19 9:56
 * 功能: 引导页面
 */
public class PreStartActivity2 extends BaseActivity implements ViewPager.OnPageChangeListener{
    private ViewPager viewPager;
    private List<View> views;
    private ImageView[] points;
    private Button iv_click;
    private ViewpagerAdapter adapter;


    //图片资源
    private static int[] pics = {
            R.drawable.y1, R.drawable.y03, R.drawable.y4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perstar);
        views = new ArrayList<View>();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        iv_click = (Button) findViewById(R.id.iv_click);

        //初始化引导图列表
        for (int i = 0; i < pics.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setImageBitmap(readBitMap(this,pics[i]));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            views.add(imageView);
        }
        viewPager = (ViewPager) findViewById(R.id.vp);
        adapter = new ViewpagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        initDot();
        iv_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initDot() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_dot);
        points = new ImageView[views.size()];
        for (int i = 0; i < views.size(); i++) {
            points[i] = (ImageView) linearLayout.getChildAt(i);
            points[i].setEnabled(false);
        }

        // 设置为白色，即选中状态
        points[0].setEnabled(true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    public Bitmap readBitMap(Context context, int resId){



        BitmapFactory.Options opt = new BitmapFactory.Options();



        opt.inPreferredConfig = Bitmap.Config.RGB_565;



        opt.inPurgeable = true;



        opt.inInputShareable = true;



//获取资源图片



        InputStream is = context.getResources().openRawResource(resId);



        return BitmapFactory.decodeStream(is,null,opt);



    }

    @Override
    public void onPageSelected(int i) {
        for (int x = 0; x < views.size(); x++) {
            if (x == i) {
                points[x].setEnabled(true);
            } else {
                points[x].setEnabled(false);
            }
        }
        if (i == 2) {
            iv_click.setVisibility(View.VISIBLE);

        } else {
            iv_click.setVisibility(View.GONE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class ViewpagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return (view == o);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) (container)).addView(views.get(position), 0);
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) (container)).removeView(views.get(position));
        }
    }
}
