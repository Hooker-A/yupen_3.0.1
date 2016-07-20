package com.huaop2p.yqs.module.three_mine.activity.dataactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.WaitDialog;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.three_mine.model.entity.SiteBean;
import com.huaop2p.yqs.module.three_mine.model.impl.MyDataHttp;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgq on 2016/6/28.
 * 作者:  zhu  guo qing
 * 时间:  2016/6/28 16:35
 * 功能:  编辑收货地址
 */
public class EditSiteActivity extends BaseActivity implements View.OnClickListener{

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
        setContentView(R.layout.activity_editsite);
        initData();
        intent=getIntent();
        siteBean= (SiteBean) intent.getSerializableExtra(ADAP_BEAN);
        if (siteBean!=null){
            intenData();

        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_cler:
                finish();
                break;
            case R.id.bt_space:
                waitDialog=new WaitDialog(EditSiteActivity.this);
                waitDialog.show();
                EdetHttp();

                break;
        }
    }
    /**
     * 修改地址
     * @param
     * {"KeyId":"需要修改地址的KeyId","Name":"姓名","PhoneNum":"手机号","Area":"地区","EMSNum":"邮政编码","Address":"详细地址","IsDefault":"true=默认收货地址 false=非默认收货地址"}
     */
    private void EdetHttp(){
        String Name=et_ren.getText().toString();
        String PhoneNum=et_iphone.getText().toString();
        String Area=et_addrs.getText().toString();
        String Address=et_addrrs.getText().toString();
        String EMSNum=et_emms.getText().toString();
        Map map=new HashMap();
        map.put("KeyId",siteBean.KeyId);
        map.put("Name",Name);
        map.put("PhoneNum",PhoneNum);
        map.put("Area",Area);
        map.put("EMSNum",EMSNum);
        map.put("Address",Address);
        map.put("IsDefault",isdefal);
        MyDataHttp.getInstance().PostAddAddress(map, new OnRequestLinstener<BaseResponseEntity<List<SiteBean>>>() {
            @Override
            public void success(BaseResponseEntity<List<SiteBean>> listBaseResponseEntity) {
                waitDialog.dismiss();
                finish();
            }

            @Override
            public void error(int code, String error) {

            }
        }, HttpUrl.PostEditAddress,0, RequestMethod.POST,new TypeToken<BaseResponseEntity<List<SiteBean>>>(){});
    }
}
