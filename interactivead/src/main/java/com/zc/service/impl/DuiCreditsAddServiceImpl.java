package com.zc.service.impl;

import com.zc.dao.DuiCreditsAddDao;
import com.zc.entity.DuiCreditsAdd;
import com.zc.service.DuiCreditsAddService;
import com.zc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author solar
 * @date 2018年04月24日 12:01
 */
@Service("DuiCreditsAddService")
public class DuiCreditsAddServiceImpl extends BaseServiceImpl<DuiCreditsAdd> implements DuiCreditsAddService {


	private DuiCreditsAddDao dao;

    @Resource
    public void setBaseDao(DuiCreditsAddDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}