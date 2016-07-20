package com.huaop2p.yqs.module.two_wealth.model.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.DBEntity;
import com.huaop2p.yqs.module.base.model.BaseModel;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.two_wealth.model.entity.Transfer;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

/**
 * Created by Administrator on 2016/5/12.
 * 作者：任洋
 * 功能：
 */
public class TranferModelImpl extends BaseModel<Transfer> {
    /***
     * 加载本地数据 type:区分余信余房余车
     **/
    @Override
    public List<Transfer> loadNativeData() {
        List<Transfer> yuXinBaos = null;
        try {
            if (AppApplication.dbUtils.tableIsExist(DBEntity.class)) {
                DBEntity dbEntity = AppApplication.dbUtils.findFirst(Selector.from(DBEntity.class).where("className", "=", Transfer.class.getName()));
                if (dbEntity != null)
                    yuXinBaos = gson.fromJson(dbEntity.content, new TypeToken<List<Transfer>>() {
                    }.getType());
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return yuXinBaos;
    }

    /***
     * 删除本地数据
     **/
    @Override
    public void delAllDatas() {
        try {
            AppApplication.dbUtils.delete(DBEntity.class, WhereBuilder.b("className", "=", Transfer.class.getName()));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /***
     * 保存数据到本地
     **/
    @Override
    public void saveDatas(List<Transfer> t) {
        try {
            DBEntity dbEntity = new DBEntity();
            dbEntity.className = Transfer.class.getName();
            dbEntity.content = gson.toJson(t);
            AppApplication.dbUtils.save(dbEntity);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
