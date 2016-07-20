package com.huaop2p.yqs.module.one_home.entity;

import com.huaop2p.yqs.module.one_home.interface_.HttpCallbackListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by maoxiaofei on 2016/4/13.
 */
public class Netutils {


    /* 创建一个新的类 HttpTool，将公共的操作抽象出来
    * 为了避免调用sendRequest方法时需实例化，设置为静态方法
    * 传入HttpCallbackListener对象为了方法回调
    * 因为网络请求比较耗时，一般在子线程中进行，
    * 为了获得服务器返回的数据，需要使用java的回调机制
    * */


    public static String sendRequest_get(final String address,
                                   final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;

                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if (listener != null) {
                        // 回调方法 onFinish()
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        // 回调方法 onError()
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
        return null;
    }


    /*
    *  在GET方法实现的基础上增加一个参数params即可，
    * 将参数转换为字符串后传入
    * 也可以传入键值对集合，再处理
     * */
    private  void sendRequest_post(final String address,
                                   final String params, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                URL url = null;
                try {
                    url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if (listener != null) {
                        // 回调方法 onFinish()
                        listener.onFinish(response.toString());
                    }
                } catch (MalformedURLException e) {
                    if (listener != null) {
                        // 回调方法 onError()
                        listener.onError(e);
                    }
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }

            }
        }).start();
    }


}
