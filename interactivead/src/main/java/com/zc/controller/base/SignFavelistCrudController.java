package com.zc.controller.base;

import com.zc.controller.base.BaseController;
import com.zc.entity.SignFavelist;
import com.zc.service.SignFavelistService;
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
@RequestMapping("signFavelist")
public class SignFavelistCrudController extends BaseController {
    Logger logger=LoggerFactory.getLogger(SignFavelistCrudController.class);
    @Resource
    private SignFavelistService signFavelistService;
    @RequestMapping("/select")
    @ResponseBody
    public  JsonResult select(HttpServletRequest req,@RequestParam Map requestMap,
                            @RequestParam(value = "idList[]", required = false) List idList) {
        requestMap.put("idList", idList);
        String id=(String)requestMap.get("id");
        String pageNum=(String)requestMap.get("pageNum");
         //按条件查询List
        if (StringUtil.isEmpty(id)&&StringUtil.isEmpty(pageNum)){
           return JsonResult.success(signFavelistService.selectByWhere(requestMap));
        }
        //按id查询
        if (StringUtil.isNotEmpty(id)){
            SignFavelist signFavelist=signFavelistService.getById(id);
            return JsonResult.success(signFavelist);
        }
        //按分页查询
        Pageable pageable=Pageable.getPageable(requestMap);
        Page page=signFavelistService.selectPage(pageable);
//            List<SignFavelist> signFavelistList=page.getList();
//            List childIdList=BeanUtil.getProperties(signFavelistList,"childId");
//            List childList=signFavelistService.selectByWhere("idList",childIdList);
//            List<Map> resultList=BeanUtil.leftJoin(signFavelistList,childList,"childId","id");
//            page.setList(resultList);
        return JsonResult.success(page);

    }
    @RequestMapping("/delete")
    @ResponseBody
    public  JsonResult delete(String id) {
        int row=signFavelistService.delete(id);
        return JsonResult.success(row);
    }
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public  JsonResult saveOrUpdate(@RequestBody Map requestMap) {
        SignFavelist bean= JsonUtil.toJavaObject(requestMap,SignFavelist.class);
       //如果id不为空则更新
        Date nowTime=new Date();
        if (StringUtil.isNotEmpty(bean.getId())){
            int row=signFavelistService.updateByPrimaryKey(bean);
            return JsonResult.success(row);
        }
        int row=signFavelistService.save(bean);
        return JsonResult.success(row);
    }


}
