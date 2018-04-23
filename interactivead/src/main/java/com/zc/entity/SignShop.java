package com.zc.entity;

/**
 * @Description:
 * @author solar
 * @date 2018年04月19日 16:43
 */
public class SignShop {
    private String id;

    //标题
    private String title;

    //原价
    private String originalPrice;

    //优惠
    private String couponPrice;

    //最终价格
    private String finalPrice;

    //卖家小图标
    private String vendorSmallIcon;

    //主图
    private String mainImage;

    //类别：数码，美妆，百货，食品，内衣，母婴，其他，鞋包，车品，女装，保健品，图书乐器，男装，户外运动
    private Integer type;

    //是否精选:1是，0不是
    private Integer isHot;


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

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(String couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getVendorSmallIcon() {
        return vendorSmallIcon;
    }

    public void setVendorSmallIcon(String vendorSmallIcon) {
        this.vendorSmallIcon = vendorSmallIcon;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
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