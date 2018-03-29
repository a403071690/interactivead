package com.gen.controller.base;

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
import com.gen.entity.TemplateReport;
import com.gen.service.TemplateReportService;
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
 * @date 2018年03月29日 20:17
 */
@Controller
@RequestMapping("templateReport")
public class TemplateReportCrudController extends BaseController {
    Logger logger=LoggerFactory.getLogger(TemplateReportCrudController.class);
    @Resource
    private TemplateReportService templateReportService;
    @RequestMapping("/select")
    @ResponseBody
    public  JsonResult select(HttpServletRequest req,@RequestParam Map requestMap,
                            @RequestParam(value = "idList[]", required = false) List idList) {
        requestMap.put("idList", idList);
        String id=(String)requestMap.get("id");
        String pageNum=(String)requestMap.get("pageNum");
         //按条件查询List
        if (StringUtil.isEmpty(id)&&StringUtil.isEmpty(pageNum)){
           return JsonResult.success(templateReportService.selectByWhere(requestMap));
        }
        //按id查询
        if (StringUtil.isNotEmpty(id)){
            TemplateReport templateReport=templateReportService.getById(id);
            return JsonResult.success(templateReport);
        }
        //按分页查询
        Pageable pageable=Pageable.getPageable(requestMap);
        Page page=templateReportService.selectPage(pageable);
//            List<TemplateReport> templateReportList=page.getList();
//            List childIdList=BeanUtil.getProperties(templateReportList,"childId");
//            List childList=templateReportService.selectByWhere("idList",childIdList);
//            List<Map> resultList=BeanUtil.leftJoin(templateReportList,childList,"childId","id");
//            page.setList(resultList);
        return JsonResult.success(page);

    }
    @RequestMapping("/delete")
    @ResponseBody
    public  JsonResult delete(String id) {
        int row=templateReportService.delete(id);
        return JsonResult.success(row);
    }
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public  JsonResult saveOrUpdate(@RequestBody Map requestMap) {
        TemplateReport bean= JsonUtil.toJavaObject(requestMap,TemplateReport.class);
       //如果id不为空则更新
        Date nowTime=new Date();
        if (StringUtil.isNotEmpty(bean.getId())){
            int row=templateReportService.updateByPrimaryKey(bean);
            return JsonResult.success(row);
        }
        int row=templateReportService.save(bean);
        return JsonResult.success(row);
    }


}
