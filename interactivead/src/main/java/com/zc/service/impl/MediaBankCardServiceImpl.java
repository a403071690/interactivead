package com.zc.service.impl;

import com.zc.entity.MediaBankCard;
import com.zc.dao.MediaBankCardDao;
import com.zc.service.MediaBankCardService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
 */
@Service("MediaBankCardService")
public class MediaBankCardServiceImpl extends BaseServiceImpl<MediaBankCard> implements MediaBankCardService {


	private MediaBankCardDao dao;

    @Resource
    public void setBaseDao(MediaBankCardDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}