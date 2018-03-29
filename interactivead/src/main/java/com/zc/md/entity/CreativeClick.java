package com.zc.md.entity;

/**
 * 创意点击日志实体类
 * @author dlk
 */
public class CreativeClick {

    private String  id;//	主键，32位UUID
    private String  template_id;//	模板ID
    private String  media_owner_id;//	媒体主ID
    private String  channel_id;//	渠道ID
    private String  user_cookie_id;//	用户ID，基于Cookie
    private String  user_open_id;//	微信用户唯一ID
    private String  user_finger_id;//	用户指纹ID，基于JS
    private String  type;//	类型：1创意 2奖品
    private String  creative_id;//	创意ID
    private String  prize_id;//	奖品ID
    private String  price;//	点击价格（整数，分）
    private String  duration;//	运行时间，毫秒数
    private String  create_time;//	时间
    private String advertiser_id;//广告主id
    private String campaign_id;//活动id
    private String isValid;//是否有效

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getMedia_owner_id() {
        return media_owner_id;
    }

    public void setMedia_owner_id(String media_owner_id) {
        this.media_owner_id = media_owner_id;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getUser_cookie_id() {
        return user_cookie_id;
    }

    public void setUser_cookie_id(String user_cookie_id) {
        this.user_cookie_id = user_cookie_id;
    }

    public String getUser_open_id() {
        return user_open_id;
    }

    public void setUser_open_id(String user_open_id) {
        this.user_open_id = user_open_id;
    }

    public String getUser_finger_id() {
        return user_finger_id;
    }

    public void setUser_finger_id(String user_finger_id) {
        this.user_finger_id = user_finger_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreative_id() {
        return creative_id;
    }

    public void setCreative_id(String creative_id) {
        this.creative_id = creative_id;
    }

    public String getPrize_id() {
        return prize_id;
    }

    public void setPrize_id(String prize_id) {
        this.prize_id = prize_id;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAdvertiser_id() {
        return advertiser_id;
    }

    public void setAdvertiser_id(String advertiser_id) {
        this.advertiser_id = advertiser_id;
    }

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }
}
