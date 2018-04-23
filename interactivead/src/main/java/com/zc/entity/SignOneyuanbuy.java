package com.zc.entity;

import java.util.Date;

/**
 * @Description:
 * @author solar
 * @date 2018年04月19日 16:43
 */
public class SignOneyuanbuy {
    private String id;

    //标题
    private String title;

    //需要付款
    private Integer needsGold;

    //原价
    private String price;

    //图片
    private String indexImg;

    //加载图
    private String landingPage;

    //是否热门
    private Integer isHotSell;

    //添加时间
    private Date crateTime;

    //类别
    private Integer type;

    //运费
    private Integer freight;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNeedsGold() {
        return needsGold;
    }

    public void setNeedsGold(Integer needsGold) {
        this.needsGold = needsGold;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIndexImg() {
        return indexImg;
    }

    public void setIndexImg(String indexImg) {
        this.indexImg = indexImg;
    }

    public String getLandingPage() {
        return landingPage;
    }

    public void setLandingPage(String landingPage) {
        this.landingPage = landingPage;
    }

    public Integer getIsHotSell() {
        return isHotSell;
    }

    public void setIsHotSell(Integer isHotSell) {
        this.isHotSell = isHotSell;
    }

    public Date getCrateTime() {
        return crateTime;
    }

    public void setCrateTime(Date crateTime) {
        this.crateTime = crateTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }



}