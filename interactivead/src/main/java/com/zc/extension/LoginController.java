package com.zc.extension;

import com.zc.entity.AdvertiserInfo;
import com.zc.entity.MediaOwnerInfo;
import com.zc.service.AdvertiserInfoService;
import com.zc.service.MediaOwnerInfoService;
import com.zc.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.bean.JsonResult;
import org.solar.coder.AESCoder;
import org.solar.coder.Md5Util;
import org.solar.util.JsonUtil;
import org.solar.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    AESCoder aesCoder = new AESCoder(Config.aesPassword);
    @Resource
    private AdvertiserInfoService advertiserInfoService;
    @Resource
    private MediaOwnerInfoService mediaOwnerInfoService;

    @RequestMapping(value = "isLogin")
    @ResponseBody
    public JsonResult isLogin(HttpServletRequest hreq, String t) throws Exception {
        String token = hreq.getHeader("token");
        if (StringUtil.isEmpty(token)) {
            return JsonResult.error("未登录!");
        }
        String str = aesCoder.AESDecode(token);
        if (str == null) {
            return JsonResult.error("未登录!");
        }
        Map map = JsonUtil.parseObject(str);
        long loginTime = Long.valueOf(String.valueOf(map.get("d")));
        long e = Long.valueOf(String.valueOf(map.get("e")));//过期时间秒单位
        long pastTime = System.currentTimeMillis() - loginTime;
        if (!map.get("t").equals(t)) {
            return JsonResult.error("未登录账号!");
        }
        if (pastTime < 1000 * e) {
            return JsonResult.success();
        }

        return JsonResult.error("未登录!");
    }

    @RequestMapping(value = "login")
    @ResponseBody
    public JsonResult login(HttpServletRequest request, String account, String password) throws Exception {
        if (StringUtil.isEmpty(account) || StringUtil.isEmpty(password)) {
            return JsonResult.error("账号密码不能为空!");
        }
        password = Md5Util.getMd5Hex(password);
        List<AdvertiserInfo> li = advertiserInfoService.selectByWhere("loginName", account, "password", password);
        if (li == null || li.size() == 0) {
            return JsonResult.error("账号或密码错误!");
        }
        //状态：1待审核 2审核通过 3审核未通过 4冻结
        AdvertiserInfo advertiserInfo = li.get(0);
        if (advertiserInfo.getState() == 1) {
            return JsonResult.error("账号未审核!");
        }
        if (advertiserInfo.getState() == 3) {
            return JsonResult.error("账号审核未通过!原因:" + advertiserInfo.getStateMsg());
        }
        if (advertiserInfo.getState() == 4) {
            return JsonResult.error("账号冻结!");
        }
        if (advertiserInfo.getState() == 2) {
            Map map = new HashMap();
            logger.info("login_id:"+advertiserInfo.getId());
            map.put("id", advertiserInfo.getId());
            map.put("t", "u");
            map.put("e", 60 * 60 * 1);//秒单位
            map.put("d", System.currentTimeMillis());
            String token = aesCoder.AESEncode(JsonUtil.toJSONString(map));
            return JsonResult.success(token);
        }
        return JsonResult.error("账户处于未知状态!");

    }

    @RequestMapping("mediaLogin")
    @ResponseBody
    public JsonResult agent(HttpServletRequest request, String account,String password) throws Exception {
        String token = request.getHeader("token");
        String str = aesCoder.AESDecode(token);
        Map mapt = JsonUtil.parseObject(str);
        String type = (String) mapt.get("t");
        if (!type.equals("s")) {//带正确token 管理员的无需拦截
            return JsonResult.error();
        }
        List<MediaOwnerInfo> li = mediaOwnerInfoService.selectByWhere("mail", account,"sss",password);
        if (li == null || li.size() == 0) {
            return JsonResult.error("账号或密码错误!");
        }
        Map map = new HashMap();
        map.put("id", li.get(0).getId());
        map.put("t", "u");
        map.put("e", 60 * 60 * 1);//秒单位
        map.put("d", System.currentTimeMillis());
        String newToken = aesCoder.AESEncode(JsonUtil.toJSONString(map));

        return JsonResult.success(newToken);

    }

    @RequestMapping("adminLogin")
    @ResponseBody
    public JsonResult adminLogin(HttpServletRequest request, String account, String password) throws Exception {
        if (!"admin".equals(password) || !"admin".equals(account)) {
            return JsonResult.error("密码错误!");
        }
        Map map = new HashMap();
        map.put("id", "ssp");
        map.put("t", "s");
        map.put("e", 60 * 60 * 1);//秒单位
        map.put("d", System.currentTimeMillis());
        String token = aesCoder.AESEncode(JsonUtil.toJSONString(map));
        return JsonResult.success(token);
    }

    @RequestMapping("loginOut")
    @ResponseBody
    public JsonResult loginOut(HttpServletRequest hreq) throws Exception {

        return JsonResult.success();
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
