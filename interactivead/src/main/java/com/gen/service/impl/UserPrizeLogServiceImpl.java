package com.gen.service.impl;

import com.gen.entity.UserPrizeLog;
import com.gen.dao.UserPrizeLogDao;
import com.gen.service.UserPrizeLogService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年04月08日 17:40
 */
@Service("UserPrizeLogService")
public class UserPrizeLogServiceImpl extends BaseServiceImpl<UserPrizeLog> implements UserPrizeLogService {


	private UserPrizeLogDao dao;

    @Resource
    public void setBaseDao(UserPrizeLogDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}