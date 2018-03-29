package com.zc.service.impl;

import com.zc.entity.AdvertiserAccountLog;
import com.zc.dao.AdvertiserAccountLogDao;
import com.zc.service.AdvertiserAccountLogService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
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