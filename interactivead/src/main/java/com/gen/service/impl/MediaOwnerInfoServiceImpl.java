package com.gen.service.impl;

import com.gen.entity.MediaOwnerInfo;
import com.gen.dao.MediaOwnerInfoDao;
import com.gen.service.MediaOwnerInfoService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年03月29日 20:17
 */
@Service("MediaOwnerInfoService")
public class MediaOwnerInfoServiceImpl extends BaseServiceImpl<MediaOwnerInfo> implements MediaOwnerInfoService {


	private MediaOwnerInfoDao dao;

    @Resource
    public void setBaseDao(MediaOwnerInfoDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}