package com.bitprogress.lock.annotation;

import com.bitprogress.basemodel.enums.NameEnum;
import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LockParamType implements ValueEnum, NameEnum {

    /**
     * 上下文类型，自定义获取
     */
    CONTEXT(0, "context"),

    /**
     * Spring EL表达式类型，通过 Spring EL表达式获取
     */
    SPRING_EL(1, "springEl"),

    ;

    private final Integer value;
    private final String name;

}
