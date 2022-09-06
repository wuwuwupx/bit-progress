package com.bitprogress.model.menu;

import java.io.Serializable;

/**
 * @author wuwuwupx
 */
public enum ButtonTypeEnum implements Serializable {

    /**
     * 点击类型菜单
     */
    CLICK("click", "点击类型"),

    /**
     *
     */
    VIEW("view", "网页类型"),

    /**
     *
     */
    SCANCODE_PUSH("scancode_push", "扫码推事件类型"),

    /**
     *
     */
    SCANCODE_WAIT_MSG("scancode_waitmsg", "扫码推事件且弹出“消息接收中”提示"),

    /**
     *
     */
    PIC_SYS_PHOTO("pic_sysphoto", "弹出系统拍照发图"),

    /**
     *
     */
    PIC_PHOTO_OR_ALBUM("pic_photo_or_album", "弹出拍照或者相册发图用户"),

    /**
     *
     */
    PIC_WEIXIN("pic_weixin", "弹出微信相册发图器"),

    /**
     *
     */
    LOCATION_SELECT("location_select", "弹出地理位置选择器用户点击按钮后"),

    /**
     *
     */
    MINI_PROGRAM("miniprogram", "小程序"),

    /**
     *
     */
    MEDIA_ID("media_id", "下发消息"),

    /**
     *
     */
    VIEW_LIMITED("view_limited", "跳转图文消息"),

    ;

    private String value;
    private String name;

    ButtonTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static ButtonTypeEnum typeValueOf(String value) {
        for (ButtonTypeEnum buttonType : values()) {
            if (buttonType.getValue().equals(value)) {
                return buttonType;
            }
        }
        return null;
    }

    public String getValue() {
        return this.value;
    }

}
