package com.zc.entity;

import java.util.Date;

/**
 * @Description:
 * @author solar
 * @date 2018年04月23日 15:40
 */
public class DuiCreditsConsume {
    private String id;

    //用户唯一性标识，唯一且不可变
    private String uid;

    //本次兑换扣除的积分
    private Long credits;

    //自有商品商品编码(非必须字段)
    private String itemCode;

    //接口appKey，应用的唯一标识
    private String appKey;

    //1970-01-01开始的时间戳，毫秒为单位。
    private Date timestamp;

    //本次积分消耗的描述(带中文，请用utf-8进行url解码)
    private String description;

    //兑吧订单号(请记录到数据库中)
    private String orderNum;

    //兑换类型：alipay(支付宝), qb(Q币), coupon(优惠券), object(实物), phonebill(话费), phoneflow(流量), virtual(虚拟商品), littleGame（小游戏）,singleLottery(单品抽奖)，hdtoolLottery(活动抽奖),htool(新活动抽奖),manualLottery(手动开奖),ngameLottery(新游戏),questionLottery(答题),quizzLottery(测试题),guessLottery(竞猜) 所有类型不区分大小写
    private String type;

    //兑换商品的市场价值，单位是分，请自行转换单位
    private Integer facePrice;

    //此次兑换实际扣除开发者账户费用，单位为分
    private Integer actualPrice;

    //用户ip，不保证获取到
    private String ip;

    //是否需要审核(如需在自身系统进行审核处理，请记录下此信息)
    private String waitAudit;

    //详情参数，不同的类型，返回不同的内容，中间用英文冒号分隔。(支付宝类型带中文，请用utf-8进行解码) 实物商品：返回收货信息(姓名:手机号:省份:城市:区域:详细地址)、支付宝：返回账号信息(支付宝账号:实名)、话费：返回手机号、QB：返回QQ号
    private String params;

    //MD5签名
    private String sign;

    //扣积分结果状态，回复ok或者fail （不要使用0和1）
    private String backStatus;

    //出错原因
    private String backErrorMessage;

    //开发者的订单号(兑吧会判断该订单号的唯一性，注意区分测试和正式对接时的订单号，以免重复)
    private String backBizId;

    //用户积分余额
    private Integer backCredits;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getCredits() {
        return credits;
    }

    public void setCredits(Long credits) {
        this.credits = credits;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFacePrice() {
        return facePrice;
    }

    public void setFacePrice(Integer facePrice) {
        this.facePrice = facePrice;
    }

    public Integer getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Integer actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getWaitAudit() {
        return waitAudit;
    }

    public void setWaitAudit(String waitAudit) {
        this.waitAudit = waitAudit;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBackStatus() {
        return backStatus;
    }

    public void setBackStatus(String backStatus) {
        this.backStatus = backStatus;
    }

    public String getBackErrorMessage() {
        return backErrorMessage;
    }

    public void setBackErrorMessage(String backErrorMessage) {
        this.backErrorMessage = backErrorMessage;
    }

    public String getBackBizId() {
        return backBizId;
    }

    public void setBackBizId(String backBizId) {
        this.backBizId = backBizId;
    }

    public Integer getBackCredits() {
        return backCredits;
    }

    public void setBackCredits(Integer backCredits) {
        this.backCredits = backCredits;
    }



}