package com.zc.md.controller;

import com.alibaba.fastjson.JSONObject;
import com.zc.entity.TemplateManage;
import com.zc.md.entity.Prize;
import com.zc.md.entity.Template;
import com.zc.util.CookieUtil;
import com.zc.util.CustomLogUtil;
import com.zc.util.RedisPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.bean.JsonResult;
import org.solar.util.DateUtil;
import org.solar.util.IDGenerater;
import org.solar.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
       Jedis jedis = RedisPool.getJedis();
       TemplateManage templateManage = new TemplateManage();

       //指定了模板
       try {
           //是否指定模板
           if ("0".equals(tid) || StringUtil.isEmpty(tid)){
               //未指定,随机选取模板
               List<TemplateManage> templateManageList = JSONObject.parseArray(jedis.hvals("template").toString(),TemplateManage.class);
               Iterator<TemplateManage> templateManageIterator = templateManageList.listIterator();
               while (templateManageIterator.hasNext()){
                   TemplateManage templateManage1 = templateManageIterator.next();
                   if (templateManage1.getState().equals("2")){
                       templateManageIterator.remove();
                   }
               }
               int index = new Random().nextInt(templateManageList.size());
               templateManage = templateManageList.get(index);
               logger.info("随机模板:"+templateManage.getId());
           }else {
               //指定
               templateManage = JSONObject.parseObject(jedis.hget("template", tid), TemplateManage.class);
               logger.info("指定模板:" + templateManage.getId());
           }
       }catch (Exception e){
           logger.error(e.getMessage());
           //模板不存在等异常问题出现时候
           List<TemplateManage> templateManageList = JSONObject.parseArray(jedis.hvals("template").toString(),TemplateManage.class);
           Iterator<TemplateManage> templateManageIterator = templateManageList.listIterator();
           while (templateManageIterator.hasNext()){
               TemplateManage templateManage1 = templateManageIterator.next();
               if (templateManage1.getState().equals("2")){
                   templateManageIterator.remove();
               }
           }
           int index = new Random().nextInt(templateManageList.size());
           templateManage = templateManageList.get(index);
           logger.info("出错了，随机一个模板吧:"+templateManage.getId());
       }
       url302 = templateManage.getUrl()+"?mid="+mid+"&cid="+cid ;
       //存入cookie
       CookieUtil.addCookie(response,"mid",mid,60*60*24*7);//保存7天
       CookieUtil.addCookie(response,"cid",cid,60*60*24*7);//保存7天
       CookieUtil.addCookie(response,"tid",tid,60*60*24*7);//保存7天
       CookieUtil.addCookie(response,"cookieid",IDGenerater.getNextId(),60*60*24*30);//保存30天
       template.setCreate_time(DateUtil.format(new Date()));
       template.setChannel_id(cid);
       template.setId(IDGenerater.getNextId());
       template.setMedia_owner_id(mid);
       template.setTemplate_id(templateManage.getId());
       template.setUser_finger_id("");
       template.setCreate_time(DateUtil.format(new Date()));
       template.setCurrent_time_millis(System.currentTimeMillis());
       long end  = System.currentTimeMillis();
       long duration = end-start;
       template.setDuration(duration+"");
       Object json = JSONObject.toJSON(template);
       CustomLogUtil.putTemplateLog(json.toString());
       //302跳转
       return "redirect:"+url302;
    }

}
