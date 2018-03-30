package com.zc.service.impl;

import com.zc.dao.CampaignCreativeRealtimeReportDao;
import com.zc.entity.CampaignCreativeRealtimeReport;
import com.zc.service.CampaignCreativeRealtimeReportService;
import com.zc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author solar
 * @date 2018年03月29日 20:17
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