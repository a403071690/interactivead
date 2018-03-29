package com.zc.entity;

import java.util.Date;
/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
 */
public class AdvertiserCreative {
    private String id;

    //campaignId
    private String campaignId;

    //创意名称
    private String creativeName;

    //创意类型：1跳转URL
    private Integer type;

    //中奖名称，20个字以内，显示在中奖弹出层
    private String ctTitle;

    //创意图片URL，3:2比例，要求图片小于1MB。
    private String ctImgUrl;

    //目标URL，请以http或https开头
    private String ctTargetUrl;

    //创意信息
    private String ctNote;

    //是否删除1是 0否
    private Integer isDelete;

    //状态：1启动 2暂停
    private Integer state;

    //审核状态：1通过 2不通过
    private Integer checkState;

    //审核信息
    private String checkMsg;

    private String isPrize;//1中奖了，0没有没有

    public String getIsPrize() {
        return isPrize;
    }

    public void setIsPrize(String isPrize) {
        this.isPrize = isPrize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getCreativeName() {
        return creativeName;
    }

    public void setCreativeName(String creativeName) {
        this.creativeName = creativeName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCtTitle() {
        return ctTitle;
    }

    public void setCtTitle(String ctTitle) {
        this.ctTitle = ctTitle;
    }

    public String getCtImgUrl() {
        return ctImgUrl;
    }

    public void setCtImgUrl(String ctImgUrl) {
        this.ctImgUrl = ctImgUrl;
    }

    public String getCtTargetUrl() {
        return ctTargetUrl;
    }

    public void setCtTargetUrl(String ctTargetUrl) {
        this.ctTargetUrl = ctTargetUrl;
    }

    public String getCtNote() {
        return ctNote;
    }

    public void setCtNote(String ctNote) {
        this.ctNote = ctNote;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCheckState() {
        return checkState;
    }

    public void setCheckState(Integer checkState) {
        this.checkState = checkState;
    }

    public String getCheckMsg() {
        return checkMsg;
    }

    public void setCheckMsg(String checkMsg) {
        this.checkMsg = checkMsg;
    }



}