package com.huaop2p.yqs.module.four_set.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.four_set.adapter.MessageAdapter;
import com.huaop2p.yqs.module.four_set.entity.ResPushMsg;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgq on 2016/5/19.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/19 14:58
 * 功能:  消息推送
 */
public class MessageActivity extends BaseActivity implements View.OnClickListener  {

    public static final String INTENT_IS_A_MSG = "INTENT_IS_A_MSG";

    @ViewInject(R.id.lv_message)
    private ListView lv_message;

    @ViewInject(R.id.imgBack)
    private ImageButton back;
    @ViewInject(R.id.TXT_APP_TITLE)
    private TextView title;

    private MessageAdapter adapter;

    private boolean isANotice = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);
        title.setText(getString(R.string.message_activity));
        back.setOnClickListener(this);
        isANotice = getIntent().getBooleanExtra(INTENT_IS_A_MSG, false);
        Log.i("tuisong", isANotice + "");

        adapter = new MessageAdapter(this);
        getMessages();
        lv_message.setAdapter(adapter);
//        lv_message.setOnItemClickListener(this);

    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        ResPushMsg msg = ((MessageAdapter) parent.getAdapter()).getItem(position);
//        Intent intent = new Intent(this, NotifyActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("ResPushMsg", msg);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }

    /**
     * 获取推送消息列表
     */
    private void getMessages() {
        List<ResPushMsg> list = ShareLocalUser.getInstance(this).getPushMsges();

        if (isANotice) {
            adapter.setList(getLastMsg(list));
        } else {
            adapter.setList(list);
        }
    }

    /**
     * 推送
     *
     * @param data
     */
    private List<ResPushMsg> getLastMsg(List<ResPushMsg> data) {
        if (data == null)
            return null;
        List<ResPushMsg> res = new ArrayList<ResPushMsg>();
        res.add(data.get(data.size() - 1));
        return res;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            default:
                break;
        }

    }
}
