package com.huaop2p.yqs.utils;


/**
 * Created by zgq on 2016/6/20.
 * 作者:  zhu  guo qing
 * 时间:  2016/6/20 10:19
 * 功能:
 */
public class SCcodeUtil {

    public  static  String veryfy(String id) {

        int[] ary = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
        char[] ch = { '1', '2', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
        int sum = 0;
        char data;
        switch (id.length()) {
            case 17:
                char[] ary1 = id.toCharArray();
                for (int i = 0; i < ary1.length; i++) {
                    sum += (ary1[i] - '0') * ary[i];
                }
                data = ch[sum % 11];
                String s1 = "您是输入的是17位的身份证号码，合法的身份证号码为";
                return s1+" : \n"+id + data;

            case 18:
                char[] ary2 = id.toCharArray();
                for (int i = 0; i < ary2.length - 1; i++) {
                    sum += (ary2[i] - '0') * ary[i];
                }
                data = ch[sum % 11];
                char lastNum = id.charAt(17);
                lastNum = lastNum == 'x' ? 'X' : lastNum;
                if (data == lastNum) {
                    return "身份证号码正确！";
                }
                String s2 = "您输入的身份证号码不正确";
                char[] ary3 = new char[17];
                for(int i = 0;i < id.length() - 1;i++){
                    ary3[i] = ary2[i];
                }
                return s2+" : \n"+new String(ary3);

            default:
                return "请输入17或18位的身份证号码！";
        }


    }

}
