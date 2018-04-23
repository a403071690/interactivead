package com.zc.md.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zc.entity.AdvertiserCampaign;
import com.zc.entity.AdvertiserCreative;
import com.zc.entity.CampaignCreativeReport;
import com.zc.entity.TemplateManage;
import com.zc.md.controller.SynchrodDateController;
import com.zc.md.service.SynchrodDateService;
import com.zc.service.*;
import com.zc.util.RedisPool;
import org.apache.log4j.Logger;
import org.solar.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SynchrodDateServiceImpl implements SynchrodDateService{
    Logger log = Logger.getLogger(SynchrodDateServiceImpl.class);
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
    @Autowired
    TemplateManageService templateManageService;

    @Override
    public void pushRedis() {
        log.info("推送数据同步，执行时间："+DateUtil.format(new Date()));
        long start = System.currentTimeMillis();
        //取出所有活动数据
        List<AdvertiserCampaign> advertiserCampaignList =  advertiserCampaignService.selectByWhere();
        //取出所有创意数据
        List<AdvertiserCreative> advertiserCreativeList =  advertiserCreativeService.selectByWhere();
        //取出所有充值数据
        // List<AdvertiserAccountLog> advertiserAccountLogList = advertiserAccountLogService.selectByWhere("accountType",1);
        //取出所有模板数据
        List<TemplateManage> templateManageList =  templateManageService.selectByWhere();

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

        //模板写入redis
        for (TemplateManage t:templateManageList){
            jedis.hset("template",t.getId(),JSONObject.toJSONString(t));
        }

        RedisPool.returnResource(jedis);
        long end  = System.currentTimeMillis();
        long duration = end -start;
        log.info("本次同步用时："+duration);
    }

    @Override
    public void synchrodDateToRedis() {
        log.info("数据同步开始;本次执行时间："+ DateUtil.format(new Date()));

        long start = System.currentTimeMillis();
        //取出所有活动数据
        List<AdvertiserCampaign> advertiserCampaignList =  advertiserCampaignService.selectByWhere();
        //取出所有创意数据
        List<AdvertiserCreative> advertiserCreativeList =  advertiserCreativeService.selectByWhere();
        //取出所有模板数据
        List<TemplateManage> templateManageList =  templateManageService.selectByWhere();
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
        //模板写入redis
        for (TemplateManage t:templateManageList){
            jedis.hset("template",t.getId(),JSONObject.toJSONString(t));
        }
        RedisPool.returnResource(jedis);
        long end  = System.currentTimeMillis();
        long duration = end -start;
        log.info("本次同步用时："+duration);
    }

    /**
     * 只执行一次
     */
    @Override
    public void synchrodConsDateToRedis() {
        log.info("广告主消费数据同步开始;本次执行时间："+ DateUtil.format(new Date()));

        long start = System.currentTimeMillis();
        //取出所有消费报表
        List<CampaignCreativeReport> campaignCreativeReportList = campaignCreativeReportService.selectByWhere();
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
