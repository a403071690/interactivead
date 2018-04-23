package com.zc.entity;

/**
 * @Description:
 * @author solar
 * @date 2018年04月19日 16:43
 */
public class SignGolddetail {
    private String id;

    //类型：支出还是收入
    private Integer type;

    //原因
    private String reason;

    //金额
    private String money;

    //createTime
    private String createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }



}