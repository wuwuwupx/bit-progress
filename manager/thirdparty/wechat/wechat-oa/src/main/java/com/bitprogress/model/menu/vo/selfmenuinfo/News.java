package com.bitprogress.model.menu.vo.selfmenuinfo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 */
public class News {

    /**
     * 标题
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 摘要
     */
    private String digest;

    /**
     * 是否显示封面，0为不显示，1为显示
     */
    @JsonProperty("show_cover")
    private Integer showCover;

    /**
     * 封面图片的URL
     */
    @JsonProperty("cover_url")
    private String coverUrl;

    /**
     * 正文的URL
     */
    @JsonProperty("content_url")
    private String contentUrl;

    /**
     * 原文的URL，若置空则无查看原文入口
     */
    @JsonProperty("source_url")
    private String sourceUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getShowCover() {
        return showCover;
    }

    public void setShowCover(Integer showCover) {
        this.showCover = showCover;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", digest='" + digest + '\'' +
                ", showCover=" + showCover +
                ", coverUrl='" + coverUrl + '\'' +
                ", contentUrl='" + contentUrl + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                '}';
    }

}
