package com.huaop2p.yqs.module.three_mine.activity.dataactivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.ShowDialog;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.utils.AppInentUtils;

/**
 * Created by zgq on 2016/5/9.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/9 16:09
 * 功能:  手机号
 */
public class IphoneActivity extends BaseActivity implements View.OnClickListener{
    private RelativeLayout rl_help_call;
    private String phonenum;
    private TextView txt_iphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iphone);
        SetActivityTitle("手机号");
        initData();
    }

    private void initData() {
        rl_help_call= (RelativeLayout) findViewById(R.id.rl_help_call);
        rl_help_call.setOnClickListener(this);
        txt_iphone= (TextView) findViewById(R.id.txt_iphone);
        txt_iphone.setText(ShareLocalUser.getInstance(this).getLoginUser().PhoneNum);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_help_call:
                phonenum = this.getResources().getString(R.string.str_more_helpr_phone);
                ShowDialog.Builder builder = new ShowDialog.Builder(IphoneActivity.this);
                builder.setTitle("拨打客服电话");
                builder.setImag(R.drawable.cusiphone);
                builder.setMessage("400-838-9595");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppInentUtils.callPhone(IphoneActivity.this, phonenum);
                        Toast toast=Toast.makeText(getApplicationContext(), "请稍后...", Toast.LENGTH_SHORT);
                        toast .setGravity(Gravity.CENTER, 0, 10);
                        toast.show();
                        dialog.dismiss();
                    }
                });
                builder.createOneBtn(true).show();
                break;
        }
    }
}
