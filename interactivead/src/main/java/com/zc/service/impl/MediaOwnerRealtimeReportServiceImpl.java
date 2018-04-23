package com.zc.service.impl;

import com.zc.dao.MediaOwnerRealtimeReportDao;
import com.zc.entity.MediaOwnerRealtimeReport;
import com.zc.service.MediaOwnerRealtimeReportService;
import com.zc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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