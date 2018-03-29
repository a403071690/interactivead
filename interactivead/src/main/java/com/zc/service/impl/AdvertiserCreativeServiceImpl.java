package com.zc.service.impl;

import com.zc.entity.AdvertiserCreative;
import com.zc.dao.AdvertiserCreativeDao;
import com.zc.service.AdvertiserCreativeService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
 */
@Service("AdvertiserCreativeService")
public class AdvertiserCreativeServiceImpl extends BaseServiceImpl<AdvertiserCreative> implements AdvertiserCreativeService {


	private AdvertiserCreativeDao dao;

    @Resource
    public void setBaseDao(AdvertiserCreativeDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}