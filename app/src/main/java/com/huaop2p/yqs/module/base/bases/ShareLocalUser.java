package com.huaop2p.yqs.module.base.bases;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.four_set.entity.ResPushMsg;
import com.huaop2p.yqs.module.logon.activity.LogonActivity;
import com.huaop2p.yqs.module.logon.entity.FuiouChinaAreaBean;
import com.huaop2p.yqs.module.logon.entity.LoginedBean;
import com.huaop2p.yqs.module.three_mine.model.entity.SiteBean;
import com.huaop2p.yqs.utils.net.GsonUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zgq on 2016/4/29.
 * 作者:  zhu  guo qing
 * 时间:  2016/4/29 15:02
 * 功能:  是否登录
 */
public class ShareLocalUser {
    public static final String SHARE_KEY_PUSH_CLIENT_ID = "SHARE_KEY_PUSH_CLIENT_ID";//存储客户端标记

    private static final String SHARE_KEY_COOKIE = "SHARE_KEY_COOKIE";
    private static final String SHARE_KEY_COOKIE2 = "SHARE_KEY_COOKIE2";
    private static final String SHARE_KEY_IS_FIRST_USEING ="SHARE_KEY_IS_FIRST_USEING";//第一次使用 引导页使用
    private static final String SHARE_USER_ICON = "USER_ICON";//用户头像
    private static final String SHARE_KEY_OPEN_GS_PASS = "SHARE_KEY_OPEN_GS_PASS";//是否打开手势密码;
    private static final String SHARE_KEY_GS_PASS ="SHARE_KEY_GS_PASS";//手势密码
    public static final String SHARE_KEY_LOCAL_PUSH_MSG = "SHARE_KEY_LOCAL_PUSH_MSG";//存储收到的消息
    public static final String SHARE_FUIOU_AREA = "SHARE_FUIOU_AREA";//富有地区

    public static ShareLocalUser thisShare;
    private Context context;
    public static final String SHARE_LOCAL_USER = "SHARE_LOCAL_USER";
    private SharedPreferences preferences;
    public static final String SHARE_KEY_LOGINED_USER = "SHARE_KEY_LOGIN_USER";//登录用户信息
    public static final String SHARE_KEY_LOGINED_SITE = "SHARE_KEY_LOGIN_Site";//登录用户信息
    public static final String SHARE_KEY_LOGIN_PASS = "SHARE_KEY_LOGIN_PASS";//获取本地登录密码
    public static final String SHARE_KEY_LOGIN_STATE = "SHARE_KEY_LOGIN_STATE";//是否登录


    public static ShareLocalUser getInstance(Context context) {
        if (thisShare == null) {
            thisShare = new ShareLocalUser(context);
        }
        return thisShare;
    }


    private ShareLocalUser(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(SHARE_LOCAL_USER, context.MODE_PRIVATE);

    }

    /**
     * 是否已经登陆，并根据参数提示是否弹出登录页面
     *
     * @param isOpenTologin
     * @return
     */
    public boolean isLogined(boolean isOpenTologin) {
        LoginedBean loginuser = getLoginUser();
        String loginpass = getLoginPass();
        boolean islogined = getLoginState();
        if (islogined == false || loginpass == null || loginpass == null) {
            if (isOpenTologin) {
                Intent startloginRegister = new Intent(context, LogonActivity.class);
                context.startActivity(startloginRegister);
            }
            return false;
        }
        return true;
    }
    /**
     * 设置富有地区
     *
     * @param chinaarea
     * @return
     */
    public boolean addFuiouArea(List<FuiouChinaAreaBean> chinaarea) {
        try {
            if (chinaarea == null)
                return false;

            String jstr = GsonUtils.getDateGson(null).toJson(chinaarea);
            preferences.edit().putString(SHARE_FUIOU_AREA, jstr).commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 获取富友 地区 d
     *
     * @return
     */
    public List<FuiouChinaAreaBean> getFuiouArea() {
        try {
            String liststr = preferences.getString(SHARE_FUIOU_AREA, null);
            if (liststr == null)
                return null;
            Type type = new TypeToken<List<FuiouChinaAreaBean>>() {
            }.getType();

            List<FuiouChinaAreaBean> res = GsonUtils.getDateGson(null).fromJson(liststr, type);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 添加 登录用户
     *
     * @param user
     * @return
     */
    public boolean addLoginUser(LoginedBean user) {
        try {
            if (user == null)
                return false;
            String jstr = GsonUtils.getDateGson(null).toJson(user);
            preferences.edit().putString(SHARE_KEY_LOGINED_USER, jstr).commit();
            addLoginState(true);//已登录
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 获取收到的消息集合
     *
     * @return
     */
    public List<ResPushMsg> getPushMsges() {
        String msgstr = PreferencesUtils.getString(context, ShareLocalUser.SHARE_KEY_LOCAL_PUSH_MSG);
//        String msgstr = preferences.getString(SHARE_KEY_LOCAL_PUSH_MSG, null);
        if (msgstr == null)
            return null;
        Type type = new TypeToken<List<ResPushMsg>>() {
        }.getType();

        return GsonUtils.getDateGson(null).fromJson(msgstr, type);
    }
    /**
     * 添加 登录状态
     *
     * @param isLogin
     * @return
     */
    public boolean addLoginState(boolean isLogin) {
        try {
            preferences.edit().putBoolean(SHARE_KEY_LOGIN_STATE, isLogin).commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 添加本地密码
     *
     * @param pass
     * @return
     */
    public boolean addLoginPass(String pass) {
        try {
            preferences.edit().putString(SHARE_KEY_LOGIN_PASS, pass).commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 储存用户头像
     * @param base64
     */
    public void addUserIcon(String base64){
        preferences.edit().putString(SHARE_USER_ICON,base64);
    }

    public String getUserIcon(){
        return preferences.getString(SHARE_USER_ICON, "");
    }

    /**
     * 获取用户登录信息
     *
     * @return
     */
    public LoginedBean getLoginUser() {
        try {
            String liststr = preferences.getString(SHARE_KEY_LOGINED_USER, null);
            if (liststr == null)
                return null;
            Type type = new TypeToken<LoginedBean>() {
            }.getType();

            LoginedBean res = GsonUtils.getDateGson(null).fromJson(liststr, type);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
    public boolean addsite(SiteBean bean) {
        try {
            if (bean == null)
                return false;
            String jstr = GsonUtils.getDateGson(null).toJson(bean);
            preferences.edit().putString(SHARE_KEY_LOGINED_SITE, jstr).commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public SiteBean getSite() {
        try {
            String liststr = preferences.getString(SHARE_KEY_LOGINED_SITE, null);
            if (liststr == null)
                return null;
            Type type = new TypeToken<List<SiteBean>>() {
            }.getType();

            SiteBean res = GsonUtils.getDateGson(null).fromJson(liststr, type);
            return res;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取本地密码
     *
     * @return
     */
    public String getLoginPass() {
        return preferences.getString(SHARE_KEY_LOGIN_PASS, null);
    }

    /**
     * 获取登录状态
     *
     * @return
     */
    public boolean getLoginState() {
        try {
            return preferences.getBoolean(SHARE_KEY_LOGIN_STATE, false);
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 获取 软件 第一次使用
     *
     * @return
     */
    public boolean getIsFirst() {
        return preferences.getBoolean(SHARE_KEY_IS_FIRST_USEING, false);
    }
    /**
     * 修改 是否是第一次使用状态
     *
     * @param isfist
     * @return
     */
    public boolean addIsFirst(Boolean isfist) {
        try {
            preferences.edit().putBoolean(SHARE_KEY_IS_FIRST_USEING, isfist).commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 添加cookie yp
     *
     * @param cookiestr
     * @return
     */
    public boolean addCookie2(String cookiestr) {
        try {
            preferences.edit().putString(SHARE_KEY_COOKIE2, cookiestr).commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 添加cookie
     *
     * @param cookiestr
     * @return
     */
    public boolean addCookie(String cookiestr) {
        try {
            preferences.edit().putString(SHARE_KEY_COOKIE, cookiestr).commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 获取cookie yp
     *
     * @return
     */
    public String getCookie2() {
        return preferences.getString(SHARE_KEY_COOKIE2, "");
    }
    /**
     * 获取是否打开手势密码
     *
     * @return
     */
    public boolean getIsOpenGsPass() {
        return preferences.getBoolean(SHARE_KEY_OPEN_GS_PASS, false);
    }
    /**
     * 获取手势密码
     *
     * @return
     */
    public String getGsPass() {
        return preferences.getString(SHARE_KEY_GS_PASS, null);
    }
    /**
     * 添加手势密码
     *
     * @param gspass
     * @return
     */
    public boolean addGsPass(String gspass) {
        try {
            preferences.edit().putString(SHARE_KEY_GS_PASS, gspass).commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 设置是否打开手势密码
     *
     * @param isopen
     * @return
     */
    public boolean addIsOpenGsPass(boolean isopen) {
        try {
            preferences.edit().putBoolean(SHARE_KEY_OPEN_GS_PASS, isopen).commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
