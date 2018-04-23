package com.zc.service.impl;

import com.zc.dao.SignFavelistDao;
import com.zc.entity.SignFavelist;
import com.zc.service.SignFavelistService;
import com.zc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author solar
 * @date 2018年04月19日 16:43
 */
@Service("SignFavelistService")
public class SignFavelistServiceImpl extends BaseServiceImpl<SignFavelist> implements SignFavelistService {


	private SignFavelistDao dao;

    @Resource
    public void setBaseDao(SignFavelistDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}