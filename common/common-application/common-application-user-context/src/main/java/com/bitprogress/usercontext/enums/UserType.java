package com.bitprogress.usercontext.enums;

import com.bitprogress.basemodel.enums.MessageEnum;
import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType implements ValueEnum, MessageEnum {

    /**
     * 无状态用户、系统调度线程、public接口
     */
    NONE(-1, "无状态用户"),

    ;

    private final Integer value;

    private final String message;

}
