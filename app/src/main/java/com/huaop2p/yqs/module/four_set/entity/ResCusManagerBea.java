package com.huaop2p.yqs.module.four_set.entity;

import java.util.Date;

/**
 * Created by zgq on 2016/7/5.
 * 作者:  zhu  guo qing
 * 时间:  2016/7/5 18:09
 * 功能:
 */
public class ResCusManagerBea {

    public String jsonEntity;

    public static class JsonEntity {
        public int KeyId;
        public String LoginName;
        public String LoginPass;
        public String PassSalt;
        public String TrueName;
        public String Sex;
        public int Age;
        public String CardID;
        public String PhoneNum;
        public String Email;
        public String QQ;
        public String Address;
        public Date StartTime;
        public Date EndTime;
        public int IsStop;
        public Date StopTime;
        public int IsDisable;
        public Date DisableTime;
        public String WorryUserName;
        public String WorryUserPhone;
        public int DepartmentID;
        public String Department;
        public String RoleIDS;
        public String RolesName;
        public String LBS;
        public String LocalPhoneNum;
        public String MobleCard;
        public String OS;
        public String NetWork;
        public String DisplayMetrics;
        public String PayPassWord;
        public String PayPassSalt;
        public String CustomerManagerID;
        public String Remarks;   //备注
        public String UrlHeadPhoto;//   :"头像"
    }
}
