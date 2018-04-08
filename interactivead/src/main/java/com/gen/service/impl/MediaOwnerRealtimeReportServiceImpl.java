package com.gen.service.impl;

import com.gen.entity.MediaOwnerRealtimeReport;
import com.gen.dao.MediaOwnerRealtimeReportDao;
import com.gen.service.MediaOwnerRealtimeReportService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年04月08日 17:40
 */
@Service("MediaOwnerRealtimeReportService")
public class MediaOwnerRealtimeReportServiceImpl extends BaseServiceImpl<MediaOwnerRealtimeReport> implements MediaOwnerRealtimeReportService {


	private MediaOwnerRealtimeReportDao dao;

    @Resource
    public void setBaseDao(MediaOwnerRealtimeReportDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}