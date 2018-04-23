package com.zc.service.impl;

import com.zc.dao.SignGolddetailDao;
import com.zc.entity.SignGolddetail;
import com.zc.service.SignGolddetailService;
import com.zc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author solar
 * @date 2018年04月19日 16:43
 */
@Service("SignGolddetailService")
public class SignGolddetailServiceImpl extends BaseServiceImpl<SignGolddetail> implements SignGolddetailService {


	private SignGolddetailDao dao;

    @Resource
    public void setBaseDao(SignGolddetailDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}