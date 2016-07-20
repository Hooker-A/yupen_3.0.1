package com.huaop2p.yqs.module.two_wealth.assist;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/5/24.
 * 作者：任洋
 * 功能：计算器功能
 */
public class BaseCalculator implements ICalculator {
    private static final int SCALE = 10;
    private static BaseCalculator calculator;

    public static BaseCalculator getClaculator() {
        if (calculator == null) {
            calculator = new BaseCalculator();
        }
        return calculator;
    }

    private BaseCalculator() {
    }

    @Override
    public double add(double a, double b) {
        BigDecimal b1 = new BigDecimal(Double.toString(a));
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        return b1.add(b2).doubleValue();
    }

    @Override
    public int add(int a, int b) {

        return a + b;
    }

    @Override
    public float add(float a, float b) {
        BigDecimal b1 = new BigDecimal(Float.toString(a));
        BigDecimal b2 = new BigDecimal(Float.toString(b));
        return b1.add(b2).floatValue();
    }

    @Override
    public double reduce(double a, double b) {
        BigDecimal b1 = new BigDecimal(Double.toString(a));
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        return b1.subtract(b2).doubleValue();
    }

    @Override
    public float reduce(float a, float b) {
        BigDecimal b1 = new BigDecimal(Float.toString(a));
        BigDecimal b2 = new BigDecimal(Float.toString(b));
        return b1.subtract(b2).floatValue();
    }

    @Override
    public int reduce(int a, int b) {
        return a - b;
    }

    @Override
    public double multiply(double a, double b) {
        BigDecimal b1 = new BigDecimal(Double.toString(a));
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        return b1.multiply(b2).doubleValue();
    }

    @Override
    public float multiply(float a, float b) {
        BigDecimal b1 = new BigDecimal(Float.toString(a));
        BigDecimal b2 = new BigDecimal(Float.toString(b));
        return b1.multiply(b2).floatValue();
    }

    @Override
    public int multiply(int a, int b) {
        return a * b;
    }

    @Override
    public double divide(double a, double b) {
        if (b==0)
            return 0;
        BigDecimal b1 = new BigDecimal(Double.toString(a));
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        return b1.divide(b2, SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @Override
    public float divide(float a, float b) {
        if (b==0)
            return 0;
        BigDecimal b1 = new BigDecimal(Float.toString(a));
        BigDecimal b2 = new BigDecimal(Float.toString(b));
        return b1.divide(b2, SCALE, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    @Override
    public int divide(int a, int b) {
        return a / b;
    }
}
