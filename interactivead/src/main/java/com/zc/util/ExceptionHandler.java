package com.zc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.bean.JsonResult;
import org.solar.util.JsonUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class ExceptionHandler implements HandlerExceptionResolver {
    Logger logger= LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {

        ex.printStackTrace();
        JsonResult jsonResult=JsonResult.error();
        //判断是否是字段为空了
        if (ex instanceof DataIntegrityViolationException){
            DataIntegrityViolationException sqlEx=(DataIntegrityViolationException)ex;
            if (sqlEx.getRootCause() instanceof SQLException){
                SQLException rootSqlex=(SQLException)sqlEx.getRootCause();
                String msg=rootSqlex.getMessage();
                boolean b= Pattern.matches("^Field '.*' doesn't have a default value$", msg);
                if (b){
                    msg=msg.replace("Field '","");
                    msg=msg.replace("' doesn't have a default value","");
                    msg= DataBaseFieldDict.get(msg);
                    jsonResult.setMsg(msg+"不能为空!");
                }
            }
        }else {
            jsonResult.setMsg("系统错误!      \r\n"+ex.getMessage());
        }

        /*	使用response返回	*/
        response.setStatus(HttpStatus.OK.value()); //设置状态码
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
        response.setCharacterEncoding("UTF-8"); //避免乱码

        try {
            response.getWriter().write(JsonUtil.toJSONString(jsonResult));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("与客户端通讯异常:"+ e.getMessage(), e);
        }

        logger.debug("异常:" + ex.getMessage(), ex);
        return null;
    }

}