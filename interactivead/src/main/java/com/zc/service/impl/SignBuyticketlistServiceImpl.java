package com.zc.service.impl;

import com.zc.dao.SignBuyticketlistDao;
import com.zc.entity.SignBuyticketlist;
import com.zc.service.SignBuyticketlistService;
import com.zc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author solar
 * @date 2018年04月19日 16:43
 */
@Service("SignBuyticketlistService")
public class SignBuyticketlistServiceImpl extends BaseServiceImpl<SignBuyticketlist> implements SignBuyticketlistService {


	private SignBuyticketlistDao dao;

    @Resource
    public void setBaseDao(SignBuyticketlistDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}