package com.gen.entity;

import java.util.Date;
/**
 * @Description:
 * @author solar
 * @date 2018年03月29日 20:17
 */
public class MediaOwnerRealtimeReport {
    private String id;

    //uuid
    private String uuid;

    //mediaOwnerId
    private String mediaOwnerId;

    //reportTime
    private Date reportTime;

    //statementTime
    private Date statementTime;

    //点击量
    private Long clickCount;

    //有效点击量
    private Long validClickCount;

    //支付金额（分）
    private Long payMoney;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMediaOwnerId() {
        return mediaOwnerId;
    }

    public void setMediaOwnerId(String mediaOwnerId) {
        this.mediaOwnerId = mediaOwnerId;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Date getStatementTime() {
        return statementTime;
    }

    public void setStatementTime(Date statementTime) {
        this.statementTime = statementTime;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public Long getValidClickCount() {
        return validClickCount;
    }

    public void setValidClickCount(Long validClickCount) {
        this.validClickCount = validClickCount;
    }

    public Long getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Long payMoney) {
        this.payMoney = payMoney;
    }



}