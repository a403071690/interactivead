package com.zc.service.impl;

import com.zc.entity.CampaignCreativeReport;
import com.zc.dao.CampaignCreativeReportDao;
import com.zc.service.CampaignCreativeReportService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
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