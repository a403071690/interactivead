package com.zc.Listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zc.entity.AdvertiserAccountLog;
import com.zc.entity.AdvertiserCampaign;
import com.zc.entity.AdvertiserCreative;
import com.zc.entity.CampaignCreativeReport;
import com.zc.service.AdvertiserAccountLogService;
import com.zc.service.AdvertiserCampaignService;
import com.zc.service.AdvertiserCreativeService;
import com.zc.service.CampaignCreativeReportService;
import com.zc.service.impl.AdvertiserCampaignServiceImpl;
import com.zc.util.RedisPool;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.solar.util.DateUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author dlk
 * 初始化类
 * 1.同步数据服务mysql-->redis
 */
public class SystemInitListener implements ServletContextListener{
    Logger log = Logger.getLogger(SystemInitListener.class);
    //获取spring注入的bean对象
    private WebApplicationContext springContext;
    private AdvertiserCampaignService advertiserCampaignService;
    private AdvertiserCreativeService advertiserCreativeService;
    private CampaignCreativeReportService campaignCreativeReportService;
    private AdvertiserAccountLogService advertiserAccountLogService;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println(">>>初始化参数");
      springContext = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        if(springContext != null){
            advertiserCampaignService = (AdvertiserCampaignService)springContext.getBean("AdvertiserCampaignService");
            advertiserCreativeService  = (AdvertiserCreativeService)springContext.getBean("AdvertiserCreativeService");
            campaignCreativeReportService = (CampaignCreativeReportService)springContext.getBean("CampaignCreativeReportService");
            advertiserAccountLogService = (AdvertiserAccountLogService)springContext.getBean("AdvertiserAccountLogService");
        }else{
            System.out.println("获取应用程序上下文失败!");
        }
        //取出所有活动数据
        //============
        log.info("数据同步开始;本次执行时间："+ DateUtil.format(new Date()));
        long start = System.currentTimeMillis();
        //取出所有活动数据
        List<AdvertiserCampaign> advertiserCampaignList =  advertiserCampaignService.selectByWhere("isDelete",0);
        //取出所有创意数据
        List<AdvertiserCreative> advertiserCreativeList =  advertiserCreativeService.selectByWhere("state",1);
        //取出所有充值数据
        //List<AdvertiserAccountLog> advertiserAccountLogList = advertiserAccountLogService.selectByWhere("account_type",1);
        List<Map> mapList = advertiserAccountLogService.executeSql("SELECT advertiser_id,SUM(price) as price    FROM advertiser_account_log GROUP BY advertiser_id");
        Jedis jedis = RedisPool.getJedis();
        log.info("清空redis");
        jedis.flushDB();
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

        log.info("服务器启动同步一次广告主消费数据;本次执行时间："+ DateUtil.format(new Date()));
        //取出所有消费报表
        List<CampaignCreativeReport> campaignCreativeReportList = campaignCreativeReportService.selectByWhere("","");
        //adv_cons#[advertiser_id]	[YYYYMMDD]#[campaign_id]	广告主消费金额（分）
        for (CampaignCreativeReport campaignCreativeReport:campaignCreativeReportList){
            jedis.hset("adv_cons#"+campaignCreativeReport.getAdvertiserId(),DateUtil.format(campaignCreativeReport.getReportDate(),"yyyyMMdd")+"#"+campaignCreativeReport.getCampaignId(),campaignCreativeReport.getPayMoney().toString());
        }
        RedisPool.returnResource(jedis);
        long end  = System.currentTimeMillis();
        long duration = end -start;
        log.info("本次同步用时："+duration);

}

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("程序关闭");
    }
}
