package com.test;

import com.alibaba.fastjson.JSONObject;
import com.zc.entity.AdvertiserCampaign;
import com.zc.entity.CampaignCreativeReport;
import com.zc.service.CampaignCreativeReportService;
import com.zc.service.impl.CampaignCreativeReportServiceImpl;
import com.zc.util.RedisPool;
import org.solar.util.DateUtil;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class RedisTest {
    public static void main(String[] args){
        //RedisPool.getJedis().set("name222","adfad");
    //   RedisPool.getJedis().hset("cp","we123","{\"name\":\"zhajja\"}");
     //   System.out.println(RedisPool.getJedis().hget("cp","123123123"));

        //List<AdvertiserCampaign> cplist = JSONObject.parseArray(RedisPool.getJedis().hvals("cp").toString(),AdvertiserCampaign.class);
      //  System.out.println(cplist.get(0).getId());

        //CampaignCreativeReportService service = new CampaignCreativeReportServiceImpl();
       // List<CampaignCreativeReport> campaignCreativeReportList = service.selectByWhere("","");
//
        //年月日十秒显示日期时间
        System.out.println("当前时间："+ DateUtil.format(new Date()));

    /*    Jedis jedis = RedisPool.getJedis();
        jedis.pexpire("8888888888888888",6000000);//一分钟
        jedis.set("99999999999","12341234");

        //jedis.hget();
       System.out.println( jedis.randomKey());
               RedisPool.returnResource(jedis);*/




    }
}
