package com.gen.service.impl;

import com.gen.entity.CampaignCreativeReport;
import com.gen.dao.CampaignCreativeReportDao;
import com.gen.service.CampaignCreativeReportService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年03月29日 20:17
 */
@Service("CampaignCreativeReportService")
public class CampaignCreativeReportServiceImpl extends BaseServiceImpl<CampaignCreativeReport> implements CampaignCreativeReportService {


	private CampaignCreativeReportDao dao;

    @Resource
    public void setBaseDao(CampaignCreativeReportDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}