package com.zc.controller.base;

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
import com.zc.md.entity.Prize;
import com.zc.service.PrizeService;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.List;
import org.solar.util.JsonUtil;
import org.solar.util.StringUtil;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
 */
@Controller
@RequestMapping("prize")
public class PrizeCrudController extends BaseController {
    Logger logger=LoggerFactory.getLogger(PrizeCrudController.class);
    @Resource
    private PrizeService prizeService;
    @RequestMapping("/select")
    @ResponseBody
    public  JsonResult select(HttpServletRequest req,@RequestParam Map requestMap,
                            @RequestParam(value = "idList[]", required = false) List idList) {
        requestMap.put("idList", idList);
        String id=(String)requestMap.get("id");
        String pageNum=(String)requestMap.get("pageNum");
         //按条件查询List
        if (StringUtil.isEmpty(id)&&StringUtil.isEmpty(pageNum)){
           return JsonResult.success(prizeService.selectByWhere(requestMap));
        }

        //按id查询
        if (StringUtil.isNotEmpty(id)){
            Prize prize=prizeService.getById(id);
            return JsonResult.success(prize);
        }
        //按分页查询
        Pageable pageable=Pageable.getPageable(requestMap);
        Page page=prizeService.selectPage(pageable);
//            List<Prize> prizeList=page.getList();
//            List childIdList=BeanUtil.getProperties(prizeList,"childId");
//            List childList=prizeService.selectByWhere("idList",childIdList);
//            List<Map> resultList=BeanUtil.leftJoin(prizeList,childList,"childId","id");
//            page.setList(resultList);
        return JsonResult.success(page);

    }
    @RequestMapping("/delete")
    @ResponseBody
    public  JsonResult delete(String id) {
        int row=prizeService.delete(id);
        return JsonResult.success(row);
    }
    /*@RequestMapping("/saveOrUpdate")
    @ResponseBody
    public  JsonResult saveOrUpdate(@RequestBody Map requestMap) {
        Prize bean= JsonUtil.toJavaObject(requestMap,Prize.class);
       //如果id不为空则更新
        Date nowTime=new Date();
        if (StringUtil.isNotEmpty(bean.getId())){
            int row=prizeService.updateByPrimaryKey(bean);
            return JsonResult.success(row);
        }
        int row=prizeService.save(bean);
        return JsonResult.success(row);
    }*/


}
