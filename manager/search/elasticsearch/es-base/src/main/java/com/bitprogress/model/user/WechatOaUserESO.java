package com.bitprogress.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wuwuwupx
 */
@Document(indexName = "wechatoauser")
public class WechatOaUserESO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long wechatOaUserId;

    private Long userId;

    private Long wechatUserId;

    private Long appId;

    private String appSign;

    private String openId;

    private String unionIdId;

    private String phone;

    private String nickname;

    private String avatar;

    private Integer gender;

    private String location;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Boolean deleted;

    public Long getWechatOaUserId() {
        return wechatOaUserId;
    }

    public void setWechatOaUserId(Long wechatOaUserId) {
        this.wechatOaUserId = wechatOaUserId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(Long wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getAppSign() {
        return appSign;
    }

    public void setAppSign(String appSign) {
        this.appSign = appSign;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionIdId() {
        return unionIdId;
    }

    public void setUnionIdId(String unionIdId) {
        this.unionIdId = unionIdId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "WechatOaUser{" +
                "wechatOaUserId=" + wechatOaUserId +
                ", userId=" + userId +
                ", wechatUserId=" + wechatUserId +
                ", appId=" + appId +
                ", appSign='" + appSign + '\'' +
                ", openId='" + openId + '\'' +
                ", unionIdId='" + unionIdId + '\'' +
                ", phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", gender=" + gender +
                ", location='" + location + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                '}';
    }

}
