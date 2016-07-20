package com.huaop2p.yqs.module.one_home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.one_home.bean.MsgCenterBean;

/**
 * Created by maoxiaofei on 2016/7/1.
 */
public class SecondMsgActivity extends BaseActivity {

    private Intent intent;
    private MsgCenterBean msgCenterBean;
    private TextView tv_msg_time,tv_msg_proname,tv_msg_content,tv_protitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seconemsg);
        SetActivityTitle("消息详情");

        tv_msg_time = (TextView) findViewById(R.id.tv_msg_time);
        tv_msg_proname = (TextView) findViewById(R.id.tv_msg_proname);
        tv_msg_content = (TextView)findViewById(R.id.tv_msg_content);
        tv_protitle = (TextView)findViewById(R.id.tv_protitle);

        intent = getIntent();
        msgCenterBean= (MsgCenterBean)intent.getSerializableExtra("beann");

        tv_protitle.setText("【"+msgCenterBean.getTypeName()+"】");
        tv_msg_time.setText(msgCenterBean.getTime());
        tv_msg_proname.setText(msgCenterBean.getTitle());
        tv_msg_content.setText(msgCenterBean.getContent());


    }
}
