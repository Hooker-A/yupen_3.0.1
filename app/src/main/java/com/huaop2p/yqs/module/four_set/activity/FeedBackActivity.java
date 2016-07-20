package com.huaop2p.yqs.module.four_set.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.ShowIOSDialog;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.view.ClearEditText;
import com.huaop2p.yqs.module.four_set.model.MyFinancialWeb;
import com.huaop2p.yqs.module.four_set.util.PublicWay;
import com.huaop2p.yqs.utils.BitmapForUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgq on 2016/4/15.
 * 作者:  zhu  guo qing
 * 时间:  2016/4/15 9:58
 * 功能:  意见反馈
 */
public class FeedBackActivity extends BaseActivity implements View.OnClickListener {
    //输入框
    private EditText edit_faceback;
    //发送按钮
    private Button btn_submit;
    //提示
    private ImageView tm_one,gd_photo,im_three,im_foru;
    public static Bitmap bimap;
    private ClearEditText txt_iphone;
    private static final int TAKE_PICTURE = 4;
    private TextView img_Back;
    private static final int MOBILE_GET_PIC = 1; // 本地相册
    private static final int TAKE_PHOTO = 2; // 相机拍照
    private static final int CROP_PHOTO = 3;//图片剪裁
    private static final int MOBILE_GET_PIC2 = 400;//本地相册
    private static final int TAKE_PHOTO2 = 500;//相机拍照
    private static final int CROP_PHOTO2 = 600;//图片剪裁
    private static final int MOBILE_GET_PIC3 = 700;//本地相册
    private static final int TAKE_PHOTO3 = 800;//相机拍照
    private static final int CROP_PHOTO3 = 900;//图片剪裁
    private static final int MOBILE_GET_PIC4 = 1000;//图片剪裁
    private static final int TAKE_PHOTO4 = 1100;//图片剪裁
    private static final int CROP_PHOTO4 = 1200;//图片剪裁
    private Bitmap bitmap;
    private int type;



    String ss;
    Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PublicWay.activityList.add(this);
        setContentView(R.layout.activity_fedd);
        SetActivityTitle(getResources().getString((R.string.str_helper_app)));
        initData();
        linData();
    }

    private void linData() {
        edit_faceback.addTextChangedListener(new MyTextWatcher());
    }
    class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String str1 = edit_faceback.getText().toString();
            if (str1.equals("")) {
                btn_submit.setTextColor(getResources().getColor(R.color.huise));
                btn_submit.setEnabled(false);
            }  else {
                btn_submit.setTextColor(getResources().getColor(R.color.red));
                btn_submit.setEnabled(true);
            }
        }
    }
    /**
     * 初始化控件
     */
    private void initData() {
        txt_iphone= (ClearEditText) findViewById(R.id.txt_iphone);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        edit_faceback = (EditText) findViewById(R.id.edit_faceback);
        edit_faceback.setOnClickListener(this);
        tm_one= (ImageView) findViewById(R.id.tm_one);
        gd_photo= (ImageView) findViewById(R.id.gd_photo);
        im_three= (ImageView) findViewById(R.id.im_three);
        im_foru= (ImageView) findViewById(R.id.im_foru);
        tm_one.setOnClickListener(this);
        gd_photo.setOnClickListener(this);
        im_three.setOnClickListener(this);
        im_foru.setOnClickListener(this);
        img_Back= (TextView) findViewById(R.id.img_Back);
        img_Back.setOnClickListener(this);


    }
    public void HeadSelep2() {
        ShowIOSDialog.showDialog(FeedBackActivity.this, true, new ShowIOSDialog.IntakePic() {
            @Override
            public void onTakePic() {//相机
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intentCamera, TAKE_PHOTO2);
            }

            @Override
            public void onGellayClick() {//相册
                Intent intentGallery = new Intent(Intent.ACTION_PICK, null);
                intentGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intentGallery, MOBILE_GET_PIC2);
            }

            @Override
            public void onFinish() {
                finish();
            }
        });

    }
    public void HeadSelep3() {
        ShowIOSDialog.showDialog(FeedBackActivity.this, true, new ShowIOSDialog.IntakePic() {
            @Override
            public void onTakePic() {//相机
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intentCamera, TAKE_PHOTO3);
            }

            @Override
            public void onGellayClick() {//相册
                Intent intentGallery = new Intent(Intent.ACTION_PICK, null);
                intentGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intentGallery, MOBILE_GET_PIC3);
            }

            @Override
            public void onFinish() {
                finish();
            }
        });

    }
    public void HeadSelep4() {
        ShowIOSDialog.showDialog(FeedBackActivity.this, true, new ShowIOSDialog.IntakePic() {
            @Override
            public void onTakePic() {//相机
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intentCamera, TAKE_PHOTO4);
            }

            @Override
            public void onGellayClick() {//相册
                Intent intentGallery = new Intent(Intent.ACTION_PICK, null);
                intentGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intentGallery, MOBILE_GET_PIC4);
            }

            @Override
            public void onFinish() {
                finish();
            }
        });

    }
    public void HeadSelep() {
        ShowIOSDialog.showDialog(FeedBackActivity.this, true, new ShowIOSDialog.IntakePic() {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_faceback:

                if (edit_faceback.getText().toString().equals("感谢您对余盆的支持，我们期待您的宝贵意见")) {
                    edit_faceback.setText("");
                    edit_faceback.setTextColor(Color.BLACK);
                } else {
                    edit_faceback.setTextColor(Color.BLACK);
                }
                break;
            case R.id.btn_submit:
                if ("感谢您对余盆的支持，我们期待您的宝贵意见".equals(edit_faceback.getText().toString())) {
                    return;
                }

                initHttp();

                break;
            case R.id.img_Back:
                finish();
                break;
            case R.id.tm_one:
                HeadSelep();
                type=1;

                break;
            case R.id.gd_photo:
                HeadSelep2();
                type=2;

                break;
            case R.id.im_three:
                HeadSelep3();
                type=3;

                break;
            case R.id.im_foru:
                HeadSelep4();
                type=4;

                break;
        }
    }
    /**
     * {"AppointEntity":{"CustomerID":"用户ID","Problem":"问题","OpininType":"意见类型（ 0 Apple 1 Android）","MessagerPhone":"手机号"}}
     * 意见反馈接口请求
     * {"KeyId":"意见KeyId","Image":"图片"}
     */
    private void initHttp() {

        String iphone=txt_iphone.getText().toString();
        String Problem = edit_faceback.getText().toString();
        int customerID = ShareLocalUser.getInstance(this).getLoginUser().KeyId;
        if (edit_faceback.getText().toString().length() > 0) {
            Map Dmap = new HashMap();
            Map Smap = new HashMap();
            Smap.put("CustomerID",customerID);
            Smap.put("Problem", Problem);
            Smap.put("OpininType", "1");
            Smap.put("MessagerPhone",iphone);
            Dmap.put("AppointEntity", Smap);
            MyFinancialWeb.getInstance().PostFeed(Dmap, new OnRequestLinstener<BaseResponseEntity>() {
                @Override
                public void success(BaseResponseEntity reqFeedBackBeanBaseResponseEntity) {
                    if (reqFeedBackBeanBaseResponseEntity.ReturnStatus.equals("0")){
                        finish();
                        Toast toast = Toast.makeText(getApplicationContext(), "意见反馈成功!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 10);
                        toast.show();
                        String keyid=reqFeedBackBeanBaseResponseEntity.ReturnMessage.toString();
                        String key=keyid.substring(0,4);

                        for (int e=0;e<bitmaps.size();e++){
                            Map map=new HashMap();
                            map.put("KeyId", key);
                            map.put("Image",bitmaps.get(e));
                            MyFinancialWeb.getInstance().PostFeedbackImg(map, new OnRequestLinstener<BaseResponseEntity>() {
                                @Override
                                public void success(BaseResponseEntity baseResponseEntity) {
                                    if (baseResponseEntity.ReturnStatus.equals("0")) {
                                        String sm = baseResponseEntity.ReturnReason.toString();
                                        Toast toast = Toast.makeText(getApplicationContext(), "意见反馈成功!", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 10);
                                        toast.show();
                                        finish();
                                    } else {
                                        Toast toast = Toast.makeText(getApplicationContext(), baseResponseEntity.ReturnReason, Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 10);
                                        toast.show();
                                    }


                                }

                                @Override
                                public void error(int code, String error) {

                                }
                            }, HttpUrl.PostFeedbackImg, 0, RequestMethod.POST,new TypeToken<BaseResponseEntity>(){});
                        }
                    }else {
                        Toast toast=Toast.makeText(getApplicationContext(), reqFeedBackBeanBaseResponseEntity.ReturnReason, Toast.LENGTH_SHORT);
                        toast .setGravity(Gravity.CENTER, 0, 10);
                        toast.show();
                    }



                }

                @Override
                public void error(int errorCode, String str) {

                }
            }, HttpUrl.Cj_Feedback, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity>() {
            });
        }
    }




    private List<String> bitmaps=new ArrayList<>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

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

            case CROP_PHOTO:   //剪裁
                if (data != null) {
                    Bundle extras = data.getExtras();
                    bitmap = extras.getParcelable("data");
                    if (bitmap != null) {
                        if (type==1){
                            tm_one.setImageBitmap(bitmap);
                        }else if (type==2){
                            gd_photo.setImageBitmap(bitmap);
                        }else if(type==3){
                            im_three.setImageBitmap(bitmap);
                        }else if (type==4){
                            im_foru.setImageBitmap(bitmap);
                        }
                        ss= BitmapForUtils.bitmapToBase64(bitmap);
                        bitmaps.add(ss);
                    }
                }
                break;
            case TAKE_PHOTO2:  //相机
                if (resultCode == Activity.RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    startPhotoZoom(Uri.fromFile(temp));
                }
                break;
            case MOBILE_GET_PIC2:  //图库
                if(resultCode==Activity.RESULT_OK){
                    startPhotoZoom(data.getData());
                }
                break;

            case CROP_PHOTO2:   //剪裁
                if (data != null) {
                    Bundle extras = data.getExtras();
                    bitmap = extras.getParcelable("data");
                    if (bitmap != null) {
                        gd_photo.setImageBitmap(bitmap);
                        ss= BitmapForUtils.bitmapToBase64(bitmap);
                        bitmaps.add(ss);
                    }
                }
                break;
            case TAKE_PHOTO3:  //相机
                if (resultCode == Activity.RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    startPhotoZoom(Uri.fromFile(temp));
                }
                break;
            case MOBILE_GET_PIC3:  //图库
                if(resultCode==Activity.RESULT_OK){
                    startPhotoZoom(data.getData());
                }
                break;

            case CROP_PHOTO3:   //剪裁
                if (data != null) {
                    Bundle extras = data.getExtras();
                    bitmap = extras.getParcelable("data");
                    if (bitmap != null) {
                        im_three.setImageBitmap(bitmap);
                        ss= BitmapForUtils.bitmapToBase64(bitmap);
                        bitmaps.add(ss);
                    }
                }
                break;
            case TAKE_PHOTO4:  //相机
                if (resultCode == Activity.RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    startPhotoZoom(Uri.fromFile(temp));
                }
                break;
            case MOBILE_GET_PIC4:  //图库
                if(resultCode==Activity.RESULT_OK){
                    startPhotoZoom(data.getData());
                }
                break;

            case CROP_PHOTO4:   //剪裁
                if (data != null) {
                    Bundle extras = data.getExtras();
                    bitmap = extras.getParcelable("data");
                    if (bitmap != null) {
                        im_foru.setImageBitmap(bitmap);
                        ss= BitmapForUtils.bitmapToBase64(bitmap);
                        bitmaps.add(ss);
                    }
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
}



