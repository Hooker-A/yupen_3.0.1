package com.huaop2p.yqs.module.base.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


public abstract class CommonAdapter<T> extends BaseAdapter {
	protected List<T> list=null;
	protected Context context;
	protected int layoutId;
	protected ViewHolder viewHolder;
	public CommonAdapter(List<T> list, Context context, int layoutId) {
		super();
		this.list = list;
		this.context = context;
		this.layoutId = layoutId;
	}

	@Override
	public int getCount() {
		if (list==null) 
			return 0;
		return list.size();
	}

	@Override
	public T getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
	public void  refresh(List list) {
		if(list==null){
			list=new ArrayList();
		}
		this.list=list;
		this.notifyDataSetChanged();
	}
	public void  removeItem(int position) {
		this.list.remove(position);
		this.notifyDataSetChanged();
	}
}
