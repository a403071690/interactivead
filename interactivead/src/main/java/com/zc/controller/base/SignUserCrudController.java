package com.zc.controller.base;

import com.zc.controller.base.BaseController;
import com.zc.entity.SignUser;
import com.zc.service.SignUserService;
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
@RequestMapping("signUser")
public class SignUserCrudController extends BaseController {
    Logger logger=LoggerFactory.getLogger(SignUserCrudController.class);
    @Resource
    private SignUserService signUserService;
    @RequestMapping("/select")
    @ResponseBody
    public  JsonResult select(HttpServletRequest req,@RequestParam Map requestMap,
                            @RequestParam(value = "idList[]", required = false) List idList) {
        requestMap.put("idList", idList);
        String id=(String)requestMap.get("id");
        String pageNum=(String)requestMap.get("pageNum");
         //按条件查询List
        if (StringUtil.isEmpty(id)&&StringUtil.isEmpty(pageNum)){
           return JsonResult.success(signUserService.selectByWhere(requestMap));
        }
        //按id查询
        if (StringUtil.isNotEmpty(id)){
            SignUser signUser=signUserService.getById(id);
            return JsonResult.success(signUser);
        }
        //按分页查询
        Pageable pageable=Pageable.getPageable(requestMap);
        Page page=signUserService.selectPage(pageable);
//            List<SignUser> signUserList=page.getList();
//            List childIdList=BeanUtil.getProperties(signUserList,"childId");
//            List childList=signUserService.selectByWhere("idList",childIdList);
//            List<Map> resultList=BeanUtil.leftJoin(signUserList,childList,"childId","id");
//            page.setList(resultList);
        return JsonResult.success(page);

    }
    @RequestMapping("/delete")
    @ResponseBody
    public  JsonResult delete(String id) {
        int row=signUserService.delete(id);
        return JsonResult.success(row);
    }
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public  JsonResult saveOrUpdate(@RequestBody Map requestMap) {
        SignUser bean= JsonUtil.toJavaObject(requestMap,SignUser.class);
       //如果id不为空则更新
        Date nowTime=new Date();
        if (StringUtil.isNotEmpty(bean.getId())){
            int row=signUserService.updateByPrimaryKey(bean);
            return JsonResult.success(row);
        }
        int row=signUserService.save(bean);
        return JsonResult.success(row);
    }


}
