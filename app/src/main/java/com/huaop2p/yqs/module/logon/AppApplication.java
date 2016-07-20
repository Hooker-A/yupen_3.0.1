package com.huaop2p.yqs.module.logon;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.linstener.EventBusLinstener;
import com.huaop2p.yqs.module.base.model.BaseModel;
import com.huaop2p.yqs.module.four_set.service.MQTTService;
import com.huaop2p.yqs.module.logon.entity.LoginedBean;
import com.huaop2p.yqs.module.three_mine.model.entity.SiteBean;
import com.huaop2p.yqs.utils.auto.AutoLayoutConifg;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.DbUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import de.greenrobot.event.EventBus;

/**
 * Created by yindongli on 2015/1/15.
 */
public class AppApplication extends Application implements EventBusLinstener<LoginedBean>{
    private List<Activity> activitys = new LinkedList<Activity>();
    public static Context mContext;
    public static boolean isActive;
    public static AppApplication appInstance;
    public  static DbUtils dbUtils;
    public  static  boolean isLogin=false;
    public  static LoginedBean user;
    public  static SiteBean siteBean;
    public  static String mchnt_cd;
    private   BaseModel<String> baseModel=new BaseModel<>();
    private SharedPreferences preferences;
    public static Date serverDate;
    /**
     * 管理身份认证完成时退出
     */
    private Stack<Activity> accountStack;

    public Stack<Activity> getAccountStack() {
        if (accountStack == null) {
            accountStack = new Stack<Activity>();
        }
        return accountStack;
    }
    public static AppApplication getInstance() {
        return appInstance;
    }


    /**
     * 释放身份认证栈
     */

    public void disposeAccountStack() {
        if (accountStack != null) {
            for (int i = 0; i < accountStack.size(); i++) {
                accountStack.remove(i);
            }
            accountStack = null;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
       if (!EventBus.getDefault().isRegistered(this))
           EventBus.getDefault().register(this);
        NoHttp.init(this);
        dbUtils=DbUtils.create(this);
        AutoLayoutConifg.getInstance().init(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 推送服务
                Intent svc = new Intent(getApplicationContext(), MQTTService.class);
//                startService(svc);
            }
        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent =new Intent(getApplicationContext(),RabbitmqService.class);
//                startService(intent);
//            }
//        }).start();

        // 初始化UIl
        initImageLoaderConfig();
        mContext =this;

//        闪退，打log
//        CrashHandler crashHandler=CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
//        Thread.setDefaultUncaughtExceptionHandler(crashHandler);

    }

    // 添加activity到容器中
    public void addActivity(Activity activity) {
        if (activitys != null && activitys.size() > 0) {
            if (!activitys.contains(activity)) {
                activitys.add(activity);
            }
        } else {
            activitys.add(activity);
        }
    }

    /**
     * 获取 堆头
     *
     * @return
     */
    public Activity getLastActivity() {
        if (activitys != null)
            return activitys.get(activitys.size() - 1);
        return null;
    }

    /**
     * 移除activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activitys != null && activitys.size() > 0)
            if (activitys.contains(activity))
                activitys.remove(activity);
    }

    // 遍历所有Activity并finish
    public void exit() {
        if (activitys != null && activitys.size() > 0) {
            for (int i = 0; i < activitys.size(); i++) {
                if (activitys.get(i) != null)
                    activitys.get(i).finish();
            }
            activitys.retainAll(activitys);
        }
        System.exit(0);
    }

    public void initImageLoaderConfig() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }

    //利用imageloader加载默认图片
    public DisplayImageOptions getOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.bannerholder)
                .showImageForEmptyUri(R.drawable.bannerholder)
                .showImageOnFail(R.drawable.bannerholder)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .build();
        return options;
    }

    /**
     * 获取context
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }


    @Override
    public void onEventMainThread(LoginedBean loginedBean) {
      if (isLogin){
          baseModel.loadDetailById(null, new OnRequestLinstener<BaseResponseEntity<String>>() {
              @Override
              public void success(BaseResponseEntity<String> entity) {
                  mchnt_cd=entity.ReturnMessage;
                //  mchnt_cd="0002900F0041270";
              }
              @Override
              public void error(int code, String error) {
                  mchnt_cd=null;
              }
          }, HttpUrl.GET_COMMERCE_NUM,0, RequestMethod.POST,new TypeToken<BaseResponseEntity<String>>(){});
      }
    }
}
