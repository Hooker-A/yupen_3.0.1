package com.huaop2p.yqs.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/** 
 * scrollview 阻尼效果(没有嵌套listview)
 * @author changtengxiao
 */
public class ReboundScrollNormalView extends ScrollView {

    private static final float MOVE_FACTOR = 0.3f;
    private static final int ANIM_TIME = 300;
    private View contentView;
    private float startY;
    private Rect originalRect = new Rect();
    private boolean canPullDown = false;
    private boolean canPullUp = false;
    private boolean isMoved = false;


    public ReboundScrollNormalView(Context context) {
        super(context);
    }

    public ReboundScrollNormalView(Context context, AttributeSet attrs) {
        super(context, attrs);
   }


    public ReboundScrollNormalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            contentView = getChildAt(0);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if(contentView == null) return;
        originalRect.set(contentView.getLeft(), contentView.getTop(), contentView
                .getRight(), contentView.getBottom());
    }

    /**
     * �ڴ����¼���, �����������������߼�
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (contentView == null) {
            return super.dispatchTouchEvent(ev);
        }
        boolean isTouchOutOfScrollView =  ev.getY() >= this.getHeight() || ev.getY() <= 0;

       if(isTouchOutOfScrollView){
            if(isMoved)
                boundBack();
            return true;
        }

        int action = ev.getAction();

        switch (action) {
        case MotionEvent.ACTION_DOWN:

            canPullDown = isCanPullDown();
            canPullUp = isCanPullUp();

            startY = ev.getY();

            break;

        case MotionEvent.ACTION_UP:

            boundBack();

            break;
//        case MotionEvent.ACTION_MOVE:
//
//            if(!canPullDown && !canPullUp) {
//                startY = ev.getY();
//                canPullDown = isCanPullDown();
//                canPullUp = isCanPullUp();
//                break;
//            }
//
//            float nowY = ev.getY();
//            int deltaY = (int) (nowY - startY);
//            boolean shouldMove =
//                    (canPullDown && deltaY > 0)
//                    || (canPullUp && deltaY< 0)
//                    || (canPullUp && canPullDown);
//
//            if(shouldMove){
//                int offset = (int)(deltaY * MOVE_FACTOR);
//                contentView.layout(originalRect.left, originalRect.top+offset,
//                            originalRect.right, originalRect.bottom + offset);
//                isMoved = true;
//             }
//            break;
        default:
            break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * �����ݲ����ƶ���ԭλ��
     */
    private void boundBack(){
        if(!isMoved) return;
        TranslateAnimation anim = new TranslateAnimation(0, 0,contentView.getTop(),originalRect.top);
        anim.setDuration(ANIM_TIME);
        contentView.startAnimation(anim);
        contentView.layout(originalRect.left, originalRect.top, originalRect.right, originalRect.bottom);
        canPullDown = false;
        canPullUp = false;
        isMoved = false;
    }
    /**
     * �ж��Ƿ����������
     */
    private boolean isCanPullDown() {
        return getScrollY() == 0 || contentView.getHeight() < getHeight() + getScrollY();
    }
    /**
     * �ж��Ƿ�������ײ�
     */  
    private boolean isCanPullUp() {  
        return  contentView.getHeight() <= getHeight() + getScrollY();  
    }  
      
}  

