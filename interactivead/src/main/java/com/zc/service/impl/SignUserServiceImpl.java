package com.zc.service.impl;

import com.zc.dao.SignUserDao;
import com.zc.entity.SignUser;
import com.zc.service.SignUserService;
import com.zc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author solar
 * @date 2018年04月19日 16:43
 */
@Service("SignUserService")
public class SignUserServiceImpl extends BaseServiceImpl<SignUser> implements SignUserService {


	private SignUserDao dao;

    @Resource
    public void setBaseDao(SignUserDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}