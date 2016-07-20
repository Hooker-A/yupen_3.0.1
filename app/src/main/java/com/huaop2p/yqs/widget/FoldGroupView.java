package com.huaop2p.yqs.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.utils.auto.AutoUtils;

@SuppressLint("NewApi")
public class FoldGroupView extends LinearLayout implements OnClickListener {
	public View viewBottom, viewTop;
	private boolean flag;
	private ImageView iv_down;
	public int height;
	private static final int time = 500;
	private boolean isFlag;
	public FoldGroupView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

//	@Override
//	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//		super.onSizeChanged(w, h, oldw, oldh);
//		height=viewBottom.getLayoutParams().height;
//	}



	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		viewBottom = getChildAt(1);
		viewTop = getChildAt(0);
		viewTop.setOnClickListener(this);
		iv_down = (ImageView) findViewById(R.id.iv_down);
		// int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		// int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		height = viewBottom.getLayoutParams().height;
		switch (height) {
			case -1:
			case -2:
				viewBottom.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED); // 計算高度
				height = viewBottom.getMeasuredHeight();
				break;
			default:
				height = viewBottom.getLayoutParams().height;
				break;
		}
		// viewBottom.measure(0,0); //計算高度
		height= AutoUtils.getPercentHeightSize(height);
		viewBottom.getLayoutParams().height = 0;
	}


	@Override
	public void onClick(View arg0) {
		ValueAnimator animation = null;
		if (!flag) {
			animation = ValueAnimator.ofInt(0, height);
			ObjectAnimator.ofFloat(iv_down, "rotation", 0f, 180f)
					.setDuration(time).start();
		} else {
			animation = ValueAnimator.ofInt(height, 0);
			ObjectAnimator.ofFloat(iv_down, "rotation", 180f, 0f)
					.setDuration(time).start();
		}
		animation.setDuration(time);
		animation.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int value = Integer.valueOf(animation.getAnimatedValue()
						.toString());
				viewBottom.getLayoutParams().height = value;
				FoldGroupView.this.requestLayout();

			}
		});
		animation.start();
		flag = !flag;
	}

	// @Override
	// protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	// // 获得它的父容器为它设置的测量模式和大小
	// int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
	// int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
	// int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
	// int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
	//
	// // 如果是warp_content情况下，记录宽和高
	// int width = 0;
	// int height = 0;
	// /**
	// * 记录每一行的宽度，width不断取最大宽度
	// */
	// int lineWidth = 0;
	// /**
	// * 每一行的高度，累加至height
	// */
	// int lineHeight = 0;
	//
	// int cCount = getChildCount();
	//
	// // 遍历每个子元素
	// for (int i = 0; i < cCount; i++) {
	// View child = getChildAt(i);
	// // 测量每一个child的宽和高
	// measureChild(child, widthMeasureSpec, heightMeasureSpec);
	// // 得到child的lp
	// MarginLayoutParams lp = (MarginLayoutParams) child
	// .getLayoutParams();
	// // 当前子空间实际占据的宽度
	// int childWidth = child.getMeasuredWidth() + lp.leftMargin
	// + lp.rightMargin;
	// // 当前子空间实际占据的高度
	// int childHeight = child.getMeasuredHeight() + lp.topMargin
	// + lp.bottomMargin;
	// /**
	// * 如果加入当前child，则超出最大宽度，则的到目前最大宽度给width，类加height 然后开启新行
	// */
	// if (lineWidth + childWidth > sizeWidth) {
	// width = Math.max(lineWidth, childWidth);// 取最大的
	// lineWidth = childWidth; // 重新开启新行，开始记录
	// // 叠加当前高度，
	// height += lineHeight;
	// // 开启记录下一行的高度
	// lineHeight = childHeight;
	// } else
	// // 否则累加值lineWidth,lineHeight取最大高度
	// {
	// lineWidth += childWidth;
	// lineHeight = Math.max(lineHeight, childHeight);
	// }
	// // 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
	// if (i == cCount - 1) {
	// width = Math.max(width, lineWidth);
	// height += lineHeight;
	// }
	//
	// }
	// setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth
	// : width, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight
	// : height);
	// }
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 获得它的父容器为它设置的测量模式和大小
		int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
		int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
		int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
		int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

		// 如果是warp_content情况下，记录宽和高
		int width = 0;
		int height = 0;
		/**
		 * 记录每一行的宽度，width不断取最大宽度
		 */
		int lineWidth = 0;
		/**
		 * 每一行的高度，累加至height
		 */
		int lineHeight = 0;

		int cCount = getChildCount();

		// 遍历每个子元素
		for (int i = 0; i < cCount; i++) {
			View child = getChildAt(i);
			// 测量每一个child的宽和高
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
			// 得到child的lp
			MarginLayoutParams lp = (MarginLayoutParams) child
					.getLayoutParams();
			// 当前子空间实际占据的宽度
			int childWidth = child.getMeasuredWidth() + lp.leftMargin
					+ lp.rightMargin;
			// 当前子空间实际占据的高度
			int childHeight = child.getMeasuredHeight() + lp.topMargin
					+ lp.bottomMargin;
			/**
			 * 如果加入当前child，则超出最大宽度，则的到目前最大宽度给width，类加height 然后开启新行
			 */
			if (lineWidth + childWidth > sizeWidth) {
				width = Math.max(lineWidth, childWidth);// 取最大的
				lineWidth = childWidth; // 重新开启新行，开始记录
				// 叠加当前高度，
				height += lineHeight;
				// 开启记录下一行的高度
				lineHeight = childHeight;
			} else
			// 否则累加值lineWidth,lineHeight取最大高度
			{
				lineWidth += childWidth;
				lineHeight = Math.max(lineHeight, childHeight);
			}
			// 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
			if (i == cCount - 1) {
				width = Math.max(width, lineWidth);
				height += lineHeight;
			}

		}
		setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth
				: width, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight
				: height);

	}
}
