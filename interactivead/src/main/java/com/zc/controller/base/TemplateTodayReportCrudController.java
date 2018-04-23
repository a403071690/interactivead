package com.zc.controller.base;

import com.zc.entity.TemplateRealtimeReport;
import com.zc.entity.TemplateTodayReport;
import com.zc.service.TemplateTodayReportService;
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
import java.util.*;

/**
 * @Description:
 * @author solar
 * @date 2018年04月08日 17:40
 */
@Controller
@RequestMapping("templateTodayReport")
public class TemplateTodayReportCrudController extends BaseController {
    Logger logger=LoggerFactory.getLogger(TemplateTodayReportCrudController.class);
    @Resource
    private TemplateTodayReportService templateTodayReportService;
    @RequestMapping("/select")
    @ResponseBody
    public  JsonResult select(HttpServletRequest req,@RequestParam Map requestMap,
                            @RequestParam(value = "idList[]", required = false) List idList) {
        requestMap.put("idList", idList);
        String id=(String)requestMap.get("id");
        String pageNum=(String)requestMap.get("pageNum");
         //按条件查询List
        if (StringUtil.isEmpty(id)){
          List<Map> mapList =   templateTodayReportService.executeSql("SELECT  med.company,tm.`name` ,sum(pv_count) as template_pv\n" +
                    ",sum(ct_imp_count) as ct_imp_count ,sum(ct_click_count)as ct_click_count,sum(ct_valid_click_count)  as ct_valid_click_count , SUM(pay_money) as pay_money\n" +
                    "from template_realtime_report as trr ,template_manage as tm,media_owner_info med\n" +
                    "where left(hour_time,10)=LEFT(NOW(),10) \n" +
                    "and trr.template_id = tm.id and med.id = trr.media_owner_id\n" +
                    "group by trr.media_owner_id,template_id,tm.`name`");

            List<TemplateTodayReport> templateTodayReportList = new ArrayList<TemplateTodayReport>();
            for (int i=0;i<mapList.size();i++){
                TemplateTodayReport templateTodayReport= new TemplateTodayReport();
                templateTodayReport.setCompany(mapList.get(i).get("company").toString());
                templateTodayReport.setTemplateName(mapList.get(i).get("name").toString());
                templateTodayReport.setPvCount(Long.parseLong(mapList.get(i).get("template_pv").toString()));
                templateTodayReport.setCtImpCount(Long.parseLong(mapList.get(i).get("ct_imp_count").toString()));
                templateTodayReport.setCtClickCount(Long.parseLong(mapList.get(i).get("ct_click_count").toString()));
                templateTodayReport.setCtValidClickCount(Long.parseLong(mapList.get(i).get("ct_valid_click_count").toString()));
                templateTodayReport.setPayMoney(Long.parseLong(mapList.get(i).get("pay_money").toString()));
                templateTodayReportList.add(templateTodayReport);
            }
            Map map  = new HashMap();
            map.put("pageNumber",1);
            map.put("pageSize", 10);
            map.put("pageable", "" );
            map.put("totalPage", 3);
            map.put("totalRecord",25);
            map.put("list",templateTodayReportList);

           return JsonResult.success(map);
        }
        //按id查询
        if (StringUtil.isNotEmpty(id)){
            TemplateTodayReport templateTodayReport = templateTodayReportService.getById(id);
            return JsonResult.success(templateTodayReport);
        }
        //按分页查询
        Pageable pageable=Pageable.getPageable(requestMap);
        Page page=templateTodayReportService.selectPage(pageable);
//            List<TemplateRealtimeReport> templateRealtimeReportList=page.getList();
//            List childIdList=BeanUtil.getProperties(templateRealtimeReportList,"childId");
//            List childList=templateTodayReportService.selectByWhere("idList",childIdList);
//            List<Map> resultList=BeanUtil.leftJoin(templateRealtimeReportList,childList,"childId","id");
//            page.setList(resultList);
        return JsonResult.success(page);

    }
    @RequestMapping("/delete")
    @ResponseBody
    public  JsonResult delete(String id) {
        int row=templateTodayReportService.delete(id);
        return JsonResult.success(row);
    }
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public  JsonResult saveOrUpdate(@RequestBody Map requestMap) {
        TemplateTodayReport bean= JsonUtil.toJavaObject(requestMap,TemplateTodayReport.class);
       //如果id不为空则更新
        Date nowTime=new Date();
        if (StringUtil.isNotEmpty(bean.getId())){
            int row=templateTodayReportService.updateByPrimaryKey(bean);
            return JsonResult.success(row);
        }
        int row=templateTodayReportService.save(bean);
        return JsonResult.success(row);
    }


}
