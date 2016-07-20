package com.huaop2p.yqs.module.logon.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.ActionSheetDialog;
import com.huaop2p.yqs.dialog.XinDialog;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.view.ClearEditText;
import com.huaop2p.yqs.utils.IDCardValidate;
import com.huaop2p.yqs.utils.SCcodeUtil;
import com.huaop2p.yqs.utils.ToastUtils;
import com.lidroid.xutils.util.LogUtils;

import java.text.ParseException;

/**
 * Created by zgq on 2016/5/16.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/16 17:04
 * 功能:  身份认证第一页
 */
public class RankActivity extends BaseActivity implements View.OnClickListener {
    private ClearEditText cet_name, cet_code,button;
    private Button btn_tijiao, img_tiaoguo;
    private Intent mIntent;
    private static final String[] type = {"身份证","其他"};
//    private Spinner cet_code_type;
    private ArrayAdapter<String> adapter;
    private String typeCode;
    private TextView cet_code_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.
                SOFT_INPUT_ADJUST_PAN);
        //锁定屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_rank);
        initData();
        linData();

    }


    private void linData() {
        cet_name.addTextChangedListener(new MyTextWatcher());
        cet_code.addTextChangedListener(new MyTextWatcher());
        cet_name.setText("");
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
            String str1 = cet_code.getText().toString();
            String str2 = cet_name.getText().toString();
            if (str1.equals("") || str2.equals("")) {
                btn_tijiao.setBackgroundResource(R.drawable.huise_button);
                btn_tijiao.setEnabled(false);
            }  else {
                btn_tijiao.setBackgroundResource(R.drawable.btn_com_corner);
                btn_tijiao.setEnabled(true);
            }
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        cet_name = (ClearEditText) findViewById(R.id.cet_name);//姓名
        cet_code = (ClearEditText) findViewById(R.id.cet_code);//身份证
        btn_tijiao = (Button) findViewById(R.id.btn_tijiao);//提交按钮
        btn_tijiao.setOnClickListener(this);
        img_tiaoguo = (Button) findViewById(R.id.img_tiaoguo);//跳过按钮
        img_tiaoguo.setOnClickListener(this);
//        cet_code_type = (Spinner) findViewById(R.id.cet_code_type);
        cet_code_type = (TextView) findViewById(R.id.cet_code_type);
        cet_code_type.setOnClickListener(this);

    }

    String name;
    String code;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tijiao:
                if (typeCode==null){
                    typeCode="身份证";
                }
                name = cet_name.getText().toString();
                code = cet_code.getText().toString();
                if (typeCode.equals("身份证")){
                    try {
                        String yoas=IDCardValidate.IDCardValidate(code);
                        if (yoas.toString().equals("")){
                            ToastUtils.show(getApplicationContext(),"身份证正确");
                        }else {
                            ToastUtils.show(getApplicationContext(),yoas);
                        }
                        if (!yoas.toString().equals("")){
                            return;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }

                XinDialog.Builder builder = new XinDialog.Builder(RankActivity.this);
                builder.setMessage(name);
                builder.setMessage2(typeCode);
                builder.setMessage3(code);
                builder.setMessage4("姓名:");
                builder.setMessage5("证件类型:");
                builder.setMessage6("证件号码:");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mIntent = new Intent(getApplicationContext(), RankTowActivity.class);
                        mIntent.putExtra(RankTowActivity.CET_NAME,name);
                        mIntent.putExtra(RankTowActivity.CET_CODE,code);
                        mIntent.putExtra(RankTowActivity.GET_TYPE,typeCode);
                        startActivity(mIntent);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create(true).show();

                break;
            case R.id.img_tiaoguo:
                finish();
                break;
            case R.id.cet_code_type:

                new ActionSheetDialog(RankActivity.this).builder()
                        .setCancelable(false).setCanceledOnTouchOutside(false)
                        .addSheetItem("身份证", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        cet_code_type.setText("身份证");
                                        typeCode="身份证";
                                    }
                                })
                        .addSheetItem("其他", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        cet_code_type.setText("其他");
                                        typeCode="其他";

                                    }
                                }).show();
                break;

        }

    }



}
