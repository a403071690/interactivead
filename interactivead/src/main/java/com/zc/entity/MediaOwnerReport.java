package com.zc.entity;

import java.util.Date;
/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
 */
public class MediaOwnerReport {
    private String id;

    //mediaOwnerId
    private String mediaOwnerId;

    //reportDate
    private Date reportDate;

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

    public String getMediaOwnerId() {
        return mediaOwnerId;
    }

    public void setMediaOwnerId(String mediaOwnerId) {
        this.mediaOwnerId = mediaOwnerId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
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