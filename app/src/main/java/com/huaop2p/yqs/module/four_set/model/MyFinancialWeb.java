package com.huaop2p.yqs.module.four_set.model;

import android.content.Context;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.Bus.BusConstant;
import com.huaop2p.yqs.module.base.contant.HttpConnector;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseRequestEntity;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.model.BaseModel;
import com.huaop2p.yqs.module.four_set.entity.ReqBaseBean;
import com.huaop2p.yqs.module.four_set.entity.ReqSelBean;
import com.huaop2p.yqs.module.four_set.entity.ResCusManagerBea;
import com.huaop2p.yqs.module.four_set.entity.ResCusManagerBean;
import com.huaop2p.yqs.module.four_set.entity.UpdateVersionBean;
import com.huaop2p.yqs.module.three_mine.model.entity.RedItemBean;
import com.huaop2p.yqs.utils.DigestUtils;
import com.huaop2p.yqs.utils.InputStreamUtils;
import com.huaop2p.yqs.utils.net.CallServer;
import com.huaop2p.yqs.utils.net.FastJsonRequest;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.huaop2p.yqs.utils.net.NetUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.Response;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by zgq on 2016/4/27.
 * 作者:  zhu  guo qing
 * 时间:  2016/4/27 16:09
 * 功能:  设置页面的接口网络请求
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
     * 意见反馈
     *
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
    public void PostFeed(Map map, final OnRequestLinstener<BaseResponseEntity> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken) {
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
     * 意见反馈 加图片
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
    public void PostFeedbackImg(Map map, final OnRequestLinstener<BaseResponseEntity> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken) {
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
                LogUtils.e("/////////////+" + response.get());

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e("/////////////+" + responseCode);


            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    /**
     * 获取客户经理信息
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
    public void PostSelManager(Map map, final OnRequestLinstener<BaseResponseEntity<ReqSelBean>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity<ReqSelBean>> request = new FastJsonRequest<BaseResponseEntity<ReqSelBean>>(url, method, typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);
        try {
            reqBody = DigestUtils.encrypt(reqBody, BusConstant.despas, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody = getNewString(reqBody);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<ReqSelBean>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<ReqSelBean>> response) {
                basePresenter.success(response.get());
                LogUtils.e("/////////////+" + response.get());

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e("/////////////+" + responseCode);


            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
    /**
     * 检测最新版本
     *
     * @param url
     * @return
     */
    public UpdateVersionBean getNewServiceVersion(Context context, String url) {
        String result = NetUtils.getRequest(url, null);
        try {
            if (result == null || result.length() == 0)
                return null;
            else {
                InputStream in = InputStreamUtils.StringTOInputStream(result);
//                InputStream in = context.getResources().openRawResource(R.raw.yp_version);

                return getLastVersion(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private UpdateVersionBean getLastVersion(InputStream stream) {
        List<UpdateVersionBean> list = new ArrayList<UpdateVersionBean>();
        UpdateVersionBean result = null;

        //得到 DocumentBuilderFactory 对象, 由该对象可以得到 DocumentBuilder 对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        int tvcode = 0;
        try {
            //得到DocumentBuilder对象
            DocumentBuilder builder = factory.newDocumentBuilder();
            //得到代表整个xml的Document对象
            Document document = builder.parse(stream);
            //得到 "根节点"
            Element root = document.getDocumentElement();
            //获取根节点的所有items的节点
            NodeList items = root.getElementsByTagName("update");
            //遍历所有节点
            for (int i = 0; i < items.getLength(); i++) {
                UpdateVersionBean vitem = new UpdateVersionBean();
                Element item = (Element) items.item(i);
                vitem.Name = item.getAttribute("vname");
                vitem.Code = Integer.parseInt(item.getAttribute("vcode"));
                vitem.Url = item.getAttribute("vurl");//.setName(item.getFirstChild().getNodeValue());
                vitem.updateText = item.getAttribute("vlog");
                if (vitem.Code > tvcode)
                    result = vitem;
                list.add(vitem);
            }
            return result;

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 帮助中心
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
public void GetHelpCenter(Map map, final OnRequestLinstener<BaseResponseEntity> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken){
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
     * 获取系统推荐的客户经理
     *
     * @param reqBean
     * @return
     */
    public BaseResponseEntity<ResCusManagerBea.JsonEntity> getSysCusManager(BaseRequestEntity reqBean) {
        if (reqBean != null) {
            String reqBody = GsonUtils.getGson().toJson(reqBean);
            String result = NetUtils.postRequest(HttpConnector.APP_HTTP_cj_AccountManager, reqBody);

            Type type = new TypeToken<BaseResponseEntity<ResCusManagerBea.JsonEntity>>() {
            }.getType();
            try {
                return GsonUtils.getDateGson(null).fromJson(result, type);
            } catch (JsonSyntaxException e) {
                return GsonUtils.getDateGson(null).fromJson(result, BaseResponseEntity.class);
            }
        }
        return null;
    }
    /**
     * 获取 客户经理
     *
     * @param
     * @return
     */

    public void getCusManager(Map map, final OnRequestLinstener<BaseResponseEntity<ResCusManagerBean>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken){
        Request<BaseResponseEntity<ResCusManagerBean>> request = new FastJsonRequest<BaseResponseEntity<ResCusManagerBean>>(url, method, typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);
        try {
            reqBody = DigestUtils.encrypt(reqBody, BusConstant.despas, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody = getNewString(reqBody);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<ResCusManagerBean>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<ResCusManagerBean>> response) {
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
     * 绑定客户经理
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */

    public void bindCusManager(Map map, final OnRequestLinstener<BaseResponseEntity> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken){
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
     * 获取红包规则
     * @param map
     * @param basePresenter
     * @param url
     * @param what
     * @param method
     * @param typeToken
     */
    public void PostSelectRuleById(Map map, final OnRequestLinstener<BaseResponseEntity<List<RedItemBean>>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity<List<RedItemBean>>> request = new FastJsonRequest<BaseResponseEntity<List<RedItemBean>>>(url, method, typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);
        try {
            reqBody = DigestUtils.encrypt(reqBody, BusConstant.despas, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody = getNewString(reqBody);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<List<RedItemBean>>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<RedItemBean>>> response) {
                basePresenter.success(response.get());
                LogUtils.e("/////////////+" + response.get());

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e("/////////////+" + responseCode);


            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

}
