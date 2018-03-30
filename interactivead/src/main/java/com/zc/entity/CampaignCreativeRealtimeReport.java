package com.zc.entity;

import java.util.Date;

/**
 * @Description:
 * @author solar
 * @date 2018年03月29日 20:17
 */
public class CampaignCreativeRealtimeReport {
    private String id;

    //UUID
    private String uuid;

    //报表生成时间
    private Date reportTime;

    //业务时间段
    private Date hourTime;

    //广告主ID
    private String advertiserId;

    //活动ID
    private String campaignId;

    //创意ID
    private String creativeId;

    //曝光量
    private Long impCount;

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

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Date getHourTime() {
        return hourTime;
    }

    public void setHourTime(Date hourTime) {
        this.hourTime = hourTime;
    }

    public String getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(String advertiserId) {
        this.advertiserId = advertiserId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(String creativeId) {
        this.creativeId = creativeId;
    }

    public Long getImpCount() {
        return impCount;
    }

    public void setImpCount(Long impCount) {
        this.impCount = impCount;
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