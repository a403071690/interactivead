package com.zc.util;

import org.solar.coder.AESCoder;
import org.solar.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TokenUtil {
    static AESCoder aesCoder = new AESCoder(Config.aesPassword);
    public static String getUid(HttpServletRequest hreq) {
        String token = hreq.getHeader("token");
        String str = aesCoder.AESDecode(token);
        Map<String, String> map = JsonUtil.parseObject(str);
        String id = map.get("id");
        return id;
    }
    public static String getUrl(HttpServletRequest hreq) {
        String token = hreq.getHeader("token");
        String str = aesCoder.AESDecode(token);
        Map<String, String> map = JsonUtil.parseObject(str);
        String url = map.get("url");
        return url;
    }

}
