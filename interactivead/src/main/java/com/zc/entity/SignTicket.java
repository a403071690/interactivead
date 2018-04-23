package com.zc.entity;

/**
 * @Description:
 * @author solar
 * @date 2018年04月19日 16:43
 */
public class SignTicket {
    private String id;

    //商品图片地址
    private String logo;

    //短标题
    private String title;

    //长标题
    private String subTitle;

    //描述
    private String limitation;

    //兑换商品需要红包金额
    private Integer needsGold;

    //是否是新的；0新的。1不是新的
    private Integer isNew;

    //券类型：1
    private Integer type;

    //是否热门
    private Integer isHot;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getLimitation() {
        return limitation;
    }

    public void setLimitation(String limitation) {
        this.limitation = limitation;
    }

    public Integer getNeedsGold() {
        return needsGold;
    }

    public void setNeedsGold(Integer needsGold) {
        this.needsGold = needsGold;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }



}