package com.bitprogress.annotation;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * sql 类型
 */
@Getter
@AllArgsConstructor
public enum SqlType {

    NONE(0),

    SELECT(1),

    INSERT(2),

    UPDATE(3),

    DELETE(4),

    ;

    private final int value;

}
