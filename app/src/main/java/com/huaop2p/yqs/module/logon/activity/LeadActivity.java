package com.huaop2p.yqs.module.logon.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.one_home.activity.MFActivity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgq on 2016/4/6.
 * 作者:  zhu  guo qing.
 * 时间:  2016/4/6 16:03.
 * 功能: 引导页面
 */
public class LeadActivity extends Activity implements ViewPager.OnPageChangeListener{

    private ViewPager viewPager;
    private ViewpagerAdapter adapter;
    private ImageView[] poistion;
    private List<View> views;
    private Button iv_click;
    //图片资源
    private static int[] pics = {R.drawable.y1, R.drawable.y03, R.drawable.y4};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lead_activity);
        /**
         * 判断程序是否第一次登陆
         */
        if (!ShareLocalUser.getInstance(this).getIsFirst()){
            setContentView(R.layout.myviewpager);
            views = new ArrayList<View>();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            iv_click = (Button) findViewById(R.id.iv_click);
            //初始化引导图列表
            for (int i = 0; i < pics.length; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(params);
                imageView.setImageBitmap(readBitMap(this, pics[i]));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                views.add(imageView);
            }

                viewPager = (ViewPager) findViewById(R.id.vp);
                adapter = new ViewpagerAdapter();
                viewPager.setAdapter(adapter);
                viewPager.setOnPageChangeListener(this);
                initDot();
                /**
                 * 点击引导页面的跳转
                 */
                iv_click.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LeadActivity.this, MFActivity.class);
                        startActivity(intent);
                        LeadActivity.this.finish();
                    }
                });
            ShareLocalUser.getInstance(this).addIsFirst(true);
        }else {
            setContentView(R.layout.lead_activity);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LeadActivity.this, MFActivity.class);
                    startActivity(intent);
                    LeadActivity.this.finish();
                }
            }, 2000);
        }

    }

    /**
     * viewPager适配器
     */
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
    /**
     * 初始化点
     */
    private void initDot() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_dot);
        poistion = new ImageView[views.size()];
        for (int i = 0; i < views.size(); i++) {
            poistion[i] = (ImageView) linearLayout.getChildAt(i);
            poistion[i].setEnabled(false);
        }
        // 设置为白色，即选中状态
        poistion[0].setEnabled(true);
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int i) {
        for (int x = 0; x < views.size(); x++) {
            if (x == i) {
                poistion[x].setEnabled(true);
            } else {
                poistion[x].setEnabled(false);
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
}
