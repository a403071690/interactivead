package com.zc.md.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zc.entity.*;
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
    Logger log = Logger.getLogger(SynchrodDateController.class);
    @Autowired
    AdvertiserCreativeService advertiserCreativeService;
    @Autowired
    AdvertiserCampaignService advertiserCampaignService;
    @Autowired
    AdvertiserAccountLogService advertiserAccountLogService;
    @Autowired
    AdvertiserAccountVirtualLogService advertiserAccountVirtualLogService;
    @Autowired
    CampaignCreativeReportService campaignCreativeReportService;
    @Autowired
    UserPrizeLogService userPrizeLogService;
    /**
     * 推送同步功能
     * mysql全量同步到redis
     */
    @RequestMapping(value = "pushRedis")
    public void pushRedis(){
        log.info("推送数据同步，执行时间："+DateUtil.format(new Date()));
        long start = System.currentTimeMillis();
        //取出所有活动数据
        List<AdvertiserCampaign> advertiserCampaignList =  advertiserCampaignService.selectByWhere("isDelete",0);
        //取出所有创意数据
        List<AdvertiserCreative> advertiserCreativeList =  advertiserCreativeService.selectByWhere("state",1,"checkState",1);
        //取出所有充值数据
       // List<AdvertiserAccountLog> advertiserAccountLogList = advertiserAccountLogService.selectByWhere("accountType",1);
        List<Map> mapList = advertiserAccountLogService.executeSql("SELECT advertiser_id,SUM(price) as price    FROM advertiser_account_log GROUP BY advertiser_id");
        Jedis jedis  = RedisPool.getJedis();
        //写入redis hset
        for (AdvertiserCampaign advertiserCampaign:advertiserCampaignList){
            //先写cp  再写campaignid 再写camoaign的对象json
            jedis.hset("cp",advertiserCampaign.getId(), JSONObject.toJSONString(advertiserCampaign));

        }
        for (AdvertiserCreative advertiserCreative:advertiserCreativeList){
            jedis.hset("cp#"+advertiserCreative.getCampaignId(),advertiserCreative.getId(),JSONObject.toJSONString(advertiserCreative));

        }
        //充值的adv_rech#[advertiser_id]	all_price	广告主充值金额（分）
        for (int i=0;i<mapList.size();i++){
            jedis.hset("adv_rech#"+mapList.get(i).get("advertiser_id"),"all_price",mapList.get(i).get("price")+"");
        }
        RedisPool.returnResource(jedis);
        long end  = System.currentTimeMillis();
        long duration = end -start;
        log.info("本次同步用时："+duration);

    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void synchrodDateToRedis(){
        log.info("数据同步开始;本次执行时间："+ DateUtil.format(new Date()));
        long start = System.currentTimeMillis();
        //取出所有活动数据
        List<AdvertiserCampaign> advertiserCampaignList =  advertiserCampaignService.selectByWhere("isDelete",0);
        //取出所有创意数据
        List<AdvertiserCreative> advertiserCreativeList =  advertiserCreativeService.selectByWhere("state",1,"checkState",1);
        //取出所有充值数据
        //List<AdvertiserAccountLog> advertiserAccountLogList = advertiserAccountLogService.selectByWhere("account_type",1);
         List<Map> mapList = advertiserAccountLogService.executeSql("SELECT advertiser_id,SUM(price) as price    FROM advertiser_account_log GROUP BY advertiser_id");
        Jedis jedis = RedisPool.getJedis();
        //写入redis hset
        for (AdvertiserCampaign advertiserCampaign:advertiserCampaignList){
            //先写cp  再写campaignid 再写camoaign的对象json
            jedis.hset("cp",advertiserCampaign.getId(), JSONObject.toJSONString(advertiserCampaign));
        }
        for (AdvertiserCreative advertiserCreative:advertiserCreativeList){
            jedis.hset("cp#"+advertiserCreative.getCampaignId(),advertiserCreative.getId(),JSONObject.toJSONString(advertiserCreative));
        }
        //充值的adv_rech#[advertiser_id]	all_price	广告主充值金额（分）
        for (int i=0;i<mapList.size();i++){
            jedis.hset("adv_rech#"+mapList.get(i).get("advertiser_id"),"all_price",mapList.get(i).get("price")+"");
        }
        RedisPool.returnResource(jedis);
        long end  = System.currentTimeMillis();
        long duration = end -start;
        log.info("本次同步用时："+duration);
    }

    /**
     * 同步广告主消费数据
     * 每天凌晨3点半开始同步
     */
    @Scheduled(cron = "0 30 3 ? * *")
    public void synchrodConsDateToRedis(){
        log.info("广告主消费数据同步开始;本次执行时间："+ DateUtil.format(new Date()));

        long start = System.currentTimeMillis();
        //取出所有消费报表
        List<CampaignCreativeReport> campaignCreativeReportList = campaignCreativeReportService.selectByWhere("","");
        //adv_cons#[advertiser_id]	[YYYYMMDD]#[campaign_id]	广告主消费金额（分）
        Jedis jedis = RedisPool.getJedis();
        for (CampaignCreativeReport campaignCreativeReport:campaignCreativeReportList){
            jedis.hset("adv_cons#"+campaignCreativeReport.getAdvertiserId(),DateUtil.format(campaignCreativeReport.getReportDate(),"yyyyMMdd")+"#"+campaignCreativeReport.getCampaignId(),campaignCreativeReport.getPayMoney().toString());
        }
        RedisPool.returnResource(jedis);
        long end  = System.currentTimeMillis();
        long duration = end -start;
        log.info("本次同步用时："+duration);
    }



}
