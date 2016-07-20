package com.huaop2p.yqs.module.four_set.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.four_set.adapter.AlbumGridViewAdapter;
import com.huaop2p.yqs.module.four_set.entity.ImageItem;
import com.huaop2p.yqs.module.four_set.util.AlbumHelper;
import com.huaop2p.yqs.module.four_set.util.ImageBucket;
import com.huaop2p.yqs.module.four_set.util.PublicWay;
import com.huaop2p.yqs.module.four_set.view.Bimp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgq on 2016/5/24.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/24 11:24
 * 功能:  打开手机相册
 */
public class AlbumActivity extends Activity {

    //鏄剧ず鎵嬫満閲岀殑鎵�鏈夊浘鐗囩殑鍒楄〃鎺т欢
    private GridView gridView;
    //褰撴墜鏈洪噷娌℃湁鍥剧墖鏃讹紝鎻愮ず鐢ㄦ埛娌℃湁鍥剧墖鐨勬帶浠�
    private TextView tv;
    //gridView鐨刟dapter
    private AlbumGridViewAdapter gridImageAdapter;
    //瀹屾垚鎸夐挳
    private Button okButton;
    // 杩斿洖鎸夐挳
    private Button back;
    // 鍙栨秷鎸夐挳
    private Button cancel;
    private Intent intent;
    // 棰勮鎸夐挳
    private Button preview;
    private Context mContext;
    private ArrayList<ImageItem> dataList;
    private AlbumHelper helper;
    public static List<ImageBucket> contentList;
    public static Bitmap bitmap;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_camera_album);
        PublicWay.activityList.add(this);
        mContext = this;
        //娉ㄥ唽涓�涓箍鎾紝杩欎釜骞挎挱涓昏鏄敤浜庡湪GalleryActivity杩涜棰勮鏃讹紝闃叉褰撴墍鏈夊浘鐗囬兘鍒犻櫎瀹屽悗锛屽啀鍥炲埌璇ラ〉闈㈡椂琚彇娑堥�変腑鐨勫浘鐗囦粛澶勪簬閫変腑鐘舵��
        IntentFilter filter = new IntentFilter("data.broadcast.action");
//        registerReceiver(broadcastReceiver, filter);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.duihao);
        init();
        initListener();
        //杩欎釜鍑芥暟涓昏鐢ㄦ潵鎺у埗棰勮鍜屽畬鎴愭寜閽殑鐘舵��
        isShowOkBt();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //mContext.unregisterReceiver(this);
            // TODO Auto-generated method stub
            gridImageAdapter.notifyDataSetChanged();
        }
    };

    // 棰勮鎸夐挳鐨勭洃鍚�
    private class PreviewListener implements View.OnClickListener {
        public void onClick(View v) {
            if (Bimp.tempSelectBitmap.size() > 0) {
                intent.putExtra("position", "1");
                intent.setClass(AlbumActivity.this, GalleryActivity.class);
                startActivity(intent);
            }
        }

    }

    // 瀹屾垚鎸夐挳鐨勭洃鍚�
    private class AlbumSendListener implements OnClickListener {
        public void onClick(View v) {
//            overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
            intent.setClass(mContext, FeedBackActivity.class);
            startActivity(intent);
            finish();
        }

    }

    // 杩斿洖鎸夐挳鐩戝惉
//    private class BackListener implements OnClickListener {
//        public void onClick(View v) {
//            intent.setClass(AlbumActivity.this, ImageFile.class);
//            startActivity(intent);
//        }
//    }

    // 鍙栨秷鎸夐挳鐨勭洃鍚�
    private class CancelListener implements OnClickListener {
        public void onClick(View v) {
            Bimp.tempSelectBitmap.clear();
            intent.setClass(mContext, FeedBackActivity.class);
            startActivity(intent);
        }
    }



    // 鍒濆鍖栵紝缁欎竴浜涘璞¤祴鍊�
    private void init() {
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        contentList = helper.getImagesBucketList(false);
        dataList = new ArrayList<ImageItem>();
        for(int i = 0; i<contentList.size(); i++){
            dataList.addAll( contentList.get(i).imageList );
        }

        back = (Button) findViewById(R.id.back);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new CancelListener());
//        back.setOnClickListener(new BackListener());
        preview = (Button) findViewById(R.id.preview);
        preview.setOnClickListener(new PreviewListener());
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        gridView = (GridView) findViewById(R.id.myGrid);
        gridImageAdapter = new AlbumGridViewAdapter(this,dataList,
                Bimp.tempSelectBitmap);
        gridView.setAdapter(gridImageAdapter);
        tv = (TextView) findViewById(R.id.myText);
        gridView.setEmptyView(tv);
        okButton = (Button) findViewById(R.id.ok_button);
        okButton.setText(("完成")+"(" + Bimp.tempSelectBitmap.size()
                + "/"+PublicWay.num+")");
    }

    private void initListener() {

        gridImageAdapter
                .setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(final ToggleButton toggleButton,
                                            int position, boolean isChecked,Button chooseBt) {
                        if (Bimp.tempSelectBitmap.size() >= PublicWay.num) {
                            toggleButton.setChecked(false);
                            chooseBt.setVisibility(View.GONE);
                            if (!removeOneData(dataList.get(position))) {

                            }
                            return;
                        }
                        if (isChecked) {
                            chooseBt.setVisibility(View.VISIBLE);
                            Bimp.tempSelectBitmap.add(dataList.get(position));
                            okButton.setText(("完成")+"(" + Bimp.tempSelectBitmap.size()
                                    + "/"+PublicWay.num+")");
                        } else {
                            Bimp.tempSelectBitmap.remove(dataList.get(position));
                            chooseBt.setVisibility(View.GONE);
                            okButton.setText(("完成")+"(" + Bimp.tempSelectBitmap.size() + "/"+PublicWay.num+")");
                        }
                        isShowOkBt();
                    }
                });

        okButton.setOnClickListener(new AlbumSendListener());

    }

    private boolean removeOneData(ImageItem imageItem) {
        if (Bimp.tempSelectBitmap.contains(imageItem)) {
            Bimp.tempSelectBitmap.remove(imageItem);
            okButton.setText(("完成")+"(" +Bimp.tempSelectBitmap.size() + "/"+PublicWay.num+")");
            return true;
        }
        return false;
    }

    public void isShowOkBt() {
        if (Bimp.tempSelectBitmap.size() > 0) {
            okButton.setText(("完成")+"(" + Bimp.tempSelectBitmap.size() + "/"+PublicWay.num+")");
            preview.setPressed(true);
            okButton.setPressed(true);
            preview.setClickable(true);
            okButton.setClickable(true);
            okButton.setTextColor(Color.WHITE);
            preview.setTextColor(Color.WHITE);
        } else {
            okButton.setText(("完成")+"(" + Bimp.tempSelectBitmap.size() + "/"+PublicWay.num+")");
            preview.setPressed(false);
            preview.setClickable(false);
            okButton.setPressed(false);
            okButton.setClickable(false);
            okButton.setTextColor(Color.parseColor("#E1E0DE"));
            preview.setTextColor(Color.parseColor("#E1E0DE"));
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            intent.setClass(AlbumActivity.this, ImageFile.class);
//            startActivity(intent);
        }
        return false;

    }
    @Override
    protected void onRestart() {
        isShowOkBt();
        super.onRestart();
    }
}
