package com.zc.util;

import org.solar.util.PropertiesUtil;

import java.util.Map;

/**
 * Created by xianchuanwu on 2017/9/24.
 */
public class Config {
    private static Map map=PropertiesUtil.getProperty("config.properties");
//    static{
//        map.putAll(PropertiesUtil.getProperty("file:/ssp-config.txt"));
//    }
    public static final String aesPassword=(String)map.get("aesPassword");



    public static String get(String key) {
        return (String)map.get(key);
    }
}
