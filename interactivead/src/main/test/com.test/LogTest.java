package com.test;


import com.alibaba.fastjson.JSONObject;
import com.zc.md.entity.Template;
import com.zc.util.CustomLogUtil;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.util.DateUtil;
import org.solar.util.IDGenerater;

import java.util.Date;


public class LogTest {
   public static final   Logger logger = Logger.getLogger(LogTest.class);
    private static final org.slf4j.Logger loggers = LoggerFactory.getLogger(LogTest.class);
    public static void main(String[] args){
         Person p = new Person("噢噢噢噢噢噢噢噢哦哦哦","少时诵诗书所所所ss");


        Template template = new Template();
        template.setId(IDGenerater.getNextId());
        template.setTemplate_id("1");
        template.setChannel_id("0");
        template.setCreate_time(DateUtil.format(new Date()));
        template.setDuration(System.currentTimeMillis()-36000+"");
        template.setMedia_owner_id("");
        template.setUser_cookie_id("BBCSYOOO829183");
        template.setUser_open_id("");
        template.setUser_finger_id("");

        Object json = JSONObject.toJSON(template);
         System.out.println( DateUtil.format(new Date() ));
        logger.debug(json.toString());
        loggers.debug(json.toString()+DateUtil.getTodayString());
        CustomLogUtil.putTemplateLog(json.toString());
    }

}
