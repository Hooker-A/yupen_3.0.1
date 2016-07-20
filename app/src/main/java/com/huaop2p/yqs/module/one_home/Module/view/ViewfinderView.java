/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huaop2p.yqs.module.one_home.Module.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.one_home.Module.camera.CameraManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * animation and result points.
 */
public final class ViewfinderView extends View {
    private static final int[] SCANNER_ALPHA = { 0, 64, 128, 192, 255, 192,
            128, 64 };
    private static final long ANIMATION_DELAY = 80L;
    private static final int CURRENT_POINT_OPACITY = 0xA0;
    private static final int MAX_RESULT_POINTS = 20;
    private static final int POINT_SIZE = 6;

    private CameraManager cameraManager;
    private final Paint paint;
    private Bitmap resultBitmap;
    private final int maskColor; // 取景框外的背景颜色
    private final int resultColor;// result Bitmap的颜色
    private final int laserColor; // 红色扫描线的颜色
    private final int resultPointColor; // 特征点的颜色
    private final int statusColor; // 提示文字颜色
    private int scannerAlpha;
    private List<ResultPoint> possibleResultPoints;
    private List<ResultPoint> lastPossibleResultPoints;
    // 扫描线移动的y
    private int scanLineTop;
    // 扫描线移动速度
    private final int SCAN_VELOCITY = 5;
    // 扫描线
    Bitmap scanLight;
    public boolean isShowCom = false;//是显示

    public boolean getShowCom() {
        return isShowCom;
    }

    /**
     * 手机的屏幕密度
     */
    public static float density;
    /**
     * 四个绿色边角对应的长度
     */
    public int ScreenRate;

    /**
     * 四个绿色边角对应的宽度
     */
    public static final int CORNER_WIDTH = 6;

    private static final int OPAQUE = 0xFF;
    /**
     * 字体距离扫描框下面的距离
     */
    private static final int TEXT_PADDING_TOP = 30;




    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize these once for performance rather than calling them every
        // time in onDraw().
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Resources resources = getResources();
        maskColor = resources.getColor(R.color.viewfinder_mask);
        resultColor = resources.getColor(R.color.result_view);
        laserColor = resources.getColor(R.color.viewfinder_laser);
        resultPointColor = resources.getColor(R.color.possible_result_points);
        statusColor = resources.getColor(R.color.status_text);
        scannerAlpha = 0;
        possibleResultPoints = new ArrayList<ResultPoint>(5);
        lastPossibleResultPoints = null;
        scanLight = BitmapFactory.decodeResource(resources,
                R.drawable.scan_light);

        density = context.getResources().getDisplayMetrics().density;
        //将像素转换成dp
        ScreenRate = (int) (20 * density);
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        if (cameraManager == null) {
            return; // not ready yet, early draw before done configuring
        }

        // frame为取景框
        Rect frame = cameraManager.getFramingRect();
        Rect previewFrame = cameraManager.getFramingRectInPreview();
        if (frame == null || previewFrame == null) {
            return;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Draw the exterior (i.e. outside the framing rect) darkened
        // 绘制取景框外的暗灰色的表面，分四个矩形绘制
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);// Rect_1
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint); // Rect_2
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1,
                paint); // Rect_3
        canvas.drawRect(0, frame.bottom + 1, width, height, paint); // Rect_4

        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            // 如果有二维码结果的Bitmap，在扫取景框内绘制不透明的result Bitmap
            paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
        } else {
            // Draw a red "laser scanner" line through the middle to show
            // decoding is active
            drawFrameBounds(canvas, frame);
            drawStatusText(canvas, frame, width);

            // 绘制扫描线
            // paint.setColor(laserColor);
            // paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
            // scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
            // int middle = frame.height() / 2 + frame.top;
            // canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1,
            // middle + 2, paint);

            drawScanLight(canvas, frame);

            float scaleX = frame.width() / (float) previewFrame.width();
            float scaleY = frame.height() / (float) previewFrame.height();

            // 绘制扫描线周围的特征点
            List<ResultPoint> currentPossible = possibleResultPoints;
            List<ResultPoint> currentLast = lastPossibleResultPoints;
            int frameLeft = frame.left;
            int frameTop = frame.top;
            if (currentPossible.isEmpty()) {
                lastPossibleResultPoints = null;
            } else {
                possibleResultPoints = new ArrayList<ResultPoint>(5);
                lastPossibleResultPoints = currentPossible;
                paint.setAlpha(CURRENT_POINT_OPACITY);
                paint.setColor(resultPointColor);
                synchronized (currentPossible) {
                    for (ResultPoint point : currentPossible) {
                        canvas.drawCircle(frameLeft
                                        + (int) (point.getX() * scaleX), frameTop
                                        + (int) (point.getY() * scaleY), POINT_SIZE,
                                paint);
                    }
                }
            }
            if (currentLast != null) {
                paint.setAlpha(CURRENT_POINT_OPACITY / 2);
                paint.setColor(resultPointColor);
                synchronized (currentLast) {
                    float radius = POINT_SIZE / 2.0f;
                    for (ResultPoint point : currentLast) {
                        canvas.drawCircle(frameLeft
                                + (int) (point.getX() * scaleX), frameTop
                                + (int) (point.getY() * scaleY), radius, paint);
                    }
                }
            }

            // Request another update at the animation interval, but only
            // repaint the laser line,
            // not the entire viewfinder mask.
            postInvalidateDelayed(ANIMATION_DELAY, frame.left - POINT_SIZE,
                    frame.top - POINT_SIZE, frame.right + POINT_SIZE,
                    frame.bottom + POINT_SIZE);
        }
    }

    /**
     * 字体大小
     */
    private static final int TEXT_SIZE = 16;
    /**
     * 绘制取景框边框
     *
     * @param canvas
     * @param frame
     */
    private void drawFrameBounds(Canvas canvas, Rect frame) {

        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(frame, paint);

        paint.setColor(getResources().getColor(R.color.orange_yp));
        paint.setStyle(Paint.Style.FILL);

        //画扫描框边上的角，总共8个部分

        canvas.drawRect(frame.left, frame.top, frame.left + ScreenRate,
                frame.top + CORNER_WIDTH, paint);
        canvas.drawRect(frame.left, frame.top, frame.left + CORNER_WIDTH, frame.top
                + ScreenRate, paint);
        canvas.drawRect(frame.right - ScreenRate, frame.top, frame.right,
                frame.top + CORNER_WIDTH, paint);
        canvas.drawRect(frame.right - CORNER_WIDTH, frame.top, frame.right, frame.top
                + ScreenRate, paint);
        canvas.drawRect(frame.left, frame.bottom - CORNER_WIDTH, frame.left
                + ScreenRate, frame.bottom, paint);
        canvas.drawRect(frame.left, frame.bottom - ScreenRate,
                frame.left + CORNER_WIDTH, frame.bottom, paint);
        canvas.drawRect(frame.right - ScreenRate, frame.bottom - CORNER_WIDTH,
                frame.right, frame.bottom, paint);
        canvas.drawRect(frame.right - CORNER_WIDTH, frame.bottom - ScreenRate,
                frame.right, frame.bottom, paint);



//        int corWidth = 15;
//        int corLength = 45;
//
//        // 左上角
//        canvas.drawRect(frame.left - corWidth, frame.top, frame.left, frame.top
//                + corLength, paint);
//        canvas.drawRect(frame.left - corWidth, frame.top - corWidth, frame.left
//                + corLength, frame.top, paint);
//        // 右上角
//        canvas.drawRect(frame.right, frame.top, frame.right + corWidth,
//                frame.top + corLength, paint);
//        canvas.drawRect(frame.right - corLength, frame.top - corWidth,
//                frame.right + corWidth, frame.top, paint);
//        // 左下角
//        canvas.drawRect(frame.left - corWidth, frame.bottom - corLength,
//                frame.left, frame.bottom, paint);
//        canvas.drawRect(frame.left - corWidth, frame.bottom, frame.left
//                + corLength, frame.bottom + corWidth, paint);
//        // 右下角
//        canvas.drawRect(frame.right, frame.bottom - corLength, frame.right
//                + corWidth, frame.bottom, paint);
//        canvas.drawRect(frame.right - corLength, frame.bottom, frame.right
//                + corWidth, frame.bottom + corWidth, paint);
    }

    /**
     * 绘制提示文字
     *
     * @param canvas
     * @param frame
     * @param width
     */
    private void drawStatusText(Canvas canvas, Rect frame, int width) {

//        String statusText1 = getResources().getString(
//                R.string.viewfinderview_status_text1);
//        String statusText2 = getResources().getString(
//                R.string.viewfinderview_status_text2);
//        int statusTextSize = 45;
//        int statusPaddingTop = 180;
//
//        paint.setColor(statusColor);
//        paint.setTextSize(statusTextSize);
//
//        int textWidth1 = (int) paint.measureText(statusText1);
//        canvas.drawText(statusText1, (width - textWidth1) / 2, frame.top
//                - statusPaddingTop, paint);
//
//        int textWidth2 = (int) paint.measureText(statusText2);
//        canvas.drawText(statusText2, (width - textWidth2) / 2, frame.top
//                - statusPaddingTop + 60, paint);

        //画扫描框下面的字
        paint.setColor(statusColor);
        paint.setTextSize(TEXT_SIZE * density);
        paint.setAlpha(0x40);
        paint.setTypeface(Typeface.create("System", Typeface.BOLD));
        paint.setTextSize(35);
        paint.setColor(statusColor);

        canvas.drawText(getResources().getString(R.string.viewfinderview_status_text1), frame.left,
                (float) (frame.bottom + (float) TEXT_PADDING_TOP * density), paint);


        Collection<ResultPoint> currentPossible = possibleResultPoints;
        Collection<ResultPoint> currentLast = lastPossibleResultPoints;
        if (currentPossible.isEmpty()) {
            lastPossibleResultPoints = null;
        } else {
            lastPossibleResultPoints = (List<ResultPoint>) currentPossible;
            paint.setAlpha(OPAQUE);
            paint.setColor(resultPointColor);
            for (ResultPoint point : currentPossible) {
                canvas.drawCircle(frame.left + point.getX(), frame.top
                        + point.getY(), 6.0f, paint);
            }
        }
        if (currentLast != null) {
            paint.setAlpha(OPAQUE / 2);
            paint.setColor(resultPointColor);
            for (ResultPoint point : currentLast) {
                canvas.drawCircle(frame.left + point.getX(), frame.top
                        + point.getY(), 3.0f, paint);
            }
        }
    }

    /**
     * 绘制移动扫描线
     *
     * @param canvas
     * @param frame
     */
    private void drawScanLight(Canvas canvas, Rect frame) {

        if (scanLineTop == 0) {
            scanLineTop = frame.top;
        }

        if (scanLineTop >= frame.bottom) {
            scanLineTop = frame.top;
        } else {
            scanLineTop += SCAN_VELOCITY;
        }
        Rect scanRect = new Rect(frame.left, scanLineTop, frame.right,
                scanLineTop + 30);
        canvas.drawBitmap(scanLight, null, scanRect, paint);
    }

    public void drawViewfinder() {
        Bitmap resultBitmap = this.resultBitmap;
        this.resultBitmap = null;
        if (resultBitmap != null) {
            resultBitmap.recycle();
        }
        invalidate();
    }

    /**
     * Draw a bitmap with the result points highlighted instead of the live
     * scanning display.
     *
     * @param barcode
     *            An image of the decoded barcode.
     */
    public void drawResultBitmap(Bitmap barcode) {
        resultBitmap = barcode;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
        List<ResultPoint> points = possibleResultPoints;
        synchronized (points) {
            points.add(point);
            int size = points.size();
            if (size > MAX_RESULT_POINTS) {
                // trim it
                points.subList(0, size - MAX_RESULT_POINTS / 2).clear();
            }
        }
    }
}
