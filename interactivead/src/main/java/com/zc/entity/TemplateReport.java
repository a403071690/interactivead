package com.zc.entity;

import java.util.Date;
/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
 */
public class TemplateReport {
    private String id;

    //templateId
    private String templateId;

    //reportDate
    private Date reportDate;

    //mediaOwnerId
    private String mediaOwnerId;

    //页面曝光数
    private Long pvCount;

    //uvCount
    private Long uvCount;

    //奖券曝光数
    private Long ctImpCount;

    //点击量
    private Long ctClickCount;

    //有效点击量
    private Long ctValidClickCount;

    //广告主支付金额（分）
    private Long payMoney;

    private String mediaName;

    private  String templateName;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getMediaOwnerId() {
        return mediaOwnerId;
    }

    public void setMediaOwnerId(String mediaOwnerId) {
        this.mediaOwnerId = mediaOwnerId;
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