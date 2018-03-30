package com.zc.md.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zc.entity.*;
import com.zc.md.service.SynchrodDateService;
import com.zc.service.*;
import com.zc.service.impl.AdvertiserCampaignServiceImpl;
import com.zc.util.RedisPool;
import org.apache.log4j.Logger;
import org.solar.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SynchrodDateController {
    @Autowired
    SynchrodDateService synchrodDateService;
    /**
     * 推送同步功能
     * mysql全量同步到redis
     */
    @RequestMapping(value = "pushRedis")
    public void pushRedis(){
        synchrodDateService.pushRedis();
    }

    /**
     * 每10分钟同步一次
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void synchrodDateToRedis(){
        synchrodDateService.synchrodDateToRedis();
    }

    /**
     * 同步广告主消费数据
     * 每天凌晨3点半开始同步
     */
    @Scheduled(cron = "0 30 3 ? * *")
    public void synchrodConsDateToRedis(){
        synchrodDateService.synchrodConsDateToRedis();
    }



}
