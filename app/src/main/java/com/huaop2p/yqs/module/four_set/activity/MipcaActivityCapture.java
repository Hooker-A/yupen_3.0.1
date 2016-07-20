package com.huaop2p.yqs.module.four_set.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.CustomD;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.InvestStepCache;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.four_set.entity.Code2Bean;
import com.huaop2p.yqs.module.four_set.entity.ProductBean;
import com.huaop2p.yqs.module.four_set.entity.SingleCons;
import com.huaop2p.yqs.module.logon.activity.LoginActivity;
import com.huaop2p.yqs.module.one_home.Module.android.CaptureActivityHandler;
import com.huaop2p.yqs.module.one_home.Module.android.InactivityTimer;
import com.huaop2p.yqs.module.one_home.Module.camera.CameraManager;
import com.huaop2p.yqs.module.one_home.Module.view.ViewfinderView;
import com.huaop2p.yqs.module.two_wealth.activity.WealthCenterDetailActivity;
import com.huaop2p.yqs.utils.DigestUtils;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.lidroid.xutils.util.LogUtils;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by zgq on 2016/7/5.
 * 作者:  zhu  guo qing
 * 时间:  2016/7/5 14:10
 * 功能:  没有客户经理
 */
public class MipcaActivityCapture extends BaseActivity implements SurfaceHolder.Callback {

    protected CaptureActivityHandler handler;
    protected ViewfinderView viewfinderView;
    protected boolean hasSurface;
    protected Vector<BarcodeFormat> decodeFormats;
    protected String characterSet;
    protected InactivityTimer inactivityTimer;
    protected MediaPlayer mediaPlayer;
    protected boolean playBeep;
    protected static final float BEEP_VOLUME = 0.10f;
    protected boolean vibrate;
    protected TextView txt_right_opt;
    protected boolean canStepOver = false;
    public static final String INTENT_STEP_OVER = "INTENT_STEP_OVER";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_capture);
        canStepOver = getIntent().getBooleanExtra(INTENT_STEP_OVER, false);
        txt_right_opt = (TextView) findViewById(R.id.txt_right_opt);
        if (canStepOver) {
            txt_right_opt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            txt_right_opt.setVisibility(View.INVISIBLE);
        }
        //ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        SetActivityTitle("扫描二维码");
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * 系统推荐客户经理
     *
     * @param view
     */
    public void systemCusManager(View view) {
        finish();
        Intent tempCusManager = new Intent(MipcaActivityCapture.this, ScanedCustomerManager.class);
        tempCusManager.putExtra(ScanedCustomerManager.INTENT_IS_SYS_SELECT, true);
        startActivity(tempCusManager);
    }


    /**
     * 输入员工号码
     *
     * @param view
     */
    public void inputCusManager(View view) {
        final CustomD.Builder builder = new CustomD.Builder(MipcaActivityCapture.this);
        builder.setTitle(R.string.str_input_cus_manager_card);
        builder.setMessage("");

        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String cuscode = builder.inputText.getText().toString().trim();
                if (cuscode.length() > 0) {
                    startSacanDetilPage(cuscode);
                    dialog.dismiss();
                    finish();
                } else
                    return;
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        builder.create(true).show();
    }

    /**
     * ����ɨ����
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (resultString.equals("")) {
            Toast.makeText(MipcaActivityCapture.this, "Scan failed!", Toast.LENGTH_SHORT).show();
        } else {
//            Intent resultIntent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putString("result", resultString);
//            bundle.putParcelable("bitmap", barcode);
//            resultIntent.putExtras(bundle);
//            this.setResult(RESULT_OK, resultIntent);
            startSacanDetilPage(resultString);
        }
        MipcaActivityCapture.this.finish();
    }

    private static final int toChoujiang = 1;//去抽奖
    private static final int toProduct = 2;//跳转到产品
    private static final int toRedBao = 3;//跳转红包
    private static final int toCusManager = 4;//跳转到客户经理

    /**
     * @param res
     */
    private void startActivityBy(Code2Bean res) {
        if (res == null)
            return;

        if (res.type == toChoujiang) {
//            getLotteryPic2(res.dt);
        } else if (res.type == toProduct) {
            if (SingleCons.products == null || SingleCons.products.isEmpty()) {
                ToastUtils.show(this, "很遗憾没找到相应产品");
                finish();

            } else {
                for (int i = 0; i < SingleCons.products.size(); i++) {
                    ProductBean bean = SingleCons.products.get(i);
                    if (bean.KeyId == Integer.parseInt(res.dt)) {
                        Intent lotteryActivity = new Intent(MipcaActivityCapture.this, WealthCenterDetailActivity.class);
                        InvestStepCache.InvestProduct = bean;
                        startActivity(lotteryActivity);
                    }
                }
            }


        } else if (res.type == toRedBao) {
            if (ShareLocalUser.getInstance(this).getLoginState()) {
//                getHongBao(res.dt);
            } else {
                Intent startloginRegister = new Intent(MipcaActivityCapture.this, LoginActivity.class);
                startActivity(startloginRegister);
            }

        } else if (res.type == toCusManager) {
            startCusManager(res.dt);
        }
    }

    private static final String security = "k%g*p!j#";

    /**
     * 弹出 扫描结果页面
     *
     * @param resultString
     */
    private void startSacanDetilPage(String resultString) {
        Log.i("二维码", resultString);

        try {
            resultString = DigestUtils.decrypt(resultString, security, true);
            Log.i("二维码", resultString);

        } catch (Exception e) {
//            ToastUtils.show(MipcaActivityCapture.this, "二维码内容出错");
        }
//        if (viewfinderView.getShowCom()) {
//            Log.i("二维码", resultString);
//            Code2Bean res = null;
//            try {
//                res = GsonUtils.getDateGson(null).fromJson(resultString, Code2Bean.class);
//            } catch (Exception e) {
//                LogUtils.e(e.toString());
//                ToastUtils.show(MipcaActivityCapture.this,resultString);
//                return;
//            }
//            startActivityBy(res);
//        } else {
//            startCusManager(resultString);
//        }
    }

    /**
     * 跳转客户经理
     *
     * @param resultString
     */
    private void startCusManager(String resultString) {
        Intent startTempManager = new Intent(MipcaActivityCapture.this, ScanedCustomerManager.class);
        startTempManager.putExtra(ScanedCustomerManager.INTENT_SCAND_RESULT, resultString);
        startActivity(startTempManager);
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
//        if (handler == null) {
//            handler = new CaptureActivityHandler(this, decodeFormats,
//                    characterSet);
//        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    /**
     * 绑定红包
     */
//    private void getHongBao(String hongbaoId) {
//        HttpUtils httpUtils = new HttpUtils();
//        CrowdCJWeb.getHongBaoInfo(MipcaActivityCapture.this, hongbaoId, new IReqUICallback<BaseResponseEntity<String>>() {
//            @Override
//            public void onRenderSuccess(BaseResponseEntity<String> res) {
//                LogUtils.e(res.ReturnStatus);
//                if (res.ReturnStatus.equals("0")) {
//                    Intent intent = new Intent(MipcaActivityCapture.this, HongBaoActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else if (res.ReturnStatus.equals("1")) {
//                    ToastUtils.show(MipcaActivityCapture.this, res.ReturnReason);
//                }
//            }
//
//            @Override
//            public void onRenderFailure(String msg) {
//                LogUtils.e(msg);
//            }
//        });
//
//    }



//    private void getLotteryPic2(String DDH) {
//
//        XhttpUtils http = XhttpUtils.getInstance();
//        if (!http.isNetworkConnected(this)) {
//            ToastUtils.show(this, getString(R.string.net_error));//网络链接失败
//            return;
//        }
//        ProgressDialogUtils.showProgressDialog(this, null);
////        ReqComLoadBean reqBean = new ReqComLoadBean();
//        ReqComLoadBean.JsonEntity subReq = new ReqComLoadBean.JsonEntity();
//        subReq.DDH = DDH;
//        ReqBaseBean reqBaseBean = new ReqBaseBean();
//        ReqBaseBean.DesEncToDesZ desZ = reqBaseBean.new DesEncToDesZ();
//        desZ.AppointEntity = subReq;
//        try {
//            reqBaseBean.DesEncToDes = DigestUtils.encrypt(GsonUtils.getDateGson(null).toJson(desZ), BusConstants.despas, true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
////        reqBean.action = "DrawPictures";
////        reqBean.CustomerName = ShareLocalUser.getInstance(this).getLoginUser().LoginName;
////        reqBean.CustomerPass = ShareLocalUser.getInstance(this).getLoginPass();
////        reqBean.jsonEntity = GsonUtils.getDateGson(null).toJson(subReq);
//        String reqBody = GsonUtils.getGson().toJson(reqBaseBean);
//        http.send(HttpRequest.HttpMethod.POST, HttpUrl.DrawPictures, reqBody, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                String json = responseInfo.result;
//                LotteryBean bean = JSON.parseObject(json, LotteryBean.class);
//                if ("0".equals(bean.getReturnStatus())) {
//                    Intent lotteryActivity = new Intent(MipcaActivityCapture.this, LotteryActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("bean", bean);
//                    lotteryActivity.putExtras(bundle);
//                    startActivity(lotteryActivity);
//                } else {
//                    ToastUtils.show(MipcaActivityCapture.this, bean.getReturnReason());
//                }
//                ProgressDialogUtils.dismissProgressDialog();
//
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                ToastUtils.show(MipcaActivityCapture.this, s);
//
//                LogUtils.e(s);
//                ProgressDialogUtils.dismissProgressDialog();
//            }
//        });
//
//    }
}
