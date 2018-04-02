package com.zc.controller.base;

import com.alibaba.fastjson.JSONArray;
import com.zc.entity.AdvertiserInfo;
import com.zc.service.AdvertiserInfoService;
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
import com.zc.entity.AdvertiserAccountVirtualLog;
import com.zc.service.AdvertiserAccountVirtualLogService;
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
@RequestMapping("advertiserAccountVirtualLog")
public class AdvertiserAccountVirtualLogCrudController extends BaseController {
    Logger logger=LoggerFactory.getLogger(AdvertiserAccountVirtualLogCrudController.class);
    @Resource
    private AdvertiserAccountVirtualLogService advertiserAccountVirtualLogService;
    @Autowired
    private AdvertiserInfoService advertiserInfoService;
    @RequestMapping("/select")
    @ResponseBody
    public  JsonResult select(HttpServletRequest req,@RequestParam Map requestMap,
                            @RequestParam(value = "idList[]", required = false) List idList) {
        String advertiserId= TokenUtil.getUid(req);
        requestMap.put("idList", idList);
        if (!"D9B3DCECFC000000D00000000016E000".equals(advertiserId)){                requestMap.put("advertiserId", advertiserId);            }
        String id=(String)requestMap.get("id");
        String pageNum=(String)requestMap.get("pageNum");
         //按条件查询List
        if (StringUtil.isEmpty(id)&&StringUtil.isEmpty(pageNum)){
           return JsonResult.success(advertiserAccountVirtualLogService.selectByWhere(requestMap));
        }
        //按id查询
        if (StringUtil.isNotEmpty(id)){
            AdvertiserAccountVirtualLog advertiserAccountVirtualLog=advertiserAccountVirtualLogService.getById(id);
            return JsonResult.success(advertiserAccountVirtualLog);
        }
        //按分页查询
        Pageable pageable=Pageable.getPageable(requestMap);
        Page page=advertiserAccountVirtualLogService.selectPage(pageable);
//            List<AdvertiserAccountVirtualLog> advertiserAccountVirtualLogList=page.getList();
//            List childIdList=BeanUtil.getProperties(advertiserAccountVirtualLogList,"childId");
//            List childList=advertiserAccountVirtualLogService.selectByWhere("idList",childIdList);
//            List<Map> resultList=BeanUtil.leftJoin(advertiserAccountVirtualLogList,childList,"childId","id");
//            page.setList(resultList);
        return JsonResult.success(page);

    }

    @RequestMapping("/selectAdvertiser")
    @ResponseBody
    public  JsonResult selectAdvertiser(HttpServletRequest req) {
        String advertiserId= TokenUtil.getUid(req);
        System.out.println("/advertiserInfo/selectAdvertiserInfo:"+advertiserId);
        //按id查询
        JSONArray jsonArray = new JSONArray();
        if (StringUtil.isNotEmpty(advertiserId)){
            List <AdvertiserInfo> advertiserInfoList=advertiserInfoService.selectByWhere();
            //List<String> advertiserNameList = new ArrayList<String>();

            for (AdvertiserInfo advertiserInfo:advertiserInfoList){
                //advertiserNameList.add("{label:'"+advertiserInfo.getLoginName()+"',"+"value:'"+advertiserInfo.getId()+"'}");
                Map maps= new HashMap();
                maps.put("label",advertiserInfo.getLoginName());
                maps.put("value",advertiserInfo.getId());
                // maps.put("disabled","");
                jsonArray.add(maps);
            }
            //Map map = new HashMap();
            //map.put("campaignNameList",jsonArray);
            return JsonResult.success(jsonArray);
        }
        return null;

    }

    @RequestMapping("/delete")
    @ResponseBody
    public  JsonResult delete(String id) {
        int row=advertiserAccountVirtualLogService.delete(id);
        return JsonResult.success(row);
    }
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public  JsonResult saveOrUpdate(@RequestBody Map requestMap) {
        AdvertiserAccountVirtualLog bean= JsonUtil.toJavaObject(requestMap,AdvertiserAccountVirtualLog.class);
       //如果id不为空则更新
        Date nowTime=new Date();
        if (StringUtil.isNotEmpty(bean.getId())){
            int row=advertiserAccountVirtualLogService.updateByPrimaryKey(bean);
            return JsonResult.success(row);
        }
        int row=advertiserAccountVirtualLogService.save(bean);
        return JsonResult.success(row);
    }


}
