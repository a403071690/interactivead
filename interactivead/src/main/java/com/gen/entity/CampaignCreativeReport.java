package com.gen.entity;

import java.util.Date;
/**
 * @Description:
 * @author solar
 * @date 2018年04月08日 17:40
 */
public class CampaignCreativeReport {
    private String id;

    //广告主ID
    private String advertiserId;

    //reportDate
    private Date reportDate;

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

    public String getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(String advertiserId) {
        this.advertiserId = advertiserId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
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