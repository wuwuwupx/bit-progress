package com.bitprogress.model.kf.message;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 * 
 */
public class MiniProgramPage implements KfBaseMessage {

    /**
     * 图文消息/视频消息/音乐消息/小程序卡片的标题
     */
    private String title;

    /**
     * 小程序的appid，要求小程序的appid需要与公众号有关联关系
     */
    @JsonProperty("appid")
    private String appId;

    /**
     * 小程序的页面路径，跟app.json对齐，支持参数，比如pages/index/index?foo=bar
     */
    @JsonProperty("pagepath")
    private String pagePath;

    /**
     * 缩略图/小程序卡片图片的媒体ID，小程序卡片图片建议大小为520*416
     */
    @JsonProperty("thumb_media_id")
    private String thumbMediaId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

}
