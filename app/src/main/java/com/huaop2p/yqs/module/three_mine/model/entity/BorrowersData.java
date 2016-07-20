package com.huaop2p.yqs.module.three_mine.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/5/26.
 * 作者：任洋
 * 功能：
 */
public class BorrowersData {
    public String L_LastName;//": "姓",
    public String L_FirstName;//": "名",
    public String L_Name;//": "姓名",
    public String L_Gender;//": "性别",
    public String L_Education;//": "学历",
    public int L_MarriageState;//": "婚姻状态 0:未婚 1：已婚 2：离异 3：丧偶",
    public String L_Age;//": "年龄",
    public String L_CardIdCity;//": "户籍城市",
    public String L_CardId;//": "身份证Id",
    public String L_Email;//": "邮箱",
    public String L_PhoneNum;//": "手机号",
    public String L_Address;//": "现居住地址",
    public String L_HasCar;//": "是否有车: 有/无",
    public String L_HasRoom;//": "是否有房: 有/无",
    public List<MaterialInfo> L_MaterialInfo;
    public  String SafeguardRemark;//保障说明
    public String SafeguardMechanism;// 保障机构
    public String LoanRemark;

    public class MaterialInfo {
        public String MaterialUrl;//": "资料地址(网站绝对路径)",
        public String UrlRemark;//": "资料地址的说明",
        public String MaterialName;//": "资料名称",
        public String IsEnable;//": "是否启用（0否   1是）",
        public String NameRemark;//": "资料名称说明"

    }
}
