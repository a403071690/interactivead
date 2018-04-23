package com.zc.controller.base;

import com.zc.controller.base.BaseController;
import com.zc.entity.SignTicket;
import com.zc.service.SignTicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.bean.JsonResult;
import org.solar.bean.Page;
import org.solar.bean.Pageable;
import org.solar.util.JsonUtil;
import org.solar.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author solar
 * @date 2018年04月19日 16:43
 */
@Controller
@RequestMapping("signTicket")
public class SignTicketCrudController extends BaseController {
    Logger logger=LoggerFactory.getLogger(SignTicketCrudController.class);
    @Resource
    private SignTicketService signTicketService;
    @RequestMapping("/select")
    @ResponseBody
    public  JsonResult select(HttpServletRequest req,@RequestParam Map requestMap,
                            @RequestParam(value = "idList[]", required = false) List idList) {
        requestMap.put("idList", idList);
        String id=(String)requestMap.get("id");
        String pageNum=(String)requestMap.get("pageNum");
        logger.info("pageNum="+pageNum);
         //按条件查询List
        if (StringUtil.isEmpty(id)&&StringUtil.isEmpty(pageNum)){
           return JsonResult.success(signTicketService.selectByWhere(requestMap));
        }
        //按id查询
        if (StringUtil.isNotEmpty(id)){
            SignTicket signTicket=signTicketService.getById(id);
            return JsonResult.success(signTicket);
        }
        //按分页查询
        Pageable pageable=Pageable.getPageable(requestMap);
        Page page=signTicketService.selectPage(pageable);
//            List<SignTicket> signTicketList=page.getList();
//            List childIdList=BeanUtil.getProperties(signTicketList,"childId");
//            List childList=signTicketService.selectByWhere("idList",childIdList);
//            List<Map> resultList=BeanUtil.leftJoin(signTicketList,childList,"childId","id");
//            page.setList(resultList);
        return JsonResult.success(page);

    }
    @RequestMapping("/delete")
    @ResponseBody
    public  JsonResult delete(String id) {
        int row=signTicketService.delete(id);
        return JsonResult.success(row);
    }
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public  JsonResult saveOrUpdate(@RequestBody Map requestMap) {
        SignTicket bean= JsonUtil.toJavaObject(requestMap,SignTicket.class);
       //如果id不为空则更新
        Date nowTime=new Date();
        if (StringUtil.isNotEmpty(bean.getId())){
            int row=signTicketService.updateByPrimaryKey(bean);
            return JsonResult.success(row);
        }
        int row=signTicketService.save(bean);
        return JsonResult.success(row);
    }


}
