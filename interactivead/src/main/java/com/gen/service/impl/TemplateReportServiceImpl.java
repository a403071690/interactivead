package com.gen.service.impl;

import com.gen.entity.TemplateReport;
import com.gen.dao.TemplateReportDao;
import com.gen.service.TemplateReportService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年04月08日 17:40
 */
@Service("TemplateReportService")
public class TemplateReportServiceImpl extends BaseServiceImpl<TemplateReport> implements TemplateReportService {


	private TemplateReportDao dao;

    @Resource
    public void setBaseDao(TemplateReportDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}