package com.gen.entity;

import java.util.Date;
/**
 * @Description:
 * @author solar
 * @date 2018年04月08日 17:40
 */
public class TemplateRealtimeReport {
    private String id;

    //UUID
    private String uuid;

    //报表生成时间
    private Date reportTime;

    //业务时间段
    private Date hourTime;

    //媒体主id
    private String mediaOwnerId;

    //模板id
    private String templateId;

    //页面曝光数
    private Long pvCount;

    //用户访问数
    private Long uvCount;

    //奖券曝光数
    private Long ctImpCount;

    //奖券点击数
    private Long ctClickCount;

    //奖券有效点击数
    private Long ctValidClickCount;

    //广告主支付金额（分）
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

    public String getMediaOwnerId() {
        return mediaOwnerId;
    }

    public void setMediaOwnerId(String mediaOwnerId) {
        this.mediaOwnerId = mediaOwnerId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Long getPvCount() {
        return pvCount;
    }

    public void setPvCount(Long pvCount) {
        this.pvCount = pvCount;
    }

    public Long getUvCount() {
        return uvCount;
    }

    public void setUvCount(Long uvCount) {
        this.uvCount = uvCount;
    }

    public Long getCtImpCount() {
        return ctImpCount;
    }

    public void setCtImpCount(Long ctImpCount) {
        this.ctImpCount = ctImpCount;
    }

    public Long getCtClickCount() {
        return ctClickCount;
    }

    public void setCtClickCount(Long ctClickCount) {
        this.ctClickCount = ctClickCount;
    }

    public Long getCtValidClickCount() {
        return ctValidClickCount;
    }

    public void setCtValidClickCount(Long ctValidClickCount) {
        this.ctValidClickCount = ctValidClickCount;
    }

    public Long getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Long payMoney) {
        this.payMoney = payMoney;
    }



}