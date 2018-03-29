package com.zc.service.impl;

import com.zc.entity.AdvertiserAccountVirtualLog;
import com.zc.dao.AdvertiserAccountVirtualLogDao;
import com.zc.service.AdvertiserAccountVirtualLogService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
 */
@Service("AdvertiserAccountVirtualLogService")
public class AdvertiserAccountVirtualLogServiceImpl extends BaseServiceImpl<AdvertiserAccountVirtualLog> implements AdvertiserAccountVirtualLogService {


	private AdvertiserAccountVirtualLogDao dao;

    @Resource
    public void setBaseDao(AdvertiserAccountVirtualLogDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}