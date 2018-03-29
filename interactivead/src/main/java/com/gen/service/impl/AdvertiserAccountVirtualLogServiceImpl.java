package com.gen.service.impl;

import com.gen.entity.AdvertiserAccountVirtualLog;
import com.gen.dao.AdvertiserAccountVirtualLogDao;
import com.gen.service.AdvertiserAccountVirtualLogService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年03月29日 20:17
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