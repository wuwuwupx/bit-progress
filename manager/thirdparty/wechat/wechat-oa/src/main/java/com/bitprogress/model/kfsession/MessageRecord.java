package com.bitprogress.model.kfsession;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author wuwuwupx
 */
public class MessageRecord {

    /**
     * 用户的openid
     */
    @JsonProperty("openid")
    private String openId;

    /**
     * 操作ID（会话状态）
     */
    @JsonProperty("opercode")
    private String operCode;

    /**
     * 聊天记录
     */
    @JsonProperty("text")
    private String text;

    /**
     * 操作时间，UNIX时间戳
     */
    private LocalDateTime time;

    /**
     * 客服账号
     */
    private String worker;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOperCode() {
        return operCode;
    }

    public void setOperCode(String operCode) {
        this.operCode = operCode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(Integer time) {
        this.time = LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.ofHours(8));
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

}
