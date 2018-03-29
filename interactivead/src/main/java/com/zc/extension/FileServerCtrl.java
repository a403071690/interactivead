package com.zc.extension;

import org.solar.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class FileServerCtrl {
    private Map fileMap = new ConcurrentHashMap();

    @RequestMapping("file_server")
    public String file_server(HttpServletResponse res, String uri) throws Exception {
        if (uri == null || "".equals(uri)) {
            return null;
        }
        System.out.println("uri:"+uri);
        byte[] byteArray = null;// (byte[]) fileMap.get(id);
        res.setContentType("image/jpg");
        if (byteArray == null) {
            byteArray = FileUtil.getBytesFromFile(uri);
            // fileMap.put(id, byteArray);
            res.setContentType("image/jpg");
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.UK);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            res.setHeader("Last-Modified", sdf.format(new Date()));
        }
        res.getOutputStream().write(byteArray);
        res.getOutputStream().flush();
        res.getOutputStream().close();

//        res.setContentType("image/png");
//        res.getOutputStream().write(byteArray);
//        res.getOutputStream().flush();
//        res.getOutputStream().close();
        return null;
    }


}
