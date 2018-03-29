package com.zc.service.impl;

import com.zc.entity.AdvertiserQualifications;
import com.zc.dao.AdvertiserQualificationsDao;
import com.zc.service.AdvertiserQualificationsService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
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