package com.zc.entity;

import java.util.Date;

/**
 * @Description:
 * @author solar
 * @date 2018年04月23日 15:40
 */
public class DuiCreditsNotify {
    private String id;

    //接口appKey，应用的唯一标识码
    private String appKey;

    //1970-01-01开始的时间戳，毫秒。
    private Date timestamp;

    //用户唯一标识，唯一且不可变
    private String uid;

    //兑换是否成功，状态是true和false
    private Integer success;

    //出错原因(带中文，请用utf-8进行解码)
    private String errorMessage;

    //兑吧订单号
    private String orderNum;

    //开发者的订单号
    private String bizId;

    //签名
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }



}