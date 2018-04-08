package com.gen.service.impl;

import com.gen.entity.TemplateRealtimeReport;
import com.gen.dao.TemplateRealtimeReportDao;
import com.gen.service.TemplateRealtimeReportService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

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