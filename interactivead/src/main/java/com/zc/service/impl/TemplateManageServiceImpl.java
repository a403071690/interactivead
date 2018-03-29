package com.zc.service.impl;

import com.zc.entity.TemplateManage;
import com.zc.dao.TemplateManageDao;
import com.zc.service.TemplateManageService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
 */
@Service("TemplateManageService")
public class TemplateManageServiceImpl extends BaseServiceImpl<TemplateManage> implements TemplateManageService {


	private TemplateManageDao dao;

    @Resource
    public void setBaseDao(TemplateManageDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}