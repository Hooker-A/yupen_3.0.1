package com.huaop2p.yqs.module.three_mine.activity.dataactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.WaitDialog;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.base.view.ClearEditText;
import com.huaop2p.yqs.module.logon.entity.LoginedBean;
import com.huaop2p.yqs.module.three_mine.adapter.SortAdapter;
import com.huaop2p.yqs.module.three_mine.db.LandDivide;
import com.huaop2p.yqs.module.three_mine.db.LandDivideDB;
import com.huaop2p.yqs.module.three_mine.db.PinyinComp;
import com.huaop2p.yqs.module.three_mine.model.entity.CitySortModel;
import com.huaop2p.yqs.widget.SideBar;
import com.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zgq on 2016/6/14.
 * 作者:  zhu  guo qing
 * 时间:  2016/6/14 11:37
 * 功能: 测试而已别当真
 */
public class CeshiActivity extends BaseActivity implements OnClickListener{
    public LocationClient mLocationClient = null;
    private String city;
    private String direction;
    private ListView mListView1;
    private ListView mListView2;
    private ListView mListView3;
    List<CitySortModel> mSortList = new ArrayList<>();
    private SortAdapter adapter;
    private LinearLayout mLinearLayout1;
    private LinearLayout ll_layout;
    private LinearLayout mLinearLayout2;
    private LinearLayout mLinearLayout3;
    private WaitDialog waitDialog;

    private List<String> sheng = new ArrayList<String>();
    private List<String> shi = new ArrayList<String>();
    private List<String> qu = new ArrayList<String>();

    private ArrayAdapter<String> shengAdapter;
    private ArrayAdapter<String> shiAdapter;
    private ArrayAdapter<String> quAdapter;
    private SideBar city_ctionbar;
    private TextView shengTxt2;
    private TextView shengTxt3, shiTxt3, topView3, my_set_adresschoose_qu_3;
    private ImageView img_weizzhib;
    private static String shengStr, shiStr, quStr;
    private LandDivideDB landDivideDB;

    private ClearEditText txt_area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceshi);
        SetActivityTitle("所在地区");
        waitDialog=new WaitDialog(CeshiActivity.this);

        mLocationClient = new LocationClient(getApplicationContext());
        MyLocationListener myListener = new MyLocationListener();//声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();

        initData();
        shengTxt3.setText("定位中...");
        landDivideDB = LandDivideDB.getInstance(getBaseContext());
        List<LandDivide> landDivide = landDivideDB.queryAddress("superior=?", new String[]{"1"});
        Iterator<LandDivide> iterator = null;
        if (landDivide != null) {
            iterator = landDivide.iterator();

            while (iterator.hasNext()) {
                LandDivide l = iterator.next();
                sheng.add(l.getName());
            }
        } else {
            return;
        }

        shengAdapter = new ArrayAdapter(this, R.layout.my_set_addresschoose_listview_item, R.id.my_set_adresschoose_textview, sheng);
//        adapter=new SortAdapter(this,mSortList);
        shiAdapter = new ArrayAdapter(this, R.layout.my_set_addresschoose_listview_item, R.id.my_set_adresschoose_textview, shi);
        quAdapter = new ArrayAdapter(this, R.layout.my_set_addresschoose_listview_item, R.id.my_set_adresschoose_textview, qu);
        mListView1 = (ListView) this.findViewById(R.id.my_set_adresschoose_listview_1);
        mListView1.setAdapter(shengAdapter);
        mListView2 = (ListView) this.findViewById(R.id.my_set_adresschoose_listview_2);
        mListView2.setAdapter(shiAdapter);
        mListView3 = (ListView) this.findViewById(R.id.my_set_adresschoose_listview_3);
        mListView3.setAdapter(quAdapter);
        img_weizzhib= (ImageView) findViewById(R.id.img_weizzhib);
//        shengTxt2.setOnClickListener(click);
        shengTxt3.setOnClickListener(click);
        shiTxt3.setOnClickListener(click);

        mListView1.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                mLinearLayout1.setVisibility(View.GONE);
                mLinearLayout2.setVisibility(View.VISIBLE);
                mLinearLayout3.setVisibility(View.GONE);

                shi.clear();
                String name = sheng.get(position);
                String code = null;
                shengStr = name;
                shiTxt3.setText(name);
                shengTxt3.setVisibility(View.GONE);


                List<LandDivide> landDivide = landDivideDB.queryAddress("name=?", new String[]{name});
                Collections.sort(landDivide, new PinyinComp());
                Iterator<LandDivide> iterator = landDivide.iterator();
                while (iterator.hasNext()) {
                    LandDivide l = iterator.next();
                    code = l.getCode();
                    break;
                }

                List<LandDivide> landDivide_2 = landDivideDB.queryAddress("superior=?", new String[]{code});
                Iterator<LandDivide> iterator_2 = landDivide_2.iterator();
                while (iterator_2.hasNext()) {
                    LandDivide l = iterator_2.next();
                    shi.add(l.getName());
                }

                shiAdapter.notifyDataSetChanged();
                quAdapter.clear();
                quAdapter.notifyDataSetChanged();
            }
        });

        mListView2.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                qu.clear();
                String name = shi.get(position);
                String code = null;

                shiStr = name;
                shiTxt3.setText(shengStr + " " + name);

                List<LandDivide> landDivide = landDivideDB.queryAddress("name=?", new String[]{name});

                Iterator<LandDivide> iterator = landDivide.iterator();
                while (iterator.hasNext()) {
                    LandDivide l = iterator.next();
                    code = l.getCode();
                    break;
                }
                List<LandDivide> landDivide_2 = landDivideDB.queryAddress("superior=?", new String[]{code});
                if (landDivide_2 != null) {
                    Iterator<LandDivide> iterator_2 = landDivide_2.iterator();
                    while (iterator_2.hasNext()) {
                        LandDivide l = iterator_2.next();
                        qu.add(l.getName());
                    }
                }
                quAdapter.notifyDataSetChanged();

                if (qu.size() < 1) {
                    mLinearLayout1.setVisibility(View.GONE);
                    mLinearLayout2.setVisibility(View.VISIBLE);
                    mLinearLayout3.setVisibility(View.GONE);


                } else {
                    mLinearLayout1.setVisibility(View.GONE);
                    mLinearLayout2.setVisibility(View.GONE);
                    mLinearLayout3.setVisibility(View.VISIBLE);
                    mListView3.setVisibility(View.VISIBLE);
                }
            }
        });

        mListView3.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                waitDialog.show();
                String name = qu.get(position);
                quStr = name;
                my_set_adresschoose_qu_3.setText(name);
                LoginedBean bean = ShareLocalUser.getInstance(CeshiActivity.this).getLoginUser();
                bean.Address = shengStr+" "+shiStr+" "+name;
                ShareLocalUser.getInstance(CeshiActivity.this).addLoginUser(bean);
                waitDialog.dismiss();
                finish();
            }
        });

    }

    private void initData() {
        mLinearLayout1 = (LinearLayout) this.findViewById(R.id.my_set_adresschoose_1);
        mLinearLayout2 = (LinearLayout) this.findViewById(R.id.my_set_adresschoose_2);
        mLinearLayout3 = (LinearLayout) this.findViewById(R.id.my_set_adresschoose_3);

        mLinearLayout1.setVisibility(View.VISIBLE);
        mLinearLayout2.setVisibility(View.GONE);
        mLinearLayout3.setVisibility(View.GONE);


//        shengTxt2 = (TextView) this.findViewById(R.id.my_set_adresschoose_sheng_2);
        shengTxt3 = (TextView) this.findViewById(R.id.my_set_adresschoose_sheng_3);
        shiTxt3 = (TextView) this.findViewById(R.id.my_set_adresschoose_shi_3);
        ll_layout= (LinearLayout) findViewById(R.id.ll_layout);
        ll_layout.setOnClickListener(this);
        my_set_adresschoose_qu_3 = (TextView) findViewById(R.id.my_set_adresschoose_qu_3);
    }

    OnClickListener click = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.my_set_adresschoose_sheng_3:
                    mLinearLayout1.setVisibility(View.VISIBLE);
                    mLinearLayout2.setVisibility(View.GONE);
                    mLinearLayout3.setVisibility(View.GONE);
                    break;
                case R.id.my_set_adresschoose_shi_3:
                    mLinearLayout1.setVisibility(View.GONE);
                    mLinearLayout2.setVisibility(View.VISIBLE);
                    mLinearLayout3.setVisibility(View.GONE);
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_layout:
                String ding=shengTxt3.getText().toString();
                LoginedBean bean = ShareLocalUser.getInstance(CeshiActivity.this).getLoginUser();
                bean.Address = ding;
                ShareLocalUser.getInstance(CeshiActivity.this).addLoginUser(bean);
                finish();
                break;
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                city = location.getCity();//市
                direction = location.getDistrict();//区
                LogUtils.e(direction+"------------direction---------------");

                if (city==null || direction==null){
                    img_weizzhib.setImageResource(R.drawable.weizhi001);
                    shengTxt3.setText("定位失败!");
                }else {
                    img_weizzhib.setImageResource(R.drawable.weizhi011);
                    shengTxt3.setText(city +" "+direction);
                }


            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                city = location.getCity();
                direction = location.getDistrict();
                if (city==null || direction==null){
                    img_weizzhib.setImageResource(R.drawable.weizhi001);
                    shengTxt3.setText("定位失败!");
                }else {
                    img_weizzhib.setImageResource(R.drawable.weizhi011);
                    shengTxt3.setText(city +" "+ direction);
                }
//                SPUtil.getInstance(AddressActivity.this).save("localcity",city);
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                // sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                // sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                // sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                // sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
        }
    }
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
    @Override
    protected void onStop() {
        super.onStop();
        // 停止定位
        mLocationClient.stop();
    }
}
