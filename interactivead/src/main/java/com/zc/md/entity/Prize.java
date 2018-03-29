package com.zc.md.entity;

import java.util.Date;

/**
 * 请求创意实体类
 * @author dlk
 */
public class Prize {

    private String img_url;//中奖的创意图片地址
    private String  title;//中奖名称
    private String  clk_url;//点击跳转地址
    private Date time;//中奖时间 YYYYMMDD HH:mm:ss
    private String  state;//奖品状态，使用，未使用
    private Date  end_time;//奖品结束日期 YYYYMMDD HH:mm:ss



    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClk_url() {
        return clk_url;
    }

    public void setClk_url(String clk_url) {
        this.clk_url = clk_url;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }
}
