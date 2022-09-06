package com.bitprogress.model.kfsession;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.bitprogress.model.WechatResult;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author wuwuwupx
 *  客服会话
 */
public class KfSession extends WechatResult {

    /**
     * 客服账号
     * 此接口获取一个客户的会话，如果不存在，则kf_account为空
     */
    @JsonProperty("kf_account")
    private String kfAccount;

    /**
     * 用户openId
     */
    @JsonProperty("openid")
    private String openId;

    private LocalDateTime createTime;

    public String getKfAccount() {
        return kfAccount;
    }

    public void setKfAccount(String kfAccount) {
        this.kfAccount = kfAccount;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    @JsonProperty("createtime")
    public void setCreateTime(Integer createTime) {
        this.createTime = LocalDateTime.ofEpochSecond(createTime, 0, ZoneOffset.ofHours(8));
    }

}
