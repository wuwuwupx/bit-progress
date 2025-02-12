package com.bitprogress.mybatisplus.handler;

import com.bitprogress.basemodel.enums.ValueEnum;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValueEnumTypeHandler<E extends Enum<E>> extends EnumOrdinalTypeHandler<E> {

    private final boolean isValueEnum;
    private final Class<E> enumClassType;
    private final E[] enums;

    public ValueEnumTypeHandler(Class<E> enumClassType) {
        super(enumClassType);
        this.enumClassType = enumClassType;
        this.enums = enumClassType.getEnumConstants();
        this.isValueEnum = ValueEnum.class.isAssignableFrom(enumClassType);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        if (isValueEnum) {
            ps.setInt(i, ((ValueEnum) parameter).getValue());
        } else {
            super.setNonNullParameter(ps, i, parameter, jdbcType);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (isValueEnum) {
            int value = rs.getInt(columnName);
            if (value == 0 && rs.wasNull()) {
                return null;
            }
            return valueOf(value);
        } else {
            return super.getNullableResult(rs, columnName);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (isValueEnum) {
            int value = rs.getInt(columnIndex);
            if (value == 0 && rs.wasNull()) {
                return null;
            }
            return valueOf(value);
        } else {
            return super.getNullableResult(rs, columnIndex);
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (isValueEnum) {
            int value = cs.getInt(columnIndex);
            if (value == 0 && cs.wasNull()) {
                return null;
            }
            return valueOf(value);
        } else {
            return super.getNullableResult(cs, columnIndex);
        }
    }

    private E valueOf(int value) {
        try {
            for (E e : enums) {
                if (((ValueEnum) e).getValue().equals(value)) {
                    return e;
                }
            }
            return null;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot convert " + value + " to " + enumClassType.getSimpleName() + " by value", ex);
        }
    }

}
