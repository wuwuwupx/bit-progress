package com.bitprogress.model.media.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx 
 */
public class ArticleDTO {

    /**
     * 图文消息的标题
     */
    private String title;

    /**
     * 图文消息的封面图片素材id
     */
    @JsonProperty("thumb_media_id")
    private String thumbMediaId;

    /**
     * 作者
     */
    private String author;

    /**
     * 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空
     */
    private String digest;

    /**
     * 是否显示封面，0为false，即不显示，1为true，即显示
     */
    @JsonProperty("show_cover_pic")
    private String showCoverPic;

    /**
     * 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS
     */
    private String content;

    /**
     * 图文消息的原文地址，即点击“阅读原文”后的URL
     */
    @JsonProperty("content_source_url")
    private String contentSourceUrl;

    /**
     * 封面图片地址
     */
    @JsonProperty("thumb_url")
    private String thumbUrl;

    /**
     * Uint32 是否打开评论，0不打开，1打开
     */
    @JsonProperty("need_open_comment")
    private Integer needOpenComment;

    /**
     * Uint32 是否粉丝才可评论，0所有人可评论，1粉丝才可评论
     */
    @JsonProperty("only_fans_can_comment")
    private Integer onlyFansCanComment;

    /**
     * 图文页的URL(高级群发不可用外链)
     */
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public String getShowCoverPic() {
        return showCoverPic;
    }

    public void setShowCoverPic(String showCoverPic) {
        this.showCoverPic = showCoverPic;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentSourceUrl() {
        return contentSourceUrl;
    }

    public void setContentSourceUrl(String contentSourceUrl) {
        this.contentSourceUrl = contentSourceUrl;
    }

    @Override
    public String toString() {
        return "ArticleDTO{" +
                "title='" + title + '\'' +
                ", thumbMediaId='" + thumbMediaId + '\'' +
                ", showCoverPic='" + showCoverPic + '\'' +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", author='" + author + '\'' +
                ", digest='" + digest + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", contentSourceUrl='" + contentSourceUrl + '\'' +
                '}';
    }

}
