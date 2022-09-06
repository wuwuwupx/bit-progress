package com.bitprogress.model.app.envm;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author wuwuwupx
 */
public enum AppTypeEnum implements IEnum<Integer> {

    /**
     * 安卓类型
     */
    ANDROID(0, "安卓"),

    /**
     * ios类型
     */
    IOS(1, "ios"),

    /**
     * 微信小程序类型
     */
    WECHAT_APPLET(2, "微信小程序"),

    /**
     * 微信公众号类型
     */
    WECHAT_OA(3, "微信公众号"),

    ;

    private Integer value;
    private String name;

    AppTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }


}
