package com.bitprogress.ormmodel.enums;

import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * sql 类型
 */
@Getter
@AllArgsConstructor
public enum SqlType implements OrmEnum, ValueEnum {

    NONE(0),

    SELECT(1),

    INSERT(2),

    UPDATE(3),

    DELETE(4),

    ;

    private final Integer value;

}
