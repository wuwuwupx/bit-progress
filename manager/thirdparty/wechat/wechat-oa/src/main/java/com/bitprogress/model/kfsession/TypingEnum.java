package com.bitprogress.model.kfsession;

import java.io.Serializable;

/**
 * @author wuwuwupx
 */
public enum TypingEnum implements Serializable {

    /**
     * 正在输入状态
     */
    TYPING(0, "Typing"),

    /**
     * 取消正在输入
     */
    CANCEL_TYPING(1, "CancelTyping");

    private Integer value;

    private String name;

    TypingEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
