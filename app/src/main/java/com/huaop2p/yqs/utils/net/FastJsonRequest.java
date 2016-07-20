package com.huaop2p.yqs.utils.net;/*
 * Copyright ? YOLANDA. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.JsonObjectRequest;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RestRequest;
import com.yolanda.nohttp.StringRequest;

/**
 * <p>自定义请求对象.</p>
 * Created in Feb 1, 2016 8:53:17 AM.
 *
 * @param <T>nohttp
 * @author YOLANDA;
 */
public class FastJsonRequest<T> extends RestRequest<T> {
    private Gson gson;
    private TypeToken clazz;

    private static final int CONNECT_TIME_OUT = 25000;


    public FastJsonRequest(String url, TypeToken clazz) {
        super(url);
        this.clazz = clazz;
        gson = new Gson();
        this.addHeader("X-Equipment", "Android");
        setConnectTimeout(CONNECT_TIME_OUT);//连接超时
        setReadTimeout(CONNECT_TIME_OUT);//读取超时

    }

    public FastJsonRequest(String url, RequestMethod requestMethod, TypeToken t) {
        super(url, requestMethod);
        this.clazz = t;
        gson = new Gson();
        this.addHeader("X-Equipment", "Android");
        setConnectTimeout(CONNECT_TIME_OUT);//连接超时
        setReadTimeout(CONNECT_TIME_OUT);//读取超时

    }


    @Override
    public T parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
        String result = StringRequest.parseResponseString(url, responseHeaders, responseBody);
        T myEntity = null;
        Log.i("eee", result);
        try {
            myEntity = gson.fromJson(result, clazz.getType());
        } catch (Throwable e) {
            Log.i("eee", result);
            try {
                myEntity = gson.fromJson(result, new TypeToken<BaseResponseEntity<String>>() {
                }.getType());
            } catch (Throwable ee) {
            }

        }
        return myEntity;
    }

    @Override
    public String getAccept() {
        // 告诉服务器你接受什么类型的数据, 会添加到请求头的Accept中
        return JsonObjectRequest.ACCEPT;
    }

    @Override
    public String getContentType() {
        return "application/json; charset=" + getParamsEncoding();
    }

}
