package com.huaop2p.yqs.module.one_home.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.entity.BusConstants;
import com.huaop2p.yqs.module.one_home.adapter.RedBaoAdapter;
import com.huaop2p.yqs.module.one_home.bean.HongbaoItemBean;
import com.huaop2p.yqs.module.one_home.entity.DesEncToDes;
import com.huaop2p.yqs.utils.DateUtils;
import com.huaop2p.yqs.utils.DigestUtils;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.utils.auto.SoapUtils;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.huaop2p.yqs.utils.net.NetUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by maoxiaofei on 2016/6/28.
 */
public class HongBaoActivity extends BaseActivity {
    /**
     * 传递给红包的id
     */
    public static final String HONG_BAO_ID = "hong_bao_id";
    /**
     * 红包的金额
     */
    public static final String HONG_BAO_MONEY = "hong_bao_money";
    /**
     * 允许使用红包金额
     */
    public static final String HONG_BAO_AMOUNT = "hong_bao_amount";
    /**
     * 是否来使用红包
     */
    public static boolean isUseRedPackage = false;
    RedBaoAdapter mAdapter;
    List<HongbaoItemBean> mList = new ArrayList<HongbaoItemBean>();
    String mAmount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hongbao_activity2);
        mTxtTitle.setText("红包记录");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            mAmount = bundle.getString(HONG_BAO_AMOUNT);
        getDateWeb();
//        getList();

    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mAdapter = new RedBaoAdapter(HongBaoActivity.this, mList);
            mListView.setAdapter(mAdapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    HongbaoItemBean bean = (HongbaoItemBean) mAdapter.getItem(position);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String Xdate = sdf.format(new java.util.Date());
                    String Edate = bean.RedEndTime;
                    Date dateEndTime = DateUtils.Parse2Date(Edate, DateUtils.yyyyMMdd);
                    Date dataStar = DateUtils.Parse2Date(Xdate, DateUtils.yyyyMMdd);
                    if (dateEndTime.getTime() < dataStar.getTime()) {
                        bean.IsUse=2;
                        ToastUtils.show(getApplicationContext(), "该红包已过期!", Toast.LENGTH_SHORT);
                    }else {
                        switch (bean.IsUse) {
                            case 0:
                                if (isUseRedPackage) {   //如果使用红包返回id
                                    if (mAmount != null) {
                                        if (Double.parseDouble(mAmount) >= bean.RedMoney) {
                                            Intent hongBao = new Intent();
                                            hongBao.putExtra(HONG_BAO_ID, bean.KeyId + "");
                                            hongBao.putExtra(HONG_BAO_MONEY, bean.RedMoney);
                                            setResult(RESULT_OK, hongBao);
                                            isUseRedPackage = false;
                                            finish();
                                        } else {
                                            DecimalFormat df = new DecimalFormat(".00");
                                            if (mAmount != null) {
                                                ToastUtils.show(HongBaoActivity.this, "可使用的最大红包金额为" + df.format(Double.parseDouble(mAmount)) + "元");
                                            }
                                        }
                                    }

                                }
                                break;
                            case 1:
                                ToastUtils.show(getApplicationContext(),"该红包已使用!",Toast.LENGTH_SHORT);
                                break;
                            case 2:
                                break;
                            default:
                                break;

                        }
                    }
                }
            });
            return false;
        }
    });

    /**
     * 从网络上获得红包数据
     */
    private Handler getmHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Collections.sort(mList, new Comparator<HongbaoItemBean>() {
                @Override
                public int compare(HongbaoItemBean lhs, HongbaoItemBean rhs) {
                    Date date1 = DateUtils.stringToDate(lhs.RedStartTime);
                    Date date2 = DateUtils.stringToDate(rhs.RedStartTime);
                    if (date1.before(date2)) {
                        return -1;
                    }
                    return 1;
                }
            });
            RedBaoAdapter redBaoAdapter = new RedBaoAdapter(HongBaoActivity.this, mList);
            mListView.setAdapter(redBaoAdapter);
            return false;
        }
    });
    private void getDateWeb() {



        PageBean pageBean = new PageBean();
        pageBean.PageIndex = "1";
        pageBean.PageSize = "100000";

        DesEncToDes des = new DesEncToDes();
        try {
            des.DesEncToDes = DigestUtils.encrypt(GsonUtils.getDateGson(null).toJson(pageBean), BusConstants.despas, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AsyncGetHbList hbList = new AsyncGetHbList();

        hbList.execute(des);

    }

    class PageBean {
        public String PageIndex;
        public String PageSize;
    }

    ProgressDialog progressDialog;

    //加密：{\"PageIndex\":\"当前页\",\"PageSize\":\"数量\"}
    class AsyncGetHbList extends AsyncTask<DesEncToDes, Integer, BaseResponseEntity<List<HongbaoItemBean>>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(HongBaoActivity.this, AlertDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("正在加载请稍后......");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected BaseResponseEntity doInBackground(DesEncToDes... params) {

            String reqBody = GsonUtils.getGson().toJson(params[0]);
            String result = NetUtils.postRequest(HttpUrl.GetMyRedPackage, reqBody);
            LogUtils.e(result + "88888888888888888888888888888888");
            Type type = new TypeToken<BaseResponseEntity<List<HongbaoItemBean>>>() {
            }.getType();
            BaseResponseEntity responseEntity = GsonUtils.getGson().fromJson(result, type);
            LogUtils.e(GsonUtils.getDateGson(null).toJson(responseEntity));
            return responseEntity;
        }

        @Override
        protected void onPostExecute(BaseResponseEntity<List<HongbaoItemBean>> responseEntity) {
            super.onPostExecute(responseEntity);
            if (SoapUtils.isResponseOk(HongBaoActivity.this, responseEntity)) {
                mList = responseEntity.ReturnMessage;
                LogUtils.e("size" + mList.size());
                mHandler.sendEmptyMessage(0);
                getmHandler.sendEmptyMessage(0);
            }
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
                progressDialog=null;
            }
        }
    }

    @ViewInject(R.id.TXT_APP_TITLE)
    private TextView mTxtTitle;

    @ViewInject(R.id.lv_red_package)
    ListView mListView;
}
