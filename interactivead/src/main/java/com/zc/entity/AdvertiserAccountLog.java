package com.zc.entity;

import java.util.Date;
/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
 */
public class AdvertiserAccountLog {
    private String id;

    //advertiserId
    private String advertiserId;

    //账户数据日期，比如扣款是扣20180316，create_time为20180317
    private Date accountDataTime;

    //1入账 2出账
    private Integer accountType;

    //金额（分）
    private Long price;

    //备注
    private String note;

    //createTime
    private Date createTime;


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

    public Date getAccountDataTime() {
        return accountDataTime;
    }

    public void setAccountDataTime(Date accountDataTime) {
        this.accountDataTime = accountDataTime;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



}