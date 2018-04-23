package com.zc.util;

import org.slf4j.LoggerFactory;
import org.solar.bean.JsonResult;
import org.solar.coder.AESCoder;
import org.solar.util.JsonUtil;
import org.solar.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class BaseInterceptor extends HandlerInterceptorAdapter {
    AESCoder aesCoder = new AESCoder(Config.aesPassword);
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BaseInterceptor.class);

    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String path = request.getContextPath();
        String basePath = scheme + "://" + serverName + ":" + port + path;
        //response.setHeader("Access-Control-Allow-Origin", "*");
        // CORS "pre-flight" request
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE,OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type,Origin, X-Requested-With, Content-Type, Accept,x-csrf-token");
        response.addHeader("Access-Control-Max-Age", "1800");//30 min





/**
        if (path != null && !"/".equals(path) && !"".equals(path)) {
            uri = uri.replace(path, "");
        }
        if (uri == null || uri.equals("") || uri.equals("/")) {
            return true;
        }

        String[] uris = uri.split("/");
        if (uris.length == 2 ) {//无需权限
            return true;
        }
        String token = request.getHeader("token");
        if (StringUtil.isEmpty(token)) {
            returnJson(response, JsonResult.error("未登录"));
        }
        String str = aesCoder.AESDecode(token);
        if (str == null) {
            returnJson(response, JsonResult.error("未登录!"));
        }
        Map map = JsonUtil.parseObject(str);
        String type=(String)map.get("t");
//      System.out.println("uris[1]="+uris[1]);
        if (uris[1].equals("ssp")) {//带正确token的无需拦截 ssp
            return true;
        }
        if (type.equals("s")) {//带正确token 管理员的无需拦截
            return true;
        }

        returnJson(response, JsonResult.error("权限错误"));
        return false;**/
        return true;
    }

    public void returnJson(HttpServletResponse response, JsonResult jsonResult) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JsonUtil.toJSONString(jsonResult));

        } catch (IOException e) {
            logger.error("response error", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }

}