package com.zc.controller.base;

import com.zc.controller.base.BaseController;
import com.zc.entity.SignBuyticketlist;
import com.zc.service.SignBuyticketlistService;
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
 * @date 2018年04月19日 16:43
 */
@Controller
@RequestMapping("signBuyticketlist")
public class SignBuyticketlistCrudController extends BaseController {
    Logger logger=LoggerFactory.getLogger(SignBuyticketlistCrudController.class);
    @Resource
    private SignBuyticketlistService signBuyticketlistService;
    @RequestMapping("/select")
    @ResponseBody
    public  JsonResult select(HttpServletRequest req,@RequestParam Map requestMap,
                            @RequestParam(value = "idList[]", required = false) List idList) {
        requestMap.put("idList", idList);
        String id=(String)requestMap.get("id");
        String pageNum=(String)requestMap.get("pageNum");
         //按条件查询List
        if (StringUtil.isEmpty(id)&&StringUtil.isEmpty(pageNum)){
           return JsonResult.success(signBuyticketlistService.selectByWhere(requestMap));
        }
        //按id查询
        if (StringUtil.isNotEmpty(id)){
            SignBuyticketlist signBuyticketlist=signBuyticketlistService.getById(id);
            return JsonResult.success(signBuyticketlist);
        }
        //按分页查询
        Pageable pageable=Pageable.getPageable(requestMap);
        Page page=signBuyticketlistService.selectPage(pageable);
//            List<SignBuyticketlist> signBuyticketlistList=page.getList();
//            List childIdList=BeanUtil.getProperties(signBuyticketlistList,"childId");
//            List childList=signBuyticketlistService.selectByWhere("idList",childIdList);
//            List<Map> resultList=BeanUtil.leftJoin(signBuyticketlistList,childList,"childId","id");
//            page.setList(resultList);
        return JsonResult.success(page);

    }
    @RequestMapping("/delete")
    @ResponseBody
    public  JsonResult delete(String id) {
        int row=signBuyticketlistService.delete(id);
        return JsonResult.success(row);
    }
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public  JsonResult saveOrUpdate(@RequestBody Map requestMap) {
        SignBuyticketlist bean= JsonUtil.toJavaObject(requestMap,SignBuyticketlist.class);
       //如果id不为空则更新
        Date nowTime=new Date();
        if (StringUtil.isNotEmpty(bean.getId())){
            int row=signBuyticketlistService.updateByPrimaryKey(bean);
            return JsonResult.success(row);
        }
        int row=signBuyticketlistService.save(bean);
        return JsonResult.success(row);
    }


}
