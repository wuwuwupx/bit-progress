package com.bitprogress.ormmodel.enums;

import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DataScopeType implements ValueEnum {

    /**
     * 全部数据
     */
    ALL(0),

    /**
     * 范围数据
     */
    SCOPE(1),

    /**
     * 自身数据
     */
    SELF(2),

    ;

    private final Integer value;

    /**
     * 根据value获取枚举
     *
     * @param value 枚举value
     * @return 枚举
     */
    public static DataScopeType getByValue(Integer value) {
        for (DataScopeType dataScopeType : values()) {
            if (dataScopeType.getValue().equals(value)) {
                return dataScopeType;
            }
        }
        return null;
    }

}
