package com.huaop2p.yqs.module.three_mine.activity.dataactivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.ShowIOSDialog;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.base.contant.HttpConnector;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.logon.entity.LoginedBean;
import com.huaop2p.yqs.module.three_mine.model.impl.MyDataHttp;
import com.huaop2p.yqs.module.two_wealth.model.entity.EventBusEntity;
import com.huaop2p.yqs.utils.BitmapForUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yolanda.nohttp.RequestMethod;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by zgq on 2016/5/11.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/11 16:14
 * 功能:
 */
public class HandActivity extends BaseActivity{
    private ImageView img_hand;
    private static final int MOBILE_GET_PIC = 1; // 本地相册
    private static final int TAKE_PHOTO = 2; // 相机拍照
    private static final int CROP_PHOTO = 3;//图片剪裁
    private Bitmap bitmap;
    public static final String LOGIN_PHOTO ="login_photo_url" ;  //登陆界面返回的url request
    public static final int TAKE_IMG_PSE =6;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        img_hand = (ImageView) findViewById(R.id.img_hand);
//        TranslateAnimation tAnim = new TranslateAnimation(400, 0, 0, 0);
        ScaleAnimation scaleAnimation=new ScaleAnimation(0,1,0,1);
        scaleAnimation.setDuration(500);
        img_hand.startAnimation(scaleAnimation);
        /**
         * 头像初始化
         */
        if (ShareLocalUser.getInstance(this).getLoginState()) {
            ImageLoader.getInstance().displayImage(HttpConnector.HTTT_HOST + ShareLocalUser.getInstance(this).getLoginUser().UrlHeadPhoto, img_hand);
        } else {
            img_hand.setBackgroundResource(R.drawable.wodelg);
        }
        img_hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.activity_anim, R.anim.activity_mout);//activity动画

            }
        });
        HeadSelep();

    }

    /**
     * 头像选择
     */
    public void HeadSelep() {
        ShowIOSDialog.showDialog(HandActivity.this, true, new ShowIOSDialog.IntakePic() {
            @Override
            public void onTakePic() {//相机
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intentCamera, TAKE_PHOTO);
            }

            @Override
            public void onGellayClick() {//相册
                Intent intentGallery = new Intent(Intent.ACTION_PICK, null);
                intentGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intentGallery, MOBILE_GET_PIC);
            }

            @Override
            public void onFinish() {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PHOTO:  //相机
                if (resultCode == Activity.RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    startPhotoZoom(Uri.fromFile(temp));
                }
                break;
            case MOBILE_GET_PIC:  //图库
                if(resultCode==Activity.RESULT_OK){
                    startPhotoZoom(data.getData());
                }
                break;
            /**
             * 修改头像：{"HeadPhoto":"新头像"}
             */
            case CROP_PHOTO:   //剪裁
                if (data != null) {
                    Bundle extras = data.getExtras();
                    bitmap = extras.getParcelable("data");
                    if (bitmap != null) {
                        img_hand.setImageBitmap(bitmap);
                        String bitmapStr= BitmapForUtils.bitmapToBase64(bitmap);
                        Map map=new HashMap();
                        map.put("HeadPhoto",bitmapStr);
                        MyDataHttp.getInstance().PostHeadPhoto(map, new OnRequestLinstener<BaseResponseEntity<String>>() {
                            @Override
                            public void success(BaseResponseEntity<String> baseResponseEntity) {
                                LoginedBean bean=ShareLocalUser.getInstance(HandActivity.this).getLoginUser();
                                bean.UrlHeadPhoto=baseResponseEntity.ReturnMessage;
                                ShareLocalUser.getInstance(HandActivity.this).addUserIcon(baseResponseEntity.ReturnMessage);
                                ShareLocalUser.getInstance(HandActivity.this).addLoginUser(bean);
                                img_hand.setImageBitmap(bitmap);
                                String a=baseResponseEntity.ReturnMessage;
                                AppApplication.user.UrlHeadPhoto=baseResponseEntity.ReturnMessage;
                                EventBus.getDefault().postSticky(new EventBusEntity<String>(baseResponseEntity.ReturnMessage,3));
                                finish();
                                LogUtils.e(a+"768120757");
                            }

                            @Override
                            public void error(int code, String error) {

                            }
                        }, HttpUrl.PostHeadPhoto,0, RequestMethod.POST,new TypeToken<BaseResponseEntity>(){});

//
                    }
                }
                break;
            case TAKE_IMG_PSE://头像传递
                if(resultCode==12){
                    String url= data.getStringExtra(LOGIN_PHOTO);
                    ImageLoader.getInstance().displayImage(HttpConnector.HTTT_HOST+url,img_hand);
                }

                break;
        }
    }
    /**
     * 剪裁图片
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
        intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
        intent.putExtra("aspectY", 1);// x:y=1:1
        intent.putExtra("outputX", 200);// 图片输出大小
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_PHOTO);
    }



    @Override
    protected void onResume() {
        super.onResume();


    }

}
