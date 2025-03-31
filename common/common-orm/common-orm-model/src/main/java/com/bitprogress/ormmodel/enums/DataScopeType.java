package com.bitprogress.ormmodel.enums;

import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DataScopeType implements ValueEnum {

    /**
     * 自身数据
     * user_id = ${userId}
     */
    SELF(0),

    /**
     * 查看所属层级的数据（不包括子层级的数据）
     * data_scope = dataScope
     */
    BELONG_LEVEL_CURRENT(1),

    /**
     * 查看所属层级的所有数据（包括子层级的数据）
     * data_scope like dataScope
     */
    BELONG_LEVEL(2),

    /**
     * 即可查看所管辖层级的数据（不包括子层级）
     * data_scope in dataScopes.add(dataScope)
     */
    MANAGED_LEVEL_CURRENT(3),

    /**
     * 即可查看所管辖层级的所有数据（包括子层级）
     * data_scope like dataScopes.add(dataScope)
     */
    MANAGED_LEVEL(4),

    /**
     * 综合层级
     * 即可以查看当前层级（部门或组织）和所管层级的数据（不包括子层级的数据）
     * data_scope in dataScopes.add(dataScope)
     */
    COMPOSITE_LEVEL_CURRENT(5),

    /**
     * 综合层级
     * 即可以查看当前层级（部门或组织）和所管层级的所有数据（包括子层级的数据）
     * data_scope like dataScopes.add(dataScope)
     */
    COMPOSITE_LEVEL(6),

    /**
     * 全部数据
     */
    ALL(7),

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
