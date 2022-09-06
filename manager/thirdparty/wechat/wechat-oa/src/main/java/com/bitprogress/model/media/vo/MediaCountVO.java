package com.bitprogress.model.media.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.bitprogress.model.WechatResult;

/**
 * @author wuwuwupx
 */
public class MediaCountVO extends WechatResult {

    /**
     * 语音总数量
     */
    @JsonProperty("voice_count")
    private Integer voiceCount;

    /**
     * 视频总数量
     */
    @JsonProperty("video_count")
    private Integer videoCount;

    /**
     * 图片总数量
     */
    @JsonProperty("image_count")
    private Integer imageCount;

    /**
     * 图文总数量
     */
    @JsonProperty("news_count")
    private Integer newsCount;

    public Integer getVoiceCount() {
        return voiceCount;
    }

    public void setVoiceCount(Integer voiceCount) {
        this.voiceCount = voiceCount;
    }

    public Integer getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }

    public Integer getImageCount() {
        return imageCount;
    }

    public void setImageCount(Integer imageCount) {
        this.imageCount = imageCount;
    }

    public Integer getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(Integer newsCount) {
        this.newsCount = newsCount;
    }

    @Override
    public String toString() {
        return "MediaCountVO{" +
                "voiceCount=" + voiceCount +
                ", videoCount=" + videoCount +
                ", imageCount=" + imageCount +
                ", newsCount=" + newsCount +
                '}';
    }

}
