package com.bitprogress.model.menu;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

/**
 * @author wuwuwupx
 */
public class Button {

    /**
     * 菜单类型
     */
    private ButtonTypeEnum type;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 菜单KEY值，用于消息接口推送，不超过128字节
     */
    private String key;

    /**
     * 网页 链接，用户点击菜单可打开链接，不超过1024字节。 type为miniprogram时，不支持小程序的老版本客户端将打开本url。
     */
    private String url;

    /**
     *  媒体id
     */
    @JsonProperty("media_id")
    private String mediaId;

    /**
     * 小程序的appid
     */
    @JsonProperty("appid")
    private String appId;

    /**
     * 小程序的页面路径
     */
    @JsonProperty("pagepath")
    private String pagePath;

    /**
     * 子菜单
     */
    @JsonProperty("sub_button")
    private List<Button> subButton;

    public ButtonTypeEnum getType() {
        return type;
    }

    public void setType(ButtonTypeEnum type) {
        this.type = type;
    }

    @JsonProperty("type")
    public String getTypeValue() {
        return Objects.nonNull(type) ? type.getValue() : null;
    }

    @JsonProperty("type")
    public void setTypeByValue(String type) {
        this.type = ButtonTypeEnum.typeValueOf(type);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
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

    public List<Button> getSubButton() {
        return subButton;
    }

    public void setSubButton(List<Button> subButton) {
        this.subButton = subButton;
    }

    @Override
    public String toString() {
        return "Button{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", url='" + url + '\'' +
                ", mediaId='" + mediaId + '\'' +
                ", appId='" + appId + '\'' +
                ", pagePath='" + pagePath + '\'' +
                ", subButton=" + subButton +
                '}';
    }

}
