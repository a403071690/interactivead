package com.zc.md.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zc.entity.AdvertiserCampaign;
import com.zc.entity.AdvertiserCreative;
import com.zc.md.entity.CreativeClick;
import com.zc.md.entity.CreativeImp;
import com.zc.md.service.PrizeService;
import com.zc.util.CustomLogUtil;
import com.zc.util.RedisPool;
import org.apache.log4j.Logger;
import org.solar.bean.JsonResult;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.stream.Stream;

@Service("PrizeServiceImpl")
public class PrizeServiceImpl implements PrizeService {
    Logger log = Logger.getLogger(PrizeServiceImpl.class);
    @Override
    public AdvertiserCampaign getBestCampaign(List<AdvertiserCampaign> cplist) {
        log.info("遍历一遍找出符合条件的活动");
        /*  1. 活动和创意状态
            2. 是否符合投放时间
            3. 地域定向
            4. 天预算是否剩余
            5. 广告主余额是否充足
            6.频次控制*/
        if (cplist.size() == 0 ) {
            log.error("当前数据库不存在活动");
          return null;
        }

        //list for循环里面不能家remove,需要remove操作要用iterrator遍历
        ListIterator<AdvertiserCampaign> listIterator = cplist.listIterator();
        log.info("遍历所有符合条件的活动");
        while (listIterator.hasNext()){
            AdvertiserCampaign advertiserCampaign = listIterator.next();
            log.info("本次活动名称："+advertiserCampaign.getCampaignName());
            if (advertiserCampaign.getState()==0){
                log.info("去除活动状态暂停的活动"+advertiserCampaign.getCampaignName());
                listIterator.remove();
            }

            if (advertiserCampaign.getBeginTime().getTime()> System.currentTimeMillis() &&
                    advertiserCampaign.getEndTime().getTime() < System.currentTimeMillis() ){
                log.info("去除活动不再投放期间的活动："+advertiserCampaign.getCampaignName());
                listIterator.remove();
            }
            log.info("广告主余额充足,从redis取出消费金额，取出充值金额，相减");
            log.info("adv_rech#"+advertiserCampaign.getAdvertiserId()+"  all_price");
            Jedis jedis = RedisPool.getJedis();
            String all_price = jedis.hget("adv_rech#"+advertiserCampaign.getAdvertiserId(),"all_price");
            log.info("充值金额:"+all_price);
            List<Integer> conlist = JSONObject.parseArray(jedis.hvals("adv_cons#"+advertiserCampaign.getAdvertiserId()).toString(),Integer.class);
            RedisPool.returnResource(jedis);
            long consum = conlist.stream().reduce(0, Integer::sum);
            log.info("计算消费总额："+consum);
            if (consum>=Integer.parseInt(all_price)){
                log.info("去除余额不足的活动："+advertiserCampaign.getCampaignName());
                listIterator.remove();
            }
            //日预算符合
            if (advertiserCampaign.getDayPrice()<consum){
                log.info("去除日预算花完的活动："+advertiserCampaign.getCampaignName());
                listIterator.remove();
            }
            log.info("剩余活动数："+cplist.size());
        }

        log.info("把所有合格的活动进行规则排序,竞价价格最高优先");
        OptionalLong sum = cplist.stream().mapToLong(AdvertiserCampaign::getBidPrice).max();
        log.info(" 排序");
        Stream collect = cplist.stream().sorted(Comparator.comparing(AdvertiserCampaign::getBidPrice));

        Optional<AdvertiserCampaign> collectFirst = collect.findFirst();
        return   collectFirst.orElse(null);
    }

    @Override
    public AdvertiserCreative getBestCreatve(List<AdvertiserCreative> list) {
        log.info("取出该活动下的所有创意");
        System.out.println("创意数目："+list.size());
        if (list.size() > 0) {
            AdvertiserCreative advertiserCreative = list.stream().findAny().get();
            advertiserCreative.setIsPrize("1");
            return  advertiserCreative;
        }else {
            AdvertiserCreative advertiserCreative = new AdvertiserCreative();
            advertiserCreative.setIsPrize("0");
            return advertiserCreative;
        }

    }

    @Override
    public void writeCreativeImplLog(CreativeImp creativeImp) {
         Object json = JSONObject.toJSON(creativeImp);
        CustomLogUtil.putCreativeImpLog(json.toString());
    }

    @Override
    public void writeCreativeClicklLog(CreativeClick creativeClick) {
        Object json = JSONObject.toJSON(creativeClick);
        CustomLogUtil.putCreativeClickLog(json.toString());
    }
}
