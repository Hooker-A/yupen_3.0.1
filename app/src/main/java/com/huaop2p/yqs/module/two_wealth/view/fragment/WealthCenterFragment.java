package com.huaop2p.yqs.module.two_wealth.view.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.view.BaseAutoFragment;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.three_mine.model.entity.Borrower;
import com.huaop2p.yqs.module.two_wealth.activity.BorrowerInfoActivity;
import com.huaop2p.yqs.module.two_wealth.activity.WealthCenterDetailActivity;
import com.huaop2p.yqs.module.two_wealth.adapter.ViewPagerAdapter;
import com.huaop2p.yqs.module.two_wealth.adapter.WealthAdapter;
import com.huaop2p.yqs.module.two_wealth.model.entity.WealthCenter;
import com.huaop2p.yqs.module.two_wealth.presenter.impl.WealthCenterPresenterImpl;
import com.huaop2p.yqs.module.two_wealth.view.IWealthCenterView;
import com.huaop2p.yqs.utils.DensityUtil;
import com.huaop2p.yqs.utils.ToastUtils;
import com.huaop2p.yqs.utils.auto.AutoUtils;
import com.huaop2p.yqs.widget.CustomViewPager;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshBase;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshListView;
import com.huaop2p.yqs.widget.scroll.FixedSpeedScroller;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/4/11.
 */
public class WealthCenterFragment extends BaseAutoFragment<BaseResponseEntity<List<WealthCenter>>> implements IWealthCenterView<WealthCenter> ,View.OnClickListener{
    @ViewInject(R.id.mListView)
    private PullToRefreshListView mListView;
    private WealthAdapter adapter;
    private BaseResponseEntity<List<WealthCenter>> yuXinBao;
    private WealthCenterPresenterImpl wealthCenterPresenter;
    private Map<String, Object> map;
    private String type;  //  type=1：房 type=2：车  type=3：信
    private int pageIndex;
    private final static int PAGESIZE = 10;
    private CustomViewPager vp;
    private List<View> listViews;
    private StringBuffer sb;
    private ImageView iv_right;
    private int position;
    private Runnable runnable;

    @Override
    public void showData() {
        if (adapter == null) {
            type = getArguments().getString("type");
            wealthCenterPresenter = new WealthCenterPresenterImpl(this, Integer.valueOf(type));
            setLinstener();
            map = new LinkedHashMap<>();
            adapter = new WealthAdapter(null, getActivity(), R.layout.item_yuxinbao, Integer.valueOf(type));
            mListView.getRefreshableView().setAdapter(adapter);
            addHeadViewByType();
            Map<String, Object> childMap = new LinkedHashMap<String, Object>();
            childMap.put("Type", type);
            map.put("AppointEntity", childMap);
            map.put("PageSize", PAGESIZE);
            mListView.setRefreshing();
        }

    }

    private void addHeadViewByType() {
        if (type.equals(String.valueOf(State.YUXINBAO))) {
            if (listViews == null) {
                listViews = new ArrayList<>();
                sb = new StringBuffer();
            }
            View view = getActivity().getLayoutInflater().inflate(R.layout.head_carousel_viewpager, null);
            iv_right = (ImageView) view.findViewById(R.id.iv_right);
            vp = (CustomViewPager) view.findViewById(R.id.vp);
            AutoUtils.auto(vp);
            mListView.getRefreshableView().addHeaderView(view);
            wealthCenterPresenter.loadBorrowerInfo(null);
            try {
                Field field = ViewPager.class.getDeclaredField("mScroller");
                field.setAccessible(true);
                FixedSpeedScroller scroller = new FixedSpeedScroller(vp.getContext(),
                        new AccelerateInterpolator());
                field.set(vp, scroller);
                scroller.setmDuration(3000);
            } catch (Exception e) {
            }
            iv_right.setOnClickListener(this);
            switchVP();
        }
        ;
    }

    private void switchVP() {
        if (runnable == null) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    if (getParentFragment()!=null&&!getParentFragment().isHidden() &&getActivity()!=null&& !((BaseActivity) getActivity()).isStop && isVisible()) {
                        position++;
                        if (position % 5 == listViews.size()) {
                            vp.setCurrentItem(0);
                        } else {
                            vp.setCurrentItem(position);
                        }
                    }
                    vp.postDelayed(this, 5000);
                }
            };
        }
        vp.postDelayed(runnable, 3000);
    }

    private void setLinstener() {
        if (Integer.valueOf(type) != State.YUXINBAO)
            mListView.setMode(PullToRefreshBase.Mode.BOTH);
        else
            mListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                pageIndex = 1;
                wealthCenterPresenter.setPageIndex(pageIndex);
                map.put("PageIndex", String.valueOf(pageIndex));
                try {
                    wealthCenterPresenter.loadData(map, HttpUrl.POST_GET_YUFANG_LIST, 0, RequestMethod.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                map.put("PageIndex", String.valueOf(++pageIndex));
                try {
                    wealthCenterPresenter.setPageIndex(pageIndex);
                    wealthCenterPresenter.loadData(map, HttpUrl.POST_GET_YUFANG_LIST, 0, RequestMethod.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position = position - 1;
                if (type.equals(String.valueOf(State.YUXINBAO)))
                    position = position - 1;
                loadDetailView(adapter.getItem(position));
            }
        });
    }

    @Override
    public View initViews(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_yuxinbao, null);
        return view;
    }

    @Override
    public void loadDetailView(final WealthCenter wealthCenter) {
        Intent intent = new Intent(getActivity(), WealthCenterDetailActivity.class);
        intent.putExtra("type", Integer.valueOf(type));
        intent.putExtra("id", wealthCenter.KeyId);
        startActivity(intent);
        EventBus.getDefault().postSticky(wealthCenter);
    }

    @Override
    public void clearData() {
        adapter.refresh(null);
        if (yuXinBao != null)
            yuXinBao.ReturnMessage.clear();
    }

    @Override
    public List<WealthCenter> getData() {
        if (yuXinBao == null)
            return null;
        return yuXinBao.ReturnMessage;
    }

    @Override
    public void setData(List<WealthCenter> wealthCenters) {
        if (yuXinBao == null) {
            adapter.refresh(wealthCenters);
            return;
        }
        this.yuXinBao.ReturnMessage.addAll(wealthCenters);
        adapter.refresh(this.yuXinBao.ReturnMessage);
    }

    @Override
    public void showBorrowerInfo(List<String> bs) {
        if (!isAdded())
            return;
        listViews.clear();
        Resources res = getResources();
        Drawable img_off = res.getDrawable(R.drawable.jiekuanren);
        img_off.setBounds(0, 0, img_off.getMinimumWidth() - 20, img_off.getMinimumHeight() - 20);
        for (int i = 0; i < bs.size(); i++) {
            String b = bs.get(i);
            TextView tv = new TextView(getActivity());
            ViewPager.MarginLayoutParams layoutParams = new ViewPager.MarginLayoutParams(ViewPager.MarginLayoutParams.MATCH_PARENT, ViewPager.MarginLayoutParams.MATCH_PARENT);
            tv.setLayoutParams(layoutParams);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.small_content_size));
            tv.setCompoundDrawables(img_off, null, null, null); //设置左图标
            tv.setCompoundDrawablePadding(DensityUtil.dip2px(getActivity()
                    , 10));
            tv.setOnClickListener(this);
            sb.append(b);
            tv.setText(sb);
            sb.delete(0, sb.length());
            AutoUtils.auto(tv);
            listViews.add(tv);
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(listViews,true);
        vp.setAdapter(adapter);
    }


    /**
     * 加载数据之前
     **/
    @Override
    public void startLoadData() {

    }

    /**
     * 加载数据之后
     **/
    @Override
    public void loadDataOver() {
        mListView.stopLoadOrRefresh();
    }


    /***
     * 填充數據
     **/
    @Override
    public void showSuccess(BaseResponseEntity<List<WealthCenter>> yuXinBaos) {
        if (yuXinBaos.Total.equals("0") || yuXinBaos.ReturnMessage.size() == 0) {
            ToastUtils.show(AppApplication.mContext, "没有数据了");
            return;
        }
        if (this.yuXinBao == null) {
            adapter.refresh(yuXinBaos.ReturnMessage);
            this.yuXinBao = yuXinBaos;
            return;
        }
        setData(yuXinBaos.ReturnMessage);
    }

    /***
     * 顯示錯誤
     **/
    @Override
    public void showError(int errorCode, String str) {
        ToastUtils.show(AppApplication.mContext, str);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), BorrowerInfoActivity.class);
        startActivity(intent);
    }
}
