package com.huaop2p.yqs.module.base.contant;

/**
 * Created by maoxiaofei on 2016/4/13.
 */
public class HttpUrl {

    /***
     * POST Api/Customer/Logins 登陆
     */
    public static String Logins = HttpConnector.APP_HTTP_HOST2 + "Customer/Logins";

    /**
     * POST	Api/Activity/RichScan   扫一扫
     **/
    public static String Scan = HttpConnector.APP_HTTP_HOST2 + "Activity/RichScan";
    /***
     * get
     * Api/Infomations/PrivateRaise
     **/
    public static String PrivateRaise = HttpConnector.APP_HTTP_HOST2 + "Infomations/PrivateRaise";
    /*
    *  GET Api/MobileProject/HotBanners 获取推荐Banner
    * */
    public static final String bannerURL = HttpConnector.CrowdHost + "MobileProject/HotBanners";

    /**
     * POST Api/Cj_Version/GetNewAgreement 获取协议
     */
    public static String GetNewAgreement = HttpConnector.APP_HTTP_HOST2 + "Cj_Version/GetNewAgreement";

    /**
     * POST Api/Cj_Version/SetNewAgreement 设置协议
     */
    public static String SetNewAgreement = HttpConnector.APP_HTTP_HOST2 + "Cj_Version/SetNewAgreement";

    /**
     * POST api/Cj_Feedback 获取意见
     * Api/Cj_Feedback/Cj_Feedbacks
     */
    public static String Cj_Feedback = HttpConnector.APP_HTTP_HOST2 + "Cj_Feedback/Cj_Feedbacks";
    /**
     * Post
     * Api/Cj_Feedback/FeedbackImg 意见反馈加图片
     */
    public static String PostFeedbackImg = HttpConnector.APP_HTTP_HOST2 + "Cj_Feedback/FeedbackImg";
    /***
     * post
     * Api/LoanPersonStandard/BuyLoanStandard
     * 支付余房余车
     **/
    public static String PAY_HOUSE_CAR = HttpConnector.APP_HTTP_HOST2 + "LoanPersonStandard/BuyLoanStandard";
    /***
     * post
     * Api/LoanPInfo/BuyLoanPerson
     * 支付余信
     **/
    public static String PAY_XIN = HttpConnector.APP_HTTP_HOST2 + "LoanPInfo/BuyLoanPerson";
    /***
     * post
     * <p/>
     * Api/TransferController/BuyTransferProduct
     **/
    public static String PAY_TRANSFER = HttpConnector.APP_HTTP_HOST2 + "TransferController/BuyTransferProduct";
    /*
    *
    * GET Api/Business/TradeStatistic 交易总笔数,投资总金额(万元)，总收益(万元) 不用登录
    *
    * */
    public static String HomeDataMsg = HttpConnector.APP_HTTP_HOST2 + "Business/TradeStatistic";

    /**
     * GET Api/Business/GetRecommend 获取推荐列表
     * GET Api/Business/GetRecommend 获取推荐列表
     */
    public static final String GetRecPro = HttpConnector.APP_HTTP_HOST2 + "Business/GetRecommend";
    /**
     * POST
     * Api/Business/GetProduct获取余余房车列表
     */
    public static final String POST_GET_YUFANG_LIST = HttpConnector.APP_HTTP_HOST2 + "Business/GetProduct";
    /**
     * POST
     * Api/Business/InvestmentList  获取余信投资人列表
     */
    public static final String GET_InvestmentList = HttpConnector.APP_HTTP_HOST2 + "Business/InvestmentList";
    /**
     * POST
     * Api/LoanPerson/SelectLoanPersonInfo
     * <p/>
     * Api/LoanPerson/SelectLoanByPage获取借款人信息列表
     */
    public static final String SELECT_LOAN_PERSONINFO = HttpConnector.APP_HTTP_HOST2 + "LoanPerson/SelectLoanByPage";

    /***
     * Api/LoanPerson/SelectTopTen 获取头部借款人信息列表
     * POST
     */
    public static final String SelectTopTen = HttpConnector.APP_HTTP_HOST2 + "LoanPerson/SelectTopTen";
    /**
     * post
     * Api/Business/GetProById
     * 获取余房车信详情
     **/
    public static final String POST_GET_YUFANG_DETAILS = HttpConnector.APP_HTTP_HOST2 + "Business/GetProById";
    /***
     * Api/TopProduct/SelectLoanInfo
     * post
     * 获取借款人资料
     **/
    public static final String GET_BORROWERDATA = HttpConnector.APP_HTTP_HOST2 + "TopProduct/SelectLoanInfo";
    /***
     * Api/Person/GetMortgageRepay
     * post
     * 获取还款记录列表
     **/

    public static final String GET_PAYMENT_HISTORY = HttpConnector.APP_HTTP_HOST2 + "Person/GetMortgageRepay";
    /***
     * Api/TransferController/ApplyTransferProduct
     * post
     * 申请转让
     **/
    public static final String APPLICATION_TRANSFER = HttpConnector.APP_HTTP_HOST2 + "TransferController/ApplyTransferProduct";
    /***
     * Api/TransferController/CancelTransferProduct
     * post
     * 取消转让
     **/
    public static final String CANCEL_TRANSFER = HttpConnector.APP_HTTP_HOST2 + "TransferController/CancelTransferProduct";
    /**
     * POST
     * Api/TransferController/SelectAllTransferProduct   //获取转让列表
     **/
    public static final String POST_GET_TRANSFER_LIST = HttpConnector.APP_HTTP_HOST2 + "TransferController/SelectAllTransferProduct";
    /**
     * POST
     * Api/TransferController/SelectTransferRecord   //获取转让记录列表
     **/
    public static final String GET_TRANSFER_LIST = HttpConnector.APP_HTTP_HOST2 + "TransferController/SelectTransferRecord";
    /**
     * 获取转让记录详情
     * Api/TransferController/SelectMyTransferRecord
     * post
     **/
    public static final String GET_TRANSFER_DETAILS = HttpConnector.APP_HTTP_HOST2 + "TransferController/SelectMyTransferRecord";
    /***
     * 查看转让产品详情
     * Api/TransferController/SelectBuyTransferProduct
     * post
     **/
    public static final String TRANSFER_DETAILS = HttpConnector.APP_HTTP_HOST2 + "TransferController/SelectBuyTransferProduct";
    /****
     * GET
     * Api/Activity/CheckIn 签到
     **/
    public static final String GET_SIGN = HttpConnector.APP_HTTP_HOST2 + "Activity/CheckIn";
    /**
     * POST
     * Api/MoneyCenter/GetLoanPersonInfo
     * 获取借款人列表
     **/
    public static final String GET_SELLOAN_PERSON_LIST = HttpConnector.APP_HTTP_HOST2 + "MoneyCenter/GetLoanPersonInfo";
    /**
     * POST
     * 投资界面的借款人列表弹窗
     * Api/LoanPInfo/SelLoanPerson
     **/
    public static final String SelLoanPerson = HttpConnector.APP_HTTP_HOST2 + "LoanPInfo/SelLoanPerson";
    /**
     * Post
     * Api/MoneyCenter/GetRoundLend
     * <p/>
     * 获取循环出借列表
     **/
    public static final String GET_ROUND_LEND = HttpConnector.APP_HTTP_HOST2 + "MoneyCenter/GetRoundLend";
    /***
     * get
     * Api/lky_GoldAccount/GetBalanceAction
     * 获取余额
     **/
    public static final String GET_BALANCE = HttpConnector.APP_HTTP_HOST2 + "lky_GoldAccount/GetBalanceAction";

    /***
     * get
     * Api/lky_GoldAccount/GetBalanceAction
     * 获取是否已签到
     **/
    public static final String SELECT_IS_SIGN = HttpConnector.APP_HTTP_HOST2 + "Activity/CheckInStatu";
    /***
     * GET
     * Api/Person/GetAssets获取个人财产
     **/
    public static final String GET_ASSETS = HttpConnector.APP_HTTP_HOST2 + "Person/GetAssets";
    /**
     * POST
     * 获取合同
     * Api/BuyProduct/ContratBQ
     **/
    public static final String GET_CONTRACT = HttpConnector.APP_HTTP_HOST2 + "BuyProduct/ContratBQ";
    /***
     * POST
     * Api/Person/GetAlreadyByDDH  获取投资记录
     */
    public static final String GET_INVERSTMENT_RECORD = HttpConnector.APP_HTTP_HOST2 + "Person/GetAlreadyByDDH";
    /**
     * POST
     * Api/NCustomer/SendCode 获取验证码
     */
    public static final String PostSendCode = HttpConnector.APP_HTTP_HOST2 + "NCustomer/SendCode";
    /**
     * POST
     * Api/NCustomer/Register 注册
     */
    public static final String PostRegister = HttpConnector.APP_HTTP_HOST2 + "NCustomer/Register";

    /*
    *  GET Api/Activity/CheckIn 签到送积分
    * */
    public static final String GetSign = HttpConnector.APP_HTTP_HOST2 + "Activity/CheckIn";

    /**
     * POST
     * Api/Customer/UpdateLoginPass2 更改密码
     */

    public static final String PostLogoPass = HttpConnector.APP_HTTP_HOST2 + "Customer/UpdateLoginPass2";

    /**
     * POST
     * Api/Person/EditHeadPhoto 更改头像
     */
    public static final String PostHeadPhoto = HttpConnector.APP_HTTP_HOST2 + "Person/EditHeadPhoto";


    /* POST
    * Api/Person/EditPersonInfos 修改身份信息
    */
    public static final String PostPersonInfos = HttpConnector.APP_HTTP_HOST2 + "Person/EditPersonInfos";

    /**
     * POST
     * Api/Customer/SeekPass 忘记密码 发送短信验证码
     */
    public static final String PostSeekPass = HttpConnector.APP_HTTP_HOST2 + "Customer/SeekPass";
    /*  POST Api/Infomations/GetMessage 获取消息列表
    * */
    public static final String GetMessage = HttpConnector.APP_HTTP_HOST2 + "Infomations/GetMessage";

    /**
     * Post
     * Api/Customer/SeekNext 忘记密码 下一步
     */
    public static final String PostSeekNext = HttpConnector.APP_HTTP_HOST2 + "Customer/SeekNext";

    /**
     * Post
     * Api/Customer/SeekPassWord 忘记密码 最后
     */
    public static final String PostSeekPassWord = HttpConnector.APP_HTTP_HOST2 + "Customer/SeekPassWord";


    /**
     * Post
     * Api/AccountManager/SelManager 获取客户经理信息
     */
    public static final String PostSelManager = HttpConnector.APP_HTTP_HOST2 + "AccountManager/SelManager";
    /**
     * POST Api/Customer/BandCMID 绑定客户用户ID
     */
    public static String BandCMID = HttpConnector.APP_HTTP_HOST2 + "Customer/BandCMID";

    /**
     * Get 获得富有支持的地区
     * Api/lky_GoldAccount/GetAreaIDByParentID
     */
    public static final String GetAreaID = HttpConnector.APP_HTTP_HOST2 + "lky_GoldAccount/GetAreaIDByParentID";

    /**
     * Post
     * Api/Cj_BankCard/GetGoldAccountBank
     */
    public static final String PostAccountBank = HttpConnector.APP_HTTP_HOST2 + "Cj_BankCard/GetGoldAccountBank";
    /**
     * Get收入明细
     * Api/Person/UserTradeDetail
     */
    public static final String GetDetail = HttpConnector.APP_HTTP_HOST2 + "Person/UserTradeDetail";

    //   GET Api/Person/GetUserBankAccount 我的银行卡
    public static final String GetBankCard = HttpConnector.APP_HTTP_HOST2 + "Person/GetUserBankAccount";

    /**
     * GET
     * Api/NCustomer/FyTopUp
     * 充值界面
     */
    public static final String FyTopUp = HttpConnector.APP_HTTP_HOST2 + "NCustomer/FyTopUp";

    /**
     * Post
     * Api/Activity/AllActivitys
     */
    public static final String PostActivitys = HttpConnector.APP_HTTP_HOST2 + "Activity/AllActivitys";


    /*
        *  POST Api/Person/GetUserRedRecord 获取用户红包
        * */
    public static final String GetUserRedRecord = HttpConnector.APP_HTTP_HOST2 + "Person/GetUserRedRecord";

    /*
    *   POST  Api/RedPackage/SelectUseRedPackage 获取用户可用红包
    * */
    public static final String GetUserRedRecord2 = HttpConnector.APP_HTTP_HOST2 + "RedPackage/SelectUseRedPackage";

    /*
    *  POST Api/Person/GetUserTicket 获取用户奖券
    * */
    public static final String GetUserTicket = HttpConnector.APP_HTTP_HOST2 + "Person/GetUserTicket";

    /**
     * POST Api/RedPackage/SelectUseTicket  获取用户可用奖券
     */
    public static final String Get_SelectUseTicket = HttpConnector.APP_HTTP_HOST2 + "RedPackage/SelectUseTicket";


    /**
     * 快速充值
     **/
//    public static final String QUICK_RECHARGE = "http://www-1.fuiou.com:9057/jzh/app/500001.action";
    public static final String QUICK_RECHARGE = "https://jzh.fuiou.com/app/500001.action";
    /***
     * 快速提现
     **/
//    public static final String CASH = "http://www-1.fuiou.com:9057/jzh/app/500003.action";
    public static final String CASH = "https://jzh.fuiou.com/app/500003.action";
    /***
     * 获取签名
     * Api/lky_GoldAccount/GetSign
     **/
    public static final String GETSIGN = HttpConnector.APP_HTTP_HOST2 + "lky_GoldAccount/GetSign";


    /***
     * 获取余信投资介绍
     * Api/Business/xinInvestIntroduce
     **/
    public static final String xinInvestIntroduce = HttpConnector.APP_HTTP_HOST2 + "Business/xinInvestIntroduce";
    /***
     * 获取借款人详情
     * Api/LoanPerson/LoanPersonInfoById
     **/
    public static final String LoanPersonInfoById = HttpConnector.APP_HTTP_HOST2 + "LoanPerson/LoanPersonInfoById";
    /***
     * 获取余信宝协议
     * Api/lky_GoldAccount/GetSign
     **/
    public static final String SeasonRed = HttpConnector.APP_HTTP_HOST2 + "BuyProduct/SeasonRed";
    /***
     * 获取余车宝协议
     * Api/BuyProduct/GetCarModel
     **/
    public static final String GetCarModel = HttpConnector.APP_HTTP_HOST2 + "BuyProduct/GetCarModel";
    /***
     * 获取余房宝协议
     * Api/BuyProduct/GetHouseModel
     **/
    public static final String GetHouseModel = HttpConnector.APP_HTTP_HOST2 + "BuyProduct/GetHouseModel";
    /**
     * 获取商户号
     * Api/Cj_Version/GetCommerceNum
     **/
    public static final String GET_COMMERCE_NUM = HttpConnector.APP_HTTP_HOST2 + "Cj_Version/GetCommerceNum";

    /*
    * GET Api/Person/UserPoints 查询用户最近30天积分
    * */
    public static final String GET_THIRTYUSERPOINTS = HttpConnector.APP_HTTP_HOST2 + "Person/UserPoints";

    /*
    * GET Api/Person/UserSumCredits 用户总积分
    * */
    public static final String GET_USERSUMCREDITS = HttpConnector.APP_HTTP_HOST2 + "Person/UserSumCredits";

    /*
    *GET Api/Person/UserHistoryPoints 查询用户积分历史记录
    * */
    public static final String GET_USERHISTORYPOINTS = HttpConnector.APP_HTTP_HOST2 + "Person/UserHistoryPoints";

    /**
     * 检测金账户状态
     * POST Api/lky_GoldAccount/CheckGoldAccount 检测金账户状态
     */
    public static final String GetCheckGoldAccount = HttpConnector.APP_HTTP_HOST2 + "lky_GoldAccount/CheckGoldAccount";

    /**
     * GET
     * Api/lky_GoldAccount/GetGAUserInfo 获取金账户信息
     */
    public static final String GetGAUserInfo = HttpConnector.APP_HTTP_HOST2 + "lky_GoldAccount/GetGAUserInfo";
    /**
     * Post
     * Api/SpAddress/AddAddress 添加收货地址
     */
    public static final String PostAddAddress = HttpConnector.APP_HTTP_HOST2 + "SpAddress/AddAddress";
    /**
     * Get
     * Api/SpAddress/SelectAddress 查询收货地址
     */
    public static final String GetAddress = HttpConnector.APP_HTTP_HOST2 + "SpAddress/SelectAddress";
    /**
     * Post
     * Api/SpAddress/DeleteAddress 删除地址
     */
    public static final String PostDeleteAddress = HttpConnector.APP_HTTP_HOST2 + "SpAddress/DeleteAddress";

    /**
     * Post
     * Api/SpAddress/EditAddress 修改地址
     */
    public static final String PostEditAddress = HttpConnector.APP_HTTP_HOST2 + "SpAddress/EditAddress";

    /**
     * Post
     * 身份认证
     * Api/Customer/UpdateCardID
     */
    public static final String PostUpdateCardID = HttpConnector.APP_HTTP_HOST2 + "Customer/UpdateCardID";

    /**
     * GET 摇一摇
     * <p/>
     * POST Api/RedPackage/GetMyRedPackage 获取我的红包列表
     */
    public static final String GetMyRedPackage = HttpConnector.APP_HTTP_HOST2 + "RedPackage/GetMyRedPackage";

    /**
     * GET 摇一摇
     * <p/>
     * Api/RedPackage/GetShake
     */
    public static final String yaoyiyao = HttpConnector.APP_HTTP_HOST2 + "RedPackage/GetShake";

    /**
     * GET
     * Api/RedPackage/GetSurplusCount 获取今日剩余次数
     */
    public static final String YaoYiYaoNum = HttpConnector.APP_HTTP_HOST2 + "RedPackage/GetSurplusCount";

    /**
     * GET 规则pop
     * Api/RedPackage/GetRules
     */
    public static final String GetGuize = HttpConnector.APP_HTTP_HOST2 + "RedPackage/GetRules";

    /**
     * POST Api/RedPackage/SGiveRed 赠送红包
     */
    public static final String GiveRedPag = HttpConnector.APP_HTTP_HOST2 + "RedPackage/SGiveRed";

    /*
    * GET Api/Infomations/GetInfomations 获取三种资讯类型的所有数据中的一条数据
    * */
    public static final String Get_Infomations = HttpConnector.APP_HTTP_HOST2 + "Infomations/GetInfomations";

    /*
    * POST Api/Infomations/GetInfomationsByName 通过资讯类型获取详情
    * */
    public static final String Get_InfomationsByName = HttpConnector.APP_HTTP_HOST2 + "Infomations/GetInfomationsByName";


    /**
     * Get
     * 帮助中心
     * Api/Infomations/HelpCenter
     */
    public static final String GetHelpCenter = HttpConnector.APP_HTTP_HOST2 + "Infomations/HelpCenter";

    /**
     * Post
     * 默认地址
     * Api/SpAddress/SetAddress
     */
    public static final String PostSetAddress = HttpConnector.APP_HTTP_HOST2 + "SpAddress/SetAddress";
    /**
     * GET Api/Infomations/PrivateRaise 私募基金
     */
    public static final String Get_PrivateRaise = HttpConnector.APP_HTTP_HOST2 + "Infomations/PrivateRaise";


    /**
     * GET Api/Infomations/AboutPlatform 关于平台
     */
    public static final String Get_AboutPlatform = HttpConnector.APP_HTTP_HOST2 + "Infomations/AboutPlatform";

    /**
     * GET Api/Activity/NewActivity 获取最新活动
     */
    public static final String Get_NewActivity = HttpConnector.APP_HTTP_HOST2 + "Activity/NewActivity";

    /**
     * 余盆资讯的url   https://api.yupen.cn/Information/GetInfoById?Id=
     */
    public static final String Get_yupenzixun = HttpConnector.HTTT_HOST + "/Information/GetInfoById?Id=";

    /**
     * POST
     * Api/RedPackage/SelectRuleById 红包规则
     */
    public static final String PostSelectRuleById = HttpConnector.APP_HTTP_HOST2 + "RedPackage/SelectRuleById";

}
