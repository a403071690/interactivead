package com.zc.extension;

import com.zc.util.Config;
import net.coobird.thumbnailator.Thumbnails;
import org.solar.bean.JsonResult;
import org.solar.util.IDGenerater;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;


@Controller
public class FileUploadController {

    @RequestMapping("imgUpload")
    @ResponseBody
    public JsonResult imgUpload(HttpServletRequest request) throws IOException {

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (!multipartResolver.isMultipart(request)) {
            return JsonResult.error("传输格式错误!");
        }
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iter = multiRequest.getFileNames();
        //只接受第一个文件
        if (!iter.hasNext()){
            return JsonResult.error("空文件!");
        }
        MultipartFile file = multiRequest.getFile((String) iter.next());
        if (file == null) {
            return JsonResult.error("空文件!");
        }
        String fileName = file.getOriginalFilename();
        Calendar calendar = Calendar.getInstance();
        String datepath = calendar.get(Calendar.YEAR) + "/" +
                (calendar.get(Calendar.MONTH) + 1) + "_" + calendar.get(Calendar.DATE) + "/";
        String uploadRealPath = Config.get("uploadRealPath")+datepath;//"/ssp/imgs/" + datepath;
        File uploadRealPathfile = new File(uploadRealPath);
        if (!uploadRealPathfile.exists()) {
            uploadRealPathfile.mkdirs();
            System.out.println("uploadRealPathfile:mkdirs......");
        }
        System.out.println("uploadRealPath=" + uploadRealPath);
        String fileType=fileName.substring(fileName.lastIndexOf(".") + 1);
        File localFile = new File(uploadRealPath + IDGenerater.getNextId() + "." + fileType);
        localFile.createNewFile();
        file.transferTo(localFile);
        //校验宽高
        String height= (String) request.getParameter("height");
        String width= (String) request.getParameter("width");
        if (height==null||width==null){
            return JsonResult.success(localFile.getPath());
        }
        BufferedImage bufferedImage=Thumbnails.of(localFile).scale(1.0).outputQuality(1.0).asBufferedImage();
        String imgheight=bufferedImage.getHeight(null)+"";
        String imgwidth=bufferedImage.getWidth(null)+"";

        if (!imgheight.equalsIgnoreCase(height)){
            return JsonResult.error("图片高度不符!"+imgheight);
        }
        if (!imgwidth.equalsIgnoreCase(width)){
            return JsonResult.error("图片宽度不符!"+imgwidth);
        }
        return JsonResult.success(localFile.getPath());
    }


}