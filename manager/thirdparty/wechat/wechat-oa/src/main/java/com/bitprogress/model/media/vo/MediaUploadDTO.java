package com.bitprogress.model.media.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.bitprogress.model.media.MediaTypeEnum;

import java.io.File;

/**
 * @author wuwuwupx
 */
public class MediaUploadDTO {

    @JsonIgnore
    private MediaTypeEnum mediaType;

    @JsonIgnore
    private File media;

    private String title;

    private String introduction;

    public MediaTypeEnum getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaTypeEnum mediaType) {
        this.mediaType = mediaType;
    }

    public File getMedia() {
        return media;
    }

    public void setMedia(File media) {
        this.media = media;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return "MediaUploadDTO{" +
                "mediaType=" + mediaType +
                ", media=" + media +
                ", title='" + title + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }

}
