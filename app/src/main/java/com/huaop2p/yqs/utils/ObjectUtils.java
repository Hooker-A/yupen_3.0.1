package com.huaop2p.yqs.utils;

/**
 * Created by lenovo on 2014/12/15.
 */
public class ObjectUtils {
    private ObjectUtils() {
        throw new AssertionError();
    }

    /**
     * compare two object
     *
     * @param actual
     * @param expected
     * @return if both are null return true
     * if teturn actual.{@link java.lang.Object#equals(Object)}
     */
    public static boolean isEquals(Object actual, Object expected) {
        return actual == expected || (actual == null ? expected == null : actual.equals(expected));
    }

    /**
     * null Object to empty string
     *
     * @param str
     * @return
     */
    public static String nullStrToEmpty(Object str) {
        return (str == null ? "" : (str instanceof String ? (String) str : str.toString()));
    }

    /**
     * convert long array to Long array
     *
     * @param source
     * @return
     */
    public static Long[] transformLongArray(long[] source) {
        Long[] destin = new Long[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = source[i];
        }
        return destin;
    }

    /**
     * convert Long array to long array
     *
     * @param source
     * @return
     */
    public static long[] transformLongArray(Long[] source) {
        long[] destin = new long[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = source[i];
        }
        return destin;
    }

    /**
     * convert int array to Integer array
     *
     * @param source
     * @return
     */
    public static Integer[] transformIntArray(int[] source) {
        Integer[] destin = new Integer[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = source[i];
        }
        return destin;
    }

    /**
     * convert Integer array to int array
     *
     * @param source
     * @return
     */
    public static int[] transfromIntArray(Integer[] source) {
        int[] destin = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = source[i];
        }
        return destin;
    }

    /**
     * compare two object
     * about result
     * v1>v2 return 1
     * v1=v2 return 0
     * v1<v2 return 01
     *
     * @param v1
     * @param v2
     * @param <V>
     * @return if v1 is null,v2 is null then return 0
     * if v1 is null,v2 is not null,then return -1
     * if v1 is not null,v2 is null,then return 1
     * return v1.{@link java.lang.Comparable#compareTo(Object)}
     */
    public static <V> int compare(V v1, V v2) {
        return v1 == null ? (v2 == null ? 0 : -1) : (v2 == null ? 1 : ((Comparable) v1).compareTo(v2));
    }


}
