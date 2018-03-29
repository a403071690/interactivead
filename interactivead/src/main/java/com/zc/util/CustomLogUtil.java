package com.zc.util;

import org.apache.log4j.Logger;
import org.solar.util.DateUtil;

import java.util.Date;

/**
 * @author dlk
 * @date 2018/03/20
 * 自定义日志工具类
 */
public class CustomLogUtil {

    /**
     * 写入模板曝光日志
     * @param loginfo
     */
    
    public static void putTemplateLog(String  loginfo){

        Logger logger = CustomLog.getInstance().createLogger(Config.get("logpath"), "template", "", true);
        CustomLog.getInstance().customLog(logger,loginfo);
        CustomLog.getInstance().closeCustomLog(logger);
        
    }

    /**
     * 写入创意曝光日志
     * @param loginfo
     */
    public static void putCreativeImpLog(String  loginfo){
        Logger logger = CustomLog.getInstance().createLogger(Config.get("logpath"), "creative_imp", "", true);
        CustomLog.getInstance().customLog(logger,loginfo);
        CustomLog.getInstance().closeCustomLog(logger);
    }

    /**
     * 写入创意点击日志
     * @param loginfo
     */
    public static void putCreativeClickLog(String  loginfo){
        Logger logger = CustomLog.getInstance().createLogger(Config.get("logpath"), "creative_click", "", true);
        CustomLog.getInstance().customLog(logger,loginfo);
        CustomLog.getInstance().closeCustomLog(logger);
    }
}
