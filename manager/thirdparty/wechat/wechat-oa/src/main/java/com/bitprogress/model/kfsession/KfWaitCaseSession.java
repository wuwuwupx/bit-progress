package com.bitprogress.model.kfsession;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 *  未接入会话
 */
public class KfWaitCaseSession {

    /**
     * 最后一条消息的时间
     */
    @JsonProperty("latest_time")
    private String latestTime;

    /**
     * 用户openid
     */
    @JsonProperty("openid")
    private String openId;

    public String getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

}
