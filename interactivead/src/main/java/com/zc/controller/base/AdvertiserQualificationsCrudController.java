package com.zc.controller.base;

import com.zc.util.TokenUtil;
import org.solar.bean.JsonResult;
import org.solar.bean.Page;
import org.solar.bean.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zc.entity.AdvertiserQualifications;
import com.zc.service.AdvertiserQualificationsService;
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
@RequestMapping("advertiserQualifications")
public class AdvertiserQualificationsCrudController extends BaseController {
    Logger logger=LoggerFactory.getLogger(AdvertiserQualificationsCrudController.class);
    @Resource
    private AdvertiserQualificationsService advertiserQualificationsService;
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
           return JsonResult.success(advertiserQualificationsService.selectByWhere(requestMap));
        }
        //按id查询
        if (StringUtil.isNotEmpty(id)){
            AdvertiserQualifications advertiserQualifications=advertiserQualificationsService.getById(id);
            return JsonResult.success(advertiserQualifications);
        }
        //按分页查询
        Pageable pageable=Pageable.getPageable(requestMap);
        Page page=advertiserQualificationsService.selectPage(pageable);
//            List<AdvertiserQualifications> advertiserQualificationsList=page.getList();
//            List childIdList=BeanUtil.getProperties(advertiserQualificationsList,"childId");
//            List childList=advertiserQualificationsService.selectByWhere("idList",childIdList);
//            List<Map> resultList=BeanUtil.leftJoin(advertiserQualificationsList,childList,"childId","id");
//            page.setList(resultList);
        return JsonResult.success(page);

    }
    @RequestMapping("/delete")
    @ResponseBody
    public  JsonResult delete(String id) {
        int row=advertiserQualificationsService.delete(id);
        return JsonResult.success(row);
    }
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public  JsonResult saveOrUpdate(HttpServletRequest req,@RequestBody Map requestMap) {
        AdvertiserQualifications bean= JsonUtil.toJavaObject(requestMap,AdvertiserQualifications.class);
        String advertiserId= TokenUtil.getUid(req);
        bean.setAdvertiserId(advertiserId);
       //如果id不为空则更新
        Date nowTime=new Date();
        if (StringUtil.isNotEmpty(bean.getId())){
            int row=advertiserQualificationsService.updateByPrimaryKey(bean);
            return JsonResult.success(row);
        }
        //默认数据
        bean.setState(1);

        bean.setCreateTime(DateUtil.parse( DateUtil.getNowTime()));
        int row=advertiserQualificationsService.save(bean);
        return JsonResult.success(row);
    }

}
