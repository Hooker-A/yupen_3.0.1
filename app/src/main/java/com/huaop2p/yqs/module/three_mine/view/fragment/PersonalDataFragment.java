package com.huaop2p.yqs.module.three_mine.view.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.contant.State;
import com.huaop2p.yqs.module.base.view.ScrollAbleFragment;
import com.huaop2p.yqs.module.three_mine.adapter.ImageTextAdapter;
import com.huaop2p.yqs.module.three_mine.model.entity.BorrowersData;
import com.huaop2p.yqs.module.three_mine.presenter.impl.BorrowerDataPresenterImpl;
import com.huaop2p.yqs.module.two_wealth.activity.ImageActivity;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/5/13.
 * 作者：任洋
 * 功能：借款的个人资料信息
 */
public class PersonalDataFragment extends ScrollAbleFragment<BorrowersData> {
    private ScrollView mScrollView;
    private String keyId;
    private BorrowerDataPresenterImpl presenter;
    private HashMap<String, Object> map;
    private int color;
    private TextView tv_name, tv_sex, tv_age, tv_education_value, tv_marital_status_value, tv_car_production_value, tv_house_property_value, tv_hukou_value, tv_seat_value, tv_tuijianjigou_value, tv_safeguard_way_value;
    private View line1, line2, line3,ll_top,line;
    private GridView c_gv;
    private ImageTextAdapter adapter;
    private boolean flag;
    private  TextView tv_describe;
    private ArrayList<String> urls;
    @Override
    public void showData() {
        if (keyId == null) {
            keyId = getArguments().getString("KeyId");
            color = getArguments().getInt("color");
            flag = getArguments().getBoolean("flag");
            line1.setBackgroundColor(color);
            line2.setBackgroundColor(color);
            line3.setBackgroundColor(color);
            line.setBackgroundColor(color);
            map = new HashMap<>();
            presenter = new BorrowerDataPresenterImpl(this);
            map.put("KeyId", keyId);
            presenter.loadDetail(map, HttpUrl.GET_BORROWERDATA, 0, RequestMethod.POST);
            adapter = new ImageTextAdapter(null, getActivity(), R.layout.item_text_image);
            c_gv.setAdapter(adapter);
        }
    }

    @Override
    public View initViews(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_personal_data, null);
        mScrollView = (ScrollView) view.findViewById(R.id.mScrollView);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_sex = (TextView) view.findViewById(R.id.tv_sex);
        tv_age = (TextView) view.findViewById(R.id.tv_age);
        tv_education_value = (TextView) view.findViewById(R.id.tv_education_value);
        tv_marital_status_value = (TextView) view.findViewById(R.id.tv_marital_status_value);
        tv_car_production_value = (TextView) view.findViewById(R.id.tv_car_production_value);
        tv_house_property_value = (TextView) view.findViewById(R.id.tv_house_property_value);
        tv_describe= (TextView) view.findViewById(R.id.tv_describe);
        tv_hukou_value = (TextView) view.findViewById(R.id.tv_hukou_value);
        tv_seat_value = (TextView) view.findViewById(R.id.tv_seat_value);
        tv_tuijianjigou_value = (TextView) view.findViewById(R.id.tv_tuijianjigou_value);
        tv_safeguard_way_value = (TextView) view.findViewById(R.id.tv_safeguard_way_value);
        line1 = view.findViewById(R.id.line1);
        line2 = view.findViewById(R.id.line2);
        line3 = view.findViewById(R.id.line3);
        line=view.findViewById(R.id.line);
        ll_top=view.findViewById(R.id.ll_top);
        c_gv = (GridView) view.findViewById(R.id.c_gv);
        c_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BorrowersData.MaterialInfo m = adapter.getItem(position);
                Intent intent = new Intent(getActivity(), ImageActivity.class);
                intent.putExtra("urls",urls);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
        disableAutoScrollToBottom();
        return view;
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
    }

    @Override
    public View getScrollableView() {
        return mScrollView;
    }

    @Override
    public void showSuccess(BorrowersData b) {
        tv_name.setText(b.L_Name);
        tv_age.setText(b.L_Age);
        tv_sex.setText(b.L_Gender);
        tv_education_value.setText(b.L_Education);
        switch (b.L_MarriageState) {
            case State.UNMARRIED:
                tv_marital_status_value.setText("未婚");
                break;
            case State.MARRIED:
                tv_marital_status_value.setText("已婚");
                break;
            case State.DIVORCE:
                tv_marital_status_value.setText("离异");
                break;
            case State.DIE:
                tv_marital_status_value.setText("丧偶");
                break;
        }
        tv_car_production_value.setText(b.L_HasCar);
        tv_house_property_value.setText(b.L_HasRoom);
        tv_hukou_value.setText(b.L_CardIdCity);
        tv_seat_value.setText(b.L_Address);
        tv_tuijianjigou_value.setText(b.SafeguardMechanism);
        tv_safeguard_way_value.setText(b.SafeguardRemark);
        adapter.refresh(b.L_MaterialInfo);
        urls=new ArrayList<>();
       for (int i=0;i<b.L_MaterialInfo.size();i++){
           urls.add(b.L_MaterialInfo.get(i).MaterialUrl);
       }
        if (flag){
            ll_top.setVisibility(View.VISIBLE);
            tv_describe.setVisibility(View.VISIBLE);
            tv_describe.setText(b.LoanRemark);
        }
    }
}
