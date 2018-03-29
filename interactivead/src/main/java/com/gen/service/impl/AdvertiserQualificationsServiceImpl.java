package com.gen.service.impl;

import com.gen.entity.AdvertiserQualifications;
import com.gen.dao.AdvertiserQualificationsDao;
import com.gen.service.AdvertiserQualificationsService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年03月29日 20:17
 */
@Service("AdvertiserQualificationsService")
public class AdvertiserQualificationsServiceImpl extends BaseServiceImpl<AdvertiserQualifications> implements AdvertiserQualificationsService {


	private AdvertiserQualificationsDao dao;

    @Resource
    public void setBaseDao(AdvertiserQualificationsDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}