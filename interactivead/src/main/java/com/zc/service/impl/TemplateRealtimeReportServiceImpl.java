package com.zc.service.impl;

import com.zc.dao.TemplateRealtimeReportDao;
import com.zc.entity.TemplateRealtimeReport;
import com.zc.service.TemplateRealtimeReportService;
import com.zc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author solar
 * @date 2018年04月08日 17:40
 */
@Service("TemplateRealtimeReportService")
public class TemplateRealtimeReportServiceImpl extends BaseServiceImpl<TemplateRealtimeReport> implements TemplateRealtimeReportService {


	private TemplateRealtimeReportDao dao;

    @Resource
    public void setBaseDao(TemplateRealtimeReportDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}