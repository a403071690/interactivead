package com.gen.service.impl;

import com.gen.entity.AdvertiserInfo;
import com.gen.dao.AdvertiserInfoDao;
import com.gen.service.AdvertiserInfoService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年04月08日 17:40
 */
@Service("AdvertiserInfoService")
public class AdvertiserInfoServiceImpl extends BaseServiceImpl<AdvertiserInfo> implements AdvertiserInfoService {


	private AdvertiserInfoDao dao;

    @Resource
    public void setBaseDao(AdvertiserInfoDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}