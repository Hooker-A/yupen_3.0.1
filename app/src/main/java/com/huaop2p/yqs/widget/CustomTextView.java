package com.huaop2p.yqs.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.huaop2p.yqs.R;


public class CustomTextView extends TextView {
    private float bigTextSize;
    private float smallTextSize;
    private SpannableString msp = null;

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CustomTextView);
        bigTextSize = mTypedArray.getFloat(R.styleable.CustomTextView_bigSize,
                2);
        smallTextSize = mTypedArray.getFloat(R.styleable.CustomTextView_smallSize,
                1);
    }

    public void setText(String text, String indexFlag, int textColor) {
        int index = text.indexOf(indexFlag);
        if (index == -1 || indexFlag.equals("")) {
            index = text.length();
        }
        msp = new SpannableString(text);
        msp.setSpan(new RelativeSizeSpan(bigTextSize), 0, index,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 0.5f表示默认字体大小的两倍
        msp.setSpan(new RelativeSizeSpan(smallTextSize), index, text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 2.0f表示默认字体大小的一半
//
//		// 设置字体前景色
//		msp.setSpan(new ForegroundColorSpan(Color.RED), 0, 1,
//				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为洋红色
        msp.setSpan(new ForegroundColorSpan(textColor), 0, text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为洋红色
        this.setText(msp);
//		this.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
