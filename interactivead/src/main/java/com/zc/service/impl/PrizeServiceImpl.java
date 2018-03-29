package com.zc.service.impl;

import com.zc.md.entity.Prize;
import com.zc.dao.PrizeDao;
import com.zc.service.PrizeService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
 */
@Service("PrizeService")
public class PrizeServiceImpl extends BaseServiceImpl<Prize> implements PrizeService {


	private PrizeDao dao;

    @Resource
    public void setBaseDao(PrizeDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}