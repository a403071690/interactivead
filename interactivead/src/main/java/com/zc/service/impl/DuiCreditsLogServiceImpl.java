package com.zc.service.impl;

import com.zc.dao.DuiCreditsLogDao;
import com.zc.entity.DuiCreditsLog;
import com.zc.service.DuiCreditsLogService;
import com.zc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author solar
 * @date 2018年04月23日 15:12
 */
@Service("DuiCreditsLogService")
public class DuiCreditsLogServiceImpl extends BaseServiceImpl<DuiCreditsLog> implements DuiCreditsLogService {


	private DuiCreditsLogDao dao;

    @Resource
    public void setBaseDao(DuiCreditsLogDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}