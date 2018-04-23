package com.zc.service.impl;

import com.zc.dao.SignCashdetailDao;
import com.zc.entity.SignCashdetail;
import com.zc.service.SignCashdetailService;
import com.zc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author solar
 * @date 2018年04月19日 16:43
 */
@Service("SignCashdetailService")
public class SignCashdetailServiceImpl extends BaseServiceImpl<SignCashdetail> implements SignCashdetailService {


	private SignCashdetailDao dao;

    @Resource
    public void setBaseDao(SignCashdetailDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}