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


import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestQueue;

/**
 * Created in Oct 23, 2015 1:00:56 PM.
 *
 * @author YOLANDA;
 */
public class CallServer {

    private static CallServer callServer;

    /**
     * 请求队列.
     */
    private RequestQueue requestQueue;


    public CallServer() {
        super();
        requestQueue= NoHttp.newRequestQueue();
    }


    /**
     * 请求队列.
     */
    public synchronized static CallServer getRequestInstance() {
        if (callServer == null)
            callServer = new CallServer();
        return callServer;
    }


    public <T> void add(int what, Request<T> request,OnResponseListener<T> listener) {
        requestQueue.add(what, request,listener);
    }

    /**
     * 取消这个sign标记的所有请求.
     */
    public void cancelBySign(Object sign) {
        requestQueue.cancelBySign(sign);
    }

    /**
     * 取消队列中所有请求.
     */
    public void cancelAll() {
        requestQueue.cancelAll();
    }

    /**
     * 退出app时停止所有请求.
     */
    public void stopAll() {
        requestQueue.stop();
    }

}
