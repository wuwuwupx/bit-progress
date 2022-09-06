package com.bitprogress.model.kf.message;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 *  素材消息  图片消息/语音消息/图文消息（点击跳转到图文消息页面）
 */
public class Media implements KfBaseMessage {

    /**
     * 媒体ID
     */
    @JsonProperty("media_id")
    private String mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

}
