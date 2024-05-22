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

    /**
     * 匹配用户类型
     *
     * @param value 用户类型value
     * @return 用户类型
     */
    public static UserType matchOfValue(Integer value) {
        for (UserType userType : values()) {
            if (userType.getValue().equals(value)) {
                return userType;
            }
        }
        return null;
    }

}
