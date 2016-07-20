package com.huaop2p.yqs.module.two_wealth.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.two_wealth.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends FragmentActivity
{
	//private ImageView z_iv;
	private ViewPager vp;
	//private WealthCenterAdapter adapter;
	private ViewPagerAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		vp= (ViewPager) findViewById(R.id.vp);
		ArrayList<String> urls=getIntent().getStringArrayListExtra("urls");
		List<View> imageViews=new ArrayList<>();
		for (int i=0;i<urls.size();i++){
			View view=getLayoutInflater().inflate(R.layout.item_image,null);
			view.setTag(urls.get(i));
			imageViews.add(view);
		}
		adapter=new ViewPagerAdapter(imageViews,false);
		vp.setAdapter(adapter);
		vp.setCurrentItem(getIntent().getIntExtra("position",0));
	}
}
