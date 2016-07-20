package com.huaop2p.yqs.module.four_set.activity;



import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.WaitDialog;
import com.huaop2p.yqs.module.base.activity.NoAniActivity;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseRequestEntity;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.entity.BusConstants;
import com.huaop2p.yqs.module.four_set.entity.ReqBaseBean;
import com.huaop2p.yqs.module.four_set.entity.ReqBindCusManagerBean;
import com.huaop2p.yqs.module.four_set.entity.ResCusManagerBea;
import com.huaop2p.yqs.module.four_set.entity.ResCusManagerBean;
import com.huaop2p.yqs.module.four_set.model.MyFinancialWeb;
import com.huaop2p.yqs.module.logon.entity.LoginedBean;
import com.huaop2p.yqs.utils.AppInentUtils;
import com.huaop2p.yqs.utils.DigestUtils;
import com.huaop2p.yqs.utils.ImageUtils;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgq on 2016/7/5.
 * 作者:  zhu  guo qing
 * 时间:  2016/7/5 14:20
 * 功能:获取网络客户经理选择界面
 */
public class ScanedCustomerManager extends NoAniActivity {
    private LinearLayout linear_cus_manager;
    TextView txt_manager_name, txt_manager_phone, txt_manager_mendian, txt_manager_introduction;
    public static final String INTENT_SCAND_RESULT = "INTENT_SCAND_RESULT";
    String managerScane ;//获取Intent 参数
    ImageView img_manager_photo;
    boolean isSysSelect = false;
    public static final String INTENT_IS_SYS_SELECT = "INTENT_IS_SYS_SELECT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_scaned_customer_manager);
        txt_manager_name = (TextView) findViewById(R.id.txt_manager_name);
        txt_manager_phone = (TextView) findViewById(R.id.txt_manager_phone);
        txt_manager_mendian = (TextView) findViewById(R.id.txt_manager_mendian);
        img_manager_photo = (ImageView) findViewById(R.id.img_manager_photo);
        txt_manager_introduction = (TextView) findViewById(R.id.txt_manager_introduction);
        managerScane = getIntent().getStringExtra(INTENT_SCAND_RESULT);
        isSysSelect = getIntent().getBooleanExtra(INTENT_IS_SYS_SELECT, false);
        linear_cus_manager = (LinearLayout) findViewById(R.id.linear_cus_manager);
        linear_cus_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowAnimationStyle});
        int windowAnimatinoStyleResId = activityStyle.getResourceId(0, 0);
        activityStyle.recycle();

        if (isSysSelect) {
            asdyncReqSysSelectManager();
        } else {
            asyncReqCusManager();
        }
    }

    /**
     * 请求客户经理
     */
    void asyncReqCusManager() {
        httpData();
    }

    /**
     * 系统选择客户经理
     */
    void asdyncReqSysSelectManager() {
        BaseRequestEntity reqParam = new BaseRequestEntity();
        reqParam.CustomerName = ShareLocalUser.getInstance(this).getLoginUser().LoginName;
        reqParam.CustomerPass = ShareLocalUser.getInstance(this).getLoginPass();
        reqParam.action = BusConstants.Action_SManager;
        AsyncReqSysManager asyncReq = new AsyncReqSysManager();
        asyncReq.execute(reqParam);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }


    public void btnExit(View view) {
        finish();
    }

    /**
     * 取消
     *
     * @param view
     */
    public void onCancel(View view) {
        finish();
    }

    /**
     * 确定选择项目经理
     *
     * @param view
     */
    public void onConfirm(View view) {
        BandHttp();

        if (managerScane == null || managerScane.length() == 0)
            return;

        ReqBindCusManagerBean reqParam = new ReqBindCusManagerBean();
        reqParam.action = BusConstants.Action_BandCMID;
        reqParam.CustomerName = ShareLocalUser.getInstance(this).getLoginUser().LoginName;
        reqParam.CustomerPass = ShareLocalUser.getInstance(this).getLoginPass();
//
    }

    //系统选择客户经理
    class AsyncReqSysManager extends AsyncTask<BaseRequestEntity, Integer, BaseResponseEntity<ResCusManagerBea.JsonEntity>> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected BaseResponseEntity doInBackground(BaseRequestEntity[] params) {
            BaseResponseEntity reqBody = MyFinancialWeb.getInstance().getSysCusManager(params[0]);
            return reqBody;
        }

        @Override
        protected void onPostExecute(BaseResponseEntity<ResCusManagerBea.JsonEntity> baseResponseEntity) {

            ResCusManagerBea.JsonEntity res = baseResponseEntity.ReturnMessage;
                managerScane = Integer.toString(res.KeyId);
                LoginedBean loginuser = ShareLocalUser.getInstance(ScanedCustomerManager.this).getLoginUser();
                loginuser.CustomerManagerID = managerScane;
                ShareLocalUser.getInstance(ScanedCustomerManager.this).addLoginUser(loginuser);
                if (res.Sex.equals("男")) {
                    txt_manager_name.setText(res.TrueName);
                } else {
                    txt_manager_name.setText(res.TrueName);
                }
//                txt_manager_name.setText(res.TrueName);
                txt_manager_mendian.setText(res.Department);
                txt_manager_phone.setText(res.PhoneNum);
//                txt_manager_introduction.setText(res.Remarks);
                byte[] beforestr = Base64.decode(res.UrlHeadPhoto, Base64.DEFAULT);
                Bitmap biamp = ImageUtils.byteToBitmap(beforestr);
            img_manager_photo.setImageBitmap(biamp);
            }
    }
    /**
     * 获取客户经理
     */
    /**
     * {"KeyIds":"15982246190"}
     */
   private void httpData(){
       Map map=new HashMap();
       map.put("KeyIds",managerScane);
       MyFinancialWeb.getInstance().getCusManager(map, new OnRequestLinstener<BaseResponseEntity<ResCusManagerBean>>() {
           @Override
           public void success(BaseResponseEntity<ResCusManagerBean> resCusManagerBeanBaseResponseEntity) {
               if (resCusManagerBeanBaseResponseEntity == null) {
                ToastUtils.show(ScanedCustomerManager.this, "客户经理不存在", Toast.LENGTH_SHORT);
                finish();
            } else if (resCusManagerBeanBaseResponseEntity.ReturnStatus.equals(BusConstants.Return_Status_0)) {// && response.ReturnReason.equals(BusConstants.Return_Reason_OK)
                ResCusManagerBean res = resCusManagerBeanBaseResponseEntity.ReturnMessage;
                managerScane = Integer.toString(res.KeyId);
                if (res.Sex.equals("男")) {
                    txt_manager_name.setText(res.TrueName);
                } else {
                    txt_manager_name.setText(res.TrueName);
                }
//                txt_manager_name.setText(res.TrueName);
                txt_manager_mendian.setText(res.Department);
                txt_manager_phone.setText(res.PhoneNum);
                txt_manager_introduction.setText(res.Remarks);
                byte[] beforestr = Base64.decode(res.UrlHeadPhoto, Base64.DEFAULT);
                Bitmap biamp = ImageUtils.byteToBitmap(beforestr);
                img_manager_photo.setImageBitmap(biamp);
            } else if (resCusManagerBeanBaseResponseEntity.ReturnStatus.equals(BusConstants.Return_Status_lose_1)) {
//                LoginedBean user = ShareLocalUser.getInstance(ScanedCustomerManager.this).getLoginUser();
//                user.CustomerManagerID = null;
//                ShareLocalUser.getInstance(ScanedCustomerManager.this).addLoginUser(user);
                AppInentUtils.openCusManagerIsLose(ScanedCustomerManager.this, getResources().getString(R.string.str_cus_manager_is_lose));
//                finish();
            } else if (resCusManagerBeanBaseResponseEntity.ReturnStatus.equals(BusConstants.Return_Status_1)) {
                ToastUtils.show(ScanedCustomerManager.this, resCusManagerBeanBaseResponseEntity.ReturnReason, Toast.LENGTH_SHORT);
            }else if(resCusManagerBeanBaseResponseEntity.ReturnStatus.equals(BusConstants.Return_Status_2)){
                AppInentUtils.openCusManagerIsLose(ScanedCustomerManager.this,resCusManagerBeanBaseResponseEntity.ReturnReason);

            }

        }

           @Override
           public void error(int code, String error) {

           }
       }, HttpUrl.PostSelManager,0, RequestMethod.POST,new TypeToken<BaseResponseEntity<ResCusManagerBean>>(){});
   }


    /**
     * 绑定客户经理
     *
     * {"AppointEntity":{"CustomerManagerID":"客户经理ID"}}
     */
    private void BandHttp(){
        Map Smap=new HashMap();
        Smap.put("CustomerManagerID", managerScane);
        Map Dmap=new HashMap();
        Dmap.put("AppointEntity",Smap);
        MyFinancialWeb.getInstance().bindCusManager(Dmap, new OnRequestLinstener<BaseResponseEntity>() {
            @Override
            public void success(BaseResponseEntity baseResponseEntity) {
                if (baseResponseEntity.ReturnStatus.toString().equals("0")){
                    LoginedBean lgoine = ShareLocalUser.getInstance(ScanedCustomerManager.this).getLoginUser();
                    lgoine.CustomerManagerID = managerScane;//登录添加客户经理
                    LogUtils.e("------------------"+managerScane);
                    ShareLocalUser.getInstance(ScanedCustomerManager.this).addLoginUser(lgoine);
                    AppInentUtils.DialogToast(ScanedCustomerManager.this, "您已选择客户经理");
                    finish();
                }

            }

            @Override
            public void error(int code, String error) {

            }
        },HttpUrl.BandCMID,0,RequestMethod.POST,new TypeToken<BaseResponseEntity>(){});
    }
//
}
