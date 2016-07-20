package com.huaop2p.yqs.module.logon.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.WaitDialog;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.logon.model.MyFinancialWeb;
import com.huaop2p.yqs.module.three_mine.activity.dataactivity.DataActivity;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.huaop2p.yqs.widget.SlidSwitch;
import com.huaop2p.yqs.widget.SwitchManager;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgq on 2016/4/27.
 * 作者:  zhu  guo qing
 * 时间:  2016/4/27 10:50
 * 功能:  我的账号
 */
public class AccountActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rl_shenfen, rl_ziliao, rl_ercode, rl_xiugai, rl_kaiqi, rl_shezhiss;
    private TextView txt_ureName,txt_shoushimm,txt_ok;
    private SlidSwitch switchSetGesture;
    boolean isopengs;//是否打开手势密码
    public static final String LOGIN_PHOTO = "login_photo_url";  //登陆界面返回的url request
    private static final int MOBILE_GET_PIC = 1; // 本地相册
    private static final int TAKE_PHOTO = 2; // 相机拍照
    private static final int CROP_PHOTO = 3;//图片剪裁
    public static final int TAKE_IMG_PSE = 6;
    private Intent mIntent;
    private WaitDialog waitDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        SetActivityTitle("我的账号");
        waitDialog=new WaitDialog(AccountActivity.this);
        waitDialog.show();
        initData();
        httpData();

        SwitchManager.mSlidSwitch = switchSetGesture;
        /**
         * 是否开启手势密码
         */
        switchSetGesture.setOnSwitchChangedListener(new SlidSwitch.OnSwitchChangedListener() {
            @Override
            public void onSwitchChanged(SlidSwitch obj, int status) {
                if (status == 2 || status == 0) {
                    SwitchManager.isMyAccount = true;
                    validateGsPass();
                    switchSetGesture.setStatus(SwitchManager.setSwichSta);
                } else {
                    mIntent = new Intent(getApplicationContext(), LockSetupActivity.class);
                    startActivityForResult(mIntent, 111);
                }
            }

        });
    }
    void validateGsPass() {
        //开启手势密码
        if (ShareLocalUser.getInstance(this).getIsOpenGsPass()) {
            String gspass = ShareLocalUser.getInstance(getApplicationContext()).getGsPass();
            if (gspass != null && gspass.length() > 0) {
                mIntent = new Intent(AccountActivity.this, LockValidatorActivity.class);
                startActivity(mIntent);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //身份认证
            case R.id.rl_shenfen:
                String txt=txt_ok.getText().toString();
                if (txt!=null){
                    if (txt.equals("已认证")){
                        Intent intentA=new Intent(getApplicationContext(),AeestActivity.class);
                        startActivity(intentA);
                    }else {
                        Intent intentR=new Intent(getApplicationContext(),RankActivity.class);
                        startActivity(intentR);
                    }
                }

                break;
            //我的资料
            case R.id.rl_ziliao:
                mIntent = new Intent(getApplicationContext(), DataActivity.class);
                startActivity(mIntent);
                break;
            //我的二维码
            case R.id.rl_ercode:
                if (ShareLocalUser.getInstance(this).getLoginState()) {
                    mIntent = new Intent(AccountActivity.this, CodeActivity.class);
                    startActivity(mIntent);
                } else {
                    mIntent = new Intent(AccountActivity.this, LogonActivity.class);
                    startActivity(mIntent);
                }
                break;
            //修改登录密码
            case R.id.rl_xiugai:
                mIntent=new Intent(getApplicationContext(),LogoPassActivity.class);
                startActivity(mIntent);
                break;
            //开启手势密码
            case R.id.rl_kaiqi:

                break;
            //设置手势密码
            case R.id.rl_shezhiss:
                String gspass = ShareLocalUser.getInstance(getApplicationContext()).getGsPass();
                if (gspass == null || gspass.length() < 4||!ShareLocalUser.getInstance(this).getIsOpenGsPass()) {
                    Intent intent = new Intent(getApplicationContext(), LockSetupActivity.class);
                    startActivityForResult(intent,222);
                } else {
                    Intent modifygspass = new Intent(this, LockGSModifyActivity.class);
                    startActivity(modifygspass);
                }
                break;
            default:
                break;

        }
    }

    /**
     * 金账户状态的网络加载
     */
    private void httpData() {
        Map map=new HashMap();

        MyFinancialWeb.getInstance().GetCheckGoldAccount(map, new OnRequestLinstener<BaseResponseEntity>() {
            @Override
            public void success(BaseResponseEntity baseResponseEntity) {

                String requst=baseResponseEntity.ReturnMessage.toString();
                if (requst.length() >0){
                    //显示通过认证的页面
                    txt_ok.setText("已认证");
                }else {
                    //验证页面
                    txt_ok.setText("未认证");
                }
                waitDialog.dismiss();

            }

            @Override
            public void error(int code, String error) {

            }
        }, HttpUrl.GetCheckGoldAccount,0, RequestMethod.POST,new TypeToken<BaseResponseEntity>(){});
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (waitDialog!=null){
            waitDialog.dismiss();
        }
//        setAccountByStart();
        String gspass = ShareLocalUser.getInstance(getApplicationContext()).getGsPass();
        if (gspass == null || gspass.length() < 4||!ShareLocalUser.getInstance(this).getIsOpenGsPass()) {
            txt_shoushimm.setText("设置手势密码");
        } else {
            txt_shoushimm.setText("修改手势密码");
        }
        isopengs = ShareLocalUser.getInstance(this).getIsOpenGsPass();
        switchSetGesture.setStatus(isopengs);
    }

    private Bitmap bitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==111){
            if(resultCode==RESULT_OK){
                if(data.getBooleanExtra(LockSetupActivity.IS_SETSUCESS,false))
                    ShareLocalUser.getInstance(getApplicationContext()).addIsOpenGsPass(true);
            }
        }else if(requestCode==222){
            if(resultCode==RESULT_OK){
                if(data.getBooleanExtra(LockSetupActivity.IS_SETSUCESS,false))
                    ShareLocalUser.getInstance(getApplicationContext()).addIsOpenGsPass(true);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }




    /**
     * 初始化控件
     */
    private void initData() {
        rl_shenfen = (RelativeLayout) findViewById(R.id.rl_shenfen);
        rl_shenfen.setOnClickListener(this);
        rl_ziliao = (RelativeLayout) findViewById(R.id.rl_ziliao);
        rl_ziliao.setOnClickListener(this);
        rl_ercode = (RelativeLayout) findViewById(R.id.rl_ercode);
        rl_ercode.setOnClickListener(this);
        rl_xiugai = (RelativeLayout) findViewById(R.id.rl_xiugai);
        rl_xiugai.setOnClickListener(this);
        rl_kaiqi = (RelativeLayout) findViewById(R.id.rl_kaiqi);
        rl_kaiqi.setOnClickListener(this);
        rl_shezhiss = (RelativeLayout) findViewById(R.id.rl_shezhiss);
        rl_shezhiss.setOnClickListener(this);
        txt_shoushimm= (TextView) findViewById(R.id.txt_shoushimm);//修改or设置
        switchSetGesture= (SlidSwitch) findViewById(R.id.switchSetGesture);
        txt_ok= (TextView) findViewById(R.id.txt_ok);

    }

}
