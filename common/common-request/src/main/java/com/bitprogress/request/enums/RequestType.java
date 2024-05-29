package com.bitprogress.request.enums;

import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请求类型
 */
@AllArgsConstructor
@Getter
public enum RequestType implements ValueEnum {

    /**
     * 用户请求
     */
    USER_REQUEST(0),

    /**
     * 匿名请求
     */
    ANONYMOUS_REQUEST(1),

    ;

    private final Integer value;

}
