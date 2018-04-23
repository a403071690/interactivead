package com.zc.controller.base;


import com.alibaba.fastjson.JSONObject;
import com.zc.md.service.SynchrodDateService;
import com.zc.util.Config;
import com.zc.util.RedisPool;
import com.zc.util.TokenUtil;
import org.solar.bean.JsonResult;
import org.solar.bean.Page;
import org.solar.bean.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.coder.AESCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zc.entity.AdvertiserCampaign;
import com.zc.service.AdvertiserCampaignService;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import org.solar.util.JsonUtil;
import org.solar.util.BeanUtil;
import org.solar.util.StringUtil;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
 */
@Controller
@RequestMapping("advertiserCampaign")
public class AdvertiserCampaignCrudController extends BaseController {
    Logger logger=LoggerFactory.getLogger(AdvertiserCampaignCrudController.class);
    @Autowired
    private SynchrodDateService synchrodDateService;
    AESCoder aesCoder = new AESCoder(Config.aesPassword);
    @Resource
    private AdvertiserCampaignService advertiserCampaignService;
    @RequestMapping("/select")
    @ResponseBody
    public  JsonResult select(HttpServletRequest req,@RequestParam Map requestMap,
                            @RequestParam(value = "idList[]", required = false) List idList) {
        try {
            String advertiserId= TokenUtil.getUid(req);
            if (!"D9B3DCECFC000000D00000000016E000".equals(advertiserId)){
                requestMap.put("advertiserId", advertiserId);
            }

        }catch (Exception e){
            logger.debug("没有登录");
        }
        requestMap.put("idList", idList);
        String id=(String)requestMap.get("id");
        String pageNum=(String)requestMap.get("pageNum");
         //按条件查询List
        if (StringUtil.isEmpty(id)&&StringUtil.isEmpty(pageNum)){
           return JsonResult.success(advertiserCampaignService.selectByWhere(requestMap));
        }
        //按id查询
        if (StringUtil.isNotEmpty(id)){
            AdvertiserCampaign advertiserCampaign=advertiserCampaignService.getById(id);
            return JsonResult.success(advertiserCampaign);
        }
        //按分页查询
        Pageable pageable=Pageable.getPageable(requestMap);
        Page page=advertiserCampaignService.selectPage(pageable);
//            List<AdvertiserCampaign> advertiserCampaignList=page.getList();
//            List childIdList=BeanUtil.getProperties(advertiserCampaignList,"childId");
//            List childList=advertiserCampaignService.selectByWhere("idList",childIdList);
//            List<Map> resultList=BeanUtil.leftJoin(advertiserCampaignList,childList,"childId","id");
//            page.setList(resultList);
        return JsonResult.success(page);

    }
    @RequestMapping("/delete")
    @ResponseBody
    public  JsonResult delete(String id) {
        int row=advertiserCampaignService.delete(id);
        return JsonResult.success(row);
    }
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public  JsonResult saveOrUpdate(HttpServletRequest request, @RequestBody Map requestMap) {
        String advertiserId= TokenUtil.getUid(request);
        AdvertiserCampaign bean= JsonUtil.toJavaObject(requestMap,AdvertiserCampaign.class);
       //如果id不为空则更新
        Date nowTime=new Date();
        if (StringUtil.isNotEmpty(bean.getId())){
            int row=advertiserCampaignService.updateByPrimaryKey(bean);
            synchrodDateService.synchrodDateToRedis();
            return JsonResult.success(row);
        }
        System.out.println("userid:"+advertiserId);
        bean.setAdvertiserId(advertiserId);
        //设置默认值
        bean.setState(2);
        int row=advertiserCampaignService.save(bean);
        synchrodDateService.synchrodDateToRedis();
        return JsonResult.success(row);
    }



    @RequestMapping("/changeState")
    @ResponseBody
    public  JsonResult changeState(String id) {
        AdvertiserCampaign advertiserCampaign = advertiserCampaignService.getById(id);
        Jedis jedis = RedisPool.getJedis();
        String all_price = jedis.hget("adv_rech#"+advertiserCampaign.getAdvertiserId(),"all_price");
        if ( advertiserCampaign.getBeginTime().getTime()> System.currentTimeMillis() || advertiserCampaign.getEndTime().getTime() < System.currentTimeMillis()){
            return JsonResult.error("本活动不在投放周期内！");
        }else if( StringUtil.isEmpty(all_price)){
            return JsonResult.error("您当前余额不足，请联系我们为您充值！");
        }else {
            List<Integer> conlist = JSONObject.parseArray(jedis.hvals("adv_cons#" + advertiserCampaign.getAdvertiserId()).toString(), Integer.class);
            long consum = conlist.stream().reduce(0, Integer::sum);
            if (consum >= Integer.parseInt(all_price)) {
                return JsonResult.error("您当前余额不足，请联系我们为您充值！");
            } else  if (advertiserCampaign.getDayPrice() < consum) {
                    return JsonResult.error("该活动当日预算已花完，请增加预算后重试！");
                }
        }
        RedisPool.returnResource(jedis);
        if (advertiserCampaign.getState()==1){
            advertiserCampaign.setState(2);
        }else {
            advertiserCampaign.setState(1);
        }
        int row=advertiserCampaignService.updateByPrimaryKey(advertiserCampaign);
        synchrodDateService.synchrodDateToRedis();
        return JsonResult.success(row);
    }


}
