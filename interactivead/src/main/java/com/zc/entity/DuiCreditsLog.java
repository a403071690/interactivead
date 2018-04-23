package com.zc.entity;

/**
 * @Description:
 * @author solar
 * @date 2018年04月23日 15:40
 */
public class DuiCreditsLog {
    private String id;

    //userInfoId
    private String userInfoId;

    //创建时间
    private String createTime;

    //自己系统订单号
    private String orderNum;

    //积分类型：1收入 2支出
    private Integer creditsType;

    //积分
    private Long credits;

    //积分描述
    private String description;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(String userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getCreditsType() {
        return creditsType;
    }

    public void setCreditsType(Integer creditsType) {
        this.creditsType = creditsType;
    }

    public Long getCredits() {
        return credits;
    }

    public void setCredits(Long credits) {
        this.credits = credits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}