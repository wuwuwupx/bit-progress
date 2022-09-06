package com.bitprogress.model.kf.message;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 *  视频消息
 */
public class Video implements KfBaseMessage {

    /**
     * 图文消息/视频消息/音乐消息/小程序卡片的标题
     */
    private String title;

    /**
     * 图文消息/视频消息/音乐消息的描述
     */
    private String description;

    /**
     * 媒体ID
     */
    @JsonProperty("media_id")
    private String mediaId;

    /**
     * 缩略图/小程序卡片图片的媒体ID
     */
    private String thumbMediaId;


    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
