package com.zc.service.impl;

import com.zc.dao.SignBuyshoplistDao;
import com.zc.entity.SignBuyshoplist;
import com.zc.service.SignBuyshoplistService;
import com.zc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author solar
 * @date 2018年04月19日 16:43
 */
@Service("SignBuyshoplistService")
public class SignBuyshoplistServiceImpl extends BaseServiceImpl<SignBuyshoplist> implements SignBuyshoplistService {


	private SignBuyshoplistDao dao;

    @Resource
    public void setBaseDao(SignBuyshoplistDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}