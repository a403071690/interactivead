package com.zc.service.impl;

import com.zc.entity.MediaOwnerReport;
import com.zc.dao.MediaOwnerReportDao;
import com.zc.service.MediaOwnerReportService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
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