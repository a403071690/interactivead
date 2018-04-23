package com.zc.md.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.regexp.internal.RE;
import com.zc.entity.*;
import com.zc.md.entity.CreativeClick;
import com.zc.md.entity.CreativeImp;
import com.zc.md.entity.Prize;
import com.zc.md.entity.Template;
import com.zc.md.service.PrizeService;
import com.zc.md.service.impl.PrizeServiceImpl;


import com.zc.service.AdvertiserCreativeService;
import com.zc.service.UserPrizeLogService;
import com.zc.util.*;
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
import org.springframework.web.bind.annotation.RequestMethod;
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
    static String yuming =  Config.get("yuming");
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
                              @RequestParam(value = "fid", required = false) String fid,
                              @RequestParam(value = "mid", required = false,defaultValue = "0") String mid,
                              @RequestParam(value = "cid", required = false,defaultValue = "0") String cid){

        log.info("cid:"+cid+"-mid:"+mid+"-tid:"+tid+"-fid:"+fid);
        if (StringUtil.isEmpty(fid)){
            return  JsonResult.error("fid不能为空");
        }
        long start = System.currentTimeMillis();
        log.info("tid:"+tid+"fid:"+fid);
        CookieUtil.addCookie(response,"fid",fid,60*60*24*7);//保存7天

        log.info("redis取出所有活动");
        Jedis cpjedis = RedisPool.getJedis();
        List<AdvertiserCampaign> cplist = JSONObject.parseArray(cpjedis.hvals("cp").toString(),AdvertiserCampaign.class);
        AdvertiserCampaign advertiserCampaignBest = prizeService.getBestCampaign(cplist,fid);
        AdvertiserCreative advertiserCreativeBest = null;
        String userPrizeLogId = IDGenerater.getNextId();//中奖日志表id
        UserPrizeLog userPrizeLog = new UserPrizeLog();
        if (advertiserCampaignBest!=null){
           // cpjedis.hincrBy("CampaignCount#"+advertiserCampaignBest.getId()+"#"+fid,"getCampaignCount",1);
           // cpjedis.pexpire("CampaignCount#"+advertiserCampaignBest.getId()+"#"+fid,90000);//设置key有效期为一分钟
            cpjedis.hincrBy("CampaignCount#"+fid,advertiserCampaignBest.getId(),1);
            cpjedis.pexpire("CampaignCount#"+fid,120000);//设置key有效期为一分钟
           // cpjedis.hincrBy("CampaignCount#"+advertiserCampaignBest.getId()+"#"+fid,"getCampaignCount",1);
           // cpjedis.pexpire("CampaignCount#"+advertiserCampaignBest.getId()+"#"+fid,60000);//设置key有效期为一分钟

            log.info("redis取出该活动下的创意:"+advertiserCampaignBest.getCampaignName());
            List<AdvertiserCreative> advertiserCreativelist = JSONObject.parseArray(cpjedis.hvals("cp#"+advertiserCampaignBest.getId()).toString(),AdvertiserCreative.class);
            advertiserCreativeBest = prizeService.getBestCreatve(advertiserCreativelist);
            RedisPool.returnResource(cpjedis);

            //写入中奖信息
            if (advertiserCreativeBest!=null) {
                log.info("最佳创意："+advertiserCreativeBest.getCreativeName());
                userPrizeLog.setChannelId(cid);//渠道id
                userPrizeLog.setCreateTime(DateUtil.format(new Date()));
                userPrizeLog.setCreativeId(advertiserCreativeBest.getId());
                long end2 = System.currentTimeMillis();
                userPrizeLog.setDuration((end2-start) + "");//
                userPrizeLog.setId(userPrizeLogId);
                userPrizeLog.setMediaOwnerId(mid);//meitiid
                userPrizeLog.setPrice(advertiserCampaignBest.getBidPrice() + "");
                userPrizeLog.setPrizeId("");//目前没有奖品表
                userPrizeLog.setTemplateId(tid);//模板
                userPrizeLog.setType("1");
                userPrizeLog.setUserCookieId( CookieUtil.getUid(req, "cookieid"));//cookieid
                userPrizeLog.setUserFingerId(fid);
                userPrizeLog.setUserOpenId("");
                userPrizeLog.setAdvertiserId(advertiserCampaignBest.getAdvertiserId());
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
            creativeImp.setUser_cookie_id(CookieUtil.getUid(req, "cookieid"));
            creativeImp.setChannel_id(cid);
            creativeImp.setMedia_owner_id(mid);
            if ("0".equals(mid)){
                if (StringUtil.isEmpty(CookieUtil.getUid(req, "mid"))){
                    creativeImp.setMedia_owner_id("0");
                }else {
                    creativeImp.setMedia_owner_id(CookieUtil.getUid(req,"mid"));
                }
            }
            if ("0".equals(cid)){
                if (StringUtil.isEmpty(CookieUtil.getUid(req, "cid"))){
                    creativeImp.setChannel_id("0");
                }else {
                    creativeImp.setChannel_id(CookieUtil.getUid(req, "cid"));
                }
            }

            /*if (StringUtil.isEmpty(CookieUtil.getUid(req, "mid"))){
                creativeImp.setMedia_owner_id("");
            }else {
                creativeImp.setMedia_owner_id(CookieUtil.getUid(req,"mid"));
            }

            if (StringUtil.isEmpty(CookieUtil.getUid(req, "cid"))){
                creativeImp.setChannel_id("");
            }*/
            if (StringUtil.isEmpty(CookieUtil.getUid(req, "cookieid"))){
                creativeImp.setUser_cookie_id("");
            }
            creativeImp.setCreate_time(DateUtil.format(new Date()));

            long end = System.currentTimeMillis();
            long duration = end - start;
            creativeImp.setDuration(duration + "");
            prizeService.writeCreativeImplLog(creativeImp);
            //将创意信息写入token响应给用户
            Map<String, String> map = new HashMap<String, String>();
            map.put("advertiserCreativeid", advertiserCreativeBest.getId());
            map.put("userPrizeLogId",userPrizeLogId);
            map.put("advertiser_id", advertiserCampaignBest.getAdvertiserId());
            map.put("fid", fid);
            map.put("tid",tid);
            map.put("mid",mid);
            map.put("cid",cid);
            String token = aesCoder.AESEncode(JsonUtil.toJSONString(map));
            advertiserCreativeBest.setCtTargetUrl(click_url_prefix + token);
            advertiserCreativeBest.setCtImgUrl(yuming+advertiserCreativeBest.getCtImgUrl());
            log.info("取最优创意响应给用户:" + advertiserCreativeBest.getCreativeName());
            userPrizeLog.setClickUrl(click_url_prefix + token);
        }
        userPrizeLogService.save(userPrizeLog);
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
        Map mapl = new HashMap();
        mapl.put("userFingerId",fid);
        mapl.put("orderProperty","createTime");
        mapl.put("orderDirection","desc");
        List<UserPrizeLog> userPrizeLogList = userPrizeLogService.selectByWhere(mapl);
        log.info("奖品数目："+userPrizeLogList.size());
        //List<AdvertiserCreative> advertiserCreativeList = new ArrayList<AdvertiserCreative>();
        List<Prize> prizeList = new ArrayList<Prize>();
        if (userPrizeLogList.size()>0){
           for (UserPrizeLog u :userPrizeLogList){
               if (StringUtil.isNotEmpty(u.getCreativeId())){
                   AdvertiserCreative advertiserCreative =  advertiserCreativeService.getById(u.getCreativeId());
                   //advertiserCreativeList.add(advertiserCreative);
                   Prize prize = new Prize();
                   prize.setImg_url(yuming+advertiserCreative.getCtImgUrl());
                   prize.setTitle(advertiserCreative.getCtTitle());

                  /* //将创意信息写入token
                   Map<String,String> map = new HashMap<String,String>();
                   map.put("advertiserCreativeid",advertiserCreative.getId());
                   map.put("userPrizeLogId",u.getId());
                   map.put("advertiser_id",u.getAdvertiserId());
                   map.put("fid",fid);
                   String token = aesCoder.AESEncode(JsonUtil.toJSONString(map));
                   prize.setClk_url(click_url_prefix+token);//advertiserCreative.getCtTargetUrl());
                   prize.setTime(DateUtil.parse(u.getCreateTime()));*/
                   prize.setTime(DateUtil.parse(u.getCreateTime()));
                   prize.setClk_url(u.getClickUrl());
                   log.info("有效期为当前创建日期加3个月："+DateUtil.format(DateUtil.addDay(DateUtil.parse(u.getCreateTime()),90),"yyyy-MM-dd HH:mm:ss"));
                   prize.setEnd_time(DateUtil.parse(DateUtil.format(DateUtil.addDay(DateUtil.parse(u.getCreateTime()),90),"yyyy-MM-dd HH:mm:ss")));
                   prize.setState("0");
                   prizeList.add(prize);
               }
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
        Map<String, String> map = JsonUtil.parseObject(tokens);
        String advertiserCreativeid ="";
        String userPrizeLogId = "";
        String advertiser_id = "";
        String tid = "";
        String fid ="";
        String cid = "";
        String mid = "";

         try {
             advertiserCreativeid = map.get("advertiserCreativeid");
             userPrizeLogId = map.get("userPrizeLogId");
             advertiser_id = map.get("advertiser_id");
             tid = map.get("tid");
             fid = map.get("fid");
             cid = map.get("cid");
             mid = map.get("mid");
            log.info("fid:" + fid + "；tid:" + tid + "；advertiser_id:" + advertiser_id + "；advertiserCreativeid:" + advertiserCreativeid + "；userPrizeLogId：" + userPrizeLogId);
        }catch (Exception e){
            log.error(e.getMessage());
        }
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
            jedis.hset("click#"+advertiserCreative.getCampaignId()+"#"+fid,"1","1");//写入redis
            jedis.pexpire("click#"+advertiserCreative.getCampaignId()+"#"+fid,60000);//设置key有效期为一分钟
            creativeClick.setIsValid("1");
        }else {
            creativeClick.setIsValid("0");
        }
        RedisPool.returnResource(jedis);
        //点击写入log
        log.info("写入创意点击日志log");

        creativeClick.setAdvertiser_id(advertiser_id);
        creativeClick.setCampaign_id(advertiserCreative.getCampaignId());
        creativeClick.setChannel_id(CookieUtil.getUid(req,"cid"));
        creativeClick.setCreate_time(DateUtil.format(new Date()));
        creativeClick.setCreative_id(advertiserCreative.getId());
        creativeClick.setId(IDGenerater.getNextId());
        creativeClick.setPrice(bidprice+"");
        creativeClick.setTemplate_id(tid);
        log.info("click========tid:"+tid);
        creativeClick.setUser_cookie_id(CookieUtil.getUid(req,"cookieid"));
        creativeClick.setMedia_owner_id(mid);
        if (StringUtil.isEmpty(mid)) {
            if (StringUtil.isEmpty(CookieUtil.getUid(req, "mid"))) {
                creativeClick.setMedia_owner_id("0");
            } else {
                creativeClick.setMedia_owner_id(CookieUtil.getUid(req, "mid"));
            }
        }

        if (StringUtil.isEmpty(CookieUtil.getUid(req, "cookieid"))){
            creativeClick.setUser_cookie_id(CookieUtil.getUid(req, "cookieid"));
        }

        if (StringUtil.isEmpty(cid)) {
            if (StringUtil.isEmpty(CookieUtil.getUid(req, "cid"))) {
                creativeClick.setChannel_id(CookieUtil.getUid(req, "cid"));
            }else {
                creativeClick.setChannel_id("0");
            }
        }
        if (StringUtil.isEmpty(tid)) {
            if (StringUtil.isEmpty(creativeClick.getTemplate_id())) {
                creativeClick.setTemplate_id(CookieUtil.getUid(req, "tid"));
            }else {
                creativeClick.setTemplate_id("0");
            }
        }
        creativeClick.setUser_finger_id(fid);
        long end =System.currentTimeMillis();
        long duration = end - start;
        creativeClick.setDuration(duration+"");
        prizeService.writeCreativeClicklLog(creativeClick);
        return "redirect:"+click_url;
    }

    @RequestMapping(value = "changeGame")
    public String changeGame(HttpServletResponse  response,@RequestParam(value = "mid", required = false, defaultValue = "0") String mid, @RequestParam(value = "tid", required = false, defaultValue = "0") String tid,@RequestParam(value = "cid", required = false, defaultValue = "0") String cid){
        String changeUrl = "http://hd.adsmar.com/zhuanpan/";
        //获取参数
        System.out.println("md>mid:"+mid+",cid:"+cid);
        //定义302跳转地址
        String url302 ="";
        //记录模板曝光数据写入log
        long start = System.currentTimeMillis();
        Template template = new Template();
        Jedis jedis = RedisPool.getJedis();
        TemplateManage templateManage = new TemplateManage();

        //指定了模板
        try {
            //是否指定模板
            if ("0".equals(tid) || StringUtil.isEmpty(tid)){
                //未指定,随机选取模板
                List<TemplateManage> templateManageList = JSONObject.parseArray(jedis.hvals("template").toString(),TemplateManage.class);
                Iterator<TemplateManage> templateManageIterator = templateManageList.listIterator();
                while (templateManageIterator.hasNext()){
                    TemplateManage templateManage1 = templateManageIterator.next();
                    if (templateManage1.getState().equals("2")){
                        templateManageIterator.remove();
                        break;
                    }
                }
                int index = new Random().nextInt(templateManageList.size());
                templateManage = templateManageList.get(index);
                log.info("随机数："+index+"随机模板:"+templateManage.getId());
            }else {
                //指定一个模板，随机跳转时排除该模板
                List<TemplateManage> templateManageList = JSONObject.parseArray(jedis.hvals("template").toString(),TemplateManage.class);
                Iterator<TemplateManage> templateManageIterator = templateManageList.listIterator();
                while (templateManageIterator.hasNext()){
                    TemplateManage templateManage1 = templateManageIterator.next();
                    if (templateManage1.getId().equals(tid) ||  templateManage1.getState().equals("2")){
                        templateManageIterator.remove();
                        break;
                    }
                }
                int index = new Random().nextInt(templateManageList.size());
                templateManage = templateManageList.get(index);
                log.info("随机数："+index+";随机模板2:"+templateManage.getId());

            }
            }catch (Exception e){
                log.error(e.getMessage());
                //模板不存在等异常问题出现时候
                List<TemplateManage> templateManageList = JSONObject.parseArray(jedis.hvals("template").toString(),TemplateManage.class);
                int index = new Random().nextInt(templateManageList.size());
                templateManage = templateManageList.get(index);
                log.info("出错了，随机一个模板吧:"+templateManage.getId());
        }
        url302 = templateManage.getUrl()+"?mid="+mid+"&cid="+cid ;
        //存入cookie
        CookieUtil.addCookie(response,"mid",mid,60*60*24*7);//保存7天
        CookieUtil.addCookie(response,"cid",cid,60*60*24*7);//保存7天
        CookieUtil.addCookie(response,"tid",tid,60*60*24*7);//保存7天
        CookieUtil.addCookie(response,"cookieid",IDGenerater.getNextId(),60*60*24*30);//保存30天
        template.setCreate_time(DateUtil.format(new Date()));
        template.setChannel_id(cid);
        template.setId(IDGenerater.getNextId());
        template.setMedia_owner_id(mid);
        template.setTemplate_id(templateManage.getId());
        template.setUser_finger_id("");
        template.setCreate_time(DateUtil.format(new Date()));
        template.setCurrent_time_millis(System.currentTimeMillis());
        long end  = System.currentTimeMillis();
        long duration = end-start;
        template.setDuration(duration+"");
        Object json = JSONObject.toJSON(template);
        CustomLogUtil.putTemplateLog(json.toString());
        //302跳转
        return "redirect:"+url302;
    }



}
