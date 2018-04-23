package com.zc.controller.base;

import com.zc.util.TokenUtil;
import org.solar.bean.JsonResult;
import org.solar.bean.Page;
import org.solar.bean.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.coder.Md5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zc.entity.MediaOwnerInfo;
import com.zc.service.MediaOwnerInfoService;
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
@RequestMapping("mediaOwnerInfo")
public class MediaOwnerInfoCrudController extends BaseController {
    Logger logger=LoggerFactory.getLogger(MediaOwnerInfoCrudController.class);
    @Resource
    private MediaOwnerInfoService mediaOwnerInfoService;
    @RequestMapping("/select")
    @ResponseBody
    public  JsonResult select(HttpServletRequest req,@RequestParam Map requestMap,
                            @RequestParam(value = "idList[]", required = false) List idList) {
        requestMap.put("idList", idList);
        String id=(String)requestMap.get("id");
        String pageNum=(String)requestMap.get("pageNum");
         //按条件查询List
        if (StringUtil.isEmpty(id)&&StringUtil.isEmpty(pageNum)){
           return JsonResult.success(mediaOwnerInfoService.selectByWhere(requestMap));
        }
        //按id查询
        if (StringUtil.isNotEmpty(id)){
            MediaOwnerInfo mediaOwnerInfo=mediaOwnerInfoService.getById(id);
            return JsonResult.success(mediaOwnerInfo);
        }
        //按分页查询
        Pageable pageable=Pageable.getPageable(requestMap);
        Page page=mediaOwnerInfoService.selectPage(pageable);
//            List<MediaOwnerInfo> mediaOwnerInfoList=page.getList();
//            List childIdList=BeanUtil.getProperties(mediaOwnerInfoList,"childId");
//            List childList=mediaOwnerInfoService.selectByWhere("idList",childIdList);
//            List<Map> resultList=BeanUtil.leftJoin(mediaOwnerInfoList,childList,"childId","id");
//            page.setList(resultList);
        return JsonResult.success(page);

    }
    @RequestMapping("/delete")
    @ResponseBody
    public  JsonResult delete(String id) {
        int row=mediaOwnerInfoService.delete(id);
        return JsonResult.success(row);
    }

    @RequestMapping("/selectMediaOwnerInfo")
    @ResponseBody
    public  JsonResult selectAdvertiserInfo(HttpServletRequest req) {
        String medId= TokenUtil.getUid(req);
        //按id查询
        if (StringUtil.isNotEmpty(medId)){
            MediaOwnerInfo advertiserInfo=mediaOwnerInfoService.getById(medId);
            return JsonResult.success(advertiserInfo);
        }
        return null;

    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public  JsonResult saveOrUpdate(@RequestBody Map requestMap) {
        MediaOwnerInfo bean= JsonUtil.toJavaObject(requestMap,MediaOwnerInfo.class);
        //如果id不为空则更新
        Date nowTime=new Date();
        if (StringUtil.isNotEmpty(bean.getId())){
            bean.setUpdateTime(new Date());
            int row=mediaOwnerInfoService.updateByPrimaryKey(bean);
            return JsonResult.success(row);
        }
        //md5密码
        bean.setPassword(Md5Util.getMd5Hex(bean.getPassword()));
        bean.setCreateTime(new Date());
        int row=mediaOwnerInfoService.save(bean);
        return JsonResult.success(row);
    }


}
