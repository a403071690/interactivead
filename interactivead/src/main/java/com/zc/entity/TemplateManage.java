package com.zc.entity;

import java.util.Date;
/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
 */
public class TemplateManage {
    private String id;

    //模板名称
    private String name;

    //模板地址
    private String url;

    //状态：1有效 2停止
    private Integer state;

    //createTime
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



}