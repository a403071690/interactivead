package com.zc.extension;

import com.zc.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.bean.JsonResult;
import org.solar.coder.AESCoder;
import org.solar.util.AliMessageSender;
import org.solar.util.Captcha;
import org.solar.util.JsonUtil;
import org.solar.util.PropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class CaptchaController {
    private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);
    AESCoder aesCoder = new AESCoder(Config.aesPassword);
    static {
        AliMessageSender.accessKeyId= PropertiesUtil.getValueByKey("ali_Msg_accessKeyId","config.properties");
        AliMessageSender.accessSecret= PropertiesUtil.getValueByKey("ali_Msg_accessSecret","config.properties");
    }

    @RequestMapping("captcha")
    public String captcha(HttpSession session,HttpServletResponse hres) throws Exception {
        String code=Captcha.randomString();
        session.setAttribute("captcha",code);
        byte[] img=Captcha.generateCaptchaImage(code);
        OutputStream os=hres.getOutputStream();
        os.write(img);
        os.close();
        return null;

    }
    @RequestMapping("sendMsgCode")
    @ResponseBody
    public JsonResult login(HttpSession session,String imgCode,String mobileNo) throws Exception {
        String code=(String)session.getAttribute("captcha");
        if (code==null||!code.equalsIgnoreCase(imgCode)){
            return JsonResult.error("图片验证码错误!");
        };
        //发送验证码
        String mobileNoCode=new Random().nextInt(8999)+1000+"";
        System.out.println("mobileNoCode="+mobileNoCode);
        session.setAttribute("mobileNoCode:"+mobileNo,mobileNoCode);
        Map codeMap=new HashMap();
        codeMap.put("code",mobileNoCode);
        //AliMessageSender.sendMsg("纷熙平台","SMS_117300079",mobileNo,JsonUtil.toJSONString(codeMap));
        return JsonResult.success("发送成功!");

    }


}
