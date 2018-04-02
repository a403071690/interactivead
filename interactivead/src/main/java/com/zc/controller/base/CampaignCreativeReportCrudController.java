package com.zc.controller.base;

import com.zc.util.TokenUtil;
import org.solar.bean.JsonResult;
import org.solar.bean.Page;
import org.solar.bean.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zc.entity.CampaignCreativeReport;
import com.zc.service.CampaignCreativeReportService;
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
@RequestMapping("campaignCreativeReport")
public class CampaignCreativeReportCrudController extends BaseController {
    Logger logger=LoggerFactory.getLogger(CampaignCreativeReportCrudController.class);
    @Resource
    private CampaignCreativeReportService campaignCreativeReportService;
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
           return JsonResult.success(campaignCreativeReportService.selectByWhere(requestMap));
        }
        //按id查询
        if (StringUtil.isNotEmpty(id)){
            CampaignCreativeReport campaignCreativeReport=campaignCreativeReportService.getById(id);
            return JsonResult.success(campaignCreativeReport);
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
    @RequestMapping("/delete")
    @ResponseBody
    public  JsonResult delete(String id) {
        int row=campaignCreativeReportService.delete(id);
        return JsonResult.success(row);
    }
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public  JsonResult saveOrUpdate(@RequestBody Map requestMap) {
        CampaignCreativeReport bean= JsonUtil.toJavaObject(requestMap,CampaignCreativeReport.class);
       //如果id不为空则更新
        Date nowTime=new Date();
        if (StringUtil.isNotEmpty(bean.getId())){
            int row=campaignCreativeReportService.updateByPrimaryKey(bean);
            return JsonResult.success(row);
        }
        int row=campaignCreativeReportService.save(bean);
        return JsonResult.success(row);
    }


}
