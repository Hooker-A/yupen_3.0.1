package com.huaop2p.yqs.module.two_wealth.assist;

/**
 * Created by Administrator on 2016/5/24.
 * 作者：任洋
 * 功能：
 */
public interface ICalculator {
    public  double add(double a, double b);  //加法

    public int add(int a, int b);         //加法
    public float add(float a, float b);         //加法

    public double reduce(double a, double b);  //减法
    public float reduce(float a, float b);  //减法
    public int reduce(int a, int b);          //减法


    public double multiply(double a, double b);  //乘法
    public float multiply(float a, float b);  //乘法
    public int multiply(int a, int b);          //乘法

    public double divide(double a, double b);  //除法
    public float divide(float a, float b);  //除法
    public int divide(int a, int b);          //除法
}
