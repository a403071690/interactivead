package com.zc.service.impl;

import com.zc.entity.MediaOwnerInfo;
import com.zc.dao.MediaOwnerInfoDao;
import com.zc.service.MediaOwnerInfoService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
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