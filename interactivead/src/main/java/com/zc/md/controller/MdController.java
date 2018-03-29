package com.zc.md.controller;

import com.alibaba.fastjson.JSONObject;
import com.zc.md.entity.Prize;
import com.zc.md.entity.Template;
import com.zc.util.CookieUtil;
import com.zc.util.CustomLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.bean.JsonResult;
import org.solar.util.DateUtil;
import org.solar.util.IDGenerater;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author dlk
 */
@Controller
public class MdController {
    private static final Logger logger = LoggerFactory.getLogger(MdController.class);
    private static final Date initTime=new Date();

    /**
     *
     *  请求模板接口
     * @param response
     * @param mid 媒体id
     * @param cid 渠道id
     * @return 302跳转到模板
     * author dlk
     */
   @RequestMapping(value = "/md*", method = RequestMethod.GET)
    public String mdRedirect(HttpServletResponse  response,@RequestParam(value = "mid", required = false, defaultValue = "0") String mid, @RequestParam(value = "tid", required = false, defaultValue = "0") String tid,@RequestParam(value = "cid", required = false, defaultValue = "0") String cid){
       //获取参数
        System.out.println("md>mid:"+mid+",cid:"+cid);
        //定义302跳转地址
       String url302 ="";
        //记录模板曝光数据写入log
       long start = System.currentTimeMillis();
       Template template = new Template();
       template.setCreate_time(DateUtil.format(new Date()));
       template.setChannel_id(cid);
       template.setId(IDGenerater.getNextId());
       template.setMedia_owner_id(mid);

       //是否指定模板
       if("0".equals(tid)){
           //如果没有指定模板，返回随机的模板
           url302 = "http://hd.adsmar.com/zhuanpan/";
           template.setTemplate_id("0");
       }else if ("1".equals(tid)){
           //返回指定模板,则返回指定的模板
           url302 = "http://hd.adsmar.com/jindan";
           template.setTemplate_id("1");
       }else if ("2".equals(tid)){
           //返回指定模板,则返回指定的模板
           url302 = "http://hd.adsmar.com/zhuanpan/";
           template.setTemplate_id("2");
       }
       //存入cookie
        CookieUtil.addCookie(response,"mid",mid,60*60*24*7);//保存7天
        CookieUtil.addCookie(response,"cid",cid,60*60*24*7);//保存7天
       CookieUtil.addCookie(response,"tid",tid,60*60*24*7);//保存7天
       CookieUtil.addCookie(response,"cookieid",IDGenerater.getNextId(),60*60*24*30);//保存30天
       long end  = System.currentTimeMillis();
       long duration = end-start;
       template.setDuration(duration+"");
       Object json = JSONObject.toJSON(template);
       CustomLogUtil.putTemplateLog(json.toString());
       //302跳转
        return "redirect:"+url302;
    }

}
