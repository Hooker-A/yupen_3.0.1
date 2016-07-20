package com.huaop2p.yqs.module.one_home.interface_;

import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.one_home.bean.BinnerListBean;
import com.huaop2p.yqs.module.one_home.bean.HomeMseZiXunBean;
import com.huaop2p.yqs.module.one_home.bean.HomeMsgBean;
import com.huaop2p.yqs.module.one_home.bean.ReqProductBean;
import com.huaop2p.yqs.module.one_home.bean.YieldBean;
import com.huaop2p.yqs.module.one_home.bean.YuPenBean;

import java.util.List;

/**
 * Created by maoxiaofei on 2016/4/18.
 *
 * 首頁binner类的回调
 */
public interface CallBean {
     void bean(BinnerListBean bean);
     void beanHomeMsg(BaseResponseEntity<HomeMsgBean> beanMsg);
     void beanHomeProduct(BaseResponseEntity<ReqProductBean> beanProduct);
     void beanHomeMsgZiXun(BaseResponseEntity<List<HomeMseZiXunBean>> beanmsgzixun);
     void beanYield(BaseResponseEntity<YieldBean> beanMsg);
     void beanHomeYuPen(BaseResponseEntity<List<YuPenBean>> beanmsgzixun);

}
