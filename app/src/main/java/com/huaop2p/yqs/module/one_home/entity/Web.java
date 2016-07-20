package com.huaop2p.yqs.module.one_home.entity;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.Bus.BusConstant;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.model.BeforeNetworkCallbace;
import com.huaop2p.yqs.module.base.view.BeforeNetworkBean;
import com.huaop2p.yqs.module.one_home.bean.BinnerListBean;
import com.huaop2p.yqs.module.one_home.bean.HomeMseZiXunBean;
import com.huaop2p.yqs.module.one_home.bean.HomeMsgBean;
import com.huaop2p.yqs.module.one_home.bean.MsgCenterBean;
import com.huaop2p.yqs.module.one_home.bean.ReqProductBean;
import com.huaop2p.yqs.module.one_home.bean.YaoYiYaoBean;
import com.huaop2p.yqs.module.one_home.bean.YieldBean;
import com.huaop2p.yqs.module.one_home.bean.YuPenBean;
import com.huaop2p.yqs.module.one_home.interface_.CallBean;
import com.huaop2p.yqs.module.one_home.interface_.SignBean;
import com.huaop2p.yqs.utils.DigestUtils;
import com.huaop2p.yqs.utils.net.CallServer;
import com.huaop2p.yqs.utils.net.FastJsonRequest;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.Response;
import com.huaop2p.yqs.module.base.model.BaseModel;

import java.util.List;
import java.util.Map;

/**
 * Created by maoxiaofei on 2016/4/14.
 */
public class Web extends BaseModel {

    int SuccessorFail;

    private static class SingletonHolder {
        private static final Web INSTANCE = new Web();
    }

    private Web() {
    }


    public static Web getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void getBeforeNetwork(final BeforeNetworkCallbace callbace) {
        final Request<BeforeNetworkBean> request = new FastJsonRequest<BeforeNetworkBean>(
                "http://infotip.yupen.cn/api/values", RequestMethod.GET, new TypeToken<BeforeNetworkBean>() {
        });
        CallServer.getRequestInstance().add(0, request, new OnResponseListener<BeforeNetworkBean>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BeforeNetworkBean> response) {


                BeforeNetworkBean bean1 = response.get();
                callbace.beforenetwork(bean1);

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(exception + "");

            }

            @Override
            public void onFinish(int what) {

            }

        });
    }

    //获取首面页binner
    public void getrequest(final CallBean bean) {
        final Request<BinnerListBean> request = new FastJsonRequest<BinnerListBean>(
                HttpUrl.bannerURL, RequestMethod.GET, new TypeToken<BinnerListBean>() {
        });
        CallServer.getRequestInstance().add(0, request, new OnResponseListener<BinnerListBean>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BinnerListBean> response) {
                LogUtils.e(GsonUtils.getGson().toJson(response.get()));
                SuccessorFail = 0;

                BinnerListBean bean1 = response.get();
                bean.bean(bean1);
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(exception + "");
//                SuccessorFail += 1;
//                if (SuccessorFail == 3) {
//                    getBeforeNetwork(new BeforeNetworkCallbace() {
//                        @Override
//                        public void beforenetwork(BeforeNetworkBean bean) {
////                            BeforeNetworkBean bean1 = new BeforeNetworkBean();
//
//                            //1和2的区别是 Normal = 0,//正常  Tip = 1,//信息提示可以关闭  Exception = 2//系统异常不允许关闭
//
//
//                            if (bean.InfoType == 0) {
//
//                            } else if (bean.InfoType == 2) {
////                                Intent intent = new Intent(this, BeforeNetWork_Activity.class);
////                                intent.putExtra(bean.Url);
////                                startActivity(intent);
//                            }
//                        }
//                    });
//
//                }

            }

            @Override
            public void onFinish(int what) {

            }

        });
    }

    //获取首页信息接口调用
    public BaseResponseEntity<HomeMsgBean> getHomeMsg(final CallBean callBean) {

        final Request<BaseResponseEntity<HomeMsgBean>> request = new FastJsonRequest<BaseResponseEntity<HomeMsgBean>>(
                HttpUrl.HomeDataMsg, RequestMethod.GET, new TypeToken<BaseResponseEntity<HomeMsgBean>>() {
        });
        CallServer.getRequestInstance().add(0, request, new OnResponseListener<BaseResponseEntity<HomeMsgBean>>() {
            @Override
            public void onStart(int what) {
                // 请求开始时，可以显示一个Dialog

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<HomeMsgBean>> response) {
                // 接受请求结果
                // String result = response.get();
//                 Bitmap imageHead = response.get(); // 如果是bitmap类型，都是同样的用法
                BaseResponseEntity<HomeMsgBean> bean = response.get();
                LogUtils.e(GsonUtils.getGson().toJson(response.get()));
                if (bean != null && bean.ReturnMessage.TradeCount != null && bean.ReturnMessage.TradeMoney != null && bean.ReturnMessage.TradeRevenue != null) {
                    callBean.beanHomeMsg(bean);

                } else {

                }


            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                // 请求失败或者发生异常
                // 这里根据exception处理不同的错误，比如超时、网络不好等
                LogUtils.e(exception + "");
            }

            @Override
            public void onFinish(int what) {
                // 请求接受时，关闭Dialog
            }
        });
        return null;
    }

    //获取首页固定收益率接口调用
    public BaseResponseEntity<YieldBean> getHomeYield(final CallBean callBean) {

        final Request<BaseResponseEntity<YieldBean>> request = new FastJsonRequest<BaseResponseEntity<YieldBean>>(
                HttpUrl.Get_PrivateRaise, RequestMethod.GET, new TypeToken<BaseResponseEntity<YieldBean>>() {
        });
        CallServer.getRequestInstance().add(0, request, new OnResponseListener<BaseResponseEntity<YieldBean>>() {
            @Override
            public void onStart(int what) {
                // 请求开始时，可以显示一个Dialog

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<YieldBean>> response) {
                // 接受请求结果
                // String result = response.get();
//                 Bitmap imageHead = response.get(); // 如果是bitmap类型，都是同样的用法
                BaseResponseEntity<YieldBean> bean = response.get();
                LogUtils.e(GsonUtils.getGson().toJson(response.get()));
                if (bean != null && bean.ReturnMessage.Url != null) {
                    callBean.beanYield(bean);

                } else {

                }


            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                // 请求失败或者发生异常
                // 这里根据exception处理不同的错误，比如超时、网络不好等
                LogUtils.e(exception + "");
            }

            @Override
            public void onFinish(int what) {
                // 请求接受时，关闭Dialog
            }
        });
        return null;
    }

    //获取首页关于余盆接口调用
    public BaseResponseEntity<List<YuPenBean>> getHomeYuPen(final CallBean callBean) {

        final Request<BaseResponseEntity<List<YuPenBean>>> request = new FastJsonRequest<BaseResponseEntity<List<YuPenBean>>>(
                HttpUrl.Get_AboutPlatform, RequestMethod.GET, new TypeToken<BaseResponseEntity<List<YuPenBean>>>() {
        });
        CallServer.getRequestInstance().add(0, request, new OnResponseListener<BaseResponseEntity<List<YuPenBean>>>() {
            @Override
            public void onStart(int what) {
                // 请求开始时，可以显示一个Dialog

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<YuPenBean>>> response) {

                // 接受请求结果
                // String result = response.get();
//                 Bitmap imageHead = response.get(); // 如果是bitmap类型，都是同样的用法
                BaseResponseEntity<List<YuPenBean>> bean = response.get();
                LogUtils.e(GsonUtils.getGson().toJson(response.get()));
                if (bean != null && bean.ReturnMessage != null) {
                    callBean.beanHomeYuPen(bean);

                } else {

                }


            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

                // 请求失败或者发生异常
                // 这里根据exception处理不同的错误，比如超时、网络不好等
                LogUtils.e(exception + "");
            }

            @Override
            public void onFinish(int what) {
                // 请求接受时，关闭Dialog
            }
        });
        return null;
    }


    //获取首页三条资讯
    public BaseResponseEntity<List<HomeMseZiXunBean>> getHomeZiXun(final CallBean callBean) {

        final Request<BaseResponseEntity<List<HomeMseZiXunBean>>> request = new FastJsonRequest<BaseResponseEntity<List<HomeMseZiXunBean>>>(
                HttpUrl.Get_Infomations, RequestMethod.GET, new TypeToken<BaseResponseEntity<List<HomeMseZiXunBean>>>() {
        });
        CallServer.getRequestInstance().add(0, request, new OnResponseListener<BaseResponseEntity<List<HomeMseZiXunBean>>>() {
            @Override
            public void onStart(int what) {
                // 请求开始时，可以显示一个Dialog

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<HomeMseZiXunBean>>> response) {

                LogUtils.e(GsonUtils.getGson().toJson(response.get()));
                BaseResponseEntity<List<HomeMseZiXunBean>> bean = response.get();
                if (bean != null && bean.ReturnMessage.size() != 0) {
                    callBean.beanHomeMsgZiXun(bean);

                } else {

                }

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                // 请求失败或者发生异常
                // 这里根据exception处理不同的错误，比如超时、网络不好等
                LogUtils.e(exception + "");
            }

            @Override
            public void onFinish(int what) {
                // 请求接受时，关闭Dialog
            }
        });
        return null;
    }

    //获取更多资讯
    public void getmorezixun(Map map, final OnRequestLinstener<BaseResponseEntity<List<HomeMseZiXunBean>>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity<List<HomeMseZiXunBean>>> entityRequest = new FastJsonRequest<BaseResponseEntity<List<HomeMseZiXunBean>>>(url, method,
                typeToken);

        String reqBody = GsonUtils.getGson().toJson(map);

        try {
            reqBody = DigestUtils.encrypt(reqBody, BusConstant.despas, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody = getNewString(reqBody);
        entityRequest.setRequestBody(reqBody);

        CallServer.getRequestInstance().add(what, entityRequest, new OnResponseListener<BaseResponseEntity<List<HomeMseZiXunBean>>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<HomeMseZiXunBean>>> response) {
                LogUtils.e(GsonUtils.getGson().toJson(response.get()));
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(exception + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    /**
     * 获取推荐的产品列表
     *
     * @return
     */

    public void getCrowdProductList2(final CallBean beanProduct) {
        Request<BaseResponseEntity<ReqProductBean>> entityRequest = new FastJsonRequest<BaseResponseEntity<ReqProductBean>>(
                HttpUrl.GetRecPro, RequestMethod.GET, new TypeToken<BaseResponseEntity<ReqProductBean>>() {
        });
        CallServer.getRequestInstance().add(0, entityRequest, new OnResponseListener<BaseResponseEntity<ReqProductBean>>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<ReqProductBean>> response) {
                LogUtils.e(GsonUtils.getGson().toJson(response.get()));
                LogUtils.e(response.get().ReturnMessage.Xin.size() + "");
                BaseResponseEntity<ReqProductBean> bean = response.get();
                if (bean != null && !bean.equals("")) {
                    beanProduct.beanHomeProduct(response.get());
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(exception + "");
            }

            @Override
            public void onFinish(int what) {

            }


        });
    }

    /*
    * 签到送积分
    * */
    public void getSign(SignBean signBean) {
        Request<BaseResponseEntity<String>> entityrequest = new FastJsonRequest<BaseResponseEntity<String>>(HttpUrl.GetSign, RequestMethod.GET, new TypeToken<BaseResponseEntity<String>>() {
        });
        CallServer.getRequestInstance().add(0, entityrequest, new OnResponseListener<BaseResponseEntity<String>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<String>> response) {
                LogUtils.e(GsonUtils.getGson().toJson(response.get()));
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(exception + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /*
    * 消息列表
    * */
    public void getMsgQue(Map map, final OnRequestLinstener<BaseResponseEntity<List<MsgCenterBean>>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity<List<MsgCenterBean>>> entityRequest = new FastJsonRequest<BaseResponseEntity<List<MsgCenterBean>>>(url, method,
                typeToken);

        String reqBody = GsonUtils.getGson().toJson(map);

        try {
            reqBody = DigestUtils.encrypt(reqBody, BusConstant.despas, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody = getNewString(reqBody);
        entityRequest.setRequestBody(reqBody);

        CallServer.getRequestInstance().add(what, entityRequest, new OnResponseListener<BaseResponseEntity<List<MsgCenterBean>>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<List<MsgCenterBean>>> response) {
                LogUtils.e(GsonUtils.getGson().toJson(response.get()));
                basePresenter.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(exception + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /*
   * 首页活动
   * */
    public void getHomeAction(Map map, final OnRequestLinstener<BaseResponseEntity<Object>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity<Object>> entityRequest = new FastJsonRequest<BaseResponseEntity<Object>>(url, method,
                typeToken);

        String reqBody = GsonUtils.getGson().toJson(map);

        try {
            reqBody = DigestUtils.encrypt(reqBody, BusConstant.despas, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody = getNewString(reqBody);
        entityRequest.setRequestBody(reqBody);

        CallServer.getRequestInstance().add(what, entityRequest, new OnResponseListener<BaseResponseEntity<Object>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<Object>> response) {
                LogUtils.e(GsonUtils.getGson().toJson(response.get()));
                if (response.get() == null) {
                    return;
                } else {
                    basePresenter.success(response.get());
                }

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(exception + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /*
  * 摇一摇规则 接口
  * */
    public void getYaoYiYaoAction(Map map, final OnRequestLinstener<BaseResponseEntity<String>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity<String>> entityRequest = new FastJsonRequest<BaseResponseEntity<String>>(url, method,
                typeToken);

        String reqBody = GsonUtils.getGson().toJson(map);

        try {
            reqBody = DigestUtils.encrypt(reqBody, BusConstant.despas, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody = getNewString(reqBody);
        entityRequest.setRequestBody(reqBody);

        CallServer.getRequestInstance().add(what, entityRequest, new OnResponseListener<BaseResponseEntity<String>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<String>> response) {
                LogUtils.e(GsonUtils.getGson().toJson(response.get()));
                if (response.get() == null) {
                    return;
                } else {
                    basePresenter.success(response.get());
                }

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(exception + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /*
    * 摇一摇规则 接口
    * */
    public void getYaoYiYaoNum(Map map, final OnRequestLinstener<BaseResponseEntity<YaoYiYaoBean>> basePresenter, String url, int what, RequestMethod method, TypeToken typeToken) {
        Request<BaseResponseEntity<YaoYiYaoBean>> entityRequest = new FastJsonRequest<BaseResponseEntity<YaoYiYaoBean>>(url, method,
                typeToken);

        String reqBody = GsonUtils.getGson().toJson(map);

        try {
            reqBody = DigestUtils.encrypt(reqBody, BusConstant.despas, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqBody = getNewString(reqBody);
        entityRequest.setRequestBody(reqBody);

        CallServer.getRequestInstance().add(what, entityRequest, new OnResponseListener<BaseResponseEntity<YaoYiYaoBean>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<YaoYiYaoBean>> response) {
                LogUtils.e(GsonUtils.getGson().toJson(response.get()));
                if (response.get() == null) {
                    return;
                } else {
                    basePresenter.success(response.get());
                }

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(exception + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


}