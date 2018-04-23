package com.zc.service.impl;

import com.zc.dao.SignOneyuanbuyDao;
import com.zc.entity.SignOneyuanbuy;
import com.zc.service.SignOneyuanbuyService;
import com.zc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author solar
 * @date 2018年04月19日 16:43
 */
@Service("SignOneyuanbuyService")
public class SignOneyuanbuyServiceImpl extends BaseServiceImpl<SignOneyuanbuy> implements SignOneyuanbuyService {


	private SignOneyuanbuyDao dao;

    @Resource
    public void setBaseDao(SignOneyuanbuyDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}