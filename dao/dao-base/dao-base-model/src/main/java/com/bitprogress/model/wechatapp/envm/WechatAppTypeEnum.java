package com.bitprogress.model.wechatapp.envm;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author wuwuwupx
 */
public enum WechatAppTypeEnum implements IEnum<Integer> {

    /**
     * 小程序类型
     */
    APPLET(0, "小程序"),

    /**
     * 公众号类型
     */
    OA(1, "公众号"),

    ;

    private Integer value;
    private String name;

    WechatAppTypeEnum(Integer value, String name) {
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
