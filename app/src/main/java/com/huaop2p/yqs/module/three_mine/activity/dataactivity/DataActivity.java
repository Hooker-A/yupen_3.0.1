package com.huaop2p.yqs.module.three_mine.activity.dataactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.base.contant.HttpConnector;
import com.huaop2p.yqs.widget.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * Created by zgq on 2016/5/6.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/6 14:48
 * 功能:  我的资料
 */
public class DataActivity extends BaseActivity implements View.OnClickListener{

    private CircleImageView txt_data_hand;
    private RelativeLayout rl_nicheng,rl_xingbie,rl_iphone,rl_youxiang,rl_diqu,rl_dizhi;
    private TextView txt_data_nick,txt_data_sex,txt_data_iphone,txt_data_emil,txt_data_area,txt_data_site;
    private Intent mIntent;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acyivity_data);
        SetActivityTitle("我的资料");
        initData();
        /**
         * 头像初始化
         */
        if (ShareLocalUser.getInstance(this).getLoginState()) {
            ImageLoader.getInstance().displayImage(HttpConnector.HTTT_HOST + ShareLocalUser.getInstance(this).getLoginUser().UrlHeadPhoto, txt_data_hand);
            txt_data_nick.setText(ShareLocalUser.getInstance(this).getLoginUser().Nickname);
            txt_data_sex.setText(ShareLocalUser.getInstance(this).getLoginUser().Sex);
            txt_data_iphone.setText(ShareLocalUser.getInstance(this).getLoginUser().PhoneNum);
            txt_data_emil.setText(ShareLocalUser.getInstance(this).getLoginUser().Email);
            txt_data_area.setText(ShareLocalUser.getInstance(this).getLoginUser().Address);
//            txt_data_site.setText(ShareLocalUser.getInstance(this).getLoginUser().Address);
        } else {
            txt_data_hand.setBackgroundResource(R.drawable.wodelg);
        }
    }

    /**
     * 初始化控件
     */
    private void initData() {
        //头像
        txt_data_hand= (CircleImageView) findViewById(R.id.txt_data_hand);
        txt_data_hand.setOnClickListener(this);
        //昵称
        rl_nicheng= (RelativeLayout) findViewById(R.id.rl_nicheng);
        txt_data_nick= (TextView) findViewById(R.id.txt_data_nick);
        rl_nicheng.setOnClickListener(this);
        //性别
        rl_xingbie= (RelativeLayout) findViewById(R.id.rl_xingbie);
        txt_data_sex= (TextView) findViewById(R.id.txt_data_sex);
        rl_xingbie.setOnClickListener(this);
        //手机号
        rl_iphone= (RelativeLayout) findViewById(R.id.rl_iphone);
        txt_data_iphone= (TextView) findViewById(R.id.txt_data_iphone);
        rl_iphone.setOnClickListener(this);
        //邮箱
        rl_youxiang= (RelativeLayout) findViewById(R.id.rl_youxiang);
        txt_data_emil= (TextView) findViewById(R.id.txt_data_emil);
        rl_youxiang.setOnClickListener(this);
        //地区
        rl_diqu= (RelativeLayout) findViewById(R.id.rl_diqu);
        txt_data_area= (TextView) findViewById(R.id.txt_data_area);
        txt_data_area.setText(AreaActivity.CITY);
        rl_diqu.setOnClickListener(this);
        //地址
        rl_dizhi= (RelativeLayout) findViewById(R.id.rl_dizhi);
        txt_data_site= (TextView) findViewById(R.id.txt_data_site);
        rl_dizhi.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        txt_data_nick.setText(ShareLocalUser.getInstance(this).getLoginUser().Nickname);
        if (ShareLocalUser.getInstance(this).getLoginState()) {
            ImageLoader.getInstance().displayImage(HttpConnector.HTTT_HOST + ShareLocalUser.getInstance(this).getLoginUser().UrlHeadPhoto, txt_data_hand);
            if (ShareLocalUser.getInstance(this).getLoginUser().Nickname!=null){
                txt_data_nick.setText(ShareLocalUser.getInstance(this).getLoginUser().Nickname);

            }
            txt_data_sex.setText(ShareLocalUser.getInstance(this).getLoginUser().Sex);
            txt_data_iphone.setText(ShareLocalUser.getInstance(this).getLoginUser().PhoneNum);
            txt_data_emil.setText(ShareLocalUser.getInstance(this).getLoginUser().Email);
            txt_data_area.setText(ShareLocalUser.getInstance(this).getLoginUser().Address);
//            txt_data_site.setText(ShareLocalUser.getInstance(this).getLoginUser().Address);
        } else {
            txt_data_hand.setBackgroundResource(R.drawable.wodelg);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_nicheng:
                //昵称
                mIntent=new Intent(getApplicationContext(),NickActivity.class);
                startActivity(mIntent);
                break;
            case R.id.rl_xingbie:
                //性别
                mIntent=new Intent(getApplicationContext(),SexActivity.class);
                startActivity(mIntent);
                break;
            case R.id.rl_iphone:
                //电话号码
                mIntent=new Intent(getApplicationContext(),IphoneActivity.class);
                startActivity(mIntent);
                break;
            case R.id.rl_youxiang:
                //邮箱
                mIntent=new Intent(getApplicationContext(),E_mailActivity.class);
                startActivity(mIntent);
                break;
            case R.id.rl_diqu:
                //地区
                mIntent=new Intent(getApplicationContext(),CeshiActivity.class);
                startActivity(mIntent);

                break;
            case R.id.rl_dizhi:
                //地址
                mIntent=new Intent(getApplicationContext(),SiteActivity.class);
                startActivity(mIntent);
                break;
            //头像
            case R.id.txt_data_hand:
                mIntent=new Intent(getApplicationContext(),HandActivity.class);
                startActivity(mIntent);
//                overridePendingTransition(R.anim.activity_anim, R.anim.activity_mout);
                break;
//            case R.id.rl_diz:
//                mIntent=new Intent(getApplicationContext(),CeshiActivity.class);
//                startActivity(mIntent);
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
