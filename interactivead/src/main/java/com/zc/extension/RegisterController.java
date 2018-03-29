package com.zc.extension;


import com.zc.entity.AdvertiserInfo;
import com.zc.service.AdvertiserInfoService;
import com.zc.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.bean.JsonResult;
import org.solar.coder.AESCoder;
import org.solar.coder.Md5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    AESCoder aesCoder = new AESCoder(Config.aesPassword);
    @Resource
    private AdvertiserInfoService advertiserInfoService;

    @RequestMapping("advregister")
    @ResponseBody
    public JsonResult register(HttpSession session,String imgCode,String mail,String password,Integer type,String msgCode,String phoneNumber) throws Exception {
        String captcha=(String)session.getAttribute("captcha");

        if (!captcha.equalsIgnoreCase(imgCode)){
            return JsonResult.error("验证码错误!");
        };
        String captchaPhoneNumber=(String)session.getAttribute("mobileNoCode:"+phoneNumber);
        if (captchaPhoneNumber==null){
            return JsonResult.error("请先获取手机验证码!");
        }
        if (!captchaPhoneNumber.equalsIgnoreCase(msgCode)){
            return JsonResult.error("手机验证码错误!");
        };
        List li=advertiserInfoService.selectByWhere("login_name",mail);//  selectbyWhere("mail",mail);
        if (li.size()>0){
            return JsonResult.error("该邮箱已经被注册!");
        }
        AdvertiserInfo advertiserInfo=new AdvertiserInfo();
        advertiserInfo.setLoginName(mail);
        advertiserInfo.setPassword(Md5Util.getMd5Hex(password));
        System.out.println("type:"+type);
        advertiserInfo.setType(type);
        advertiserInfo.setPhone(phoneNumber);
        //状态：1待审核 2审核通过 3审核未通过 4冻结
        advertiserInfo.setState(1);
        advertiserInfoService.save(advertiserInfo);
        return JsonResult.success("注册成功!");

    }
    @RequestMapping("/forgetPassword")
    @ResponseBody
    public JsonResult forgetPassword(HttpServletRequest hreq, String password, String mobileNo, String msgCode) {
        String captcha=(String)hreq.getSession().getAttribute("mobileNoCode:"+mobileNo);
        if (captcha==null){
            return JsonResult.error("请先获取手机验证码!");
        }
        if (!captcha.equalsIgnoreCase(msgCode)){
            return JsonResult.error("手机验证码错误!");
        };
        //按id查询
        List<AdvertiserInfo> advertiserInfoList = advertiserInfoService.selectByWhere("phoneNumber",mobileNo);
        password=Md5Util.getMd5Hex(password);
        for (AdvertiserInfo s:advertiserInfoList ) {
            s.setPassword(password);
            advertiserInfoService.updateByPrimaryKey(s);
        }
        return JsonResult.success();

    }

}
