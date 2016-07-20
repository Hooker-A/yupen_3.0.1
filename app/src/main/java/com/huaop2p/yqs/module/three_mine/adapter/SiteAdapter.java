package com.huaop2p.yqs.module.three_mine.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;
import com.huaop2p.yqs.dialog.ShowDialog;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.three_mine.activity.dataactivity.EditSiteActivity;
import com.huaop2p.yqs.module.three_mine.model.entity.SiteBean;
import com.huaop2p.yqs.module.three_mine.model.impl.MyDataHttp;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.lidroid.xutils.util.LogUtils;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgq on 2016/6/21.
 * 作者:  zhu  guo qing
 * 时间:  2016/6/21 16:56
 * 功能:  查询地址适配器
 */
public class SiteAdapter extends BaseAdapter{
    private Context context;
    private List<SiteBean> list;
    private LayoutInflater inflater;
    private SiteBean siteBean;
    public static int temp = -1;
    private Activity activity;




    public SiteAdapter(List<SiteBean> list,Activity context){
        this.list=list;
        this.context=context;
        this.activity = context;
        this.inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.activity_site_item, null);
            viewHolder=new ViewHolder();
            viewHolder.name=(TextView)convertView.findViewById(R.id.txt_siet_name);
            viewHolder.listViewItem=(LinearLayout)convertView.findViewById(R.id.ll_listitem);
            viewHolder.provinces=(TextView)convertView.findViewById(R.id.txt_siet_adds);
            viewHolder.phone=(TextView)convertView.findViewById(R.id.txt_siet_iphone);
            viewHolder.moren=(CheckBox)convertView.findViewById(R.id.cb_siet_mo);
            viewHolder.txt_bianji= (TextView) convertView.findViewById(R.id.txt_bianji);
            viewHolder.txt_sanchu= (TextView) convertView.findViewById(R.id.txt_sanchu);
            viewHolder.txt_mo= (TextView) convertView.findViewById(R.id.txt_mo);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
         siteBean=list.get(position);
        viewHolder.moren.setClickable(false);
        viewHolder.name.setText(siteBean.Name);
        viewHolder.provinces.setText(siteBean.Address);
        viewHolder.phone.setText(siteBean.PhoneNum);
        if (siteBean.IsDefault){
            viewHolder.moren.setBackgroundResource(R.drawable.invest_radio_selcted);
            viewHolder.txt_mo.setText("默认地址");

        }else {
            viewHolder.txt_mo.setText("设为默认");
            viewHolder.moren.setBackgroundResource(R.drawable.invest_radio_unselcted);



        }
        viewHolder.listViewItem.setTag(siteBean.KeyId);
        viewHolder.moren.setId(position);
        viewHolder.moren.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//实现checkbox的单选功能,同样适用于radiobutton

                    if (temp != -1) {
                        //找到上次点击的checkbox,并把它设置为false,对重新选择时可以将以前的关掉

                        CheckBox tempCheckBox = (CheckBox) activity.findViewById(temp);
                        if (tempCheckBox != null){
                            tempCheckBox.setChecked(false);
                            notifyDataSetChanged();


                        }
                    }
                    temp = buttonView.getId();//保存当前选中的checkbox的id值


                }
            }
        });
        if (position == temp){//比对position和当前的temp是否一致
            viewHolder.moren.setChecked(true);
            viewHolder.txt_mo.setText("默认地址");
            viewHolder.moren.setBackgroundResource(R.drawable.invest_radio_selcted);

        }else {
            if (temp == -1){
            }else {
                viewHolder.moren.setChecked(false);
                viewHolder.txt_mo.setText("设为默认");
                viewHolder.moren.setBackgroundResource(R.drawable.invest_radio_unselcted);
            }
//            viewHolder.moren.setBackgroundResource(R.drawable.invest_radio_unselcted);


        }
/**
 * 编辑
 */
        viewHolder.txt_bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String keyid = list.get(position).KeyId;
                String keyid = siteBean.KeyId;
                LogUtils.e(keyid + "----------------------------------------");
                String name = siteBean.Name;
                String phone = siteBean.PhoneNum;
                String area = siteBean.Area;
                String emsn = siteBean.EMSNum;
                String addares = siteBean.Address;
                boolean isdefau = siteBean.IsDefault;
                EdetHttp(keyid, name, phone, area, emsn, addares, isdefau, position);

            }
        });
        /**
         * 删除
         */
        viewHolder.txt_sanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog.Builder builder1 = new ShowDialog.Builder(context);
                builder1.setTitle("确认删除地址?");
                builder1.setMessage("删除后将无法恢复");
                builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String dele = list.get(position).KeyId;
                        DeleHttp(dele, position);
                        dialog.dismiss();

                    }
                });
                builder1.createOneBtn(true).show();

            }
        });
        /**
         * 默认
         */
        viewHolder.moren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();

                String ke=list.get(position).KeyId;
                MoHttp(ke, position);

            }
        });

        return convertView;
    }



    class ViewHolder{
        LinearLayout listViewItem;
        TextView name,provinces,street,phone,txt_bianji,txt_sanchu,txt_mo;
        CheckBox moren;
    }
    /**
     * 默认地址
     */
    private void MoHttp(String key,final int position){
        Map map=new HashMap();
        map.put("KeyId",key);
        MyDataHttp.getInstance().PostAddAddress(map, new OnRequestLinstener<BaseResponseEntity<List<SiteBean>>>() {
            @Override
            public void success(BaseResponseEntity<List<SiteBean>> listBaseResponseEntity) {
                list.get(position);
                notifyDataSetChanged();
            }

            @Override
            public void error(int code, String error) {

            }
        }, HttpUrl.PostSetAddress, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity<List<SiteBean>>>(){});
    }
    /**
     * 删除地址
     * @param key
     * {"KeyId":"需要删除地址的KeyId"}
     */
    private void DeleHttp(String key,final int position){
        Map map=new HashMap();
        map.put("KeyId",key);
        MyDataHttp.getInstance().PostAddAddress(map, new OnRequestLinstener<BaseResponseEntity<List<SiteBean>>>() {
            @Override
            public void success(BaseResponseEntity<List<SiteBean>> listBaseResponseEntity) {
                list.remove(list.get(position));
                notifyDataSetChanged();
            }

            @Override
            public void error(int code, String error) {

            }
        }, HttpUrl.PostDeleteAddress, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity<List<SiteBean>>>(){});
    }

    /**
     * 修改地址
     * @param key
     * {"KeyId":"需要修改地址的KeyId","Name":"姓名","PhoneNum":"手机号","Area":"地区","EMSNum":"邮政编码","Address":"详细地址","IsDefault":"true=默认收货地址 false=非默认收货地址"}
     */
private void EdetHttp(String key,String name,String phone,String area,String emsn,String addares,boolean isdefau, final int position){
    Map map=new HashMap();
    map.put("KeyId",key);
    map.put("Name",name);
    map.put("PhoneNum",phone);
    map.put("Area",area);
    map.put("EMSNum",emsn);
    map.put("Address",addares);
    map.put("IsDefault",isdefau);
    MyDataHttp.getInstance().PostAddAddress(map, new OnRequestLinstener<BaseResponseEntity<List<SiteBean>>>() {
        @Override
        public void success(BaseResponseEntity<List<SiteBean>> listBaseResponseEntity) {
            Intent intent=new Intent(context, EditSiteActivity.class);
            intent.putExtra(EditSiteActivity.ADAP_BEAN, listBaseResponseEntity.ReturnMessage.get(position));
            context.startActivity(intent);

        }

        @Override
        public void error(int code, String error) {

        }
    },HttpUrl.PostEditAddress,0,RequestMethod.POST,new TypeToken<BaseResponseEntity<List<SiteBean>>>(){});
}


}
