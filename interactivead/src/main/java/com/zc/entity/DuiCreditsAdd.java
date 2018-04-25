package com.zc.entity;

import java.util.Date;

/**
 * @Description:
 * @author solar
 * @date 2018年04月24日 12:01
 */
public class DuiCreditsAdd {
    private String id;

    //接口appKey，应用的唯一标识
    private String appKey;

    //用户标识，唯一且不可变
    private String uid;

    //本次兑换增加的积分
    private Long credits;

    //game（游戏），report（楼层签到），sign（弹层签到）,reSignAddCredits（补签到）,creditsGame（积分游戏）。所有类型不区分大小写
    private String type;

    //兑吧订单号(请记录到数据库中)
    private String orderNum;

    //1970-01-01开始的时间戳，毫秒为单位。
    private Date timestamp;

    //本次增加积分的描述(带中文，请用utf-8进行url解码)
    private String description;

    //用户ip，不保证获取到
    private String ip;

    //MD5签名，详见签名规则
    private String sign;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }



}