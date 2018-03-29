package com.zc.md.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.regexp.internal.RE;
import com.zc.entity.AdvertiserCampaign;
import com.zc.entity.AdvertiserCreative;
import com.zc.entity.CampaignCreativeReport;
import com.zc.entity.UserPrizeLog;
import com.zc.md.entity.CreativeClick;
import com.zc.md.entity.CreativeImp;
import com.zc.md.entity.Prize;
import com.zc.md.service.PrizeService;
import com.zc.md.service.impl.PrizeServiceImpl;


import com.zc.service.AdvertiserCreativeService;
import com.zc.service.UserPrizeLogService;
import com.zc.util.Config;
import com.zc.util.CookieUtil;
import com.zc.util.RedisPool;
import com.zc.util.TokenUtil;
import org.apache.log4j.Logger;
import org.solar.bean.JsonResult;
import org.solar.coder.AESCoder;
import org.solar.util.DateUtil;
import org.solar.util.IDGenerater;
import org.solar.util.JsonUtil;
import org.solar.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class PrizeController {
    
    Logger log = Logger.getLogger(PrizeController.class);
    @Autowired
    PrizeServiceImpl prizeService;
    @Autowired
    UserPrizeLogService userPrizeLogService;
    @Autowired
    AdvertiserCreativeService advertiserCreativeService;
    static AESCoder aesCoder = new AESCoder(Config.aesPassword);

   static String click_url_prefix =  Config.get("click_url_prefix");
    /**
     * 请求奖品
     * @param "tid":"模板id","fid":"指纹id",
     * @return
     @author dlk
     创意检索
     1. 活动和创意状态
     2. 是否符合投放时间
     3. 地域定向
     4. 曝光频次控制
     5. 广告主余额是否充足

     排序
     1. CPC竞价排序
     */
    @RequestMapping(value = "/prize")
    @ResponseBody
    public JsonResult isPrize(HttpServletRequest req,HttpServletResponse response, @RequestParam Map requestMap,
                              @RequestParam(value = "tid", required = false) String tid,
                              @RequestParam(value = "fid", required = false) String fid){

        if (StringUtil.isEmpty(fid)){
            return  JsonResult.error("fid不能为空");
        }

        CookieUtil.addCookie(response,"tid",tid,60*60*24*7);
        long start = System.currentTimeMillis();
        log.info("tid:"+tid+"fid:"+fid);
        CookieUtil.addCookie(response,"fid",fid,60*60*24*7);//保存7天
        log.info("redis取出所有活动");
        Jedis cpjedis = RedisPool.getJedis();
        List<AdvertiserCampaign> cplist = JSONObject.parseArray(cpjedis.hvals("cp").toString(),AdvertiserCampaign.class);
        AdvertiserCampaign advertiserCampaignBest = advertiserCampaignBest = prizeService.getBestCampaign(cplist);
        AdvertiserCreative advertiserCreativeBest = null;

        if (advertiserCampaignBest!=null){

            log.info("redis取出该活动下的创意:"+advertiserCampaignBest.getCampaignName());
            List<AdvertiserCreative> advertiserCreativelist = JSONObject.parseArray(cpjedis.hvals("cp#"+advertiserCampaignBest.getId()).toString(),AdvertiserCreative.class);
            advertiserCreativeBest = prizeService.getBestCreatve(advertiserCreativelist);
            RedisPool.returnResource(cpjedis);
            //写入中奖信息
            if (advertiserCreativeBest!=null) {
                log.info("最佳创意："+advertiserCreativeBest.getCreativeName());
                UserPrizeLog userPrizeLog = new UserPrizeLog();
                userPrizeLog.setChannelId(CookieUtil.getUid(req, "cid"));//渠道id
                userPrizeLog.setCreateTime(DateUtil.format(new Date()));
                userPrizeLog.setCreativeId(advertiserCreativeBest.getId());
                long end2 = System.currentTimeMillis();
                userPrizeLog.setDuration((end2-start) + "");//
                userPrizeLog.setId(IDGenerater.getNextId());
                userPrizeLog.setMediaOwnerId( CookieUtil.getUid(req, "mid"));//meitiid
                userPrizeLog.setPrice(advertiserCampaignBest.getBidPrice() + "");
                userPrizeLog.setPrizeId("");//目前没有奖品表
                userPrizeLog.setTemplateId(tid);//模板
                userPrizeLog.setType("1");
                userPrizeLog.setUserCookieId( CookieUtil.getUid(req, "cookieid"));//cookieid
                userPrizeLog.setUserFingerId(fid);
                userPrizeLog.setUserOpenId("");
                userPrizeLog.setAdvertiserId(advertiserCampaignBest.getAdvertiserId());
                userPrizeLogService.save(userPrizeLog);
            }else
            {
                log.error("没有符合条件的创意1");
                return JsonResult.error("没有符合条件的创意");
            }
        }else {
            log.error("没有符合条件的活动2");
            return JsonResult.error("没有符合条件的活动");
        }

        if (StringUtil.isNotEmpty(advertiserCreativeBest.getCreativeName())) {
            log.info("写创意曝光日志");
            CreativeImp creativeImp = new CreativeImp();
            creativeImp.setId(IDGenerater.getNextId());
            creativeImp.setTemplate_id(tid);
            creativeImp.setUser_finger_id(fid);
            creativeImp.setUser_open_id("");
            creativeImp.setType("1");
            creativeImp.setPrice(advertiserCampaignBest.getBidPrice() + "");
            creativeImp.setAdvertiser_id(advertiserCampaignBest.getAdvertiserId());
            creativeImp.setCreative_id(advertiserCreativeBest.getId());
            creativeImp.setCampaign_id(advertiserCampaignBest.getId());
            creativeImp.setMedia_owner_id(CookieUtil.getUid(req, "mid"));
            creativeImp.setUser_cookie_id(CookieUtil.getUid(req, "cookieid"));
            creativeImp.setChannel_id(CookieUtil.getUid(req, "cid"));
            if (StringUtil.isEmpty(CookieUtil.getUid(req, "mid"))){
                creativeImp.setMedia_owner_id("");
            }
            if (StringUtil.isEmpty(CookieUtil.getUid(req, "cookieid"))){
                creativeImp.setUser_cookie_id("");
            }
            if (StringUtil.isEmpty(CookieUtil.getUid(req, "cid"))){
                creativeImp.setChannel_id("");
            }
            creativeImp.setCreate_time(DateUtil.format(new Date()));

            long end = System.currentTimeMillis();
            long duration = end - start;
            creativeImp.setDuration(duration + "");
            prizeService.writeCreativeImplLog(creativeImp);
            //将创意信息写入token响应给用户
            Map<String, String> map = new HashMap<String, String>();
            map.put("advertiserCreativeid", advertiserCreativeBest.getId());
            //  map.put("userPrizeLogId",u.getId());
            map.put("advertiser_id", advertiserCampaignBest.getAdvertiserId());
            map.put("fid", fid);
            String token = aesCoder.AESEncode(JsonUtil.toJSONString(map));
            advertiserCreativeBest.setCtTargetUrl(click_url_prefix + token);
            log.info("取最优创意响应给用户:" + advertiserCreativeBest.getCreativeName());

        }
        return JsonResult.success(advertiserCreativeBest);
    }
    /**
     * 请求奖品列表
     * @param  fid
     * @return
     * 奖品数组
     */
    @ResponseBody
    @RequestMapping(value = "getPrizeList")
    public JsonResult getPrizeList(HttpServletRequest req,@RequestParam Map requestMap,
                                   @RequestParam(value = "fid", required = true) String fid) {

        long start = System.currentTimeMillis();
        log.info("fid:"+fid);
        //根据fid查询所得奖品id
        if (StringUtil.isEmpty(fid)){
            return JsonResult.error("fid不能为空");
        }
        List<UserPrizeLog> userPrizeLogList = userPrizeLogService.selectByWhere("userFingerId",fid);
        log.info("奖品数目："+userPrizeLogList.size());
        //List<AdvertiserCreative> advertiserCreativeList = new ArrayList<AdvertiserCreative>();
        List<Prize> prizeList = new ArrayList<Prize>();
        if (userPrizeLogList.size()>0){
           for (UserPrizeLog u :userPrizeLogList){
               AdvertiserCreative advertiserCreative =  advertiserCreativeService.getById(u.getCreativeId());
               //advertiserCreativeList.add(advertiserCreative);
               Prize prize = new Prize();
               prize.setImg_url(advertiserCreative.getCtImgUrl());
               prize.setTitle(advertiserCreative.getCtTitle());
               /*private String  id;//	主键，32位UUID
               private String  template_id;//	模板ID
               private String  media_owner_id;//	媒体主ID
               private String  channel_id;//	渠道ID
               private String  user_cookie_id;//	用户ID，基于Cookie
               private String  user_open_id;//	微信用户唯一ID
               private String  user_finger_id;//	用户指纹ID，基于JS
               private String  type;//	类型：1创意 2奖品
               private String  creative_id;//	创意ID
               private String  prize_id;//	奖品ID
               private String  price;//	点击价格（整数，分）
               private String  duration;//	运行时间，毫秒数
               private String  create_time;//	时间
               private String advertiser_id;//广告主id
               private String campaign_id;//活动id*/
               //将创意信息写入token
               Map<String,String> map = new HashMap<String,String>();
               map.put("advertiserCreativeid",advertiserCreative.getId());
               map.put("userPrizeLogId",u.getId());
               map.put("advertiser_id",u.getAdvertiserId());
               map.put("fid",fid);
               String token = aesCoder.AESEncode(JsonUtil.toJSONString(map));
               prize.setClk_url(click_url_prefix+token);//advertiserCreative.getCtTargetUrl());

               prize.setTime(DateUtil.parse(u.getCreateTime()));
               log.info("有效期为当前创建日期加3个月："+DateUtil.format(DateUtil.addDay(DateUtil.parse(u.getCreateTime()),90),"yyyy-MM-dd HH:mm:ss"));
               prize.setEnd_time(DateUtil.parse(DateUtil.format(DateUtil.addDay(DateUtil.parse(u.getCreateTime()),90),"yyyy-MM-dd HH:mm:ss")));
               prize.setState("0");
               prizeList.add(prize);
           }
        }
        return JsonResult.success(prizeList);

    }

    /**
     * 创意点击接口
     * @param  token
     * @return
     * 奖品数组
     */
    @RequestMapping(value = "adclick")
    public String adclick(HttpServletRequest req, HttpServletResponse response ,@RequestParam Map requestMap,
                              @RequestParam(value = "token", required = true) String token) {
        log.info("token:"+token);
        CreativeClick creativeClick = new CreativeClick();
        long start = System.currentTimeMillis();
        //根据token查询创意url
        String tokens = aesCoder.AESDecode(token);
        Map<String,String> map = JsonUtil.parseObject(tokens);
        String advertiserCreativeid = map.get("advertiserCreativeid");

        String userPrizeLogId = map.get("userPrizeLogId");
        String advertiser_id = map.get("advertiser_id");
        String fid = map.get("fid");
        log.info("fid:"+fid);
        AdvertiserCreative advertiserCreative = advertiserCreativeService.getById(advertiserCreativeid);
       // UserPrizeLog userPrizeLog = userPrizeLogService.getById(userPrizeLogId);
        String click_url = advertiserCreative.getCtTargetUrl();
        log.info("advertiserCreative.getCampaignId()："+advertiserCreative.getCampaignId());
        //查询点击单价
        Jedis jedis = RedisPool.getJedis();
        //jedis.hget("cp",advertiserCreative.getCampaignId());
        JSON json = JSONObject.parseObject(jedis.hget("cp",advertiserCreative.getCampaignId()));
        AdvertiserCampaign advertiserCampaign = JSONObject.toJavaObject(json,AdvertiserCampaign.class);
        log.info("creativeClick.creative_id:"+advertiserCreativeid);
        //价格是
        long bidprice = advertiserCampaign.getBidPrice();
        //查看1分钟之内是否已经有过点击
        Boolean clickexists = jedis.exists("click#"+advertiserCreative.getCampaignId()+"#"+fid);
        log.info("clickexists:"+clickexists);
        log.info("advertiserCreative.getCampaignId():"+advertiserCreative.getCampaignId());
        if(!clickexists){
            log.info("广告主消费金额（分）增量 扣费");
            //广告主消费金额（分）增量 扣费
            jedis.hincrBy("adv_cons#"+advertiser_id,DateUtil.format(new Date(),"yyyyMMdd")+"#"+advertiserCreative.getCampaignId(),bidprice);
            log.info("记录点击信息保存redis 1分钟");
            //记录点击信息保存redis 1分钟 click#[campaign_id]#[finger_id]
            jedis.hset("click#"+advertiserCreative.getCampaignId()+"#"+fid,"1","1");
            jedis.pexpire("click#"+advertiserCreative.getCampaignId()+"#"+fid,60000);//一分钟
            creativeClick.setIsValid("1");
        }else {
            creativeClick.setIsValid("0");
        }
        RedisPool.returnResource(jedis);
        //点击写入log
        log.info("写入点击日志log");

        creativeClick.setAdvertiser_id(advertiser_id);
        creativeClick.setCampaign_id(advertiserCreative.getCampaignId());
        creativeClick.setChannel_id(CookieUtil.getUid(req,"cid"));
        creativeClick.setCreate_time(DateUtil.format(new Date()));
        creativeClick.setCreative_id(advertiserCreative.getId());
        creativeClick.setId(IDGenerater.getNextId());
        creativeClick.setMedia_owner_id(CookieUtil.getUid(req,"mid"));
        creativeClick.setPrice(bidprice+"");
        creativeClick.setTemplate_id(CookieUtil.getUid(req,"tid"));
        creativeClick.setUser_cookie_id(CookieUtil.getUid(req,"cookieid"));
        if (StringUtil.isEmpty(CookieUtil.getUid(req, "mid"))){
            creativeClick.setMedia_owner_id("");
        }
        if (StringUtil.isEmpty(CookieUtil.getUid(req, "cookieid"))){
            creativeClick.setUser_cookie_id("");
        }
        if (StringUtil.isEmpty(CookieUtil.getUid(req, "cid"))){
            creativeClick.setChannel_id("");
        } if (StringUtil.isEmpty(CookieUtil.getUid(req, "tid"))){
            creativeClick.setTemplate_id("");
        }
        creativeClick.setUser_finger_id(fid);
        long end =System.currentTimeMillis();
        long duration = end - start;
        creativeClick.setDuration(duration+"");
        prizeService.writeCreativeClicklLog(creativeClick);

        return "redirect:"+click_url;

    }

}
