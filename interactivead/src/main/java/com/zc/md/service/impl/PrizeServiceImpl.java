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
import org.solar.util.StringUtil;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.stream.Stream;

@Service("PrizeServiceImpl")
public class PrizeServiceImpl implements PrizeService {
    Logger log = Logger.getLogger(PrizeServiceImpl.class);
    @Override
    public AdvertiserCampaign getBestCampaign(List<AdvertiserCampaign> cplist,String fid) {
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
        Jedis jedis = RedisPool.getJedis();
        while (listIterator.hasNext()){
            AdvertiserCampaign advertiserCampaign = listIterator.next();
            String all_price = jedis.hget("adv_rech#"+advertiserCampaign.getAdvertiserId(),"all_price");
            log.info("adv_rech#"+advertiserCampaign.getAdvertiserId()+"  all_price");
            log.info("本次活动名称："+advertiserCampaign.getCampaignName()+"状态是："+advertiserCampaign.getState());

          if (  advertiserCampaign.getState()==1 &&
                advertiserCampaign.getBeginTime().getTime()< System.currentTimeMillis() &&
                advertiserCampaign.getEndTime().getTime() > System.currentTimeMillis() &&
                StringUtil.isNotEmpty(all_price)
                ){
                  log.info("广告主有余额,从redis取出消费金额，取出充值金额，相减");
                  List<Integer> conlist = JSONObject.parseArray(jedis.hvals("adv_cons#"+advertiserCampaign.getAdvertiserId()).toString(),Integer.class);
                  long consum = conlist.stream().reduce(0, Integer::sum);
                  log.info("计算消费总额："+consum);
                  log.info("===============================================");
                  if (consum>=Integer.parseInt(all_price)){
                      log.info("由于余额不足被筛出的活动2->"+advertiserCampaign.getCampaignName());
                      listIterator.remove();
                  }else{
                          log.info("===============================================");
                          //日预算符合
                          if (advertiserCampaign.getDayPrice()<consum){
                              log.info("由于日预算花完被筛出的活动->"+advertiserCampaign.getCampaignName());
                              listIterator.remove();
                          }
                  }
            }else {
                log.info("第一次筛选去除不符合的活动->"+advertiserCampaign.getCampaignName());
                log.info("删选原因有3种可能：未开启，未在推广期，未充值");
                listIterator.remove();
            }
            log.info("本次剩余活动数："+cplist.size());
        }
        log.info("第一次筛选剩余活动数："+cplist.size());
        log.info("遍历筛选出已经被点击2次以上的活动");

        if (cplist.size()>0){
            ListIterator<AdvertiserCampaign> listIterator2 = cplist.listIterator();
            while (listIterator2.hasNext()){
                AdvertiserCampaign advertiserCampaign = listIterator2.next();
                //String count = jedis.hget("CampaignCount#"+advertiserCampaign.getId()+"#"+fid,"getCampaignCount");
                String count = jedis.hget("CampaignCount#"+fid,advertiserCampaign.getId());
               // cpjedis.hincrBy("CampaignCount#"+fid,advertiserCampaignBest.getId(),1);
                log.info("出现次数：count:"+count);
                    if (StringUtil.isNotEmpty(count)){
                        if(Integer.parseInt(count) >=1){
                            if (cplist.size()>1) {
                                log.info("由于该活动在1分钟之内已经被该客户点击两次筛出的活动->"+advertiserCampaign.getCampaignName());
                                listIterator2.remove();
                            }else {
                                log.info("没有可用的活动了，全部重置一下->"+advertiserCampaign.getCampaignName());
                                log.info("删除先关的fid:CampaignCount#"+fid);
                                //jedis.hdel("CampaignCount#"+fid);
                                jedis.del("CampaignCount#"+fid);
                            }
                        }
                }
            }

            log.info("第2次筛选剩余活动数："+cplist.size());
        }else
        {
            return   null;
        }
        RedisPool.returnResource(jedis);
        log.info("最终剩余活动数："+cplist.size());
        log.info("把所有合格的活动进行规则排序,竞价价格最高优先");
        OptionalLong sum = cplist.stream().mapToLong(AdvertiserCampaign::getBidPrice).min();
        log.info(" 排序最大值是："+sum.getAsLong());
        Stream collect = cplist.stream().sorted(Comparator.comparing(AdvertiserCampaign::getBidPrice).reversed());
        //Stream collect2 = cplist.stream().sorted(Comparator.comparing(AdvertiserCampaign::getBidPrice));
        Optional<AdvertiserCampaign> collectFirst = collect.findFirst();
        //Optional<AdvertiserCampaign> collectFirst2 = collect2.findFirst();
        log.info("最终活动："+collectFirst.get().getCampaignName());
        //log.info("最小创意："+collectFirst2.get().getCampaignName());

        return   collectFirst.orElse(null);
    }

    @Override
    public AdvertiserCreative getBestCreatve(List<AdvertiserCreative> list) {
        log.info("取出该活动下的所有创意");
        System.out.println("创意数目："+list.size());
        if (list.size() > 0) {
            if (list.size()==1){
                AdvertiserCreative advertiserCreative = list.get(0);
                advertiserCreative.setIsPrize("1");
                return  advertiserCreative;
            }else {
                int index =new Random().nextInt(list.size());
                log.info("index:"+index);
                AdvertiserCreative advertiserCreative = list.get(index);
                log.info("随机获取一个："+advertiserCreative.getCreativeName());
                advertiserCreative.setIsPrize("1");
                return  advertiserCreative;
            }
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
