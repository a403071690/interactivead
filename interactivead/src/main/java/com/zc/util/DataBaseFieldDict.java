package com.zc.util;

import org.solar.util.PropertiesUtil;

import java.util.Map;

public class DataBaseFieldDict {
    private static Map map= PropertiesUtil.getProperty("dataBaseFieldDict.properties");

    public static String get(String key) {
        return (String)map.get(key);
    }

    public static void main(String[] args) {
        System.out.println(get("ad_media_type_id"));
    }
}
