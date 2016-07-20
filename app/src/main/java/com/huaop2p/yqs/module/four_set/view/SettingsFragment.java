package com.huaop2p.yqs.module.four_set.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.ShowDialog;
import com.huaop2p.yqs.dialog.WaitDialog;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.base.contant.HttpConnector;
import com.huaop2p.yqs.module.base.view.BaseFragment;
import com.huaop2p.yqs.module.four_set.activity.AboutApp;
import com.huaop2p.yqs.module.four_set.activity.CustomerManagerDetialActivity;
import com.huaop2p.yqs.module.four_set.activity.CustomerManagerNoneActivity;
import com.huaop2p.yqs.module.four_set.activity.FeedBackActivity;
import com.huaop2p.yqs.module.four_set.activity.MatterActivity;
import com.huaop2p.yqs.module.four_set.activity.PreStartActivity2;
import com.huaop2p.yqs.module.four_set.activity.ShareActivity;
import com.huaop2p.yqs.module.four_set.activity.ValidatorVersion;
import com.huaop2p.yqs.module.four_set.entity.UpdateVersionBean;
import com.huaop2p.yqs.module.four_set.model.MyFinancialWeb;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.logon.activity.AccountActivity;
import com.huaop2p.yqs.module.logon.activity.LogonActivity;
import com.huaop2p.yqs.module.logon.entity.LoginedBean;
import com.huaop2p.yqs.module.one_home.activity.MFActivity;
import com.huaop2p.yqs.utils.AppInentUtils;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.utils.auto.AppUtils;
import com.huaop2p.yqs.utils.auto.AutoUtils;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.huaop2p.yqs.widget.CircleImageView;
import com.huaop2p.yqs.widget.ReboundScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yolanda.nohttp.cookie.DiskCookieStore;

import de.greenrobot.event.EventBus;

/**
 * Created by zgq on 2016/4/6.
 * 作者:  zhu  guo qing
 * 时间:  2016/4/6 17:21
 * 功能:  更多
 */
public class SettingsFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout rl_yijian, rl_wenti, rl_jiancha, rl_guanyu, rl_help_call, rl_jingli, rl_yindao, rl_zhuanfa;
    private View view;
    private CircleImageView circle_accounts;
    private Button btn_tuichu;
    private ImageView img_call,img_hander;
    private TextView txt_onename;
    private ReboundScrollView sl_view;

    // 记录首次按下位置
    private float mFirstPosition = 0;
    // 是否正在放大
    private Boolean mScaling = false;

    private DisplayMetrics metric;

    public static final int isNotUpdateWin = 0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        initData();
        /**
         * 头像初始化
         */
        if (ShareLocalUser.getInstance(getActivity()).getLoginState()) {
            ImageLoader.getInstance().displayImage(HttpConnector.HTTT_HOST + ShareLocalUser.getInstance(getActivity()).getLoginUser().UrlHeadPhoto, circle_accounts);
            txt_onename.setText(ShareLocalUser.getInstance(getActivity()).getLoginUser().Nickname);

        } else {
            circle_accounts.setImageResource(R.drawable.wodelg);
            txt_onename.setText("点击登录");
        }
        // 获取屏幕宽高
        metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        //设置图片初始大小  这里我设为满屏的16:9
        ViewGroup.LayoutParams lp = img_hander.getLayoutParams();
        lp.width = metric.widthPixels;
        lp.height = metric.widthPixels * 9 / 23;
        img_hander.setLayoutParams(lp);
        sl_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewGroup.LayoutParams lp = img_hander.getLayoutParams();
                switch (event.getAction()) {

                    case MotionEvent.ACTION_UP:
                        if (!mScaling) {
                            if (sl_view.getScrollY() == 0) {
                                mFirstPosition = event.getY();// 滚动到顶部时记录位置，否则正常返回
                            } else {
                                break;
                            }
                        }
                        int distanc = (int) ((event.getY() - mFirstPosition) * 0.6); // 滚动距离乘以一个系数
                        if (distanc < 0) { // 当前位置比记录位置要小，正常返回
                            break;
                        }
                        // 处理放大
                        mScaling = true;
                        lp.width = metric.widthPixels + distanc;
                        lp.height = (metric.widthPixels + distanc) * 9 / 23;
                        img_hander.setLayoutParams(lp);
                        mScaling = false;
                        replyImage();
                        return true; // 返回true表示已经完成触摸事件，不再处理
                    //手指离开后恢复图片

//                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (!mScaling) {
                            if (sl_view.getScrollY() == 0) {
                                mFirstPosition = event.getY();// 滚动到顶部时记录位置，否则正常返回
                            } else {
                                break;
                            }
                        }
                        int distance = (int) ((event.getY() - mFirstPosition) * 0.6); // 滚动距离乘以一个系数
                        if (distance < 0) { // 当前位置比记录位置要小，正常返回
                            break;
                        }
                        // 处理放大
                        mScaling = true;
                        lp.width = metric.widthPixels + distance;
                        lp.height = (metric.widthPixels + distance) * 9 / 23;
                        img_hander.setLayoutParams(lp);
                        return true; // 返回true表示已经完成触摸事件，不再处理

                }
                return false;
            }
        });
        return view;

    }
    public void replyImage() {
        final ViewGroup.LayoutParams lp =  img_hander.getLayoutParams();
        final float w = img_hander.getLayoutParams().width;// 图片当前宽度
        final float h = img_hander.getLayoutParams().height;// 图片当前高度
        final float newW = metric.widthPixels;// 图片原宽度
        final float newH = metric.widthPixels * 9 / 23;// 图片原高度

        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(0.0F, 1.0F).setDuration(200);

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                lp.width = (int) (w - (w - newW) * cVal);
                lp.height = (int) (h - (h - newH) * cVal);
                img_hander.setLayoutParams(lp);
            }
        });
        anim.start();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    @Override
    protected boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    /**
     * 初始化控件
     */
    private void initData() {
        circle_accounts = (CircleImageView) view.findViewById(R.id.circle_accounts);
        circle_accounts.setOnClickListener(this);

        rl_yijian = (RelativeLayout) view.findViewById(R.id.rl_yijian);
        rl_yijian.setOnClickListener(this);

        rl_wenti = (RelativeLayout) view.findViewById(R.id.rl_wenti);
        rl_wenti.setOnClickListener(this);


        rl_jiancha = (RelativeLayout) view.findViewById(R.id.rl_jiancha);
        rl_jiancha.setOnClickListener(this);


        rl_guanyu = (RelativeLayout) view.findViewById(R.id.rl_guanyu);
        rl_guanyu.setOnClickListener(this);

        rl_help_call = (RelativeLayout) view.findViewById(R.id.rl_help_call);
        rl_help_call.setOnClickListener(this);

        rl_yindao = (RelativeLayout) view.findViewById(R.id.rl_yindao);
        rl_yindao.setOnClickListener(this);

        rl_zhuanfa = (RelativeLayout) view.findViewById(R.id.rl_zhuanfa);
        rl_zhuanfa.setOnClickListener(this);

        btn_tuichu = (Button) view.findViewById(R.id.btn_tuichu);
        btn_tuichu.setOnClickListener(this);
        img_call = (ImageView) view.findViewById(R.id.img_call);
        img_call.setOnClickListener(this);

        rl_jingli = (RelativeLayout) view.findViewById(R.id.rl_jingli);
        rl_jingli.setOnClickListener(this);

        img_hander= (ImageView) view.findViewById(R.id.img_hander);

        sl_view= (ReboundScrollView) view.findViewById(R.id.sl_view);

        txt_onename= (TextView) view.findViewById(R.id.txt_onename);
        txt_onename.setOnClickListener(this);
    }

    public Intent intentY;
    String phonenum;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //余盆
            case R.id.circle_accounts:
                if (!ShareLocalUser.getInstance(getActivity()).getLoginState()) {
                    intentY = new Intent(getActivity(), LogonActivity.class);
                    startActivity(intentY);
                    Toast toast=Toast.makeText(getActivity(), "请登录!", Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.show();
                } else {
                    Intent intentYP = new Intent(getActivity(), AccountActivity.class);
                    startActivity(intentYP);
                }

                break;
            //意见反馈
            case R.id.rl_yijian:
                if (!ShareLocalUser.getInstance(getActivity()).getLoginState()) {
                    intentY = new Intent(getActivity(), LogonActivity.class);
                    startActivity(intentY);
                    Toast toast=Toast.makeText(getActivity(), "请登录!", Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.show();
                } else {
                    Intent yiIntent = new Intent(getActivity(), FeedBackActivity.class);
                    startActivity(yiIntent);
                }


                break;
            //帮助中心
            case R.id.rl_wenti:
                Intent intentC=new Intent(getActivity(),MatterActivity.class);
                startActivity(intentC);
                break;
            //检查更新
            case R.id.rl_jiancha:
                AsyncReqNewVersion asyncReqNewVersion = new AsyncReqNewVersion();
                ReqNewVersionBean bean = new ReqNewVersionBean();
                bean.mode = 0;
                bean.Url = HttpConnector.APP_UPDATE_VERSION_URL;
                asyncReqNewVersion.execute(bean);


                break;
            //关于余盆
            case R.id.rl_guanyu:
                Intent guanIntent = new Intent(getActivity(), AboutApp.class);
                startActivity(guanIntent);
                break;
            //客服电话
            case R.id.img_call:
                phonenum = this.getResources().getString(R.string.str_more_helpr_phone);
                ShowDialog.Builder builder = new ShowDialog.Builder(getActivity());
                builder.setTitle("拨打客服电话");
                builder.setImag(R.drawable.dianhua002);
                builder.setMessage("400-838-9595");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppInentUtils.callPhone(getActivity(), phonenum);
                        Toast toast=Toast.makeText(getActivity(), "请稍后...", Toast.LENGTH_SHORT);
                        toast .setGravity(Gravity.CENTER, 0, 10);
                        toast.show();
                        dialog.dismiss();
                    }
                });
                builder.createOneBtn(true).show();
                break;
            //安全退出
            case R.id.btn_tuichu:
                if (ShareLocalUser.getInstance(getActivity()).getLoginState()) {
                    ShowDialog.Builder builder1 = new ShowDialog.Builder(getActivity());
                    builder1.setTitle("提示");
                    builder1.setImag(R.drawable.logoout);
                    builder1.setMessage("安全退出");
                    builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            circle_accounts.setImageResource(R.drawable.wodelg);
                            txt_onename.setText("");
                            ShareLocalUser.getInstance(getActivity()).addLoginState(false);
                            ShareLocalUser.getInstance(getActivity()).addCookie("");
                            ShareLocalUser.getInstance(getActivity()).addUserIcon("");
                            Intent intent = new Intent(getActivity(), MFActivity.class);
                            startActivity(intent);
                             DiskCookieStore.INSTANCE.removeAll();
                            AppApplication.isLogin = false;
                            AppApplication.mchnt_cd=null;
                            AppApplication.user=null;
                            DiskCookieStore.INSTANCE.removeAll();
                            EventBus.getDefault().postSticky(new LoginedBean());
                            Toast toast = Toast.makeText(getActivity(),
                                    "安全退出", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            LinearLayout toastView = (LinearLayout) toast.getView();
                            ImageView imageCodeProject = new ImageView(getActivity());
                            imageCodeProject.setImageResource(R.drawable.dh001);
                            toastView.addView(imageCodeProject, 0);
                            AutoUtils.initLayoutSize(toastView,getActivity());
                            toast.show();
                            dialog.dismiss();

                        }
                    });
                    builder1.createOneBtn(true).show();
                } else {
                    Intent intent1 = new Intent(getActivity(), LogonActivity.class);
                    startActivity(intent1);

                }

                break;
            //客户经理
            case R.id.rl_jingli:
                if (ShareLocalUser.getInstance(getActivity()).getLoginState()) {
                    LoginedBean logUser = ShareLocalUser.getInstance(getActivity()).getLoginUser();
                    boolean haveCustomer = false;
                    if (logUser.CustomerManagerID == null || logUser.CustomerManagerID.length() == 0){

                    }else {
                        haveCustomer = true;
                    }
                    if (!haveCustomer) {//无客户经理
                        Intent startItemActivity = new Intent(getActivity(), CustomerManagerNoneActivity.class);//没有认证的客户经理
                        startActivity(startItemActivity);

                    } else {
                        Intent startItemActivity = new Intent(getActivity(), CustomerManagerDetialActivity.class);//已经认证的客户经理
                        startActivity(startItemActivity);

                    }
                } else {
                    Intent intentKH=new Intent(getActivity(),LogonActivity.class);
                    startActivity(intentKH);
                    Toast toast=Toast.makeText(getActivity(), "请登录!", Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.show();

                }
                break;
            //引导页面
            case R.id.rl_yindao:
                Intent intentYD = new Intent(getActivity(), PreStartActivity2.class);
                startActivity(intentYD);
                break;
            //转发分享
            case R.id.rl_zhuanfa:
                if (ShareLocalUser.getInstance(getActivity()).getLoginState()){
                    Intent intentZF = new Intent(getActivity(), ShareActivity.class);
                    startActivity(intentZF);
                }else {
                    Intent intentKH=new Intent(getActivity(),LogonActivity.class);
                    startActivity(intentKH);
                    Toast toast=Toast.makeText(getActivity(), "请登录!", Toast.LENGTH_SHORT);
                    toast .setGravity(Gravity.CENTER, 0, 10);
                    toast.getView();
                    toast.show();

                }

                break;
            /**
             * 点击用户名字
             */
            case R.id.txt_onename:
                if (txt_onename.getText().toString().equals("点击登录")){
                    txt_onename.setEnabled(true);
                    Intent intentON = new Intent(getActivity(), LogonActivity.class);
                    startActivity(intentON);

                }else {
                    txt_onename.setEnabled(false);
                }
            default:
                break;

        }

    }
    class ReqNewVersionBean {
        public String Url;
        public int mode;
    }
    WaitDialog waitDialog;
    /**
     * 异步请求 app 新版本
     */
    class AsyncReqNewVersion extends AsyncTask<ReqNewVersionBean, Integer, UpdateVersionBean> {
        ReqNewVersionBean bean;

        @Override
        protected void onPreExecute() {
            waitDialog=new WaitDialog(getActivity());
            waitDialog.show();
        }

        @Override
        protected UpdateVersionBean doInBackground(ReqNewVersionBean[] params) {
            bean = params[0];
            UpdateVersionBean reqBody = MyFinancialWeb.getInstance().getNewServiceVersion(getActivity(), bean.Url);
            return reqBody;
        }

        @Override
        protected void onPostExecute(UpdateVersionBean versionBean) {
            if (waitDialog != null) {
                waitDialog.dismiss();
                waitDialog = null;
            }

            try {
                if (versionBean == null) {
                    ToastUtils.show(getActivity(), R.string.str_eror_net_update, Toast.LENGTH_SHORT);
                } else {
                    if (bean.mode == isNotUpdateWin) {
                        String vstr = GsonUtils.getGson().toJson(versionBean);

                        Intent intent = new Intent(getActivity(), ValidatorVersion.class);
                        intent.putExtra(ValidatorVersion.INTENT_VERSION_OBJECT, vstr);
                        startActivity(intent);

                    } else {
                        int thisversion = AppUtils.getVersionCode(getActivity());
                        if (versionBean.Code > thisversion) {
                            Intent intent = new Intent(getActivity(), ValidatorVersion.class);
                            String vstr2 = GsonUtils.getGson().toJson(versionBean);
                            intent.putExtra(ValidatorVersion.INTENT_VERSION_OBJECT, vstr2);
                            startActivity(intent);
                        }
                    }
                }

            } catch (Exception e) {
            }
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        setAccountByStart();
        if (ShareLocalUser.getInstance(getActivity()).getLoginState()) {
            ImageLoader.getInstance().displayImage(HttpConnector.HTTT_HOST + ShareLocalUser.getInstance(getActivity()).getLoginUser().UrlHeadPhoto, circle_accounts);
            txt_onename.setText(ShareLocalUser.getInstance(getActivity()).getLoginUser().Nickname);

        } else {
            circle_accounts.setImageResource(R.drawable.wodelg);
            txt_onename.setText("点击登录");
        }
    }

    private void setAccountByStart() {
        LoginedBean logUser = ShareLocalUser.getInstance(getActivity()).getLoginUser();
        boolean logined = ShareLocalUser.getInstance(getActivity()).getLoginState();
        if (logUser != null && logined) {
            btn_tuichu.setText("安全退出");
        } else {
            btn_tuichu.setText("立即登录");
        }

        if (logUser != null && logined) {
            if (logUser.LoginName.length() > 0) {
                txt_onename.setText(logUser.Nickname);
                ImageLoader.getInstance().displayImage(HttpConnector.HTTT_HOST + ShareLocalUser.getInstance(getActivity()).getLoginUser().UrlHeadPhoto, circle_accounts);
            }

        } else {
            circle_accounts.setImageResource(R.drawable.wodelg);
            txt_onename.setText("点击登录");
        }
    }
}
