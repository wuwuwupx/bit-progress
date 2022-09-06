package com.bitprogress.model.media;

import com.bitprogress.util.StringUtils;

/**
 * @author wuwuwupx
 *  素材类型
 */
public enum MediaTypeEnum {

    /**
     * 图片，10M，支持PNG\JPEG\JPG\GIF格式
     */
    IMAGE("image"),

    /**
     * 语音，2M，播放长度不超过60s，支持AMR\MP3格式
     */
    VOICE("voice"),

    /**
     * 视频，10MB，支持MP4格式
     */
    VIDEO("video"),

    /**
     * 缩略图，64KB，支持JPG格式
     */
    THUMB("thumb"),

    ;

    private String name;

    public String getName() {
        return name;
    }

    MediaTypeEnum(String name) {
        this.name = name;
    }

    public static MediaTypeEnum nameOf(String name) {
        for (MediaTypeEnum mediaType : values()) {
            if (StringUtils.equals(mediaType.name, name)) {
                return mediaType;
            }
        }
        return null;
    }

}
