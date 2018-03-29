package com.zc.dao.impl;

import com.zc.dao.UserPrizeLogDao;
import com.zc.dao.impl.BaseDaoImpl;
import com.zc.entity.UserPrizeLog;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @author solar
 * @date 2018年03月26日 14:01
 */
@Repository("UserPrizeLogDao")
public class UserPrizeLogDaoImpl extends BaseDaoImpl<UserPrizeLog> implements UserPrizeLogDao {

//    private final String demoSqlId = entityName + ".demoId";

//    public List demoMethod(Object obj) {
//        return sessionTemplate.selectList(demoSqlId, obj);
//    }

}