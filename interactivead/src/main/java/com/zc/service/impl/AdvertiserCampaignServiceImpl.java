package com.zc.service.impl;

import com.zc.entity.AdvertiserCampaign;
import com.zc.dao.AdvertiserCampaignDao;
import com.zc.service.AdvertiserCampaignService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
 */
@Service("AdvertiserCampaignService")
public class AdvertiserCampaignServiceImpl extends BaseServiceImpl<AdvertiserCampaign> implements AdvertiserCampaignService {


	private AdvertiserCampaignDao dao;

    @Resource
    public void setBaseDao(AdvertiserCampaignDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}