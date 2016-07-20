package com.huaop2p.yqs.module.four_set.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.entity.BusConstants;
import com.huaop2p.yqs.module.four_set.entity.ReqSelBean;
import com.huaop2p.yqs.module.four_set.model.MyFinancialWeb;
import com.huaop2p.yqs.module.logon.entity.LoginedBean;
import com.huaop2p.yqs.utils.AppInentUtils;
import com.huaop2p.yqs.utils.ImageUtils;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgq on 2016/5/25.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/25 11:41
 * 功能:  有数据的客户经理
 */
public class CustomerManagerDetialActivity extends BaseActivity implements View.OnClickListener{

    TextView txt_manager_name, txt_user_phone_number, txt_manager_organization, txt_customer_introduction;
    ImageView img_cus_manager_photo;
    RelativeLayout relativeCustomerManager;
    LinearLayout ll_bg_customer;
    ImageView iv_individual_arrow1, iv_individual_arrow2;
    private Button img_tiaoguo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custdetia);
        SetActivityTitle("我的客户经理");
        initData();
        httpData();

        String pass = ShareLocalUser.getInstance(this).getLoginPass();
    }

    /**
     *  {"KeyIds":"15982246190"}

     */
    private void httpData() {
        LoginedBean log = ShareLocalUser.getInstance(this).getLoginUser();
        String key=log.CustomerManagerID;
        Map map=new HashMap();
        map.put("KeyIds",key);
        MyFinancialWeb.getInstance().PostSelManager(map, new OnRequestLinstener<BaseResponseEntity<ReqSelBean>>() {
            @Override
            public void success(BaseResponseEntity<ReqSelBean> reqSelBeanBaseResponseEntity) {
                if (reqSelBeanBaseResponseEntity == null) {
                    ToastUtils.show(CustomerManagerDetialActivity.this, R.string.str_error_net_conect, Toast.LENGTH_SHORT);
                } else if (reqSelBeanBaseResponseEntity.ReturnStatus.equals(BusConstants.Return_Status_0)) {
                    ReqSelBean reqSelBean = reqSelBeanBaseResponseEntity.ReturnMessage;
                    byte[] beforestr = Base64.decode(reqSelBean.UrlHeadPhoto, Base64.DEFAULT);
                    Bitmap biamp = ImageUtils.byteToBitmap(beforestr);
                    img_cus_manager_photo.setImageBitmap(biamp);
                    if (reqSelBean.Sex.equals("男")) {
                        txt_manager_name.setText(reqSelBean.TrueName);
                    } else {
                        txt_manager_name.setText(reqSelBean.TrueName);
                    }
                    txt_manager_organization.setText(reqSelBean.Department);
                    cusManagerNumber = reqSelBean.PhoneNum;
                    txt_customer_introduction.setText("       " + reqSelBean.Remarks);
                }else if (reqSelBeanBaseResponseEntity.ReturnStatus.equals(BusConstants.Return_Status_lose_1)){
                    LoginedBean user = ShareLocalUser.getInstance(CustomerManagerDetialActivity.this).getLoginUser();
                    user.CustomerManagerID = null;
                    ShareLocalUser.getInstance(CustomerManagerDetialActivity.this).addLoginUser(user);
                    ToastUtils.show(CustomerManagerDetialActivity.this, getResources().getString(R.string.str_cus_manager_is_lose));
                }else if (reqSelBeanBaseResponseEntity.ReturnStatus.equals(BusConstants.Return_Status_lose_1)){
                    ToastUtils.show(CustomerManagerDetialActivity.this, reqSelBeanBaseResponseEntity.ReturnReason, Toast.LENGTH_SHORT);
                }else if (reqSelBeanBaseResponseEntity.ReturnStatus.equals(BusConstants.Return_Status_2)){
                    ToastUtils.show(CustomerManagerDetialActivity.this, reqSelBeanBaseResponseEntity.ReturnReason);
                }

            }

            @Override
            public void error(int code, String error) {

            }
        }, HttpUrl.PostSelManager,0, RequestMethod.POST,new TypeToken<BaseResponseEntity<ReqSelBean>>(){});
    }

    private void initData() {
        ll_bg_customer = (LinearLayout) findViewById(R.id.ll_bg_customer);
//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.includeTitle);
        img_tiaoguo= (Button)findViewById(R.id.img_tiaoguo);
        img_tiaoguo.setText("投诉");
        img_tiaoguo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneStr = getResources().getString(R.string.str_complain_cus_manager);
                AppInentUtils.callPhone(CustomerManagerDetialActivity.this, phoneStr);
            }
        });

        ImageView iv_call400 = (ImageView) findViewById(R.id.iv_call400);
        ImageView iv_call_connect = (ImageView) findViewById(R.id.iv_call_connect);

        iv_call400.setOnClickListener(this);
        iv_call_connect.setOnClickListener(this);

        iv_individual_arrow1 = (ImageView) findViewById(R.id.iv_individual_arrow1);
        iv_individual_arrow2 = (ImageView) findViewById(R.id.iv_individual_arrow2);

        txt_manager_name = (TextView) findViewById(R.id.txt_manager_name);
        txt_manager_organization = (TextView) findViewById(R.id.txt_manager_organization);
        txt_customer_introduction = (TextView) findViewById(R.id.txt_customer_introduction);
        img_cus_manager_photo = (ImageView) findViewById(R.id.img_cus_manager_photo);
    }

    /**
     * 个人简介click
     *
     * @param view
     */
    public void onIntroductClick(View view) {
        if (txt_customer_introduction.getVisibility() == View.GONE) {
            txt_customer_introduction.setVisibility(View.VISIBLE);
            iv_individual_arrow1.setVisibility(View.GONE);
            iv_individual_arrow2.setVisibility(View.VISIBLE);
        } else {
            txt_customer_introduction.setVisibility(View.GONE);
            iv_individual_arrow1.setVisibility(View.VISIBLE);
            iv_individual_arrow2.setVisibility(View.GONE);
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_call400:
                String phoneStr = getResources().getString(R.string.str_complain_cus_manager);
                AppInentUtils.callPhone(CustomerManagerDetialActivity.this, phoneStr);
                break;
            case R.id.iv_call_connect:
                if (cusManagerNumber != null) {
                    AppInentUtils.callPhone(CustomerManagerDetialActivity.this, cusManagerNumber);
                } else {
                   ToastUtils.show(CustomerManagerDetialActivity.this, "客户经理没有预留手机号");
                }

                break;
        }
    }
    private String cusManagerNumber;



}
