package com.bitprogress.model.kfsession.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 *  聊天记录查询DTO
 */
public class MessageRecordQueryDTO {

    /**
     * 起始时间，unix时间戳，秒级时间戳
     */
    @JsonProperty("starttime")
    private Long starTime;

    /**
     * 结束时间，unix时间戳，秒级时间戳，每次查询时段不能超过24小时
     */
    @JsonProperty("endtime")
    private Long endTime;

    /**
     * 消息id顺序从小到大，从1开始
     */
    @JsonProperty("msgid")
    private Integer msgId;

    /**
     * 每次获取条数，最多10000条
     */
    private Integer number;

    public Long getStarTime() {
        return starTime;
    }

    public void setStarTime(Long starTime) {
        this.starTime = starTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "MessageRecordQueryDTO{" +
                "starTime=" + starTime +
                ", endTime=" + endTime +
                ", msgId=" + msgId +
                ", number=" + number +
                '}';
    }

}
