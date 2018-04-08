package com.gen.service.impl;

import com.gen.entity.AdvertiserAccountLog;
import com.gen.dao.AdvertiserAccountLogDao;
import com.gen.service.AdvertiserAccountLogService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年04月08日 17:40
 */
@Service("AdvertiserAccountLogService")
public class AdvertiserAccountLogServiceImpl extends BaseServiceImpl<AdvertiserAccountLog> implements AdvertiserAccountLogService {


	private AdvertiserAccountLogDao dao;

    @Resource
    public void setBaseDao(AdvertiserAccountLogDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}