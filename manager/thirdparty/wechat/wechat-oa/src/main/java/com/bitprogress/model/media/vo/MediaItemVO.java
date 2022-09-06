package com.bitprogress.model.media.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 */
public class MediaItemVO {

    @JsonProperty("media_id")
    private String mediaId;

    /**
     * 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS
     */
    private MediaContentVO content;

    /**
     * 文件名称
     */
    private String name;

    @JsonProperty("update_time")
    private String updateTime;

    /**
     * 图文页的URL，或者，当获取的列表是图片素材列表时，该字段是图片的URL
     */
    private String url;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public MediaContentVO getContent() {
        return content;
    }

    public void setContent(MediaContentVO content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "MediaItemVO{" +
                "mediaId='" + mediaId + '\'' +
                ", content=" + content +
                ", name='" + name + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
