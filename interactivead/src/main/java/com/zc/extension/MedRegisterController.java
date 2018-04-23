package com.zc.extension;


import com.zc.entity.AdvertiserInfo;
import com.zc.entity.MediaOwnerInfo;
import com.zc.service.AdvertiserInfoService;
import com.zc.service.MediaOwnerInfoService;
import com.zc.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.bean.JsonResult;
import org.solar.coder.AESCoder;
import org.solar.coder.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MedRegisterController {
    private static final Logger logger = LoggerFactory.getLogger(MedRegisterController.class);
    AESCoder aesCoder = new AESCoder(Config.aesPassword);

    @Autowired
    private MediaOwnerInfoService mediaOwnerInfoService;

    @RequestMapping("medregister")
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
        List li=mediaOwnerInfoService.selectByWhere("loginName",mail);//  selectbyWhere("mail",mail);
        if (li.size()>0){
            return JsonResult.error("该邮箱已经被注册!");
        }
        MediaOwnerInfo mediaOwnerInfo  = new MediaOwnerInfo();
        mediaOwnerInfo.setLoginName(mail);
        mediaOwnerInfo.setPassword(Md5Util.getMd5Hex(password));
        mediaOwnerInfo.setType(1);
        mediaOwnerInfo.setPhone(phoneNumber);
        //状态：1待审核 2审核通过 3审核未通过 4冻结
        mediaOwnerInfo.setState(1);
        mediaOwnerInfoService.save(mediaOwnerInfo);
        return JsonResult.success("注册成功!");

    }
    @RequestMapping("/forgetMedPassword")
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
        List<MediaOwnerInfo> mediaOwnerInfoList = mediaOwnerInfoService.selectByWhere("phoneNumber",mobileNo);
        password=Md5Util.getMd5Hex(password);
        for (MediaOwnerInfo s:mediaOwnerInfoList ) {
            s.setPassword(password);
            mediaOwnerInfoService.updateByPrimaryKey(s);
        }
        return JsonResult.success();

    }

}
