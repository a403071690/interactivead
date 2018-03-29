package com.gen.entity;

import java.util.Date;
/**
 * @Description:
 * @author solar
 * @date 2018年03月29日 20:17
 */
public class AdvertiserCampaign {
    private String id;

    //广告主id
    private String advertiserId;

    //活动名称
    private String campaignName;

    //beginTime
    private Date beginTime;

    //endTime
    private Date endTime;

    //活动状态：1开启 2暂停
    private Integer state;

    //活动每天预算（分）
    private Long dayPrice;

    //竞价方式：1CPM 2CPC 3CPA 4CPS
    private Integer bidType;

    //竞价出价单位分，0.1元起
    private Long bidPrice;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //是否删除：1是 0否
    private Integer isDelete;


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

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(Long dayPrice) {
        this.dayPrice = dayPrice;
    }

    public Integer getBidType() {
        return bidType;
    }

    public void setBidType(Integer bidType) {
        this.bidType = bidType;
    }

    public Long getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Long bidPrice) {
        this.bidPrice = bidPrice;
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

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }



}