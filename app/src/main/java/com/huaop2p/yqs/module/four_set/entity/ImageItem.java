package com.huaop2p.yqs.module.four_set.entity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zgq on 2016/5/24.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/24 11:11
 * 功能:
 */
public class ImageItem implements Parcelable {

    public String imageId;
    public String thumbnailPath;
    public String imagePath;
    private Bitmap bitmap;



    public Bitmap getBitmap() {

        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bun=new Bundle();
        bun.putParcelable("a",bitmap);
        dest.writeBundle(bun);
    }

    public static final Parcelable.Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        @Override
        public ImageItem createFromParcel(Parcel source) {
            ImageItem mPerson = new ImageItem();
            mPerson.bitmap = source.readBundle().getParcelable("a");
            return mPerson;
        }

        @Override
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };
}
