package com.zc.service.impl;

import com.zc.entity.AdvertiserInfo;
import com.zc.dao.AdvertiserInfoDao;
import com.zc.service.AdvertiserInfoService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
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