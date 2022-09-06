package com.bitprogress.model.media.dto;

import com.bitprogress.model.media.MediaTypeEnum;

import java.util.Objects;

/**
 * @author wuwuwupx
 */
public class MediaQueryDTO {

    /**
     * 素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
     */
    private MediaTypeEnum type;

    /**
     * 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
     */
    private Integer offset;

    /**
     * 返回素材的数量，取值在1到20之间
     */
    private Integer count;

    public String getType() {
        return Objects.isNull(type) ? null : type.getName();
    }

    public void setType(MediaTypeEnum type) {
        this.type = type;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "MediaQueryDTO{" +
                "type=" + type +
                ", offset=" + offset +
                ", count=" + count +
                '}';
    }

}
