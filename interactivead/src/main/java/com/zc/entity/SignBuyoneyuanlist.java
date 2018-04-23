package com.zc.entity;

import java.util.Date;

/**
 * @Description:
 * @author solar
 * @date 2018年04月19日 16:43
 */
public class SignBuyoneyuanlist {
    private String id;

    //oneyuanbuyId
    private Integer oneyuanbuyId;

    //userId
    private Integer userId;

    //createTime
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getOneyuanbuyId() {
        return oneyuanbuyId;
    }

    public void setOneyuanbuyId(Integer oneyuanbuyId) {
        this.oneyuanbuyId = oneyuanbuyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



}