package com.zc.service.impl;

import com.zc.dao.DuiCreditsNotifyDao;
import com.zc.entity.DuiCreditsNotify;
import com.zc.service.DuiCreditsNotifyService;
import com.zc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author solar
 * @date 2018年04月23日 15:12
 */
@Service("DuiCreditsNotifyService")
public class DuiCreditsNotifyServiceImpl extends BaseServiceImpl<DuiCreditsNotify> implements DuiCreditsNotifyService {


	private DuiCreditsNotifyDao dao;

    @Resource
    public void setBaseDao(DuiCreditsNotifyDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}