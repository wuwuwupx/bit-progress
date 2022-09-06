package com.bitprogress.model.menu.vo.selfmenuinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.bitprogress.model.menu.ButtonTypeEnum;

import java.util.List;

/**
 * @author wuwuwupx
 *  对于不同的菜单类型，value的值意义不同
 * 官网上设置的自定义菜单：
 * Text:保存文字到value；
 * Img、voice：保存mediaID到value；
 * Video：保存视频下载链接到value；
 * News：保存图文消息到news_info，同时保存mediaID到value；
 * View：保存链接到url。
 * 使用API设置的自定义菜单： click、scancode_push、scancode_waitmsg、pic_sysphoto、pic_photo_or_album、 pic_weixin、location_select：保存值到key；
 * view：保存链接到url
 */
public class SelfButton {

    /**
     * 名称
     */
    private String name;

    private ButtonTypeEnum type;

    /**
     * click、scancode_push、scancode_waitmsg、pic_sysphoto、pic_photo_or_album、	pic_weixin、location_select
     */
    private String key;

    /**
     * View：保存链接到url
     */
    private String url;

    /**
     * 保存文字到value； Img、voice：保存mediaID到value； Video：保存视频下载链接到value； News：保存图文消息到news_info，同时保存mediaID到value；
     */
    private String value;

    /**
     *
     */
    @JsonProperty("news_info")
    private NewsInfo newsInfo;

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
     *
     */
    private List<SelfButton> list;

    @JsonProperty("sub_button")
    private SelfButton subButton;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ButtonTypeEnum getType() {
        return type;
    }

    public void setType(ButtonTypeEnum type) {
        this.type = type;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public NewsInfo getNewsInfo() {
        return newsInfo;
    }

    public void setNewsInfo(NewsInfo newsInfo) {
        this.newsInfo = newsInfo;
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

    public List<SelfButton> getList() {
        return list;
    }

    public void setList(List<SelfButton> list) {
        this.list = list;
    }

    public SelfButton getSubButton() {
        return subButton;
    }

    public void setSubButton(SelfButton subButton) {
        this.subButton = subButton;
    }

    @Override
    public String toString() {
        return "SelfButton{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", key='" + key + '\'' +
                ", url='" + url + '\'' +
                ", value='" + value + '\'' +
                ", newsInfo=" + newsInfo +
                ", appId='" + appId + '\'' +
                ", pagePath='" + pagePath + '\'' +
                ", list=" + list +
                ", subButton=" + subButton +
                '}';
    }

}
