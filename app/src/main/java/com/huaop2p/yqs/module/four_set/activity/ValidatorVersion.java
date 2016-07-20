package com.huaop2p.yqs.module.four_set.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.NoAniActivity;
import com.huaop2p.yqs.module.four_set.entity.UpdateVersionBean;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.utils.auto.AppUtils;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.lidroid.xutils.util.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zgq on 2016/6/23.
 * 作者:  zhu  guo qing
 * 时间:  2016/6/23 10:14
 * 功能:  版本更新
 */
public class ValidatorVersion extends NoAniActivity {
    public static String INTENT_VERSION_OBJECT = "INTENT_VERSION_OBJECT";
    public int TAG;
    String versionobj = "";
    UpdateVersionBean versionBean = null;
    TextView txt_new_version, txt_update_log, txt_title_update, txt_win_title;
    Button confirm_btn;
    /* 下载包安装路径 */
    private static final String savePath = "/sdcard/updateyupen/";
    private int cc;

    private static final String saveFileName = savePath + "yupenupdate.apk";

    /* 进度条与通知ui刷新的handler和msg常量 */
    private ProgressBar mProgress;


    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_YUYU = 3;
    private static final int DOWN_YUYUO = 4;

    private static final int DOWN_OVER = 2;

    private int progress;

    private Thread downLoadThread;
    private ImageView img_yuyu;

    private boolean interceptFlag = false;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mProgress.setProgress(progress);
                    break;
                case DOWN_OVER:

                    installApk();
                    break;
                case DOWN_YUYU:
                    img_yuyu.layout(cc / 100 * progress, 0, cc / 98 * progress + img_yuyu.getMeasuredWidth(),img_yuyu.getHeight());
                        if (img_yuyu.getVisibility()== View.GONE)
                            img_yuyu.setVisibility(View.VISIBLE);
//                    LogUtils.e("------------------------->"+progress);
                    break;
                case DOWN_YUYUO:
                    img_yuyu.setVisibility(View.GONE);
                default:
                    break;
            }
        }
    };

    //    //外部接口让主Activity调用
//    public void checkUpdateInfo() {
//        showNoticeDialog();
//    }
    int thisversion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_validator_version);
        txt_new_version = (TextView) findViewById(R.id.txt_new_version);
        img_yuyu= (ImageView) findViewById(R.id.img_yuyu);
        img_yuyu.setVisibility(View.GONE);
        LogUtils.e("------------------------->");
//        TranslateAnimation tAnim = new TranslateAnimation(0, 1000, 0, 0);
//        tAnim.setDuration(6000);
//        img_yuyu.setAnimation(tAnim);
//        tAnim.start();
        txt_update_log = (TextView) findViewById(R.id.txt_update_log);
        mProgress = (ProgressBar) findViewById(R.id.progress);
        txt_title_update = (TextView) findViewById(R.id.txt_title_update);
        txt_win_title = (TextView) findViewById(R.id.txt_win_title);
//        mProgress.setBackgroundColor(Color.RED);
        confirm_btn = (Button) findViewById(R.id.confirm_btn);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (versionBean == null || versionBean.Url.length() == 0)
                    return;

                 cc= mProgress.getMeasuredWidth();
                LogUtils.e(cc+"************");
                downloadApk();
                confirm_btn.setEnabled(false);
                confirm_btn.setBackgroundResource(R.drawable.dialog_check_right_btn);
            }
        });

        versionobj = getIntent().getStringExtra(INTENT_VERSION_OBJECT);
        TAG=getIntent().getIntExtra("tag",0);
        if (versionobj != null) {
            try {
                thisversion = AppUtils.getVersionCode(this);
                versionBean = GsonUtils.getGson().fromJson(versionobj, UpdateVersionBean.class);
                if (thisversion >= versionBean.Code) {
                    txt_win_title.setText(getResources().getString(R.string.str_is_new_version));
                    txt_title_update.setText("升级日志：");
                    confirm_btn.setEnabled(false);
                    confirm_btn.setBackgroundResource(R.drawable.dialog_check_right_btn);
                    finish();
                    if (TAG==-1){

                    }else {
                        ToastUtils.show(getApplicationContext(), "当前已是最新版本!!", Toast.LENGTH_LONG);
                    }

                }
                txt_new_version.setText(versionBean.Name);
                String updattext = versionBean.updateText;
                txt_update_log.setText(Html.fromHtml(updattext));
            } catch (Exception e) {
                ToastUtils.show(this, "参数不正确", Toast.LENGTH_SHORT);
                finish();
            }
        }

    }


    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(versionBean.Url);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];

                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);


                    //更新进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    mHandler.sendEmptyMessage(DOWN_YUYU);

                    if (numread <= 0) {
                        //下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        mHandler.sendEmptyMessage(DOWN_YUYUO);
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!interceptFlag);//点击取消就停止下载.

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    /**
     * 下载apk
     */

    private void downloadApk() {
        Toast.makeText(this, "正在更新请稍后...", Toast.LENGTH_LONG).show();
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    /**
     * 安装apk
     */
    private void installApk() {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        this.startActivity(i);
        finish();
    }

    /**
     * 关闭 更新
     *
     * @param view
     */
    public void closeValidator(View view) {
        interceptFlag = true;
        finish();
    }
}
