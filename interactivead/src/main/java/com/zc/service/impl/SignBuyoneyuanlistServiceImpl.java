package com.zc.service.impl;

import com.zc.dao.SignBuyoneyuanlistDao;
import com.zc.entity.SignBuyoneyuanlist;
import com.zc.service.SignBuyoneyuanlistService;
import com.zc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author solar
 * @date 2018年04月19日 16:43
 */
@Service("SignBuyoneyuanlistService")
public class SignBuyoneyuanlistServiceImpl extends BaseServiceImpl<SignBuyoneyuanlist> implements SignBuyoneyuanlistService {


	private SignBuyoneyuanlistDao dao;

    @Resource
    public void setBaseDao(SignBuyoneyuanlistDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}