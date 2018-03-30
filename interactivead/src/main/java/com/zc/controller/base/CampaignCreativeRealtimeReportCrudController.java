package com.zc.controller.base;

import com.zc.controller.base.BaseController;
import com.zc.entity.CampaignCreativeRealtimeReport;
import com.zc.service.CampaignCreativeRealtimeReportService;
import com.zc.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.bean.JsonResult;
import org.solar.bean.Page;
import org.solar.bean.Pageable;
import org.solar.util.JsonUtil;
import org.solar.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author solar
 * @date 2018年03月29日 20:17
 */
@Controller
@RequestMapping("campaignCreativeRealtimeReport")
public class CampaignCreativeRealtimeReportCrudController extends BaseController {
    Logger logger=LoggerFactory.getLogger(CampaignCreativeRealtimeReportCrudController.class);
    @Resource
    private CampaignCreativeRealtimeReportService campaignCreativeRealtimeReportService;
    @RequestMapping("/select")
    @ResponseBody
    public  JsonResult select(HttpServletRequest req,@RequestParam Map requestMap,
                            @RequestParam(value = "idList[]", required = false) List idList) {
        String advertiserId= TokenUtil.getUid(req);
        requestMap.put("idList", idList);
        requestMap.put("advertiserId", advertiserId);
        String id=(String)requestMap.get("id");
        String pazcum=(String)requestMap.get("pageNum");//pazucm
         //按条件查询List
        if (StringUtil.isEmpty(id)&&StringUtil.isEmpty(pazcum)){
           return JsonResult.success(campaignCreativeRealtimeReportService.selectByWhere(requestMap));
        }
        //按id查询
        if (StringUtil.isNotEmpty(id)){
            CampaignCreativeRealtimeReport campaignCreativeRealtimeReport=campaignCreativeRealtimeReportService.getById(id);
            return JsonResult.success(campaignCreativeRealtimeReport);
        }
        //按分页查询
        Pageable pageable=Pageable.getPageable(requestMap);
        Page page=campaignCreativeRealtimeReportService.selectPage(pageable);
//            List<CampaignCreativeRealtimeReport> campaignCreativeRealtimeReportList=page.getList();
//            List childIdList=BeanUtil.getProperties(campaignCreativeRealtimeReportList,"childId");
//            List childList=campaignCreativeRealtimeReportService.selectByWhere("idList",childIdList);
//            List<Map> resultList=BeanUtil.leftJoin(campaignCreativeRealtimeReportList,childList,"childId","id");
//            page.setList(resultList);
        return JsonResult.success(page);

    }
    @RequestMapping("/delete")
    @ResponseBody
    public  JsonResult delete(String id) {
        int row=campaignCreativeRealtimeReportService.delete(id);
        return JsonResult.success(row);
    }
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public  JsonResult saveOrUpdate(@RequestBody Map requestMap) {
        CampaignCreativeRealtimeReport bean= JsonUtil.toJavaObject(requestMap,CampaignCreativeRealtimeReport.class);
       //如果id不为空则更新
        Date nowTime=new Date();
        if (StringUtil.isNotEmpty(bean.getId())){
            int row=campaignCreativeRealtimeReportService.updateByPrimaryKey(bean);
            return JsonResult.success(row);
        }
        int row=campaignCreativeRealtimeReportService.save(bean);
        return JsonResult.success(row);
    }


}
