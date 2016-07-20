package com.huaop2p.yqs.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by zgq on 2016/5/4.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/4 10:58
 * 功能:  图像处理工具类
 */
public class BitmapForUtils {
    /**
     * bitmap 转成base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 给控件设置缩小的bitmap图像
     *
     * @param img          控件
     * @param bitmapName   bitmap的名字
     * @param outputFile   bitmap的路径
     * @param screeenWidth 屏幕的宽  也可以是控件的宽高
     * @param screenHeight 屏幕的高
     */
    public static void setImaBitamap(ImageView img, String bitmapName, Uri outputFile, int screeenWidth, int screenHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(outputFile.getPath(), options);
        int imgH = options.outHeight;
        int imgW = options.outWidth;
        int be = 1;
        if (imgW > imgH && imgW > screeenWidth) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (options.outWidth / screeenWidth);
        } else if (imgW < imgH && imgH > screenHeight) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (options.outHeight / screenHeight);
        }
        if (be <= 0)
            be = 1;

        options.inJustDecodeBounds = false;
        options.inSampleSize = be * 2;
        options.inPurgeable = true;


        Bitmap bitmap = BitmapFactory.decodeFile(outputFile.getPath(), options);
        if (bitmap != null) {
            int d = BitmapForUtils.getBitmapDegree(outputFile.getPath());
            bitmap = BitmapForUtils.rotateBitmapByDegree(bitmap, d);
            img.setImageBitmap(bitmap);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
        }

    }

    /**
     * 处理图片
     *
     * @param bm       所要转换的bitmap
     * @param newWidth 新的宽
     * @param newWidth 新的高
     * @return 指定宽高的bitmap
     */
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    /**
     * 旋转图片
     *
     * @param bm
     * @param degree
     * @return
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    /**
     * 获得图片的旋转度数
     *
     * @param path
     * @return
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

}
