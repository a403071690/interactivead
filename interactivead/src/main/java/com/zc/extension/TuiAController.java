package com.zc.extension;


import cn.com.duiba.credits.sdk.CreditTool;
import cn.com.duiba.credits.sdk.entity.CreditConsumeParams;
import cn.com.duiba.credits.sdk.entity.CreditNotifyParams;
import cn.com.duiba.credits.sdk.result.CreditConsumeResult;
import com.zc.entity.MediaOwnerInfo;
import com.zc.service.MediaOwnerInfoService;
import com.zc.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.bean.JsonResult;
import org.solar.coder.AESCoder;
import org.solar.coder.Md5Util;
import org.solar.util.IDGenerater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TuiAController {
    private static final Logger logger = LoggerFactory.getLogger(TuiAController.class);
    AESCoder aesCoder = new AESCoder(Config.aesPassword);

    @Autowired
    private MediaOwnerInfoService mediaOwnerInfoService;

    @RequestMapping("/api/duiba")
    public String register(HttpServletRequest request , HttpServletResponse response) throws Exception {

        CreditTool tool=new CreditTool(Config.get("tuia.appKey"), Config.get("tuia.appSecret"));
        Map params=new HashMap();
        params.put("uid",IDGenerater.getNextId());
        params.put("credits","100");
        String redirect = null;
        if(redirect!=null){
            // redirect是目标页面地址，如果要跳转到积分商城指定页面，redirect地址就是目标页面地址
            //此处请设置成一个外部传进来的参数，方便运营灵活配置
            params.put("redirect", redirect);
        }
        String url=tool.buildUrlWithSign("http://www.duiba.com.cn/autoLogin/autologin?",params);
            //此url即为免登录url
        logger.info("免密登录url"+url);
        return "redirect:"+url;
        //return  JsonResult.success(url);
    }

    @RequestMapping("/consume")
    @ResponseBody
    public String consume(HttpServletRequest request) {
        CreditTool tool = new CreditTool(Config.get("tuia.appKey"), Config.get("tuia.appSecret"));
        boolean success = false;
        String errorMessage = "";
        String bizId =null;
        Long credits=0L;
        try {
            CreditConsumeParams params = tool.parseCreditConsume(request);

            logger.info(params.getIp()+""+params.getOrderNum()+""+params.getCredits());
            bizId = IDGenerater.getNextId();//todo(); //开发者业务订单号，保证唯一不重复
            credits = 100L;//getCredits(); // getCredits()是根据开发者自身业务，获取的用户最新剩余积分数。
            success = true;
        } catch (Exception e) {
            success = false;
            errorMessage = e.getMessage();
            e.printStackTrace();
        }
        CreditConsumeResult ccr = new CreditConsumeResult(success);
        ccr.setBizId(bizId);
        ccr.setErrorMessage(errorMessage);
        ccr.setCredits(credits);
        logger.info(ccr.toString());
        return ccr.toString();//返回扣积分结果json信息
    }

    /*
     *  兑换订单的结果通知请求的解析方法
     *  当兑换订单成功时，兑吧会发送请求通知开发者，兑换订单的结果为成功或者失败，如果为失败，开发者需要将积分返还给用户
     */
    @RequestMapping("/creditNotify")
    @ResponseBody
    public String creditNotify(HttpServletRequest request) {
        CreditTool tool = new CreditTool(Config.get("tuia.appKey"), Config.get("tuia.appSecret"));

        try {
            CreditNotifyParams params = tool.parseCreditNotify(request);//利用tool来解析这个请求
            String orderNum = params.getOrderNum();
            logger.info("dingdan编号："+orderNum);
            if (params.isSuccess()) {
                //兑换成功
                logger.info("兑换成功");
                return  "ok";
            } else {
                //兑换失败，根据orderNum，对用户的金币进行返还，回滚操作
                logger.info("兑换失败，根据orderNum，对用户的金币进行返还，回滚操作");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return  "ok";
    }

}
