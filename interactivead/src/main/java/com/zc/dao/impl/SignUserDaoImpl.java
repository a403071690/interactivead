package com.zc.dao.impl;

import com.zc.dao.SignUserDao;
import com.zc.dao.impl.BaseDaoImpl;
import com.zc.entity.SignUser;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @author solar
 * @date 2018年04月19日 16:43
 */
@Repository("SignUserDao")
public class SignUserDaoImpl extends BaseDaoImpl<SignUser> implements SignUserDao {

//    private final String demoSqlId = entityName + ".demoId";

//    public List demoMethod(Object obj) {
//        return sessionTemplate.selectList(demoSqlId, obj);
//    }

}