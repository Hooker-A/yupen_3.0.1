package com.huaop2p.yqs.module.one_home.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.base.contant.HttpConnector;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.view.BaseFragment;
import com.huaop2p.yqs.module.four_set.activity.ValidatorVersion;
import com.huaop2p.yqs.module.four_set.entity.ReqNewVersionBean;
import com.huaop2p.yqs.module.four_set.entity.UpdateVersionBean;
import com.huaop2p.yqs.module.four_set.model.MyFinancialWeb;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.logon.activity.LoginActivity;
import com.huaop2p.yqs.module.logon.activity.LogonActivity;
import com.huaop2p.yqs.module.one_home.activity.JumpWebviewAcitvity;
import com.huaop2p.yqs.module.one_home.Module.view.RiseNumberTextView;
import com.huaop2p.yqs.module.one_home.activity.AboutYuPenActivtity;
import com.huaop2p.yqs.module.one_home.activity.CaptureActivity;
import com.huaop2p.yqs.module.one_home.activity.MFActivity;
import com.huaop2p.yqs.module.one_home.activity.MessagActivity;
import com.huaop2p.yqs.module.one_home.activity.SiMuZhaiActivity;
import com.huaop2p.yqs.module.one_home.activity.YaoYiYaoActivity;
import com.huaop2p.yqs.module.one_home.activity.YuPenMsgActivity;
import com.huaop2p.yqs.module.one_home.activity.ZiXun_WebViewActivity;
import com.huaop2p.yqs.module.one_home.adapter.BinnerListviewAdapter;
import com.huaop2p.yqs.module.one_home.adapter.BinnerViewPagerAdapter;
import com.huaop2p.yqs.module.one_home.bean.BinnerBean;
import com.huaop2p.yqs.module.one_home.bean.BinnerListBean;
import com.huaop2p.yqs.module.one_home.bean.HomeActionBean;
import com.huaop2p.yqs.module.one_home.bean.HomeMseZiXunBean;
import com.huaop2p.yqs.module.one_home.bean.HomeMsgBean;
import com.huaop2p.yqs.module.one_home.bean.ListviewBean;
import com.huaop2p.yqs.module.one_home.bean.ReqProductBean;
import com.huaop2p.yqs.module.one_home.bean.YieldBean;
import com.huaop2p.yqs.module.one_home.bean.YuPenBean;
import com.huaop2p.yqs.module.one_home.interface_.CallBean;
import com.huaop2p.yqs.module.three_mine.model.impl.MyWealthModelImpl;
import com.huaop2p.yqs.module.three_mine.presenter.impl.MyWealthPresenterImpl;
import com.huaop2p.yqs.module.two_wealth.activity.WealthCenterDetailActivity;
import com.huaop2p.yqs.module.two_wealth.model.entity.EventBusEntity;
import com.huaop2p.yqs.module.two_wealth.model.entity.Style;
import com.huaop2p.yqs.module.two_wealth.model.entity.WealthCenter;
import com.huaop2p.yqs.utils.DecimalFormatUitls;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.module.one_home.entity.Web;
import com.huaop2p.yqs.utils.auto.AppUtils;
import com.huaop2p.yqs.utils.auto.AutoUtils;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.huaop2p.yqs.widget.MyTextView;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshBase;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshScrollView;
import com.lidroid.xutils.util.LogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yolanda.nohttp.RequestMethod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by zgq on 2016/4/6.
 * 作者:  zhu  guo qing
 * 时间:  2016/4/6 17:16
 * 功能:  首页
 */
public class PreViewFragment extends BaseFragment {


    private ImageLoader imageLoader = ImageLoader.getInstance();
    private AppApplication application;

    private ViewPager vp_binner;
    private RiseNumberTextView all_stokecount;
    private RiseNumberTextView all_money;
    private RiseNumberTextView all_income;
    private View view;
    private BinnerViewPagerAdapter binnerpageradapter;

    private PullToRefreshScrollView mScrollView;

    private ImageView imb_scan;
    private RelativeLayout relay_sao;
    private RelativeLayout relay_yao;
    private RelativeLayout relay_msg;

    private RelativeLayout relay_terrace;
    private RelativeLayout relay_scan;
    private RelativeLayout relay_market;
    private RelativeLayout relay_landing;

    private ImageView img_hongbao;
    private TextView textview_hometitle;


    private ImageView image_landing;
    private TextView text_landing;
    private TextView text_landingg;


    private ViewPager vp_produce;
    private Button btn_buy;

    private LinearLayout liner_layout_msg;
    private LinearLayout linlay_viewpa;


    private Runnable viewpagerRunnable;
    private Handler handler = new Handler();
    private static final int TIME = 3000;
    private PopupWindow popupWindows;
    private LinearLayout liner_pop;
    private LinearLayout lilay_redpag;

    private ListView listview_new;
    private BinnerListviewAdapter listviewAdapter;
    private List<HomeMseZiXunBean> homeMseZiXunBeans;

    private List<YuPenBean> aboutyupen;

    private ImageView image_right;
    private ImageView image_left;

    private MyWealthPresenterImpl myWealthPresenter;
    private MyWealthModelImpl myWealthModel = new MyWealthModelImpl();

    int index;


    private List<WealthCenter> list;
    private List<View> pagerproduce;

    int mode;
    String url;
    YuPenBean bean;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_preview, container, false);
        application = (AppApplication) getActivity().getApplication();

        initview();

        getbinnerpager();
        getBinner();

        Listenner();

//        getListviewData();

        isLogn();

        setLinstener();

        Intent intent = getActivity().getIntent();
        String dat = intent.getDataString();
//        ToastUtils.show(getActivity(),dat+"成功");
        getUpApp();
        return view;
    }


    @Override
    protected boolean onTouchEvent(MotionEvent event) {
        return false;
    }


    //初始化ui
    private void initview() {
        vp_binner = (ViewPager) view.findViewById(R.id.vp_binner);
        all_stokecount = (RiseNumberTextView) view.findViewById(R.id.all_stokecount);
        all_money = (RiseNumberTextView) view.findViewById(R.id.all_money);
        all_income = (RiseNumberTextView) view.findViewById(R.id.all_income);
        vp_produce = (ViewPager) view.findViewById(R.id.vp_produce);
        imb_scan = (ImageView) view.findViewById(R.id.imb_scan);
        listview_new = (ListView) view.findViewById(R.id.listview_new);
        liner_layout_msg = (LinearLayout) view.findViewById(R.id.liner_layout_msg);
        btn_buy = (Button) view.findViewById(R.id.btn_buy);
        linlay_viewpa = (LinearLayout) view.findViewById(R.id.linlay_viewpa);
        relay_terrace = (RelativeLayout) view.findViewById(R.id.relay_terrace);
        relay_scan = (RelativeLayout) view.findViewById(R.id.relay_scan);
        relay_msg = (RelativeLayout) view.findViewById(R.id.relay_msg);
        relay_market = (RelativeLayout) view.findViewById(R.id.relay_market);
        relay_landing = (RelativeLayout) view.findViewById(R.id.relay_landing);
        image_landing = (ImageView) view.findViewById(R.id.image_landing);
        text_landing = (TextView) view.findViewById(R.id.text_landing);
        text_landingg = (TextView) view.findViewById(R.id.text_landingg);
        image_left = (ImageView) view.findViewById(R.id.image_left);
        image_right = (ImageView) view.findViewById(R.id.image_right);
        mScrollView = (PullToRefreshScrollView) view.findViewById(R.id.mScrollView);
        img_hongbao = (ImageView) view.findViewById(R.id.img_hongbao);
        textview_hometitle = (TextView) view.findViewById(R.id.textview_hometitle);
    }


    //设置监听
    private void Listenner() {

        initpop();//创建pop


        //首页+进行监听
        imb_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (popupWindows.isShowing()) {
                    popupWindows.dismiss();

                } else {
                    popupWindows.showAsDropDown(imb_scan, 800, 40);
                    imb_scan.setImageResource(R.drawable.shouyexiaotubiao);
                }

            }
        });

        //对pop里的扫一扫监听
        relay_sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scan();
                popupWindows.dismiss();
                imb_scan.setImageResource(R.drawable.shouyexiaotubiao);
            }
        });

        //对pop里的消息进行监听
        relay_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShareLocalUser.getInstance(getActivity()).getLoginState()) {
                    Intent intent = new Intent(getActivity(), MessagActivity.class);
                    startActivity(intent);
                    popupWindows.dismiss();
                    imb_scan.setImageResource(R.drawable.shouyexiaotubiao);
                } else {
                    Intent intent1 = new Intent(getActivity(), LogonActivity.class);
                    startActivity(intent1);
                    popupWindows.dismiss();
                }

            }
        });

        //对摇一摇进行监听
        relay_yao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ShareLocalUser.getInstance(getActivity()).getLoginState()) {
                    Intent intent = new Intent(getActivity(), YaoYiYaoActivity.class);
                    startActivity(intent);
                    popupWindows.dismiss();
                } else {
                    Intent intent1 = new Intent(getActivity(), LogonActivity.class);
                    startActivity(intent1);
                    popupWindows.dismiss();
                }

            }
        });

        //对余盆资讯进行监听
        liner_layout_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), YuPenMsgActivity.class);
                startActivity(intent);
            }
        });

        //对viewpager推荐产品进行监听
        vp_produce.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //对关于平台进行监听
        relay_terrace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Web.getInstance().getHomeYuPen(new CallBean() {
                    @Override
                    public void bean(BinnerListBean bean) {

                    }

                    @Override
                    public void beanHomeMsg(BaseResponseEntity<HomeMsgBean> beanMsg) {

                    }

                    @Override
                    public void beanHomeProduct(BaseResponseEntity<ReqProductBean> beanProduct) {

                    }

                    @Override
                    public void beanHomeMsgZiXun(BaseResponseEntity<List<HomeMseZiXunBean>> beanmsgzixun) {

                    }

                    @Override
                    public void beanYield(BaseResponseEntity<YieldBean> beanMsg) {

                    }

                    @Override
                    public void beanHomeYuPen(BaseResponseEntity<List<YuPenBean>> beanmsgzixun) {
                        aboutyupen = beanmsgzixun.ReturnMessage;
                        for (int i = 0; i < aboutyupen.size(); i++) {
                            mode = aboutyupen.get(i).Mode;
                            url = aboutyupen.get(i).Url;
                            bean = aboutyupen.get(i);
                        }
                        Intent intent = new Intent(getActivity(), AboutYuPenActivtity.class);
                        intent.putExtra("mode", mode);
                        intent.putExtra("url", url);
                        intent.putExtra("beannn", (Serializable) bean);
                        intent.putExtra("tag", 1);
                        intent.putExtra("aboutyupen", (Serializable) aboutyupen);
                        startActivity(intent);
                    }
                });
            }
        });

        //对扫一扫进行监听
        relay_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scan();
            }
        });

        //固定收益类监听
        relay_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Web.getInstance().getHomeYield(new CallBean() {
                    @Override
                    public void bean(BinnerListBean bean) {

                    }

                    @Override
                    public void beanHomeMsg(BaseResponseEntity<HomeMsgBean> beanMsg) {

                    }

                    @Override
                    public void beanHomeProduct(BaseResponseEntity<ReqProductBean> beanProduct) {

                    }

                    @Override
                    public void beanHomeMsgZiXun(BaseResponseEntity<List<HomeMseZiXunBean>> beanmsgzixun) {

                    }

                    @Override
                    public void beanYield(BaseResponseEntity<YieldBean> beanMsg) {
                        YieldBean yieldBean = beanMsg.ReturnMessage;
                        Intent intent = new Intent(getActivity(), SiMuZhaiActivity.class);
                        intent.putExtra("yieldbean", (Serializable) yieldBean);
                        intent.putExtra("tag", 3);
                        startActivity(intent);
                    }

                    @Override
                    public void beanHomeYuPen(BaseResponseEntity<List<YuPenBean>> beanmsgzixun) {

                    }
                });


            }
        });

        //对登陆状态，签到状态进行监听
        relay_landing.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //判断登陆，签到状态的方法
                if (ShareLocalUser.getInstance(getActivity()).getLoginState()) {
                    //签到状态的接口判断
                    myWealthModel.selectIsSign(new OnRequestLinstener<BaseResponseEntity<String>>() {
                        @Override
                        public void success(BaseResponseEntity<String> o) {

                            if (o.ReturnMessage.equals("true")) {
                                image_landing.setImageResource(R.mipmap.ico_qiandao);
                                text_landing.setText("已签到");
//                                setSignResult("今日已签到");//动画果
                                ToastUtils.show(getActivity(), "今日已签到");
                            } else {
                                image_landing.setImageResource(R.mipmap.ico_weiqiandao);
                                text_landing.setText("未签到");
                                //调用签到接口
                                myWealthModel.sign(new OnRequestLinstener<BaseResponseEntity<String>>() {
                                    @Override
                                    public void success(BaseResponseEntity<String> entity) {
                                        if (entity.ReturnStatus.equals("0")) {
                                            image_landing.setImageResource(R.mipmap.ico_qiandao);
                                            text_landing.setText("已签到");
                                            EventBus.getDefault().postSticky(new EventBusEntity(null, 10));
//                                            setSignResult("积分+10");//动画果
                                        } else {
                                            ToastUtils.show(getActivity(), "签到失败");
                                        }
                                    }

                                    @Override
                                    public void error(int code, String error) {
                                        ToastUtils.show(getActivity(), error + "网络错误");
                                    }
                                });
                            }
                        }

                        @Override
                        public void error(int code, String error) {

                        }
                    });

                } else {
                    image_landing.setImageResource(R.mipmap.ico_denglu);
                    text_landing.setText("登   录");
                    Intent intent1 = new Intent(getActivity(), LogonActivity.class);
                    startActivity(intent1);
                }

            }
        });

        //对向右的箭头进行监听
        image_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_produce.arrowScroll(2);//向后翻页，调用viewpager自带的函数
            }
        });

        //对向左的箭头进行监听
        image_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_produce.arrowScroll(1);//向前翻页，调用viewpager自带的函数
            }
        });

    }


    //签到动画
    private void setSignResult(String str) {
        text_landingg.setText(str);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(text_landingg, "alpha", 0.f, 0.2f, 0.f);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(text_landingg, "translationY", 0, -10, -50);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alpha, translateY);
        animatorSet.setDuration(3000);
        animatorSet.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        isLogn();
    }


    //判断是否登陆，签到
    private void isLogn() {

        if (ShareLocalUser.getInstance(getActivity()).getLoginState()) {
            //签到状态判断
            myWealthModel.selectIsSign(new OnRequestLinstener<BaseResponseEntity<String>>() {
                @Override
                public void success(BaseResponseEntity<String> o) {

                    if (o.ReturnMessage.equals("true")) {
                        image_landing.setImageResource(R.mipmap.ico_qiandao);
                        text_landing.setText("已签到");
                    } else {
                        image_landing.setImageResource(R.mipmap.ico_weiqiandao);
                        text_landing.setText("未签到");
                    }
                }

                @Override
                public void error(int code, String error) {

                }
            });

        } else {
            image_landing.setImageResource(R.mipmap.ico_denglu);
            text_landing.setText("登   录");
        }
    }


    //创建弹出popupWindow

    private void initpop() {
        View view = View.inflate(getActivity(), R.layout.home_pop_yaoyiyao, null);
        relay_sao = (RelativeLayout) view.findViewById(R.id.relay_sao);
        relay_yao = (RelativeLayout) view.findViewById(R.id.relay_yao);
        relay_msg = (RelativeLayout) view.findViewById(R.id.relay_msg);
        liner_pop = (LinearLayout) view.findViewById(R.id.liner_pop);

        popupWindows = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindows.setBackgroundDrawable(new BitmapDrawable());//必须设置一个背景
        popupWindows.setTouchable(true);

        // 使其聚集
        popupWindows.setFocusable(true);
        // 设置允许在外点击消失
        popupWindows.setOutsideTouchable(true);

        //对pop消失进行监听
        popupWindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                imb_scan.setImageResource(R.drawable.shouyexiaotubiao);
            }
        });

    }

    private static final int REQUEST_CODE_SCAN = 0x0000;

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";

    //点击扫一扫跳转扫一扫页面，没有写完，等后续逻辑
    //这里有一个demo  xml文件名是aaaa
    private void Scan() {
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        getActivity().startActivityForResult(intent, REQUEST_CODE_SCAN);
    }


    public static final int isNotUpdateWin = 0;

    /**
     * 异步版本更新
     */
    private void getUpApp() {
        AsyncReqNewVersion asyncReqNewVersion = new AsyncReqNewVersion();
        ReqNewVersionBean bean = new ReqNewVersionBean();
        bean.mode = 0;
        bean.Url = HttpConnector.APP_UPDATE_VERSION_URL;
        asyncReqNewVersion.execute(bean);
    }

    /**
     * app检查更新
     */
    public class AsyncReqNewVersion extends AsyncTask<ReqNewVersionBean, Integer, UpdateVersionBean> {
        ReqNewVersionBean bean;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected UpdateVersionBean doInBackground(ReqNewVersionBean[] params) {
            bean = params[0];
            UpdateVersionBean reqBody = MyFinancialWeb.getInstance().getNewServiceVersion(getActivity(), bean.Url);
            LogUtils.e(reqBody + "768120757");
            return reqBody;
        }

        @Override
        protected void onPostExecute(UpdateVersionBean versionBean) {
            try {
                if (versionBean == null) {
                    ToastUtils.show(getActivity(), R.string.str_eror_net_update, Toast.LENGTH_SHORT);
                } else {
                    if (bean.mode == isNotUpdateWin) {
                        Intent intent = new Intent(getActivity(), ValidatorVersion.class);
                        String vstr = GsonUtils.getGson().toJson(versionBean);
                        intent.putExtra(ValidatorVersion.INTENT_VERSION_OBJECT, vstr);
                        intent.putExtra("tag", -1);
                        startActivity(intent);
                    } else {
                        int thisversion = AppUtils.getVersionCode(getActivity());
                        if (versionBean.Code > thisversion) {
                            Intent intent = new Intent(getActivity(), ValidatorVersion.class);
                            String vstr2 = GsonUtils.getGson().toJson(versionBean);
                            intent.putExtra(ValidatorVersion.INTENT_VERSION_OBJECT, vstr2);
                            intent.putExtra("tag", -1);
                            startActivity(intent);
                        } else {

                        }
                    }
                }
//

            } catch (Exception e) {
            }
        }
    }


    //头部binner设置适配
    private void setPagerAdapter(ViewPager viewPager, List<View> list, boolean isLoop) {
        binnerpageradapter = new BinnerViewPagerAdapter();
        binnerpageradapter.setpagview(list, isLoop);
        viewPager.setAdapter(binnerpageradapter);
    }

    private void getbinnerpager() {
        //获取首页binner
        Web.getInstance().getrequest(new CallBean() {
            @Override
            public void bean(BinnerListBean bean) {
                bannerViewPage(vp_binner, R.id.ll_pions, bean);
                //启动自动轮播
//                initRunnable();
            }

            @Override
            public void beanHomeMsg(BaseResponseEntity<HomeMsgBean> beanMsg) {

            }

            @Override
            public void beanHomeProduct(BaseResponseEntity<ReqProductBean> beanProduct) {

            }

            @Override
            public void beanHomeMsgZiXun(BaseResponseEntity<List<HomeMseZiXunBean>> beanmsgzixun) {

            }

            @Override
            public void beanYield(BaseResponseEntity<YieldBean> beanMsg) {

            }

            @Override
            public void beanHomeYuPen(BaseResponseEntity<List<YuPenBean>> beanmsgzixun) {

            }


        });
    }

    //首页的东西
    private void getBinner() {

        getMessageData();

        //下拉刷新
        disableAutoScrollToBottom();


        //获取首页数据
        Web.getInstance().getHomeMsg(new CallBean() {
            @Override
            public void bean(BinnerListBean bean) {

            }

            @Override
            public void beanHomeMsg(BaseResponseEntity<HomeMsgBean> beanMsg) {
                if (!beanMsg.getReturnReason().equals("OK")) {
                    ToastUtils.show(getActivity(), beanMsg.getReturnReason());
                } else {

                    //自定义控件，对数字的变化做了一个动画处理，
                    all_stokecount.withNumber(Float.parseFloat(DecimalFormatUitls.DecimalFormat_zro(Double.parseDouble(beanMsg.ReturnMessage.TradeCount)))).start();
                    all_money.withNumber(Float.parseFloat(DecimalFormatUitls.DecimalFormat_zro(Double.parseDouble(beanMsg.ReturnMessage.TradeMoney)))).start();
                    all_income.withNumber(Float.parseFloat(DecimalFormatUitls.DecimalFormat_two(Double.parseDouble(beanMsg.ReturnMessage.TradeRevenue)))).start();
                }
            }

            @Override
            public void beanHomeProduct(BaseResponseEntity<ReqProductBean> beanProduct) {

            }

            @Override
            public void beanHomeMsgZiXun(BaseResponseEntity<List<HomeMseZiXunBean>> beanmsgzixun) {

            }

            @Override
            public void beanYield(BaseResponseEntity<YieldBean> beanMsg) {

            }

            @Override
            public void beanHomeYuPen(BaseResponseEntity<List<YuPenBean>> beanmsgzixun) {

            }


        });

        //获取首页资讯的三条数据
        Web.getInstance().getHomeZiXun(new CallBean() {
            @Override
            public void bean(BinnerListBean bean) {

            }

            @Override
            public void beanHomeMsg(BaseResponseEntity<HomeMsgBean> beanMsg) {

            }

            @Override
            public void beanHomeProduct(BaseResponseEntity<ReqProductBean> beanProduct) {

            }

            @Override
            public void beanHomeMsgZiXun(BaseResponseEntity<List<HomeMseZiXunBean>> beanmsgzixun) {
                homeMseZiXunBeans = beanmsgzixun.ReturnMessage;


                listviewAdapter = new BinnerListviewAdapter(getActivity(), homeMseZiXunBeans);
                listview_new.setAdapter(listviewAdapter);
                setListViewHeight(listview_new);

                listview_new.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String idd = homeMseZiXunBeans.get(position).Id;

                        //正式接口   测试接口
                        String url = HttpUrl.Get_yupenzixun + idd;//https://api.yupen.cn
                        HomeMseZiXunBean bean = homeMseZiXunBeans.get(position);


                        Intent intent = new Intent(getActivity(), ZiXun_WebViewActivity.class);
                        intent.putExtra("url", url);
                        intent.putExtra("bean", (Serializable) bean);
                        intent.putExtra("tag", 0);
                        startActivity(intent);


                    }
                });


            }

            @Override
            public void beanYield(BaseResponseEntity<YieldBean> beanMsg) {

            }

            @Override
            public void beanHomeYuPen(BaseResponseEntity<List<YuPenBean>> beanmsgzixun) {

            }
        });


        //获取首页推荐产品
        Web.getInstance().getCrowdProductList2(new CallBean() {
            @Override
            public void bean(BinnerListBean bean) {

            }

            @Override
            public void beanHomeMsg(BaseResponseEntity<HomeMsgBean> beanMsg) {

            }

            @Override
            public void beanHomeProduct(final BaseResponseEntity<ReqProductBean> beanProduct) {
                LogUtils.e(beanProduct.ReturnMessage.Xin.get(0).Name);
                if (beanProduct != null) {
                    ProducePage(vp_produce, beanProduct);
                    //对马上投资进行监听
                    btn_buy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent inten = new Intent(getActivity(), WealthCenterDetailActivity.class);
                            inten.putExtra("id", list.get(index).KeyId);
                            int type = State.YUXINBAO;
                            if (list.get(index).AssetStyles.get(0).Font.equals("房"))
                                type = State.YUFANGBAO;
                            else if (list.get(index).AssetStyles.get(0).Font.equals("车"))
                                type = State.YUCHEBAO;
                            inten.putExtra("type", type);//1是余信宝，2是余房宝，3是余车宝
                            startActivity(inten);
                            EventBus.getDefault().postSticky(list.get(index));

                        }
                    });
                }


            }

            @Override
            public void beanHomeMsgZiXun(BaseResponseEntity<List<HomeMseZiXunBean>> beanmsgzixun) {

            }

            @Override
            public void beanYield(BaseResponseEntity<YieldBean> beanMsg) {

            }

            @Override
            public void beanHomeYuPen(BaseResponseEntity<List<YuPenBean>> beanmsgzixun) {

            }


        });

    }

    //获取首页活动
    HomeActionBean homeActionBean = null;

    private void getMessageData() {


        Map map = new HashMap();
        Web.getInstance().getHomeAction(map, new OnRequestLinstener<BaseResponseEntity<Object>>() {
            @Override
            public void success(BaseResponseEntity<Object> msgCenterBeanBaseResponseEntity) {
                LogUtils.e(msgCenterBeanBaseResponseEntity.ReturnMessage + "");
                if (msgCenterBeanBaseResponseEntity.ReturnMessage instanceof HomeActionBean) {
                    homeActionBean = (HomeActionBean) msgCenterBeanBaseResponseEntity.ReturnMessage;
                    if (homeActionBean != null && !homeActionBean.equals("")) {
                        textview_hometitle.setText(homeActionBean.HomeTitle);
                        img_hongbao.setVisibility(View.VISIBLE);


                        ImageLoader.getInstance().displayImage(homeActionBean.IconUrl, img_hongbao);
                        img_hongbao.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), SiMuZhaiActivity.class);
                                intent.putExtra("tag", 1);
                                intent.putExtra("bean", (Serializable) homeActionBean);
                                startActivity(intent);
                            }
                        });

                    } else {
                        textview_hometitle.setText("首页");
                        img_hongbao.setVisibility(View.GONE);
                    }
                } else {

                }


            }

            @Override
            public void error(int code, String error) {
                LogUtils.e(error);
            }
        }, HttpUrl.Get_NewActivity, 0, RequestMethod.GET, new TypeToken<BaseResponseEntity<HomeActionBean>>() {
        });
    }


    /**
     * bannerviewpager 初始化
     *
     * @param viewPager
     * @param piontId
     * @param binnerListBean
     */
    private void bannerViewPage(ViewPager viewPager, final int piontId, BinnerListBean binnerListBean) {
        if (binnerListBean.getProjects() == null || binnerListBean.getProjects().isEmpty()) {
            return;
        }
        final List<View> pageViews = new ArrayList<View>(binnerListBean.getProjects().size());
        for (int i = 0; i < binnerListBean.getProjects().size(); i++) {
            LinearLayout layout = new LinearLayout(getActivity());
            ViewGroup.LayoutParams ltp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            final ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            imageLoader.displayImage(binnerListBean.getProjects().get(i).getUrl(), imageView, application.getOptions());
            layout.addView(imageView, ltp);
            setViewClick(layout, binnerListBean.getProjects().get(i));
            pageViews.add(layout);
        }
        final ImageView[] imageViews = addPoints(piontId, pageViews.size(), R.drawable.red_radius, R.drawable.white_radius);
        //给viewpager设置监听事件 1
        setViewPagerListener(viewPager, imageViews, imageViews.length, R.drawable.red_radius, R.drawable.white_radius);
        //给viewpager设置适配器
        setPagerAdapter(viewPager, pageViews, true);

    }

    /**
     * 设置头banner点击
     *
     * @param view
     * @param resBannerBean
     */
    private void setViewClick(View view, final BinnerBean resBannerBean) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (resBannerBean.getSource()) {
                    case 1://不发生跳转行为

                        break;
                    case 2://余盆投资列表
                        ((MFActivity) getActivity()).OnInMarket();
                        break;
                    case 3://余盆投资详情页面（产品的KeyId-产品类型（0余信 1余房 2 余车 3转让））
                        ((MFActivity) getActivity()).OnInMarket(resBannerBean.getItemId());
//                        Intent intent = new Intent(getActivity(), WealthCenterDetailActivity.class);
//                        intent.putExtra("type", State.YUXINBAO);
//                        intent.putExtra("id",resBannerBean.Id);
//                        startActivity(intent);
                        break;
                    case 4://众筹列表
//                        ((MFActivity) getActivity()).onInCrowed();
                        break;
                    case 5://众筹详情页面
                        break;
                    case 6://注册页面（注册得红包）
                        Intent register = new Intent(getActivity(), LoginActivity.class);
                        startActivity(register);
                        break;
                    case 7: //跳转到一个网页
                        Intent intent = new Intent(getActivity(), SiMuZhaiActivity.class);
                        intent.putExtra("url", resBannerBean.getItemId());
                        intent.putExtra("tag", 2);
                        startActivity(intent);
                        break;
                    case 8://跳转摇一摇
                        break;
                    case 9://抽奖
                        break;

                }
            }
        });

    }


    private BaseResponseEntity<ReqProductBean> reqProductBean;

    /*
    * 获取首页推荐产品
    * */

    boolean flag;

    private void ProducePage(final ViewPager viewPager, final BaseResponseEntity<ReqProductBean> reqProductBean) {

        if (reqProductBean.ReturnMessage == null || reqProductBean.ReturnMessage.equals("")) {
            return;
        }
        this.reqProductBean = reqProductBean;

        list = new ArrayList<WealthCenter>();//泛型是任洋逗逼的类
        list.clear();
        if (reqProductBean.ReturnMessage.Xin != null) {
            list.addAll(reqProductBean.ReturnMessage.Xin);
        }
        if (reqProductBean.ReturnMessage.House != null) {
            list.addAll(reqProductBean.ReturnMessage.House);
        }
        if (reqProductBean.ReturnMessage.Car != null) {
            list.addAll(reqProductBean.ReturnMessage.Car);
        }

        pagerproduce = new ArrayList<View>(list.size());
        LogUtils.e("list" + list.size());
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i) != null && isAdded()) {

                LinearLayout layout = new LinearLayout(getActivity());
                ViewGroup.LayoutParams ltps = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                View view1 = View.inflate(getActivity(), R.layout.view_product, null);

                TextView text_prod = (TextView) view1.findViewById(R.id.text_prod);//融信宝
                TextView text_prod2 = (TextView) view1.findViewById(R.id.text_prod2);//融信宝
                TextView text_prod3 = (TextView) view1.findViewById(R.id.text_prod3);
                TextView text_per = (TextView) view1.findViewById(R.id.text_per);  //年化收益的百发比
                TextView tet_day = (TextView) view1.findViewById(R.id.tet_day);     //投资的月数
                TextView text_money = (TextView) view1.findViewById(R.id.text_money);//已投金额
                TextView text_yea = (TextView) view1.findViewById(R.id.text_yea); //预期年化收益，就这几个字

                TextView text_con = (TextView) view1.findViewById(R.id.text_con);
//                TextView text_pro1 = (TextView) view1.findViewById(R.id.text_pro1);//500元起投
//                TextView text_pro2 = (TextView) view1.findViewById(R.id.text_pro2);//保
//                TextView text_pro3 = (TextView) view1.findViewById(R.id.text_pro3);//红包
//                TextView text_pro4 = (TextView) view1.findViewById(R.id.text_pro4);//奖券
                lilay_redpag = (LinearLayout) view1.findViewById(R.id.lilay_redpag);

                String name = list.get(i).Name;

                //用来判断是否有-
                if (name.contains("-")) {
                    String[] name1 = name.split("-");
                    String name2 = name1[0];
                    String name3 = name1[1];
                    text_prod.setText(name2 + "-");
                    text_prod2.setText(name3);
                    if (name1.length >= 3) {
                        String name4 = name1[2];
                        text_prod3.setText("-" + name4);
                    }

                } else {
                    text_prod.setText(name);
                }
                text_yea.setText(list.get(i).TopoTwo);

                //对收益率里的字符串作处理
                String topoone = list.get(i).TopoOne;
                if (topoone.indexOf(".") != -1 && topoone.indexOf("%") > topoone.indexOf(".")) {
                    text_per.setText(topoone.substring(0, topoone.indexOf(".")));
                    text_con.setText(topoone.substring(topoone.indexOf(".")));
                } else {
                    text_per.setText(topoone.substring(0, topoone.indexOf("%")));
                    text_con.setText(topoone.substring(topoone.indexOf("%")));
                }

                Double dddd = Double.parseDouble(list.get(i).Amount);
                Double ddddd = dddd / 20f;
                text_money.setText(DecimalFormatUitls.DecimalFormat_two(ddddd));
                String MinimumMoney = list.get(i).MinimumMoney;
//                text_pro1.setText(DecimalFormatUitls.DecimalFormat_zro(Double.parseDouble(MinimumMoney)) + "起投");

                String suffix = null;
                switch (list.get(i).LoanTermId) {
                    case State.DAY:
                        suffix = "天";
                        break;
                    case State.MONTH:
                        suffix = "个月";
                        break;
                    case State.YEAR:
                        suffix = "年";
                        break;
                }
                tet_day.setText(String.valueOf(list.get(i).LoanTerm) + suffix);

                WealthCenter wealthCenter1 = new WealthCenter();
                wealthCenter1.AssetStyles = list.get(i).AssetStyles;

                //对起投额，信，红包，奖券进行设置，任洋逗逼写的方法
                if (wealthCenter1.AssetStyles != null) {
                    for (int j = 0; j < wealthCenter1.AssetStyles.size(); j++) {

                        Style style = wealthCenter1.AssetStyles.get(j);
                        MyTextView textView = new MyTextView(getActivity());
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(8, 8, 8, 8);
                        textView.setLayoutParams(layoutParams);
                        textView.setText(style.Font);
                        textView.setTextSize(7);
                        textView.setPadding(8, 8, 8, 8);
                        textView.setTextColor(Color.WHITE);
                        textView.setBackgroundColor(Color.parseColor("#" + style.Color));
                        AutoUtils.auto(textView);
                        lilay_redpag.addView(textView, 0);
                    }
                }


                layout.addView(view1, ltps);
                pagerproduce.add(layout);
                setPagerAdapter(viewPager, pagerproduce, false);

                //对viewpager里的view进行监听跳转
                view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent inten = new Intent(getActivity(), WealthCenterDetailActivity.class);
                        inten.putExtra("id", list.get(index).KeyId);
                        int type = State.YUXINBAO;
                        if (list.get(index).AssetStyles.get(0).Font.equals("房"))
                            type = State.YUFANGBAO;
                        else if (list.get(index).AssetStyles.get(0).Font.equals("车"))
                            type = State.YUCHEBAO;
                        inten.putExtra("type", type);//1是余信宝，2是余房宝，3是余车宝
                        startActivity(inten);
                        EventBus.getDefault().postSticky(list.get(index));
                    }
                });
            }

            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    index = position;

                    if (position == 0) {
                        image_left.setImageResource(R.drawable.left);
                        image_right.setImageResource(R.drawable.ho_first_right);
                    } else if (position == pagerproduce.size() - 1) {
                        image_left.setImageResource(R.drawable.ho_first_left);
                        image_right.setImageResource(R.drawable.right);
                    } else {
                        image_left.setImageResource(R.drawable.ho_first_left);
                        image_right.setImageResource(R.drawable.ho_first_right);
                    }


                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }


    }


    /**
     * 自动增加点的个数
     */
    private ImageView[] addPoints(int piontId, int num, int checked, int unchecked) {
        ImageView[] imageViews = new ImageView[num];
        // 包裹小圆点的LinearLayout
        ViewGroup group = (ViewGroup) view.findViewById(piontId);
        // 有几张图片 下面就显示几个小圆点
        for (int i = 0; i < num; i++) {
            LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            //设置每个小圆点距离左边的间距
            margin.setMargins(10, 0, 0, 0);
            ImageView imageView = new ImageView(getActivity());
            //设置每个小圆点的宽高
            imageView.setLayoutParams(new ViewGroup.LayoutParams(15, 15));
            imageViews[i] = imageView;
            if (i == 0) {
                // 默认选中第一张图片
                imageViews[i].setBackgroundResource(checked);
            } else {
                //其他图片都设置未选中状态
                imageViews[i].setBackgroundResource(unchecked);
            }
            group.addView(imageViews[i], margin);
        }
        return imageViews;

    }

    /**
     * 为viewpager 设置监听
     *
     * @param viewPager
     * @param imageViews
     * @param size
     * @param checked
     * @param unchecked
     */

    int selectId = 0;

    private void setViewPagerListener(ViewPager viewPager, final ImageView[] imageViews, final int size, final int checked, final int unchecked) {

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //遍历数组让当前选中图片下的小圆点设置颜色
                if (position > 1) {
                    position = position % size;
                }

                for (int i = 0; i < size; i++) {
                    imageViews[position].setBackgroundResource(checked);

                    if (position != i) {
                        imageViews[i].setBackgroundResource(unchecked);
                    }
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });
    }

    private List<ListviewBean> ListviewBean = new ArrayList<ListviewBean>();

    //首页list加载假数据
//    private void getListviewData() {
//
//
//        for (int i = 0; i < 3; i++) {
//            ListviewBean listbean = new ListviewBean();
//            listbean.setText1("【余盆公告】");
//            listbean.setText2("爱心传递正能量,真情聚集福利院");
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            listbean.setText3(sdf.format(new Date()));
//            ListviewBean.add(listbean);
//        }
//        listviewAdapter = new BinnerListviewAdapter(getActivity(), ListviewBean);
//        liner_layout_msg.setAdapter(listviewAdapter);
//        setListViewHeight(listview_new);
//    }

    //解决scrollview与listview的滑动冲突问题
    private void setListViewHeight(ListView listview) {
        BinnerListviewAdapter listadapter = (BinnerListviewAdapter) listview.getAdapter();
        if (listadapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listadapter.getCount(); i++) {
            View listItem = listadapter.getView(i, null, listview);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listview.getLayoutParams();
        params.height = totalHeight + (listview.getDividerHeight() * (listadapter.getCount() - 1));
        listview.setLayoutParams(params);
    }

    /**
     * 定时切换
     */
    protected void initRunnable() {
        viewpagerRunnable = new Runnable() {

            @Override
            public void run() {
                int current = vp_binner.getCurrentItem();
                vp_binner.setCurrentItem(current + 1);
                handler.postDelayed(viewpagerRunnable, TIME);
            }
        };
        handler.postDelayed(viewpagerRunnable, TIME);
    }

    /***
     * 防止scrollview回滚到底部
     **/
    private void disableAutoScrollToBottom() {
        mScrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        mScrollView.setFocusable(true);
        mScrollView.setFocusableInTouchMode(true);
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });

        showError();
    }

    private void setLinstener() {
        mScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {

                getBinner();
                Listenner();
                isLogn();
            }
        });

        showError();
    }

    //加载完数据之后
    public void showError() {
        mScrollView.stopRefresh();
    }

}

