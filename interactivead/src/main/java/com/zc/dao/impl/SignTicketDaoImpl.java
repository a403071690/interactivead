package com.zc.dao.impl;

import com.zc.dao.SignTicketDao;
import com.zc.dao.impl.BaseDaoImpl;
import com.zc.entity.SignTicket;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @author solar
 * @date 2018年04月19日 16:43
 */
@Repository("SignTicketDao")
public class SignTicketDaoImpl extends BaseDaoImpl<SignTicket> implements SignTicketDao {

//    private final String demoSqlId = entityName + ".demoId";

//    public List demoMethod(Object obj) {
//        return sessionTemplate.selectList(demoSqlId, obj);
//    }

}