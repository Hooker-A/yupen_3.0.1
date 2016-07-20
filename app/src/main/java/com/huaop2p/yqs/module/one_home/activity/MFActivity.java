package com.huaop2p.yqs.module.one_home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.ScreenListener;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.model.BaseModel;
import com.huaop2p.yqs.module.four_set.view.SettingsFragment;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.logon.activity.AgreementActivity;
import com.huaop2p.yqs.module.logon.entity.LoginedBean;
import com.huaop2p.yqs.module.logon.model.MyFinancialWeb;
import com.huaop2p.yqs.module.one_home.entity.AgreeMentVersionBean;
import com.huaop2p.yqs.module.one_home.view.PreViewFragment;
import com.huaop2p.yqs.module.three_mine.activity.WebActivity;
import com.huaop2p.yqs.module.three_mine.db.landDivideServeice;
import com.huaop2p.yqs.module.three_mine.view.fragment.CusWealthFragment;
import com.huaop2p.yqs.module.two_wealth.view.fragment.FragmentCentre;
import com.huaop2p.yqs.module.two_wealth.view.fragment.WealthModelFragment;
import com.huaop2p.yqs.utils.DigestUtils;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.utils.auto.SoapUtils;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class MFActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    public static boolean gotoCusWealthFragment = false;


    @ViewInject(R.id.rg_navigation)
    private RadioGroup rg_navigation;
    @ViewInject(R.id.rb_1)
    private RadioButton rb_1;
    @ViewInject(R.id.rb_2)
    private RadioButton rb_2;
    @ViewInject(R.id.rb_3)
    private RadioButton rb_3;
    @ViewInject(R.id.rb_4)
    private RadioButton rb_4;
    private FragmentManager fm;
    @ViewInject(R.id.fl_Containerr)
    private FrameLayout frameLayout;
    AgreeMentVersionBean agreeMentVersionBean;


    /**
     * 首页
     */
    private PreViewFragment homeFragment;
    /**
     * 财富中心
     */
    private FragmentCentre marketFragment;
    /**
     * 我的财富
     */

    private CusWealthFragment cusWealthFragment;
    /**
     * 更多
     */
    private SettingsFragment settingsFragment;


    private ScreenListener screenListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().registerSticky(this);
        setContentView(R.layout.activity_mf);
        Intent i = new Intent(this, landDivideServeice.class);
        startService(i);
        init();

        initLogin();

    }

    private void httpData() {
        final Map map = new HashMap();
        MyFinancialWeb.getInstance().getNewAgreement(map, new OnRequestLinstener<BaseResponseEntity<AgreeMentVersionBean>>() {
            @Override
            public void success(BaseResponseEntity<AgreeMentVersionBean> baseResponseEntity) {
                agreeMentVersionBean = baseResponseEntity.ReturnMessage;
//            LogUtils.e(baseResponseEntity.ReturnMessage.AgreeMentVersion.toString()+"--------------");
                if (baseResponseEntity.ReturnMessage != null) {

                    Intent intent = new Intent(MFActivity.this, AgreementActivity.class);
                    intent.putExtra(AgreementActivity.STRING, agreeMentVersionBean);
                    startActivity(intent);

                }

            }

            @Override
            public void error(int code, String error) {

            }
        }, HttpUrl.GetNewAgreement, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity<AgreeMentVersionBean>>() {
        });
    }

    //把本地的登录状态传给服务器
    private void initLogin() {

        /**
         * 登录接口
         * {"AppointEntity":{"LoginName":"账号","LoginPass":"密码"}}
         */

        if (ShareLocalUser.getInstance(this).getLoginState()) {
            Map map = new HashMap();
            Map map1 = new HashMap();
            map1.put("LoginName", ShareLocalUser.getInstance(this).getLoginUser().LoginName);
            map1.put("LoginPass", ShareLocalUser.getInstance(this).getLoginPass());
            map.put("AppointEntity", map1);
            autoLogin(map);
        }

    }

    //二维码扫描处理结果的回传，
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra("codedContent");
                try {
                    content = DigestUtils.decrypt(content, "k%g*p!j#", true);
                    LogUtils.e(content);
                    final Map<String, Object> map = GsonUtils.getGson().fromJson(content, new TypeToken<Map<String, Object>>() {
                    }.getType());
                    BaseModel<Object> model = new BaseModel<>();
                    model.loadDetailById(map, new OnRequestLinstener<BaseResponseEntity<Object>>() {

                        @Override
                        public void success(BaseResponseEntity<Object> mapBaseResponseEntity) {
                            String type = (String) map.get("Type");
                            if (type.equals("BindCusManager")) {
//                                ToastUtils.show(MFActivity.this, "绑定客户经理成功！");

                                Intent intent = new Intent(MFActivity.this, CustomerActivity.class);
                                intent.putExtra("keyid", map.get("KeyId").toString());
                                startActivity(intent);
                                return;
                            }
                            Object o = mapBaseResponseEntity.ReturnMessage;
                            if (o instanceof String) {
                                ToastUtils.show(MFActivity.this, "没有扫出什么~");
                                return;
                            } else {
                                Map<String, Object> activity = (Map<String, Object>) mapBaseResponseEntity.ReturnMessage;
                                Intent intent = new Intent(MFActivity.this, WebActivity.class);
                                intent.putExtra("title", activity.get("Title").toString());
                                intent.putExtra("url", activity.get("ActivityUrl").toString());
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void error(int code, String error) {
                            ToastUtils.show(MFActivity.this, error);
                        }
                    }, HttpUrl.Scan, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity<Object>>() {
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.show(this, "该二维码不属于余盆，" + content);
                }
            }
        }
    }

    //自动登录
    private void autoLogin(final Map map) {

//        LogUtils.e(ShareLocalUser.getInstance(this).getLoginUser().LoginName);
//        LogUtils.e(ShareLocalUser.getInstance(this).getLoginUser().LoginPass);
//        LogUtils.e(ShareLocalUser.getInstance(this).getLoginUser().PhoneNum);
//        LogUtils.e(ShareLocalUser.getInstance(this).getLoginPass());
        MyFinancialWeb.getInstance().logo(map, new OnRequestLinstener<BaseResponseEntity<LoginedBean>>() {
            @Override
            public void success(BaseResponseEntity<LoginedBean> o) {
                if (o != null && o.ReturnStatus.equals("0")) {
                    AppApplication.isLogin = true;
                    AppApplication.user = o.ReturnMessage;
                    EventBus.getDefault().post(o.ReturnMessage);
                }
            }

            @Override
            public void error(int errorCode, String str) {
                rg_navigation.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        autoLogin(map);
                    }
                }, 5000);
            }
        }, HttpUrl.Logins, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity<LoginedBean>>() {
        });
    }

    private void init() {
        fm = getSupportFragmentManager();
        rg_navigation.setOnCheckedChangeListener(this);
        rb_1.setChecked(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction ft = fm.beginTransaction();
        switch (checkedId) {
            case R.id.rb_1:
                /**
                 * 首页
                 */
                if (rb_1.isChecked()) {//如果被选中
                    if (homeFragment == null) {
                        homeFragment = new PreViewFragment();
                        ft.add(R.id.fl_Containerr, homeFragment);
                        hideFragment(ft, marketFragment, settingsFragment, cusWealthFragment);
                    } else {
                        hideFragment(ft, marketFragment, settingsFragment, cusWealthFragment);
                        ft.show(homeFragment);
                    }
                    ft.commitAllowingStateLoss();
                }
                break;
            case R.id.rb_2:
                /**
                 * 财富中心
                 */
                if (rb_2.isChecked()) {
                    if (marketFragment == null) {
                        marketFragment = new FragmentCentre();
                        ft.add(R.id.fl_Containerr, marketFragment, "FragmentCentre");
                        hideFragment(ft, homeFragment, settingsFragment, cusWealthFragment);
                    } else {
                        hideFragment(ft, homeFragment, settingsFragment, cusWealthFragment);
                        ft.show(marketFragment);
                    }
                    ft.commit();
                }
                break;
            case R.id.rb_3:
                /**
                 * 我的财富
                 */
                if (rb_3.isChecked()) {
                    if (cusWealthFragment == null) {
                        cusWealthFragment = new CusWealthFragment();
                        ft.add(R.id.fl_Containerr, cusWealthFragment);
                        hideFragment(ft, homeFragment, marketFragment, settingsFragment);
                    } else {
                        hideFragment(ft, homeFragment, marketFragment, settingsFragment);
                        ft.show(cusWealthFragment);
                    }
                    ft.commit();
                }
                break;
            case R.id.rb_4:
                /**
                 * 更多
                 */
                if (rb_4.isChecked()) {
                    if (settingsFragment == null) {
                        settingsFragment = new SettingsFragment();
                        ft.add(R.id.fl_Containerr, settingsFragment);
                        hideFragment(ft, homeFragment, marketFragment, cusWealthFragment);
                    } else {
                        hideFragment(ft, homeFragment, marketFragment, cusWealthFragment);
                        ft.show(settingsFragment);
                    }
                    ft.commit();
                }
                break;
            default:
                break;
        }
    }

    private void hideFragment(FragmentTransaction ft, Fragment fg1, Fragment fg2, Fragment fg3) {
        if (fg1 != null && fg1.isVisible()) {
            ft.hide(fg1);
        }
        if (fg2 != null && fg2.isVisible()) {
            ft.hide(fg2);
        }
        if (fg3 != null && fg3.isVisible()) {
            ft.hide(fg3);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (gotoCusWealthFragment) {
            rb_3.setChecked(true);
            FragmentTransaction ft = fm.beginTransaction();
            if (cusWealthFragment == null) {

                cusWealthFragment = new CusWealthFragment();
                ft.add(R.id.fl_Containerr, cusWealthFragment);
            } else {
                hideFragment(ft, homeFragment, marketFragment, settingsFragment);
                ft.show(cusWealthFragment);
            }
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentCentre fragmentCentre = (FragmentCentre) getSupportFragmentManager().findFragmentByTag("FragmentCentre");
        if (fragmentCentre == null) {
            super.onBackPressed();
            return;
        }
        WealthModelFragment fragment = (WealthModelFragment) fragmentCentre.adapter.getItem(3);
        if (fragment.isHidden() || !fragment.isBack()) {
            super.onBackPressed();
        }
    }

    public void OnInMarket() {
        rb_2.setChecked(true);
    }

    public void OnInMarket(String id) {
        rb_2.setChecked(true);
//        marketFragment.onItemPosition(id);
    }

    BaseResponseEntity agreeMentBean;
    Type agreeMentType = new TypeToken<BaseResponseEntity<AgreeMentVersionBean>>() {
    }.getType();


    @Override
    protected void onResume() {
        super.onResume();
        if (AppApplication.isActive) {
            if (ShareLocalUser.getInstance(this).getLoginState())
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        httpData();


                    }
                }).start();
        }
    }


}
