package com.huaop2p.yqs.module.three_mine.activity.dataactivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.three_mine.adapter.SortAdapter;
import com.huaop2p.yqs.module.three_mine.model.entity.CitySortModel;
import com.huaop2p.yqs.module.three_mine.model.entity.PinyinComparator;
import com.huaop2p.yqs.utils.PinyinUtils;
import com.huaop2p.yqs.widget.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zgq on 2016/5/10.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/10 10:58
 * 功能:  所在地区
 */
public class AreaActivity extends BaseActivity {
    private TextView txt_area;
    public LocationClient mLocationClient = null;
    private String city;
    public static String CITY;

    // 县级地域信息
    private SideBar sideBar;
    private List<CitySortModel> SourceDateList;
    private SortAdapter adapter;
    List<CitySortModel> mSortList = new ArrayList<>();

    private String direction;
    private ListView mCityLit;
    private TextView txt_title, dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
        SetActivityTitle("所在地区");
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_area = (TextView) findViewById(R.id.txt_area);

        txt_area.setText("定位中...");
        mLocationClient = new LocationClient(getApplicationContext());
        MyLocationListener myListener = new MyLocationListener();//声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();
        initViews();

    }

    private void initViews() {
        sideBar = (SideBar) findViewById(R.id.city_loactionbar);
        dialog = (TextView) findViewById(R.id.dialog);
        mCityLit = (ListView) findViewById(R.id.city_list);
        initDatas();
        initEvents();
        setAdapter();
    }

    private void setAdapter() {
        SourceDateList = filledData(getResources().getStringArray(R.array.provinces));
        Collections.sort(SourceDateList, new PinyinComparator());
        adapter = new SortAdapter(this, SourceDateList);
        mCityLit.setAdapter(adapter);
    }

    private void initEvents() {
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mCityLit.setSelection(position);
                }
            }
        });
        //ListView的点击事件
        mCityLit.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                txt_area.setText(((CitySortModel) adapter.getItem(position)).getName());
                Toast.makeText(getApplication(), ((CitySortModel) adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        //根据输入框输入值的改变来过滤搜索

        txt_area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                String voel = txt_area.getText().toString();
                int key = voel.indexOf("市");

                if (key != -1) {
                    String vv = voel.substring(0, key);
                    filterData(vv);
                }else{
                    filterData(voel);
                }
            }
        });
    }

    private void initDatas() {
        sideBar.setTextView(dialog);
    }


    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {

        // txt_area.getText().toString().contains(filterStr)
        if (TextUtils.isEmpty(filterStr)) {//contain
            mSortList = SourceDateList;
        } else {
            mSortList.clear();
            for (CitySortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.toUpperCase().indexOf(filterStr.toString().toUpperCase()) != -1 || PinyinUtils.getPingYin(name).toUpperCase().startsWith(filterStr.toString().toUpperCase())) {
                    mSortList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(mSortList, new PinyinComparator());
        adapter.updateListView(mSortList);
    }

    private List<CitySortModel> filledData(String[] date) {
        List<CitySortModel> mSortList = new ArrayList<>();
        ArrayList<String> indexString = new ArrayList<>();

        for (int i = 0; i < date.length; i++) {
            CitySortModel sortModel = new CitySortModel();
            sortModel.setName(date[i]);
            String pinyin = PinyinUtils.getPingYin(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
                if (!indexString.contains(sortString)) {
                    indexString.add(sortString);
                }
            }
            mSortList.add(sortModel);
        }
        Collections.sort(indexString);
        sideBar.setIndexText(indexString);
        return mSortList;
    }


    @Override
    protected void onStop() {
        super.onStop();
        // 停止定位
        mLocationClient.stop();
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

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                city = location.getCity();//市
                direction = location.getDistrict();//区
                txt_area.setText(city + direction);
                CITY = direction;
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                city = location.getCity();
                direction = location.getDistrict();
                txt_area.setText(city + direction);
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


}
