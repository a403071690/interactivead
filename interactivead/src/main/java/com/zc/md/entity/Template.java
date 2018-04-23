package com.zc.md.entity;

/*
模板实体类
@author dlk
 */
public class Template {

    private  String id;
    private  String template_id;
    private  String media_owner_id;
    private  String channel_id;
    private  String user_cookie_id;
    private  String user_open_id;
    private  String user_finger_id;
    private  String duration;//执行时长
    private  String create_time;
    private  Long current_time_millis;

    public Long getCurrent_time_millis() {
        return current_time_millis;
    }

    public void setCurrent_time_millis(Long current_time_millis) {
        this.current_time_millis = current_time_millis;
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


}
