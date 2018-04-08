package com.gen.entity;

import java.util.Date;
/**
 * @Description:
 * @author solar
 * @date 2018年04月08日 17:40
 */
public class MediaOwnerInfo {
    private String id;

    //登录名称，使用邮箱注册
    private String loginName;

    //登录密码，使用不可逆加密。
    private String password;

    //手机号，需要短信认证，才可注册。
    private String phone;

    //类型：1.媒体主，2.广告主
    private Integer type;

    //状态：1待审核 2审核通过 3审核未通过 4冻结
    private Integer state;

    //状态信息，用于审核不通过原因，冻结说明等。
    private String stateMsg;

    //公司名称
    private String company;

    //公司营业执照URL
    private String companyLicenseImg;

    //createTime
    private Date createTime;

    //updateTime
    private Date updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateMsg() {
        return stateMsg;
    }

    public void setStateMsg(String stateMsg) {
        this.stateMsg = stateMsg;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyLicenseImg() {
        return companyLicenseImg;
    }

    public void setCompanyLicenseImg(String companyLicenseImg) {
        this.companyLicenseImg = companyLicenseImg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }



}