package com.zc.service.impl;

import com.zc.dao.AdvertiserInvoiceDao;
import com.zc.entity.AdvertiserInvoice;
import com.zc.service.AdvertiserInvoiceService;
import com.zc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author solar
 * @date 2018年04月08日 17:11
 */
@Service("AdvertiserInvoiceService")
public class AdvertiserInvoiceServiceImpl extends BaseServiceImpl<AdvertiserInvoice> implements AdvertiserInvoiceService {


	private AdvertiserInvoiceDao dao;

    @Resource
    public void setBaseDao(AdvertiserInvoiceDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}