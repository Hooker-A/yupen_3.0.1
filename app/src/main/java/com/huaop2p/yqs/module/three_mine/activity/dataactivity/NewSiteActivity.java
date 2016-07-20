package com.huaop2p.yqs.module.three_mine.activity.dataactivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.WaitDialog;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.three_mine.model.entity.SiteBean;
import com.huaop2p.yqs.module.three_mine.model.impl.MyDataHttp;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgq on 2016/6/12.
 * 作者:  zhu  guo qing
 * 时间:  2016/6/12 15:38
 * 功能: 新建收货地址
 */
public class NewSiteActivity extends BaseActivity implements View.OnClickListener{
    private Button bt_cler,bt_space;
    private EditText et_ren,et_iphone,et_addrs,et_addrrs,et_emms;
    private String name,iph,addr,addrr,emm;
    public static final String ADAP_BEAN="bean";
    private Intent intent;
    private SiteBean siteBean;
    private WaitDialog waitDialog;
    private boolean isdefal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsite);
        intent=getIntent();
        siteBean= (SiteBean) intent.getSerializableExtra(ADAP_BEAN);
        initData();
        if (siteBean!=null){
            intenData();

        }
        linData();

    }
    private void intenData(){
        et_ren.setText(siteBean.Name);
        et_iphone.setText(siteBean.PhoneNum);
        et_addrs.setText(siteBean.Area);
        et_addrrs.setText(siteBean.Address);
        et_emms.setText(siteBean.EMSNum);
        isdefal=siteBean.IsDefault;
    }

    /**
     * 控件初始化
     */
    private void initData() {
        bt_cler= (Button) findViewById(R.id.bt_cler);
        bt_cler.setOnClickListener(this);
        bt_space= (Button) findViewById(R.id.bt_space);
        bt_space.setOnClickListener(this);
        et_ren= (EditText) findViewById(R.id.et_ren);
        et_iphone= (EditText) findViewById(R.id.et_iphone);
        et_addrs= (EditText) findViewById(R.id.et_addrs);
        et_addrrs= (EditText) findViewById(R.id.et_addrrs);
        et_emms= (EditText) findViewById(R.id.et_emms);

    }

    /**
     * button监听
     */
    private void linData() {
        et_ren.addTextChangedListener(new MyTextWatcher());
        et_iphone.addTextChangedListener(new MyTextWatcher());
        et_addrs.addTextChangedListener(new MyTextWatcher());
        et_addrrs.addTextChangedListener(new MyTextWatcher());
        et_emms.addTextChangedListener(new MyTextWatcher());
//        cet_name.setText("");
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
            String str1 = et_ren.getText().toString();
            String str2 = et_iphone.getText().toString();
            String str3 = et_addrs.getText().toString();
            String str4 = et_addrrs.getText().toString();
            String str5 = et_emms.getText().toString();
            if (str1.equals("") || str2.equals("")||str3.equals("") || str4.equals("")||str5.equals("")) {
                bt_space.setTextColor(getResources().getColor(R.color.ipone_num_color));
                bt_space.setEnabled(false);
            }  else {
                bt_space.setTextColor(getResources().getColor(R.color.red));
                bt_space.setEnabled(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        name=et_ren.getText().toString();
        iph=et_iphone.getText().toString();
        addr=et_addrs.getText().toString();
        addrr=et_addrrs.getText().toString();
        emm=et_emms.getText().toString();
        switch (v.getId()){
            case R.id.bt_cler:
                finish();
                break;
            case R.id.bt_space:
                waitDialog=new WaitDialog(NewSiteActivity.this);
                waitDialog.show();
                if (name!=null){
                    initHttp();
                }else {
                    Toast toast=Toast.makeText(getApplicationContext(), "填写信息不完整,添加地址失败!", Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.show();
                    waitDialog.dismiss();


                }
                break;
        }
    }

    /**
     * 网络请求
     * {"Name":"姓名","PhoneNum":"手机号","Area":"地区","EMSNum":"邮政编码","Address":"详细地址","IsDefault":"true=默认收货地址 false=非默认收货地址"}
     */
    private void initHttp() {


        Map map=new HashMap();
        map.put("Name",name);
        map.put("PhoneNum",iph);
        map.put("Area",addr);
        map.put("Address",addrr);
        map.put("EMSNum",emm);
        map.put("IsDefault",isdefal);

        MyDataHttp.getInstance().PostAddAddress(map, new OnRequestLinstener<BaseResponseEntity<List<SiteBean>>>() {
            @Override
            public void success(BaseResponseEntity<List<SiteBean>> listBaseResponseEntity) {
                LogUtils.e(listBaseResponseEntity.ReturnMessage.get(0).KeyId);
                waitDialog.dismiss();
                finish();


            }

            @Override
            public void error(int code, String error) {
                LogUtils.e(error);
            }
        }, HttpUrl.PostAddAddress,0, RequestMethod.POST,new TypeToken<BaseResponseEntity<List<SiteBean>>>(){});
    }
}
