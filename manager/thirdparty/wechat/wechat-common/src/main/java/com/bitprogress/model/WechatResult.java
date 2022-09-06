package com.bitprogress.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 */
public class WechatResult {

    /**
     * 状态码
     */
    @JsonProperty("errcode")
    private String errCode;

    /**
     * 异常信息
     */
    @JsonProperty("errmsg")
    private String errMsg;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
