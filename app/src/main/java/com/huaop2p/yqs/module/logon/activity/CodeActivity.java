package com.huaop2p.yqs.module.logon.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.utils.DigestUtils;

import java.util.Hashtable;

/**
 * Created by zgq on 2016/5/4.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/4 17:29
 * 功能:  我的二维码
 */
public class CodeActivity extends BaseActivity {

    private ImageView img_code;
    private int QR_WIDTH = 200, QR_HEIGHT = 200;
    private String code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        SetActivityTitle("我的二维码");
        img_code= (ImageView) findViewById(R.id.img_code);
        String phoneName= ShareLocalUser.getInstance(getApplication()).getLoginUser().LoginName;
        try {
            code= DigestUtils.encrypt(phoneName, "j#&d@v$l", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        createQRImage(code);
    }

    /**
     * 生成二维码
     * @param url
     * String loginName = ShareLocalUser.getInstance(getApplication()).getLoginUser().LoginName;

     */
    private  void createQRImage(String url){

        try {
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++)
            {
                for (int x = 0; x < QR_WIDTH; x++)
                {
                    if (bitMatrix.get(x, y))
                    {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    }
                    else
                    {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            //显示到一个ImageView上面
            img_code.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
