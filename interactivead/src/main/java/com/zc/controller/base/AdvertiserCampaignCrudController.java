package com.zc.controller.base;

import com.sun.xml.internal.ws.resources.HttpserverMessages;
import com.zc.util.Config;
import com.zc.util.TokenUtil;
import org.solar.bean.JsonResult;
import org.solar.bean.Page;
import org.solar.bean.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.coder.AESCoder;
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
    AESCoder aesCoder = new AESCoder(Config.aesPassword);
    @Resource
    private AdvertiserCampaignService advertiserCampaignService;
    @RequestMapping("/select")
    @ResponseBody
    public  JsonResult select(HttpServletRequest req,@RequestParam Map requestMap,
                            @RequestParam(value = "idList[]", required = false) List idList) {
        String advertiserId= TokenUtil.getUid(req);
        requestMap.put("idList", idList);
        requestMap.put("advertiserId", advertiserId);
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
            return JsonResult.success(row);
        }
        System.out.println("userid:"+advertiserId);
        bean.setAdvertiserId(advertiserId);
        //设置默认值
        bean.setState(2);
        int row=advertiserCampaignService.save(bean);
        return JsonResult.success(row);
    }



    @RequestMapping("/changeState")
    @ResponseBody
    public  JsonResult changeState(String id) {
        AdvertiserCampaign advertiserCampaign = advertiserCampaignService.getById(id);
        if (advertiserCampaign.getState()==1){
            advertiserCampaign.setState(2);
        }else {
            advertiserCampaign.setState(1);
        }
        int row=advertiserCampaignService.updateByPrimaryKey(advertiserCampaign);
        return JsonResult.success(row);
    }


}
