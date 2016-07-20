package com.huaop2p.yqs.module.logon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.activity.BaseActivity;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.logon.adapter.AbstractWheelTextAdapter;
import com.huaop2p.yqs.module.logon.entity.FuiouChinaAreaBean;
import com.huaop2p.yqs.module.logon.model.MyFinancialWeb;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.huaop2p.yqs.widget.bank.OnWheelChangedListener;
import com.huaop2p.yqs.widget.bank.WheelView;
import com.lidroid.xutils.util.LogUtils;
import com.huaop2p.yqs.R;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgq on 2016/5/26.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/26 15:04
 * 功能: 城市选择器
 */
public class SelectProvinceAreaWheelActivity extends BaseActivity{
    public static final String RETURN_SELECT_PROVINCE = "RETURN_SELECT_PROVINCE";
    public static final String RETURN_SELECT_AREA = "RETURN_SELECT_AREA";


    public static final String INTENT_DEFAULT_PROFINCE_KEY = "INTENT_DEFAULT_PROFINCE_KEY";
    public static final String INTENT_DEFAULT_AREA_KEY = "INTENT_DEFAULT_AREA_KEY";

    int defaultProvinceIndex = 0, defaultAreaIndex = 0;
    String intentComProvinceCityid = "", intentComAreaCityId = "";
    WheelView wheel_view_province, wheel_view_area;
    List<FuiouChinaAreaBean> fuiouAreas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.financial_select_province_area_wheel);
        TextView imageButton = (TextView) findViewById(R.id.bt_ok);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String province = GsonUtils.getGson().toJson(selectProvince);
                intent.putExtra(RETURN_SELECT_PROVINCE, province);
                String area = GsonUtils.getGson().toJson(selectArea);
                intent.putExtra(RETURN_SELECT_AREA, area);
                SelectProvinceAreaWheelActivity.this.setResult(RESULT_OK, intent);
                finish();
            }
        });
        application.getAccountStack();
        intentComProvinceCityid = getIntent().getStringExtra(INTENT_DEFAULT_PROFINCE_KEY);
        intentComAreaCityId = getIntent().getStringExtra(INTENT_DEFAULT_AREA_KEY);


        wheel_view_province = (WheelView) findViewById(R.id.wheel_view_province);
        wheel_view_province.setVisibleItems(5);


        wheel_view_area = (WheelView) findViewById(R.id.wheel_view_area);
        wheel_view_area.setVisibleItems(5);


        fuiouAreas = ShareLocalUser.getInstance(this).getFuiouArea();
        if (fuiouAreas == null || fuiouAreas.size() == 0) {
            asyncReqFuiouArea();
        } else {
            asyncInitWheel(fuiouAreas);
        }


    }

    /**
     * 获取key所在队列的索引
     *
     * @param allArea
     * @param cityId
     * @return
     */
    int getIndex(List<FuiouChinaAreaBean> allArea, String parentId, String cityId) {
        List<FuiouChinaAreaBean> tempArea = getChildren(allArea, parentId);
        if (tempArea != null)
            for (int i = 0; i < tempArea.size(); i++) {
                if (allArea.get(i).City_ID.equals(cityId))
                    return i;
            }
        return 0;
    }

    int getIndexByCityId(List<FuiouChinaAreaBean> areaes, String cityId) {
        if (areaes != null && cityId != null)
            for (int i = 0; i < areaes.size(); i++) {
                if (areaes.get(i).City_ID.equals(cityId))
                    return i;
            }
        return 0;
    }

    List<FuiouChinaAreaBean> province = null;//省数据结合
    FuiouChinaAreaBean selectProvince = null;//当前滚轮选择的省；
    FuiouChinaAreaBean selectArea = null;//选择的地区
    List<FuiouChinaAreaBean> currAreas = null;//当前省对应的地区

    void asyncInitWheel(final List<FuiouChinaAreaBean> allAreas) {
        province = getChildren(allAreas, "0");
        for (FuiouChinaAreaBean bean : province) {
            LogUtils.e(bean.toString());
        }
        defaultProvinceIndex = getIndexByCityId(province, intentComProvinceCityid);
        selectProvince = province.get(defaultProvinceIndex);
            currAreas = getChildren(allAreas, selectProvince.ID);
        defaultAreaIndex = getIndexByCityId(currAreas, intentComAreaCityId);

        wheel_view_province.setViewAdapter(new AreaWheelAdapter(this, province));

        wheel_view_province.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectProvince = province.get(newValue);
                currAreas = getChildren(allAreas, selectProvince.ID);
                    wheel_view_area.setViewAdapter(new AreaWheelAdapter(SelectProvinceAreaWheelActivity.this, currAreas));//地区绑定数据

                    wheel_view_area.setCurrentItem(0);

//                selectArea = currAreas.get(0);
                selectArea = currAreas.get(0);
            }
        });
        wheel_view_province.setCurrentItem(1);
        if (!selectProvince.ParentID.equals("0")||selectProvince.City_ID.length()==4){
            LogUtils.e("------selectProvince.City_ID11111111111----------"+selectProvince.City_ID.toString());

            wheel_view_area.setViewAdapter(new AreaWheelAdapter(this, allAreas));

        }
    wheel_view_area.addChangingListener(new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (currAreas != null)
                selectArea = currAreas.get(newValue);
        }
    });
    wheel_view_area.setCurrentItem(0);
        if (!selectProvince.ParentID.equals("0")||selectProvince.City_ID.toString().length()==4){
            selectArea = currAreas.get(defaultAreaIndex);

        }
}



    /**
     * 请求服务器shu
     * public String action = "";
     public String CustomerName = "";
     public String CustomerPass = "";
     */
    void asyncReqFuiouArea() {
        Map map=new HashMap();
        map.put("action","");
        map.put("CustomerName","");
        map.put("CustomerPass","");
        MyFinancialWeb.getInstance().GetAreaID(map, new OnRequestLinstener<BaseResponseEntity<List<FuiouChinaAreaBean>>>() {
            @Override
            public void success(BaseResponseEntity<List<FuiouChinaAreaBean>> listBaseResponseEntity) {
                fuiouAreas = listBaseResponseEntity.ReturnMessage;
                ShareLocalUser.getInstance(SelectProvinceAreaWheelActivity.this).addFuiouArea(fuiouAreas);
                asyncInitWheel(fuiouAreas);

            }

            @Override
            public void error(int code, String error) {

            }
        }, HttpUrl.GetAreaID,0, RequestMethod.GET,new TypeToken <BaseResponseEntity<List<FuiouChinaAreaBean>>>(){});
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent intent = new Intent();
        String province = GsonUtils.getGson().toJson(selectProvince);
        intent.putExtra(RETURN_SELECT_PROVINCE, province);
        String area = GsonUtils.getGson().toJson(selectArea);
        intent.putExtra(RETURN_SELECT_AREA, area);
        SelectProvinceAreaWheelActivity.this.setResult(RESULT_OK, intent);
        finish();
        return true;
    }

    /**
     * 获取
     *
     * @param param
     * @param parentid
     * @return
     */
    public List<FuiouChinaAreaBean> getChildren(List<FuiouChinaAreaBean> param, String parentid) {
        if (param == null)
            return null;

        List<FuiouChinaAreaBean> result = new ArrayList<FuiouChinaAreaBean>();
        FuiouChinaAreaBean orgTemp = null;
        for (int i = 0; i < param.size(); i++) {
            orgTemp = param.get(i);
            if (parentid.equals(orgTemp.ParentID)) {
                result.add(orgTemp);
            }
        }
        return result;
    }

    /**
     * 存储用户数据
     */


    class AreaWheelAdapter extends AbstractWheelTextAdapter {
        /**
         * The default items length
         */
        public static final int DEFAULT_LENGTH = -1;

        // items
        public List<FuiouChinaAreaBean> items;
        // length
        public int length;

        protected AreaWheelAdapter(Context context, List<FuiouChinaAreaBean> list) {
            super(context);
            items = list;
        }

        @Override
        public int getItemsCount() {
            return items.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            if (index >= 0 && index < items.size()) {
                return items.get(index).City_Name.toString();
            }
            return null;
        }
    }
}
