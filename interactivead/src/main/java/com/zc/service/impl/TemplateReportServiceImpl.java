package com.zc.service.impl;

import com.zc.entity.TemplateReport;
import com.zc.dao.TemplateReportDao;
import com.zc.service.TemplateReportService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
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