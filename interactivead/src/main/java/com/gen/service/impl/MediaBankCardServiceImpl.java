package com.gen.service.impl;

import com.gen.entity.MediaBankCard;
import com.gen.dao.MediaBankCardDao;
import com.gen.service.MediaBankCardService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author solar
 * @date 2018年04月08日 17:40
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