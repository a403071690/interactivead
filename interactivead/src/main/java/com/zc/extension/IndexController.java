package com.zc.extension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.bean.JsonResult;
import org.solar.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    private static final Date initTime=new Date();
    @RequestMapping("/admin")
    public String adminRedirect() {
        return "redirect:static/html/admin.html?v="+System.currentTimeMillis()/1000/1000;
    }

    @RequestMapping("/{path}")
    public String path(@PathVariable("path") String path) {
        System.out.println("path="+path);
        return path;
    }

    @RequestMapping("/")
    public String index() {
        return "redirect:static/html/index.html?v="+System.currentTimeMillis()/1000/1000;
    }

    @RequestMapping("/tologin")
    public String tologin() {
        return "redirect:static/html/login.html?v="+System.currentTimeMillis()/1000/1000;
    }

    @RequestMapping("/version")
    @ResponseBody
    public String version() {
        Runtime rt=Runtime.getRuntime();
        String result="版本:"+ DateUtil.format(initTime)+"\r\n";
        result+="服务器时间:"+DateUtil.format(new Date())+"\r\n";
        result+="cpu核:"+rt.availableProcessors()+"\r\n";
        result+="Jvm空闲内存:"+(double)rt.freeMemory()/1000/1000+"m\r\n";
        result+="Jvm最大内存量:"+(double)rt.maxMemory()/1000/1000+"m\r\n";
        result+="Jvm已用内存量:"+(double)rt.totalMemory()/1000/1000+"m\r\n";

        return result;
    }

    @RequestMapping("/index")
    public String index2() {
        return "index";
    }

    @RequestMapping("/404")
    @ResponseBody
    public JsonResult e404() {
        return JsonResult.error("404");
    }
    @RequestMapping("/400")
    @ResponseBody
    public JsonResult e400(HttpServletResponse res) {
        res.setStatus(200);
        return JsonResult.error("数据格式不正确!");
    }

}
