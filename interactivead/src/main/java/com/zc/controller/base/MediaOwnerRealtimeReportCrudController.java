package com.zc.controller.base;

import com.zc.controller.base.BaseController;
import com.zc.entity.MediaOwnerRealtimeReport;
import com.zc.service.MediaOwnerRealtimeReportService;
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
 * @date 2018年04月08日 17:40
 */
@Controller
@RequestMapping("mediaOwnerRealtimeReport")
public class MediaOwnerRealtimeReportCrudController extends BaseController {
    Logger logger=LoggerFactory.getLogger(MediaOwnerRealtimeReportCrudController.class);
    @Resource
    private MediaOwnerRealtimeReportService mediaOwnerRealtimeReportService;
    @RequestMapping("/select")
    @ResponseBody
    public  JsonResult select(HttpServletRequest req,@RequestParam Map requestMap,
                            @RequestParam(value = "idList[]", required = false) List idList) {
        requestMap.put("idList", idList);
        String id=(String)requestMap.get("id");
        String pageNum=(String)requestMap.get("pageNum");
         //按条件查询List
        if (StringUtil.isEmpty(id)&&StringUtil.isEmpty(pageNum)){
           return JsonResult.success(mediaOwnerRealtimeReportService.selectByWhere(requestMap));
        }
        //按id查询
        if (StringUtil.isNotEmpty(id)){
            MediaOwnerRealtimeReport mediaOwnerRealtimeReport=mediaOwnerRealtimeReportService.getById(id);
            return JsonResult.success(mediaOwnerRealtimeReport);
        }
        //按分页查询
        Pageable pageable=Pageable.getPageable(requestMap);
        Page page=mediaOwnerRealtimeReportService.selectPage(pageable);
//            List<MediaOwnerRealtimeReport> mediaOwnerRealtimeReportList=page.getList();
//            List childIdList=BeanUtil.getProperties(mediaOwnerRealtimeReportList,"childId");
//            List childList=mediaOwnerRealtimeReportService.selectByWhere("idList",childIdList);
//            List<Map> resultList=BeanUtil.leftJoin(mediaOwnerRealtimeReportList,childList,"childId","id");
//            page.setList(resultList);
        return JsonResult.success(page);

    }
    @RequestMapping("/delete")
    @ResponseBody
    public  JsonResult delete(String id) {
        int row=mediaOwnerRealtimeReportService.delete(id);
        return JsonResult.success(row);
    }
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public  JsonResult saveOrUpdate(@RequestBody Map requestMap) {
        MediaOwnerRealtimeReport bean= JsonUtil.toJavaObject(requestMap,MediaOwnerRealtimeReport.class);
       //如果id不为空则更新
        Date nowTime=new Date();
        if (StringUtil.isNotEmpty(bean.getId())){
            int row=mediaOwnerRealtimeReportService.updateByPrimaryKey(bean);
            return JsonResult.success(row);
        }
        int row=mediaOwnerRealtimeReportService.save(bean);
        return JsonResult.success(row);
    }


}
