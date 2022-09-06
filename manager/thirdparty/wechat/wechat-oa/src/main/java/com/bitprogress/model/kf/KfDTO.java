package com.bitprogress.model.kf;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 *  客服DTO
 */
public class KfDTO {

    /**
     * 完整客服账号，格式为：账号前缀@公众号微信号，账号前缀最多10个字符，必须是英文或者数字字符。如果没有公众号微信号，请前往微信公众平台设置
     */
    @JsonProperty("kf_account")
    private String kfAccount;

    /**
     * 客服昵称
     */
    @JsonProperty("nickname")
    private String nickname;

    /**
     * 客服密码(md5摘要后的密码)
     */
    @JsonProperty("password")
    private String password;

    public String getKfAccount() {
        return kfAccount;
    }

    public void setKfAccount(String kfAccount) {
        this.kfAccount = kfAccount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{" +
                "kfAccount='" + kfAccount + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
