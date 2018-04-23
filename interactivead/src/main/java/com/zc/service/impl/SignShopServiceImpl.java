package com.zc.service.impl;

import com.zc.dao.SignShopDao;
import com.zc.entity.SignShop;
import com.zc.service.SignShopService;
import com.zc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author solar
 * @date 2018年04月19日 16:43
 */
@Service("SignShopService")
public class SignShopServiceImpl extends BaseServiceImpl<SignShop> implements SignShopService {


	private SignShopDao dao;

    @Resource
    public void setBaseDao(SignShopDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}