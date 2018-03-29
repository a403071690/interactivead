package com.zc.entity;

import java.util.Date;
/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
 */
public class AdvertiserQualifications {
    private String id;

    //advertiserId
    private String advertiserId;

    //资质名称
    private String name;

    //资质图片上传
    private String imgUrl;

    //状态：1待审核 2审核通过 3审核失败
    private Integer state;

    //stateMsg
    private String stateMsg;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



}