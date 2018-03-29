package com.zc.entity;

import java.util.Date;
/**
 * @Description:
 * @author solar
 * @date 2018年03月20日 22:41
 */
public class MediaBankCard {
    private String id;

    //mediaOwnerId
    private String mediaOwnerId;

    //是否删除 1是 0否
    private Integer isDelete;

    //银行名称
    private String bankName;

    //开户行地址具体支行名称
    private String bankAddress;

    //账户名/开户人姓名
    private String accountName;

    //账户号码、银行卡号
    private String accountNumber;

    //创建时间
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMediaOwnerId() {
        return mediaOwnerId;
    }

    public void setMediaOwnerId(String mediaOwnerId) {
        this.mediaOwnerId = mediaOwnerId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



}