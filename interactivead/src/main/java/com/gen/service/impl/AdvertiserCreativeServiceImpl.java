package com.gen.service.impl;

import com.gen.entity.AdvertiserCreative;
import com.gen.dao.AdvertiserCreativeDao;
import com.gen.service.AdvertiserCreativeService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年03月29日 20:17
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