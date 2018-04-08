package com.zc.controller.base;

import com.zc.controller.base.BaseController;
import com.zc.entity.AdvertiserInvoice;
import com.zc.service.AdvertiserInvoiceService;
import com.zc.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.bean.JsonResult;
import org.solar.bean.Page;
import org.solar.bean.Pageable;
import org.solar.util.DateUtil;
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
 * @date 2018年04月08日 17:11
 */
@Controller
@RequestMapping("advertiserInvoice")
public class AdvertiserInvoiceCrudController extends BaseController {
    Logger logger=LoggerFactory.getLogger(AdvertiserInvoiceCrudController.class);
    @Resource
    private AdvertiserInvoiceService advertiserInvoiceService;
    @RequestMapping("/select")
    @ResponseBody
    public  JsonResult select(HttpServletRequest req,@RequestParam Map requestMap,
                            @RequestParam(value = "idList[]", required = false) List idList) {
        String advertiserId= TokenUtil.getUid(req);
        requestMap.put("idList", idList);

        String id=(String)requestMap.get("id");
        String pageNum=(String)requestMap.get("pageNum");
        if (!"D9B3DCECFC000000D00000000016E000".equals(advertiserId)){
            requestMap.put("advertiserId", advertiserId);
        }
         //按条件查询List
        if (StringUtil.isEmpty(id)&&StringUtil.isEmpty(pageNum)){
           return JsonResult.success(advertiserInvoiceService.selectByWhere(requestMap));
        }
        //按id查询
        if (StringUtil.isNotEmpty(id)){
            AdvertiserInvoice advertiserInvoice=advertiserInvoiceService.getById(id);
            return JsonResult.success(advertiserInvoice);
        }
        //按分页查询
        Pageable pageable=Pageable.getPageable(requestMap);
        Page page=advertiserInvoiceService.selectPage(pageable);
//            List<AdvertiserInvoice> advertiserInvoiceList=page.getList();
//            List childIdList=BeanUtil.getProperties(advertiserInvoiceList,"childId");
//            List childList=advertiserInvoiceService.selectByWhere("idList",childIdList);
//            List<Map> resultList=BeanUtil.leftJoin(advertiserInvoiceList,childList,"childId","id");
//            page.setList(resultList);
        return JsonResult.success(page);

    }
    @RequestMapping("/delete")
    @ResponseBody
    public  JsonResult delete(String id) {
        int row=advertiserInvoiceService.delete(id);
        return JsonResult.success(row);
    }
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public  JsonResult saveOrUpdate(HttpServletRequest request ,@RequestBody Map requestMap) {
        String advertiserId= TokenUtil.getUid(request);
        AdvertiserInvoice bean= JsonUtil.toJavaObject(requestMap,AdvertiserInvoice.class);

       //如果id不为空则更新
        if (StringUtil.isNotEmpty(bean.getId())){
            bean.setUpdateTime(new Date());
            int row=advertiserInvoiceService.updateByPrimaryKey(bean);
            return JsonResult.success(row);
        }
        bean.setCreateTime(new Date());
        System.out.println("userid:"+advertiserId);
        bean.setAdvertiserId(advertiserId);
        int row=advertiserInvoiceService.save(bean);
        return JsonResult.success(row);
    }


}
