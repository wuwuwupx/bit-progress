package com.bitprogress.model.media.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 */
public class UpdateArticleDTO {

    @JsonProperty("media_id")
    private String mediaId;

    private String index;

    private ArticleDTO articles;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public ArticleDTO getArticles() {
        return articles;
    }

    public void setArticles(ArticleDTO articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "UpdateArticleDTO{" +
                "mediaId='" + mediaId + '\'' +
                ", index='" + index + '\'' +
                ", articles=" + articles +
                '}';
    }

}
