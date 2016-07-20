package com.huaop2p.yqs.module.four_set.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.entity.BusConstants;
import com.huaop2p.yqs.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zgq on 2016/5/19.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/19 10:16
 * 功能:  转发分享
 */
public class ShareActivity extends BaseActivity {
    protected int activityCloseEnterAnimation;
    protected int activityCloseExitAnimation;
    IWXAPI wxapi;
    public static Tencent mTencent;
    SsoHandler mSsoHandler;
    AuthInfo mAuthInfo;
    Oauth2AccessToken mAccessToken;
    /**
     * 微信分享事件
     *
     * @param view
     */
    @OnClick(R.id.img_weixin_share)
    public void onWxShareClick(View view) {
        wechatShare(0);
    }

    /**
     * 微信朋友圈分享
     *
     * @param view
     */
    @OnClick(R.id.img_wx_pengyouquan)
    public void onWXFrientClick(View view) {
        wechatShare(1);//分享到朋友圈
    }

    /**
     * @param view
     */
    @OnClick(R.id.img_sina_share)
    public void onSinaWeiboShareClick(View view) {
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_share);
        reqMsg(thumb);
    }

    /**
     * qq分享
     *
     * @param view
     */
    @OnClick(R.id.img_qq_share)
    public void onQQShareClick(View view) {
        QQShare2Other();
    }

    /**
     * qq 微博分享
     *
     * @param view
     */
    @OnClick(R.id.img_share_qq_kongjian)
    public void onQQWeiboShareClick(View view) {
        shareToQQzone();
    }

    /**
     * 邮件分享
     *
     * @param view
     */
    @OnClick(R.id.img_email_share)
    public void onEmlShareClick(View view) {
        setEmailShare(getResources().getString(R.string.str_welcome_share_yupen));
    }

    /**
     * 发送短信
     *
     * @param view
     */
    @OnClick(R.id.img_sms_share)
    public void onSendSMSClick(View view) {
        sendSMS(getResources().getString(R.string.str_welcome_share_yupen));
    }

    /**
     * 取消
     *
     * @param view
     */
    @OnClick(R.id.btn_cancel)
    public void onCancelClick(View view) {
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowAnimationStyle});

        int windowAnimatinoStyleResId = activityStyle.getResourceId(0, 0);
        activityStyle.recycle();
        activityStyle = getTheme().obtainStyledAttributes(windowAnimatinoStyleResId, new int[]{android.R.attr.activityCloseEnterAnimation,
                android.R.attr.activityCloseExitAnimation});

        activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
        activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
        activityStyle.recycle();
        mTencent = Tencent.createInstance(BusConstants.QQ_SHARE_APP_ID, ShareActivity.this);

        // 创建微博分享接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, BusConstants.Sina_Share_App_Key);


        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
        mWeiboShareAPI.registerApp();
    }
    public IWeiboShareAPI mWeiboShareAPI = null;
    /**
     * 发送短信
     *
     * @param message
     */
    public void sendSMS(String message) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("sms_body", message);
        sendIntent.setType("vnd.android-dir/mms-sms");
        if (sendIntent.resolveActivity(ShareActivity.this.getPackageManager()) != null)
            startActivity(sendIntent);
        else
            ToastUtils.show(ShareActivity.this, "该手机不支持短信分享");
    }

    /**
     * 邮件分享
     *
     * @param msg
     */
    public void setEmailShare(String msg) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String[] tos = {""};
        String[] ccs = {""};
        String[] bccs = {""};
        intent.putExtra(Intent.EXTRA_EMAIL, tos);
        intent.putExtra(Intent.EXTRA_CC, ccs);
        intent.putExtra(Intent.EXTRA_BCC, bccs);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        intent.putExtra(Intent.EXTRA_SUBJECT, "余盆");

        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///mnt/sdcard/a.jpg"));
        intent.setType("image/*");
        intent.setType("message/rfc882");
        Intent.createChooser(intent, "Choose Email Client");
        startActivity(intent);
    }


    /**
     * 微信分享
     *
     * @param flag 0：分享到微信好友，1：分享到朋友圈
     */
    public void wechatShare(int flag) {
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_share);

        wxapi = WXAPIFactory.createWXAPI(this, BusConstants.WX_APP_ID, true);
        wxapi.registerApp(BusConstants.WX_APP_ID);//将应用的appid注册到微信
        WXImageObject wxImageObject=new WXImageObject(thumb);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = BusConstants.SHARE_DOWNLOAD;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        if (flag == 1) {
            msg.title = getResources().getString(R.string.str_welcome_share_yupen);
        } else {
            msg.title = BusConstants.str_share_title;
        }
        msg.description = getResources().getString(R.string.str_welcome_share_yupen);
        //这里替换一张自己工程里的图片资源
//        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_share);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        wxapi.sendReq(req);
    }

    /**
     * 分享给qq好友
     */
    public void QQShare2Other() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, BusConstants.str_share_title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, getResources().getString(R.string.str_welcome_share_yupen));
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, BusConstants.SHARE_DOWNLOAD);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, BusConstants.AppImageURL);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, BusConstants.SHARE_DOWNLOAD);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "余盆");
//      params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, "其他附加功能");
        mTencent.shareToQQ(ShareActivity.this, params, null);
    }

    /**
     * 分享到qq空间
     */
    public void shareToQQzone() {
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, BusConstants.str_share_title);
        params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_URL, BusConstants.AppImageURL);
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, getResources().getString(R.string.str_welcome_share_yupen))
        ;
        params.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, "余盆");
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, BusConstants.SHARE_DOWNLOAD);
        ArrayList<String> imageUrls = new ArrayList<String>();
        imageUrls.add(BusConstants.AppImageURL);
//        imageUrls.add("http://media-cdn.tripadvisor.com/media/photo-s/01/3e/05/40/the-sandbar-that-links.jpg");
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
        params.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        mTencent.shareToQzone(ShareActivity.this, params, new BaseUiListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
        if (null != mTencent)
            mTencent.onActivityResult(requestCode, resultCode, data);
    }

    public class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            //V2.0版本，参数类型由JSONObject 改成了Object,具体类型参考api文档
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {
            //在这里可以做一些登录成功的处理
            ToastUtils.show(ShareActivity.this, getString(R.string.qq_login_success));
        }

        @Override
        public void onError(UiError e) {
            //在这里可以做登录失败的处理
            ToastUtils.show(ShareActivity.this, getString(R.string.qq_login_fail));
        }

        @Override
        public void onCancel() {
            //在这里可以做登录被取消的处理
            ToastUtils.show(ShareActivity.this, getString(R.string.qq_login_cancel));
        }
    }

    public void reqMsg(Bitmap bitmap) {

        mWeiboShareAPI.registerApp();

    /*图片对象*/
        ImageObject imageobj = new ImageObject();

        if (bitmap != null) {
            imageobj.setImageObject(bitmap);
        }

    /*微博数据的message对象*/
        WeiboMultiMessage multmess = new WeiboMultiMessage();
        TextObject textobj = new TextObject();
        textobj.text = getResources().getString(R.string.str_welcome_share_yupen);
        multmess.textObject = textobj;
        multmess.imageObject = imageobj;
    /*微博发送的Request请求*/
        SendMultiMessageToWeiboRequest multRequest = new SendMultiMessageToWeiboRequest();
        multRequest.multiMessage = multmess;
        //以当前时间戳为唯一识别符
        multRequest.transaction = String.valueOf(System.currentTimeMillis());
        mWeiboShareAPI.sendRequest(this, multRequest);
    }




}
