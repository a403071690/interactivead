package com.gen.service.impl;

import com.gen.entity.MediaOwnerReport;
import com.gen.dao.MediaOwnerReportDao;
import com.gen.service.MediaOwnerReportService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年04月08日 17:40
 */
@Service("MediaOwnerReportService")
public class MediaOwnerReportServiceImpl extends BaseServiceImpl<MediaOwnerReport> implements MediaOwnerReportService {


	private MediaOwnerReportDao dao;

    @Resource
    public void setBaseDao(MediaOwnerReportDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}