package com.huaop2p.yqs.module.three_mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.activity.NoAniActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.four_set.model.MyFinancialWeb;
import com.huaop2p.yqs.module.three_mine.adapter.RedItemAdapter;
import com.huaop2p.yqs.module.three_mine.model.entity.RedItemBean;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maoxiaofei on 2016/7/7.
 */
public class RedGzActivity extends NoAniActivity implements View.OnClickListener{
    public static final String GZKID="kid";
    public static final String TITEL1="titel1";
    public static final String TITEL2="titel2";
    private RedItemAdapter redItemAdapter;
    private String keyid="";
    private String titel1="";
    private String titel2="";
    private Intent intent;
    private ListView lv_itemview;
    private List<RedItemBean> list;
    private ImageView img_colse;
    private TextView txt_win_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redgz);
        intent=getIntent();
        keyid=intent.getStringExtra("kid");
        titel1=intent.getStringExtra("titel1");
        titel2=intent.getStringExtra("titel2");
        initData();
        txt_win_title.setText(titel1+"元"+titel2);
        httpData(keyid);
    }

    private void initData() {
        lv_itemview= (ListView) findViewById(R.id.lv_itemview);
        img_colse= (ImageView) findViewById(R.id.img_colse);
        img_colse.setOnClickListener(this);
        txt_win_title= (TextView) findViewById(R.id.txt_win_title);

    }

    /**
     * {"KeyId":"产品ID"}
     * @param keid
     */
    private void httpData(String keid){
        Map map=new HashMap();
        map.put("KeyId",keid);
        MyFinancialWeb.getInstance().PostSelectRuleById(map, new OnRequestLinstener<BaseResponseEntity<List<RedItemBean>>>() {
            @Override
            public void success(BaseResponseEntity<List<RedItemBean>> listBaseResponseEntity) {
                list=listBaseResponseEntity.ReturnMessage;
                redItemAdapter=new RedItemAdapter(list,getApplicationContext());
                lv_itemview.setAdapter(redItemAdapter);
            }

            @Override
            public void error(int code, String error) {

            }
        }, HttpUrl.PostSelectRuleById,0, RequestMethod.POST,new TypeToken<BaseResponseEntity<List<RedItemBean>>>(){});

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_colse:
                finish();
                break;
        }
    }
}
