package com.bitprogress.model.media.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.bitprogress.model.WechatResult;

import java.io.File;

/**
 * @author wuwuwupx
 */
public class MediaVO extends WechatResult {

    private File media;

    @JsonProperty("video_url")
    private String videoUrl;

    public File getMedia() {
        return media;
    }

    public void setMedia(File media) {
        this.media = media;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return "MediaVO{" +
                "media=" + media +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }

}
