package com.gen.service.impl;

import com.gen.entity.TemplateManage;
import com.gen.dao.TemplateManageDao;
import com.gen.service.TemplateManageService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年04月08日 17:40
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