package com.huaop2p.yqs.module.logon.model;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.Bus.BusConstant;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.entity.BusConstants;
import com.huaop2p.yqs.module.logon.entity.AeestBean;
import com.huaop2p.yqs.module.logon.entity.FuiouChinaAreaBean;
import com.huaop2p.yqs.module.logon.entity.FuiuoBankBean;
import com.huaop2p.yqs.module.logon.entity.LoginedBean;
import com.huaop2p.yqs.module.base.model.BaseModel;
import com.huaop2p.yqs.module.logon.entity.NewAgreeMentIdBean;
import com.huaop2p.yqs.module.one_home.bean.YaoNumBean;
import com.huaop2p.yqs.module.one_home.bean.YaoYiYaoBean;
import com.huaop2p.yqs.module.one_home.entity.AgreeMentVersionBean;
import com.huaop2p.yqs.utils.net.CallServer;
import com.huaop2p.yqs.utils.net.FastJsonRequest;
import com.huaop2p.yqs.utils.DigestUtils;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.Response;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by zgq on 2016/4/22.
 * 作者:  zhu  guo qing
 * 时间:  2016/4/22 14:22
 * 功能:  activity的网络请求
 */
public class MyFinancialWeb extends BaseModel {

    private static class SingletonHolder {
        private static final MyFinancialWeb INSTANCE = new MyFinancialWeb();
    }

    private MyFinancialWeb() {
    }

    public static MyFinancialWeb getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 登录接口
     *
     * @param map           map集合
     * @param basePresenter 实体类
     * @param url           接口地址
     * @param what          0
     * @param method        请求方式
     * @param typeToken     类型转换
     */
    public void logo(Map map, final OnRequestLinstener<BaseResponseEntity<LoginedBean>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity<LoginedBean>> request = new FastJsonRequest<BaseResponseEntity<LoginedBean>>(
                url, method, typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);
        try {
            reqBody = DigestUtils.encrypt(reqBody, BusConstant.despas, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody = getNewString(reqBody);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<LoginedBean>>() {
            @Override
            public void onStart(int what) {

            }

            @Override

            public void onSucceed(int what, Response<BaseResponseEntity<LoginedBean>> response) {
                BaseResponseEntity<LoginedBean> result = response.get();
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(responseCode + "/////////////////////////");
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }
    /**
     * 获取协议
     *
     * @return
     */
    public void getNewAgreement(Map map, final OnRequestLinstener<BaseResponseEntity<AgreeMentVersionBean>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity<AgreeMentVersionBean>> request = new FastJsonRequest<BaseResponseEntity<AgreeMentVersionBean>>(url, method, typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);

        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<AgreeMentVersionBean>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<AgreeMentVersionBean>> response) {
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

//    public String getNewAgreement() {
//        return NetUtils.postRequest(HttpUrl.GetNewAgreement, "");
//    }

    class DesEncTodesBean {
        public String DesEncToDes;
    }
    public void setNewAgreement(Map map, final OnRequestLinstener<BaseResponseEntity<AgreeMentVersionBean>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity<AgreeMentVersionBean>> request = new FastJsonRequest<BaseResponseEntity<AgreeMentVersionBean>>(
                url, method, typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);
        try {
            reqBody = DigestUtils.encrypt(reqBody, BusConstant.despas, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody = getNewString(reqBody);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<AgreeMentVersionBean>>() {
            @Override
            public void onStart(int what) {

            }

            @Override

            public void onSucceed(int what, Response<BaseResponseEntity<AgreeMentVersionBean>> response) {
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(responseCode + "/////////////////////////");
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    /**
     * 设置协议
     *
     * @return
     */
//    public String setNewAgreement(NewAgreeMentIdBean.NewAgreeMent mentIdBean) {
//        //{"DesEncToDes":"加密{"","AppointEntity":{NewAgreeMentId":"1"}}}
//        DesEncTodesBean desEncTodesBean = new DesEncTodesBean();
//        NewAgreeMentIdBean newAgreeMentIdBean = new NewAgreeMentIdBean();
//        newAgreeMentIdBean.AppointEntity = mentIdBean;
//        try {
//            String encrypt = DigestUtils.encrypt(GsonUtils.getDateGson(null).toJson(newAgreeMentIdBean), BusConstants.despas, true);
//            if (encrypt != null) {
//                desEncTodesBean.DesEncToDes = encrypt;
//            }
//            String req = GsonUtils.getDateGson(null).toJson(desEncTodesBean);
//            return NetUtils.postRequest(HttpUrl.SetNewAgreement, req);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * 验证码发送
     *
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
    public void SendCode(Map map, final OnRequestLinstener<BaseResponseEntity> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity> request = new FastJsonRequest<BaseResponseEntity>(url, method, typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);
        try {
            reqBody = DigestUtils.encrypt(reqBody, BusConstant.despas, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody = getNewString(reqBody);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity> response) {
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 注册接口
     *
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
    public void PostRegister(Map map, final OnRequestLinstener<BaseResponseEntity<LoginedBean>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity<LoginedBean>> request = new FastJsonRequest<BaseResponseEntity<LoginedBean>>(url, method, typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);
        try {
            reqBody = DigestUtils.encrypt(reqBody, BusConstant.despas, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody = getNewString(reqBody);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<LoginedBean>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<LoginedBean>> response) {
                basePresenter.success(response.get());

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 修改登录密码
     *
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
    public void PostLogoPass(Map map, final OnRequestLinstener<BaseResponseEntity> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity> request = new FastJsonRequest<BaseResponseEntity>(url, method, typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);
        try {
            reqBody = DigestUtils.encrypt(reqBody, BusConstant.despas, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody = getNewString(reqBody);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity> response) {
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 忘记密码---短信验证码
     *
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
    public void PostSeekPass(Map map, final OnRequestLinstener<BaseResponseEntity> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity> request = new FastJsonRequest<BaseResponseEntity>(url, method, typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);
        try {
            reqBody = DigestUtils.encrypt(reqBody, BusConstant.despas, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody = getNewString(reqBody);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity> response) {
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 请求地区富有
     *
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
    public void GetAreaID(Map map, final OnRequestLinstener<BaseResponseEntity<List<FuiouChinaAreaBean>>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity<List<FuiouChinaAreaBean>>> request = new FastJsonRequest<BaseResponseEntity<List<FuiouChinaAreaBean>>>(url, method, typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<List<FuiouChinaAreaBean>>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<FuiouChinaAreaBean>>> response) {
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 获取富有银行
     *
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
    public void PostAccountBank(Map map, final OnRequestLinstener<BaseResponseEntity<List<FuiuoBankBean>>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity<List<FuiuoBankBean>>> request = new FastJsonRequest<BaseResponseEntity<List<FuiuoBankBean>>>(url, method, typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<List<FuiuoBankBean>>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<FuiuoBankBean>>> response) {
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 检测金账户状态
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
    public void GetCheckGoldAccount(Map map, final OnRequestLinstener<BaseResponseEntity> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken){
        Request<BaseResponseEntity> request = new FastJsonRequest<BaseResponseEntity>(url, method, typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity> response) {
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 获取金账户信息
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
    public void GetGAUserInfo(Map map, final OnRequestLinstener<BaseResponseEntity<List<AeestBean>>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken){
            Request<BaseResponseEntity<List<AeestBean>>> request = new FastJsonRequest<BaseResponseEntity<List<AeestBean>>>(url, method, typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<List<AeestBean>>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<AeestBean>>> response) {
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 身份证绑定
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */

    public void PostUpdateCardID(Map map, final OnRequestLinstener<BaseResponseEntity<LoginedBean>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity<LoginedBean>> request = new FastJsonRequest<BaseResponseEntity<LoginedBean>>(
                url, method, typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);
        try {
            reqBody = DigestUtils.encrypt(reqBody, BusConstant.despas, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody = getNewString(reqBody);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<LoginedBean>>() {
            @Override
            public void onStart(int what) {

            }

            @Override

            public void onSucceed(int what, Response<BaseResponseEntity<LoginedBean>> response) {
                BaseResponseEntity<LoginedBean> result = response.get();
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(responseCode + "/////////////////////////");
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    /**
     * 获取摇一摇红包
     *
     * @return
     */
    public BaseResponseEntity<YaoNumBean> getYaoYiYaoProduct(String str) {
        if (str != null) {
            String restult = NetUtils.getRequest(HttpUrl.yaoyiyao, null);
            Type type = new TypeToken<BaseResponseEntity<YaoNumBean>>() {
            }.getType();
            try {
                BaseResponseEntity bre = GsonUtils.getGson().fromJson(restult, type);
                return bre;
            } catch (JsonSyntaxException e) {
                return GsonUtils.getDateGson(null).fromJson(restult, BaseResponseEntity.class);
            }
        }

        return null;
    }

    /**
     * 获取摇一摇次数
     *
     * @param bean
     * @return
     */
    public BaseResponseEntity<YaoYiYaoBean> getYaoNum(String bean) {
        if (bean != null) {
            String yaoBody = GsonUtils.getGson().toJson(bean);
            String resullt = NetUtils.getRequest(HttpUrl.YaoYiYaoNum, null);
            LogUtils.e(resullt);
            Type type = new TypeToken<BaseResponseEntity<YaoYiYaoBean>>() {
            }.getType();
            try {
                BaseResponseEntity bre = GsonUtils.getGson().fromJson(resullt, type);
                return bre;
            } catch (JsonSyntaxException e) {
                return GsonUtils.getDateGson(null).fromJson(resullt, BaseResponseEntity.class);
            }
        }
        return null;
    }

    /**
     * 获取规则pop
     */
    public BaseResponseEntity getGuize(String string) {
        if (string != null) {
            String restul = NetUtils.getRequest(HttpUrl.GetGuize, null);
            Type type = new TypeToken<BaseResponseEntity>() {
            }.getType();
            try {
                BaseResponseEntity besa = GsonUtils.getGson().fromJson(restul, type);
                return besa;
            } catch (Exception e) {
                return GsonUtils.getGson().fromJson(restul, BaseResponseEntity.class);
            }
        }
        return null;
    }


}
