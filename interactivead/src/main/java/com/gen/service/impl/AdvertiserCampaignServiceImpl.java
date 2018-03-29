package com.gen.service.impl;

import com.gen.entity.AdvertiserCampaign;
import com.gen.dao.AdvertiserCampaignDao;
import com.gen.service.AdvertiserCampaignService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年03月29日 20:17
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