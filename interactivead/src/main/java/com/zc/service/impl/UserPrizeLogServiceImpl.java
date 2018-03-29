package com.zc.service.impl;

import com.zc.dao.UserPrizeLogDao;
import com.zc.entity.UserPrizeLog;
import com.zc.service.UserPrizeLogService;
import com.zc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author solar
 * @date 2018年03月26日 14:01
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