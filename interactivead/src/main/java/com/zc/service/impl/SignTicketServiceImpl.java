package com.zc.service.impl;

import com.zc.dao.SignTicketDao;
import com.zc.entity.SignTicket;
import com.zc.service.SignTicketService;
import com.zc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author solar
 * @date 2018年04月19日 16:43
 */
@Service("SignTicketService")
public class SignTicketServiceImpl extends BaseServiceImpl<SignTicket> implements SignTicketService {


	private SignTicketDao dao;

    @Resource
    public void setBaseDao(SignTicketDao dao) {
    	super.baseDao = dao;
    	this.dao = dao;
    }

}