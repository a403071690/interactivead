package com.gen.service.impl;

import com.gen.entity.CampaignCreativeRealtimeReport;
import com.gen.dao.CampaignCreativeRealtimeReportDao;
import com.gen.service.CampaignCreativeRealtimeReportService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年04月08日 17:40
 */
@Service("CampaignCreativeRealtimeReportService")
public class CampaignCreativeRealtimeReportServiceImpl extends BaseServiceImpl<CampaignCreativeRealtimeReport> implements CampaignCreativeRealtimeReportService {


	private CampaignCreativeRealtimeReportDao dao;

    @Resource
    public void setBaseDao(CampaignCreativeRealtimeReportDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}