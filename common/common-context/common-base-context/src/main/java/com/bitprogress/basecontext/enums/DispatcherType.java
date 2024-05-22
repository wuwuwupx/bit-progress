package com.bitprogress.basecontext.enums;

import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 调度类型
 * 不同于 servlet 的 DispatcherType
 */
@Getter
@AllArgsConstructor
public enum DispatcherType implements ValueEnum {

    /**
     * 系统调度
     */
    SYSTEM_DISPATCH(0),

    /**
     * 定时任务调度
     */
    SCHEDULE_DISPATCH(1),

    /**
     * 匿名请求
     */
    ANONYMOUS_REQUEST(2),

    /**
     * 用户请求
     */
    USER_REQUEST(3),

    ;

    private final Integer value;

}
