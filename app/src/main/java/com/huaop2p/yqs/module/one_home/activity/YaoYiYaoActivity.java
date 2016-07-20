package com.huaop2p.yqs.module.one_home.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.entity.BusConstants;
import com.huaop2p.yqs.module.base.view.ClearEditText;
import com.huaop2p.yqs.module.logon.model.MyFinancialWeb;
import com.huaop2p.yqs.module.one_home.bean.YaoNumBean;
import com.huaop2p.yqs.module.one_home.bean.YaoYiYaoBean;
import com.huaop2p.yqs.module.one_home.entity.CustomToastDialog;
import com.huaop2p.yqs.module.one_home.entity.DesEncToDes;
import com.huaop2p.yqs.module.one_home.entity.ShakeListener;
import com.huaop2p.yqs.module.one_home.entity.Web;
import com.huaop2p.yqs.module.one_home.view.FlakeView;
import com.huaop2p.yqs.module.three_mine.activity.LotteryTicketActivity;
import com.huaop2p.yqs.utils.DateUtils;
import com.huaop2p.yqs.utils.DigestUtils;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.utils.auto.SoapUtils;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.huaop2p.yqs.utils.net.NetUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.RequestMethod;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by maoxiaofei on 2016/5/30.
 */
public class YaoYiYaoActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {
    ShakeListener mShakeListener = null;
    Vibrator mVibrator;
    private RelativeLayout mImgUp;
    private ImageView shakeBg;
    private Button btn_guize, imgBack_space;
    private RelativeLayout red_pop;
    private TextView np__number, txt_cisuo, text_count;

    private FlakeView flakeView;
    private LinearLayout container;
    private LinearLayout lin_pop;
    private String uil;
    private WebView webView;


    private TextView text_money;

    private String keyid;


    private YaoYiYaoBean beannn = new YaoYiYaoBean();

    private AsyncYaoNum num;

    GetUrLHanlder hanlder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red);
//        SetActivityTitle("摇一摇");

        flakeView = new FlakeView(this);
//        hanlder = new GetUrLHanlder();

        //drawerSet ();//设置  drawer监听    切换 按钮的方向

        mVibrator = (Vibrator) getApplication().getSystemService(VIBRATOR_SERVICE);

        mImgUp = (RelativeLayout) findViewById(R.id.shakeImgUp);
        btn_guize = (Button) findViewById(R.id.btn_guize);
        btn_guize.setOnClickListener(this);
        imgBack_space = (Button) findViewById(R.id.imgBack_space);
        imgBack_space.setOnClickListener(this);
        container = (LinearLayout) findViewById(R.id.container);


        shakeBg = (ImageView) findViewById(R.id.shakeBg);
        red_pop = (RelativeLayout) findViewById(R.id.red_pop);


        np__number = (TextView) findViewById(R.id.np__number);
        txt_cisuo = (TextView) findViewById(R.id.txt_cisuo);
        text_count = (TextView) findViewById(R.id.text_count);

//        num = new AsyncYaoNum();
//        num.execute();

        getYaoNum();


    }

    @Override
    protected void onResume() {
        super.onResume();
        flakeView.resume();
        if (mShakeListener != null)
            mShakeListener.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        flakeView.pause();
        if (mShakeListener != null)
            mShakeListener.stop();
    }

    //手机摇一摇的动画效果

    public void startAnim() {
        AnimationSet animup = new AnimationSet(true);
        TranslateAnimation mYstar = new TranslateAnimation(0, 250, 0, 0);
        mYstar.setDuration(100);//持续时间
        mYstar.setRepeatCount(4);//重复次数
        mYstar.setRepeatMode(Animation.REVERSE);
//        mYstar.setStartOffset(1000);
        animup.addAnimation(mYstar);
        shakeBg.startAnimation(animup);
    }


    //振动效果


    //振动效果
    public void startVibrato() {


        MediaPlayer player;
        player = MediaPlayer.create(this, R.raw.sample);
        player.setLooping(false);
        player.start();
        //定义震动
//        mVibrator.vibrate( new long[]{500,200,500,200}, -1); //第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        }
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_guize:
//                AsyncGuize asyncGuize = new AsyncGuize();
//                asyncGuize.execute();

                getYaoYiYaoAction();
                if (mShakeListener != null)
                    mShakeListener.stop();
                break;
            case R.id.imgBack_space:
                Intent intensp = new Intent(getApplicationContext(), LotteryTicketActivity.class);
                startActivity(intensp);
                if (mShakeListener != null)
                    mShakeListener.stop();
                break;
        }
    }

    PopupWindow pop;

    /**
     * 弹出红包
     *
     * @param v
     * @param moneyStr
     * @param show
     * @return
     */
    private PopupWindow showPopWindows(Vibrator v, String moneyStr, boolean show) {
        View view = this.getLayoutInflater().inflate(R.layout.activity_rad_pop, null);
        //将flakeView 添加到布局中   掉金币的效果
        container.addView(flakeView);
        //设置背景
        this.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.huise)));
        //设置同时出现在屏幕上的金币数量  建议64以内 过多会引起卡顿
        flakeView.addFlakes(15);
        /**
         * 绘制的类型
         * @see View.LAYER_TYPE_HARDWARE
         * @see View.LAYER_TYPE_SOFTWARE
         * @see View.LAYER_TYPE_NONE
         */
        flakeView.setLayerType(View.LAYER_TYPE_NONE, null);

        text_money = (TextView) view.findViewById(R.id.text_money);
        /**
         * 查看红包记录
         */
        view.findViewById(R.id.btn_see).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), HongBaoActivity.class);
                startActivity(intent1);
                if (mShakeListener != null)
                    mShakeListener.stop();
                pop.dismiss();
            }
        });
        /**
         *关闭pop按钮
         */
        view.findViewById(R.id.btn_guan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                if (mShakeListener != null)
                    mShakeListener.start();

            }
        });
        /**
         * 接着摇
         */
        view.findViewById(R.id.btn_yaoy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (container != null) {
                    container.removeAllViews();
                }
                pop.dismiss();
                if (mShakeListener != null)
                    mShakeListener.start();
            }
        });
        pop = new PopupWindow(view, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(getResources().getColor(R.color.transparent));
        pop.setBackgroundDrawable(dw);
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        pop.showAtLocation(container, Gravity.CENTER, 0, 0);

        /**
         * 移除动画
         */
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //设置2秒后
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            container.removeAllViews();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        if (!show)
            thread.start();
        MediaPlayer player = MediaPlayer.create(this, R.raw.sample2);
        player.start();
        return pop;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            pop.dismiss();
            return true;
        }
        return false;
    }

    int main, andd, othercount;


    //摇到的红包接口
    class AsyncYaoYiYao extends AsyncTask<String, Integer, BaseResponseEntity<YaoNumBean>> {
        @Override
        protected BaseResponseEntity<YaoNumBean> doInBackground(String... params) {

            BaseResponseEntity bre = MyFinancialWeb.getInstance().getYaoYiYaoProduct(" ");
            return bre;
        }

        @Override
        protected void onPostExecute(BaseResponseEntity<YaoNumBean> listBaseResponseEntity) {
            super.onPostExecute(listBaseResponseEntity);
            if (SoapUtils.isResponseOk(YaoYiYaoActivity.this, listBaseResponseEntity)) {
                LogUtils.e(GsonUtils.getDateGson(null).toJson(listBaseResponseEntity));
                keyid = listBaseResponseEntity.ReturnMessage.KeyID + "";
                if (main >= 0) {
                    showPopWindows(mVibrator, "20", false);
                    text_money.setText(listBaseResponseEntity.ReturnMessage.RedMoney);
                } else {
                    showZengPop(listBaseResponseEntity.ReturnMessage.RedMoney, false);
                }
                if (andd <= 0) {
                    ToastUtils.show(getApplication(), "您己没有次数", 0);
                }


            }
        }
    }

    ProgressDialog progressDialog;

    /**
     * 摇一摇次数数据接口  已经放弃使用该方法，用下边的getYaoNum()方法来加载
     */
    class AsyncYaoNum extends AsyncTask<String, Integer, BaseResponseEntity<YaoYiYaoBean>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(YaoYiYaoActivity.this, AlertDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("正在加载请稍后......");
            progressDialog.show();
        }

        @Override
        protected BaseResponseEntity<YaoYiYaoBean> doInBackground(String... params) {
            BaseResponseEntity bre = MyFinancialWeb.getInstance().getYaoNum("");
            return bre;
        }

        @Override
        protected void onPostExecute(BaseResponseEntity<YaoYiYaoBean> listBaseResponseEntity) {
            super.onPostExecute(listBaseResponseEntity);
            progressDialog.dismiss();
            if (SoapUtils.isResponseOk(YaoYiYaoActivity.this, listBaseResponseEntity)) {
                LogUtils.e(GsonUtils.getDateGson(null).toJson(listBaseResponseEntity));
                main = listBaseResponseEntity.ReturnMessage.MyCount;
                othercount = listBaseResponseEntity.ReturnMessage.OtherCount;
                andd = main + othercount;


                String starTiem = listBaseResponseEntity.ReturnMessage.AcStartTime;
                String currentTime = listBaseResponseEntity.ReturnMessage.CurrentTime;
                String endTime = listBaseResponseEntity.ReturnMessage.AcEndTimes;
                Date dateCurrentTime = DateUtils.Parse2Date(currentTime, DateUtils.yyyyMMddHHmmss);
                Date dateStarTiem = DateUtils.Parse2Date(starTiem, DateUtils.yyyyMMddHHmmss);
                Date dateEndTime = DateUtils.Parse2Date(endTime, DateUtils.yyyyMMddHHmmss);
                if (dateCurrentTime.getTime() > dateStarTiem.getTime() && dateCurrentTime.getTime() < dateEndTime.getTime()) {
                    txt_cisuo.setVisibility(View.VISIBLE);
                    text_count.setVisibility(View.VISIBLE);
                    np__number.setText(andd + "");

                    mShakeListener = new ShakeListener(YaoYiYaoActivity.this);
                    mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
                        public void onShake() {
                            startAnim();  //开始 摇一摇手掌动画
                            mShakeListener.stop();
                            startVibrato(); //开始 震动
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (main >= 0) {
                                        main--;
                                    } else {
                                        if (othercount > 0)
                                            othercount--;
                                    }
                                    andd = main + othercount;

                                    if (andd >= 0) {
                                        np__number.setText(andd + "");
                                        AsyncYaoYiYao as = new AsyncYaoYiYao();
                                        as.execute(" ");
                                    } else {
                                        ToastUtils.show(getApplication(), "您己没有次数", 0);
                                    }

                                    mVibrator.cancel();
                                    mShakeListener.stop();
                                }
                            }, 2000);
                        }
                    });
                } else {
                    np__number.setText("活动已结束");
                    txt_cisuo.setVisibility(View.GONE);
                    text_count.setVisibility(View.GONE);
                }




            }
        }
    }

    //获取摇一摇次数接口
    private void getYaoNum(){
        Map map = new HashMap();
        Web.getInstance().getYaoYiYaoNum(map, new OnRequestLinstener<BaseResponseEntity<YaoYiYaoBean>>() {
            @Override
            public void success(BaseResponseEntity<YaoYiYaoBean> msgCenterBeanBaseResponseEntity) {
                LogUtils.e(msgCenterBeanBaseResponseEntity.ReturnMessage + "");
                if (SoapUtils.isResponseOk(YaoYiYaoActivity.this, msgCenterBeanBaseResponseEntity)) {
                    LogUtils.e(GsonUtils.getDateGson(null).toJson(msgCenterBeanBaseResponseEntity));
                    main = msgCenterBeanBaseResponseEntity.ReturnMessage.MyCount;
                    othercount = msgCenterBeanBaseResponseEntity.ReturnMessage.OtherCount;
                    andd = main + othercount;


                    String starTiem = msgCenterBeanBaseResponseEntity.ReturnMessage.AcStartTime;
                    String currentTime = msgCenterBeanBaseResponseEntity.ReturnMessage.CurrentTime;
                    String endTime = msgCenterBeanBaseResponseEntity.ReturnMessage.AcEndTimes;
                    Date dateCurrentTime = DateUtils.Parse2Date(currentTime, DateUtils.yyyyMMddHHmmss);
                    Date dateStarTiem = DateUtils.Parse2Date(starTiem, DateUtils.yyyyMMddHHmmss);
                    Date dateEndTime = DateUtils.Parse2Date(endTime, DateUtils.yyyyMMddHHmmss);
                    if (dateCurrentTime.getTime() > dateStarTiem.getTime() && dateCurrentTime.getTime() < dateEndTime.getTime()) {
                        txt_cisuo.setVisibility(View.VISIBLE);
                        text_count.setVisibility(View.VISIBLE);
                        np__number.setText(andd + "");

                        mShakeListener = new ShakeListener(YaoYiYaoActivity.this);
                        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
                            public void onShake() {
                                startAnim();  //开始 摇一摇手掌动画
                                mShakeListener.stop();
                                startVibrato(); //开始 震动
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (main >= 0) {
                                            main--;
                                        } else {
                                            if (othercount > 0)
                                                othercount--;
                                        }
                                        andd = main + othercount;

                                        if (andd >= 0) {
                                            np__number.setText(andd + "");
                                            AsyncYaoYiYao as = new AsyncYaoYiYao();
                                            as.execute(" ");
                                        } else {
                                            ToastUtils.show(getApplication(), "您己没有次数", 0);
                                        }

                                        mVibrator.cancel();
                                        mShakeListener.stop();
                                    }
                                }, 2000);
                            }
                        });
                    } else {
                        txt_cisuo.setText("活动已结束");
                        txt_cisuo.setBackgroundColor(getResources().getColor(R.color.white));
                        np__number.setVisibility(View.GONE);
                        text_count.setVisibility(View.GONE);
                    }


                }

            }

            @Override
            public void error(int code, String error) {
                LogUtils.e(error);
            }
        }, HttpUrl.YaoYiYaoNum, 0, RequestMethod.GET, new TypeToken<BaseResponseEntity<YaoYiYaoBean>>() {
        });
    }

    /**
     * 规则pop
     */

    private void showPop(String url) {
        View viewGui = getLayoutInflater().inflate(R.layout.activity_pop_guize, null);
        webView = (WebView) viewGui.findViewById(R.id.web_guize);
        webView.loadUrl(url);
//        mProgressDialog.dismiss();
        LinearLayout lin_popo = (LinearLayout) viewGui.findViewById(R.id.lin_popo);
        Button btn_pop_guiz = (Button) viewGui.findViewById(R.id.btn_pop_guiz);
        final PopupWindow mPop = new PopupWindow(viewGui, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        mPop.setFocusable(true);
        mPop.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(getResources().getColor(R.color.huise));
        mPop.setBackgroundDrawable(dw);
        mPop.showAtLocation(lin_popo, Gravity.CENTER, 0, 0);
        btn_pop_guiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop.dismiss();
                if (mShakeListener != null)
                    mShakeListener.start();
            }

        });

    }

    ProgressDialog mProgressDialog;


    //获取活动规则  获取规则pop
    private void getYaoYiYaoAction() {
        Map map = new HashMap();
        Web.getInstance().getYaoYiYaoAction(map, new OnRequestLinstener<BaseResponseEntity<String>>() {
            @Override
            public void success(BaseResponseEntity<String> msgCenterBeanBaseResponseEntity) {
                LogUtils.e(msgCenterBeanBaseResponseEntity.ReturnMessage + "");
                uil = msgCenterBeanBaseResponseEntity.ReturnMessage;
                showPop(uil);


            }

            @Override
            public void error(int code, String error) {
                LogUtils.e(error);
            }
        }, HttpUrl.GetGuize, 0, RequestMethod.GET, new TypeToken<BaseResponseEntity<String>>() {
        });
    }


    /**
     * 赠送pop
     *
     * @param str
     */
    private void showZengPop(String str, boolean showw) {
        View viewZeng = getLayoutInflater().inflate(R.layout.activity_zengsong_pop, null);
        container.addView(flakeView);
        LinearLayout red_pop_zeng = (LinearLayout) viewZeng.findViewById(R.id.contai);
        Button btn_zengG = (Button) viewZeng.findViewById(R.id.btn_zengG);
        Button btn_zeng = (Button) viewZeng.findViewById(R.id.btn_zeng);
        Button btn_yaoyao = (Button) viewZeng.findViewById(R.id.btn_yaoyao);

        final PopupWindow zPop = new PopupWindow(viewZeng, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        zPop.setFocusable(true);
        zPop.setOutsideTouchable(false);
//        ColorDrawable dww = new ColorDrawable(new BitmapDrawable());
        zPop.setBackgroundDrawable(new BitmapDrawable());
        zPop.showAtLocation(red_pop_zeng, Gravity.CENTER, 0, 0);

        TextView textView = (TextView) viewZeng.findViewById(R.id.text_money_z);
        textView.setText(str);

        flakeView.addFlakes(15);

        /**
         * 绘制的类型
         * @see View.LAYER_TYPE_HARDWARE
         * @see View.LAYER_TYPE_SOFTWARE
         * @see View.LAYER_TYPE_NONE
         */
        flakeView.setLayerType(View.LAYER_TYPE_NONE, null);
        /**
         * 赠送
         */
        btn_zeng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomToastDialog.Builder builder = new CustomToastDialog.Builder(YaoYiYaoActivity.this);
                builder.setTitle("赠送亲友");
                builder.setMessage("");
                builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClearEditText inputText = builder.inputText;
                        if (!TextUtils.isEmpty(inputText.getText())) {
                            GiverRedPacakge(keyid, inputText.getText().toString());
                        }
                        dialog.dismiss();
                        mShakeListener.start();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mShakeListener.start();
                    }
                });
                Dialog dialog = builder.create(true);
                dialog.show();
                if (mShakeListener != null)
                    mShakeListener.stop();
                zPop.dismiss();
            }
        });
        /**
         *关闭赠送pop按钮
         */
        btn_zengG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zPop.dismiss();
                mShakeListener.start();
            }
        });
        /**
         * 接着摇
         */
        btn_yaoyao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (container != null) {
                    container.removeAllViews();
                }
                zPop.dismiss();
                mShakeListener.start();
            }
        });

        /**
         * 移除动画
         */
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //设置2秒后
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            container.removeAllViews();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        if (!showw)
            thread.start();
        MediaPlayer player = MediaPlayer.create(this, R.raw.sample2);
        player.start();
    }

    class GetUrLHanlder extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showPop(uil);
        }

    }

    ProgressDialog dialog;


    //赠送接口
    class AsyncGive extends AsyncTask<DesEncToDes, Integer, BaseResponseEntity<String>> {

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(YaoYiYaoActivity.this, AlertDialog.THEME_HOLO_LIGHT);
            dialog.setMessage("正在赠送中请稍后......");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected BaseResponseEntity<String> doInBackground(DesEncToDes... params) {
            String reqBody = GsonUtils.getGson().toJson(params[0]);
            String result = NetUtils.postRequest(HttpUrl.GiveRedPag, reqBody);
            Type type = new TypeToken<BaseResponseEntity<String>>() {
            }.getType();
            BaseResponseEntity responseEntity = GsonUtils.getGson().fromJson(result, type);
            LogUtils.e(GsonUtils.getDateGson(null).toJson(responseEntity));
            LogUtils.e(result + " ");
            return responseEntity;
        }

        @Override
        protected void onPostExecute(BaseResponseEntity<String> stringBaseResponseEntity) {
            super.onPostExecute(stringBaseResponseEntity);

            if (stringBaseResponseEntity != null && stringBaseResponseEntity.ReturnStatus.equals("0")) {
                ToastUtils.show(YaoYiYaoActivity.this, stringBaseResponseEntity.Remarks);
            } else {
                ToastUtils.show(YaoYiYaoActivity.this, stringBaseResponseEntity.Remarks);
            }

            dialog.dismiss();
        }
    }

    public void GiverRedPacakge(String KeyId, String PhoneNum) {

        AsyncGive asyncAvailableAmount = new AsyncGive();

        DesEncToDes des = new DesEncToDes();
        AppHongbao appHongbao = new AppHongbao();
        AppointEntity entity = new AppointEntity();
        entity.Ca_RecordId = KeyId;
        entity.GivePhone = PhoneNum;
        appHongbao.appointEntity = entity;
        try {
            des.DesEncToDes = DigestUtils.encrypt(GsonUtils.getDateGson(null).toJson(appHongbao), BusConstants.despas, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        asyncAvailableAmount.execute(des);


    }

    class AppHongbao {
        public AppointEntity appointEntity;
    }

    class AppointEntity {
        /**
         * {"DesEncToDes":"加密{"AppointEntity":{"Ca_RecordId":"红包KeyId","GivePhone":"赠送手机号"}}"}
         */
        public String Ca_RecordId;
        public String GivePhone;
    }
//    public ImageView imageView;
//    public Button button;
//
//    static int x = 0, xx = 0, y = 0, yy = 0;
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_yaoyiyao;
//    }
//
//    @Override
//    public void initViews() {
//        imageView = (ImageView) findViewById(R.id.imageView);
//        button = (Button) findViewById(R.id.button);
//    }
//
//    @Override
//    public void initData() {
//    }
//
//
//    public void imgClick(View view) {
//        Toast.makeText(this, "ImageView", Toast.LENGTH_SHORT).show();
//    }
//
//    public void buttonClick(View view) {
//
//
////         xx += 20;
////         TranslateAnimation ta = new TranslateAnimation(x, xx, y, yy);//设置动画的偏移位移
////         x += 20;
////        ta.setDuration(1000);//设置动画的时长
////        ta.setFillAfter(true);//设置动画结束后停留在该位置
////        imageView.startAnimation(ta);
//
//        //属性动画调用start()方法后是一个异步操作
////        ObjectAnimator.ofFloat(imageView, "translationX", 0F, 360F).setDuration(1000).start();//X轴平移旋转
////        ObjectAnimator.ofFloat(imageView, "translationY", 0F, 360F).setDuration(1000).start();//Y轴平移旋转
////        ObjectAnimator.ofFloat(imageView, "rotation", 0F, 360F).setDuration(1000).start();//360度旋转
//
//        //同步动画设计
////        PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("translationX", 0, 360F);
////        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("translationY", 0, 360F);
////        PropertyValuesHolder p3 = PropertyValuesHolder.ofFloat("rotation", 0, 360F);
////        ObjectAnimator.ofPropertyValuesHolder(imageView, p1, p2 ,p3).setDuration(1000).start();
//
//        //通过AnimatiorSet来设计同步执行的多个属性动画
//        ObjectAnimator animator1 = ObjectAnimator.ofFloat(imageView, "translationX", 0F, 360F);//X轴平移旋转
//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(imageView, "translationY", 0F, 360F);//Y轴平移旋转
//        ObjectAnimator animator3 = ObjectAnimator.ofFloat(imageView, "rotation", 0F, 360F);//360度旋转
//        AnimatorSet set = new AnimatorSet();
//        //set.playSequentially(animator1, animator2, animator3);//分步执行
//        //set.playTogether(animator1, animator2, animator3);//同步执行
//
//        //属性动画的执行顺序控制
//        // 先同步执行动画animator2和animator3,然后再执行animator1
//        set.play(animator3).with(animator1);
//        set.play(animator2).after(animator3);
//
//        set.setDuration(1000);
//        set.start();
//
//

}
