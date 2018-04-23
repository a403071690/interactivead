package com.zc.service.impl;

import com.zc.dao.DuiCreditsConsumeDao;
import com.zc.entity.DuiCreditsConsume;
import com.zc.service.DuiCreditsConsumeService;
import com.zc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author solar
 * @date 2018年04月23日 15:12
 */
@Service("DuiCreditsConsumeService")
public class DuiCreditsConsumeServiceImpl extends BaseServiceImpl<DuiCreditsConsume> implements DuiCreditsConsumeService {


	private DuiCreditsConsumeDao dao;

    @Resource
    public void setBaseDao(DuiCreditsConsumeDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}