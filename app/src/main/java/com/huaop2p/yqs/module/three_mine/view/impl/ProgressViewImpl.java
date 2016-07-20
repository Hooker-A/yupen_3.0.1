package com.huaop2p.yqs.module.three_mine.view.impl;

import android.graphics.Color;

import com.huaop2p.yqs.module.three_mine.view.IProgressView;
import com.huaop2p.yqs.module.two_wealth.assist.BaseCalculator;
import com.huaop2p.yqs.widget.ProgressView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/6.
 * 作者：任洋
 * 功能：进度的view
 */
public class ProgressViewImpl implements IProgressView {
    private float[] progress;
    private int[] colors;
    private ProgressView progressView;
    private BaseCalculator calculator = BaseCalculator.getClaculator();
    private List<Integer> integers;

    public ProgressViewImpl(int[] colors, float[] progress, ProgressView progressView) {
        this.colors = colors;
        this.progress = progress;
        this.progressView = progressView;
        integers = new ArrayList<>();
    }

    public ProgressViewImpl(ProgressView progressView) {
        this.progressView = progressView;
        integers = new ArrayList<>();

    }

    @Override
    public void setProgress(float[] progress) {
        integers.clear();
        float total = 0;
        for (int i = 0; i < progress.length; i++) {
            total += progress[i];
            if (progress[i] > 0) {
                integers.add(i);
            }
        }
        if (integers.size() == 0) {
            return;
        }
        this.progress = new float[integers.size() * 2 - 1];
        int index = 0;
        float a = 0;
        float aa = 0;
        for (int i = 0; i < this.progress.length; i++) {
            if (i % 2 != 0) {
                this.progress[i] = aa;
            } else {
                a = progress[integers.get(index)] / total;   //a是百分比
                if (a <= 0.2f / 100f)                     // 如果a<=百分之0.2  进度白条就不显示
                    aa = 0;
                else                                   //否则 aa是白条 aa大小为百分之0.2长
                    aa = 0.2f;
                if (aa != 0)
                    this.progress[i] = a * 100 - (integers.size() > 1 == true ? aa : 0);
                else
                    this.progress[i] = 0;               //如果白条不显示，进度值也不显示
                index++;
            }
        }
    }

    //    if (i % 2 == 0) {
//        this.progress[i] = calculator.divide(calculator.multiply(progress[index],100f),total)*0.1f;
//    } else {
//        this.progress[i] =calculator.divide(calculator.multiply(progress[index],100f),total)  *0.9f;
//        index++;
//    }
    @Override
    public void setColors(int[] colors) {
        if (integers.size() == 0)
            return;
        this.colors = new int[integers.size() * 2 - 1];
        int index = 0;
        for (int i = 0; i < this.colors.length; i++) {
            if (i % 2 != 0) {
                this.colors[i] = Color.WHITE;
            } else {
                this.colors[i] = colors[integers.get(index)];
                index++;
            }
        }
    }

    @Override
    public void startLoad() {
        if (integers.size() == 0) {
            clearProgerss();
        } else {
            boolean isEqual = true;
            if (progress.length == progressView.getPro().length) {
                for (int i = 0; i < progress.length; i++) {
                    if (Math.abs( progressView.getPro()[i] - progress[i]) >= 0.01) {
                        isEqual = false;
                        break;
                    }
                }
            } else {
                isEqual = false;
            }
            if (isEqual) {
                return;
            }
            progressView.setPro(progress, colors);
        }
    }

    @Override
    public void setMargin(int margin) {
        progressView.seMargin(margin);
    }

    @Override
    public void clearProgerss() {
        progressView.clearPro();
    }
}
