package com.gen.entity;

import java.util.Date;
/**
 * @Description:
 * @author solar
 * @date 2018年03月29日 20:17
 */
public class UserPrizeLog {
    private String id;

    //templateId
    private String templateId;

    //创意ID
    private String mediaOwnerId;

    //channelId
    private String channelId;

    //userCookieId
    private String userCookieId;

    //userOpenId
    private String userOpenId;

    //userFingerId
    private String userFingerId;

    //type
    private String type;

    //creativeId
    private String creativeId;

    //prizeId
    private String prizeId;

    //price
    private String price;

    //duration
    private String duration;

    //createTime
    private String createTime;

    //广告主ID
    private String advertiserId;


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

    public String getMediaOwnerId() {
        return mediaOwnerId;
    }

    public void setMediaOwnerId(String mediaOwnerId) {
        this.mediaOwnerId = mediaOwnerId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getUserCookieId() {
        return userCookieId;
    }

    public void setUserCookieId(String userCookieId) {
        this.userCookieId = userCookieId;
    }

    public String getUserOpenId() {
        return userOpenId;
    }

    public void setUserOpenId(String userOpenId) {
        this.userOpenId = userOpenId;
    }

    public String getUserFingerId() {
        return userFingerId;
    }

    public void setUserFingerId(String userFingerId) {
        this.userFingerId = userFingerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(String creativeId) {
        this.creativeId = creativeId;
    }

    public String getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(String advertiserId) {
        this.advertiserId = advertiserId;
    }



}