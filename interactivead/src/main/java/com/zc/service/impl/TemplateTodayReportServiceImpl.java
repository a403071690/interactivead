package com.zc.service.impl;

import com.zc.dao.TemplateRealtimeReportDao;
import com.zc.dao.TemplateTodayReportDao;
import com.zc.entity.TemplateRealtimeReport;
import com.zc.entity.TemplateTodayReport;
import com.zc.service.TemplateRealtimeReportService;
import com.zc.service.TemplateTodayReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author solar
 * @date 2018年04月08日 17:40
 */
@Service("TemplateTodayReportService")
public class TemplateTodayReportServiceImpl extends BaseServiceImpl<TemplateTodayReport> implements TemplateTodayReportService {


	private TemplateTodayReportDao dao;

    @Resource
    public void setBaseDao(TemplateTodayReportDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}