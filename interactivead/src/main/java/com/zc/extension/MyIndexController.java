package com.zc.extension;

import com.alibaba.fastjson.JSONObject;
import com.zc.entity.CampaignCreativeRealtimeReport;
import com.zc.entity.CampaignCreativeReport;
import com.zc.service.CampaignCreativeRealtimeReportService;
import com.zc.service.CampaignCreativeReportService;
import com.zc.util.RedisPool;
import com.zc.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.bean.JsonResult;
import org.solar.bean.Page;
import org.solar.bean.Pageable;
import org.solar.util.DateUtil;
import org.solar.util.JsonUtil;
import org.solar.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "myindex")
public class MyIndexController {
    private static final Logger logger = LoggerFactory.getLogger(MyIndexController.class);
    private static final Date initTime=new Date();


    @Resource
    private CampaignCreativeReportService campaignCreativeReportService;
    @Resource
    private CampaignCreativeRealtimeReportService campaignCreativeRealtimeReportService;
    @RequestMapping("/select")
    @ResponseBody
    public  JsonResult myindex(HttpServletRequest req, @RequestParam Map requestMap,
                              @RequestParam(value = "idList[]", required = false) List idList) {
        String advertiserId= TokenUtil.getUid(req);
        logger.info("myindex:"+advertiserId);
        requestMap.put("idList", idList);
        if (!"D9B3DCECFC000000D00000000016E000".equals(advertiserId)){                requestMap.put("advertiserId", advertiserId);            }
        String id=(String)requestMap.get("id");
        String pageNum=(String)requestMap.get("pageNum");

        //按id查询
        if (StringUtil.isNotEmpty(advertiserId)){
            List<Map> mapList = campaignCreativeRealtimeReportService.executeSql("SELECT sum(imp_count) as imp_count,sum(click_count) as click_count,sum(valid_click_count)as valid_click_count ,sum(pay_money) as pay_money  from campaign_creative_realtime_report WHERE  LEFT(report_time,10) = LEFT(NOW(),10) AND advertiser_id='"+advertiserId+"'");
            if (StringUtil.isNotEmpty(mapList) && mapList.size()>0 && StringUtil.isNotEmpty(mapList.get(0))){
                CampaignCreativeRealtimeReport bean= JsonUtil.toJavaObject( mapList.get(0),CampaignCreativeRealtimeReport.class);
                return JsonResult.success(bean);
            }else {
                CampaignCreativeRealtimeReport bean = new CampaignCreativeRealtimeReport();
                bean.setClickCount((long) 0);
                bean.setImpCount((long) 0);
                bean.setPayMoney((long) 0);
                bean.setValidClickCount((long) 0);
                bean.setId("asdasdasdasd");
                return JsonResult.success(bean);
            }
        }

        //按分页查询
        Pageable pageable=Pageable.getPageable(requestMap);
        Page page=campaignCreativeReportService.selectPage(pageable);
//            List<CampaignCreativeReport> campaignCreativeReportList=page.getList();
//            List childIdList=BeanUtil.getProperties(campaignCreativeReportList,"childId");
//            List childList=campaignCreativeReportService.selectByWhere("idList",childIdList);
//            List<Map> resultList=BeanUtil.leftJoin(campaignCreativeReportList,childList,"childId","id");
//            page.setList(resultList);
        return JsonResult.success(page);

    }

    @RequestMapping("/getchart")
    @ResponseBody
    public  JsonResult getchart(HttpServletRequest req, @RequestParam Map requestMap,
                                @RequestParam(value = "day", required = false)String day) {
        String advertiserId= TokenUtil.getUid(req);
        logger.info("myindex:"+advertiserId+";"+"查询最近天数："+day);
        if (!"D9B3DCECFC000000D00000000016E000".equals(advertiserId)){                requestMap.put("advertiserId", advertiserId);            }
        String id=(String)requestMap.get("id");
        String pageNum=(String)requestMap.get("pageNum");


        //按id查询
        if (StringUtil.isNotEmpty(advertiserId)){
           List<CampaignCreativeReport> campaignCreativeReportList= campaignCreativeReportService.selectByWhere("advertiserId",advertiserId);


          /*  List<Map> mapList = campaignCreativeRealtimeReportService.executeSql("SELECT sum(imp_count) as imp_count,sum(click_count) as click_count,sum(valid_click_count)as valid_click_count ,sum(pay_money) as pay_money  from campaign_creative_realtime_report WHERE advertiser_id='"+advertiserId+"'");
            if (StringUtil.isNotEmpty(mapList) && mapList.size()>0 && StringUtil.isNotEmpty(mapList.get(0))){
                CampaignCreativeRealtimeReport bean= JsonUtil.toJavaObject( mapList.get(0),CampaignCreativeRealtimeReport.class);
                return JsonResult.success(bean);
            }else {
                CampaignCreativeRealtimeReport bean = new CampaignCreativeRealtimeReport();
                bean.setClickCount((long) 0);
                bean.setImpCount((long) 0);
                bean.setPayMoney((long) 0);
                bean.setValidClickCount((long) 0);
                bean.setId("asdasdasdasd");*/
                return JsonResult.success(campaignCreativeReportList);
            }

        //按分页查询
        Pageable pageable=Pageable.getPageable(requestMap);
        Page page=campaignCreativeReportService.selectPage(pageable);
//            List<CampaignCreativeReport> campaignCreativeReportList=page.getList();
//            List childIdList=BeanUtil.getProperties(campaignCreativeReportList,"childId");
//            List childList=campaignCreativeReportService.selectByWhere("idList",childIdList);
//            List<Map> resultList=BeanUtil.leftJoin(campaignCreativeReportList,childList,"childId","id");
//            page.setList(resultList);
        return JsonResult.success(page);

    }

    @RequestMapping("/getAccount")
    @ResponseBody
    public JsonResult getAccount(HttpServletRequest req,HttpServletResponse rs, @RequestParam Map requestMap){
        //从redis查询数据
        String advertiserId= TokenUtil.getUid(req);
        logger.info("myindex-advertiserId:"+advertiserId);
        if (!"D9B3DCECFC000000D00000000016E000".equals(advertiserId)){
            requestMap.put("advertiserId", advertiserId);
        }
        if(StringUtil.isEmpty(advertiserId)){
            return JsonResult.error("未登录");
        }
        String id=(String)requestMap.get("id");
        Jedis jedis = RedisPool.getJedis();
        String all_price = jedis.hget("adv_rech#"+advertiserId,"all_price");
        logger.info("充值金额:"+all_price);
        List<Integer> conlist = JSONObject.parseArray(jedis.hvals("adv_cons#"+advertiserId).toString(),Integer.class);
        RedisPool.returnResource(jedis);
        long consum = conlist.stream().reduce(0, Integer::sum);
        logger.info("计算消费总额："+consum);

        long balance = Long.parseLong( all_price) - consum;
        Map map = new HashMap();
        map.put("all_account", Long.parseLong( all_price));
        map.put("consum",consum);
        map.put("balance",balance);
        return JsonResult.success(map);
    }



}
