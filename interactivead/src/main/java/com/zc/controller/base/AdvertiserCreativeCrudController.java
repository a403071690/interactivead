package com.zc.controller.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zc.entity.AdvertiserCampaign;
import com.zc.md.service.SynchrodDateService;
import com.zc.service.AdvertiserCampaignService;
import com.zc.util.RedisPool;
import com.zc.util.TokenUtil;
import org.solar.bean.JsonResult;
import org.solar.bean.Page;
import org.solar.bean.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zc.entity.AdvertiserCreative;
import com.zc.service.AdvertiserCreativeService;
import javax.annotation.Resource;
import java.util.*;

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
@RequestMapping("advertiserCreative")
public class AdvertiserCreativeCrudController extends BaseController {
    Logger logger=LoggerFactory.getLogger(AdvertiserCreativeCrudController.class);

    @Autowired
    private SynchrodDateService synchrodDateService;
    @Resource
    private AdvertiserCreativeService advertiserCreativeService;
    @Autowired
    private AdvertiserCampaignService advertiserCampaignService;
    @RequestMapping("/select")
    @ResponseBody
    public  JsonResult select(HttpServletRequest req,@RequestParam Map requestMap,
                            @RequestParam(value = "idList[]", required = false) List idList) {
        String advertiserId= TokenUtil.getUid(req);
        requestMap.put("idList", idList);

        String id=(String)requestMap.get("id");
        String pageNum=(String)requestMap.get("pageNum");
        if (!"D9B3DCECFC000000D00000000016E000".equals(advertiserId)){
            requestMap.put("advertiserId", advertiserId);
        }

        //按条件查询List
        if (StringUtil.isEmpty(id)&&StringUtil.isEmpty(pageNum)){
           return JsonResult.success(advertiserCreativeService.selectByWhere(requestMap));
        }
        //按id查询
        if (StringUtil.isNotEmpty(id)){
            AdvertiserCreative advertiserCreative=advertiserCreativeService.getById(id);
            return JsonResult.success(advertiserCreative);
        }

        //先查询该广告主下的所有活动
        List<AdvertiserCampaign> advertiserCampaignList = null;
        if (!"D9B3DCECFC000000D00000000016E000".equals(advertiserId)){
           advertiserCampaignList = advertiserCampaignService.selectByWhere("advertiserId",advertiserId);
        }else {
            advertiserCampaignList = advertiserCampaignService.selectByWhere();
        }

        //将所有活动id方到map
        List campaignIdList = new ArrayList();
        if(advertiserCampaignList.size()>0){
            for (AdvertiserCampaign advertiserCampaign :advertiserCampaignList){
                campaignIdList.add(advertiserCampaign.getId());
            }
        }
        requestMap.put("campaignIdList",campaignIdList);

        //按分页查询
        Pageable pageable=Pageable.getPageable(requestMap);
        Page page=advertiserCreativeService.selectPage(pageable);
//            List<AdvertiserCreative> advertiserCreativeList=page.getList();
//            List childIdList=BeanUtil.getProperties(advertiserCreativeList,"childId");
//            List childList=advertiserCreativeService.selectByWhere("idList",childIdList);
//            List<Map> resultList=BeanUtil.leftJoin(advertiserCreativeList,childList,"childId","id");
//            page.setList(resultList);
        return JsonResult.success(page);

    }

    @RequestMapping("/selectCampaign")
    @ResponseBody
    public  JsonResult selectCampaign(HttpServletRequest req,@RequestParam Map requestMap,
                              @RequestParam(value = "idList[]", required = false) List idList) {
        String advertiserId= TokenUtil.getUid(req);
        logger.info("advertiserId----:"+advertiserId);
        requestMap.put("idList", idList);

        String id=(String)requestMap.get("id");
        String pageNum=(String)requestMap.get("pageNum");

        JSONArray jsonArray = new JSONArray();
        //按条件查询List
        if (!StringUtil.isEmpty(advertiserId)){
            List<AdvertiserCampaign> advertiserCampaignList = null;
            if (!"D9B3DCECFC000000D00000000016E000".equals(advertiserId)){
                advertiserCampaignList =  advertiserCampaignService.selectByWhere("advertiserId",advertiserId);
            }else {
                advertiserCampaignList =  advertiserCampaignService.selectByWhere();
            }

            List<String> campaignNameList = new ArrayList<String>();

            for (AdvertiserCampaign advertiserCampaign:advertiserCampaignList){
                campaignNameList.add("{label:'"+advertiserCampaign.getCampaignName()+"',"+"value:'"+advertiserCampaign.getId()+"'}");
                Map maps= new HashMap();
                maps.put("label",advertiserCampaign.getCampaignName());
                maps.put("value",advertiserCampaign.getId());

                // maps.put("disabled","");
                jsonArray.add(maps);
            }
            Map map = new HashMap();

            map.put("campaignNameList",jsonArray);

            return JsonResult.success(jsonArray);
        }

        //按分页查询
        Pageable pageable=Pageable.getPageable(requestMap);
        Page page=advertiserCreativeService.selectPage(pageable);

        return JsonResult.success(page);

    }



    @RequestMapping("/delete")
    @ResponseBody
    public  JsonResult delete(String id) {
        int row=advertiserCreativeService.delete(id);
        return JsonResult.success(row);
    }
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public  JsonResult saveOrUpdate(@RequestBody Map requestMap) {
        AdvertiserCreative bean= JsonUtil.toJavaObject(requestMap,AdvertiserCreative.class);
       //如果id不为空则更新
        Date nowTime=new Date();
        if (StringUtil.isNotEmpty(bean.getId())){
            int row=advertiserCreativeService.updateByPrimaryKey(bean);
            synchrodDateService.synchrodDateToRedis();
            return JsonResult.success(row);
        }
        bean.setState(1);
        bean.setCheckState(1);
        int row=advertiserCreativeService.save(bean);
        synchrodDateService.synchrodDateToRedis();
        return JsonResult.success(row);
    }

    @RequestMapping("/changeState")
    @ResponseBody
    public  JsonResult changeState(String id) {
        AdvertiserCreative advertiserCreative = advertiserCreativeService.getById(id);
        //查询当前活动的状态
        AdvertiserCampaign advertiserCampaign = advertiserCampaignService.getById(advertiserCreative.getCampaignId());

        if (advertiserCampaign.getState()==2){
            return JsonResult.error("所属活动未开启！");
        }
        else{
            Jedis jedis = RedisPool.getJedis();
            String all_price = jedis.hget("adv_rech#"+advertiserCampaign.getAdvertiserId(),"all_price");
            if ( advertiserCampaign.getBeginTime().getTime()> System.currentTimeMillis() || advertiserCampaign.getEndTime().getTime() < System.currentTimeMillis()){
                return JsonResult.error("所属活动不在投放周期内！");
            }else if( StringUtil.isEmpty(all_price)){
                return JsonResult.error("您当前余额不足，请联系我们为您充值！");
            }else if(advertiserCreative.getCheckState()==2){
                return JsonResult.error("创意未审核，请联系我们审核！");
            }
            else{
                List<Integer> conlist = JSONObject.parseArray(jedis.hvals("adv_cons#" + advertiserCampaign.getAdvertiserId()).toString(), Integer.class);
                long consum = conlist.stream().reduce(0, Integer::sum);
                if (consum >= Integer.parseInt(all_price)) {
                    return JsonResult.error("您当前余额不足，请联系我们为您充值！");
                } else  if (advertiserCampaign.getDayPrice() < consum) {
                    return JsonResult.error("所属活动当日预算已花完，请增加预算后重试！");
                }
            }
            RedisPool.returnResource(jedis);
        }
        if (advertiserCreative.getState()==1){
            advertiserCreative.setState(2);
        }else {
            advertiserCreative.setState(1);
        }
        int row=advertiserCreativeService.updateByPrimaryKey(advertiserCreative);
        synchrodDateService.synchrodDateToRedis();
        return JsonResult.success(row);
    }


}
