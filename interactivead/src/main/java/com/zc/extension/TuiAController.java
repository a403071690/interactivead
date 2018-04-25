package com.zc.extension;


import cn.com.duiba.credits.sdk.CreditTool;
import cn.com.duiba.credits.sdk.entity.AddCreditsParams;
import cn.com.duiba.credits.sdk.entity.CreditConsumeParams;
import cn.com.duiba.credits.sdk.entity.CreditNotifyParams;
import cn.com.duiba.credits.sdk.result.CreditConsumeResult;
import com.zc.entity.*;
import com.zc.service.*;
import com.zc.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.bean.JsonResult;
import org.solar.coder.AESCoder;
import org.solar.coder.Md5Util;
import org.solar.util.DateUtil;
import org.solar.util.IDGenerater;
import org.solar.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TuiAController {
    private static final Logger logger = LoggerFactory.getLogger(TuiAController.class);
    AESCoder aesCoder = new AESCoder(Config.aesPassword);

    @Autowired
    private DuiCreditsConsumeService duiCreditsConsumeService;
    @Autowired
    private DuiCreditsNotifyService duiCreditsNotifyService;
    @Autowired
    private DuiCreditsLogService duiCreditsLogService;
    @Autowired
    private SignUserService signUserService;
    @Autowired
    private DuiCreditsAddService duiCreditsAddService;

    @RequestMapping("/api/duiba")
    public String autoLogin(HttpServletRequest request , HttpServletResponse response,  @RequestParam(value = "fid", required = false, defaultValue = "0") String fid,@RequestParam(value = "dbredirect", required = false, defaultValue ="") String dbredirect) throws Exception {

        if (StringUtil.isEmpty(fid)){
            fid=IDGenerater.getNextId();
        }
        logger.info("fid:"+fid);
        SignUser signUser = null;
        Long credits = 0L;
        String uid= "";
        //判断是 新老用户
        List<SignUser> signUsersList = signUserService.selectByWhere("fid",fid);
        if (signUsersList.size()==0 || signUsersList == null){
            logger.error("新用户，奖励5积分");
            //新用户，给5积分，保存到数据库
            signUser = new SignUser();
            signUser.setFid(fid);
            signUser.setUpdatetime(new Date());
            signUser.setTime(new Date());
            signUserService.save(signUser);
            List<SignUser> signUsersLists =  signUserService.selectByWhere("fid",fid);
            if (signUsersList.size()==0 || signUsersList == null){
                uid = signUsersLists.get(0).getId();
            }
            DuiCreditsLog duiCreditsLog = new DuiCreditsLog();
            duiCreditsLog.setCreateTime(DateUtil.format(new Date()));
            duiCreditsLog.setUserInfoId(uid);
            duiCreditsLog.setId(IDGenerater.getNextId());
            duiCreditsLog.setCredits(5L);
            credits = 5L;
            duiCreditsLog.setCreditsType(1);
            duiCreditsLog.setDescription("第一次访问赠送积分");
            duiCreditsLogService.save(duiCreditsLog);

        }else
        {
            logger.error("老用户，查询积分");
            signUser = signUsersList.get(0);
            //List<DuiCreditsLog> duiCreditsLogList = duiCreditsLogService.selectByWhere("user_info_id",signUser.getId());
            List<Map> list = duiCreditsLogService.executeSql("SELECT (SELECT  sum(credits)   from dui_credits_log WHERE   user_info_id='"+signUser.getId()+"' and credits_type=1 ) - (SELECT IFNULL((SELECT sum(credits)  from dui_credits_log WHERE   user_info_id='"+signUser.getId()+"'  and credits_type=2),0)) as credits");
            if (StringUtil.isNotEmpty(list)  && list.size()>0) {
                   logger.info("积分："+list.get(0).get("credits").toString());
                   credits = Long.parseLong(list.get(0).get("credits").toString());
                   logger.error("查询到了积分："+credits);
            }else{
                credits = 5L;
                logger.error("注册的用户没有积分记录");
            }
        }

        CreditTool tool=new CreditTool(Config.get("tuia.appKey"), Config.get("tuia.appSecret"));
        Map params=new HashMap();
        params.put("uid",signUser.getId());
        params.put("credits",credits+"");
        //头像：avatar 昵称：nickname
        //String avatar="http://www.goupuzi.com/attachment/Mon_1110/2_78193_d53aae9ded8bc26.jpg";
        //String nickname = "一条大白狗";
        //params.put("dcustom","avatar="+ URLEncoder.encode(avatar,"utf-8")+"nickname="+ URLEncoder.encode(nickname,"utf-8"));
        if(StringUtil.isNotEmpty(dbredirect)){
            // redirect是目标页面地址，如果要跳转到积分商城指定页面，redirect地址就是目标页面地址
            //此处请设置成一个外部传进来的参数，方便运营灵活配置
            params.put("redirect", dbredirect);
        }
        String url=tool.buildUrlWithSign("http://www.duiba.com.cn/autoLogin/autologin?",params);
            //此url即为免登录url
        logger.info("免密登录url"+url);
        return "redirect:"+url;
    }

    /**
     * 积分消费
     * @param request
     * @return
     */
    @RequestMapping("/consume")
    @ResponseBody
    public String consume(HttpServletRequest request) {
        CreditTool tool = new CreditTool(Config.get("tuia.appKey"), Config.get("tuia.appSecret"));
        boolean success = false;
        String errorMessage = "";
        String bizId =null;
        Long credits=0L;
        DuiCreditsConsume duiCreditsConsume = new DuiCreditsConsume();
        DuiCreditsLog duiCreditsLog = new DuiCreditsLog();
        try {
            CreditConsumeParams params = tool.parseCreditConsume(request);
            logger.info("用户uid="+params.getUid());
            //获取该用户的积分情况
            //新用户：5积分 抽奖奖励1积分，每个活动抽奖次数为8次。SELECT sum(credits) as credits from dui_credits_log WHERE   user_info_id='"+params.getUid()+"' "   GROUP BY credits_type ORDER BY credits_type
            //List<Map> list = duiCreditsLogService.executeSql("SELECT credits_type, sum(credits) as credits from dui_credits_log WHERE   user_info_id='"+params.getUid()+"' GROUP BY credits_type ORDER BY credits_type ");
            List<Map> list = duiCreditsLogService.executeSql("SELECT (SELECT  sum(credits)   from dui_credits_log WHERE   user_info_id='"+params.getUid()+"' and credits_type=1 ) - (SELECT IFNULL((SELECT sum(credits)  from dui_credits_log WHERE   user_info_id='"+params.getUid()+"'  and credits_type=2),0)) as credits");
            logger.info("list:"+list+"s："+list.size());
            if (StringUtil.isNotEmpty(list)  && list.size()>0) {
                logger.info("积分："+list.get(0).get("credits").toString());
                credits = Long.parseLong(list.get(0).get("credits").toString());
                logger.error("查询到了积分："+credits);
            }else{
                credits = 5L;
                logger.error("注册的用户没有积分记录");
            }
            logger.info("credits<params.getCredits()"+credits+"-------"+params.getCredits());
            if (credits<params.getCredits()){
                logger.info("积分不足");
                CreditConsumeResult ccr = new CreditConsumeResult(false);
                ccr.setBizId(bizId);
                ccr.setErrorMessage("积分不足");
                ccr.setCredits(0L);
                return ccr.toString();
            }else{
                logger.info(params.getIp()+"|"+params.getOrderNum()+"|"+params.getCredits());
                duiCreditsConsume.setActualPrice(params.getActualPrice());
                duiCreditsConsume.setCredits(params.getCredits());
                duiCreditsConsume.setAppKey(params.getAppKey());
                duiCreditsConsume.setDescription(params.getDescription());
                duiCreditsConsume.setFacePrice(params.getFacePrice());
                duiCreditsConsume.setIp(params.getIp());
                duiCreditsConsume.setItemCode(params.getItemCode());
                duiCreditsConsume.setOrderNum(params.getOrderNum());
                duiCreditsConsume.setParams(params.getParams());

                duiCreditsConsume.setTimestamp(params.getTimestamp());
                duiCreditsConsume.setType(params.getType());
                duiCreditsConsume.setUid(params.getUid());
                duiCreditsConsume.setWaitAudit("0");
                bizId = IDGenerater.getNextId();//todo(); //开发者业务订单号，保证唯一不重复
                duiCreditsConsume.setBackBizId(bizId);
                duiCreditsConsume.setBackErrorMessage("");
                duiCreditsConsume.setBackStatus("ok");//ok或者fail
                duiCreditsConsume.setSign(request.getParameter("sign"));
                credits = credits-duiCreditsConsume.getCredits();//getCredits(); // getCredits()是根据开发者自身业务，获取的用户最新剩余积分数。
                success = true;
                duiCreditsConsume.setBackCredits(Integer.parseInt(credits+""));
                duiCreditsConsumeService.save(duiCreditsConsume);


                duiCreditsLog.setUserInfoId(params.getUid());
                duiCreditsLog.setCreditsType(2);
                duiCreditsLog.setCredits(params.getCredits());
                duiCreditsLog.setDescription(params.getDescription());
                duiCreditsLog.setCreateTime(DateUtil.format(params.getTimestamp()));
                duiCreditsLog.setOrderNum(params.getOrderNum());
                duiCreditsLogService.save(duiCreditsLog);
            }


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


    /**
     * 增加积分
     * @param request
     * @return
     */
    @RequestMapping("/addcredits")
    @ResponseBody
    public String addCredits(HttpServletRequest request) {
        CreditTool tool = new CreditTool(Config.get("tuia.appKey"), Config.get("tuia.appSecret"));
        boolean success = false;
        String errorMessage = "";
        String bizId =null;
        Long credits=0L;
        logger.info("-------》增加积分");
        DuiCreditsAdd duiCreditsAdd = new DuiCreditsAdd();
        DuiCreditsLog duiCreditsLog = new DuiCreditsLog();
        try {
           // CreditConsumeParams params = tool.parseCreditConsume(request);
            AddCreditsParams params = tool.parseaddCredits(request);
            logger.info("用户uid="+params.getUid());
            duiCreditsAdd.setAppKey(params.getAppKey());
            duiCreditsAdd.setCredits(params.getCredits());
            duiCreditsAdd.setDescription(params.getDescription());
            duiCreditsAdd.setIp(params.getIp());
            duiCreditsAdd.setOrderNum(params.getOrderNum());
            duiCreditsAdd.setTimestamp(params.getTimestamp());
            duiCreditsAdd.setType(params.getType());
            duiCreditsAdd.setUid(params.getUid());
            bizId = params.getOrderNum();
            duiCreditsAddService.save(duiCreditsAdd);

            List<Map> list = duiCreditsLogService.executeSql("SELECT (SELECT  sum(credits)   from dui_credits_log WHERE   user_info_id='"+params.getUid()+"' and credits_type=1 ) - (SELECT IFNULL((SELECT sum(credits)  from dui_credits_log WHERE   user_info_id='"+params.getUid()+"'  and credits_type=2),0)) as credits");
            if (StringUtil.isNotEmpty(list)  && list.size()>0) {
                credits = Long.parseLong(list.get(0).get("credits").toString());
                logger.error("查询到了积分："+credits);
            }else{
                credits = 0L;
                logger.error("注册的用户没有积分记录");
            }

            credits = credits+params.getCredits();
            duiCreditsLog.setUserInfoId(params.getUid());
            duiCreditsLog.setCreditsType(1);
            duiCreditsLog.setCredits(params.getCredits());
            duiCreditsLog.setDescription(params.getDescription());
            duiCreditsLog.setCreateTime(DateUtil.format(params.getTimestamp()));
            duiCreditsLog.setOrderNum(params.getOrderNum());
            success = true;
            duiCreditsLogService.save(duiCreditsLog);

        } catch (Exception e) {
            logger.info("-------增加积分出错了");
            success = false;
            errorMessage = e.getMessage();
            e.printStackTrace();
        }
        CreditConsumeResult ccr = new CreditConsumeResult(success);
        ccr.setBizId(bizId);
        ccr.setErrorMessage(errorMessage);
        ccr.setCredits(credits);
        logger.info("ccr:"+ccr.toString());
        return ccr.toString();//返回增加积分结果json信息
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
                logger.info("用户uid="+request.getParameter("uid"));
                logger.info("兑换成功");
                DuiCreditsNotify duiCreditsNotify = new DuiCreditsNotify();
                duiCreditsNotify.setId(IDGenerater.getNextId());
                duiCreditsNotify.setOrderNum(params.getOrderNum());
                duiCreditsNotify.setBizId(params.getBizId());
                duiCreditsNotify.setSuccess(1);
                duiCreditsNotify.setUid(request.getParameter("uid"));
                duiCreditsNotify.setErrorMessage("");
                duiCreditsNotify.setTimestamp(params.getTimestamp());
                duiCreditsNotify.setSign(request.getParameter("sign"));
                duiCreditsNotifyService.save(duiCreditsNotify);
                return  "ok";
            } else {
                //兑换成功
                logger.info("用户uid="+request.getParameter("uid"));
                logger.info("兑换失败");
                DuiCreditsNotify duiCreditsNotify = new DuiCreditsNotify();
                duiCreditsNotify.setId(IDGenerater.getNextId());
                duiCreditsNotify.setOrderNum(params.getOrderNum());
                duiCreditsNotify.setBizId(params.getBizId());
                duiCreditsNotify.setSuccess(0);
                duiCreditsNotify.setErrorMessage(URLDecoder.decode(params.getErrorMessage(),"utf-8"));
                duiCreditsNotify.setUid(request.getParameter("uid"));
                duiCreditsNotify.setTimestamp(params.getTimestamp());
                duiCreditsNotify.setSign(request.getParameter("sign"));
                duiCreditsNotifyService.save(duiCreditsNotify);

                logger.info("兑换失败，根据orderNum，对用户的金币进行返还，回滚操作");
                return  "no";
                //兑换失败，根据orderNum，对用户的金币进行返还，回滚操作
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return  "ok";
    }

}
