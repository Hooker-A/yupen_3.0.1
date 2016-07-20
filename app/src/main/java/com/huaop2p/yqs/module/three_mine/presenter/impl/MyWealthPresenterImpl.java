package com.huaop2p.yqs.module.three_mine.presenter.impl;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.three_mine.model.entity.Balance;
import com.huaop2p.yqs.module.three_mine.model.entity.Investment;
import com.huaop2p.yqs.module.three_mine.model.entity.MyWealth;
import com.huaop2p.yqs.module.three_mine.model.impl.MyWealthModelImpl;
import com.huaop2p.yqs.module.three_mine.presenter.IMyWealthPresenter;
import com.huaop2p.yqs.module.three_mine.task.MyWealthTask;
import com.huaop2p.yqs.module.three_mine.view.fragment.CusWealthFragment;
import com.huaop2p.yqs.module.two_wealth.assist.BaseCalculator;
import com.huaop2p.yqs.utils.DateUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

/**
 * Created by Administrator on 2016/4/13.
 */
public class MyWealthPresenterImpl extends BasePresenter implements IMyWealthPresenter {
    private CusWealthFragment myWealthView;
    private MyWealthModelImpl myWealthModel;
    private Timer timer;
    private MyWealthTask myWealthTask;
    private boolean isStop;
    private Date serverDate;
    private double time;
    private List<List<Investment>> investments;
    private int index;

    public MyWealthPresenterImpl(CusWealthFragment myWealthView) {
        super();
        this.myWealthView = myWealthView;
        myWealthModel = new MyWealthModelImpl();
        investments = new ArrayList<>();
        Collections.synchronizedCollection(investments);

    }


    @Override
    public void loadDetail(Map<String, Object> map, String url, int what, RequestMethod method) {
        if (!AppApplication.isLogin) {
            myWealthView.openShade();
            myWealthView.clearUiData();
            return;
        } else {
            myWealthView.closeShade();
        }
        getBalance();
        selectIsSign();
        myWealthModel.loadDetailById(map, new OnRequestLinstener<BaseResponseEntity<MyWealth>>() {
            @Override
            public void success(BaseResponseEntity<MyWealth> myWealth) {
                myWealthView.showSuccess(myWealth);
                clearTimer();
                if (timer == null)
                    timer = new Timer();
                if (!myWealthView.isHidden() && !isStop) {
                    AppApplication.serverDate = new Date();
                    startTask();
                }

            }

            @Override
            public void error(int code, String error) {
                myWealthView.showError(code, error);

            }
        }, url, what, method, new TypeToken<BaseResponseEntity<MyWealth>>() {
        });
    }

    /***
     * 签到
     **/
    @Override
    public void sign() {
        if (!ShareLocalUser.getInstance(AppApplication.mContext).isLogined(true)) {
            return;
        }
        myWealthModel.sign(new OnRequestLinstener<BaseResponseEntity<String>>() {
            @Override
            public void success(BaseResponseEntity<String> entity) {
                if (entity.ReturnStatus.equals("0")) {
                    myWealthView.signSuccess("积分+" + entity.ReturnMessage);
                } else {
                    myWealthView.signError("已签到");
                }
            }

            @Override
            public void error(int code, String error) {
                myWealthView.signError(error);
            }
        });
    }

    /***
     * 获取余额
     **/
    @Override
    public void getBalance() {
        myWealthModel.getBalance(new OnRequestLinstener<BaseResponseEntity<List<Balance>>>() {
            @Override
            public void success(BaseResponseEntity<List<Balance>> o) {
                if (o == null || o.ReturnMessage == null || o.ReturnMessage.get(0) == null) {
                    myWealthView.setBalance(null);
                } else {
                    myWealthView.setBalance(o.ReturnMessage.get(0));
                }
            }

            @Override
            public void error(int code, String error) {
                myWealthView.setBalance(null);
            }
        });
    }

    /***
     * 查询是否签到
     */
    @Override
    public void selectIsSign() {
        myWealthModel.selectIsSign(new OnRequestLinstener<BaseResponseEntity<String>>() {
            @Override
            public void success(BaseResponseEntity<String> o) {
                if (o != null)
                    myWealthView.updateSignButton(o.ReturnMessage);
                else
                    myWealthView.updateSignButton("flase");
            }

            @Override
            public void error(int code, String error) {
                myWealthView.updateSignButton("flase");
            }
        });
    }

    @Override
    public void getTotalMoney() {
        investments.clear();
        for (int i = 1; i <= 3; i++) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("Type", String.valueOf(i));
            myWealthModel.loadDatas(paramMap, new OnRequestLinstener<BaseResponseEntity<List<Investment>>>() {
                @Override
                public void success(BaseResponseEntity<List<Investment>> investment) {
                    MyWealthPresenterImpl.this.investments.add(investment.ReturnMessage);
                    jisuan();
                }

                @Override
                public void error(int code, String error) {
                    Log.i("aaa", "aaa" + error);
                }
            }, HttpUrl.GET_INVERSTMENT_RECORD, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity<List<Investment>>>() {
            });
        }
    }

    public void jisuan() {
        if (investments.size() != 3)
            return;
        //  final double[] total = {0};
        final double[] total_shouyi = {0};
        for (int p = 0; p < investments.size(); p++) {
            for (int i = 0; i < investments.get(p).size(); i++) {
                Investment in = investments.get(p).get(i);
                double value = BaseCalculator.getClaculator().multiply(in.TrueRate, Double.valueOf(in.LoanMoney));      //每天获得的钱
                double NowDay = 0;
                double totalDay = 0;
                if (!(in.StartTime == null || in.DateTimeNow == null || in.EndTime == null)) {
                    try {
                        Date dateStart = DateUtils.sdf.parse(in.StartTime);
                        Date serverDate = DateUtils.sdf.parse(in.DateTimeNow);
                        Date dateEnd = DateUtils.sdf.parse(in.EndTime);
                        NowDay = DateUtils.subtract1(serverDate, dateStart);
                        NowDay += time;
                        totalDay = DateUtils.subtract1(dateEnd, dateStart);
                        total_shouyi[0] += BaseCalculator.getClaculator().divide(BaseCalculator.getClaculator().multiply(NowDay, value), totalDay);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                //total[0] += value * NowDay;
            }
        }
        myWealthView.setTotalMoney(String.valueOf(total_shouyi[0]));
        if (investments.size() == 3) {
            time = DateUtils.subtract1(new Date(), AppApplication.serverDate);
        }
    }


    public void startTask() {
        if (AppApplication.isLogin && timer != null && myWealthTask == null) {
            myWealthTask = new MyWealthTask(MyWealthPresenterImpl.this, myWealthView.getActivity());
            timer.schedule(myWealthTask, 0, 5000);
            isStop = false;
        }
    }

    public void stopTask() {
        if (myWealthTask != null) {
            myWealthTask.cancel();
            myWealthTask = null;
            isStop = true;
        }
    }

    public void clearTimer() {
        if (myWealthTask != null)
            myWealthTask.cancel();
        myWealthTask = null;
    }

}

