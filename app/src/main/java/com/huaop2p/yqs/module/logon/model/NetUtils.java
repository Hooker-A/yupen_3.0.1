package com.huaop2p.yqs.module.logon.model;

import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BusConstants;
import com.huaop2p.yqs.module.four_set.service.MQTTService;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.utils.DigestUtils;
import com.huaop2p.yqs.utils.net.SSLSocketFactoryEx;
import com.lidroid.xutils.util.LogUtils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zgq on 2016/4/22.
 * 作者:  zhu  guo qing
 * 时间:  2016/4/22 14:27
 * 功能:
 */
public class NetUtils {

    /**
     * get请求
     *
     * @param urlString
     * @param params
     * @return
     */
    public static String getRequest(String urlString, Map<String, String> params) {

        try {
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append(urlString);

            if (null != params) {

                urlBuilder.append("?");

                Iterator<Map.Entry<String, String>> iterator = params.entrySet()
                        .iterator();

                while (iterator.hasNext()) {
                    Map.Entry<String, String> param = iterator.next();
                    urlBuilder
                            .append(URLEncoder.encode(param.getKey(), "UTF-8"))
                            .append('=')
                            .append(URLEncoder.encode(param.getValue(), "UTF-8"));
                    if (iterator.hasNext()) {
                        urlBuilder.append('&');
                    }
                }
            }
            // 创建HttpClient对象
            HttpClient client = getNewHttpClient();
            client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);//请求超时时间
            // 发送get请求创建HttpGet对象
            HttpGet getMethod = new HttpGet(urlBuilder.toString());
            getMethod.addHeader("X-Equipment", "Android");
//            getMethod.addHeader("X-Equiment-Id", MQTTService.getMqttClientId());
            getMethod.addHeader("X-Equiment-Id", MQTTService.getMqttClientId());
            getMethod.addHeader("Cookie", ShareLocalUser.getInstance(AppApplication.getContext()).getCookie2());
            HttpResponse response = client.execute(getMethod);
            // 获取状态码
            int res = response.getStatusLine().getStatusCode();
            if (res == 200) {

                StringBuilder builder = new StringBuilder();
                // 获取响应内容
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                for (String s = reader.readLine(); s != null; s = reader
                        .readLine()) {
                    builder.append(s);
                }
                return builder.toString();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    private static HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(
                    params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }
    public static String postRequest(String urlString, String jsonBody) {
        try {
            // 1. 创建HttpClient对象
            HttpClient client = getNewHttpClient();
            client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
            // 2. 发get请求创建HttpGet对象
            HttpPost postMethod = new HttpPost(urlString);
            postMethod.addHeader("Content-Type", "application/json");
            postMethod.addHeader("X-Equipment", "Android");
            String cookie = ShareLocalUser.getInstance(AppApplication.getContext()).getCookie2();
            if (cookie != null)
                postMethod.addHeader("cookie", cookie);
            String edsReq = DigestUtils.encrypt(jsonBody, BusConstants.despas, DigestUtils.isDes);
            StringEntity se = new StringEntity(edsReq, HTTP.UTF_8);
            postMethod.setEntity(se);
            HttpResponse response = client.execute(postMethod);
            int statueCode = response.getStatusLine().getStatusCode();
            if (statueCode == 200) {
//                System.out.println(statueCode);
                String res = EntityUtils.toString(response.getEntity());
                res = DigestUtils.decrypt(res, BusConstants.despas, DigestUtils.isDes);
                if(urlString.equals(HttpUrl.Logins)){
                    saveLoginCookie((DefaultHttpClient) client);
                }
                return res;
            }
        } catch (Exception e) {

        }

        return null;
    }

    // 保存时+当时的秒数，
    public static long expires(String second) {
        Long l = Long.valueOf(second);

        return l * 1000L + System.currentTimeMillis();
    }



    /**
     * 保存cookie
     *
     * @param client
     */
    private static void saveLoginCookie(DefaultHttpClient client) {

        String cookie = "";
        if (client == null)
            return;
        if (client.getCookieStore().getCookies().size() > 0) {
            for (Cookie item : client.getCookieStore().getCookies()) {
                cookie += item.getName() + "=" + item.getValue() + ";";
            }
            LogUtils.e(cookie);

            ShareLocalUser.getInstance(AppApplication.getContext()).addCookie2(cookie);
        }
    }


}
