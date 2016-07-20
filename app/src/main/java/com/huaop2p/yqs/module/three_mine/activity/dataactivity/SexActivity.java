package com.huaop2p.yqs.module.three_mine.activity.dataactivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.WaitDialog;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.logon.entity.LoginedBean;
import com.huaop2p.yqs.module.three_mine.model.impl.MyDataHttp;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgq on 2016/5/9.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/9 15:56
 * 功能:  性别
 */
public class SexActivity extends BaseActivity implements OnClickListener {
    private LinearLayout rl_boy, rl_gril;
    private TextView txt_gril,txt_boy;
    private CheckBox img_boy,img_gril;
    private String boy,gril;
    private Map mMap;
    private WaitDialog waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex);
        SetActivityTitle("性别");

        initData();
        if (ShareLocalUser.getInstance(this).getLoginState()){
            String b=ShareLocalUser.getInstance(this).getLoginUser().Sex;
            if (b!=null){
            if (b.equals("男")){
                img_boy.setVisibility(View.VISIBLE);
            }else {
                img_gril.setVisibility(View.VISIBLE);
            }
          }
        }


    }

    private void initData() {
        rl_boy = (LinearLayout) findViewById(R.id.rl_boy);
        txt_boy= (TextView) findViewById(R.id.txt_boy);
        img_boy= (CheckBox) findViewById(R.id.img_boy);
        txt_boy.setText("男");
        rl_boy.setOnClickListener(this);
        rl_gril = (LinearLayout) findViewById(R.id.rl_gril);
        txt_gril= (TextView) findViewById(R.id.txt_gril);
        img_gril= (CheckBox) findViewById(R.id.img_gril);
        txt_gril.setText("女");
        rl_gril.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ShareLocalUser.getInstance(this).getLoginState()){
            String b=ShareLocalUser.getInstance(this).getLoginUser().Sex;
            if (b!=null){
                if (b.equals("男")){
                    img_boy.setVisibility(View.VISIBLE);
                }else {
                    img_gril.setVisibility(View.VISIBLE);
                }
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * ：{"Type":"EditSex","Sex":"男/女"}
             */
            case R.id.rl_boy:
                waitDialog=new WaitDialog(SexActivity.this);
                waitDialog.show();
                mMap=new HashMap();
                boy=txt_boy.getText().toString();
                mMap.put("Type","EditSex");
                mMap.put("Sex", boy);
                MyDataHttp.getInstance().PostPersonInfos(mMap, new OnRequestLinstener<BaseResponseEntity>() {


                    @Override
                    public void success(BaseResponseEntity baseResponseEntity) {
                        LoginedBean bean = ShareLocalUser.getInstance(SexActivity.this).getLoginUser();
                        bean.Sex = baseResponseEntity.ReturnMessage + "";
                        ShareLocalUser.getInstance(SexActivity.this).addLoginUser(bean);
                        txt_boy.setText(bean.Sex);
                        waitDialog.dismiss();
                        finish();


                    }

                    @Override
                    public void error(int code, String error) {
                        LogUtils.e(code + "768120");
                    }
                }, HttpUrl.PostPersonInfos, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity>() {
                });
                img_boy.setVisibility(View.VISIBLE);
                img_gril.setVisibility(View.GONE);
                rl_gril.setClickable(false);
                break;



            case R.id.rl_gril:
                mMap=new HashMap();
                gril=txt_gril.getText().toString();
                mMap.put("Type","EditSex");
                mMap.put("Sex", gril);
                MyDataHttp.getInstance().PostPersonInfos(mMap, new OnRequestLinstener<BaseResponseEntity>() {
                    @Override
                    public void success(BaseResponseEntity baseResponseEntity) {
                        LoginedBean bean = ShareLocalUser.getInstance(SexActivity.this).getLoginUser();
                        bean.Sex = baseResponseEntity.ReturnMessage + "";
                        ShareLocalUser.getInstance(SexActivity.this).addLoginUser(bean);
                        txt_boy.setText(bean.Sex);
                        finish();

                    }

                    @Override
                    public void error(int code, String error) {
                        LogUtils.e(code + "768120");
                    }
                }, HttpUrl.PostPersonInfos, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity>() {
                });
                img_gril.setVisibility(View.VISIBLE);
                img_boy.setVisibility(View.GONE);
                rl_boy.setClickable(false);
                break;
        }

    }
}
