package com.huaop2p.yqs.module.two_wealth.anim;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/30.
 * 作者：任洋
 * 功能：
 */
public class AnimList {
    private static Map<String, Drawable> map = new HashMap<>();

    public static AnimationDrawable addFrame(String name, int count, int time, Context context) {
//        if (map.containsKey(name) == true)
//            return map.get(name);
        AnimationDrawable frameAnim = new AnimationDrawable();
        String packageName = context.getPackageName();
        for (int i = 1; i <= count; i++) {
            int imageResId = context.getResources().getIdentifier(name + i, "drawable", packageName);
            Drawable drawable = null;
            if (map.containsKey(name) == false) {
                drawable = context.getResources().getDrawable(imageResId);
                map.put(name + i, drawable);
            } else
                drawable = map.get(name + i);
            frameAnim.addFrame(drawable, time);
        }
        return frameAnim;
    }
}
