package com.huaop2p.yqs.module.one_home.bean;


import com.huaop2p.yqs.module.two_wealth.model.entity.WealthCenter;

import java.util.List;

/**
 * Created by maoxiaofei on 2016/4/19.
 */
public class ReqProductBean {
    /*
    * "ReturnMessage": {
        "Xin": [
            {
                "AssetStyles": [
                    {
                        "Font": "保",
                        "Color": "#42a8ea"
                    },
                    {
                        "Font": "500起投",
                        "Color": "#f6bb43"
                    },
                    {
                        "Font": "红包",
                        "Color": "#e9573e"
                    },
                    {
                        "Font": "奖券",
                        "Color": "#37bd9c"
                    }
                ],
                "KeyId": 6,
                "Name": "6月期",
                "TopoOne": "8.5%",
                "TopoTwo": "预期年化收益",
                "TrueRate": 0.0425,
                "MinimumMoney": 500,
                "Amount": 0,
                "AlreadyBuy": 26235,
                "LoanTermId": 2,
                "LoanTerm": 6,
                "LoanState": 2,
                "ReturnWay": -1
            },
            {
                "AssetStyles": [],
                "KeyId": 11,
                "Name": "12月期",
                "TopoOne": "12.5%",
                "TopoTwo": "预期年化收益",
                "TrueRate": 0.125,
                "MinimumMoney": 500,
                "Amount": 459,
                "AlreadyBuy": 485473,
                "LoanTermId": 2,
                "LoanTerm": 12,
                "LoanState": 1,
                "ReturnWay": -1
            }
        ],
        "House": [],
        "Car": []
    }
    * */
    public List<WealthCenter> Xin;
    public List<WealthCenter> House;
    public List<WealthCenter> Car;

}
