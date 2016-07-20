package com.huaop2p.yqs.module.logon.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.WaitDialog;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.logon.entity.AeestBean;
import com.huaop2p.yqs.module.logon.model.MyFinancialWeb;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgq on 2016/6/15.
 * 作者:  zhu  guo qing
 * 时间:  2016/6/15 17:04
 * 功能:  已认证页面
 */
public class AeestActivity extends BaseActivity{
    private TextView txt_name,txt_qian,txt_hou,txt_pap,txt_codehou,txt_rank,txt_rankhou;
    private ImageView img_rankhand;
    private LinearLayout ll_rankBrang;
    private WaitDialog waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aeest);
        SetActivityTitle("身份认证");
        initData();
        httpData();
    }

    /**
     * 网络加载
     *
     */
    private void httpData() {
        waitDialog=new WaitDialog(AeestActivity.this);
        waitDialog.show();
        Map map=new HashMap();
        MyFinancialWeb.getInstance().GetGAUserInfo(map, new OnRequestLinstener<BaseResponseEntity<List<AeestBean>>>() {
            @Override
            public void success(BaseResponseEntity<List<AeestBean>> listBaseResponseEntity) {
                if (listBaseResponseEntity.ReturnStatus.equals("0")){
                    AeestBean aeestBean=listBaseResponseEntity.ReturnMessage.get(0);
                    txt_name.setText(aeestBean.cust_nm);//mobile_no
                    String qian=aeestBean.mobile_no;
                    txt_qian.setText(qian.substring(0,3));
                    txt_hou.setText(qian.substring(qian.length()-4,qian.length()));
                    String hou=aeestBean.certif_id;
                    txt_codehou.setText(hou.substring(hou.length()-4,hou.length()));
                    String rank=aeestBean.bank_nm;
                    txt_rank.setText(rank.substring(0,6).trim());
                    String rankId=aeestBean.parent_bank_id;
                    String ranHou=aeestBean.capAcntNo;
                    txt_rankhou.setText(ranHou.substring(ranHou.length()-4,ranHou.length()));
                    switch (rankId){
                        case "0102":
                            ll_rankBrang.setBackgroundResource(R.drawable.gongshang);
                            break;
                        case "0103":
                            ll_rankBrang.setBackgroundResource(R.drawable.nongye);
                            break;
                        case "0104":
                            ll_rankBrang.setBackgroundResource(R.drawable.zhonghang);
                            break;
                        case "0105":
                            ll_rankBrang.setBackgroundResource(R.drawable.jianhang);
                            break;
                        case "0308":
                            ll_rankBrang.setBackgroundResource(R.drawable.zhaoshang);
                            break;
                        case "0309":
                            ll_rankBrang.setBackgroundResource(R.drawable.xingye);
                            break;
                        case "0305":
                            ll_rankBrang.setBackgroundResource(R.drawable.minsheng);
                            break;
                        case "0304":
                            ll_rankBrang.setBackgroundResource(R.drawable.huaxia);
                            break;
                        case "0403":
                            ll_rankBrang.setBackgroundResource(R.drawable.youzheng);
                            break;
                        case "0303":
                            ll_rankBrang.setBackgroundResource(R.drawable.guangda);
                            break;
                        case "0302":
                            ll_rankBrang.setBackgroundResource(R.drawable.zhongxin);
                            break;
                        case "0306":
                            ll_rankBrang.setBackgroundResource(R.drawable.guangfa);
                            break;
                        case "0307":
                            ll_rankBrang.setBackgroundResource(R.drawable.pingan);
                            break;
                        case "0310":
                            ll_rankBrang.setBackgroundResource(R.drawable.pufa);
                            break;
                        default:
                            break;
                    }
                    waitDialog.dismiss();
                }

            }

            @Override
            public void error(int code, String error) {

            }
        },HttpUrl.GetGAUserInfo, 0, RequestMethod.GET, new TypeToken<BaseResponseEntity<List<AeestBean>>>() {
        });

    }

    /**
     * 数据初始化
     * txt_name,txt_qian,txt_hou,txt_pap,txt_codehou,txt_rank,txt_rankhou
     */
    private void initData() {
        txt_name= (TextView) findViewById(R.id.txt_name);
        txt_qian= (TextView) findViewById(R.id.txt_qian);
        txt_hou= (TextView) findViewById(R.id.txt_hou);
        txt_pap= (TextView) findViewById(R.id.txt_pap);
        txt_codehou= (TextView) findViewById(R.id.txt_codehou);
        txt_rank= (TextView) findViewById(R.id.txt_rank);
        txt_rankhou= (TextView) findViewById(R.id.txt_rankhou);
        ll_rankBrang= (LinearLayout) findViewById(R.id.ll_rankBrang);
    }
}
