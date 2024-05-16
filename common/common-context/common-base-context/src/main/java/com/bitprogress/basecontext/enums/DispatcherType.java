package com.bitprogress.basecontext.enums;

/**
 * 调度类型
 */
public enum DispatcherType {

    /**
     * 系统调度
     */
    SYSTEM_SCHEDULE(0),

    /**
     * 用户请求
     */
    USER_REQUEST(1),

    ;

    private final int value;

    public int getValue() {
        return value;
    }

    DispatcherType(int value) {
        this.value = value;
    }

}
